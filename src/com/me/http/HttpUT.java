package com.me.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 对httpComponents的简单封装
 * 
 * @author ZHU
 */
public class HttpUT {

    public static String getCharSet(HttpEntity entity) {
	String code = CharSets.UTF_8;
	ContentType contentType = ContentType.getOrDefault(entity);
	if (contentType != null && contentType.getCharset() != null
		&& StringUtils.isNotEmpty(contentType.getCharset().name())) {
	    code = contentType.getCharset().name();
	}
	return code;
    }

    /**
     * 简单的get请求返回网页内容
     * <hr>
     * <br>
     * outcode:优先用服务器端返回的response的编码，如果没返回用UTF-8
     * 
     * @return
     */
    public static String get(String url) {
	String out = "";
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	try {
	    // (1) 创建HttpGet实例
	    HttpGet get = new HttpGet(url);

	    // 设置cookie策略
	    get.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);
	    
	    // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse

	    response = http.execute(get);

	    // (3) 读取返回结果
	    HttpEntity entity = response.getEntity();
	    String code = HttpUT.getCharSet(entity);
	    InputStream in = entity.getContent();
	    out = readResponse(in, code);

	    if (response.getStatusLine().getStatusCode() != 200) {
		String errmsg = "请求错误："
			+ response.getStatusLine().getStatusCode();
		errmsg = errmsg + "<br>---------<br>" + out;
		System.err.println(errmsg);
		throw new RuntimeException(errmsg);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}
	return out;
    }

    /**
     * 简单的post请求返回网页内容
     * <hr>
     * <br>
     * outcode:优先用服务器端返回的response的编码，如果没返回用UTF-8
     * 
     * @return
     */
    public static String post(String url) {
	String out = "";
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	try {
	    // (1) 创建HttpPost实例
	    HttpPost post = new HttpPost(url);
	 
	    // 设置cookie策略
	    post.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);
	    // (2) 使用HttpClient发送post请求，获得返回结果HttpResponse

	    response = http.execute(post);
	    
	    // (3) 读取返回结果
	    HttpEntity entity = response.getEntity();
	    out = EntityUtils.toString(entity);
	    
	    if (response.getStatusLine().getStatusCode() != 200) {
		String errmsg = "请求错误："
			+ response.getStatusLine().getStatusCode();
		errmsg = errmsg + "<br>---------<br>" + out;
		System.err.println(errmsg);
		throw new RuntimeException(errmsg);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}
	return out;
    }

    /**
     * 执行带有参数的get请求,返回网页全部文本<br>
     * <hr>
     * 输入编码如果没有指定默认是UTF-8<br>
     * 输出编码如果没有返回默认是UTF-8<br>
     * 
     * @param url
     * @param params
     * @param incode
     * @return
     */
    public static String get(String url, Map<String, String> params,
	    String incode) {

	String out = "";
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	try {
	    // (1)创建查询参数
	    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
	    if (params != null && !params.isEmpty()) {
		Set<String> keyset = params.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
		    String key = it.next();
		    String value = params.get(key);
		    paramss.add(new BasicNameValuePair(key, value));
		}
	    }
	    
	    String queryString = "";
	    if (StringUtils.isNotEmpty(incode)) {
		queryString = URLEncodedUtils.format(paramss, incode);
	    } else {
		queryString = URLEncodedUtils.format(paramss, CharSets.UTF_8);
	    }

	    // (2) 创建Get实例
	    URIBuilder uriBuilder = new URIBuilder(url);
	    uriBuilder.setQuery(queryString);
	    URI uri = uriBuilder.build();
	    HttpGet get = new HttpGet(uri);

	    // 设置cookie策略
	    get.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);
	    
	    // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse
	    response = http.execute(get);

	    // (3) 读取返回结果
	    HttpEntity entity = response.getEntity();
	    out = EntityUtils.toString(entity);

	    if (response.getStatusLine().getStatusCode() != 200) {
		String errmsg = "请求错误："
			+ response.getStatusLine().getStatusCode();
		errmsg = errmsg + "<br>---------<br>" + out;
		System.err.println(errmsg);
		throw new RuntimeException(errmsg);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}

	return out;
    }

    /**
     * 
     * 执行带有参数的post请求,返回网页全部文本<br>
     * <hr>
     * 输入编码如果没有指定默认是UTF-8<br>
     * 输出编码如果没有返回默认是UTF-8<br>
     * 
     * @param url
     * @param params
     * @param incode
     * @return
     */
    public static String post(String url, Map<String, String> params,
	    String incode) {
	String out = "";
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	try {
	    // (1)创建查询参数
	    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
	    if (params != null && !params.isEmpty()) {
		Set<String> keyset = params.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
		    String key = it.next();
		    String value = params.get(key);
		    paramss.add(new BasicNameValuePair(key, value));
		}
	    }

	    if (StringUtils.isEmpty(incode)) {
		incode = CharSets.UTF_8;
	    }

	    // (2) 创建post实例
	    HttpPost post = new HttpPost(url);
	    post.setEntity(new UrlEncodedFormEntity(paramss, incode));
	    
	    // 设置cookie策略
	    post.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);
	    
	    // (2) 使用HttpClient发送post请求，获得返回结果HttpResponse
	    response = http.execute(post);

	    // (3) 读取返回结果
	    HttpEntity entity = response.getEntity();
	    out = EntityUtils.toString(entity);

	    if (response.getStatusLine().getStatusCode() >400) {
		String errmsg = "请求错误："
			+ response.getStatusLine().getStatusCode();
		errmsg = errmsg + "<br>---------<br>" + out;
		System.err.println(errmsg);
		throw new RuntimeException(errmsg);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}
	return out;
    }

    /**
     * 执行带有参数的get请求,返回网页全部文本<br>
     * <hr>
     * 输入编码如果没有指定默认是UTF-8<br>
     * 输出编码如果没有返回默认是UTF-8<br>
     * 
     * @param url
     * @param params
     * @param incode
     * @return
     */
    public static TextHttpResponse get_response(String url,
	    Map<String, String> params, String incode) {
	Header[] heads = null;
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	TextHttpResponse out = new TextHttpResponse();
	try {
	    // (1)创建查询参数
	    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
	    if (params != null && !params.isEmpty()) {
		Set<String> keyset = params.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
		    String key = it.next();
		    String value = params.get(key);
		    paramss.add(new BasicNameValuePair(key, value));
		}
	    }
	    
	    String queryString = "";
	    if (StringUtils.isNotEmpty(incode)) {
		queryString = URLEncodedUtils.format(paramss, incode);
	    } else {
		queryString = URLEncodedUtils.format(paramss, CharSets.UTF_8);
	    }

	    // (2) 创建Get实例
	    URIBuilder uriBuilder = new URIBuilder(url);
	    uriBuilder.setQuery(queryString);
	    URI uri = uriBuilder.build();
	    HttpGet get = new HttpGet(uri);

	    // 设置cookie策略
	    get.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);
	    
	    
	    // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse
	    response = http.execute(get);
	    HttpEntity entity = response.getEntity();
	    String out_str = EntityUtils.toString(entity);
	    out.setContent(out_str);
	    
	    if (response.getStatusLine().getStatusCode() != 200) {

		throw new RuntimeException(out_str);
	    }

	    // 获取所有的响应http头
	    heads = response.getAllHeaders();
	    out.setHeaders(heads);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}
	
	return out;

    }

    /**
     * 
     * 执行带有参数的post请求,返回网页全部文本<br>
     * <hr>
     * 输入编码如果没有指定默认是UTF-8<br>
     * 输出编码如果没有返回默认是UTF-8<br>
     * 
     * @param url
     * @param params
     * @param incode
     * @return
     * 
     */
    public static TextHttpResponse post_response(String url,
	    Map<String, String> params, String incode) {
	Header[] heads = null;
	HttpClient http = SingleHttpClient.getInstance();
	HttpResponse response = null;
	TextHttpResponse out = new TextHttpResponse();
	try {
	    // (1)创建查询参数
	    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
	    if (params != null && !params.isEmpty()) {
		Set<String> keyset = params.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
		    String key = it.next();
		    String value = params.get(key);
		    paramss.add(new BasicNameValuePair(key, value));
		}
	    }

	    if (StringUtils.isEmpty(incode)) {
		incode = CharSets.UTF_8;
	    }

	    // (2) 创建post实例
	    HttpPost post = new HttpPost(url);
	    post.setEntity(new UrlEncodedFormEntity(paramss, incode));

	    // 设置cookie策略
	    post.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		    CookiePolicy.BROWSER_COMPATIBILITY);

	    // (2) 使用HttpClient发送post请求，获得返回结果HttpResponse
	    response = http.execute(post);

	    // (3) 读取返回结果
	    HttpEntity entity = response.getEntity();
	    String out_str = EntityUtils.toString(entity);
	    out.setContent(out_str);
	    
	    if (response.getStatusLine().getStatusCode() != 200) {
		throw new RuntimeException(out_str);
	    }

	    // 取得heads
	    heads = response.getAllHeaders();
	    out.setHeaders(heads);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    HttpClientUtils.closeQuietly(response);
	}
	return out;
    }

    // 读取返回结果
    private static String readResponse(InputStream in, String code)
	    throws Exception {
	StringBuilder stringBuilder = new StringBuilder();
	BufferedReader reader = new BufferedReader(new InputStreamReader(in,
		code));
	String line = null;
	while ((line = reader.readLine()) != null) {
	    stringBuilder.append(line + "\r\n");
	}
	return stringBuilder.toString();
    }

    /**
     * 也可以使用BasicResponseHandler实现
     * 
     * @param response
     * @return
     */
    public static String readAll(HttpResponse response) {
	String out = "";
	try {
	    HttpEntity entity = response.getEntity();
	    String code = HttpUT.getCharSet(entity);
	    InputStream in = entity.getContent();
	    out = readResponse(in, code);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return out;
    }

    
    
    
}
