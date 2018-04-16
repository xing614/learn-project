package demo.util;

import java.io.Serializable;

/**
 * 设置返回json的格式，包括状态，数据
 * 
 * 这种返回的类一定要有getter，setter方法！！！！！！！，要不然会出现错误
 * No serializer found for class demo.util.ReturnJsonBody and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
 */
public class ReturnJsonBody implements Serializable {
    /**
     * 状态码
     */
    private int code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 附加信息
     */
    private String msg;

    public ReturnJsonBody(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }
    
    public ReturnJsonBody(RespCode respCode, Object data) {
        this(respCode);
        this.data = data;
    }

    
	public ReturnJsonBody(int code, Object data, String msg) {
		super();
		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ReturnJsonBody [code=" + code + ", data=" + data + ", msg=" + msg + "]";
	}
	
	
}
