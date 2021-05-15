package com.badbao.bbchat.controller;

import com.badbao.bbchat.result.Result;
import com.badbao.bbchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.badbao.bbchat.pojo.User;

import java.security.MessageDigest;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            System.out.println("user does not exist");
            return new Result(400);   //密码错误或用户不存在
        } else {
            return new Result(200);   //密码与用户匹配
        }
    }
}
