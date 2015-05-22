package com.jike.orm;

import java.util.List;
import java.util.Map;

/**
 * 课程信息
 * @author Administrator
 *
 */
public interface CurriculumMapper {
	/**
	 * 随机取得5条课程信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findRandomCurriculum();
}
