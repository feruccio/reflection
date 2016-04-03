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
			if (o1 == o2) {
				return true;
			}
			if (o1 == null || o2 == null) {
				return false;
			}
			Map<String, Field> fields1 = getSignificantFieldsMap(o1.getClass());
			if (fields1.size() == 0) {
				return false;
			}
			Map<String, Field> fields2 = getSignificantFieldsMap(o2.getClass());
			if (fields1.size() != fields2.size()) {
				return false;
			}
			for (Field field1 : fields1.values()) {
				Field field2 = fields2.get(field1.getName());
				if (field2 == null) {
					return false;
				}
				Equal ann1 = field1.getAnnotation(Equal.class);
				Equal ann2 = field2.getAnnotation(Equal.class);
				Equal.Method method = ann1.compareby();
				if (method != ann2.compareby()) {
					return false;
				}
				field1.setAccessible(true);
				Object value1 = field1.get(o1);
				field2.setAccessible(true);
				Object value2 = field2.get(o2);
				if (value1 != null && value2 != null) {
					switch (method) {
					case REFERENCE:
						if (value1 != value2) {
							return false;
						}
						break;

					case VALUE:
						if (!value1.equals(value2)) {
							return false;
						}
						break;

					default:
						throw new EnumConstantNotPresentException(Equal.Method.class, 
								method.name());
					}
				} else if (!(value1 == null && value2 == null)) {
					return false;
				}
			}
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
