package com.jike.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 课程信息
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("curriculum.html")
public class CurriculumAction extends BaseAction {
	/**
	 * 随机取得5条课程信息
	 * @return 课程信息  201为空数据
	 */
	@RequestMapping(params = "mt=findRandomCurriculum", method = RequestMethod.POST)
	@ResponseBody
	public String findRandomCurriculum() {
		@SuppressWarnings("rawtypes")
		List<Map> lists = getAllService().getCurriculumService()
				.findRandomCurriculum();
		if (lists != null && lists.size() > 0) {
			return JSONArray.fromObject(lists).toString();
		} else {
			return "201";
		}
	}
}
