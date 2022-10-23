package com.search.search.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class User {
	
	private String id;

	@ExcelProperty("人员姓名")
	private String name = "";
	//出入
	@ExcelProperty("出入标识")
	private String inout = "";
	//地区
	@ExcelProperty("国籍/地区")
	private String area = "";
	//性别
	@ExcelProperty("性别")
	private String sex = "";
	//生日
	@ExcelProperty("出生日期")
	private String birth = "";
	//证件号码
	@ExcelProperty("证件类别")
	private String numType = "";
	//证件号码
	@ExcelProperty("证件号码")
	private String num = "";
	//出入时间
	@ExcelProperty(value="出入时间")
	private String time;
	//出入口岸
	@ExcelProperty("出入口岸")
	private String inoutArea = "";
	//交通工具
	@ExcelProperty("交通工具")
	private String traffic = "";
	//目的地
	@ExcelProperty("前往地/出发地")
	private String target = "";
	//导入时间
	@ExcelIgnore
	private Long importTime;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInout() {
		return inout;
	}
	public void setInout(String inout) {
		this.inout = inout;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getInoutArea() {
		return inoutArea;
	}
	public void setInoutArea(String inoutArea) {
		this.inoutArea = inoutArea;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getImportTime() {
		return importTime;
	}
	public void setImportTime(Long importTime) {
		this.importTime = importTime;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	
}
