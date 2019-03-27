package com.ssbc.nmg.dataspider.wuchain;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class ProductDaoPipeline implements Pipeline {

//    @Resource
//    private JobInfoDAO jobInfoDAO;

    @Override
    public void process(ResultItems resultItems, Task task) {
        if(resultItems!= null){
            Map all  = resultItems.getAll();
            if(all.containsKey("product")){

            }
        }
    }
}