function clickIE4(){
    if (event.button==2){
        return false;
    }
}

function clickNS4(e){
    if (document.layers||document.getElementById&&!document.all){
        if (e.which==2||e.which==3){
            return false;
        }
    }
}

if (document.layers){
    document.captureEvents(Event.MOUSEDOWN);
    document.onmousedown=clickNS4;
}else if (document.all&&!document.getElementById){
    document.onmousedown=clickIE4;
}

document.oncontextmenu = new Function("return false;")

function disableSelect(e){
    if (document.all){
        return false;
    }
}

function reEnable(){
    return true;
}

document.onselectstart=new Function ("return false") //if IE4+

if (window.sidebar){ //if NS6
    document.onmousedown = disableSelect;
    document.onclick = reEnable;
}