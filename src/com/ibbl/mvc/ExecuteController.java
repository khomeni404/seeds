package com.ibbl.mvc;

import bd.com.softengine.security.model.User;
import bd.com.softengine.util.ActionResult;
import bd.com.softengine.util.DateUtil;
import com.ibbl.mvc.dao.DataDAO;
import com.ibbl.mvc.dao.DividendDAO;
import com.ibbl.mvc.util.SeedsConstants;
import com.ibbl.mvc.util.SessionUtil;
import com.vision.accounts.model.Dividend;
import com.vision.accounts.model.DividendRecord;
import com.vision.util.VisionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 15/02/2016
 * Last modification by: ayat $
 * Last modification on 15/02/2016: 2:18 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
@Controller
@RequestMapping("/dividend/")
public class ExecuteController {
    @RequestMapping(method = RequestMethod.GET, value = "newDividend")
    public String newDividend(ModelMap model, @RequestParam String msg) {
        model.addAttribute("executionDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        model.addAttribute("id", new Long(SeedsConstants.SDF_yyyyMMdd.format(new Date())));
        model.addAttribute("msg", msg);
        return "new_dividend";
    }

    @RequestMapping(method = RequestMethod.POST, value = "execute")
    public String execute(ModelMap model,
                          //@RequestParam Long dividendId,
                          @RequestParam String declare_date,
                          @RequestParam String dividend_date,
                          @RequestParam String execution_date,
                          @RequestParam Double profit_rate) {
        User user = SessionUtil.getSessionUser();

        if (user == null) {
            return "redirect:/dividend/newDividend?msg=No Session Exists ! <br/>Please Login again.";
        }

        Date declareDate = DateUtil.toDate(declare_date, "DD-MM-YYYY");
        Date executionDate = DateUtil.toDate(execution_date, "DD-MM-YYYY");
        Date dividendDate = DateUtil.toDate(dividend_date, "DD-MM-YYYY");
        model.addAttribute("message", "Here I am");
        ActionResult result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Long dividendId = Long.valueOf(sdf.format(dividendDate));
        try {
            result = new DividendDAO().execute(user, dividendId, declareDate, executionDate, dividendDate, profit_rate);
        } catch (SQLException e) {
            e.printStackTrace();
            result = new ActionResult();
            result.setSuccess(false);
            result.setMsg("System Error");
        }
        if (result.isSuccess()) {
            return "redirect:/auth/home";
        } else {
            return "redirect:/dividend/newDividend?msg=" + result.getMsg();
        }
    }

    //    /execute/getDividendRecordList
    @RequestMapping(method = RequestMethod.GET, value = "getDividendRecordList")
    public String getDividendRecordList(ModelMap model, Long dividendId) {
        model.addAttribute("message", "Here I am");
        List<DividendRecord> recordList = new ArrayList<DividendRecord>(0);
        Dividend dividend = null;
        try {
            recordList = new DataDAO().getDividendRecordList(dividendId);
            dividend = new DataDAO().getDividend(dividendId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("recordList", recordList);
        model.addAttribute("dividend", dividend);

        return "dividend_details";
    }

}
