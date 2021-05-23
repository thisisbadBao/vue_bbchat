package com.bbchat.dao.mapper;

import com.bbchat.dao.entity.Message;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface MessageMapper extends Mapper<Message> {

}