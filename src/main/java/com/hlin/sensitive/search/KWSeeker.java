package com.hlin.sensitive.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hlin.sensitive.search.processor.HTMLFragment;
import com.hlin.sensitive.search.processor.HighlightProcessor;
import com.hlin.sensitive.search.processor.Processor;

/**
 * 
 * 关键词搜索器
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年5月22日
 * 
 */
public class KWSeeker {

    /**
     * 构建IK分词器，使用smart分词模式
     */
    private Analyzer analyzer;

    /**
     * 全部词组
     */
    private Set<SensitiveText> sensitiveTexts;

    /**
     * 文档树
     */
    private Map<Integer, SensitiveText> docTree;

    /**
     * 分词树
     */
    private Map<String, Set<SensitiveWord>> wordTree;

    /**
     * 默认处理器
     */
    private Processor defaultProcessor = new HighlightProcessor(new HTMLFragment(
            "<font color='red'>", "</font>"));
    /**
     * 默认返回条数
     */
    private int defaultTop = Integer.MAX_VALUE;

    /**
     * 构造器
     * 
     * @param sensitiveTexts
     */
    public KWSeeker(Set<SensitiveText> sensitiveTexts) {
        this.analyzer = new IKAnalyzer(true);
        this.docTree = new ConcurrentHashMap<Integer, SensitiveText>();
        this.wordTree = new ConcurrentHashMap<String, Set<SensitiveWord>>();
        this.sensitiveTexts = sensitiveTexts;

        initialize();
    }

    /**
     * 构造器
     * 
     * @param sensitiveTexts
     * @param processor
     * @param top
     */
    public KWSeeker(Set<SensitiveText> sensitiveTexts, Processor processor, int top) {
        this(sensitiveTexts);
        this.defaultProcessor = processor;
        this.defaultTop = top;
    }

    /**
     * 初始化
     */
    private void initialize() {
        TokenStream ts = null;
        try {
            // 获取词元位置属性
            OffsetAttribute offset;
            // 获取词元文本属性
            CharTermAttribute term;
            // 获取词元文本属性
            TypeAttribute type;

            for (SensitiveText text : sensitiveTexts) {
                docTree.put(text.getId(), text);
                ts = analyzer.tokenStream("", new StringReader(text.getText()));
                offset = ts.addAttribute(OffsetAttribute.class);
                term = ts.addAttribute(CharTermAttribute.class);
                type = ts.addAttribute(TypeAttribute.class);
                ts.reset();
                // 迭代获取分词结果
                while (ts.incrementToken()) {
                    String word = term.toString();
                    SensitiveWord sensitiveWord = new SensitiveWord(text.getId(), word,
                            type.type(), offset.startOffset(), offset.endOffset());
                    if (wordTree.containsKey(word)) {
                        wordTree.get(word).add(sensitiveWord);
                        continue;
                    }
                    HashSet<SensitiveWord> sensitiveWords = new HashSet<SensitiveWord>();
                    sensitiveWords.add(sensitiveWord);
                    wordTree.put(word, sensitiveWords);
                }
                // 关闭TokenStream（关闭StringReader）
                ts.end();
                ts.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 搜索
     * 
     * @param text
     * @return
     * @throws Exception
     */
    public List<CountData> search(String text) {
        return search(text, defaultProcessor, defaultTop);
    }

    /**
     * 搜索
     * 
     * @param text
     * @return
     * @throws Exception
     */
    public List<String> searchToString(String text) {
        return searchToString(text, defaultProcessor, defaultTop);
    }

    /**
     * 搜索
     * 
     * @param text
     * @param top
     * @return
     */
    public List<CountData> search(String text, int top) {
        return search(text, defaultProcessor, top);
    }

    /**
     * 搜索
     * 
     * @param text
     * @param top
     * @return
     */
    public List<String> searchToString(String text, int top) {
        return searchToString(text, defaultProcessor, top);
    }

    /**
     * 搜索
     * 
     * @param text
     * @param processor
     * @param top
     * @return
     */
    public List<String> searchToString(String text, Processor processor, int top) {
        List<CountData> countDatas = search(text, processor, top);
        return processor.process(countDatas);
    }

    /**
     * 搜索
     * 
     * @param text
     * @param processor
     * @param top
     * @return
     */
    public List<CountData> search(String text, Processor processor, int top) {
        SearchAnalyze searchAnalyze = new SearchAnalyze(this, processor, top);
        // 分析结果
        List<CountData> list = searchAnalyze.top(text);
        searchAnalyze = null;

        return list;
    }

    /**
     * 获取敏感词文本
     * 
     * @param docId
     * @return
     */
    public SensitiveText getSensitiveText(Integer docId) {
        return docTree.get(docId);
    }

    /**
     * 根据词返回词组
     * 
     * @param word
     * @return
     */
    public Set<SensitiveWord> getWords(String word) {
        return wordTree.get(word);
    }

    /**
     * 返回分词器
     * 
     * @return
     */
    public Analyzer getAnalyzer() {
        return this.analyzer;
    }

}
