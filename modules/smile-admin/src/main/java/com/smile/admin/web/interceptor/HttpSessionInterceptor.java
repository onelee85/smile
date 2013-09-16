package com.smile.admin.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smile.admin.web.utils.HttpSessionConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HttpSessionInterceptor implements HandlerInterceptor {

    public static long access_num = 0;
    public static Map<String,Long> IPMap = new HashMap<String,Long>();
    public static Map<String,Long> urlMap = new HashMap<String,Long>();

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obj) throws Exception {

        access_num += 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String url = request.getRequestURI() != null ? request.getRequestURI().toString() : null;
        url= sdf.format(now) + "-" + url;
        if(url != null && urlMap.containsKey(url)) {
            urlMap.put(url, urlMap.get(url) + 1 );
        } else if(url != null) {
            urlMap.put(url, 1l);
        }

        String key = sdf.format(now) + "-" + ip;

        if(IPMap.containsKey(key)) {
            IPMap.put(key, IPMap.get(key) + 1 );
        } else {
            IPMap.put(key, 1l);
        }

        if(null == request.getSession().getAttribute("user")){
            String origin_url = request.getServletPath();

            if(StringUtils.isEmpty(origin_url)){
                origin_url = "index";
            }
            request.getSession().setAttribute(HttpSessionConstant.REDIRECT_URL, origin_url);
            response.sendRedirect(request.getContextPath() + "/public/login_page.html");
            return false;
        }
        return true;
    }

}