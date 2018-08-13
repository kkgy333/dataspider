package com.ssbc.nmg.dataspider;

import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.mapper.AgencyMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;


@Component("AgencyDaoPipeline")
public class AgencyDaoPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());

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