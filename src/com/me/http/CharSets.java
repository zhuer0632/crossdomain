package com.me.http;

public class CharSets {

    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 
     * 默认编码转成UTF-8
     * 
     * @param str
     * @return
     */
    public static String ISO_8859_1_to_UTF_8(String str) {
	try {
	    String temp = new String(str.getBytes("ISO-8859-1"), "UTF-8");
	    return temp;
	} catch (java.io.IOException ex) {
	    ex.printStackTrace();
	    return null;
	}
    }

}
