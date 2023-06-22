package com.example.Canteen.Models;
import java.util.Date;

public class Menu {
    private Long id;
    private String name;
    private String description;


    private Long price;
    private String image;

    private Date createdAt;


    public Menu() {
        // Empty constructor needed for JSON deserialization
    }

    // Getter methods

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public long getPrice() { return price; }
    public String getImage() {
        return image;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    // Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setPrice(long price) { this.price = price; }
    public void setImage(String image) {
        this.image = image;
    }


    public void setCreatedAt(Date date){
        this.createdAt = date;
    }

    public void add(Menu menu) {
    }
}
