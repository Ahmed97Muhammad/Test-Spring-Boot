package com.example.demo.dto.request;

import javax.validation.constraints.NotNull;

public class EditEmployeeRequestDTO {

    @NotNull(message = "id is required")
    private Long id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "age is required")
    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
