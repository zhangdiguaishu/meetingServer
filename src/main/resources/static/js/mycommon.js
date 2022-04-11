/*
 * Copyright (c) 2021. This file is created and edited by Guoqing, all rights are reserved by him.
 */



    $(document).ready(function() {
    document.getElementById("username").innerHTML=""+sessionStorage.getItem("realname");
    document.getElementById("welcome-name").innerHTML="欢迎\<br\>"+sessionStorage.getItem("realname");
    document.getElementById("addpatient").onclick=patientRegister;
});

    /*注册*/
    var patientRegister=function(){
    $(".container-fluid").empty();
    let tr=' <div class="card-body p-0">\n' +
    '                <!-- Nested Row within Card Body -->\n' +
    '                <div class="row">\n' +
    '                    <div class="col-lg-7">\n' +
    '                        <div class="p-5">\n' +
    '                            <div class="text-center">\n' +
    '                                <h1 class="h4 text-gray-900 mb-4">新建会议</h1>\n' +
    /*'                                 <img class="hddd" src="img/undraw_profile_1.svg" >' +*/
    '                            </div>\n' +
    '                            <form class="patient" id="patient-form" method="post">\n' +
    '                                <div class="form-group">\n' +
    '\n' +
    '                                    <input type="text" name="realname" class="form-control form-control-user" id="realname"\n' +
    '                                           placeholder="会议主题">\n' +
    '                                </div>\n' +
    '                                <div class="form-group">\n' +
    '                                    <label class="btn btn-info">\n' +
    '                                        录制方式\n' +
    '                                    </label>\n' +
    '                                    <label class="btn btn-info btn_red">\n' +
    '                                        <input name="gender" value="0" type="radio"> 在线录制\n' +
    '                                    </label>\n' +
    '                                    <label class="btn btn-info">\n' +
    '                                        <input checked="checked" name="gender"  value="1" type="radio"> 本地上传\n' +
    '                                    </label>\n' +
    '                  <input type="file" width=60>'+
        '                                    <label class="btn btn-info">\n' +
        '                                        预计人数&nbsp;&nbsp;<input style="width:80px"  value="" type="text">\n' +
        '                                    </label>\n' +
        '<p></p>'+
    '                                    <label class="btn btn-info">\n' +
        '                                        采样率\n' +
        '                                    </label>\n' +
    '                                    <label class="btn btn-info">\n' +
    '										<select style="width:120px" checked="checked" name="gender"  value="1" type="radio"><option value="40k">40k</option><option value="44k">44k</option><option value="48k">48k</option></select> \n' +
    '                                    </label>\n' +
    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
    '                                    <label class="btn btn-info">\n' +
    '                                         BF方式\n' +
    '                                    </label>\n' +
    '                                    <label class="btn btn-info">\n' +
    '                                        <input checked="checked" name="gender"  value="1" type="radio"> BF1\n' +
    '                                    </label>\n' +
    '                                    <label class="btn btn-info">\n' +
    '                                        <input checked="checked" name="gender"  value="1" type="radio"> BF2\n' +
    '                                    </label>\n' +
    '                                    <label class="btn btn-info">\n' +
        '                                        <input checked="checked" name="gender"  value="1" type="radio"> BF2\n' +
        '                                    </label>\n' +

    '                                </div>\n' +
    '                                <div class="form-group">\n' +
    '                                <input type="date" name="birthday" value="" class="form-control form-control-user" placeholder="日期"/>\n' +
    '                                </div>\n' +
    '                                <div class="form-group">\n' +
        '\n' +
        '                                    <input type="text" name="phone" class="form-control form-control-user" id="phone"\n' +
        '                                           placeholder="与会人员名单">\n'  +
        '                                </div>\n' +

    '                                <div class="form-group">\n' +
    '                                    <textarea name="shortnote" rows="2" cols="40" class="form-control form-control-user" placeholder="会议简介"></textarea>\n'  +
    '                                </div>\n' +
    '\n' +
    '                                <div class="form-group">\n' +
    '                                    <textarea name="description" rows="5" cols="40" class="form-control form-control-user" placeholder="备注"></textarea>\n'  +
    '                                </div>\n' +
    '\n' +
    '                                <div class="form-group">\n' +
    '                                    <input type="button" value="新建" class="btn btn-primary btn-user btn-block"\n' +
    '                                           id="patient-reg">\n' +
    '                                </div>\n' +
    '                            </form>\n' +
    '                            <hr>\n' +
    '\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>';

    $(".container-fluid").append(tr);
    document.getElementById("patient-reg").onclick=registerbtn;
}
    var registerbtn=function() {
        // alert($("#patient-form").serialize())
        $.ajax({
            url: "/users/addpatient",
            type: "POST",
            data: $("#patient-form").serialize(),
            dataType: "json",
            success: function(json) {
                if (json.state == 200) {
                    alert("登记成功！");
                    location.href = "index.html";
                } else {
                    alert("登记失败！" + json.message);
                }
            },
            error: function (xhr){
                alert("会议创建成功");
                location.href = "dataPlay.html";
            }
        });

}