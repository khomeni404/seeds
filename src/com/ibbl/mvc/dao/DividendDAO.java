package com.ibbl.mvc.dao;

import bd.com.softengine.security.model.User;
import bd.com.softengine.util.ActionResult;
import bd.com.softengine.util.DateUtil;
import com.ibbl.mvc.util.SeedsConstants;
import com.vision.accounts.model.Dividend;
import com.vision.util.VisionConstants;

import java.sql.*;
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
 * Last modification on 15/02/2016: 2:26 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class DividendDAO implements SeedsConstants {
    Date dividendDate = DateUtil.toDate("30-07-2011", "DD-MM-YYYY");

    Connection conn = null;


    public ActionResult execute(Long projectId,User user, Long dividendId, Date declareDate,
                                Date executionDate, Date dividendDate, Double profit_rate) throws SQLException {

        ActionResult result = new ActionResult();
        Statement detailDataSet;
        Long operatorId = user.getId();
        String dividend_date = SDF_yyyy_MM_dd.format(dividendDate);
        String declare_date = SDF_yyyy_MM_dd.format(declareDate);
        String execution_date = SDF_yyyy_MM_dd.format(executionDate);
        String receipt_type = /*"%";//*/ "apartment";
        String receipt_status = "Passed";

        conn = MyConnection.getDBConnection2();
        conn.setAutoCommit(false);

        String apts = "SELECT id as customer_id FROM `abc_user_master` \n" +
                "WHERE phase_id IN (SELECT id FROM `csd_phase` WHERE  project_id = "+projectId+")\n" +
                "AND apartment_id IS NOT NULL\n" +
                "AND user_type = 'customer'";

        Statement aptIdListStm = conn.prepareStatement(apts);
        ResultSet aptIdListRs = aptIdListStm.executeQuery(apts);
        List<Long> customerIdList = new ArrayList<>();
        while (aptIdListRs.next()) {
            customerIdList.add(aptIdListRs.getLong("customer_id"));
        }
        aptIdListRs.close();


        String where_clause = "WHERE cash_date <= '" + dividend_date + "' AND receipt_type LIKE '" + receipt_type + "' AND status = '" + receipt_status + "'";

        int[] LENGTH = VisionConstants.DIVIDEND_BREAKDOWN_INDEX_ARR;
        //{15, 10, 10, 10, 5, 13};
        String detail_data_sql = "SELECT id, customer_id, SUM(amount) as amount_on, \n" +
                "SUM((CASE \n" +
                "WHEN last_dividend_date IS NULL THEN ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', cash_date)/365))/100), 2) \n" +
                "WHEN last_dividend_date < cash_date THEN ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', cash_date)/365))/100), 2) \n" +
                "ELSE ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', last_dividend_date)/365))/100), 2)\n" +
                "   END)) AS profit,\n" +
                "GROUP_CONCAT(\n" +
                "CONCAT(LPAD(receipt_no, " + LENGTH[0] + ", 'X'),\n" +
                "LPAD(amount, " + LENGTH[1] + ", '0')),\n" +
                "(CASE \n" +
                "WHEN last_dividend_date < cash_date THEN CONCAT(cash_date, '" + dividend_date + "')\n" +
                "WHEN last_dividend_date IS NULL THEN CONCAT(cash_date, '" + dividend_date + "')\n" +
                "ELSE CONCAT(last_dividend_date,'" + dividend_date + "')\n" +
                "    END),\n" +
                "    (CASE \n" +
                "WHEN last_dividend_date IS NULL THEN LPAD(DATEDIFF('" + dividend_date + "', cash_date), " + LENGTH[4] + ", '0')\n" +
                "WHEN last_dividend_date < cash_date THEN LPAD(DATEDIFF('" + dividend_date + "', cash_date), " + LENGTH[4] + ", '0')\n" +
                "ELSE LPAD(DATEDIFF('" + dividend_date + "', last_dividend_date), " + LENGTH[4] + ", '0')\n" +
                "    END),\n" +
                "    (CASE \n" +
                "WHEN last_dividend_date IS NULL THEN LPAD(ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', cash_date)/365))/100), 0), " + LENGTH[5] + ", '0') \n" +
                "WHEN last_dividend_date < cash_date THEN LPAD(ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', cash_date)/365))/100), 0), " + LENGTH[5] + ", '0') \n" +
                "ELSE LPAD(ROUND(((amount*" + profit_rate + "*(DATEDIFF('" + dividend_date + "', last_dividend_date)/365))/100), 2), " + LENGTH[5] + ", '0') \n" +
                "   END)\n" +
                "SEPARATOR ',') as details \n" +
                "FROM ac_money_receipt_master receipt " +
                where_clause +
                "GROUP BY customer_id";


        try {
            String duplicate_dividend = "SELECT id FROM ac_share_dividend where id=" + dividendId;
            ResultSet dupRs = conn.prepareStatement(duplicate_dividend).executeQuery(duplicate_dividend);
            int ddc = 0;
            while (dupRs.next()) {
                ddc++;
            }
            if (ddc > 0) {
                result.setSuccess(false);
                result.setMsg("No More dividend can be executed today.");
            } else {

                String Dividend = "INSERT INTO ac_share_dividend (" +
                        "id, declare_date, dividend_date, execution_date, profit_rate, total_holder, total_amt_on, total_dividend_amt, project_id)" +
                        "VALUES(" + dividendId + ", '" + declare_date + "','" + dividend_date + "', '" + execution_date + "', " + profit_rate + ", 0, 0.0, 0.0, "+projectId+")";
                conn.prepareStatement(Dividend).executeUpdate(Dividend);
                //conn.commit();
                detailDataSet = conn.prepareStatement(detail_data_sql);
                ResultSet rs = detailDataSet.executeQuery(detail_data_sql);
                String DividendRecord;
                String apt_id_sql;
                try {
                    int holder = 0;
                    Double totalDividendOn = 0.0;
                    Double totalDividendAmt = 0.0;
                    while (rs.next()) {
                        //if (holder == 0) continue;
                        long customerId = rs.getLong("customer_id");
                        if (!customerIdList.contains(customerId)) {
                            continue;
                        }
                        Double amount = rs.getDouble("amount_on");
                        Double profit = rs.getDouble("profit");
                        if (profit <= 0.0) continue;
                        String details = rs.getString("details");
                        DividendRecord = "INSERT INTO ac_share_dividend_record (customer_id, dividend_on_amt, dividend_amt, dividend_breakup, dividend_id) " +
                                "VALUES(" + customerId + "," + amount + "," + profit + ", '" + details + "', " + dividendId + ")";
                       PreparedStatement ps = conn.prepareStatement(DividendRecord);
                                ps.executeUpdate(DividendRecord);     // TODO.. Open IT

                        apt_id_sql = "SELECT apartment_id, plot_id FROM abc_user_master where id = " + customerId;
                        Statement aptIdStm = conn.prepareStatement(apt_id_sql);
                        ResultSet aptIdRs = aptIdStm.executeQuery(apt_id_sql);
                        String aptId = "";
                        String plotId = "";
                        while (aptIdRs.next()) {
                            aptId = aptIdRs.getString("apartment_id");
                            plotId = aptIdRs.getString("plot_id");
                        }
                        String mrNo = "D" + DF_00.format(dividendId).substring(2, 8) + "-" + DF_0000.format(customerId);
                        String cash_date = SDF_yyyy_MM_dd.format(DateUtil.getDateAfterDay(dividendDate, 1));
                        String MoneyReceipt = "INSERT INTO ac_money_receipt_master(" +
                                "receipt_type, customer_id, instrument_no, amount, CASH_DATE, last_dividend_date, STATUS, ENTRY_DATE,\n" +
                                "note, PAYMENT_METHOD, RECEIPT_DATE, RECEIPT_NO, operator_id, statusUpdateBy_id, apartment_id)\n" +
                                "VALUES( 'apartment', " + customerId +", 'SD-"+ dividendId + "', " + profit + ", '" + cash_date + "', '" + dividend_date + "', 'Passed'," +
                                "'" + execution_date + "', 'Share Dividend', 'Dividend', '" + execution_date + "', '" + mrNo + "'," +
                                operatorId+", " + operatorId+", " + aptId + ")";
                        conn.prepareStatement(MoneyReceipt)
                                .executeUpdate(MoneyReceipt);

                        totalDividendOn += amount;
                        totalDividendAmt += profit;
                        holder++;
                    }
                    rs.close();
                    String dividend_update_sql = "UPDATE ac_share_dividend " +
                            "SET total_holder = " + holder +
                            ", total_amt_on = " + totalDividendOn +
                            ", total_dividend_amt =" + totalDividendAmt +" WHERE id = "+dividendId;
                    conn.prepareStatement(dividend_update_sql).executeUpdate(dividend_update_sql);

                    // conn.commit();

                    if (holder > 0) {
                        String group_concat_max_len = "SET group_concat_max_len = 30000000";
                        conn.prepareStatement(group_concat_max_len).executeUpdate(group_concat_max_len);

                        String mr_id_list_sql = "SELECT GROUP_CONCAT(id SEPARATOR ', ') as mr_list  FROM ac_money_receipt_master receipt \n" +
                                where_clause +
                                "GROUP BY 'all'";
                        Statement mrIdListStm = conn.prepareStatement(mr_id_list_sql);
                        ResultSet mrIdListRs = mrIdListStm.executeQuery(mr_id_list_sql);
                        String idIn = "";
                        int totalId = 0;
                        while (mrIdListRs.next()) {
                            idIn = mrIdListRs.getString("mr_list");
                            totalId = idIn.split(",").length;
                        }

                        /**Updating last_dividend_date*/
                        String last_dividend_date_sql = "UPDATE ac_money_receipt_master SET last_dividend_date='" + dividend_date + "' " +
                                "WHERE id in (" + idIn + ")";
                        conn.prepareStatement(last_dividend_date_sql).executeUpdate(last_dividend_date_sql);
                        result.setSuccess(true);
                    } else {
                        result.setSuccess(false);
                        result.setMsg("No shareholder found for dividend ");
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    result.setSuccess(false);
                    result.setMsg("System ERROR !!!");
                    e.printStackTrace();
                }
            }


        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
            result.setSuccess(false);
            result.setMsg("No shareholder found for dividend ");
        } finally {

            try {
                if (conn != null) {
                    if (result.isSuccess()) {
                        conn.commit();
                        result.setMsg("Dividend executed SUCCESSFULLY !!!");
                    } else {
                        conn.rollback();
                    }
                    conn.close();
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                result.setSuccess(false);
                result.setMsg("No shareholder found for dividend ");
            }
        }
        return result;
    }

    public List<Dividend> getDividendList() throws SQLException {
        List<Dividend> result = new ArrayList<Dividend>();
        try {
            conn = MyConnection.getDBConnection2();
            String sql = "SELECT * FROM ac_share_dividend ORDER BY id";
            ResultSet rs = conn.prepareStatement(sql).executeQuery(sql);
            while (rs.next()) {
                //id, declare_date, dividend_date, execution_date,
                // profit_rate, total_holder, total_amt_on, total_dividend_amt)" +
                Dividend dividend = new Dividend();
                dividend.setId(rs.getLong("id"));
                dividend.setDeclareDate(rs.getDate("declare_date"));
                dividend.setDividendDate(rs.getDate("dividend_date"));
                dividend.setExecutionDate(rs.getDate("execution_date"));
                dividend.setProfitRate(rs.getDouble("profit_rate"));
                dividend.setTotalHolder(rs.getInt("total_holder"));
                dividend.setTotalAmountOn(rs.getDouble("total_amt_on"));
                dividend.setTotalDividendAmt(rs.getDouble("total_dividend_amt"));
                result.add(dividend);
            }
        } catch (SQLException e) {
            //conn.rollback();
            System.out.println(e.getMessage());
            result = null;
        } finally {
            try {
                if (conn != null) {
                    //conn.commit();
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                result = null;
            }
        }
        return result;
    }
}
