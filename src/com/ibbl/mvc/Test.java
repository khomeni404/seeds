package com.ibbl.mvc;

import com.vision.accounts.bean.DividendBreakdownBean;
import com.vision.util.VisionConstants;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright &copy; Soft Engine Inc.
 * Created on 15/02/16
 * Created By : Khomeni
 * Edited By : Khomeni &
 * Last Edited on : 15/02/16
 * Version : 1.0
 */

public class Test {
    public static void main(String[] args) {
        String dataAr = "XXXXXXXXXXXX40a00000150002011-03-232011-10-30002210000000000536,XXXXXXXXXXXXX6700000050002011-05-052011-10-30001780000000000144,XXXXXXXXXXXXX8300000050002011-06-262011-10-30001260000000000102,XXXXXXXXXXXXX9800000050002011-10-042011-10-30000260000000000021,XXXXXXXXXXXX10900000050002011-10-042011-10-30000260000000000021,XXXXXXXXXXXX13400000050002011-10-042011-10-30000260000000000021,XXXXXXXXXXXX14200000050002011-10-172011-10-30000130000000000011";
        List<DividendBreakdownBean> beanList = new ArrayList();
        String[] dataArr = dataAr.split(",");
        int[] I_ARR = VisionConstants.DIVIDEND_BREAKDOWN_INDEX_ARR;
        for(String data : dataArr) {
            int start = 0; int end = I_ARR[0];
            DividendBreakdownBean bean = new DividendBreakdownBean();
            bean.setReceipt(data.substring(start, end).replaceAll("X", ""));
            start = end;  end += I_ARR[1];
            bean.setAmountOn(Double.valueOf(data.substring(start, end)));
            start = end;  end += I_ARR[2];
            bean.setFrom(data.substring(start, end));
            start = end;  end += I_ARR[3];
            bean.setTo(data.substring(start, end));
            start = end;  end += I_ARR[4];
            bean.setDays(Integer.valueOf(data.substring(start, end)));
            start = end;  end += I_ARR[5];
            bean.setDividendAmt(Double.valueOf(data.substring(start, end)));
            beanList.add(bean);
        }
       System.out.println("20160215".substring(2,8));
    }
}
