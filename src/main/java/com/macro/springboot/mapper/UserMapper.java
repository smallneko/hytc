package com.macro.springboot.mapper;

import com.macro.springboot.entity.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 继承通用Mapper获取CURD方法
 */
public interface UserMapper extends Mapper<User> {
	@Select("select * from [User] where uuid = #{uuid}")
	User findUserByUuid(String uuid);

	@Select("select top ${pageSize} * from (select ROW_NUMBER() " +
			"over(Order by id) as rn, " +
			"* from [User])temTable " + //查询Address Sql
			"where rn > ${pageSize}*(${page}-1) and rn <= ${pageSize}*${page}")
	List<User> findAllUsers(int pageSize, int page);

	@Select("select count(a.id) from [User] a ")
	int countUser();

}
