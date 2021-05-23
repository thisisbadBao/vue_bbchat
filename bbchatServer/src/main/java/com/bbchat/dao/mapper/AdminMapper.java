package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Admin;
import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(Admin record);

    Admin selectByPrimaryKey(String id);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
}