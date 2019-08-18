package cn.bblu.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Map resolveConstraintViolationException(ConstraintViolationException ex) {
        Map<String,String> returnData = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            StringBuilder valBuilder = new StringBuilder();
            String split = "";
            for (ConstraintViolation cv : constraintViolations) {
                msgBuilder.append(split).append(cv.getMessage());
                valBuilder.append(split).append(cv.getPropertyPath());
                split = ",";
            }
            returnData.put("error", msgBuilder.toString());
            returnData.put("data", valBuilder.toString());
            return returnData;
        }
        returnData.put("error", ex.getMessage());
        return returnData;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> returnData = new HashMap<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            StringBuilder valBuilder = new StringBuilder();
            String split = "";
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(split).append(objectError.getDefaultMessage());
                split = ",";
            }
            returnData.put("error", msgBuilder.toString());
            return returnData;
        }
        returnData.put("error", ex.getMessage());
        return returnData;
    }
}