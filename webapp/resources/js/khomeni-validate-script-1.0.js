/**
 * Copyright (c) Islami Bank Bangladesh Limited.
 * Created by: Khomeni
 * Date: 03/09/15
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
function setDefaultValue(obj, dVal) {
    //var objId = obj.prop("id");
    var objVal = obj.val();
    if (objVal == "") {
         obj.val(dVal);
    }
}

function isEmpty(obj, msg) {
    var objId = obj.prop("id");
    var objVal = obj.val();
    $(".mak-err-msg").html("");
    var td = obj.parent('div');
    var content = td.html();
    content = content.replace(/<BR>/, "");  // For IE
    content = content.replace("<br>", "");  // For Chrome, Firefox & Other
    if (obj.val() == "") {
        if (content.indexOf(msg) > -1) {
            return false;
        } else {
            td.html(content
            + "<b style=\"color: red\" class=\"mak-err-msg\">"
            + msg
            + "</b>");
            $('#' + objId).prop('value', objVal); // For Chrome, coz it clear value when content fetch and set again.
            $('#' + objId).prop('id', objId); // For Chrome, coz it clear value when content fetch and set again.
            $('#' + objId).focus();
            return false;
        }
    } else {
        return true;
    }
}

function isNumber(obj, msg) {
    var objId = obj.prop("id");
    var objVal = obj.val();
    $(".mak-err-msg").html("");
    var td = obj.parent('div');
    var content = td.html();
    content = content.replace(/<BR>/, "");  // For IE
    content = content.replace("<br>", "");  // For Chrome, Firefox & Other
    if (isNaN(obj.val())) {
        if (content.indexOf(msg) > -1) {
            return false;
        } else {
            td.html(content
            + "<b style=\"color: red\" class=\"mak-err-msg\">"
            + msg
            + "</b>");
            $('#' + objId).prop('value', objVal); // For Chrome, coz it clear value when content fetch and set again.
            $('#' + objId).focus();
            return false;
        }
    } else {
        return true;
    }
}


function setErrorMsg(obj, msg) {
    var objId = obj.prop("id");
    var objVal = obj.val();
    $(".mak-err-msg").html("");
    var td = obj.parent('div');
    var content = td.html();
    //alert(content);
    content = content.replace(/<BR>/, "");  // For IE
    content = content.replace("<br>", "");  // For Chrome, Firefox & Other
    if (content.indexOf(msg) > -1) {
        return false;
    } else {
        td.html(content
        + "<b style=\"color: red\" class=\"mak-err-msg\">"
        + msg
        + "</b>");
        $('#' + objId).prop('value', objVal); // For Chrome, coz it clear value when content fetch and set again.
        $('#' + objId).focus();
        return false;
    }
}

function setMsg(obj, msg) {
    $(".mak-err-msg").html("");
    var td = obj.parent('div');
    var content = td.html();
    //alert(content);
    content = content.replace(/<BR>/, "");  // For IE
    content = content.replace("<br>", "");  // For Chrome, Firefox & Other
    td.html(content
    + "<b style=\"color: green\" class=\"mak-err-msg\">"
    + msg
    + "</b>");

}


function isValidCellNo(cell) {
    var cellNo = cell.val();
    var homeArray = ['011', '015', '016', '017', '018', '019'];
    if (isNaN(cellNo)) {
        setErrorMsg(cell, "No character,Space allowed");
        return false;
    } else if (cellNo.length != 11) {
        setErrorMsg(cell, "Number must be 11 digit");
        return false;
    }
    var homeNo = cellNo.substring(0, 3);
    if ($.inArray(homeNo, homeArray) == -1) {
        setErrorMsg(cell, "Thi is not a valid BD cell no.");
        return false;
    } else {
        return true;
    }
}

function isValidEmail(address) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(address);
}

function isValidBrList(br) {
    var brVal = br.val();
    var brArr = brVal.split(",");
    var returnMsg = true;
    for (var i = 0; i < brArr.length; i++) {
        if (isNaN(brArr[i])) {
            setErrorMsg(br, "Branch Code must be with DIGIT");
            returnMsg = false;
            break;
        }
        if (brArr[i].length != 3) {
            setErrorMsg(br, "All BR Code must be with 3 Digit");
            returnMsg = false;
            break;
        }
    }
    return returnMsg;
}

function makModalAlert(type, head, body) {
    $('#alertModal-' + type).modal('show');
    $('#alert-' + type + '-title').html(head);
    $('#alert-' + type + '-body').html(body);
}

function hideMakModalAlert(type) {
    $('#alertModal-' + type).modal('hide');
}

