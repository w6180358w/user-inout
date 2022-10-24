package com.search.search.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.fastjson2.JSON;
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
	
	DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
	
	

	public Page<User> query(Map<String,Object> params){
		Page<User> users = new Page<>();
		users.setData(this.userDao.queryLimit(params));
		users.setRecordsTotal(this.userDao.queryCount(params));
		users.setRecordsFiltered(users.getRecordsTotal());
		users.setLimit(Long.parseLong((String) params.get("pageSize")));
		users.setDraw(params.get("draw")+"");
		return users;
	}
	
	public Page<User> download(Map<String,Object> params){
		String names = (String) params.get("name");
		if(ObjectUtils.isEmpty(names) ||names.split(",").length<2) {
			throw new RuntimeException("请至少选择两位用户!");
		}
		String start = (String) params.get("startTime");
		String end = (String) params.get("endTime");
		Long dur = Long.parseLong(params.get("dur")+"")*60*1000;
		List<User> users = this.userDao.query(params);
		Map<String,List<User>> mapData = users.stream().collect(Collectors.groupingBy(User::getName));

		List<Map<String,String>> descs = new ArrayList<>();
		for (Entry<String, List<User>> data : mapData.entrySet()) {
			String name = data.getKey();
			// 组装概述
			Map<String,String> desc = new HashMap<>();
			desc.put("desc", name+"："+start+"至"+end+"共有"+data.getValue().size()+"条出入境数据。");
			descs.add(desc);
		}

		
		List<Pair<User,User>> pairs = new ArrayList<>();
		int s = 0;
		List<List<User>> datas = new ArrayList<>(mapData.values());
		for (List<User> list : datas) {
			
			for(int i=s+1,j=datas.size();i<j;i++) {
				List<User> sources = datas.get(s);
				List<User> targets = datas.get(i);
				
				for (User user : sources) {
					Long time = LocalDateTime.parse(user.getTime(),df).toInstant(ZoneOffset.of("+8")).toEpochMilli();
					for (User target : targets) {
						Long ttime = LocalDateTime.parse(target.getTime(),df).toInstant(ZoneOffset.of("+8")).toEpochMilli();
						// 出入标识  出入口岸  目的地  时间间隔
						if(user.getInout().equals(target.getInout())
								&& user.getInoutArea().equals(target.getInoutArea())
								&& user.getTarget().equals(target.getTarget())
								&& Math.abs(ttime-time)<=dur) {
							// 添加到对比集合
							pairs.add(Pair.create(user, target));
						}
					}
				}
			}
			s++;
		}
		// 导出文件
		writ(descs, pairs, mapData);
		
		System.out.println(JSON.toJSONString(pairs));
		return null;
	}
	
	private String writ(List<Map<String,String>> desc,List<Pair<User,User>> comp,Map<String,List<User>> datas) {
		String templateFileName = "C:\\Users\\HF-Tech\\Desktop\\同行记录核对\\template.xlsx";

		String fileName = "C:\\Users\\HF-Tech\\Desktop\\同行记录核对\\aa.xlsx";
		
		// 方案1
		try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
		    WriteSheet writeSheet = EasyExcel.writerSheet().build();
		    FillConfig fillConfig = FillConfig.builder().build();
		    // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
		    
		    // 概述
		    excelWriter.fill(desc, fillConfig, writeSheet);
		    
		    // 对比表格
		    // 实际上可以一直变
		    writeSheet = EasyExcel.writerSheet("总结模板").head(User.class).build();
		    // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
		    excelWriter.write(comp, writeSheet);
		    
		    // 用户表格
		    int i=2;
		    for (Entry<String, List<User>> users:datas.entrySet()) {
		        // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class
		        // 实际上可以一直变
		        writeSheet = EasyExcel.writerSheet(i++, users.getKey()).head(User.class).build();
		        // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
		        excelWriter.write(users.getValue(), writeSheet);
		    }
		    
		}
		return fileName;
	}
}
