
package com.adc.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.adc.eshop.common.Constants;
import com.adc.eshop.common.ServiceResultEnum;
import com.adc.eshop.controller.vo.UserVO;
import com.adc.eshop.dao.UserMapper;
import com.adc.eshop.entity.User;
import com.adc.eshop.service.UserService;
import com.adc.eshop.util.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult getUsersPage(PageQueryUtil pageUtil) {
        List<User> users = userMapper.findUserList(pageUtil);
        int total = userMapper.getTotalUsers(pageUtil);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (userMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (userMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        User user = userMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
//            if (user.getNickName() != null && user.getNickName().length() > 7) {
//                String tempNickName = user.getNickName().substring(0, 7) + "..";
//                user.setNickName(tempNickName);
//            }
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);

            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, userVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public UserVO updateUserInfo(UserVO user, HttpSession httpSession) {
        UserVO userTemp = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        User userFromDB = userMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (user.getPassword()!=null && user.getNewPassword()!=null) {
            	String originalPasswordMd5 = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
                String newPasswordMd5 = MD5Util.MD5Encode(user.getNewPassword(), "UTF-8");
                if (originalPasswordMd5.equals(userFromDB.getPasswordMd5())) {
                    userFromDB.setPasswordMd5(newPasswordMd5);
                }
            }
            if (!StringUtils.isEmpty(user.getNickName())) {
                userFromDB.setNickName(Utils.cleanString(user.getNickName()));
            }
            if (!StringUtils.isEmpty(user.getAddress())) {
                userFromDB.setAddress(Utils.cleanString(user.getAddress()));
            }
            if (!StringUtils.isEmpty(user.getIntroduceSign())) {
                userFromDB.setIntroduceSign(Utils.cleanString(user.getIntroduceSign()));
            }
            if (userMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                UserVO userVO = new UserVO();
                userFromDB = userMapper.selectByPrimaryKey(user.getUserId());
                BeanUtil.copyProperties(userFromDB, userVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, userVO);
                return userVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return userMapper.lockUserBatch(ids, lockStatus) > 0;
    }

	@Override
	public Integer adminUpdateUserInfo(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}
}
