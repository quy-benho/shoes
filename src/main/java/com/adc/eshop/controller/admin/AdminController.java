
package com.adc.eshop.controller.admin;

import com.adc.eshop.controller.vo.OrderCustomVO;
import com.adc.eshop.controller.vo.ProductBestSeller;
import com.adc.eshop.controller.vo.ReportDashBoard;
import com.adc.eshop.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.adc.eshop.common.ServiceResultEnum;
import com.adc.eshop.entity.AdminUser;
import com.adc.eshop.service.AdminUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private OrderService orderService;

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @GetMapping({"/test"})
    public String test() {
        return "admin/test";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "start_date", required = false, defaultValue = "") String startDate,
                        @RequestParam(value = "end_date", required = false, defaultValue = "") String endDate
    ) {
        Map<String, Integer> graphData = new TreeMap<>();
        List<OrderCustomVO> list = orderService.getTotalByDate(startDate, endDate);
        for(OrderCustomVO od : list){
            graphData.put(od.getDateOrder(), od.getSumPrice());
        }
        ReportDashBoard reportDashBoard = orderService.getReportDashBoard();
        List<ProductBestSeller> productBestSellers = orderService.getProductBestSeler();
        int count = 1;
        for (ProductBestSeller productBestSeller : productBestSellers){
            productBestSeller.setId(count);
            count ++;
        }

        model.addAttribute("chartData", graphData);
        model.addAttribute("reportDashBoard", reportDashBoard);
        model.addAttribute("productBestSellers", productBestSellers);
        request.setAttribute("path", "index");
        return "admin/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "Verify code none!");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "Username or Password can't be null");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode.toLowerCase())) {
            session.setAttribute("errorMsg", "Verify fail !");
            return "admin/login";
        }
        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());

            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "Login fail !");
            return "admin/login";
        }
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "The parameter cannot be null";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "Can't be change";
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "The parameter cannot be null";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "Can't be change";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
