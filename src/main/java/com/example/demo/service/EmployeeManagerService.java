package com.example.demo.service;

import com.example.demo.config.db.ConfigMap;
import com.example.demo.dto.request.EditEmployeeRequestDTO;
import com.example.demo.dto.request.GetEmployeeRequestDTO;
import com.example.demo.dto.request.SaveEmployeeRequestDTO;
import com.example.demo.dto.request.UpdateImageRequestDTO;
import com.example.demo.entities.Employee;
import com.example.demo.exceptions.EmployeeDeleteNotFoundException;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.impl.EmployeeManager;
import com.example.demo.repositories.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagerService implements EmployeeManager {

    EmployeeRepository employeeRepository;
    ObjectMapper objectMapper;
    ConfigMap configMap;

    public EmployeeManagerService(EmployeeRepository employeeRepository, ObjectMapper objectMapper, ConfigMap configMap) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
        this.configMap = configMap;
    }

//    @HystrixCommand(fallbackMethod = "genericMethod", groupKey = "EmployeeService",
//                        commandKey = "EmployeeService", threadPoolKey = "EmployeeService"
//            )
    @Override
    public ResponseEntity getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(allEmployees, HttpStatus.OK);
    }

    private ResponseEntity<?> genericMethod(){
        String serviceDownMessage = configMap.getConfigProperty("service.down.message");
        return new ResponseEntity<>(serviceDownMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getEmployeeById(GetEmployeeRequestDTO getEmployeeRequestDTO) {
        Optional<Employee> employee = employeeRepository.findById(getEmployeeRequestDTO.getId());
        if(!employee.isPresent())
            throw new EmployeeNotFoundException();
        return new ResponseEntity(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveEmployee(SaveEmployeeRequestDTO saveEmployeeRequestDTO) {
        Employee employee = objectMapper.convertValue(saveEmployeeRequestDTO, Employee.class);
        employeeRepository.save(employee);
        return new ResponseEntity(employee, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity deleteEmployee(GetEmployeeRequestDTO getEmployeeRequestDTO) {
        Optional<Employee> employee = employeeRepository.findById(getEmployeeRequestDTO.getId());
        if(!employee.isPresent())
            throw new EmployeeDeleteNotFoundException();
        employeeRepository.deleteById(getEmployeeRequestDTO.getId());
        return new ResponseEntity(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity editEmployee(EditEmployeeRequestDTO editEmployeeRequestDTO) {
        Optional<Employee> employee = employeeRepository.findById(editEmployeeRequestDTO.getId());
        if(!employee.isPresent())
            throw new EmployeeNotFoundException();
        Employee requestObject = objectMapper.convertValue(editEmployeeRequestDTO, Employee.class);
        employeeRepository.save(requestObject);
        return new ResponseEntity(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateImage(UpdateImageRequestDTO updateImageRequestDTO) {
        try {
            String filePath = configMap.getConfigPropertyFromEnvironment("static.file.path.to.serve");
            MultipartFile image = updateImageRequestDTO.getImage();
            if (!image.isEmpty()) {
                byte[] bytes = image.getBytes();
                Path path = Paths.get(filePath.replace("file:","") + updateImageRequestDTO.getImageName());
                Files.write(path, bytes);
            }
            return new ResponseEntity<>("File updated successfully",HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File updation failed",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateConfigurationHashMap() {
        configMap.readAllConfigPropertiesFromDBToConfigMap();
        return new ResponseEntity<>("Configurations updated successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateConfigurationInPropertyFile() {
        configMap.readAllConfigPropertiesFromDBToConfigMapPropertyFileUpdate();
        return new ResponseEntity<>("Configurations updated successfully",HttpStatus.OK);
    }
}
