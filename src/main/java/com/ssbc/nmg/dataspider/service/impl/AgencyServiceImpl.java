package com.ssbc.nmg.dataspider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssbc.nmg.dataspider.dao.Agency;
import com.ssbc.nmg.dataspider.dao.mapper.AgencyMapper;
import com.ssbc.nmg.dataspider.service.AgencyService;
import org.springframework.stereotype.Service;

@Service
public class AgencyServiceImpl extends ServiceImpl<AgencyMapper, Agency> implements AgencyService {

}
