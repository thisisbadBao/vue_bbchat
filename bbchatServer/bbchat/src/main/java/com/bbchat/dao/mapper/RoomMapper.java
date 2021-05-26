package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Room;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface RoomMapper extends Mapper<Room> {
    @Select("SELECT name FROM room WHERE roomId='${roomId}'")
    String[] getUserOfRoom(String roomId);

}
