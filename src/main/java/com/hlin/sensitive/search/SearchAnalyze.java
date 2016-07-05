package com.hlin.sensitive.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.hlin.sensitive.search.processor.Processor;

/**
 * 搜索分析类
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月8日
 * 
 */
public class SearchAnalyze {

    /**
     * 结果集大小
     */
    private int top;
    /**
     * 统计结果集合
     */
    private List<CountData> counts;

    /**
     * 搜索器
     */
    private KWSeeker kwSeeker;

    /**
     * 结果处理器
     */
    private Processor processor;
    

    /**
     * 
     * @param kwSeeker
     * @param processor
     * @param top
     */
    public SearchAnalyze(KWSeeker kwSeeker, Processor processor,int top) {
        super();
        this.kwSeeker = kwSeeker;
        this.processor = processor;
        this.top = top;
        counts = new ArrayList<CountData>();
    }

    /**
     * 分析
     * 
     * @param text
     */
    private void analyzer(String text) {
        TokenStream ts = null;
        try {
            ts = kwSeeker.getAnalyzer().tokenStream("", new StringReader(text));
            // 获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            // 重置TokenStream（重置StringReader）
            ts.reset();
            // 迭代获取分词结果
            //System.out.print("分词结果:");
            while (ts.incrementToken()) {
                String word = term.toString();
                //System.out.print(word + ",");
                statistics(kwSeeker.getWords(word));
            }
            //System.out.println();
            // 关闭TokenStream（关闭StringReader）
            ts.end();
            ts.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 释放TokenStream的所有资源
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
     * 统计
     * 
     * @param words
     */
    private void statistics(Set<SensitiveWord> words) {
        if (null == words || words.isEmpty()) {
            return;
        }
        for (SensitiveWord word : words) {
            CountData countData = new CountData(kwSeeker.getSensitiveText(word.getDocId()), word,processor);
            int i = counts.indexOf(countData);
            if (i == -1) {
                counts.add(countData);
            } else {
                counts.get(i).add(word);
            }
        }
    }

    /**
     * 返回频率最高的top条
     * 
     * @param top
     * @return
     */
    public List<CountData> top(String text) {
        analyzer(text);
        Collections.sort(counts);
        return counts.subList(0, top > counts.size() ? counts.size() : top);
    }

}
