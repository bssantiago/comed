var url = 'http://localhost:4200/comed/#/authenticate';

var index = (function (srvCompany, srvRequest) {
    var self = {};


    self.init = function () {
        $("#test").click(function () {
            const key = '1234567887654321';
            let path = '/rest/authenticate';
            const d = new Date();
            const n = d.getTime();
            const requestBy = 'Koordinator-test';
            const nonce = '' + n;
            const token = 'V94aW0zBxc5gLpSvjQh0BVcfYN5l/QaL82e2NwpYzBU=';
            const sk = '' + n;
            const clientId = $("#client").val();
            const patientId = $("#patient").val();

            let signature = key + path;

            path = url;
            path = path + "/" + encodeURIComponent(token) + "/" + nonce + "/" + sk + "/" + requestBy

            if (clientId != "") {
                signature = signature + 'clientId' + clientId
            }

            signature = signature + 'nonce' + nonce;

            if (patientId != "") {
                signature = signature + 'patientId' + patientId;
            }

            signature = signature + 'requested-by' + requestBy;
            signature = signature + 'sk' + sk;
            signature = signature + 'token' + token;

            console.log(signature);
            var shaObj = new jsSHA("SHA-256", "TEXT");
            shaObj.update(signature);
            var hash = shaObj.getHash("HEX");

            signature = hash;

            path = path + "/" + signature

            if (clientId != "") {
                path = path + "/" + clientId;
            }

            if (patientId != "") {
                path = path + "/" + patientId;
            }

            const width = window.innerWidth;
            window.open(path, 'Comed', 'width=' + width + ', height=1000');
            //$('#frame').attr('src', path);
        })
    }

    return self;
})();

$(document).ready(function () {
    index.init();
});