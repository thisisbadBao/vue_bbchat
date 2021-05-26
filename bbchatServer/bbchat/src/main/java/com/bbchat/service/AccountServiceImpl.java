package com.bbchat.service;

import com.bbchat.common.CONST;
import com.bbchat.common.ConstantUtil;
import com.bbchat.dao.dto.*;
import com.bbchat.dao.entity.Account;
import com.bbchat.dao.mapper.AccountMapper;
import com.bbchat.exception.BizException;
import com.bbchat.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Object registerUser(Register register) {
        Account account= new Account();
        //生成8位的有重复数字的用户账号
        String accountId = getRepeatableVerifyNumberCode(8);
        account.setAccountid(accountId);
        account.setName(register.getName());
        account.setCode(register.getCode());
        account.setAble(true);
        if(register.getAvatar()==null || register.getAvatar().equals("")){
            account.setAvatar(CONST.avatar);
        }else{
            account.setAvatar(register.getAvatar());
        }
        if(accountMapper.getOneAccountByName(account.getName())!=null){
            System.out.println("已经存在该用户");
            return new Result(3);
        }

        accountMapper.insertSelective(account);
        return accountId;//返回注册成功的生成的账号
    }

    public static String getRepeatableVerifyNumberCode(int len) {
        // 生成n位可重复数字串
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; ++i) {
            stringBuilder.append(random.nextInt(65535) % 10);
        }
        return stringBuilder.toString();
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

    @Override
    public Account getUser(String name){
        return accountMapper.getOneAccountByName(name);
    }

    @Override
    public Object stopUser(Admin admin){
        String from_who = admin.getFrom_who();
        String to_who = admin.getTo_who();
        Account from_account = accountMapper.getOneAccountByName(from_who);
        Account to_account = accountMapper.getOneAccountByName(to_who);

        if(to_account==null){
            return new Result(10);//没有要禁言的用户存在
        }

        if(from_account.getType().equals("user")){
            return new Result(4);//返回错误信息，当前账号权限不够
        }

        if(to_account.getType().equals("admin")){
            return new Result(5);//返回错误信息，无法对管理员操作
        }

        if(!to_account.getAble()){
            return new Result(6);//返回错误信息，该用户已被禁言
        }

        accountMapper.stopMessage(to_who);
        return new Result(7);//返回禁言成功的提示消息

    }

    @Override
    public Object enableUser(Admin admin){
        String from_who = admin.getFrom_who();
        String to_who = admin.getTo_who();
        Account from_account = accountMapper.getOneAccountByName(from_who);
        Account to_account = accountMapper.getOneAccountByName(to_who);

        if(to_account==null){
            return new Result(10);//没有要禁言的用户存在
        }

        if(from_account.getType().equals("user")){
            return new Result(4);//返回错误信息，当前账号权限不够
        }

        if(to_account.getType().equals("admin")){
            return new Result(5);//返回错误信息，无法对管理员操作
        }

        if(to_account.getAble()){
            return new Result(8);//返回错误信息，该用户能够发言
        }

        accountMapper.enableMessage(to_who);
        return new Result(9);//返回解除禁言成功的提示消息
    }

    @Override
    public Object changeName(User user){
        if(user.getNew_name().equals(user.getOld_name())){
            return new Result(11);//提示修改前后的用户名一致
        }

        if(accountMapper.getOneAccountByName(user.getNew_name()) != null){
            return new Result(15);//无法修改为已存在的用户名
        }

        accountMapper.changeName(user.getNew_name(),user.getOld_name());

        return new Result(12);//修改用户名成功
    }

    @Override
    public Object changeCode(Code code){
        if(!code.getOld_code().equals(accountMapper.getOneAccountByName(code.getName()).getCode())){
            return new Result(13);//密码错误，无法修改
        }

        accountMapper.changeCode(code.getNew_code(),code.getName());

        return new Result(14);//修改密码成功
    }
}
