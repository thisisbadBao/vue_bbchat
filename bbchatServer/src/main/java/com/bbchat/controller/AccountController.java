package com.bbchat.controller;

import com.bbchat.common.annotation.WebResponse;
import com.bbchat.dao.dto.Register;
import com.bbchat.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
        accountService.registerUser(register);
        return "success";
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(
            String accountId,String password
            ){
        return accountService.LoginUser(accountId,password);
    }
}
