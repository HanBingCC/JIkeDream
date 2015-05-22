package com.jike.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jike.dao.AllDao;

@Service
public class DurationService {
	@Autowired
	private AllDao allDao;

	/**
	 * 求出课程的课时信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findCurriculumId(Integer id) {
		return allDao.getDurationDao().findCurriculumId(id);
	}

	/**
	 * 根据课时Id求出课时信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map findDurationId(Integer id) {
		return allDao.getDurationDao().findDurationId(id);
	}
}
