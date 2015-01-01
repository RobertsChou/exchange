package com.howto.exchange.bean;

public class BaseGsonBean<T> {
	public String status;
	public String info;
	public T data;
	@Override
	public String toString() {
		return "BaseGsonBean [status=" + status + ", info=" + info + ", data="
				+ data + "]";
	}
	
	
}
