package com.abi.hotel.HotelService.services;

import com.abi.hotel.HotelService.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel createHotel(Hotel hotel);
    List<Hotel> getAllHotel();
    Hotel get(String id);
}
