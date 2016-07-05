package com.hlin.sensitive.search;

/**
 * 
 * 分解之后的词
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月8日
 * 
 */
public class SensitiveWord {

    /**
     * 文档id
     */
    private Integer docId;

    /**
     * 关键词
     */
    private String word;

    /**
     * 类型，中文/英文
     */
    private String type;

    /**
     * 关键词在文档中开始下标
     */
    private Integer beginPosition;

    /**
     * 关键词在文档中结束下标
     */
    private Integer endPosition;

    /**
     * 
     * @param docId
     * @param word
     * @param type
     * @param beginPosition
     * @param endPosition
     */
    public SensitiveWord(Integer docId, String word, String type, Integer beginPosition,
            Integer endPosition) {
        super();
        this.docId = docId;
        this.word = word;
        this.type = type;
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
    }

    /**
     * @return the docId
     */
    public Integer getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the beginPosition
     */
    public Integer getBeginPosition() {
        return beginPosition;
    }

    /**
     * @param beginPosition the beginPosition to set
     */
    public void setBeginPosition(Integer beginPosition) {
        this.beginPosition = beginPosition;
    }

    /**
     * @return the endPosition
     */
    public Integer getEndPosition() {
        return endPosition;
    }

    /**
     * @param endPosition the endPosition to set
     */
    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SensitiveWord [docId=" + docId + ", word=" + word + ", beginPosition="
                + beginPosition + ", endPosition=" + endPosition + "]";
    }

}
