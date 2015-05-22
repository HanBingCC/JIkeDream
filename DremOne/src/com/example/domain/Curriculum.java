package com.example.domain;

import java.io.Serializable;


/**
 * 课程实体
 * 
 * @author Administrator
 *
 */
public class Curriculum  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6709416980283370927L;
	private Integer id; // Id
	private String title; // 标题
	private Integer durationCount; // 课时总数量
	private String url; // 图片uri
	private String briefIntroduction; // 简介
	private Integer useFlag; // 是否是vip教程<0为普通用户,1为vip>

	public Curriculum() {
		super();
	}

	public Curriculum(Integer id, String title, Integer durationCount,
			String url, String briefIntroduction, Integer useFlag) {
		super();
		this.id = id;
		this.title = title;
		this.durationCount = durationCount;
		this.url = url;
		this.briefIntroduction = briefIntroduction;
		this.useFlag = useFlag;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the durationCount
	 */
	public Integer getDurationCount() {
		return durationCount;
	}

	/**
	 * @param durationCount
	 *            the durationCount to set
	 */
	public void setDurationCount(Integer durationCount) {
		this.durationCount = durationCount;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the briefIntroduction
	 */
	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	/**
	 * @param briefIntroduction
	 *            the briefIntroduction to set
	 */
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	/**
	 * @return the useFlag
	 */
	public Integer getUseFlag() {
		return useFlag;
	}

	/**
	 * @param useFlag
	 *            the useFlag to set
	 */
	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}
}
