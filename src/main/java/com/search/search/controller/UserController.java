package com.search.search.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.search.search.bean.Page;
import com.search.search.entity.User;
import com.search.search.excel.UserReadListener;
import com.search.search.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("query")
	@ResponseBody
	public Page<User> getUsers(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = new HashMap<>();
		params.put("name", request.getParameter("name"));
		params.put("inout", request.getParameter("inout"));
		params.put("inoutArea", request.getParameter("inoutArea"));
		params.put("target", request.getParameter("target"));
		params.put("startTime", request.getParameter("startTime"));
		params.put("endTime", request.getParameter("endTime"));
		params.put("currentPage", request.getParameter("start"));
		params.put("pageSize", request.getParameter("length"));
		params.put("draw", request.getParameter("draw"));
		return this.userService.query(params);
	}
	
	@RequestMapping("add")
	public Page<User> add(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> params = new HashMap<>();
		return this.userService.query(params);
	}
	
	@RequestMapping("import")
	@ResponseBody
	public void upload(HttpServletRequest request,MultipartFile file) throws IOException{
		EasyExcel.read(file.getInputStream(), User.class, new UserReadListener(userService)).sheet().doRead();
	}
	
	@RequestMapping("export")
	@ResponseBody
	public void download(HttpServletRequest request,MultipartFile file) throws IOException{
		EasyExcel.read(file.getInputStream(), User.class, new UserReadListener(userService)).sheet().doRead();
	}

}
