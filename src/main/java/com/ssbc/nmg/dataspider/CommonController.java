package com.ssbc.nmg.dataspider;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.entity.JsonList;
import com.ssbc.nmg.dataspider.entity.PageParam;
import com.ssbc.nmg.dataspider.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("api")
public class CommonController {

    @Autowired
    private NmgpGovCnAgencyProcessor nmgpGovCnAgencyProcessor;

    @Autowired
    private AgencyDaoPipeline agencyDaoPipeline;

    @Autowired
    private AgencyService agencyService;

    @GetMapping(value = "start")
    public void StartSpider() {
        Spider.create(nmgpGovCnAgencyProcessor).addUrl(String.format(nmgpGovCnAgencyProcessor.baseUrl,nmgpGovCnAgencyProcessor.getPageNum()))
                .addPipeline(agencyDaoPipeline)
                .thread(5).run();
    }




    @GetMapping(value = "count")
    public int AgencyCount() {
        return agencyService.count(null);
    }


    @PostMapping(value = "getAgencyList")
    public Page<Agency> GetAgencyList( @RequestBody PageParam pageParam){




        Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();

        if(pageParam.getExtension().containsKey("name")) {
            String name  = pageParam.getExtension().get("name") != null?pageParam.getExtension().get("name").toString():"";
            if(name.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("ageinsname", name);
            }
        }
        if(pageParam.getExtension().containsKey("area") ) {
            String area  = pageParam.getExtension().get("area")!= null?pageParam.getExtension().get("area").toString():"";
            if(area.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("areaname", area);
            }
        }



//        JsonList<Agency> result = new JsonList<Agency>();
//
//        result.setDataSource(agencyService.selectListPage(current,pageSize,queryWrapper));
//        result.setTotal(result.getDataSource().size());
//        return result;
        return agencyService.selectListPage(pageParam.getCurrent(),pageParam.getPageSize(),queryWrapper);
    }



    @PostMapping(value = "extractingAgency")
    public Agency ExtractingAgency(@RequestBody Map<String,Object> param){

        Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();

        if(param.containsKey("name")) {
            String name  = param.get("name") != null?param.get("name").toString():"";
            if(name.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("ageinsname", name);
            }
        }
        if(param.containsKey("area") ) {
            String area  = param.get("area")!= null?param.get("area").toString():"";
            if(area.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("areaname", area);
            }
        }

        List<Agency> list = agencyService.list(queryWrapper);


        Random rand = new Random();
        return list.get( rand.nextInt(list.size()));
    }


    @GetMapping(value = "random")
    public List<Integer> getRandom(@RequestParam(required = false,defaultValue = "100")  Integer max,@RequestParam(required = false, defaultValue = "100")  Integer count){

        List<Integer> randoms = new ArrayList<Integer>();

        Random rand = new Random();
        for (int i=0;i<count;i++
             ) {
            randoms.add(rand.nextInt(max));
        }

        return   randoms;
    }


}
