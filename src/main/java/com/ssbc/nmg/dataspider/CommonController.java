package com.ssbc.nmg.dataspider;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.AgencyExtract;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;
import com.ssbc.nmg.dataspider.entity.JsonList;
import com.ssbc.nmg.dataspider.entity.PageParam;
import com.ssbc.nmg.dataspider.service.AgencyService;
import com.ssbc.nmg.dataspider.service.ExtractingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.*;

@RestController
@RequestMapping("api")
public class CommonController {

    @Autowired
    private NmgpGovCnAgencyProcessor nmgpGovCnAgencyProcessor;

    @Autowired
    private AgencyDaoPipeline agencyDaoPipeline;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private ExtractingLogService extractingLogService;

    @GetMapping(value = "start")
    public void StartSpider() {

        agencyService.remove(null);
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

        ((QueryWrapper<Agency>) queryWrapper).notIn("ageinstypename","集中采购机构");

//        if(pageParam.getExtension().containsKey("name")) {
//            String name  = pageParam.getExtension().get("name") != null?pageParam.getExtension().get("name").toString():"";
//            if(name.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("ageinsname", name);
//            }
//        }
//        if(pageParam.getExtension().containsKey("area") ) {
//            String area  = pageParam.getExtension().get("area")!= null?pageParam.getExtension().get("area").toString():"";
//            if(area.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("areaname", area);
//            }
//        }

        if(pageParam.getExtension().containsKey("areacode") ) {
            String areacode  = pageParam.getExtension().get("areacode")!= null?pageParam.getExtension().get("areacode").toString():"";
            if(areacode.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).eq("areacode", areacode);
            }
        }


        if(pageParam.getExtension().containsKey("opeadd") ) {
            String opeadd  = pageParam.getExtension().get("opeadd")!= null?pageParam.getExtension().get("opeadd").toString():"";
            if(opeadd.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("opeadd", opeadd);
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
        ((QueryWrapper<Agency>) queryWrapper).notIn("ageinstypename","集中采购机构");
//        if(param.containsKey("name")) {
//            String name  = param.get("name") != null?param.get("name").toString():"";
//            if(name.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("ageinsname", name);
//            }
//        }
//        if(param.containsKey("area") ) {
//            String area  = param.get("area")!= null?param.get("area").toString():"";
//            if(area.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("areaname", area);
//            }
//        }



        if(param.containsKey("areacode")) {
            String areacode  = param.get("areacode") != null?param.get("areacode").toString():"";
            if(areacode.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).eq("areacode", areacode);
            }
        }
        if(param.containsKey("opeadd") ) {
            String opeadd  = param.get("opeadd")!= null?param.get("opeadd").toString():"";
            if(opeadd.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("opeadd", opeadd);
            }
        }




        List<Agency> list = agencyService.list(queryWrapper);


        Random rand = new Random();
        Agency agency= list.get( rand.nextInt(list.size()));

        ExtractingLog extractingLog = new ExtractingLog();
        extractingLog.setAgencyId(agency.getID());
        extractingLog.setAgeinsName(agency.getAGEINSNAME());
        extractingLog.setExtractTime(new Date());
        extractingLogService.save(extractingLog);

        return agency;
    }


    @PostMapping(value = "getExtractingLogList")
    public Page<AgencyExtract> GetExtractingLogList( @RequestBody PageParam pageParam){

       Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();
//
//        if(pageParam.getExtension().containsKey("name")) {
//            String name  = pageParam.getExtension().get("name") != null?pageParam.getExtension().get("name").toString():"";
//            if(name.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("ageinsname", name);
//            }
//        }
//        if(pageParam.getExtension().containsKey("area") ) {
//            String area  = pageParam.getExtension().get("area")!= null?pageParam.getExtension().get("area").toString():"";
//            if(area.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).like("areaname", area);
//            }
//        }

        Page<AgencyExtract> page = new Page<AgencyExtract>(pageParam.getCurrent(),pageParam.getPageSize());

        Page<AgencyExtract> agencyExtracts=  extractingLogService.selectExtractingLog(page);
        return agencyExtracts;

//        List<ExtractingLog> logs= extractingLogService.list(null);
//        Collection<String> values = new ArrayList<String>();
//        for (ExtractingLog log: logs) {
//            values.add(log.getAgencyId());
//        }
//
//        ((QueryWrapper<Agency>) queryWrapper).in("id",values);
//
//
//        return agencyService.selectListPage(pageParam.getCurrent(),pageParam.getPageSize(),queryWrapper);
    }


    @PostMapping(value = "deleteExtractingLog")
    public boolean DeleteExtractingLog( @RequestBody Map<String,Object> param){
        if(param.containsKey("id")) {
            String id  = param.get("id") != null?param.get("id").toString():"";
            if(id.length()!=0) {

                Wrapper<ExtractingLog> queryWrapper = new QueryWrapper<ExtractingLog>();
                ((QueryWrapper<ExtractingLog>) queryWrapper).eq("id",id);
                return extractingLogService.remove(queryWrapper);
            }
        }
        return false;
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
