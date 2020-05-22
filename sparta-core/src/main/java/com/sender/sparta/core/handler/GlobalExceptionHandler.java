package com.sender.sparta.core.handler;

import com.sender.sparta.core.constant.BadRequestEnum;
import com.sender.sparta.core.exception.ApiException;
import com.sender.sparta.core.exception.ConflictException;
import com.sender.sparta.core.vo.ErrorModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Iterator;
import java.util.Set;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(ValidationException e) {
        e.printStackTrace();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<?> constraintViolation = iterator.next();
                return ErrorModel.validation(constraintViolation.getMessage());
            }
        }
        return ErrorModel.badRequest(BadRequestEnum.VALIDATION);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        Iterator<ObjectError> errorIterator = bindingResult.getAllErrors().iterator();

        if (errorIterator.hasNext()) {
            ObjectError error = errorIterator.next();
            return ErrorModel.validation(error.getDefaultMessage());
        }
        return ErrorModel.badRequest(BadRequestEnum.VALIDATION);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(IllegalArgumentException e) {
        e.printStackTrace();
        return ErrorModel.badRequest(BadRequestEnum.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        return ErrorModel.badRequest(BadRequestEnum.METHOD_ARGUMENT_TYPE_MISMATCH);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(MissingServletRequestParameterException e) {
        e.printStackTrace();
        return ErrorModel.badRequest(BadRequestEnum.MISSING_REQUEST_PARAMETER);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequestHandler(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ErrorModel.badRequest(BadRequestEnum.HTTP_MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Object apiExceptionHandler(ApiException e) {
        e.printStackTrace();
        return ErrorModel.api(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Object conflictExceptionHandler(ConflictException e) {
        e.printStackTrace();
        return ErrorModel.http(HttpStatus.CONFLICT);
    }

    /**
     * ### Get Stack Trace Info ###
     * option1:
     * StringWriter error = new StringWriter();
     * e.printStackTrace(new PrintWriter(error));
     * log.error(error.toString());
     * option2:
     * log.error(Throwables.getStackTraceAsString(e));
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object internalServerErrorHandler(Exception e) {
        e.printStackTrace();
        return ErrorModel.http(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

