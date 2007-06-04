package com.googlecode.transloader.clone;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;


public class ObjenesisInstantiationStrategy implements InstantiationStrategy {
	Objenesis objenesis = new ObjenesisStd();

	public Object newInstance(Class type) throws Exception {
		return objenesis.newInstance(type);
	}

}