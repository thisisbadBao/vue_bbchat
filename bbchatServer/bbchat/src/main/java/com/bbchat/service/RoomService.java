package com.bbchat.service;

import com.bbchat.dao.entity.Room;
import org.springframework.validation.annotation.Validated;

/**
 * @author thisisbadBao
 * @Date 2021--25-11:31 PM
 */
@Validated
public interface RoomService {
    String[] getUserOfRoom(String roomId);

    void insert(Room room);

    void remove(Room room);
}
