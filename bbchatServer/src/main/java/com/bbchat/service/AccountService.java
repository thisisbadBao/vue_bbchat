package com.bbchat.service;

import com.bbchat.dao.dto.Register;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AccountService {
    void registerUser(Register register);

    Object LoginUser(String accountId,String password);
}
