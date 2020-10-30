package com.jason.test;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import org.junit.Test;

/**
 * @ClassName Basic
 * @Description TODO
 * @Author GCJ
 * @Date 2020/10/13 15:21
 */
public class Basic extends BreadthCrawler {

    public Basic(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);

        System.out.println("____________________________________________________");

        /**设置爬取的网站地址
         * addSeed 表示添加种子
         * 种子链接会在爬虫启动之前加入到抓取信息中并标记为未抓取状态.这个过程称为注入*/
        this.addSeed("https://blog.github.com/");

        /**
         * 循环添加了4个种子，其实就是分页，结果类似：
         * https://blog.github.com/page/2/
         * https://blog.github.com/page/3/
         * https://blog.github.com/page/4/
         * https://blog.github.com/page/5/
         */
        for (int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("https://blog.github.com/page/%d/", pageIndex);
            this.addSeed(seedUrl);
        }

        /** addRegex 参数为一个 url 正则表达式, 可以用于过滤不必抓取的链接，如 .js .jpg .css ... 等
         * 也可以指定抓取某些规则的链接，如下 addRegex 中会抓取 此类地址：
         * https://blog.github.com/2018-07-13-graphql-for-octokit/
         * */
        this.addRegex("https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}-[^/]+/");
        /**
         * 过滤 jpg|png|gif 等图片地址 时：
         * this.addRegex("-.*\\.(jpg|png|gif).*");
         * 过滤 链接值为 "#" 的地址时：
         * this.addRegex("-.*#.*");
         */

        /**设置线程数*/
        setThreads(50);
        getConf().setTopN(100);

        /**
         * 是否进行断电爬取，默认为 false
         * setResumable(true);
         */
    }


    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        /**如果此页面地址 确实是要求爬取网址，则进行取值
         */
        if (page.matchUrl("https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}[^/]+/")) {

            /**
             * 通过 选择器 获取页面 标题以及 正文内容
             * */
            String title = page.select("h1[class=lh-condensed]").first().text();
            String content = page.selectText("div.content.markdown-body");

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);

        }

    }

    public static void main(String[] args) throws Exception {
        /**
         * DemoAutoNewsCrawler 构造器中会进行 数据初始化，这两个参数接着会传给父类
         * super(crawlPath, autoParse);
         * crawlPath：表示设置保存爬取记录的文件夹，本例运行之后会在应用根目录下生成一个 "crawl" 目录存放爬取信息
         * */
        Basic crawler = new Basic("crawl", true);
        /**
         * 启动爬虫，爬取的深度为4层
         * 添加的第一层种子链接,为第1层
         */
        crawler.start(4);
    }



}
