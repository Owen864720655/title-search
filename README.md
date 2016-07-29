# title-search

	简单的标题搜索,核心思想就是"内存hash ＋ ID list"。

# 演示效果
 ![Alt 演示效果](/doc/jg.jpg "演示效果")

# "内存hash ＋ ID list"概述
    其实就是“倒排索引”，不同的是索引不落地存储。索引初始化步骤为：对所有标题进行分词，以词的hash为key，doc_id的集合为value
    查询的步骤为：对查询词进行分词，对分词进行hash，直接查询hash表格，获取doc_id的list，然后多个词进行合并
    =====例子=====
    例如：
    doc1 : 我爱北京
    doc2 : 我爱到家
    doc3 : 到家美好
    先标题进行分词：
    doc1 : 我爱北京 -> 我，爱，北京
    doc2 : 我爱到家 -> 我，爱，到家
    doc3 : 到家美好 -> 到家，美好
    对分词进行hash，建立hash + ID list：
    hash(我) -> {doc1, doc2}
    hash(爱) -> {doc1, doc2}
    hash(北京) -> {doc1}
    hash(到家) -> {doc2, doc3}
    hash(美好) -> {doc3}
    这样，所有标题的初始化就完毕了，你会发现，数据量和标题的长度没有关系。
    用户输入“我爱”，分词后变为{我，爱}，对各个分词的hash进行内存检索
    hash(我)->{doc1, doc2}
    hash(爱)->{doc1, doc2}
    然后进行合并，得到最后的查找结果是doc1+doc2。
    =====例子END=====



# 代码示例
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
        System.out.println("搜索数据：" + "我今天很高兴");
        List<CountData> list = kws.search("我今天很高兴", 15);

        System.out.println("结果高亮\t命中关键字次数\t所有命中关键字位置");
        for (CountData data : list) {
            // System.out.println(data.result() + "\t" + data.getCount());
            System.out.println(data.result() + "\t" + data.getCount() + "\t" + data.getWords());
        }
        

# 测试代码
<a href="https://github.com/hailin0/title-search/blob/master/src/test/java/com/hailin/test/title_search/AppTest.java">点击查看-com.hailin.test.title_search.AppTest.java</a>
