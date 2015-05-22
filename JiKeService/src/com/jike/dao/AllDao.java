package com.jike.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllDao {
	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}
	@Autowired
	private CurriculumDao curriculumDao;
	/**
	 * @return the curriculumDao
	 */
	public CurriculumDao getCurriculumDao() {
		return curriculumDao;
	}
	@Autowired
	private DurationDao durationDao;

	/**
	 * @return the durationDao
	 */
	public DurationDao getDurationDao() {
		return durationDao;
	}
	
}
