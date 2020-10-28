package com.project.User.spring.controller;


import com.project.User.spring.entity.User;
import com.project.User.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        log.info("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            log.error("User with id: " + id + " does not exists.");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping(value="/signup")
    public String createUser(@RequestBody User user){
        log.info("Creating User with id " + user.getId());
        userService.createUser(user);
        return "Succesfully created " + user.getId();
    }

    @GetMapping(value = "/getRole/{id}")
    public ResponseEntity<String> getRole(@PathVariable("id") long id){
        log.info("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            log.error("User with id: " + id + " does not exists.");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        log.info("Role of user with id " + id + " is " + user.getType());
        return new ResponseEntity<String>(user.getType(),HttpStatus.OK);
    }

    @GetMapping(value = "/getUserName/{id}")
    public ResponseEntity<String> getUserName(@PathVariable("id") long id){
        log.info("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            log.error("User with id: " + id + " does not exists.");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        log.info("Role of user with id " + id + " is " + user.getType());
        return new ResponseEntity<String>(user.getUsername(),HttpStatus.OK);
    }

    @PutMapping(value="/update")
    public ResponseEntity<String> updateUser(@RequestBody User cuser)
    {
        log.info("Fetching User with id " + cuser.getId());
        User tech = userService.findById(cuser.getId());
        if (tech == null) {
            log.error("User with id: " + cuser.getId() + " does not exists.");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        userService.update(cuser);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/blockUser")
    public ResponseEntity<String> blockUser(@RequestBody Status s)
    {
        log.info("Fetching User with id " + s.id);
        User user = userService.findById(s.id);
        if (user == null) {
            log.error("User with id: " + s.id + " does not exists.");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        userService.update(s.id,s.active);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
        log.info("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            log.error("User with id: " + id + " does not exists.");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        log.info("Successfully deleted User with id " + id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
