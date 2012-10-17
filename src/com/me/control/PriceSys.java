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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.me.biz.PriceSysBiz;
import com.me.http.Dump;
import com.me.http.TextHttpResponse;
import com.me.ut.StringUT;


@Controller
@RequestMapping("PriceSys")
public class PriceSys
{

    @Autowired
    private PriceSysBiz priceSysBiz;


    @RequestMapping("login")
    public ModelAndView login(@RequestParam("nickname") String nickname,
                              @RequestParam("password") String password,
                              HttpServletResponse response)
    {
        TextHttpResponse res = new TextHttpResponse();
        ModelAndView mod = new ModelAndView();
        mod.setViewName("login");
        try
        {
            res = priceSysBiz.login2(nickname,
                                     password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Hashtable<String, String> heads = res.getHeads();
        Enumeration<String> keys = heads.keys();

        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            String value = heads.get(key);
            response.setHeader(key,
                               value);
        }

        String cookieStr = heads.get("Set-Cookie");
        String[] arr = StringUtils.split(cookieStr,
                                         "; ");
        for (int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
            if (arr[i].contains("_gcj_session"))
            {
                cookieStr = StringUtils.split(arr[i],
                                              "=")[1];
                System.out.println(StringUtils.split(arr[i],
                                                     "=")[1]);
                mod.addObject("cookie",
                              StringUtils.split(arr[i],
                                                "=")[1]);
                break;
            }
        }
      
        return mod;
    }


    
    //同域名下不同项目可以实现单点登录   例如：127.0.0.1:8080/v和127.0.0.1:8080/xj
    //如果是仅仅二级域名不一样话，可以像同域名一样，cookie自动传递。并不需要在前段或者对方服务器设置任何代码。
    // a.bbb.com:8080   b.bbb.com:8080  就属于仅仅二级域名不一样的情况。
    //需要做如下设置
    // Cookie  cookie=new Cookie("JSESSIONID", cookieStr);
    // cookie.setDomain("bbb.com");
    // cookie.setPath("/");
    // response.setHeader("Set-Cookie", "");
    // response.addCookie(cookie);
    @RequestMapping("login_xj")
    public ModelAndView login_xj(@RequestParam("nickname") String nickname,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        ModelAndView mod = new ModelAndView();
        // 跳到当前页面
        mod.setViewName("login");
        
        TextHttpResponse res = new TextHttpResponse();
        try
        {
            res = priceSysBiz.login22(request,
                                      nickname,
                                      password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Hashtable<String, String> heads = res.getHeads();
        Enumeration<String> keys = heads.keys();

        // 设置cookie
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            String value = heads.get(key);
//            response.setHeader(key,
//                               value);
        }
        
        String cookieStr = heads.get("Set-Cookie");
        String[] arr = StringUtils.split(cookieStr,
                                         "; ");
        if(StringUT.isEmpty(arr))
        {
            Dump.dump("已经登录了，客户端已经存在了cookie");
//            mod.addObject("hascookie", "hasexits");
        }
        else{
            for (int i = 0; i < arr.length; i++)
            {
                System.out.println(arr[i]);
                //Set-Cookie:JSESSIONID=706AD796C40DB743026FAD9963E7EC05; Path=/xj
                if (arr[i].contains("JSESSIONID"))
                {
                    cookieStr = StringUtils.split(arr[i],
                                                  "=")[1];
                    System.out.println(cookieStr);
                    mod.addObject("cookie",cookieStr);
                    break;
                }
            }
        }
        
      Cookie  cookie=new Cookie("JSESSIONID", cookieStr);
      cookie.setDomain("bbb.com");
      cookie.setPath("/");
      response.setHeader("Set-Cookie", "");
      response.addCookie(cookie);
       

        return mod;
    }

    // 127.0.0.1 登录 192.168.1.215 ,可以跨域登录
    // 其他工作
    // 请求该方法的页面上加上
//    $(document).ready(function(){
//        <#if  cookie??>
//        
//         var _frm = document.createElement("iframe");
//         _frm.style.display="none";
//         _frm.src="http://192.168.1.215:8080/xj/l/checkLogon.do?cookie=${cookie}";
//         document.body.appendChild(_frm);       
//         
//        </#if>
//        });
    
    //对应的 215 上的名为l的control上加上如下的方法  
//    @RequestMapping("checkLogon")
//    public @ResponseBody
//    String checkLogon(@RequestParam("cookie") String cookie,HttpServletResponse response)
//    {
//        String out = "";
//        System.out.println("cookie已经存在"+cookie);
//        response.setHeader("Set-Cookie", "JSESSIONID="+cookie+"; Path=/xj");
//        
//        out="已经验证通过";
//        return out;
//    }
    
    @RequestMapping("login_xj_2")
    public ModelAndView login_xj_2(@RequestParam("nickname") String nickname,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {
        
        ModelAndView mod = new ModelAndView();
        // 跳到当前页面
        mod.setViewName("login");
        
        TextHttpResponse res = new TextHttpResponse();
        try
        {
            res = priceSysBiz.login222(request,
                                      nickname,
                                      password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Hashtable<String, String> heads = res.getHeads();
        Enumeration<String> keys = heads.keys();

        // 设置cookie
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            String value = heads.get(key);
            response.setHeader(key,
                               value);
        }
        
        String cookieStr = heads.get("Set-Cookie");
        String[] arr = StringUtils.split(cookieStr,
                                         "; ");
        if(StringUT.isEmpty(arr))
        {
            Dump.dump("已经登录了，客户端已经存在了cookie");
            mod.addObject("hascookie", "hasexits");
        }
        else{
            for (int i = 0; i < arr.length; i++)
            {
                System.out.println(arr[i]);
                //Set-Cookie:JSESSIONID=706AD796C40DB743026FAD9963E7EC05; Path=/xj
                if (arr[i].contains("JSESSIONID"))
                {
                    cookieStr = StringUtils.split(arr[i],
                                                  "=")[1];
                    System.out.println(cookieStr);
                    mod.addObject("cookie",cookieStr);
                    break;
                }
            }
        }
        
        //想当然的通过下面的方法是不能够欺骗浏览器的，因为浏览器的请求地址根本就是127.0.0.1。即便你强调该cookie来自192.168.1.215，也会被无视。
//        Cookie  cookie=new Cookie("JSESSIONID", cookieStr);
//        cookie.setDomain("192.168.1.215");
//        cookie.setPath("/xj");
//        response.setHeader("Set-Cookie", "");
//        response.addCookie(cookie);
        
        return mod;
    }
    
    
}
