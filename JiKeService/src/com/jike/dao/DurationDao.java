package com.jike.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jike.orm.DurationMapper;

/**
 * 课时信息
 * 
 * @author Administrator
 *
 */
@Repository
public class DurationDao {
	@Autowired
	private DurationMapper durationMapper;

	/**
	 * 求出课程的课时信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findCurriculumId(Integer id) {
		return durationMapper.findCurriculumId(id);
	}

	/**
	 * 根据课时Id求出课时信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map findDurationId(Integer id) {
		return durationMapper.findDurationId(id);
	}
}
