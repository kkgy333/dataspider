package com.ssbc.nmg.dataspider.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.AgencyExtract;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;
import com.ssbc.nmg.dataspider.dao.mapper.ExtractingLogMapper;
import com.ssbc.nmg.dataspider.service.ExtractingLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractingLogServiceImpl extends ServiceImpl<ExtractingLogMapper, ExtractingLog> implements ExtractingLogService {


    @Override
    public  Page<AgencyExtract> selectExtractingLog(Page<AgencyExtract> page){


        return (Page<AgencyExtract>) page.setRecords(this.baseMapper.selectExtractingLog(page));
    }


}
