package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gjw19 on 2018/4/14.
 */
@Slf4j
public class CookieUtil {

    // 只能在该域名下面的域名读取cookie
    private final static String COOKIE_DOMAIN = ".qq.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * 读取该网站指定cookie的value值
     *
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                // 该判断字符串相等的方法不会报空指针异常
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //X:domain=".qq.com"
    //a:A.qq.com            cookie:domain=A.qq.com;path="/"
    //b:B.qq.com            cookie:domain=B.qq.com;path="/"
    //c:A.qq.com/test/cc    cookie:domain=A.qq.com;path="/test/cc"
    //d:A.qq.com/test/dd    cookie:domain=A.qq.com;path="/test/dd"
    //e:A.qq.com/test       cookie:domain=A.qq.com;path="/test"

    /**
     * 写入该网站的cookie值
     *
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        // /代表设置在根目录，根目录下的所有均可访问cookie
        ck.setPath("/");
        // 防止脚本攻击
        ck.setHttpOnly(true);
        // cookie有效期，单位为秒，-1为永久
        // 如果这个maxAge不设置的话，cookie就不会写入硬盘，而是写在内存，只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);

        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        // 写入cookie
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    // 设置成0，代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
