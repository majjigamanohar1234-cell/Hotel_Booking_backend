package com.stayease.user;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public AuthController(UserRepository users){this.users=users;}
 @PostMapping("/register") public Map<String,Object> register(@RequestBody Map<String,String> body){
  String name=body.getOrDefault("name","Guest").trim(), email=body.getOrDefault("email","").trim().toLowerCase(), password=body.getOrDefault("password","");
  if(email.isBlank()||password.length()<4)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Enter a valid email and password (minimum 4 characters).");
  if(users.existsByEmailIgnoreCase(email))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered.");
  AppUser u=users.save(new AppUser(name,email,encoder.encode(password)));return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());
 }
 @PostMapping("/login") public Map<String,Object> login(@RequestBody Map<String,String> body){
  String email=body.getOrDefault("email","").trim().toLowerCase(), password=body.getOrDefault("password","");
  AppUser u=users.findByEmailIgnoreCase(email).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password."));
  if(!encoder.matches(password,u.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password.");
  return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());
 }
}