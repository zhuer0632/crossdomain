package com.me.string;

import org.apache.commons.lang3.StringUtils;

public class Test01 {

    public static void main(String[] args) {

	Test01 t = new Test01();
	t.test01();

    }

    private void test01() {

	String s = "_gcj_session=BAh7CSIQX2NzcmZfdG9rZW4iMTFsQXQ0TTEyd0JLNmJ2RDBUMVRPRTV2bWVYMW82SzQrOHlGc2hwaW9iSzQ9IhN1c2VyX3JldHVybl90byIdL2luZm9fcHJpY2VzLzIyMTQ1L3RyZW5kIhl3YXJkZW4udXNlci51c2VyLmtleVsIIglVc2VyWwZpA8nmAzAiD3Nlc3Npb25faWQiJWQ0YWYzNWFiNzgxMWI1NmM0YjVmODc1YTZiNmNlNmJl--8fc9b1803231e91740bcb09a2197e9fcb2610072; domain=.gldjc.com; path=/; HttpOnly";

	String[] arr = StringUtils.split(s, "; ");

	for (int i = 0; i < arr.length; i++) {
	    
	    System.out.println(arr[i]);

	    if (arr[i].contains("gcj_session")) {
		s=StringUtils.split(arr[i],"=")[1];
		System.out.println(StringUtils.split(arr[i],"=")[1]);
		break;
	    }
	}

	
	System.out.println(s);
	
	
    }

}
