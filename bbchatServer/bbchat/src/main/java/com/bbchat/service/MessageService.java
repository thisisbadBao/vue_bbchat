package com.bbchat.service;


import com.bbchat.dao.entity.Message;
import org.springframework.validation.annotation.Validated;

/**
 * @author thisisbadBao
 * @Date 2021--25-12:04 PM
 */
@Validated
public interface MessageService {

    void insertMessage(Message message);
}
