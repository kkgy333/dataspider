package com.ssbc.nmg.dataspider;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jayway.jsonpath.JsonPath;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.service.AgencyService;
import com.ssbc.nmg.dataspider.wuchain.EnterpriseDaoPipeline;
import com.ssbc.nmg.dataspider.wuchain.GreenFoodProcessor;
import com.ssbc.nmg.dataspider.wuchain.ProductDaoPipeline;
import com.ssbc.nmg.dataspider.wuchain.model.Enterprise;
import com.ssbc.nmg.dataspider.wuchain.model.Product;
import lombok.Getter;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class NmgpGovCnAgencyProcessorV2 implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public final String baseUrl = "http://www.nmgp.gov.cn/zfcgwslave/web/index.php?r=new-data%2Fdljg-list";

    public final String detailUrl = "http://www.nmgp.gov.cn/category/dljg?dljgid=";
    @Getter
    private int pageNum = 1;
    @Getter
    private int pageSize =18;
    private boolean singleComplete =false;
    private int remoteCount =0;

    @Autowired
    private AgencyService agencyService;

    @Override
    public void process(Page page) {

        if(page.getUrl().regex(".*nmgp.*/category/dljg").match()) {

            if (page.getHtml().xpath("//div[@class='byf_dljg_c_top']").match()) {
                Selectable node = page.getHtml().xpath("//table[@class='byf_table_id zy_gys_table_info table1']");



                Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();
                ((QueryWrapper<Agency>) queryWrapper).in("id",page.getUrl().regex(".*nmgp.*/category/dljg\\?dljgid=([^&]*)").toString());

                Agency agency  = agencyService.getOne(queryWrapper);
                if(agency == null)
                    agency = new Agency();


                agency.setID(page.getUrl().regex(".*nmgp.*/category/dljg\\?dljgid=([^&]*)").toString());
//                node.regex("<td [^>]+>代理机构名称</td>\\s*<td[^>]+>([^<]+)</td>",1);
                agency.setOPEADD( node.regex("<td [^>]+>经营地址</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());
//                node.regex("<td [^>]+>联系电话</td>\\s*<td[^>]+>([^<]+)</td>",1);
                agency.setFAX(node.regex("<td [^>]+>传真号码</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());
//                node.regex("<td [^>]+>注册地址</td>\\s*<td[^>]+>([^<]+)</td>",1);
                agency.setEMAIL(node.regex("<td [^>]+>电子邮箱</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());
                agency.setZIP(node.regex("<td [^>]+>邮政编码</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());
                agency.setREGFUNAMOUT(node.regex("<td [^>]+>注册资金（万元）</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());

                LocalDate date = LocalDate.parse(node.regex("<td [^>]+>成立时间</td>\\s*<td[^>]+>([^<]+)</td>",1).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                agency.setREGDATE(date);
//                node.regex("<td [^>]+>登记时间</td>\\s*<td[^>]+>([^<]+)</td>",1).toString();
                agency.setINDACOMMREGADD(node.regex("<td [^>]+>办公场所地址</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());
               agency.setMAJORSCOPE( node.regex("<td [^>]+>经营范围</td>\\s*<td[^>]+>([^<]+)</td>",1).toString());

                page.putField("agency", agency);

            }
        }else {

            if (page.getRawText() == null || ((JSONArray) (JsonPath.read(page.getRawText(), "$.[0].[*]"))).size() == 0) {
                //skip this page
                page.setSkip(true);
                singleComplete = true;
            }

            int count = Integer.parseInt(new JsonPathSelector("$.[1]").select(page.getRawText()));

            if (singleComplete) {
                remoteCount = count;
            }

            if (count > remoteCount) {

                String json = JsonPath.read(page.getRawText(), "$.[0].[*]").toString();
                page.putField("page_all", count);


                page.putField("content", json);

                List<Agency> agencyList = JSON.parseArray(json, Agency.class);

                agencyList.forEach(item -> {
                    page.addTargetRequest(detailUrl + item.getAGEINSID());
                });
                Request nextPage = new Request();
                nextPage.setUrl(baseUrl);
                nextPage.setMethod(HttpConstant.Method.POST);
                Map<String, Object> params = new HashMap<>();
                params.put("byf_page", ++pageNum);
                params.put("page_size", pageSize);
                nextPage.setRequestBody(HttpRequestBody.form(params, "utf-8"));

                page.addTargetRequest(nextPage);

            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }






    public void reset() {
        pageNum = 1;
        singleComplete = false;
        remoteCount = 0;
    }

    public static void main(String[] args) {


//        Request startPage = new Request();
//        startPage.setUrl("http://www.nmgp.gov.cn/zfcgwslave/web/index.php?r=new-data%2Fdljg-list");
//        startPage.setMethod(HttpConstant.Method.POST);
//        Map<String, Object> params = new HashMap<>();
//        params.put("byf_page", 1);
//        params.put("page_size", 18);
//        startPage.setRequestBody(HttpRequestBody.form(params, "utf-8"));
//
//
//        Spider.create(new NmgpGovCnAgencyProcessorV2()).addRequest(startPage)
//                .addPipeline(new AgencyDaoPipeline())
//                //开启5个线程抓取
//                .thread(5)
//                //启动爬虫
//                .run();


    }
}