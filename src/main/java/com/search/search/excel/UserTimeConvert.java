package com.search.search.excel;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class UserTimeConvert implements Converter<Long>{

	@Override
	public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) throws Exception {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
		LocalDateTime time = LocalDateTime.parse(cellData.getStringValue(),df);
		return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

}
