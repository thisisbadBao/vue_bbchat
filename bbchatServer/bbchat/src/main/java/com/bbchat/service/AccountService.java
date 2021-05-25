package com.bbchat.service;

import com.bbchat.dao.dto.Admin;
import com.bbchat.dao.dto.Login;
import com.bbchat.dao.dto.Register;
import com.bbchat.dao.entity.Account;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountService {
    Object registerUser(Register register);

    Object LoginUser(Login login);

    Account getUser(String name);

    Object stopUser(Admin admin);

    Object enableUser(Admin admin);
}
