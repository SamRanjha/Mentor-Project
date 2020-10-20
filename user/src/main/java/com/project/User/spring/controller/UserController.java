package com.project.User.spring.controller;


import com.project.User.spring.entity.User;
import com.project.User.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

class Status{
    long id;
    boolean active;
}

@RestController
@RequestMapping
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching technology with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping(value="/signup")
    public String createUser(@RequestBody User user){
        userService.createUser(user);
        return "Succesfully created " + user.getId();
    }


    @PutMapping(value="/update")
    public ResponseEntity<String> updateTech(@RequestBody User ctech)
    {
        User tech = userService.findById(ctech.getId());
        if (tech == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        userService.update(ctech);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/blockUser")
    public ResponseEntity<String> blockUser(@RequestBody Status s)
    {
        User user = userService.findById(s.id);
        if (user == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        userService.update(s.id,s.active);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
        System.out.println("Fetching technology with id " + id);
        User tech = userService.findById(id);
        if (tech == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        System.out.println("Successfully deleted technology with id " + id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
