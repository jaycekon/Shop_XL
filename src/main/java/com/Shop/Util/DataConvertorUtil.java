package com.Shop.Util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * DataConvertorUtil--数据格式转换的工具类
 */
public class DataConvertorUtil {

	// jackson包提供的json与javabean的转换类ObjectMapper
	private static ObjectMapper objectMapper;

	// 初始化对象objectMapper
	static {
		objectMapper = new ObjectMapper();
	}

	/*
	 * 实现功能--将javabean对象转换为json对象的字符串
	 * 
	 * @param obj--要转换的javabean对象
	 * 
	 * @return String类型--json对象的字符串
	 */
	public static String object2json(Object obj) {

		// 变量json--返回用的json对象的字符串
		String json = null;

		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/*
	 * 实现功能--将json对象的字符串转换为javabean对象
	 * 
	 * @param json--要转换的json对象的字符串
	 * 
	 * @param collectionClass--原始类的类对象
	 * 
	 * @param elementClasses--泛型类的类对象
	 * 
	 * @return Object类型--期望的javabean对象(类Object类型)，调用后需要结合具体类型进行强制转换
	 */
	public static Object json2object(String json, Class<?> collectionClass,
			Class<?>... elementClasses) {

		// 变量object--返回用的javabean对象
		Object object = null;
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		try {
			object = objectMapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			System.out.println("1111111");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("22222222");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("33333333");
			e.printStackTrace();
		}

		return object;
	}

	public static Object json2object(String json, Class<?> targetClass) {

		// 变量object--返回用的javabean对象
		Object object = null;
		try {
			object = objectMapper.readValue(json, targetClass);
		} catch (JsonParseException e) {
			System.out.println("1111111");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("22222222");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("33333333");
			e.printStackTrace();
		}

		return object;
	}
	/*
	 * 实现功能--将原始类的类对象与泛型类的类对象通过objectMapper绑定为jackson包提供的JavaType对象
	 * 
	 * @param collectionClass--原始类的类对象
	 * 
	 * @param elementClasses--泛型类的类对象
	 * 
	 * @return JavaType类型--绑定后的JavaType对象
	 */
	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(
				collectionClass, elementClasses);
	}

}
