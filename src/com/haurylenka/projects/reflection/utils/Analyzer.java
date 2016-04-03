package com.haurylenka.projects.reflection.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.haurylenka.projects.reflection.annotations.Equal;
import com.haurylenka.projects.reflection.exceptions.ObjectAnalyzerException;

public class Analyzer {

	private Analyzer() {}

	/**
	 * Compares two objects on equality. The objects are considered 
	 * to be equal if the comparison is performed using the same set
	 * of criterion, these criterion are evaluated the same way and 
	 * the given objects are the same regarding each of the criterion.
	 * @Equal annotation acts as a criteria source.
	 */
	public static boolean equalObject(Object o1, Object o2) {
		try {
			System.out.println("\n------------------\nComparing objects:");
			System.out.println("Object#1 = " + o1);
			System.out.println("Object#2 = " + o2 + "\n");
			if (o1 == o2) {
				System.out.println("\nThe objects are equal\n------------------");
				return true;
			}
			if (o1 == null || o2 == null) {
				System.out.println("There are null objects");
				System.out.println("\nThe objects are not equal\n------------------");
				return false;
			}
			Map<String, Field> fields1 = getSignificantFieldsMap(o1.getClass());
			if (fields1.size() == 0) {
				System.out.println("Thre are no criterion to use");
				System.out.println("\nThe objects are not equal\n------------------");
				return false;
			}
			Map<String, Field> fields2 = getSignificantFieldsMap(o2.getClass());
			if (fields1.size() != fields2.size()) {
				System.out.println("The objects have different criterion sets");
				System.out.println("\nThe objects are not equal\n------------------");
				return false;
			}
			for (Field field1 : fields1.values()) {
				Field field2 = fields2.get(field1.getName());
				if (field2 == null) {
					System.out.println("The objects have different criterion sets");
					System.out.println("\nThe objects are not equal\n------------------");
					return false;
				}
				Equal ann1 = field1.getAnnotation(Equal.class);
				Equal ann2 = field2.getAnnotation(Equal.class);
				Equal.Method method = ann1.compareby();
				if (method != ann2.compareby()) {
					System.out.println("Different methods for the same field");
					System.out.println("\nThe objects are not equal\n------------------");
					return false;
				}
				System.out.println("comparing by " + method + " fields " + field1.getName() + ":");
				field1.setAccessible(true);
				Object value1 = field1.get(o1);
				System.out.println("field#1 = " + value1);
				field2.setAccessible(true);
				Object value2 = field2.get(o2);
				System.out.println("field#2 = " + value2);
				if (value1 != null && value2 != null) {
					switch (method) {
					case REFERENCE:
						if (value1 != value2) {
							System.out.println("not equal");
							System.out.println("\nThe objects are not equal\n------------------");
							return false;
						}
						break;

					case VALUE:
						if (!value1.equals(value2)) {
							System.out.println("not equal");
							System.out.println("\nThe objects are not equal\n------------------");
							return false;
						}
						break;

					default:
						throw new EnumConstantNotPresentException(Equal.Method.class, 
								method.name());
					}
					System.out.println("equal");
				} else if (!(value1 == null && value2 == null)) {
					System.out.println("not equal");
					System.out.println("\nThe objects are not equal\n------------------");
					return false;
				}
			}
			System.out.println("\nThe objects are equal\n------------------");
			return true;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ObjectAnalyzerException(e.getMessage());
		}
	}

	private static Map<String, Field> getSignificantFieldsMap(Class<?> clazz) {
		Map<String, Field> fields = new HashMap<>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Equal.class)) {
				fields.put(field.getName(), field);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			fields.putAll(getSignificantFieldsMap(superClazz));
		}
		return fields;
	}

}
