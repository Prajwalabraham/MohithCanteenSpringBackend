package com.example.Canteen.Models;
import java.util.Date;

public class Menu {
    private long id;
    private String name;
    private String description;
<<<<<<< HEAD
=======
    private long price;
    private String image;
>>>>>>> d9d7865 (Update)
    private Date createdAt;


    public Menu() {
        // Empty constructor needed for JSON deserialization
    }

    // Getter methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
<<<<<<< HEAD
=======
    public long getPrice() { return price; }
    public String getImage() {
        return image;
    }
>>>>>>> d9d7865 (Update)

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setter methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
<<<<<<< HEAD
=======
    public void setPrice(long price) { this.price = price; }
    public void setImage(String image) {
        this.image = image;
    }

>>>>>>> d9d7865 (Update)
    public void setCreatedAt(Date date){
        this.createdAt = date;
    }

    public void add(Menu menu) {
    }
}
