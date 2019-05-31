package com.macro.springboot.service;

import com.macro.springboot.base.BaseService;
import com.macro.springboot.entity.User;

import java.util.List;

public interface UserService extends BaseService<User> {
	User findUserByUuid(String uuid);

	List<User> findAllUsers(int pageSize, int page);

	int countUser();

}
