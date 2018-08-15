package com.ssbc.nmg.dataspider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.AgencyExtract;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtractingLogService extends IService<ExtractingLog> {

    Page<AgencyExtract> selectExtractingLog(Page<AgencyExtract> page) ;
}
