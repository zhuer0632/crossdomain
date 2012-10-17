package com.me.control;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.me.biz.PriceSysBiz;
import com.me.http.TextHttpResponse;
import com.me.ut.StringUT;

@Controller
@RequestMapping("PriceSys")
public class PriceSys {

    @Autowired
    private PriceSysBiz priceSysBiz;

    @RequestMapping("login")
    public ModelAndView login(@RequestParam("nickname") String nickname,
	    @RequestParam("password") String password,
	    HttpServletResponse response) {
	TextHttpResponse res = new TextHttpResponse();
	ModelAndView mod = new ModelAndView();
	mod.setViewName("login");
	try {
	    res = priceSysBiz.login2(nickname, password);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	Hashtable<String, String> heads = res.getHeads();
	Enumeration<String> keys = heads.keys();
	
	while (keys.hasMoreElements()) {
	    String key = (String) keys.nextElement();
	    String value = heads.get(key);
	     response.setHeader(key, value);
	}
	
	String cookieStr = heads.get("Set-Cookie");
	String[] arr = StringUtils.split(cookieStr, "; ");
	for (int i = 0; i < arr.length; i++) {
	    System.out.println(arr[i]);
	    if (arr[i].contains("_gcj_session")) {
		cookieStr = StringUtils.split(arr[i], "=")[1];
		System.out.println(StringUtils.split(arr[i], "=")[1]);
		mod.addObject("cookie", StringUtils.split(arr[i], "=")[1]);
		break;
	    }
	}

//	Cookie cookie = new Cookie("_gcj_session", cookieStr);
//	cookie.setDomain("localhost");
//	response.addCookie(cookie);

	// String cookie=heads.get("Set-Cookie");
	// _gcj_session=BAh7CSIQX2NzcmZfdG9rZW4iMUN1bzZnSVRtb1lyYUVUclVPMENOcHBKZXFEZXJlaVVQQjRiQk9hVmhvWjA9IhN1c2VyX3JldHVybl90byIdL2luZm9fcHJpY2VzLzIyMTQ1L3RyZW5kIhl3YXJkZW4udXNlci51c2VyLmtleVsIIglVc2VyWwZpA8nmAzAiD3Nlc3Npb25faWQiJTY4Njc3N2JlMzNhMDA3N2QxMGI5NmNjMDc3MzdkMTc4--c59501026fc3ea28d15c7c0d676a988ed03c6155;
	// path=/; domain=.gldjc.com; HttpOnly
	
	return mod;
    }

    // 测试215的登录可不可以单点登录

    // 登录接口中可能没有返回cookie,没有[Set-Cookie]头。因为第一次访问的页面时其他页面。已经生成好了。
    // 这个时候cookie就存在于request中的[Cookie]头中。
    @RequestMapping("login_xj")
    public ModelAndView login_xj(@RequestParam("nickname") String nickname,
	    @RequestParam("password") String password,
	    HttpServletRequest request, HttpServletResponse response) {
	ModelAndView mod = new ModelAndView();
	// 跳到当前页面
	mod.setViewName("login");

//	if (!StringUT.isEmpty(request.getCookies())) {
//	   
//	    for (int i = 0; i < request.getCookies().length; i++) {
//		response.addCookie(request.getCookies()[i]);
//	    }
//	    return mod;
//	}
	
	TextHttpResponse res = new TextHttpResponse();
	try {
	    res = priceSysBiz.login22(request, nickname, password);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	Hashtable<String, String> heads = res.getHeads();
	Enumeration<String> keys = heads.keys();

	//设置cookie
	while (keys.hasMoreElements()) {
	    String key = (String) keys.nextElement();
	    String value = heads.get(key);
	    response.setHeader(key, value);
	}

	// String cookieStr = heads.get("Set-Cookie");
	// String[] arr = StringUtils.split(cookieStr, "; ");
	// for (int i = 0; i < arr.length; i++) {
	// 
	// System.out.println(arr[i]);
	//	    
	// if (arr[i].contains("_gcj_session")) {
	// cookieStr = StringUtils.split(arr[i], "=")[1];
	// System.out.println(StringUtils.split(arr[i], "=")[1]);
	// break;
	// }
	// }
	// Cookie cookie = new Cookie("_gcj_session", cookieStr);
	// cookie.setDomain("localhost");
	// response.addCookie(cookie);

	// String cookie=heads.get("Set-Cookie");
	// _gcj_session=BAh7CSIQX2NzcmZfdG9rZW4iMUN1bzZnSVRtb1lyYUVUclVPMENOcHBKZXFEZXJlaVVQQjRiQk9hVmhvWjA9IhN1c2VyX3JldHVybl90byIdL2luZm9fcHJpY2VzLzIyMTQ1L3RyZW5kIhl3YXJkZW4udXNlci51c2VyLmtleVsIIglVc2VyWwZpA8nmAzAiD3Nlc3Npb25faWQiJTY4Njc3N2JlMzNhMDA3N2QxMGI5NmNjMDc3MzdkMTc4--c59501026fc3ea28d15c7c0d676a988ed03c6155;
	// path=/; domain=.gldjc.com; HttpOnly
	return mod;
    }

    
    //测试本地的跨域访问
    
    
    
    
    
    
    
    
    
    
}
