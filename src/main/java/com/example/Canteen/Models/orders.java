package com.example.Canteen.Models;

import java.util.Date;

public class orders {
    private long id;
    private long menuId;
    private long userId;
    private long price;
    private String MenuDescription;
    private String MenuName;
    private long MenuPrice;
    private long quantity;
    private Date createdAt;
    private String status;
    private String image;

    private String username;
    public orders() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }


    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMenuDescription(String description) {
        this.MenuDescription = description;
    }

    public void setMenuPrice(long price) {
        this.MenuPrice = price;
    }

    public void setMenuName(String name) {
        this.MenuName = name;
    }

    public String getMenuDescription(){
        return MenuDescription;
    }
    public String getMenuName(){
        return MenuName;
    }
    public long getMenuPrice(){
        return MenuPrice;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

