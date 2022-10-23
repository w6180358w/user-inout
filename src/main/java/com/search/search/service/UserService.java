package com.search.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.search.bean.Page;
import com.search.search.dao.UserDao;
import com.search.search.entity.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	public void addUser(List<User> user) {
		this.userDao.addUser(user);
	}
	

	public Page<User> query(Map<String,Object> params){
		Page<User> users = new Page<>();
		users.setData(this.userDao.queryLimit(params));
		users.setRecordsTotal(this.userDao.queryCount(params));
		users.setRecordsFiltered(users.getRecordsTotal());
		users.setLimit(Long.parseLong((String) params.get("pageSize")));
		users.setDraw(params.get("draw")+"");
		return users;
	}
}
