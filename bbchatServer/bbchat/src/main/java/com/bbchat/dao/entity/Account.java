package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Account {
<<<<<<< HEAD
    private String accountId;
=======
    @Id
    private String accountid;
>>>>>>> parent of 8b2d519 (add webSocket)

    private String name;

    private String code;

    private String avatar;

    private Boolean able;

}