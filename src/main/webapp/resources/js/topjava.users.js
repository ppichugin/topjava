const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditableUser(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});

function makeEditableUser(datatableApi) {
    makeEditable(datatableApi)
    $('#datatable :checkbox').change(function () {
        enable($(this));
    });
}

function enable(checkbox) {
    const enable = checkbox.is(":checked");
    const id = checkbox.closest('tr').attr("id");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: {enabled: enable},
    }).done(function () {
        checkbox.closest('tr').attr("data-user-enabled", enable);
        successNoty(enable ? "Account enabled" : "Account disabled");
    }).fail(function () {
        checkbox.prop('checked', !enable);
        successNoty("Nothing changed");
    })
}