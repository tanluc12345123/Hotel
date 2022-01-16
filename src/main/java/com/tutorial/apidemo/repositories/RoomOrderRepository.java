package com.tutorial.apidemo.repositories;



import com.tutorial.apidemo.models.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomOrderRepository extends JpaRepository<RoomOrder, Integer> {
    List<RoomOrder> findByRoomId(int roomlId);
}
