package com.macro.springboot.mapper;

import com.macro.springboot.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressMapper {

	@Select("select * from [Address] where user_id = #{userId}")
	Address findAddressByUser(int userId);

	@Select("select count(a.id) from [Address] a ")
	int countAddress();

	@Select("select * from [Address]")
	List<Address> queryAddressByArray();

	@Select("select top ${pageSize} * from (select ROW_NUMBER() " +
			"over(Order by id) as rn, " +
			"* from [Address])temTable " + //查询Address Sql
			"where rn > ${pageSize}*(${page}-1) and rn <= ${pageSize}*${page}")
	List<Address> findAllAddress(int pageSize, int page);

}
