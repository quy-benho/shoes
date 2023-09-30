
package com.adc.eshop.service;

import javax.servlet.http.HttpSession;

import com.adc.eshop.controller.vo.UserVO;
import com.adc.eshop.entity.User;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

public interface UserService {
     
    PageResult getUsersPage(PageQueryUtil pageUtil);

     
    String register(String loginName, String password);

     
    String login(String loginName, String passwordMD5, HttpSession httpSession);

     
    UserVO updateUserInfo(UserVO user, HttpSession httpSession);


    Boolean lockUsers(Integer[] ids, int lockStatus);


    Integer adminUpdateUserInfo(User user);
}
