package com.example.demo.dto.response;

public class ProductResponse {

    private String name;
    private double price;
    private Integer groupId;

    public ProductResponse(String name, double price, Integer groupId) {
        this.name = name;
        this.price = price;
        this.groupId = groupId;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public Integer getGroupId() { return groupId; }
}