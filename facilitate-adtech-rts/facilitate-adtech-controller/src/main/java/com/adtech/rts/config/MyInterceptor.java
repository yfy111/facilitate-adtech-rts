package com.adtech.rts.config;

import com.adtech.rts.httpclient.RtsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @功能描述 自定义拦截器
 */
@Configuration
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private RtsInterface rtsInterface;

    //方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setContentType("application/json;charset=utf-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader("Access-Control-Allow-Methods", "Access-Control-Allow-Methods");
//        String secret = request.getHeader("rts_secret");
//        String token = request.getHeader("rts_token");
//        secret = StringUtils.isEmpty(secret)?request.getParameter("rts_secret"):secret;
//        token = StringUtils.isEmpty(token)?request.getParameter("rts_token"):token;
//        if(StringUtils.isEmpty(secret)||StringUtils.isEmpty(token)){
//            Result result = new Result(CodeEnum.FAIL_UNAUTHORIZED);
//            response.getWriter().print(JSON.toJSONString(result));
//            return false;
//        }
//        Result result1 = rtsInterface.resolveUsertoken(secret,token);
//        if(result1.getCode()!=100){
//            response.getWriter().print(JSON.toJSONString(result1));
//            return false;
//        }
        return true;
    }

    //方法执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    //页面渲染前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
