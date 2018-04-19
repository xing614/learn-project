package demo.util;
public enum RespCode {
	
	
    WARN(-1, "网络异常，请稍后重试"),
    SUCCESS(0, "请求成功"),
	UserError(1,"用户名或密码错误"),
	UserLimited(2,"该用户被锁定"),
	UserOver(3,"用户登陆过期,请重新登录"),
	UserAuthority(4,"您无此权限操作"),
	InsertUserSuccess(5,"注册成功"),
	InsertUserFailure(6,"注册失败"),
	UserExisted(7,"该用户名已存在");
	

    private int code;
    private String msg;

    /**
     * 构造函数要有，不然使用时不会自动赋值
     * 开始使用该类时，将此类转map，发现code值一直为0，最后发现是因为构造函数没有写this.code = code
     * @param code
     * @param msg
     */
    RespCode(int code, String msg) {
    	this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
    
}