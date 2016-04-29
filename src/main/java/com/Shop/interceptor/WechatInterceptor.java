package com.Shop.interceptor;

import com.Shop.Model.Areas;
import com.Shop.Model.Roles;
import com.Shop.Model.User;
import com.Shop.Service.TerraceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class WechatInterceptor implements HandlerInterceptor {
    @Autowired
    TerraceService terraceService;
    Logger log = Logger.getLogger(WechatInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o){
        HttpSession session = request.getSession();
        if(session.getAttribute("openId") == null){
            log.info("没有openId");
            if(request.getRequestURL().indexOf("getCode")<0) {
                log.info("获取用户code");
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05208e667b03b794&"
                        + "redirect_uri=http://weijiehuang.productshow.cn/getCode"
                        + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
                try {
                    response.sendRedirect(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("重定向在拦截器中出现问题");
                }
                return  false;
            }

        }
        else if(session.getAttribute("areas")==null&&
                session.getAttribute("roles")==null&&
                session.getAttribute("loginUser")==null){
            String openId =(String)session.getAttribute("openId");
            log.info(openId);
            if(terraceService.findAreasByOpenId(openId)!=null){
                Areas areas = terraceService.findAreasByOpenId(openId);
                session.setAttribute("areas",areas);
                log.info(areas.getImg());
            }else if(terraceService.findRolesByOpenId(openId)!=null){
                Roles roles = terraceService.findRolesByOpenId(openId);
                session.setAttribute("roles",roles);
            }else if(terraceService.findUseByOpenId(openId)!=null){
                User user = terraceService.findUseByOpenId(openId);
                session.setAttribute("loginUser",user);
            }else {
                log.info("在这里有调用了重定向");
                if (request.getRequestURL().indexOf("getCode") < 0) {
                    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05208e667b03b794&"
                            + "redirect_uri=http://weijiehuang.productshow.cn/getCode"
                            + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
                    try {
                        response.sendRedirect(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.info("重定向出现问题");
                    }
                    return false;
                }
            }
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
