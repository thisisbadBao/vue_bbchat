package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Account;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AccountMapper extends Mapper<Account> {

    @Select("SELECT * FROM account where name='${name}'")
    Account getByName(String name);
}