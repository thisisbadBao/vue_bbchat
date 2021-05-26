package com.bbchat.controller;

import com.bbchat.common.annotation.WebResponse;
import com.bbchat.dao.dto.Source;
import com.bbchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author thisisbadBao
 * @Date 2021--26-11:52 AM
 */
@Controller
@CrossOrigin
@WebResponse
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    @ResponseBody
    public String getPastMessage(
            @RequestBody Source source
    ){
        String msg_source = source.getMsg_source();
        String[] messages = messageService.getMessage(msg_source);
        Date[] dates = messageService.getDate(msg_source);

        String returnMessage = "";
        SimpleDateFormat sim = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        for(int i = 0 ; i < messages.length ; i++){
            returnMessage += ("/"+messages[i]+"-"+sim.format(dates[i]));
        }

        return returnMessage;
    }
}
