package com.ibbl.mvc.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public interface SeedsConstants {
    String SESSION_USER = "session.user";
    String SESSION_USER_ID = "session.user.id";

    DecimalFormat DF_00 = new DecimalFormat("00");
    DecimalFormat DF_000 = new DecimalFormat("000");
    DecimalFormat DF_0000 = new DecimalFormat("0000");

    SimpleDateFormat SDF_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat SDF_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

}
