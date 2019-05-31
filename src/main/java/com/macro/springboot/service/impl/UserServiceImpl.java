package com.macro.springboot.service.impl;

import com.macro.springboot.base.impl.BaseServiceImpl;
import com.macro.springboot.entity.User;
import com.macro.springboot.mapper.UserMapper;
import com.macro.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	@SuppressWarnings("all")
	@Autowired
	UserMapper userMapper;

	@Override
	public User findUserByUuid(String uuid) {
		return userMapper.findUserByUuid(uuid);
	}

	@Override
	public List<User> findAllUsers(int pageSize, int page){
		return userMapper.findAllUsers(pageSize,page);
	}

	@Override
	public int countUser(){
		return userMapper.countUser();
	}
}
