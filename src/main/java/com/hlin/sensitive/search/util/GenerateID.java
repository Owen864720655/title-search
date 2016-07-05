package com.hlin.sensitive.search.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * id生成器
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年6月7日
 *
 */
public class GenerateID {

    // id生成器
    private static AtomicInteger ids = new AtomicInteger(0);

    public static int setInitValue(int i) {
        ids.set(i);
        return ids.incrementAndGet();
    }
    
    public static int incrementAndGet() {
        return ids.incrementAndGet();
    }

}
