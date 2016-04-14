package com.Shop.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class WechatInterceptor implements HandlerInterceptor {
    Logger log = Logger.getLogger(WechatInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("openId") == null){
            log.info("没有openId");
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05208e667b03b794&"
                    + "redirect_uri=http://weijiehuang.productshow.cn/getCode"
                    +"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
           httpServletResponse.sendRedirect(url);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
