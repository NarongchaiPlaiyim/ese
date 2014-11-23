$(document).unbind('keydown').bind('keydown', function (event) {
    var doPrevent = false;
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.type.toUpperCase() === 'FILE' || d.type.toUpperCase() === 'EMAIL' ))
            || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }

    if (doPrevent) {
        event.preventDefault();
    }
});

PrimeFaces.locales ['th_TH'] = {
    closeText: 'ปิด',
    prevText: 'ย้อนกลับ',
    nextText: 'ถัดไป',
    monthNames: ['มกราคม', 'กุมภาพันธ์', 'มีนาคม', 'เมษายน', 'พฤษภาคม', 'มิถุนายน', 'กรกฎาคม', 'สิงหาคม', 'กันยายน', 'ตุลาคม', 'พฤศจิกายน', 'ธันวาคม' ],
    //monthNamesShort: ['ม.ค.', 'ก.พ.', 'มี.ค.', 'เม.ย.', 'พ.ค.', 'มิ.ย.', 'ก.ค.', 'ส.ค.', 'ก.ย.', 'ต.ค.', 'พ.ย.', 'ธ.ค.' ],
    monthNamesShort: ['มกราคม', 'กุมภาพันธ์', 'มีนาคม', 'เมษายน', 'พฤษภาคม', 'มิถุนายน', 'กรกฎาคม', 'สิงหาคม', 'กันยายน', 'ตุลาคม', 'พฤศจิกายน', 'ธันวาคม' ],
    dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Tue', 'Fri', 'Sat'],
    dayNamesMin: ['อา', 'จ', 'อ', 'พ ', 'พฤ', 'ศ ', 'ส'],
    weekHeader: 'สัปดาห์',
    firstDay: 0,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix:'',
    timeOnlyTitle: 'เวลา เท่านั้น',
    timeText: 'Time',
    hourText: 'Hour',
    minuteText: 'Minute',
    secondText: 'Second',
    currentText: 'ปัจจุบัน',
    ampm: false,
    month: 'เดือน',
    week: 'สัปดาห์',
    day: 'วัน',
    allDayText: 'All Day'
};

function showCheckedValue(){
    alert(document.getElementById('frmMain:checkBoxId').checked);
}

function reload(){
    window.location.reload();
}

function gotoInbox(contextUrl) {
    window.location = contextUrl;
}

function checkThaiID(id) {
    if(id=='') return true;
    if(id.length != 13) return false;
    var sum = 0;
    for(i=0; i < 12; i++) {	sum += (13-i)*parseFloat(id.charAt(i));	}
    var x = sum%11;
    if(x <= 1 && (1-x != parseFloat(id.charAt(12)))) return false;
    else if(x > 1 && (11-x != parseFloat(id.charAt(12)))) return false;
    return true;
}

function checkSearchThaiID(obj){
    if(obj != undefined){
        var id = obj.value;
        if(!checkThaiID(id)){
            PF('msgBoxInvalidCitizenSearchDlg').show();
        }
    }
}

function openDialog(url){
    window.open(url,'_blank','toolbar=yes, scrollbars=yes, resizable=yes, top=500, left=500, width=400, height=400');
}

function checkJuristicThaiID(obj){
    if(obj != undefined){
        var id = obj.value;
        if(!checkThaiID(id)){
            PF('msgBoxInvalidCitizenJrDlg').show();
        }
    }
}

function checkBorrowerThaiID(obj){
    if(obj != undefined){
        var id = obj.value;
        if(!checkThaiID(id)){
            PF('msgBoxInvalidCitizenBrDlg').show();
        }
    }
}

function checkSpouseThaiID(obj){
    if(obj != undefined){
        var id = obj.value;
        if(!checkThaiID(id)){
            PF('msgBoxInvalidCitizenSpDlg').show();
        }
    }
}

function closeMessageCitizenDialog(msgDlgName, inputId){
    PF(msgDlgName).hide();
    $("#"+inputId).focus();
}

function removeComma(obj) {
    var val = obj.value;
    val = val.replace(/,/g, '');
    obj.value = val;
}

function onClickReadonlyField(obj, readonly){
    if(readonly == true){
        if(obj != undefined){
            obj.blur();
        }
    }
}

function formatNumber(obj) {
    var val = obj.value;
    val = $.trim(val);
    if (val != '') {
        val += '';
        x = val.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '.00';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        obj.value = x1 + x2;
    }
}


/*** KEY PRESS AND KEY DOWN FUNCTION ***/
/* ALLOWED SOME KEY (KEY CODE & CHAR CODE)
 *  8=backspace             9=tab               46=delete
 *  35(0)=end               36(0)=home          37(0)=left arrow
 *  38(0)=up arrow          39(0)=right arrow   40(0)=down arrow
 *  111=fwd slash(num pad)  191=fwd slash       109=dash(num pad)
 */
/*
 * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
 *  33=!       34="    35=#        36=$
 *  37=%       38=&    39='        40=(
 *  41=)       42=*    45=-        47=/
 *  58=:       59=;    60=<        62=<
 *  63=?       64=@    91=[        93=]
 *  94=^       95=_    123={       125=}
 */

function checkAllowKeyNumber(keyCode){
    var validChar = '0123456789';
    var keyChar = String.fromCharCode(keyCode);
    validChar += String.fromCharCode(8);
    if (validChar.indexOf(keyChar) < 0) {
        return false;
    }
    return true;
}

function onKeyPressNumber(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    45=-        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

//    alert("keyPress : " + keyCode);

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    /** ALLOW NUMBER **/
    /*  96-105=number(0-9) */
    if (keyCode > 95 && keyCode < 106) {
        return true;
    } else {
        return checkAllowKeyNumber(keyCode);
    }
}

function onKeyDownNumber(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

//    alert("keyDown : " + keyCode);

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9) */
    if (keyCode > 95 && keyCode < 106) {
        return true;
    } else {
        return checkAllowKeyNumber(keyCode);
    }
}

function checkAllowKeyMoney(keyCode){
    var validChar = '0123456789.,';
    var keyChar = String.fromCharCode(keyCode);
    validChar += String.fromCharCode(8);
    if (validChar.indexOf(keyChar) < 0) {
        return false;
    }
    return true;
}

function onKeyPressMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    45=-        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    188=comma
     *  46=period   190=period
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 188 || keyCode == 46 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyDownMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 144 || keyCode == 110){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    188=comma
     *  46=period   190=period
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 188 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyPressNegMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*                47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    45=(dash, minus)  188=comma
     *  46=period   190=period
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 45 || keyCode == 188 || keyCode == 189 || keyCode == 46 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyDownNegMoney(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 109 || keyCode == 110 || keyCode == 144 || keyCode == 189){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    109=minus(num pad)  188=comma   189=dash
     *  46=period   190=period
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 109 || keyCode == 188 || keyCode == 189 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyPressText(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    45=-        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     *  3647=฿
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 94 || keyCode == 123 || keyCode == 125 || keyCode == 3647){
        return false;
    }

    return true;
}

function onKeyDownText(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 45 || keyCode == 47 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 94 || keyCode == 123 || keyCode == 125){
        return false;
    }

    return true;
}

function onKeyPressAddress(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*                47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    45=(dash, minus)  188=comma
     *  46=period   190=period
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 45 || keyCode == 47 || keyCode == 188 || keyCode == 189 || keyCode == 46 || keyCode == 190 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyDownAddress(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 109 || keyCode == 110 || keyCode == 144 || keyCode == 189){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  44=comma    109=minus(num pad)  188=comma   189=dash
     *  46=period   190=period          111=slash(num)  191=slash
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 44 || keyCode == 109 || keyCode == 188 || keyCode == 189 || keyCode == 190 || keyCode == 111 || keyCode == 191 ) {
        return true;
    } else {
        return checkAllowKeyMoney(keyCode);
    }
}

function onKeyPressAddressMoo(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 58 || keyCode == 59 || keyCode == 60 || keyCode == 62 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 || keyCode == 123 || keyCode == 125){
        return false;
    }

    return true;
}

function onKeyDownAddressMoo(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    if(keyCode == 8 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46){
        return true;
    }

    return false;
}

function onKeyPressTelNo(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;
    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    36=$
     *  37=%       38=&    39='
     *  40=(       41=)    42=*        47=/
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if( keyCode == 33 || keyCode == 34 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 ||
        keyCode == 40 || keyCode == 41 || keyCode == 42 || keyCode == 47 || keyCode == 58 || keyCode == 59 ||
        keyCode == 60 || keyCode == 62 || keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 ||
        keyCode == 94 || keyCode == 95 || keyCode == 123 || keyCode == 125
        ){
        return false;
    }

    return true;
}

function onKeyDownTelNo(evt){

    var keyCode = evt.keyCode ? evt.keyCode : evt.which;
    if( keyCode == 8 ||
        keyCode == 9 ||
        keyCode == 35 ||
        keyCode == 36 ||
        keyCode == 37 ||
        keyCode == 38 ||
        keyCode == 39 ||
        keyCode == 40 ||
        keyCode == 46 ||
        keyCode == 57 || //3605   ต
        keyCode == 74 || //3656    ่
        keyCode == 86 || //3629    อ
        keyCode == 144 ||
        keyCode == 110){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  35=# 45=-
     *  189=- 109=-
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 45 || keyCode == 35 || keyCode == 109 || keyCode == 189 ) {
        return true;
    } else {
        return checkAllowKeyTelNo(keyCode);
    }

    return false;
}

function checkAllowKeyTelNo(keyCode){
    var validChar = '0123456789#-';
    var keyChar = String.fromCharCode(keyCode);
    validChar += String.fromCharCode(8);
    if (validChar.indexOf(keyChar) < 0) {
        return false;
    }
    return true;
}


function onKeyPressHomeNo(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;
    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='
     *  40=(       41=)    42=*
     *  58=:       59=;    60=<        62=<
     *  63=?       64=@    91=[        93=]
     *  94=^       95=_    123={       125=}
     */

    if( keyCode == 33 || keyCode == 34 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 ||
        keyCode == 40 || keyCode == 41 || keyCode == 42 || keyCode == 35 || keyCode == 58 || keyCode == 59 ||
        keyCode == 60 || keyCode == 62 || keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 93 ||
        keyCode == 94 || keyCode == 95 || keyCode == 123 || keyCode == 125){
        return false;
    }

    return true;
}

function onKeyDownHomeNo(evt){

    var keyCode = evt.keyCode ? evt.keyCode : evt.which;
    if(keyCode == 8 || keyCode == 9 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 46 || keyCode == 144 || keyCode == 110){
        return true;
    }
    /** ALLOW NUMBER **/
    /*  96-105=number(0-9)
     *  45=-  47=/
     *  189=- 109=-
     *  111=/ 191=/
     */
    if ( (keyCode > 95 && keyCode < 106) || keyCode == 45 || keyCode == 47 || keyCode == 109 || keyCode == 189 || keyCode == 111 || keyCode == 191 ) {
        return true;
    } else {
        return checkAllowKeyHomeNo(keyCode);
    }

    return false;
}

function checkAllowKeyHomeNo(keyCode){
    var validChar = '0123456789/-';
    var keyChar = String.fromCharCode(keyCode);
    validChar += String.fromCharCode(8);
    if (validChar.indexOf(keyChar) < 0) {
        return false;
    }
    return true;
}




function hideWindowsScrollBar() {
    $("body").attr("style", "overflow-y: hidden");
    $('input').each(function() {
        var readonly = $(this).attr("readonly");
        if(readonly && readonly.toLowerCase()!=='false') { // this is readonly
            $(this).focus(function(){
                $(this).blur();
            });
        }
    });
}
function showWindowsScrollBar() {
    $("body").attr("style", "overflow-y: scroll");
}

function onCheckRightClick(event){
    if(event.button==2)
    {
        return false;
    }
}

/*function onCheckEnter(obj, event){
    if(event.keyCode == 13){
        $(obj).attr("disabled","disabled");
        return true;
    }
}*/


function handleDialogValidation(args, widgetVarName) {
    if (isValidateComplete(args)) {
        var name = widgetVarName;
        PF(name).hide();
    }
    else
    {
        var name = widgetVarName;
        PF(name).show();
    }
}

// Credit Facility Propose Credit Dialog
function onOneClick(buttonID, isDisable){
     document.getElementById(buttonID).disabled=isDisable;
}

function onKeyPressAccountName(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    43=+        44=,
     *  58=:       59=;    60=<        61='='
     *  62=<       63=?    64=@        91=[
     *  93=]       92=|
     *  94=^       95=_    123={       125=}
     *  124=|      3665-3673 = 0-๙    3647=฿
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 43 || keyCode == 44 || keyCode == 58 || keyCode == 59 || keyCode == 60 ||keyCode == 61 || keyCode == 62 || keyCode == 125 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 92 || keyCode == 93 || keyCode == 94 || keyCode == 95 || keyCode == 123 || keyCode == 124 ||
        keyCode == 3647 ||(keyCode > 3664 && keyCode < 3674) || keyCode == 48 || (keyCode > 48 && keyCode < 58) ){
        return false;
    }

    return true;
}

function onKeyDownAccountName(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     {  16=Ctrl  9=Tab  35=End 36=Home 37=Left Arrow 38=Up Arrow 39=Right Arrow 40=Down Arrow
     *
     */

    if(keyCode == 16 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40){
        return false;
    }

    return true;
}

function onKeyPressName(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    43=+        44=,
     *  58=:       59=;    60=<        61='='
     *  62=<       63=?    64=@        91=[
     *  93=]       92=|    45=-
     *  94=^       95=_    123={       125=}
     *  124=|      3665-3673 = 0-๙    3647=฿
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 43 || keyCode == 44 || keyCode == 58 || keyCode == 59 || keyCode == 60 ||keyCode == 61 || keyCode == 62 || keyCode == 125 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 92 || keyCode == 93 || keyCode == 94 || keyCode == 95 || keyCode == 123 || keyCode == 124 || keyCode == 45 ||
        keyCode == 3647 ||(keyCode > 3664 && keyCode < 3674) || keyCode == 48 || (keyCode > 48 && keyCode < 58) ){
        return false;
    }

    return true;
}

function onKeyDownName(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     {  16=Ctrl  9=Tab  35=End 36=Home 37=Left Arrow 38=Up Arrow 39=Right Arrow 40=Down Arrow
     *
     */

    if(keyCode == 16 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40){
        return false;
    }

    return true;
}

function onKeyPressName2(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     *  33=!       34="    35=#        36=$
     *  37=%       38=&    39='        40=(
     *  41=)       42=*    43=+        44=,
     *  58=:       59=;    60=<        61='='
     *  62=<       63=?    64=@        91=[
     *  93=]       92=|
     *  94=^       95=_    123={       125=}
     *  124=|      3647=?
     */

    if(keyCode == 33 || keyCode == 34 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 ||
        keyCode == 41 || keyCode == 42 || keyCode == 43 || keyCode == 44 || keyCode == 58 || keyCode == 59 || keyCode == 60 ||keyCode == 61 || keyCode == 62 || keyCode == 125 ||
        keyCode == 63 || keyCode == 64 || keyCode == 91 || keyCode == 92 || keyCode == 93 || keyCode == 94 || keyCode == 95 || keyCode == 123 || keyCode == 124 || keyCode == 45 ||
        keyCode == 3647){
        return false;
    }

    return true;
}

function onKeyDownName2(evt){
    var keyCode = evt.keyCode ? evt.keyCode : evt.which;

    /** CHECK SPECIAL CHARACTER **/
    /*
     * SPECIAL CHARACTER (KEY CODE & CHAR CODE)
     {  16=Ctrl  9=Tab  35=End 36=Home 37=Left Arrow 38=Up Arrow 39=Right Arrow 40=Down Arrow
     *
     */

    if(keyCode == 16 || keyCode == 9 || keyCode == 35 || keyCode == 36 || keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40){
        return false;
    }

    return true;
}

function is_defined(varname) {
	return eval('typeof('+varname+')') != 'undefined';
}
function handlePostAppOnSending() {
	if (is_defined('p_postapp_loading_dialog'))
		p_postapp_loading_dialog.show();
}

function handleOnStartOpenCfmDlg() {
	if (is_defined('p_loading_dialog'))
		p_loading_dialog.show();
}
function handleOnCompleteOpenCfmDlg(args,cfmDialog) {
	if (is_defined('p_loading_dialog'))
		p_loading_dialog.hide();
	if (isValidateComplete(args)) {
		cfmDialog.show();
	} else {
		if (is_defined('p_result_dialog'))
			p_result_dialog.show();	
	}
}
function isValidateComplete(args) {
	if (!args)
		return false;
	return !args.validationFailed;
}

function collapseDetail() {
    var currentState = $("#header_collapse").attr("class");
    if (currentState == 'close') {
        $("#header_collapse").removeAttr("class");
        $("#header_collapse").attr("class", "open");
        $("#header_information").fadeOut();
    } else {
        $("#header_collapse").removeAttr("class");
        $("#header_collapse").attr("class", "close");
        $("#header_information").fadeIn();
    }
}

function wireUpEvents() {
    var out = false;
    var refresh = false;
    $("body").mouseover(function(){
        out=false;
    }).mouseout(function(){
        out=true;
    });

    $(document).keydown(function(e) {
        if((e.which == 82 && e.ctrlKey)   //Ctrl+R, F5, Enter, backspace
            || e.which == 116
            || e.which == 13
            || e.which == 8) {
            refresh = true;
        }
    });

    $(window).bind('beforeunload', function(e){
        if(out && !refresh)
        {
            return "WARNING!!! You are attempting to exit without log out.\nPlease stay on this page and log out first."
        }
    });
}