package com.hailin.test.title_search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.hlin.sensitive.search.CountData;
import com.hlin.sensitive.search.KWSeeker;
import com.hlin.sensitive.search.KWSeekerManage;
import com.hlin.sensitive.search.SensitiveText;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    /**
     * Rigourous Test :-)
     * 
     * @throws Exception
     */
    public void testApp() throws Exception {
        Set<SensitiveText> sensitiveTexts = new HashSet<SensitiveText>();
        sensitiveTexts.add(new SensitiveText("天天都好很高兴"));
        sensitiveTexts.add(new SensitiveText("1今天也很好"));
        sensitiveTexts.add(new SensitiveText("今天真的高兴"));
        sensitiveTexts.add(new SensitiveText("我很高兴啊"));
        sensitiveTexts.add(new SensitiveText("1我明天很好"));
        sensitiveTexts.add(new SensitiveText("我明天去哪儿"));
        sensitiveTexts.add(new SensitiveText("现在天气怎么样"));

        KWSeeker kws = new KWSeeker(sensitiveTexts);
        System.out.println("简易文本检索器--》");
        System.out.println("词库数据：" + sensitiveTexts);

        // 搜索
        List<CountData> list = kws.search("我今天很高兴", 15);

        System.out.println("结果高亮\t命中关键字次数\t所有命中关键字位置");
        for (CountData data : list) {
            // System.out.println(data.result() + "\t" + data.getCount());
            System.out.println(data.result() + "\t" + data.getCount() + "\t" + data.getWords());
        }

    }

}
