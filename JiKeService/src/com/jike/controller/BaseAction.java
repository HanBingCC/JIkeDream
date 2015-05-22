package com.jike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jike.common.MD5;
import com.jike.service.AllService;

@Controller
public class BaseAction {
	@Autowired
	private AllService allService;

	public AllService getAllService() {
		return allService;
	}
	private MD5 md5=new MD5();

	public MD5 getMd5() {
		return md5;
	}
	
}
