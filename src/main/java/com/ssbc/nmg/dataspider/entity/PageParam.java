package com.ssbc.nmg.dataspider.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class PageParam {
    private int current;
    private int pageSize;
    private Map<String,Object> extension;

    public PageParam(){
        current = 1;
        pageSize = 15;
        extension = new HashMap<String,Object>();
    }

}
