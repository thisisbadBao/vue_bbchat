package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Message;
import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(String accountid);

    int insert(Message record);

    Message selectByPrimaryKey(String accountid);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}