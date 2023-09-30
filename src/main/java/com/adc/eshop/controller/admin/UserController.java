
package com.adc.eshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.adc.eshop.entity.User;
import com.adc.eshop.service.UserService;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.Result;
import com.adc.eshop.util.ResultGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

 
@Controller
@RequestMapping("/admin")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/user";
    }

     
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("Abnormal parameter！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(userService.getUsersPage(pageUtil));
    }

     
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("Abnormal parameter！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("Illegal activities！");
        }
        if (userService.lockUsers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("Disable failure !");
        }
    }

    @PutMapping(value = "/users/update")
    @ResponseBody
    public Result update(HttpServletRequest request) {
    	User user = new User();
    	user.setUserId(Long.valueOf(request.getParameter("userId")));
    	user.setNickName(request.getParameter("nickName"));
    	user.setAddress(request.getParameter("address"));
    	user.setIntroduceSign(request.getParameter("introduceSign"));
        userService.adminUpdateUserInfo(user);
        return ResultGenerator.genSuccessResult();
    }
}