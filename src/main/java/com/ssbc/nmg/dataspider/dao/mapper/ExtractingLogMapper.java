package com.ssbc.nmg.dataspider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssbc.nmg.dataspider.dao.AgencyExtract;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;

import java.util.List;

public interface ExtractingLogMapper extends BaseMapper<ExtractingLog> {
    List<AgencyExtract> selectExtractingLog(IPage<AgencyExtract>  page);
}
