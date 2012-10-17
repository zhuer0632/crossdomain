package com.me.biz;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.springframework.stereotype.Controller;

import com.me.http.CharSets;
import com.me.http.Dump;
import com.me.http.HttpUT;
import com.me.http.TextHttpResponse;
import com.me.ut.StringUT;

@Controller
public class PriceSysBiz {

    public TextHttpResponse login(String nickname, String password) {

	Map<String, String> params = new Hashtable<String, String>();
	params.put("nickname", StringUtils.trim(nickname));
	params.put("password", StringUtils.trim(password));

	String out = HttpUT.post("http://xj.gldjc.com/api/users/login", params,
		CharSets.UTF_8);

	Dump.dump(out);

	Dump.dump("正在查看第二个页面");

	String nexturl = "http://xj.gldjc.com/info_prices/22145/trend";
	TextHttpResponse res = HttpUT.get_response(nexturl, null,
		CharSets.UTF_8);
	Dump.dump(res.getContent());
	Dump.dump(res.getHeads());

	try {
	    FileUtils.writeStringToFile(new File("c:\\content.html"), res
		    .getContent(), CharSets.UTF_8);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return res;
    }

    /**
     * 
     * 使用实际的网站上的接口进行测试
     * 
     * 登录接口相关内容如下
     * 
     * authenticity_token ZhYo8KO700MhYyGLrNJq/T+jJu2y2JaKDcdeV4ot2Nw= commit 登录
     * loginname zhushaolong321 lt password zhushaolong321 service
     * http://xj.gldjc
     * .com/user/auth/cas/callback?url=http%3A%2F%2Fxj.gldjc.com%2F utf8 ✓
     * 
     * 
     * 请求的地址是：http://sso.glodon.com/sessions/login
     * 
     * 
     * @param nickname
     * @param password
     */
    public TextHttpResponse login2(String nickname, String password) {

	Map<String, String> params = new Hashtable<String, String>();
	params.put("authenticity_token",
		"ZhYo8KO700MhYyGLrNJq/T+jJu2y2JaKDcdeV4ot2Nw=");
	params.put("commit", "登录");
	params.put("loginname", "zhushaolong321");
	params.put("lt", "");
	params.put("password", "zhushaolong321");
	params
		.put("service",
			"http://xj.gldjc.com/user/auth/cas/callback?url=http%3A%2F%2Fxj.gldjc.com%2F");
	params.put("utf8", "✓");

	String out = HttpUT.post("http://sso.glodon.com/sessions/login",
		params, CharSets.UTF_8);
	Dump.dump(out);
	
	Dump.dump("正在查看第二个页面");

	String nexturl = "http://xj.gldjc.com/info_prices/22145/trend";
	String content = HttpUT.get(nexturl);
	Dump.dump(content);

	nexturl = "http://xj.gldjc.com/info_prices/22145/trend";
	TextHttpResponse res = HttpUT.get_response(nexturl, null,
		CharSets.UTF_8);
	
	Dump.dump(res.getHeads());
	
	try {
	    FileUtils.writeStringToFile(new File("c:\\content.html"), res
		    .getContent(), CharSets.UTF_8);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return res;
    }

    /**
     * 
     * 测试127.0.0.1下的单点登录
     * @param request 
     * 
     */
    public TextHttpResponse login22(HttpServletRequest request, String nickname, String password) {
	
	
	Map<String, String> params = new Hashtable<String, String>();
	params.put("logon_name", StringUtils.trim(nickname));
	params.put("logon_password", StringUtils.trim(password));

	TextHttpResponse out = HttpUT.post_response("http://192.168.1.215:8080/xj/l/login.do", params,
		CharSets.UTF_8);
	String getsessionId=out.getHeads(out);// Set-Cookie	JSESSIONID=6F5A0306C7E12AA16815DF2014143A50; Path=/xj
	
//	Dump.dump(out);
	
	Dump.dump("正在查看第二个页面");
	String nexturl = "http://192.168.1.215:8080/xj/adminCom/listComMgrtoAudit.do";
	TextHttpResponse res = HttpUT.get_response(nexturl, null,
		CharSets.UTF_8);
//	Dump.dump(res.getContent());
	Dump.dump(res.getHeads());
	
	try {
	    FileUtils.writeStringToFile(new File("c:\\content.html"), res
		    .getContent(), CharSets.UTF_8);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return out;
    }

}
