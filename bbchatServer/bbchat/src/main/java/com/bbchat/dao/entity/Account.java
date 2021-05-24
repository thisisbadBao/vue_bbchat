package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Account {
    @Id
    private String accountid;

    private String name;

    private String code;

    private String avatar;

    private Boolean able;

<<<<<<< HEAD
    private String type;

}

=======
}
>>>>>>> parent of 8b2d519 (add webSocket)
