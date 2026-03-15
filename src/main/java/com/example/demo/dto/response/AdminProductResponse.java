package com.example.demo.dto.response;

public class AdminProductResponse {

    private Long id;
    private String name;
    private double price;
    private boolean active;
    private Integer groupId;

    public AdminProductResponse(Long id, String name,
                                double price, boolean active, Integer groupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.groupId = groupId;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean getActive() { return active; }
    public Integer getGroupid() { return groupId; }
}