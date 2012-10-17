package com.me.http;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.http.Header;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class TextHttpResponse {

    private Hashtable<String, String> heads;
    private String content;

    public Hashtable<String, String> getHeads() {
	return heads;
    }

    public void setHeads(Hashtable<String, String> heads) {
	this.heads = heads;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public void setHeaders(Header[] heads2) {
	if (heads == null) {
	    heads = new Hashtable<String, String>();
	}
	for (int i = 0; i < heads2.length; i++) {
	    heads.put(heads2[i].getName(), heads2[i].getValue());
	}

    }

    public String getHeads(TextHttpResponse out) {

	Hashtable head = out.getHeads();

	Enumeration<String> keys = head.keys();

	while (keys.hasMoreElements()) {
	    String key = (String) keys.nextElement();
	    String value = (String) head.get(key);

	    if (key.contains("Set-Cookie")) {
		return value;
	    }
	}
	
	return null;
    }

}
