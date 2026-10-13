package com.stayease.booking;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="bookings")
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private Long hotelId; private String customerEmail; private LocalDate checkIn; private LocalDate checkOut; private Integer guests; private String status="CONFIRMED";
 public Booking(){}
 public Long getId(){return id;} public Long getHotelId(){return hotelId;} public String getCustomerEmail(){return customerEmail;} public LocalDate getCheckIn(){return checkIn;} public LocalDate getCheckOut(){return checkOut;} public Integer getGuests(){return guests;} public String getStatus(){return status;}
 public void setId(Long id){this.id=id;} public void setHotelId(Long v){hotelId=v;} public void setCustomerEmail(String v){customerEmail=v;} public void setCheckIn(LocalDate v){checkIn=v;} public void setCheckOut(LocalDate v){checkOut=v;} public void setGuests(Integer v){guests=v;} public void setStatus(String v){status=v;}
}