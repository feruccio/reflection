package com.haurylenka.projects.reflection;

import com.haurylenka.projects.reflection.annotations.Equal;
import com.haurylenka.projects.reflection.annotations.Equal.Method;

public class ObjectA {

	@Equal(compareby=Method.VALUE)
	private int a;
	@Equal(compareby=Method.VALUE)
	private Long b;
	@Equal(compareby=Method.VALUE)
	private String c;
	@Equal(compareby=Method.REFERENCE)
	private Object d;
	
	public ObjectA(int a, Long b, String c, Object d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
}
