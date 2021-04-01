package com.example.demo.exceptions;

import com.example.demo.dto.request.GetEmployeeRequestDTO;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {EmployeeNotFoundException.class,})
    public ResponseEntity<?> employeeNotFoundException(EmployeeNotFoundException employeeNotFoundException, WebRequest request){
        GetEmployeeRequestDTO requestData = (GetEmployeeRequestDTO) request.getAttribute("requestData", RequestAttributes.SCOPE_REQUEST);

        employeeNotFoundException.printStackTrace();
        System.out.println(ExceptionUtils.getMessage(employeeNotFoundException));
        return new ResponseEntity("Employee Not Found", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EmployeeDeleteNotFoundException.class})
    public ResponseEntity<?> employeeDeleteNotFoundException(){
        return new ResponseEntity("Employee Delete Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class) //http 404 case
    public ResponseEntity<?> handle404() {
        return new ResponseEntity("Currently our serives are down :(", HttpStatus.OK);
    }


}
