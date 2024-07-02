package com.example.vcc_task5.controller;

import com.example.vcc_task5.entity.User;
import com.example.vcc_task5.payload.CommonResponse;
import com.example.vcc_task5.service.UserService;
import com.example.vcc_task5.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse<User>> add(@RequestBody User user) throws Exception {
        return ResponseUtil.wrapResponse(
                userService.add(user),
                "Added user successfully",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<User>>> getAll() {
        return ResponseUtil.wrapResponse(
                userService.getAll(),
                "Got all user successfully",
                true,
                HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Integer id) throws Exception {
        userService.delete(id);
        return ResponseUtil.wrapResponse(
                null,
                "Deleted user successfully",
                true,
                HttpStatus.OK.value());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<User>> update(@PathVariable Integer id, @RequestBody User user) throws Exception {
        return ResponseUtil.wrapResponse(
                userService.update(id, user),
                "Updated user successfully",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping("/sort")
    public ResponseEntity<CommonResponse<List<User>>> sortByName() {
        return ResponseUtil.wrapResponse(
                userService.sortByName(),
                "Sorted user by name successfully",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping("/searchByName")
    public ResponseEntity<CommonResponse<List<User>>> findByName(@RequestParam("name") String name) {
        return ResponseUtil.wrapResponse(
                userService.findByName(name),
                "Found user by name successfully",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping("/searchById")
    public ResponseEntity<CommonResponse<List<User>>> findById(@RequestParam("id") Integer id) {
        return ResponseUtil.wrapResponse(
                userService.findById(id),
                "Found user by id successfully",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping("/searchByAddress")
    public ResponseEntity<CommonResponse<List<User>>> findByAddress(@RequestParam("address") String address) {
        return ResponseUtil.wrapResponse(
                userService.findByAddress(address),
                "Found user by address successfully",
                true,
                HttpStatus.OK.value());
    }
}
