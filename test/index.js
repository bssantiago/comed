var url = 'http://localhost:4200/comed/#/authenticate';

var index = (function (srvCompany, srvRequest) {
    var self = {};


    self.init = function () {
        $("#test").click(function () {
            const clientId = $("#client").val();
            var path = url;
            if (clientId != "") {
                path = path + "/" + clientId;
            }
            const patientId = $("#patient").val();
            if (patientId != "") {
                path = path + "/" + patientId;
            }
            window.open(path,'Comed', 'width=1200, height=1000');
            //$('#frame').attr('src', path);
        })
    }

    return self;
})();

$(document).ready(function () {
    index.init();
});

