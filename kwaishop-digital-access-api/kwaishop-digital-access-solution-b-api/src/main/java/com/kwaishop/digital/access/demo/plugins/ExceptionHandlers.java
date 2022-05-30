//package com.kwaishop.digital.access.demo.plugins;
//
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//import org.springframework.http.converter.HttpServiceResponseNotReadableException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import com.kwaishop.digital.access.demo.model.ServiceResponse;
//
///**
// * Spring 全局异常处理器
// *
// * @author zhangyiying
// * Created on 2022-05-12
// */
//@RestControllerAdvice
//public class ExceptionHandlers {
//
//    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);
//
//    @ExceptionHandler(Throwable.class)
//    public ServiceResponse<?> handleThrowable(HttpServletRequest request, Throwable e) {
//        printStackTrace(request, e);
//        return ServiceResponse.error(ResultCode.SERVICE_BUSY);
//    }
//
//    @ExceptionHandler(OpenMarketServiceException.class)
//    public ServiceResponse<?> handleOpenPlatformServiceException(HttpServletRequest request, OpenMarketServiceException e) {
//        if (e.getResultCode().equals(ResultCode.RATE_LIMIT)) {
//            printParam(request, e);
//        } else {
//            printStackTrace(request, e);
//        }
//        if (e.getResultCode() == null) {
//            return ServiceResponse.error(ResultCode.SERVICE_ERROR);
//        }
//        return ServiceResponse.error(e.getResultCode(), e.getServiceResponse());
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ServiceResponse<?> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
//            HttpRequestMethodNotSupportedException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.REQUEST_METHOD_NOT_SUPPORTED, "不支持方法：" + e.getServiceResponse());
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ServiceResponse<?> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.PARAM_VALIDATION_FAILURE, "参数错误：" + e.getServiceResponse());
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ServiceResponse<?> handleMethodArgumentTypeMismatchException(HttpServletRequest request,
//            MethodArgumentTypeMismatchException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.PARAM_VALIDATION_FAILURE, "参数类型错误：" + e.getName());
//    }
//
//    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
//    public ServiceResponse<?> handleInvalidFormatException(HttpServletRequest request, InvalidFormatException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.PARAM_INVALID_FORMAT_FAILURE, "参数类型错误,转换失败");
//    }
//
//    @ExceptionHandler(org.springframework.http.converter.HttpServiceResponseNotReadableException.class)
//    public ServiceResponse<?> handleHttpServiceResponseNotReadableException(HttpServletRequest request,
//            HttpServiceResponseNotReadableException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.PARAM_INVALID_FORMAT_FAILURE, "参数类型错误,转换失败");
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ServiceResponse<?> handleMissingServletRequestParameterException(HttpServletRequest request,
//            MissingServletRequestParameterException e) {
//        printErrorMsg(request, e);
//        return ServiceResponse.error(ResultCode.PARAM_REQUIRED, "缺少参数：" + e.getParameterName());
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ServiceResponse<?> handleMethodArgumentNotValidException(HttpServletRequest request,
//            MethodArgumentNotValidException e) {
//        printErrorMsg(request, e);
//        List<String> collect = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultServiceResponse)
//                .collect(Collectors.toList());
//        String ServiceResponse = String.join(",", collect);
//        return ServiceResponse.error(ResultCode.PARAM_VALIDATION_FAILURE, ServiceResponse);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ServiceResponse<?> handle(HttpServletRequest request, ConstraintViolationException exception) {
//        List<String> collect = exception.getConstraintViolations().stream().map(ConstraintViolation::getServiceResponse)
//                .collect(Collectors.toList());
//        String ServiceResponse = String.join(",", collect);
//        return ServiceResponse.error(ResultCode.PARAM_VALIDATION_FAILURE, ServiceResponse);
//    }
//
//    /**
//     * 打印异常信息
//     */
//    private void printErrorMsg(Throwable e) {
//        logger.error("handle:{}, msg:{}", e.getClass().getName(), e.getServiceResponse());
//    }
//
//    /**
//     * 打印异常信息和入参
//     */
//    private void printErrorMsg(HttpServletRequest request, Throwable e) {
//        Map<String, String[]> params = request.getParameterMap();
//        Map<String, Object> map = Maps.transformValues(
//                params, arr -> (arr == null || arr.length == 0) ? null : arr.length == 1 ? arr[0] : arr
//        );
//        logger.error("handle:{}, msg:{}, params:{}", e.getClass().getName(), e.getServiceResponse(), toJSON(map));
//    }
//
//    /**
//     * 打印异常堆栈和入参
//     */
//    private void printStackTrace(HttpServletRequest request, Throwable e) {
//        Map<String, String[]> params = request.getParameterMap();
//        Map<String, Object> map = Maps.transformValues(
//                params, arr -> (arr == null || arr.length == 0) ? null : arr.length == 1 ? arr[0] : arr
//        );
//        logger.error("handle:{},  msg: {}, params:{}", e.getClass().getName(), e.getServiceResponse(), toJSON(map), e);
//    }
//
//    /**
//     * 打印异常堆栈和入参
//     */
//    private void printParam(HttpServletRequest request, Throwable e) {
//        Map<String, String[]> params = request.getParameterMap();
//        Map<String, Object> map = Maps.transformValues(
//                params, arr -> (arr == null || arr.length == 0) ? null : arr.length == 1 ? arr[0] : arr
//        );
//        logger.error("handle:{},  msg: {}, params:{}", e.getClass().getName(), e.getServiceResponse(), toJSON(map));
//    }
//}
