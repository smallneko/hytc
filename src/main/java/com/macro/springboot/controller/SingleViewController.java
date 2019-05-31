package com.macro.springboot.controller;

import com.macro.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SingleViewController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(){
		return "login";
	}

	// main页面返回方式
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(){

		return "main";
	}


}
