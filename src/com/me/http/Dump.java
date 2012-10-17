package com.me.http;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Dump
{

    private static void dumpList(List list)
    {
        if (list == null || list.size() == 0)
        {
            return;
        }

        
        for (int i = 0; i < list.size(); i++)
        {
            System.err.println(list.get(i));
        }
        
    }


    /**
     * Hashtable 是map的子类
     * 
     * @param map
     */
    private static void dumpMap(Map map)
    {

        if (map == null || map.isEmpty())
        {
            return;
        }

        

        Set set = map.keySet();
        Iterator it = set.iterator();
        while (it.hasNext())
        {

            Object key = it.next();
            Object value = map.get(key);
            System.out.println("key:value===" + key + ":" + value);
        }
        
    }


    private static void dumapString(String str)
    {
        if (str == null || str.length() < 1)
        {
            return;
        }

        
        System.out.println(str);

        
    }


    /**
     * 打印变量到控制台
     */
    public static void dump(Object obj)
    {

        if (obj instanceof String)
        {
            dumapString(String.valueOf(obj));
        }
        else if (obj instanceof List)
        {
            dumpList((List) obj);
        }
        else if (obj instanceof Map)
        {
            dumpMap((Map) obj);
        }
        else
        {
            dumapString(String.valueOf(obj));
        }
    }

}
