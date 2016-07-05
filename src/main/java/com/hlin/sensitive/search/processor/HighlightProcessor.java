package com.hlin.sensitive.search.processor;

import java.util.ArrayList;
import java.util.List;

import com.hlin.sensitive.search.CountData;
import com.hlin.sensitive.search.SensitiveWord;

/**
 * 对文本进行高亮处理。
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年5月22日
 *
 */
public class HighlightProcessor implements Processor {

    /**
     * 文本格式器
     */
    private AbstractFragment fragment;

    /**
     * 
     * @param fragment
     */
    public HighlightProcessor(AbstractFragment fragment) {
        this.fragment = fragment;
    }

    /**
     * 搜索文本处理器
     */
    public String process(CountData countData) {

        // 原文
        String text = countData.getSensitiveText().getText();

        // 结果集
        StringBuilder rs = new StringBuilder();

        List<SensitiveWord> words = countData.getWords();
        SensitiveWord word;
        int maxlen;
        for (int i = 0, len = words.size() - 1; i <= len; i++) {
            word = words.get(i);
            // 处理关键词头部
            if (i == 0) {
                rs.append(text.substring(0, word.getBeginPosition()));
            }
            // 处理关键词
            rs.append(fragment.format(word.getWord()));
            // 处理关键词尾部
            maxlen = text.length();
            if ((i + 1) <= len) {
                maxlen = words.get(i + 1).getBeginPosition();
            }
            rs.append(text.substring(word.getEndPosition(), maxlen));
        }
        return rs.toString();
    }

    /**
     * 批量处理
     */
    public List<String> process(List<CountData> countDatas) {

        List<String> rs = new ArrayList<String>(countDatas.size());
        for (CountData countData : countDatas) {
            rs.add(process(countData));
        }
        return rs;
    }

}
