package com.hlin.sensitive.search.processor;

/**
 * 高亮方式的片段
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月9日
 *
 */
public abstract class AbstractFragment {
    /**
     * 将指定的字符串用该格式化器进行格式化操作！
     * 
     * @param word 预处理的内容
     * @return 返回处理过的内容
     */
    public abstract String format(String word);
}
