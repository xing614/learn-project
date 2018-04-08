package demo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import demo.util.ReturnJsonBody;

/**
 * 全局异常处理
 * @author liang
 *
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "err:" + e.getMessage();
    }
    
//    @ResponseBody
//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    public ReturnJsonBody formatCheckExceptionHandler(RuntimeException e) {
//        return new ReturnJsonBody(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//    }
}
