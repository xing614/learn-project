package demo.util;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 全局json 数据返回处理,ResponseBodyAdvice是spring4.1的新特性，其作用是在响应体写出之前做一些处理；比如，修改返回值、加密等。
 */
@ControllerAdvice
public class BaseGlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 需要处理的类型
     *
     */
    //private final Set<MediaType> jsonMediaType = new ImmutableSet.Builder<MediaType>().add(MediaType.APPLICATION_JSON).add(MediaType.APPLICATION_JSON_UTF8).build();

	/*
	 * upports指定支持哪些类型的方法进行处理，此处是返回值true全部
	 */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在数据返回前修改数据。
     */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
        //当类型 不属于 需要处理的包头的时候 直接返回obj
//        if (!jsonMediaType.contains(selectedContentType)) {
//            return body;
//        }
		//判断是否是string是因为，如果返回类型是string,jackson没法自动解析string，只能解析类对象变成json，不然报错java.lang.ClassCastException: demo.util.ReturnJsonBody cannot be cast to java.lang.String
        //现在把body instanceof String删掉了，在controller方法中就对string类型的返回值进行包装
		//当类型 是属于需要处理的时候 并且 obj不是ReturnJsonBody的时候 进行格式化处理
		if(!(body instanceof ReturnJsonBody)) {
        	body = new ReturnJsonBody(RespCode.SUCCESS, body);
        }
		System.out.println(body.toString());
        return body;
	}

}
