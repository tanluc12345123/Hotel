package com.tutorial.apidemo.controllers;


import com.tutorial.apidemo.models.Hotel;
import com.tutorial.apidemo.models.Location;
import com.tutorial.apidemo.models.ResponseObject;
import com.tutorial.apidemo.repositories.HotelRepository;
import com.tutorial.apidemo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class HotelController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/locations/{locationId}/hotels")
    public List<Hotel> getHotelsByLocation(@PathVariable(value = "locationId") int locationId) {
        return hotelRepository.findByLocationId(locationId);
    }

    @PostMapping("/locations/{locationId}/hotels")
    ResponseEntity<ResponseObject> createHotel(@PathVariable(value = "locationId") int locationId, @RequestBody Hotel newHotel){
        Optional<Location> foundLocationId = locationRepository.findById(locationId);
        if(foundLocationId.isPresent()){
            foundLocationId.map(location -> {
                newHotel.setLocation(location);
                return hotelRepository.save(newHotel);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query location successfully",foundLocationId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }
    }

    @PutMapping("/locations/{locationId}/hotels/{hotelId}")
    ResponseEntity<ResponseObject> updateHotel(@PathVariable(value = "locationId") int locationId,
                               @PathVariable(value = "hotelId") int hotelId,@RequestBody Hotel hotelRequest) {
        if (!locationRepository.existsById(locationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }else{
            Hotel updateHotel = hotelRepository.findById(hotelId).map(hotel -> {
                hotel.setAddress(hotelRequest.getAddress());
                hotel.setHotel_name(hotelRequest.getHotel_name());
                hotel.setPhone(hotelRequest.getPhone());
                hotel.setContent(hotelRequest.getContent());
                hotel.setRate(hotelRequest.getRate());
                hotel.setImage(hotelRequest.getImage());
                return hotelRepository.save(hotel);
            }).orElseGet(()->{
                hotelRequest.setId(hotelId);
                return hotelRepository.save(hotelRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Hotel Successfully", updateHotel)
            );
        }
    }
    @DeleteMapping("/locations/{locationId}/hotels/{hotelId}")
    ResponseEntity<ResponseObject> deleteHotel(@PathVariable(value = "locationId") int locationId,
                                                 @PathVariable(value = "hotelId") int hotelId){
        if (locationRepository.findById(locationId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }else{
            boolean exists = hotelRepository.existsById(hotelId);
            if(exists){
                hotelRepository.deleteById(hotelId);
                return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok","Delete Hotel Successfully", "")
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed","Cannot find hotel to delete", "")
                );
            }
        }
    }
}
