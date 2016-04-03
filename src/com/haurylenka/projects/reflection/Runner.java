package com.haurylenka.projects.reflection;

import com.haurylenka.projects.reflection.utils.Analyzer;

public class Runner {

	public static void main(String[] args) {
		Object obj1 = new ObjectA(1, 2L, "xx", "yy");
		Object obj2 = new ObjectA(1, 2L, "xx", "yy");
		Object obj4 = new ObjectB("dd", "");
		Object obj5 = new ObjectC(1, 2L, "xx", "yy");
		Object obj6 = new ObjectD(1, 2L, "xx", "yy");
		Object obj7 = new ObjectAA(10, 11L, "a", obj1, "c", "d");
		Object obj8 = new ObjectA(10, 11L, "a", obj1);
		Object obj9 = new ObjectAA(10, 11L, "a", obj2, "c", "d");
		
		Analyzer.equalObject(obj1, null);
		Analyzer.equalObject(obj4, obj1);
		Analyzer.equalObject(obj1, obj4);
		Analyzer.equalObject(obj1, obj5);
		Analyzer.equalObject(obj1, obj6);
		Analyzer.equalObject(obj7, obj8);
		Analyzer.equalObject(obj7, obj9);
	}

}
