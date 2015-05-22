package com.jike.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jike.dao.AllDao;

@Service
public class CurriculumService {
	@Autowired
	private AllDao allDao;

	/**
	 * 随机取得5条课程信息
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findRandomCurriculum() {
		return allDao.getCurriculumDao().findRandomCurriculum();
	}
}
