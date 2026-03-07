package com.example.demo.dto.response;

public class AdminProductResponse {

    private Long id;
    private String name;
    private double price;
    private boolean active;
    private String groupId;

    public AdminProductResponse(Long id, String name,
                                double price, boolean active, String groupId) {
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
    public String getGroupid() { return groupId; }
}