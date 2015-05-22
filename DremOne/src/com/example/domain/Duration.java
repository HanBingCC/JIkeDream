package com.example.domain;

import java.io.Serializable;

/**
 * 课时实体
 * 
 * @author Administrator
 *
 */
public class Duration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -815535440066850814L;
	private Integer id; //课时ID
	private Integer curriculum_id;//课程Id
	private String name;//课时名称
	private String url;//课时地址
	private long timeSpan;//课时时长
	private Integer useFlag;//是否是VIp可见<0为普通，1为vip>
	private String briefIntroduction; //简介
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the curriculum_id
	 */
	public Integer getCurriculum_id() {
		return curriculum_id;
	}
	/**
	 * @param curriculum_id the curriculum_id to set
	 */
	public void setCurriculum_id(Integer curriculum_id) {
		this.curriculum_id = curriculum_id;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the timeSpan
	 */
	public long getTimeSpan() {
		return timeSpan;
	}
	/**
	 * @param timeSpan the timeSpan to set
	 */
	public void setTimeSpan(long timeSpan) {
		this.timeSpan = timeSpan;
	}

	/**
	 * @return the useFlag
	 */
	public Integer getUseFlag() {
		return useFlag;
	}
	/**
	 * @param useFlag the useFlag to set
	 */
	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}
	/**
	 * @return the briefIntroduction
	 */
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	/**
	 * @param briefIntroduction the briefIntroduction to set
	 */
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public Duration() {
		super();
	}
	public Duration(Integer id, Integer curriculum_id, String name, String url,
			long timeSpan, Integer useFlag, String briefIntroduction) {
		super();
		this.id = id;
		this.curriculum_id = curriculum_id;
		this.name = name;
		this.url = url;
		this.timeSpan = timeSpan;
		this.useFlag = useFlag;
		this.briefIntroduction = briefIntroduction;
	}
	
}
