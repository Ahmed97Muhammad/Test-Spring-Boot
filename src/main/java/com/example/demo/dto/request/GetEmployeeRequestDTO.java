package com.example.demo.dto.request;

import javax.validation.constraints.NotNull;

public class GetEmployeeRequestDTO {

    @NotNull(message = "id is required")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
