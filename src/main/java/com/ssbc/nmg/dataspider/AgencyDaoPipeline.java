package com.ssbc.nmg.dataspider;

import com.alibaba.fastjson.JSON;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.mapper.AgencyMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Component("AgencyDaoPipeline")
public class AgencyDaoPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());


        List<Agency> agencyList =JSON.parseArray(resultItems.get("content").toString(),Agency.class);


//        List<Agency> agencyList = new ArrayList<Agency>();
//        for (Object item:(JSONArray)resultItems.get("content")){
//            LinkedHashMap row = (LinkedHashMap)item;
//            Agency agency = new Agency();
//            agency.setId( row.get("ID").toString());
//            agency.setAGEINSNAME(row.get("AGEINSNAME").toString());
//            agency.setAGEINSTYPENAME(row.get("AGEINSTYPENAME").toString());
//            agency.setECOTYPENAME(row.get("ECOTYPENAME").toString());
//            agency.setAREACODE(row.get("AREACODE").toString());
//            agency.setAREANAME(row.get("AREANAME").toString());
//            agency.setTEL(row.get("TEL").toString());
//            agencyList.add(agency);
//        }

        //((JSONArray) resultItems.get("content")).toJSONString();

        //实体类 javaBean = JSON.parseObject(json, 实体类.class);
    }
}

//    @Resource
//    private AgencyMapper agencyMapper;

//    @Override
//    public void process(Agency agency, Task task) {
//        //jobInfoDAO.add(lieTouJobInfo);
//        //agencyMapper.insert(agency);
//    }
//}