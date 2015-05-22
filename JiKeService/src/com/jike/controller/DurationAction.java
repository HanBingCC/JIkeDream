package com.jike.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 课时信息
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("duration.html")
public class DurationAction extends BaseAction {
	/**
	 * 根据课程Id查询课时信息
	 * 
	 * @param id 课程Id
	 * @return 课时信息 201为空数据
	 */
	@RequestMapping(params = "mt=findCurriculumId", method = RequestMethod.POST)
	@ResponseBody
	public String findCurriculumId(String id) {
		@SuppressWarnings("rawtypes")
		List<Map> lists = getAllService().getDurationService()
				.findCurriculumId(Integer.valueOf(id));
		if (lists != null && lists.size() > 0) {
			return JSONArray.fromObject(lists).toString();
		} else {
			return "201";
		}
	}

	/**
	 * 根据课时Id查询课时系想你
	 * 
	 * @param id
	 * @return 课时信息 201为空数据
	 */
	@RequestMapping(params = "mt=findDurationId", method = RequestMethod.POST)
	@ResponseBody
	public String findDurationId(String id) {
		@SuppressWarnings("rawtypes")
		Map map = getAllService().getDurationService().findDurationId(
				Integer.valueOf(id));
		if (map != null) {
			return JSONObject.fromObject(map).toString();
		} else {
			return "201";
		}
	}
}
