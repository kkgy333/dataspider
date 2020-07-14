package com.ssbc.nmg.dataspider;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.AgencyExtract;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;
import com.ssbc.nmg.dataspider.dao.User;
import com.ssbc.nmg.dataspider.entity.PageParam;
import com.ssbc.nmg.dataspider.jwt.JwtHelper;
import com.ssbc.nmg.dataspider.service.AgencyService;
import com.ssbc.nmg.dataspider.service.ExtractingLogService;
import com.ssbc.nmg.dataspider.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.util.*;

@RestController
@RequestMapping("api")
public class CommonController {

//    @Autowired
//    private NmgpGovCnAgencyProcessor nmgpGovCnAgencyProcessor;

    @Autowired
    private AgencyDaoPipeline agencyDaoPipeline;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private ExtractingLogService extractingLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private NmgpGovCnAgencyProcessorV2 nmgpGovCnAgencyProcessor;

//    private Spider spider;


    @PostMapping(value = "login")
    public String Login(@RequestBody Map<String,Object> param) {
        String username="";
        String password = "";
        if(param.containsKey("username")) {
            username  = param.get("username") != null?param.get("username").toString():"";
        }
        if(param.containsKey("password") ) {
            password  = param.get("password")!= null?param.get("password").toString():"";
        }
        return jwtHelper.getToken(username,password);
    }

    @GetMapping(value = "start")
    public void StartSpider() {

        agencyService.remove(null);
//        nmgpGovCnAgencyProcessor.reset();
//        if(spider==null) {
//            spider =

        Request startPage = new Request();
        startPage.setUrl(nmgpGovCnAgencyProcessor.baseUrl);
        startPage.setMethod(HttpConstant.Method.POST);
        Map<String, Object> params = new HashMap<>();
        params.put("byf_page", nmgpGovCnAgencyProcessor.getPageNum());
        params.put("page_size", nmgpGovCnAgencyProcessor.getPageSize());
        startPage.setRequestBody(HttpRequestBody.form(params, "utf-8"));


                    Spider.create(nmgpGovCnAgencyProcessor).addRequest(startPage)
                    .addPipeline(agencyDaoPipeline)
                    .thread(5).run();
//        }
//        spider.run();

    }




    @GetMapping(value = "count")
    public int AgencyCount() {
        return agencyService.count(null);
    }


    @PostMapping(value = "getAgencyList")
    public Page<Agency> GetAgencyList( @RequestBody PageParam pageParam){




        Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();

//        ((QueryWrapper<Agency>) queryWrapper).notIn("ageinstypename","集中采购机构").in("county","赛罕区","新城区");
//
//        ((QueryWrapper<Agency>) queryWrapper).notInSql("AGEINSID","SELECT agency_id FROM extracting_log WHERE extract_time > '2018-12-31 23:59:59'");


//        if(pageParam.getExtension().containsKey("areacode") ) {
//            String areacode  = pageParam.getExtension().get("areacode")!= null?pageParam.getExtension().get("areacode").toString():"";
//            if(areacode.length()!=0) {
//                ((QueryWrapper<Agency>) queryWrapper).eq("areacode", areacode);
//            }
//        }


        if(pageParam.getExtension().containsKey("opeadd") ) {
            String opeadd  = pageParam.getExtension().get("opeadd")!= null?pageParam.getExtension().get("opeadd").toString():"";
            if(opeadd.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("AREANAME", opeadd);
            }
        }

        return agencyService.selectListPage(pageParam.getCurrent(),pageParam.getPageSize(),queryWrapper);
    }



    @PostMapping(value = "extractingAgency")
    public Agency ExtractingAgency(@RequestBody Map<String,Object> param){

        Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();
        ((QueryWrapper<Agency>) queryWrapper).notIn("ageinstypename","集中采购机构");

        if(param.containsKey("areacode")) {
            String areacode  = param.get("areacode") != null?param.get("areacode").toString():"";
            if(areacode.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).eq("areacode", areacode);
            }
        }
        if(param.containsKey("opeadd") ) {
            String opeadd  = param.get("opeadd")!= null?param.get("opeadd").toString():"";
            if(opeadd.length()!=0) {
                ((QueryWrapper<Agency>) queryWrapper).like("AREANAME", opeadd);
            }
        }
        List<Agency> list = agencyService.list(queryWrapper);

        Random rand = new Random();
        Agency agency= list.get( rand.nextInt(list.size()));

        ExtractingLog extractingLog = new ExtractingLog();
        extractingLog.setAgencyId(agency.getID());
        extractingLog.setAgeinsName(agency.getAGEINSNAME());
        extractingLog.setExtractTime(new Date());

        if(param.containsKey("projectname")) {
            String projectname  = param.get("projectname") != null?param.get("projectname").toString():"";
            if(projectname.length()!=0) {
                extractingLog.setProjectName( projectname);
            }
        }
        if(param.containsKey("projectperson") ) {
            String projectperson  = param.get("projectperson")!= null?param.get("projectperson").toString():"";
            if(projectperson.length()!=0) {
                extractingLog.setProjectPerson(projectperson);
            }
        }
        extractingLogService.save(extractingLog);

        return agency;
    }


    @PostMapping(value = "getExtractingLogList")
    public Page<AgencyExtract> GetExtractingLogList( @RequestBody PageParam pageParam){

       Wrapper<Agency> queryWrapper = new QueryWrapper<Agency>();


        Page<AgencyExtract> page = new Page<AgencyExtract>(pageParam.getCurrent(),pageParam.getPageSize());

        Page<AgencyExtract> agencyExtracts=  extractingLogService.selectExtractingLog(page);
        return agencyExtracts;

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
