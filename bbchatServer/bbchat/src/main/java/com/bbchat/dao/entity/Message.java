package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class Message {
    @Id
    private String accountid;

    private Date time;

    private String context;

}