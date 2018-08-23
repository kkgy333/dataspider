package com.ssbc.nmg.dataspider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssbc.nmg.dataspider.dao.ExtractingLog;
import com.ssbc.nmg.dataspider.dao.User;
import com.ssbc.nmg.dataspider.dao.mapper.ExtractingLogMapper;
import com.ssbc.nmg.dataspider.dao.mapper.UserMapper;
import com.ssbc.nmg.dataspider.service.ExtractingLogService;
import com.ssbc.nmg.dataspider.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
