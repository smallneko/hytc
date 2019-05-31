package com.macro.springboot.service.impl;

import com.macro.springboot.entity.Address;
import com.macro.springboot.mapper.AddressMapper;
import com.macro.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

	@SuppressWarnings("all")
	@Autowired
	AddressMapper addressMapper;

	@Override
	public Address findAddressByUser(int userId){
		return addressMapper.findAddressByUser(userId);
	}

	@Override
	public List<Address> queryAddressByArray(int currPage, int pageSize){

		int count = addressMapper.countAddress();
		pageSize = pageSize > count ? count : pageSize;

		//查询全部数据
		List<Address> addressList = addressMapper.queryAddressByArray();
		//从第几条数据开始
		int firstIndex = (currPage - 1) * pageSize;
		//到第几条结束
		int lastIndex = currPage * pageSize;
		lastIndex = lastIndex > count ? count : lastIndex;

		return addressList.subList(firstIndex, lastIndex); //直接在list中获取
	}

	@Override
	public int countAddress(){
		return addressMapper.countAddress();
	}

	@Override
	public List<Address> findAllAddress(int pageSize, int page){
		return addressMapper.findAllAddress(pageSize,page);
	}

}
