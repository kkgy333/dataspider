package com.ssbc.nmg.dataspider.wuchain;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class EnterpriseDaoPipeline  implements Pipeline {

//    @Resource
//    private JobInfoDAO jobInfoDAO;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //调用MyBatis DAO保存结果
        //jobInfoDAO.add(lieTouJobInfo);

        if(resultItems!= null){
            Map  all  = resultItems.getAll();
            if(all.containsKey("enterprise")){

            }
        }
    }
}