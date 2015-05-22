package com.jike.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jike.orm.UserMapper;
/**
 * 用户信息
 * @author Administrator
 *
 */
@Repository
public class UserDao {
	@Autowired
	private UserMapper userMapper;
	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public Map<String,Object> loginUser(Map<String,Object> map)
	{
		return userMapper.loginUser(map);
	}
	/**
	 * 修改用户头像
	 * @param map
	 */
	public void findByIdUpdateHead(Map<String,Object> map){
		 userMapper.findByIdUpdateHead(map);
	}
}
