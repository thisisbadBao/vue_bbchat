package com.bbchat.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountId;

    private String name;

    private String code;

    private String avatar;

    private Boolean able;

    private String type;

}
