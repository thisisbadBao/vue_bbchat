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
@RequestMapping("/api")
@WebResponse
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/message")//localhost:xxx/message
    @ResponseBody
    public String getPastMessage(
            @RequestBody Source source//只要一个房间号
    ){
        String msg_source = source.getMsg_source();
        String[] messages = messageService.getMessage(msg_source);
        Date[] dates = messageService.getDate(msg_source);
        String[] names = messageService.getName(msg_source);

        String returnMessage = "";
        SimpleDateFormat sim = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        for(int i = 0 ; i < messages.length ; i++){ 
            if (i == 0) returnMessage += (names[i]+": "+messages[i]+"-"+sim.format(dates[i]));
            else returnMessage += ("/"+names[i]+": "+messages[i]+"-"+sim.format(dates[i]));
        }

        return returnMessage;
    }
}
