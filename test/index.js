var path = 'http://cavepot.com:8080/comed/#/authenticate';

var index = (function (srvCompany, srvRequest) {
    var self = {};


    self.init = function () {
        $("#test").click(function () {
            const clientId = $("#client").val();
            if (clientId != "") {
                path = path + "/" + clientId;
            }
            const patientId = $("#patient").val();
            if (patientId != "") {
                path = path + "/" + patientId;
            }
            window.open(path, "_blank");
        })
    }

    return self;
})();

$(document).ready(function () {
    index.init();
});

