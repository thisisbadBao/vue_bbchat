package com.badbao.bbchat.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @author thisisbadBao
 * @Date 2021--13-8:01 PM
 */

@Proxy(lazy=false)
@Entity
@Table(name = "account")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "accountId", length = 10, nullable = false)
    int id;

    //@Column(name = "name",length = 10, nullable = false)
    String username;

    //@Column(name = "code", length = 50, nullable = false)
    String password;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
