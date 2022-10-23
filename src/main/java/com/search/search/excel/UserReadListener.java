package com.search.search.excel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.search.search.entity.User;
import com.search.search.service.UserService;

public class UserReadListener implements ReadListener<User>{
	
	public UserReadListener(UserService userService) {
		this.userService = userService;
		this.now = System.currentTimeMillis();
	}
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<User> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    
    UserService userService;
    
    Long now;
    
	@Override
	public void doAfterAllAnalysed(AnalysisContext arg0) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        logger.info("所有数据解析完成！");
	}

	@Override
	public void invoke(User data, AnalysisContext arg1) {
		data.setImportTime(now);
		data.setId(System.currentTimeMillis()+""+cachedDataList.size());
		logger.info("解析到一条数据:{}", JSON.toJSONString(data));
		cachedDataList.add(data);
		if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
	}

	private void saveData() {
		logger.info("{}条数据，开始存储数据库！", cachedDataList.size());
		userService.addUser(cachedDataList);
        logger.info("存储数据库成功！");
	}

}
