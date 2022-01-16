package com.tutorial.apidemo.controllers;


import com.tutorial.apidemo.models.Hotel;
import com.tutorial.apidemo.models.ResponseObject;
import com.tutorial.apidemo.models.Room;
import com.tutorial.apidemo.models.RoomOrder;
import com.tutorial.apidemo.repositories.RoomOrderRepository;
import com.tutorial.apidemo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class RoomOrderController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomOrderRepository roomOrderRepository;

    @GetMapping("/rooms/{roomId}/room_orders")
    public List<RoomOrder> getRoomOrdersByHotel(@PathVariable(value = "roomId") int roomId) {
        return roomOrderRepository.findByRoomId(roomId);
    }

    @PostMapping("/hotels/{hotelId}/rooms")
    ResponseEntity<ResponseObject> createRoom(@PathVariable(value = "hotelId") int hotelId, @RequestBody Room newRoom){
        Optional<Hotel> foundHotelId = hotelRepository.findById(hotelId);
        if(foundHotelId.isPresent()){
            foundHotelId.map(location -> {
                newRoom.setHotel(location);
                return roomRepository.save(newRoom);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query room successfully",foundHotelId)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }
    }

    @PutMapping("/hotels/{hotelId}/rooms/{roomId}")
    ResponseEntity<ResponseObject> updateRoom(@PathVariable(value = "hotelId") int hotelId,
                                              @PathVariable(value = "roomId") int roomId,@RequestBody Room roomRequest) {
        if (!hotelRepository.existsById(hotelId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find hotel", "")
            );
        }else{
            Room updateRoom = roomRepository.findById(roomId).map(room -> {
                room.setRoom_name(roomRequest.getRoom_name());
                room.setPrice(roomRequest.getPrice());
                room.setStatus(roomRequest.isStatus());
                room.setContent(roomRequest.getContent());
                room.setService(roomRequest.getService());
                return roomRepository.save(room);
            }).orElseGet(()->{
                roomRequest.setId(roomId);
                return roomRepository.save(roomRequest);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Update Room Successfully", updateRoom)
            );
        }
    }
    @DeleteMapping("/hotels/{hotelId}/rooms/{roomId}")
    ResponseEntity<ResponseObject> deleterRoom(@PathVariable(value = "hotelId") int hotelId,
                                               @PathVariable(value = "roomId") int roomId){
        if (!hotelRepository.findById(hotelId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find location", "")
            );
        }else{
            boolean exists = roomRepository.existsById(roomId);
            if(exists){
                roomRepository.deleteById(roomId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Delete Room Successfully", "")
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed","Cannot find room to delete", "")
                );
            }
        }
    }
}
