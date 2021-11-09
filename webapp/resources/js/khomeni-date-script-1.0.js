/**
 * Copyright (c) Islami Bank Bangladesh Limited.
 * Created by: Khomeni
 * Date: 11/08/15
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */

function getToday() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    return dd + '/' + mm + '/' + yyyy;
}

function getAgeInYear(birthDate) {
    return getDateDiff(birthDate, new Date(), "years");
}

function getDOMDate(date) {
    var reggie = /(\d{2})\/(\d{2})\/(\d{4})/;
    var dateArray = reggie.exec(date);
    return new Date(
        (+dateArray[3]),
        (+dateArray[2]) - 1, // Careful, month starts at 0!
        (+dateArray[1])
    );
}

function getDateDiff(date1, date2, interval) {
    var second = 1000,
        minute = second * 60,
        hour = minute * 60,
        day = hour * 24,
        week = day * 7;
    var timeDiff = date2 - date1;
    if (isNaN(timeDiff)) return NaN;
    switch (interval) {
        case "years":
            return date2.getFullYear() - date1.getFullYear();
        case "months":
            return ((date2.getFullYear() * 12 + date2.getMonth()) - (date1.getFullYear() * 12 + date1.getMonth()));
        case "weeks":
            return Math.floor(timeDiff / week);
        case "days":
            return Math.floor(timeDiff / day);
        case "hours":
            return Math.floor(timeDiff / hour);
        case "minutes":
            return Math.floor(timeDiff / minute);
        case "seconds":
            return Math.floor(timeDiff / second);
        default:
            return undefined;
    }
}


function getTodayDDMMYYYY(patern) {
    var today = new Date();
    var day = today.getDate();
    var month = today.getMonth() + 1; //January is 0!

    var year = today.getFullYear();
    if (day < 10) {
        day = '0' + day
    }
    if (month < 10) {
        month = '0' + month
    }
    return day + patern + month + patern + year;

}


function smartDateToGeneral(smartDate, pattern) {
    var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var monthSl = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
    var year, month, day;

    if (smartDate.toString().length == 12) {
        //var smartDate = "Feb 12, 2014"
        for (var i = 0; i < months.length; i++) {
            if (smartDate.substring(0, 3) == months[i]) {
                year = smartDate.substring(8, 12);
                month = monthSl[i];
                day = smartDate.substring(4, 6);
                break;
            }
        }
    } else if (smartDate.toString().length == 11) {
        //var smartDate = "Feb 2, 2014"
        for (var i = 0; i < months.length; i++) {
            if (smartDate.substring(0, 3) == months[i]) {
                year = smartDate.substring(7, 11);
                month = monthSl[i];
                day = "0" + smartDate.substring(4, 5);
                break;
            }
        }
    }

    //var smartDate = "Feb 2, 2014"

    //return year + pattern + month + pattern + day;
    return day + pattern + month + pattern + year;
}


function validateYYYYMMDD(date) {
    //var date = "2015-10-10"

    //This validator will check month & year range also
    //var patternYYYYMMDD = /^(10|99)\d{2}\-(0[1-9]|1[0-2])\-(0[1-9]|1\d|2\d|3[01])$/;
    var patternYYYYMMDD = /^\d{4}\-(\d{2})\-\d{2}$/;
    if (!patternYYYYMMDD.test(date)) {
        alert("Date pattern should be like YYYY-MM-DD");
        return false;
    }
    var parts = date.split("-");
    var month = parseInt(parts[1], 10);
    var year = parseInt(parts[0], 10);
    var day = parseInt(parts[2], 10);

    if (year < 1000 || year > 9999) {
        alert("Sorry you must put year between 1000 - 9999 instead of " + year);
        return false;
    }
    if (month == 0 || month > 12) {
        alert("Sorry you must put month '0-12', instead of " + month);
        return false;
    }
    var monthLength = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;
    if (day > 0) {
        if (day <= monthLength[month - 1]) {
            //alert ("ok");
            return true;
        } else {
            alert("This is the month of " + monthLength[month - 1] + " days");
            return false;
        }
    } else {
        alert("Please check u'r date. It may not less or equal '0'");
        return false;
    }
}


function isDate(receiptDate) {
    //var receiptDate = $('#receiptDate');
    var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
    var Val_date = receiptDate.val();
    if (Val_date.match(dateformat)) {
        var seperator1 = Val_date.split('/');
        var seperator2 = Val_date.split('-');

        if (seperator1.length > 1) {
            var splitdate = Val_date.split('/');
        }
        else if (seperator2.length > 1) {
            var splitdate = Val_date.split('-');
        }
        var dd = parseInt(splitdate[0]);
        var mm = parseInt(splitdate[1]);
        var yy = parseInt(splitdate[2]);
        var ListofDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        if (mm == 1 || mm > 2) {
            if (dd > ListofDays[mm - 1]) {
                setErrorMsg(receiptDate, "Date format should be dd/mm/yyyy");
                return false;
            }
        }
        if (mm == 2) {
            var lyear = false;
            if ((!(yy % 4) && yy % 100) || !(yy % 400)) {
                lyear = true;
            }
            if ((lyear == false) && (dd >= 29)) {
                setErrorMsg(receiptDate, "Date format should be dd/mm/yyyy");
                return false;
            }
            if ((lyear == true) && (dd > 29)) {
                setErrorMsg(receiptDate, "Date format should be dd/mm/yyyy");
                return false;
            }
        }
    } else {
        setErrorMsg(receiptDate, "Date format should be dd/mm/yyyy");

        return false;
    }
    // setErrorMsg(receiptDate, "Date format OK");
    return true;
}





