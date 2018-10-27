package com.dsys.filter;

import com.dsys.exception.CommonException;
import com.dsys.utils.JSONUtils;
import com.dsys.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author dsys
 * @CreateTime 18-10-10-下午11:26
 * @Description 验证token
 **/
public class JWTFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        String auth=httpServletRequest.getHeader("Authorization");
        String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        if(url.startsWith("/user/login")||url.startsWith("/user/register")||url.startsWith("/user/test")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(auth!=null&&auth.length()>7){
            String token=auth.substring(0,6).toLowerCase();
            if(token.compareTo("bearer")==0){
                token=auth.substring(6,auth.length());
                Claims claims=JwtHelper.parseJwt(token);
                if(claims.get("account")!=null){
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }
        }
        HttpServletResponse httpServletResponse= (HttpServletResponse) servletResponse;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(JSONUtils.packageResult(CommonException.StatusCode.AUTH.getCode(),"",CommonException.StatusCode.AUTH.getMessage()));
        return;
    }
}
