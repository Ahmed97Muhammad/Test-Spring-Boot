package com.example.demo.dto.request;

import javax.validation.constraints.NotNull;

public class SaveEmployeeRequestDTO {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "age is required")
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
