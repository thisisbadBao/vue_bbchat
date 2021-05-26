package com.bbchat.service;

import com.bbchat.dao.entity.Room;
import com.bbchat.dao.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thisisbadBao
 * @Date 2021--25-11:31 PM
 */
@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public String[] getUserOfRoom(String roomId){
        return roomMapper.getUserOfRoom(roomId);
    }

    @Override
    public void insert(Room room) {
        roomMapper.insert(room);
    }

    @Override
    public void remove(Room room) {
        roomMapper.delete(room);
    }
}
