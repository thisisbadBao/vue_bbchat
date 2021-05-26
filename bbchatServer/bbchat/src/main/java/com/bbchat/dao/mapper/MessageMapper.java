package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Message;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
@Repository
public interface MessageMapper extends Mapper<Message> {
    @Select("SELECT context FROM message WHERE msg_source='${msg_source}' ORDER BY 'time' DESC")
    String[] getMessage(String msg_source);

    @Select("SELECT time FROM message WHERE msg_source='${msg_source}' ORDER BY 'time' DESC")
    Date[] getDate(String msg_source);

}
