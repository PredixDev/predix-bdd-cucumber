package com.ge.predix.labs.common;

public class Urls {
	public final String prefix;
    public final String customer;
    
	Urls(String urlModel) {
		this.prefix = urlModel;
		customer = prefix + "/customer";
	}
}