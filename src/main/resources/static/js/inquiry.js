$(document).ready(function() {

    $("#invoke").click(function() {
        
        $.ajax({
            // url: "http://rest-service.guides.spring.io/greeting"
            url: "inquiry"
        }).then(function(data) {
            $('.out-id').append(data.id);
            $('.out-sysId').append(data.sysId);
            $('.out-sysName').append(data.sysName);
            $('.out-sysStatus').append(data.sysStatus);
            $('.out-sysReturnCode').append(data.sysReturnCode);
        });
        
    });

});

