package com.abi.hotel.HotelService.services.impl;

import com.abi.hotel.HotelService.entities.Hotel;
import com.abi.hotel.HotelService.exception.ResourceNotFoundException;
import com.abi.hotel.HotelService.repo.HotelRepo;
import com.abi.hotel.HotelService.services.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepo hotelRepo;

    public HotelServiceImpl(HotelRepo hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel get(String id) {

        return hotelRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel with the given id not found!!!!"));
    }
}
