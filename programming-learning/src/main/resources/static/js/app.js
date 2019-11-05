(function (app) {
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
})(window.app = {});