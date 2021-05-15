package com.badbao.bbchat.dao;

import com.badbao.bbchat.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author thisisbadBao
 * @Date 2021--14-4:24 PM
 */
public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User getByUsernameAndPassword(String username,String password);
}
