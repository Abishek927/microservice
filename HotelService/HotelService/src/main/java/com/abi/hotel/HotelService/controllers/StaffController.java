package com.abi.hotel.HotelService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> getAllStaffs(){
        List<String> staffs=Arrays.asList("Abishek","Amrit");
        return  ResponseEntity.status(HttpStatus.OK).body(staffs);
    }


}
