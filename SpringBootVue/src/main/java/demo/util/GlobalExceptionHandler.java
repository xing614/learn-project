package demo.util;

import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
