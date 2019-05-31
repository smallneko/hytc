package com.macro.springboot.controller;

import com.macro.springboot.entity.Address;
import com.macro.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address/*")
public class AddressController {

	@Autowired
	AddressService addressService;

	@RequestMapping(value = "queryAddressByArray")
	@ResponseBody
	public Map queryAddressByArray(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		// page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
		String pageNo = request.getParameter("page");
		int page = Integer.parseInt(pageNo);

		// rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
		String pageSize = request.getParameter("rows");
		int size = Integer.parseInt(pageSize);

		// 调用service方法，获取人员记录
		List<Address> list = addressService.findAllAddress(size,page);
		// 获取总记录数
		long total = addressService.countAddress();

		// 定义map
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		// total 存放总记录数
		jsonMap.put("total", total);

		// rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
		jsonMap.put("rows", list);
		return jsonMap;
	}

}
