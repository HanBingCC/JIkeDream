package com.jike.orm;

import java.util.List;
import java.util.Map;

/**
 * 课时信息
 * @author Administrator
 *
 */
public interface DurationMapper {
	/**
	 * 求出课程的课时信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findCurriculumId(Integer id);
	/**
	 * 根据课时Id求出课时信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map findDurationId(Integer id);
}
