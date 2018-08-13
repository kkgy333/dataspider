package com.ssbc.nmg.dataspider;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
@Component
public class NmgpGovCnAgencyProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public final String baseUrl = "http://www.nmgp.gov.cn/category/ajax?byf_page=%d&fun=dljg";
    private int pageNum = 1;
    private boolean singleComplete =false;
    private int remoteCount =0;
    @Override
    public void process(Page page) {

        if (page.getRawText()==null ||((JSONArray)(JsonPath.read(page.getRawText(), "$.[0].[*]"))).size() == 0){
            //skip this page
            page.setSkip(true);
            singleComplete =true;
        }

        int count =  Integer.parseInt(new JsonPathSelector("$.[1].[0].page_all").select(page.getRawText()));

        if(singleComplete){
            remoteCount = count;
        }

        if(count >remoteCount) {

            page.putField("page_all", count);


            page.putField("content", JsonPath.read(page.getRawText(), "$.[0].[*]").toString());


            page.addTargetRequest(String.format(baseUrl, pageNum = getPageNum() + 1));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }



    public int getPageNum() {
        return pageNum;
    }
}