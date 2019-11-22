/* 该组件依赖Jquery.js */
(function (app) {

    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * 例子：
     * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
     * @param fmt
     * @returns {*}
     * @constructor
     */
    Date.prototype.Format = function (fmt) { //author: zhangyong
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

    //序列化form表单
    jQuery.fn.uiSerializeForm = function (onlyOneValue) {
        var o = {};
        var a = this.serializeArray();
        jQuery.each(a, function (index, li) {
            if (o[li.name] && true !== onlyOneValue) {
                o[li.name] = o[li.name] + "," + li.value;
            } else {
                o[li.name] = li.value;
            }
        });
        return o;
    };

    //反序列化表单
    jQuery.fn.uiDeSerializeForm = function (obj) {
        var a = this.serializeArray();
        $.each(a,function(index,li){
            var inputs = $("[name='"+li.name+"']");
            if(inputs.length === 1) {
                if(typeof(obj[li.name]) !== "number") {
                    inputs.val(obj[li.name] || '').trigger("change");
                }else{
                    inputs.val(obj[li.name]).trigger("change");
                }
            }else{
                var i = 0;
                for(; i < inputs.length; i++) {
                    if("radio" === inputs[i].type) {
                        if(inputs[i].value === "" + obj[li.name]) {
                            $("[name='" + inputs[i].name + "'][value='" + inputs[i].value + "']").attr("checked",true);
                        }else{
                            $("[name='" + inputs[i].name + "'][value='" + inputs[i].value + "']").removeAttr("checked");
                        }
                    } else if("checkbox" === inputs[i].type) {
                        var checks = ("" + obj[li.name]).split(",");
                        if(checks.indexOf(inputs[i].value) > -1) {
                            $("[name='" + inputs[i].name + "'][value='" + inputs[i].value + "']").attr("checked",true);
                        }else{
                            $("[name='" + inputs[i].name + "'][value='" + inputs[i].value + "']").removeAttr("checked");
                        }
                    } else {
                        if(typeof(obj[li.name]) !== "number") {
                            $(inputs[i]).val(obj[li.name] || '');
                        }else{
                            $(inputs[i]).val(obj[li.name]);
                        }
                    }
                }
            }
        })
    };

    app.windowHeight = function () {
        var myHeight = 0;
        if (typeof(window.innerHeight) === 'number') {
            //Non-IE
            myHeight = window.innerHeight;
        } else if (document.documentElement && (document.documentElement.clientHeight)) {
            //IE 6+ in 'standards compliant mode'
            myHeight = document.documentElement.clientHeight;
        } else if (document.body && (document.body.clientHeight)) {
            //IE 4 compatible
            myHeight = document.body.clientHeight;
        }
        return myHeight;
    }

    app.postJson = function (options) {
        options.cache = false;
        options.dataType = "json";
        options.contentType = "application/json; charset=utf-8";
        options.type = "POST";
        options.data = JSON.stringify(options.data || {});
        $.ajax(options);
    }
})(window.app = {});