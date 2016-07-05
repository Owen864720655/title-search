package com.hlin.sensitive.search;

import com.hlin.sensitive.search.util.GenerateID;

/**
 * 敏感词文本
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月7日
 *
 */
public class SensitiveText {
    /**
     * id
     */
    private Integer id;

    /**
     * type
     */
    private Integer type;

    /**
     * 敏感词
     */
    private String text;

    /**
     * @param text
     */
    public SensitiveText(String text) {
        this(GenerateID.incrementAndGet(), text);
    }

    /**
     * @param id
     * @param text
     */
    public SensitiveText(Integer id, String text) {
        this(id, 0, text);
    }

    /**
     * @param id
     * @param type
     * @param text
     */
    public SensitiveText(Integer id, Integer type, String text) {
        super();
        this.id = id;
        this.type = type;
        this.text = text;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
        SensitiveText other = (SensitiveText) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
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
        return text;
    }
    /*
     * public String toString() { return "SensitiveText [id=" + id + ", text=" + text + "]"; }
     */
}
