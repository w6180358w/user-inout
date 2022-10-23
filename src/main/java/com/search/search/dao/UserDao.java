package com.search.search.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.search.search.entity.User;

@Mapper
@Repository
public interface UserDao {
	
	List<User> queryLimit(Map<String,Object> params);
	
	Long queryCount(Map<String,Object> params);
	 
    Integer addUser(List<User> user);

    Integer deleteUser(List<String> ids);
}
