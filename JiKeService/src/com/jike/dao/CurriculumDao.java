package com.jike.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jike.orm.CurriculumMapper;

/**
 * 课程信息
 * @author Administrator
 *
 */
@Repository
public class CurriculumDao {
	@Autowired
	private CurriculumMapper curriculumMapper;
	/**
	 * 随机取得5条课程信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findRandomCurriculum(){
		return curriculumMapper.findRandomCurriculum();
	}
}
