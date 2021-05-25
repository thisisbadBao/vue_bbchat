package com.bbchat.controller;

import com.bbchat.common.annotation.WebResponse;
import com.bbchat.dao.dto.Admin;
import com.bbchat.dao.dto.Login;
import com.bbchat.dao.dto.Register;
import com.bbchat.dao.dto.UserInfo;
import com.bbchat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@Controller
@CrossOrigin
@RequestMapping("/api")
@WebResponse
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @ResponseBody
    public Object register(
            @RequestBody Register register
    ){
        return accountService.registerUser(register);
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(
            @RequestBody Login login
    ){
        return accountService.LoginUser(login);
    }

    //管理员禁言
    @PostMapping("/stop")
    @ResponseBody
    public Object stop(
            @RequestBody Admin admin
    ){
        return accountService.stopUser(admin);
    }

    //管理员解除禁言
    @PostMapping("/enable")
    @ResponseBody
    public Object enable(
            @RequestBody Admin admin
    ){
        return accountService.enableUser(admin);
    }
}
