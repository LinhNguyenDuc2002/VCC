package com.example.vcc_task4.controller;

import com.example.vcc_task4.entity.User;
import com.example.vcc_task4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User add(@RequestBody User user) throws Exception {
        return userService.add(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @RequestBody User user) throws Exception {
        return userService.update(id, user);
    }

    @GetMapping("/sort")
    public List<User> sortByName() {
        return userService.sortByName();
    }

    @GetMapping("/searchByName")
    public List<User> findByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }

    @GetMapping("/searchById")
    public List<User> findById(@RequestParam("id") Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/searchByAddress")
    public List<User> findByAddress(@RequestParam("address") String address) {
        return userService.findByAddress(address);
    }
}
