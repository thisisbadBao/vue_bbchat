package com.bbchat.service;

import com.bbchat.dao.dto.Login;
import com.bbchat.dao.dto.Register;
import com.bbchat.dao.dto.UserInfo;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountService {
    Object registerUser(Register register);

    Object LoginUser(Login login);
}
