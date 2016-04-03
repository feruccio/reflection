package com.haurylenka.projects.reflection;

public class ObjectAA extends ObjectA {
	
	private Object e;
	private Object f;
	
	public ObjectAA(int a, Long b, String c, Object d, Object e, Object f) {
		super(a, b, c, d);
		this.e = e;
		this.f = f;
	}

	public ObjectAA(int a, Long b, String c, Object d) {
		super(a, b, c, d);
	}

}
