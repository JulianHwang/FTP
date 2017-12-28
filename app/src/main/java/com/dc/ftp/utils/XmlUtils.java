package com.dc.ftp.utils;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

/**
 * 解析XML工具类
 */
public class XmlUtils {

	/**
	 * 解析XML转换成对象
	 *
	 * @param is
	 *            输入流
	 * @param clazz
	 *            对象Class
	 * @param fields
	 *            字段集合一一对应节点集合
	 * @param elements
	 *            节点集合一一对应字段集合
	 * @param itemElement
	 *            每一项的节点标签
	 * @return
	 */
	public static Map<String, String> parse(String s) {
		Log.v("rss", "开始解析XML.");
		Map<String, String> obj = new HashMap<String, String>();
		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(new StringReader(s));
			int event = xmlPullParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					obj.put(xmlPullParser.getName(), xmlPullParser.nextText());
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = xmlPullParser.next();
			}
		} catch (Exception e) {
			Log.e("rss", "解析XML异常：" + e.getMessage());
			throw new RuntimeException("解析XML异常：" + e.getMessage());
		}
		return obj;
	}

	/**
	 * 设置字段值
	 *
	 * @param propertyName
	 *            字段名
	 * @param obj
	 *            实例对象
	 * @param value
	 *            新的字段值
	 * @return
	 */
	public static void setFieldValue(Object obj, String propertyName, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(propertyName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	
	
	
}
