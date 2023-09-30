
package com.adc.eshop.controller.common;

import com.adc.eshop.common.Constants;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class CommonController {

    @GetMapping("/common/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");


        SpecCaptcha captcha = new SpecCaptcha(150, 40, 4);

     
        captcha.setCharType(Captcha.TYPE_DEFAULT);

   
        captcha.setCharType(Captcha.FONT_9);

        httpServletRequest.getSession().setAttribute("verifyCode", captcha.text().toLowerCase());

        captcha.out(httpServletResponse.getOutputStream());
    }

    @GetMapping("/common/shop/kaptcha")
    public void mallKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");

        SpecCaptcha captcha = new SpecCaptcha(110, 40, 4);

        captcha.setCharType(Captcha.TYPE_DEFAULT);

        captcha.setCharType(Captcha.FONT_9);

        httpServletRequest.getSession().setAttribute(Constants.MALL_VERIFY_CODE_KEY, captcha.text().toLowerCase());

        captcha.out(httpServletResponse.getOutputStream());
    }
}
