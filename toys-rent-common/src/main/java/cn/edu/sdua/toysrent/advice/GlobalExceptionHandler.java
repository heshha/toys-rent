package cn.edu.sdua.toysrent.advice;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        System.out.println("【业务】 BusinessException处理");
        System.out.println(e.getCode() + " " + e.getMessage());
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public R handleNoResourceFoundException(NoResourceFoundException e) {
        System.out.println("【资源】 NoResourceFoundException处理");
        System.out.println(e.getMessage());
        return R.error(ExceptionCodeEnum.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class})
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        System.out.println("【参数】 HttpMessageNotReadableException处理");
        System.out.println(e.getMessage());
        return R.error(ExceptionCodeEnum.MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(FileUploadException.class)
    public R handleFileUploadException(FileUploadException e) {
        System.out.println("【文件】 FileUploadException处理");
        System.out.println(e.getMessage());
        return R.error(ExceptionCodeEnum.FILE_UPLOAD_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("【校验】 MethodArgumentNotValidException处理");
        BindingResult result = e.getBindingResult();
        HashMap<Object, Object> errorsMap = new HashMap<>();
        result.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorsMap.put(fieldName, errorMessage);
        });
        System.out.println(errorsMap);
        e.printStackTrace();
        return R.error(ExceptionCodeEnum.VALIDATE_ERROR, errorsMap);
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public R handleExpiredJwtException(ExpiredJwtException e) {
//        System.out.println("【token】 ExpiredJwtException处理");
//        return R.error(401, "token已过期");
//    }
//
//    @ExceptionHandler(JwtException.class)
//    public R handleJwtException(JwtException e) {
//        System.out.println("【token】 JwtException处理");
//        return R.error(402, "token无效");
//    }

    @ExceptionHandler(Throwable.class)
    public R globalException(Throwable e) {
        System.out.println("【全局】 Exception处理");
        e.printStackTrace();
        return R.error(500, e.getMessage());
    }


//    @Override
//    public boolean supports(org.springframework.core.MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        // 判断当前请求是否是 Swagger 相关的请求
//        return !returnType.getDeclaringClass().isAssignableFrom(BasicErrorController.class);
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, org.springframework.core.MethodParameter returnType, org.springframework.http.MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
//        // 判断当前请求是否是 Swagger 相关的请求
//        if (request.getURI().getPath().startsWith("/swagger") || request.getURI().getPath().startsWith("/v2/api-docs")) {
//            return body;
//        }
//
//        // 其他情况下进行缓存操作
//        // ...
//        return body;
//    }
}
