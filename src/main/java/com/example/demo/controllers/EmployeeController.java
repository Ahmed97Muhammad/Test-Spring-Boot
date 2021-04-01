package com.example.demo.controllers;

import com.example.demo.dto.request.EditEmployeeRequestDTO;
import com.example.demo.dto.request.GetEmployeeRequestDTO;
import com.example.demo.dto.request.SaveEmployeeRequestDTO;
import com.example.demo.dto.request.UpdateImageRequestDTO;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.impl.EmployeeManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController()
@RequestMapping(path = "/api/v1")
public class EmployeeController {

    EmployeeManager employeeManager;

    public EmployeeController(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }


    @PostMapping("getEmployees")
    public ResponseEntity<?> getAllEmployees(){
        return employeeManager.getAllEmployees();
    }

    @PostMapping("getEmployeeById")
    public ResponseEntity<?> getEmployeeById(@RequestBody @Valid GetEmployeeRequestDTO getEmployeeRequestDTO){
        return employeeManager.getEmployeeById(getEmployeeRequestDTO);
    }

    @PostMapping("saveEmployee")
    public ResponseEntity<?> saveEmployee(@RequestBody @Valid SaveEmployeeRequestDTO saveEmployeeRequestDTO){
        return employeeManager.saveEmployee(saveEmployeeRequestDTO);
    }

    @PostMapping("deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestBody @Valid GetEmployeeRequestDTO getEmployeeRequestDTO){
        return employeeManager.deleteEmployee(getEmployeeRequestDTO);
    }

    @PostMapping("editEmployee")
    public ResponseEntity<?> editEmployee(@RequestBody @Valid EditEmployeeRequestDTO editEmployeeRequestDTO){
        return employeeManager.editEmployee(editEmployeeRequestDTO);
    }

    @PostMapping(path = "uploadImage",consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateImage(UpdateImageRequestDTO updateImageRequestDTO) {
        return employeeManager.updateImage(updateImageRequestDTO);
    }

    @GetMapping(path = "updateConfigurationHashMap")
    public ResponseEntity<?> updateConfigurationHashMap() {
        return employeeManager.updateConfigurationHashMap();
    }

    @GetMapping(path = "updateConfigurationInPropertyFile")
    public ResponseEntity<?> updateConfigurationInPropertyFile() {
        return employeeManager.updateConfigurationInPropertyFile();
    }

}
