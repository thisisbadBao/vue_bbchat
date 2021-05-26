package com.bbchat.service;


import com.bbchat.dao.entity.Message;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * @author thisisbadBao
 * @Date 2021--25-12:04 PM
 */
@Validated
public interface MessageService {

    void insertMessage(Message message);

    String[] getMessage(String msg_source);

    Date[] getDate(String msg_source);

    String[] getName(String msg_source);
}
