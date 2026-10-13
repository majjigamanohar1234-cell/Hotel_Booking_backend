package com.stayease;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.stayease.hotel.Hotel;
import com.stayease.hotel.HotelRepository;
@SpringBootApplication
public class HotelBookingApplication {
 public static void main(String[] args){SpringApplication.run(HotelBookingApplication.class,args);}
 @Bean WebMvcConfigurer corsConfigurer(){return new WebMvcConfigurer(){@Override public void addCorsMappings(CorsRegistry r){r.addMapping("/api/**").allowedOrigins("http://localhost:5173").allowedMethods("GET","POST","PUT","DELETE","OPTIONS").allowedHeaders("*");}};}
 @Bean CommandLineRunner seedHotels(HotelRepository repo){return args->{if(repo.count()==0){repo.save(new Hotel("The Leela Palace","Bengaluru, India",12400.0,"Luxury","A refined escape with grand gardens and exceptional service."));repo.save(new Hotel("Taj Deccan","Hyderabad, India",7800.0,"Premium","A calm city retreat close to Hyderabad's best attractions."));repo.save(new Hotel("Coastal Haven Resort","Goa, India",6400.0,"Resort","Slow mornings, sea breeze, and relaxed resort living."));repo.save(new Hotel("The Fern Residency","Jaipur, India",4900.0,"Boutique","Contemporary comfort with warm hospitality."));}};}
}