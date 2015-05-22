package com.jike.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jike.dao.AllDao;

@Service
public class UserService {
	@Autowired
	private AllDao allDao;

	public Map<String, Object> loginUser(Map<String, Object> map) {
		return allDao.getUserDao().loginUser(map);
	}

	/**
	 * 修改用户头像
	 * 
	 * @param map
	 */
	public void findByIdUpdateHead(Map<String, Object> map) {
		 allDao.getUserDao().findByIdUpdateHead(map);
	}
}
