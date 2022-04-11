function sourceJmp(sourceName){
    $(".container-fluid").empty();
    let ctn =
        '<div class="mainBlock">\n' +
                        '<div>\n' +
                            '<div class="audioImage" style="display:none">\n'  +
                                '<canvas id="myAreaChart1"></canvas>\n'  +
                            '</div>\n' +
                            '<div class="audioImage">\n' +
                                '<canvas id="myAreaChart2"></canvas>\n' +
                            '</div>\n' +
                            '<div class="audioImage" style="display:none">\n' +
                                '<canvas id="myAreaChart3"></canvas>\n' +
                            '</div>\n' +
                            '<div class="audioPlayer">\n' +
                                    '<audio src="http://114.212.83.227/wav_org.wav" controls="controls" style="width:100%; height:35px;"></audio>\n' +
                            '</div>\n' +
                        '</div>\n' +
                        '<div class="middleDivide"></div>\n'  +
                        '<div class="rightBlock">\n' +
                            '<ul>\n' +
                                '<li>\n' +
                                    '<span>声源：</span>\n' +
                                    '<p id="topic">xxx小组组会</p>\n' +
                                '</li>\n' +
                                '<li>\n' +
                                    '<span>会议日期：</span>\n' +
                                    '<p id="time">xxxx年xx月xx日</p>\n' +
                                '</li>\n' +
                            '</ul>\n' +
                            '<div class="main_button" style="margin-right:10px">\n' +
                                    '转换源:&nbsp;&nbsp;\n' +
                                    '<select style="width:82px"><option value="kdxf">科大讯飞</option><option value="bdsb">百度识别</option><option value="txy">腾讯云</option></select>\n' +
                            '</div>\n' +
                            '<div class="main_button" style="margin-right:10px">\n' +
                                '起止时间:&nbsp;&nbsp;\n' +
                                '<input style="width:45px; height:20px;" placeholder="00:00">\n' +
                                '~&nbsp;' +
                                '<input style="width:45px; height:20px;" placeholder="00:00">\n' +
                                '<button class="btn btn-primary btn-user btn-block"  style="width: auto; margin-left: 60px;">转换</button>\n' +
                            '</div>\n' +
                            '<textarea></textarea>\n' +
                        '</div>\n' +
        '</div>';
        $(".container-fluid").append(ctn);
        var ctx2 = document.getElementById("myAreaChart2");
        var myLineChart2 = new Chart(ctx2, config2);
}