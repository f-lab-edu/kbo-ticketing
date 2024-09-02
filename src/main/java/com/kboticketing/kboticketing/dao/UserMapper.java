package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface UserMapper {

    void insert(User user);

    User selectByEmail(String email);
}
