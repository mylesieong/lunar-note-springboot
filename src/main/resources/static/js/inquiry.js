$(document).ready(function() {

    $("#invoke").click(function() {
        
        $.ajax({
            // url: "inquiry"
            url: "inquiry?key=" + $('#keyin').val()
        }).then(function(data) {
            // $('.out-id').append(data.id);
            // $('.out-sysId').append(data.sysId);
            // $('.out-sysName').append(data.sysName);
            // $('.out-sysStatus').append(data.sysStatus);
            // $('.out-sysReturnCode').append(data.sysReturnCode);
            $('#out-id').append(data.id);
            $('#out-sysName').append(data.sysName);
            $('#out-sysStatus').append(data.sysStatus);
        });
        
    });

});

