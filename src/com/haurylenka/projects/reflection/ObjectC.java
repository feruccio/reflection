package com.haurylenka.projects.reflection;

import com.haurylenka.projects.reflection.annotations.Equal;
import com.haurylenka.projects.reflection.annotations.Equal.Method;

public class ObjectC {

	@Equal(compareby=Method.VALUE)
	private int a;
	@Equal(compareby=Method.VALUE)
	private Long b;
	private String c;
	private Object d;
	
	public ObjectC(int a, Long b, String c, Object d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
}
