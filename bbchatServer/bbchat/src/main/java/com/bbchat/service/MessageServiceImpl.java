package com.bbchat.service;

import com.bbchat.dao.entity.Message;
import com.bbchat.dao.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thisisbadBao
 * @Date 2021--25-12:04 PM
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void insertMessage(Message message){
        messageMapper.insert(message);
    }
}
