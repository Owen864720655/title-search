package com.hlin.sensitive.search;

import java.util.ArrayList;
import java.util.List;

import com.hlin.sensitive.search.processor.Processor;

/**
 * 
 * 类/接口注释
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月8日
 * 
 */
public class CountData implements Comparable<CountData> {

    /**
     * 敏感文本
     */
    private SensitiveText sensitiveText;

    /**
     * 统计次数
     */
    private int count;

    /**
     * 命中词
     */
    private List<SensitiveWord> words;

    /**
     * 最终结果
     */
    private String resultText;
    
    /**
     * 结果处理器
     */
    private Processor processor;

    /**
     * 
     * @param sensitiveText
     */
    public CountData(SensitiveText sensitiveText) {
        super();
        this.sensitiveText = sensitiveText;
        this.count = 1;
        words = new ArrayList<SensitiveWord>();
    }

    /**
     * 
     * @param sensitiveText
     * @param word
     * @param processor
     */
    public CountData(SensitiveText sensitiveText, SensitiveWord word,Processor processor) {
        this(sensitiveText);
        words.add(word);
        this.processor = processor;
    }

    /**
     * @return the sensitiveText
     */
    public SensitiveText getSensitiveText() {
        return sensitiveText;
    }

    /**
     * @param sensitiveText the sensitiveText to set
     */
    public void setSensitiveText(SensitiveText sensitiveText) {
        this.sensitiveText = sensitiveText;
    }

    /**
     * @return the resultText
     */
    public String getResultText() {
        return resultText;
    }

    /**
     * @param resultText the resultText to set
     */
    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the words
     */
    public List<SensitiveWord> getWords() {
        return words;
    }

    /**
     * 加入统计
     * 
     * @param word
     */
    public void add(SensitiveWord word) {
        this.count++;
        words.add(word);
    }

    /**
     * 
     * @return
     */
    public String result() {
        if(null == processor){
            this.resultText = sensitiveText.getText();
        }else{
            this.resultText = processor.process(this);
        }
        return resultText;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(CountData o) {
        if (this.count == o.getCount()) {
            return 0;
        }
        return this.count > o.getCount() ? -1 : 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CountData other = (CountData) obj;
        if (sensitiveText == null) {
            if (other.sensitiveText != null)
                return false;
        } else if (!sensitiveText.equals(other.sensitiveText))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CountData [sensitiveText=" + sensitiveText + ", count=" + count + ", words="
                + words + "]";
    }

}