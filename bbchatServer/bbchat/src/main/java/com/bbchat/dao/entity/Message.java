package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class Message {

    private String name;

    private Date time;

    private String context;

    private int msg_source;

}
