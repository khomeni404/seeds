package com.ibbl.mvc;

import bd.com.softengine.security.DESEDE;
import bd.com.softengine.security.model.User;
import bd.com.softengine.util.ActionResult;
import com.ibbl.mvc.dao.AuthDAO;
import com.ibbl.mvc.dao.DividendDAO;
import com.ibbl.mvc.util.SeedsConstants;
import com.vision.accounts.model.Dividend;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 16/02/2016
 * Last modification by: ayat $
 * Last modification on 16/02/2016: 12:39 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
@Controller
@RequestMapping("/auth/")
public class AuthController {

    /**
     * Authenticate User
     * @param model ModelMap
     * @param username String username
     * @param password String password
     * @param request HttpServlet
     * @param response  HttpServlet
     * @return String view
     */
    @RequestMapping(method = RequestMethod.POST, value = "authenticateUser")
    public String authenticateUser(ModelMap model, String username, String password, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
// alom , 01820994612
        ActionResult result;
        try {
            result = new AuthDAO().authenticateUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            result = new ActionResult();
            result.setSuccess(false);
            result.setMsg("System Error !!");
        }
        model.addAttribute("result", result);
        if(result.isSuccess()) {
            session.setAttribute(SeedsConstants.SESSION_USER, result.getDataObject());
            session.setAttribute(SeedsConstants.SESSION_USER_ID, ((User) result.getDataObject()).getId());
            return "redirect:/auth/home";
        }else {
            //model.addAttribute("msg", result.getMsg());
            return "redirect:/home/logOut?msg="+result.getMsg();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "home")
    public String home(ModelMap model) {
        List<Dividend> dividendList;
        try {
            dividendList = new DividendDAO().getDividendList();
        } catch (SQLException e) {
            dividendList = new ArrayList<Dividend>(0);
            e.printStackTrace();
        }
        model.addAttribute("dividendList", dividendList);
        return "home";
    }


}
