package com.adc.eshop.dao.repository;


import com.adc.eshop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("select * from tb_user where login_name = #{loginName} and password_md5 = #{passwordMd5} ")
    public User findUsernameAndPassword(String loginName, String passwordMd5);

}
