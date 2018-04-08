package demo.util;

import java.io.Serializable;

/**
 * 当返回值是string的时候使用该类包装
 * @author liang
 *
 */
public class ReturnJsonValue implements Serializable{

	private String value;

	public ReturnJsonValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
