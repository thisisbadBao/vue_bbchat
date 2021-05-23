package com.bbchat.service;

import com.bbchat.common.CONST;
import com.bbchat.common.ConstantUtil;
import com.bbchat.dao.dto.Register;
import com.bbchat.dao.dto.UserInfo;
import com.bbchat.dao.entity.Account;
import com.bbchat.dao.mapper.AccountMapper;
import com.bbchat.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void registerUser(Register register) {
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
            throw new BizException(ConstantUtil.BizExceptionCause.HAVE_REGISTER);
        }

        accountMapper.insertSelective(account);
    }

    @Override
    public Object LoginUser(String accountId,String password) {
        Account account = new Account();
        account.setAccountid(accountId);
        if(accountMapper.selectByPrimaryKey(account)==null){
            throw new BizException(ConstantUtil.BizExceptionCause.NO_USER);
        }
        String code = accountMapper.selectByPrimaryKey(account).getCode();
        if(!code.equals(password)){
            throw new BizException(ConstantUtil.BizExceptionCause.WRONG_CODE);
        }

        return BuildUserInfo(account);
    }

    private UserInfo BuildUserInfo(Account account) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(account.getAvatar());
        userInfo.setName(account.getName());
        return userInfo;
    }
}
