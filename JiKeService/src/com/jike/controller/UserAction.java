package com.jike.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 用户信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("index.html")
public class UserAction extends BaseAction {
	/**
	 * 用户登录
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(params = "mt=login", method = RequestMethod.POST)
	@ResponseBody
	public String loginUser(String phone, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("password", getMd5().transFormMD5(password));
		map = getAllService().getUserService().loginUser(map);
		if (map != null) {
			return JSONObject.fromObject(map).toString();
		}
		return "201";
	}

	/**
	 * 修改用户头像的Id
	 * 
	 * @param id
	 *            用户Id
	 * @param filename
	 *            文件名称
	 */
	@RequestMapping(params = "mt=updatehead", method = RequestMethod.POST)
	@ResponseBody
	public void findByIdUpdateHead(String id, String filename) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image_url", filename);
		map.put("id", id);
		getAllService().getUserService().findByIdUpdateHead(map);
	}

	/**
	 * 上传头像方法
	 *
	 * @param file
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(params = "mt=uploadhead", method = RequestMethod.POST)
	@ResponseBody
	public void uploadhead(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws IllegalStateException,
			IOException {
		String fileName = file.getOriginalFilename();
		String movePath = request.getSession().getServletContext()
				.getRealPath("/fileImage/head/");
		File fileMove = new File(movePath, fileName);
		file.transferTo(fileMove);
	}
}
