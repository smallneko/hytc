package com.macro.springboot.service;

import com.macro.springboot.entity.Address;

import java.util.List;

public interface AddressService {

	Address findAddressByUser(int userId);

	List<Address> queryAddressByArray(int currPage, int pageSize);

	int countAddress();

	List<Address> findAllAddress(int pageSize, int page);


}
