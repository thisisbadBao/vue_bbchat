package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Account {

    private String accountId;

    @Id
    private String accountid;

    private String name;

    private String code;

    private String avatar;

    private Boolean able;

}
