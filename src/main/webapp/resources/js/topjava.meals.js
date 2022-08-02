const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateOnFly: function () {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $("#mealsFilter").serialize()
        }).done(refreshTable);
    }
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function clearFilter() {
    $.get(mealAjaxUrl, function () {
        $("#mealsFilter").trigger("click");
    }).done(refreshTable);
}