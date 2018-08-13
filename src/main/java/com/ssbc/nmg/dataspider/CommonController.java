package com.ssbc.nmg.dataspider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

@RestController
@RequestMapping("api")
public class CommonController {

    @Autowired
    private NmgpGovCnAgencyProcessor nmgpGovCnAgencyProcessor;

    @Autowired
    private AgencyDaoPipeline agencyDaoPipeline;

    @GetMapping(value = "start")
    public void StartSpider() {
        Spider.create(nmgpGovCnAgencyProcessor).addUrl(String.format(nmgpGovCnAgencyProcessor.baseUrl,nmgpGovCnAgencyProcessor.getPageNum()))
                .addPipeline(agencyDaoPipeline)
                .thread(5).run();
    }

}
