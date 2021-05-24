package com.bbchat.service;

import com.bbchat.common.CONST;
import com.bbchat.common.ConstantUtil;
import com.bbchat.dao.dto.Login;
import com.bbchat.dao.dto.Register;
import com.bbchat.dao.dto.UserInfo;
import com.bbchat.dao.entity.Account;
import com.bbchat.dao.mapper.AccountMapper;
import com.bbchat.exception.BizException;
import com.bbchat.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Object registerUser(Register register) {
        Account account= new Account();
        account.setAccountid(register.getAccountId());
        account.setName(register.getName());
        account.setCode(register.getCode());
        account.setAble(true);
        if(register.getAvatar()==null || register.getAvatar().equals("")){
            account.setAvatar(CONST.avatar);
        }else{
            account.setAvatar(register.getAvatar());
        }
        if(accountMapper.selectByPrimaryKey(account)!=null){
            System.out.println("已经存在该用户");
            return new Result(4);
        }

        accountMapper.insertSelective(account);
        return new Result(3);//返回注册成功的提示
    }

    @Override
    public Object LoginUser(Login login) {
        String accountId = login.getAccountId();
        String code = login.getCode();
        Account account = new Account();
        account.setAccountid(accountId);
        if(accountMapper.selectByPrimaryKey(account)==null){
            System.out.println("没有该用户");
            return new Result(1);

        }
        if(!code.equals(accountMapper.selectByPrimaryKey(account).getCode())){
            System.out.println("密码错误");
            return new Result(2);
        }

        account.setName(accountMapper.selectByPrimaryKey(account).getName());
        account.setAvatar(accountMapper.selectByPrimaryKey(account).getAvatar());

        return BuildUserInfo(account);
    }

    private UserInfo BuildUserInfo(Account account) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(account.getAvatar());
        userInfo.setName(account.getName());
        return userInfo;
    }
}
