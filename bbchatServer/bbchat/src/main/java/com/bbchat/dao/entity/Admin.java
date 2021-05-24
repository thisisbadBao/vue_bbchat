package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Admin {
    @Id
    private String id;

    private String phone;

    private String name;

}