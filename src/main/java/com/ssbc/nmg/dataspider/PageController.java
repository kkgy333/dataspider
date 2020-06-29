package com.ssbc.nmg.dataspider;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {


    /**
     * 跳转到主页
     */
    @RequestMapping("")
    public String index() {
        return  "/index.html";
    }
}
