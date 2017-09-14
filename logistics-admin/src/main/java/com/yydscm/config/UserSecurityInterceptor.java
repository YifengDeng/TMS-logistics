package com.yydscm.config;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.yydscm.Enum.ResultEnum;
import com.yydscm.Service.TokenManager;
import com.yydscm.Util.ResultUtil;
import com.yydscm.view.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenzhaopeng on 2017/6/20.
 */

/**
 * 自定义拦截器逻辑
 */
@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenManager tokenManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if (url.indexOf("/login") >= 0 || url.indexOf("/logout") >= 0 || url.indexOf("/Applogin") >= 0 || url.indexOf("/randCode") >= 0) {
            return true;
        }
        String token = request.getHeader("token");
        String tokenTo = request.getParameter("token");
        if (!Strings.isNullOrEmpty(token) && tokenManager.isVaild(token)) {
            String userid = tokenManager.getClaimsFromToken(token).get("userid") + "";
            request.setAttribute("userid", userid);
            return true;
        } else {
            if (!Strings.isNullOrEmpty(tokenTo)) {
                String userid = tokenManager.getClaimsFromToken(tokenTo).get("userid") + "";
                request.setAttribute("userid", userid);
                return true;
            }

            Result result = ResultUtil.error(ResultEnum.LOGINTIMEOUT.getCode(), ResultEnum.LOGINTIMEOUT.getMsg());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(result);
            response.getOutputStream().write(json.getBytes("UTF-8"));
            return false;
        }
    }


}
