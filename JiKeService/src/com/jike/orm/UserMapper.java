package com.jike.orm;

import java.util.Map;

public interface UserMapper {
	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public Map<String,Object> loginUser(Map<String,Object> map);
	/**
	 * 修改用户头像
	 * @param map
	 */
	public void findByIdUpdateHead(Map<String,Object> map); 
}
