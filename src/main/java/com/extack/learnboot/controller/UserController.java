package com.extack.learnboot.controller;

import com.extack.learnboot.model.*;
import com.extack.learnboot.repository.UsersRepository;
import com.extack.learnboot.security.JpaUserDetailsService;
import com.extack.learnboot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    UsersRepository repository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/user")
    public ResponseEntity<Resource> getUser(@RequestParam(value = "id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(new ErrorResource<String>().error("ID must not be null"), HttpStatus.BAD_REQUEST);
        }
        Optional<Users> user = repository.findById(id);
        return user
                .map(users -> new ResponseEntity<>(new SingleResource<Users>().success(users), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ErrorResource<String>().error("User not found"), HttpStatus.NOT_FOUND));

    }

    @GetMapping("/username")
    public ResponseEntity<Resource> getUserByName(@RequestParam(value = "name") String name) {
        if (name == null) {
            return new ResponseEntity<>(new ErrorResource<String>().error("ID must not be null"), HttpStatus.BAD_REQUEST);
        }
        Optional<Users> user = repository.findUsersByName(name);
        return user
                .map(users -> new ResponseEntity<>(new SingleResource<Users>().success(users), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ErrorResource<String>().error("User not found"), HttpStatus.NOT_FOUND));

    }

    @GetMapping("/login")
    public Optional<Users> getUserByAuth(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password
    ) {
        return repository.findByNameAndPassword(name, password);
    }

    @GetMapping("/users")
    public ResponseEntity<Resource> getAllUsers() {
        return new ResponseEntity<>(
                new ListResource<Users>().success((List<Users>) repository.findAll()),
                HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody AuthRequest authRequest){
        userDetailsService.createUser(new Users(authRequest.getUsername(),authRequest.getPassword(),"USER"));
        return ResponseEntity.ok("User created");
    }

}
