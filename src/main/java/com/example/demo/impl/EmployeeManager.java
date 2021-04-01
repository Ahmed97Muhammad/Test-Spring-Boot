package com.example.demo.impl;

import com.example.demo.dto.request.EditEmployeeRequestDTO;
import com.example.demo.dto.request.GetEmployeeRequestDTO;
import com.example.demo.dto.request.SaveEmployeeRequestDTO;
import com.example.demo.dto.request.UpdateImageRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface EmployeeManager {

    ResponseEntity<?> getAllEmployees();
    ResponseEntity<?> getEmployeeById(GetEmployeeRequestDTO getEmployeeRequestDTO);
    ResponseEntity<?> saveEmployee(SaveEmployeeRequestDTO saveEmployeeRequestDTO);
    ResponseEntity<?> deleteEmployee(GetEmployeeRequestDTO getEmployeeRequestDTO);
    ResponseEntity<?> editEmployee(EditEmployeeRequestDTO editEmployeeRequestDTO);
    ResponseEntity<?> updateImage(UpdateImageRequestDTO updateImageRequestDTO);
    ResponseEntity<?> updateConfigurationHashMap();
    ResponseEntity<?> updateConfigurationInPropertyFile();
}
