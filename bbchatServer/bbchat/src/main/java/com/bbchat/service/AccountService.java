package com.bbchat.service;

import com.bbchat.dao.dto.*;
import com.bbchat.dao.entity.Account;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountService {
    Object registerUser(Register register);

    Object LoginUser(Login login);

    Account getUser(String name);

    Object stopUser(Admin admin);

    Object enableUser(Admin admin);

    Object changeName(User user);

    Object changeCode(Code code);
}
