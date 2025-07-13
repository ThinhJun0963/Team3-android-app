package com.example.prm392_group_project.models;

public class ProductCategoryDTO {
    private Long id;
    private String name;

    // Constructor có id (để tạo nhanh khi submit)
    public ProductCategoryDTO(Long id) {
        this.id = id;
    }

    // Constructor mặc định (cần cho Retrofit, JSON, v.v.)
    public ProductCategoryDTO() {}

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
}

