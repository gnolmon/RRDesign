//Load time ads loaded
var now = new Date;
var time = Date.UTC(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
window.document.getElementById("timeload").innerHTML = time;
//process redirect when ads clicked
function process_redirect(o) {
    var time_load = window.document.getElementById("timeload").innerHTML;
    var now = new Date;
    var time_click = Date.UTC(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours(), now.getMinutes(), now.getSeconds());
    var href = o.getAttribute("href") + "/" + time_load + "/" + time_click;
    window.open(href, "_blank");
    return false;
}
//process redirect when ads clicked flash
function process_redirect1(o) {
    return;
}
//=====================DISPLAY TYPE ADS============//
function Set_Cookie(name, value, expires, path, domain, secure) {
    var today = new Date();
    today.setTime(today.getTime());

    if (expires) {
        expires = expires * 1000 * 60 * 60 * 12;
    }
    var expires_date = new Date(today.getTime() + (expires));
    document.cookie = name + "=" + escape(value) +
            ((expires) ? ";expires=" + expires_date.toGMTString() : "") +
            ((path) ? ";path=" + path : "") +
            ((domain) ? ";domain=" + domain : "") +
            ((secure) ? ";secure" : "");
}

function Get_Cookie(name) {

    var start = document.cookie.indexOf(name + "=");
    var len = start + name.length + 1;
    if ((!start) &&
            (name != document.cookie.substring(0, name.length))) {
        return null;
    }
    if (start == -1)
        return null;
    var end = document.cookie.indexOf(";", len);
    if (end == -1)
        end = document.cookie.length;
    return unescape(document.cookie.substring(len, end));
}

function Delete_Cookie(name, path, domain) {
    if (Get_Cookie(name))
        document.cookie = name + "=" +
                ((path) ? ";path=" + path : "") +
                ((domain) ? ";domain=" + domain : "") +
                ";expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

function popunder(linkWeb) {
    if (Get_Cookie('BeadsDisplayType')) {
        Delete_Cookie('BeadsDisplayType', '', '');
//        return false;
    }
//    else {
    Set_Cookie('BeadsDisplayType', 'BeadsDisplayType PopUnder', '1', '/', '', '');
    urls = linkWeb;
    var url = urls[Math.floor(Math.random() * urls.length)];
    var params = 'width=' + screen.width;
    params += ', height=' + screen.height;
    params += ', toolbar=1,scrollbars=1,location=1,statusbar=1,menubar=1,resizable=1,top=0, left=0,scrollbars=yes';
    params += ', fullscreen=yes';

    var pop = window.open(url, 'window', params);
    pop.blur();
    window.focus();
//        return false;
//    }
}

function popin() {
//    var ever = Get_Cookie("BeadsPopin");
//    if (ever){
//        document.getElementById('BeadsPopup').style.display='none';
//        return false;
//    }else{
        document.getElementById('BeadsPopup').style.display='block';
//        $("#").css("display", "block");
//        var $scrollingDiv = $("#BeadsLightbox");
//        $(window).scroll(function() {
//            Set_Cookie("BeadsPopin", 1, 1);
//            if ($(window).scrollTop() > 100)
//            {
//                $scrollingDiv.stop().animate({
//                    "marginTop": ($(window).scrollTop() + 20) + "px"
//                }, "slow");
//            }
//            else
//            {
//                $scrollingDiv.stop().animate({
//                    "marginTop": "20px"
//                }, "slow");
//            }
//        });
        
//    }
}

function closePopin() {
//    Set_Cookie("BeadsPopin", 1, 1);
    var ever = Get_Cookie("BeadsPopin");
    if (!ever){
        Set_Cookie("BeadsPopin", 1, 1/12);
    }
    document.getElementById('BeadsPopup').style.display='none';
}

var beadsDivs = ['beads_none_content','beads_content'];

function beadsHideDivs(){
    for (var i=0; i<beadsDivs.length; i++){
        if(document.getElementById(beadsDivs[i])!= undefined){
            document.getElementById(beadsDivs[i]).style.display = 'none';
        }
    }
}

function beadsShowDiv() {
    beadsHideDivs(); //hide them all before we show the next one.
    var randomDiv = beadsDivs[Math.floor(Math.random()*beadsDivs.length)];
    if(document.getElementById(randomDiv)!= undefined){
        document.getElementById(randomDiv).style.display = 'block';
    }

    setTimeout(beadsShowDiv,1000); //set a delay before showing the next div
}

beadsShowDiv();


