package com.ssbc.nmg.dataspider.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.mapper.AgencyMapper;
import com.ssbc.nmg.dataspider.service.AgencyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class AgencyServiceImpl extends ServiceImpl<AgencyMapper, Agency> implements AgencyService {



    public Page<Agency> selectListPage(int current,int size,@Param("ew") Wrapper<Agency> wrapper) {

        Page<Agency> page = new Page<Agency>(current, size);
        Page<Agency> roleDOList = (Page<Agency>) this.baseMapper.selectPage(page,wrapper);
        return roleDOList;
    }
}
