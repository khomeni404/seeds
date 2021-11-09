package com.ibbl.mvc.dao;


import com.vision.accounts.model.Dividend;
import com.vision.accounts.model.DividendRecord;
import com.vision.csd.model.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni<br/>
 * Date: 16/02/2016
 * Last modification by: ayat $
 * Last modification on 16/02/2016: 2:30 PM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class DataDAO {
    Connection conn = null;

    public List<DividendRecord> getDividendRecordList(Long dividendId) throws SQLException { // 20160216

        List<DividendRecord> recordList = new ArrayList<DividendRecord>();
        DividendRecord record;
        try {
            conn = MyConnection.getDBConnection2();
            //conn.setAutoCommit(false);
            String records_sql = "SELECT id, \n" +
                    "(SELECT c.id FROM abc_user_master c WHERE c.id = ac_share_dividend_record.customer_id) AS cust_id, \n" +
                    "(SELECT c.customer_id FROM abc_user_master c WHERE c.id = ac_share_dividend_record.customer_id) AS cid, \n" +
                    "(SELECT c.name FROM abc_user_master c WHERE c.id = ac_share_dividend_record.customer_id) AS cust_name, \n" +
                    "dividend_on_amt, dividend_amt, dividend_breakup \n" +
                    "FROM ac_share_dividend_record WHERE dividend_id = " + dividendId;
            ResultSet resultSet = conn.prepareStatement(records_sql).executeQuery(records_sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Double dividend_on_amt = resultSet.getDouble("dividend_on_amt");
                Double dividend_amt = resultSet.getDouble("dividend_amt");
                String details = resultSet.getString("dividend_breakup");
                record = new DividendRecord();
                record.setId(id);
                record.setDividendOnAmt(dividend_on_amt);
                record.setDetails(details);
                record.setDividendAmt(dividend_amt);

                Customer customer = new Customer();
                customer.setId(resultSet.getLong("cust_id"));
                customer.setCid(resultSet.getString("cid"));
                customer.setName(resultSet.getString("cust_name"));
                record.setCustomer(customer);
                recordList.add(record);
            }


        } catch (SQLException e) {
            // conn.rollback();
            System.out.println(e.getMessage());
            recordList = null;
        } finally {
            if (recordList != null)
                try {
                    if (conn != null) {
                        //conn.commit();
                        conn.close();
                    }
                } catch (SQLException e) {
                    //conn.rollback();
                    e.printStackTrace();
                    recordList = null;
                }
        }
        return recordList;
    }

    public Dividend getDividend(Long dividendId) throws SQLException { // 20160216

        Dividend dividend = null;
        try {
            conn = MyConnection.getDBConnection2();
            //conn.setAutoCommit(false);
            String records_sql = "SELECT * FROM ac_share_dividend WHERE id = " + dividendId;
            ResultSet resultSet = conn.prepareStatement(records_sql).executeQuery(records_sql);
            while (resultSet.next()) {
                //declare_date, dividend_date, execution_date, profit_rate, total_holder, total_amt_on, total_dividend_amt)" +
                Long id = resultSet.getLong("id");
                Date declare_date = resultSet.getDate("declare_date");
                Date dividend_date = resultSet.getDate("dividend_date");
                Date execution_date = resultSet.getDate("execution_date");
                Double profit_rate = resultSet.getDouble("profit_rate");
                Integer total_holder = resultSet.getInt("total_holder");
                Double total_amt_on = resultSet.getDouble("total_amt_on");
                Double total_dividend_amt = resultSet.getDouble("total_dividend_amt");
                dividend = new Dividend();
                dividend.setId(id);
                dividend.setProfitRate(profit_rate);
                dividend.setDividendDate(dividend_date);
                dividend.setExecutionDate(execution_date);
                dividend.setDeclareDate(declare_date);
                dividend.setTotalDividendAmt(total_dividend_amt);
                dividend.setTotalAmountOn(total_amt_on);
                dividend.setTotalHolder(total_holder);
            }

        } catch (SQLException e) {
            // conn.rollback();
            System.out.println(e.getMessage());
            dividend = null;
        } finally {
            if (dividend != null)
                try {
                    if (conn != null) {
                        //conn.commit();
                        conn.close();
                    }
                } catch (SQLException e) {
                    //conn.rollback();
                    e.printStackTrace();
                    dividend = null;
                }
        }
        return dividend;
    }

}
