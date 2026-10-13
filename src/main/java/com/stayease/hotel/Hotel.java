package com.stayease.hotel;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double pricePerNight;

    private String type; // e.g. Luxury, Premium, Resort, Boutique, Nature, Business

    private Double rating = 4.8;

    @Column(length = 2000)
    private String imageUrl;

    @Column(length = 1500)
    private String description;

    private String amenities = "Free Wi-Fi, Swimming Pool, Spa, Breakfast Included, Air Conditioning";

    public Hotel() {}

    public Hotel(String name, String location, Double pricePerNight, String type, Double rating, String imageUrl, String description) {
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.type = type;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
}