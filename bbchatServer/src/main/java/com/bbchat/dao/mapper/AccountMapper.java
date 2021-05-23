package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Account;
import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(String accountid);

    int insert(Account record);

    Account selectByPrimaryKey(String accountid);

    List<Account> selectAll();

    int updateByPrimaryKey(Account record);
}