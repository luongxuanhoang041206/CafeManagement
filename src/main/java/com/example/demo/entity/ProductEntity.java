package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String stt;
    private String id;   

    private String name;
    private double price;
    private String groupId;
    private Boolean deleted;
    private Boolean active;
    private Date createdAt;
    private Date deletedAt;

    // ===== Getter & Setter =====

    public String getId() { return id; }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public boolean isActive() { return active; }
    
    public String groupId() { return groupId; }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setActive(boolean active) { this.active = active; }

	public void setGroupId(String groupId2) {
		this.groupId = groupId2;
	}

	public void setId(String id2) {
		this.id = id2;
	}
}