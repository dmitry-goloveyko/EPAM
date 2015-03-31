package com.epam.wt.datatempstorage;

import java.util.HashMap;
import java.util.Map;

import com.epam.wt.entity.Note;

public class Request {
	private Map<String, Object> param = new HashMap<String, Object>();

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setValue(Object value) {
		if(value instanceof Integer) {
			param.put("index", value);
		}
		
		if (value instanceof Note) {
			param.put("note", value);
		}
	}

	public Object getValue(String key) {
		return param.get(key);
	}
	
}
