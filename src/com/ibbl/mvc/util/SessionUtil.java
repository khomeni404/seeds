package com.ibbl.mvc.util;


import bd.com.softengine.security.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Copyright &copy; Soft Engine Inc.
 * Created on 17/02/16
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 17/02/16
 * Version : 1.0
 */

public class SessionUtil {
    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static User getSessionUser() {
        HttpSession session = getSession();
        if (session != null) {
            User user = (User) session.getAttribute(SeedsConstants.SESSION_USER);
            return user instanceof User ? user : null;
        }else {
            return null;
        }
    }

    public static Long getSessionUserId() {
        Long userId = (Long) getSession().getAttribute(SeedsConstants.SESSION_USER_ID);
        return userId instanceof Long ? userId : null;
    }


}
