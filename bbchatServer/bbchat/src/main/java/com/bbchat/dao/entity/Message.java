package com.bbchat.dao.entity;

import lombok.Data;
import java.util.Date;
@Data
public class Message {

    private String name;

    private Date time;

    private String context;

    private String msg_source;

}
