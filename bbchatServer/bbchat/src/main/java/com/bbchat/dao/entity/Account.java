package com.bbchat.dao.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Account {
<<<<<<< HEAD

    private String accountId;

=======
>>>>>>> parent of 8b2d519 (add webSocket)
    @Id
    private String accountid;

    private String name;

    private String code;

    private String avatar;

    private Boolean able;

<<<<<<< HEAD
}
=======
}
>>>>>>> parent of 8b2d519 (add webSocket)
