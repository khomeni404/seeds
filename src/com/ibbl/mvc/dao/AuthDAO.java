package com.ibbl.mvc.dao;

import bd.com.softengine.security.DESEDE;
import bd.com.softengine.security.model.User;
import bd.com.softengine.util.ActionResult;
import com.vision.accounts.model.Dividend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright &copy; Soft Engine Inc.
 * Created on 17/02/16
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 17/02/16
 * Version : 1.0
 */

public class AuthDAO {
    Connection conn = null;
    public ActionResult authenticateUser(String username, String password) throws SQLException {
        ActionResult result = new ActionResult();
        try {
            conn = MyConnection.getDBConnection2();
            String sql = "SELECT id, username, PASSWORD FROM abc_token WHERE username = '"+username+"'";
            ResultSet rs = conn.prepareStatement(sql).executeQuery(sql);
            String databasePwd = "";
            String tokenId = "";
            while (rs.next()) {
                tokenId = rs.getString("id");
                databasePwd = rs.getString("password");
            }

            String userPwd = getEncryptedPassword(username, password);
            if (databasePwd.equals("")) {
                result.setSuccess(false);
                result.setMsg("Username doesn't exists !!!");
            } else
            if (userPwd.equals(databasePwd)) {
                String sql2 = "SELECT u.id, u.name FROM abc_user_master u WHERE token_id = '"+tokenId+"'";
                ResultSet rs2 = conn.prepareStatement(sql2).executeQuery(sql2);
                User user= new User();
                while (rs2.next()) {
                    user.setId(rs2.getLong("id"));
                    user.setName(rs2.getString("name"));
                }
                result.setDataObject(user);
                result.setSuccess(true);
                result.setMsg("");
            }  else {
                result.setSuccess(false);
                result.setMsg("Password Doesn't Match");
            }
        } catch (SQLException e) {
            //conn.rollback();
            System.out.println(e.getMessage());
            result.setSuccess(false);
            result.setMsg("Sorry !! System Error");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMsg("Sorry !! System Error");
            }
        }
        return result;
    }

    public static String getEncryptedPassword(String username, String password) {
        DESEDE desede = new DESEDE(username);
        return desede.encrypt(password);
    }
}
