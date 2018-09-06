$(document).ready(function () {
    $("#top-ten-container").empty();
    var clientId = localStorage.getItem("clientId")
    var invoices = $.ajax({
        type: 'GET',
        url: "http://localhost:8080/aclient/subscribers/getTenBest/"+clientId,
        headers: {
            "Authorization" : "Bearer "+ localStorage.getItem("token")
        },
        success: function (data) {
            console.log(data);
            var tbody = $("#top-ten-container"),
                props = ["phone", "name", "avgPerMonth", "allTimeTurnover"];
            $.each(data, function (i, data) {
                var tr = $('<tr>');
                $.each(props, function (i, prop) {
                    if (prop==="avgPerMonth" || prop==="allTimeTurnover") {
                        var num = data[prop].toFixed(2);
                        $('<td>').html(num).appendTo(tr);
                    }

                    else
                    $('<td>').html(data[prop]).appendTo(tr);
                });
                tbody.append(tr);
            });

        },
        error: function () {
            console.log("Unsuccessfull request");

        }

    })
    var invoices = $.ajax({
        type: 'GET',
        url: "http://localhost:8080/aclient/invoice/getLastTenPaid/"+clientId,
        headers: {
            "Authorization" : "Bearer "+ localStorage.getItem("token")
        },
        success: function (data) {
            var tbody = $("#bulk-container"),
                props = ["id","subscriberPhone", "telecomServiceType", "telecomServiceSubscriptionPlan", "price", "currency"];
            $.each(data, function (i, data) {
                var tr = $('<tr>');
                $.each(props, function (i, prop) {
                    $('<td>').html(data[prop]).appendTo(tr);
                });
                tbody.append(tr);
            });

        },
        error: function () {
            console.log("Unsuccessful request");

        }

    })
})