package com.ssbc.nmg.dataspider.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssbc.nmg.dataspider.dao.Agency;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface AgencyService extends IService<Agency> {

    Page<Agency> selectListPage(int current, int size,@Param("ew") Wrapper<Agency> wrapper) ;
}
