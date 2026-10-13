package com.stayease.hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelRepository repo;

    public HotelController(HotelRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Hotel> all(@RequestParam(required = false, defaultValue = "") String location) {
        if (location == null || location.trim().isEmpty()) {
            return repo.findAll();
        }
        return repo.findByLocationContainingIgnoreCase(location.trim());
    }

    @GetMapping("/{id}")
    public Hotel one(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found with id: " + id));
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        if (hotel.getName() == null || hotel.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name is required.");
        }
        if (hotel.getLocation() == null || hotel.getLocation().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel location is required.");
        }
        if (hotel.getPricePerNight() == null || hotel.getPricePerNight() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valid price per night is required.");
        }
        if (hotel.getImageUrl() == null || hotel.getImageUrl().isBlank()) {
            hotel.setImageUrl("https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1000&q=85");
        }
        if (hotel.getRating() == null) {
            hotel.setRating(4.8);
        }
        if (hotel.getType() == null || hotel.getType().isBlank()) {
            hotel.setType("Premium");
        }
        return repo.save(hotel);
    }

    @PutMapping("/{id}")
    public Hotel update(@PathVariable Long id, @RequestBody Hotel body) {
        Hotel h = one(id);
        if (body.getName() != null) h.setName(body.getName());
        if (body.getLocation() != null) h.setLocation(body.getLocation());
        if (body.getPricePerNight() != null) h.setPricePerNight(body.getPricePerNight());
        if (body.getType() != null) h.setType(body.getType());
        if (body.getRating() != null) h.setRating(body.getRating());
        if (body.getImageUrl() != null) h.setImageUrl(body.getImageUrl());
        if (body.getDescription() != null) h.setDescription(body.getDescription());
        if (body.getAmenities() != null) h.setAmenities(body.getAmenities());
        return repo.save(h);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found with id: " + id);
        }
        repo.deleteById(id);
    }
}