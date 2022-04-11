let isCounting = false;
let i = 0;

function clockInit(){
    i = 0;
    $("#timing").text("00:00");
}

function clockShow(){
    var min = i / 60;
    var sec = i % 60;
    min = parseInt(min);
    if(sec < 10){
        $("#timing").text("0" + min.toString() + ":0" + sec.toString());
    }else{
        $("#timing").text("0" + min.toString() + ":" + sec.toString());
    }

}

function clockIncrease(){
    i = i + 1;
    clockShow();
}