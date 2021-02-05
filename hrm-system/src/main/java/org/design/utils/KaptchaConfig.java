package org.design.utils;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KaptchaConfig {
    @Bean
    public ServletRegistrationBean<KaptchaServlet> kaptchaServlet() {

        ServletRegistrationBean<KaptchaServlet> registrationBean = new ServletRegistrationBean<>(new KaptchaServlet(), "/captcha/kaptcha.jpg");

        registrationBean.addInitParameter(Constants.KAPTCHA_SESSION_CONFIG_KEY,
                Constants.KAPTCHA_SESSION_KEY);
        //宽度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_WIDTH,"200");
        //高度
        registrationBean.addInitParameter(Constants.KAPTCHA_IMAGE_HEIGHT,"70");
        //字体大小
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"50");
//        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER_THICKNESS,"1"); //边框
        //无边框
        registrationBean.addInitParameter(Constants.KAPTCHA_BORDER,"no");
        //文字颜色
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        //长度
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        //字符间距
        registrationBean.addInitParameter(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "6");
        return registrationBean;
    }
}