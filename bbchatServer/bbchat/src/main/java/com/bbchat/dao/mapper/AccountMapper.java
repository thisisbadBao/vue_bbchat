package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AccountMapper extends Mapper<Account> {

    @Select("SELECT * FROM account where name='${name}'")
    Account getOneAccountByName(String name);

    @Update("UPDATE account SET able=FALSE WHERE name='${name}'")
    void stopMessage(String name);

    @Update("UPDATE account SET able=TRUE WHERE name='${name}'")
    void enableMessage(String name);

}
