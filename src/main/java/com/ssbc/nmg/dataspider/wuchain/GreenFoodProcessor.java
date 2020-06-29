package com.ssbc.nmg.dataspider.wuchain;

import com.ssbc.nmg.dataspider.wuchain.model.Enterprise;
import com.ssbc.nmg.dataspider.wuchain.model.Product;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;

public class GreenFoodProcessor implements PageProcessor {

    private Site site = Site
            .me().setRetryTimes(3)
            .setDomain("lssp.agri.gov.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");


    //private String listUrl = "http://lssp.agri.gov.cn/jn_ls/module/ls/outSearch/cpsearch.do";


    private int pageNum = 1;
    private int pageTotal = 0;
    private int dateTotal = 0;

    private boolean singleComplete =false;
    private int remoteCount =0;
    @Override
    public void process(Page page) {


        //列表页
        if (page.getHtml().xpath("//div[@id='divresult']").match()) {


            //添加详情页面
            page.addTargetRequests( page.getHtml().xpath("//div[@id='divresult']/table").links().all());

            //添加列表下一页
            Selectable tabPage = page.getHtml().xpath("//span[@id='lblinfo']/table/tbody/tr/td/font/text()").regex("第\\s*\\d+\\s*/\\s*\\d+\\s*页\\s*\\d+\\s*条信息");
            pageNum = Integer.parseInt(tabPage.regex("第\\s*(\\d+)\\s*/\\s*(\\d+)\\s*页\\s*(\\d+)\\s*条信息",1).toString());
            pageTotal = Integer.parseInt(tabPage.regex("第\\s*(\\d+)\\s*/\\s*(\\d+)\\s*页\\s*(\\d+)\\s*条信息",2).toString());
            dateTotal = Integer.parseInt(tabPage.regex("第\\s*(\\d+)\\s*/\\s*(\\d+)\\s*页\\s*(\\d+)\\s*条信息",3).toString());

            if(pageNum<pageTotal){
                Request nextPage = new Request();
                nextPage.setUrl(page.getUrl().toString());
                nextPage.setMethod(HttpConstant.Method.POST);
                nextPage.setRequestBody(HttpRequestBody.json("{'page':"+pageNum+1+"}","utf-8"));
                page.addTargetRequest(nextPage);
            }

            //详情页面
        } else {
            //抓取企业信息
            Enterprise enterprise  = new Enterprise();
            enterprise.setEnterpriseNameCn(page.getHtml().regex("fillformValue\\[\"entnamecn\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setEnterpriseNameEn(page.getHtml().regex("fillformValue\\[\"entnameen\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setInfocode(page.getHtml().regex("fillformValue\\[\"entinfocode\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setOrganizationCode(page.getHtml().regex("fillformValue\\[\"entregno\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setZipCode(page.getHtml().regex("fillformValue\\[\"entpostcode\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setTelephoneNumber(page.getHtml().regex("fillformValue\\[\"contactpersonphone\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setContactPerson(page.getHtml().regex("fillformValue\\[\"contactperson\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setAddress(page.getHtml().regex("fillformValue\\[\"annadd\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setEmail(page.getHtml().regex("fillformValue\\[\"entemail\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setWebSite(page.getHtml().regex("fillformValue\\[\"annwebsite\"\\] = \"([^\"]*)\"",1).toString());
            enterprise.setRegion(page.getHtml().regex("fillformValue\\[\"entsecbusi\"\\] = \"([^\"]*)\"",1).toString());



            //抓取产品信息
            List<Product> products= new ArrayList<Product>();


            for (Selectable row:page.getHtml().xpath("//table[@id='entDetail']/tbody/tr/td[@colspan='2']/table/tbody/tr[@class!='ofcctabletop']").nodes()
                 ) {
                List<String> values = row.xpath("//td/text()").all();
                Product product = new Product();
                product.setProductName(values.get(0));
                product.setCategory(values.get(1));
                product.setTrademark(values.get(2));
                product.setApprovedOutput(values.get(3));
                product.setMarkNumber(values.get(4));
                product.setValidity(values.get(5));

                products.add(product);
            }

            page.putField("enterprise", enterprise);
            page.putField("product", products);
        }








//        if (page.getRawText()==null ||((JSONArray)(JsonPath.read(page.getRawText(), "$.[0].[*]"))).size() == 0){
//            //skip this page
//            page.setSkip(true);
//            singleComplete =true;
//        }
//
//        int count =  Integer.parseInt(new JsonPathSelector("$.[1].[0].page_all").select(page.getRawText()));
//
//        if(singleComplete){
//            remoteCount = count;
//        }
//
//        if(count >remoteCount) {
//
//            page.putField("page_all", count);
//
//
//            page.putField("content", JsonPath.read(page.getRawText(), "$.[0].[*]").toString());
//
//
//            page.addTargetRequest(String.format(baseUrl, pageNum = getPageNum() + 1));
//        }
    }

    @Override
    public Site getSite() {
        return site;
    }





    public static void main(String[] args) {

        Spider.create(new GreenFoodProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://lssp.agri.gov.cn/jn_ls/module/ls/outSearch/qiyesearch.do")
                .addPipeline(new EnterpriseDaoPipeline()).addPipeline(new ProductDaoPipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}