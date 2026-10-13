package com.stayease.booking;
import com.stayease.hotel.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api/bookings")
public class BookingController {
 private final BookingRepository bookings; private final HotelRepository hotels;
 public BookingController(BookingRepository bookings,HotelRepository hotels){this.bookings=bookings;this.hotels=hotels;}
 @GetMapping public List<Booking> list(@RequestParam String email){return bookings.findByCustomerEmailIgnoreCaseOrderByIdDesc(email);}
 @PostMapping public Booking create(@RequestBody Booking b){
  if(b.getHotelId()==null||!hotels.existsById(b.getHotelId()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Choose a valid hotel.");
  if(b.getCustomerEmail()==null||b.getCustomerEmail().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer email is required.");
  if(b.getCheckIn()==null||b.getCheckOut()==null||!b.getCheckOut().isAfter(b.getCheckIn())||b.getCheckIn().isBefore(LocalDate.now()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Choose valid future check-in and check-out dates.");
  if(b.getGuests()==null||b.getGuests()<1||b.getGuests()>12)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Guests must be between 1 and 12.");
  b.setStatus("CONFIRMED");return bookings.save(b);
 }
 @DeleteMapping("/{id}") public void cancel(@PathVariable Long id,@RequestParam String email){
  Booking b=bookings.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found."));
  if(!b.getCustomerEmail().equalsIgnoreCase(email))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This booking belongs to another account.");
  bookings.delete(b);
 }
}