package com.feast.server_main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant_owners")
public class RestaurantOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ResOwnerId")
    private Long resOwnerId;

    @NotNull
    @Column(name = "UserName", columnDefinition = "VARCHAR(255)", nullable = false)
    private String userName;

    @Column(name = "Password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "OwnerName", columnDefinition = "VARCHAR(255)")
    private String ownerName;

    @Column(name = "PhoneNumber", columnDefinition = "VARCHAR(20)")
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurantOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants;

    public RestaurantOwner() {
    }

    public RestaurantOwner(String userName, String password, String ownerName, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

    public Long getResOwnerId() {
        return resOwnerId;
    }

    public void setResOwnerId(Long resOwnerId) {
        this.resOwnerId = resOwnerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantOwner that = (RestaurantOwner) o;
        return Objects.equals(resOwnerId, that.resOwnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resOwnerId);
    }

    @Override
    public String toString() {
        return "RestaurantOwner{" +
                "resOwnerId=" + resOwnerId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}