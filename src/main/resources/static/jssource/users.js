$(function () {

    $(document).on('show.bs.modal', '#addUserModal', function (event) {
    });



    $(document).on('click', '#addUserLink', function (ev) {

        var userEmail = $('#inputUserEmail').val();
        var userPass = $('#inputUserPassword').val();
        var dFs= {
            email : userEmail,
            pass : userPass
        };

        $.ajax({
            url: location.origin + "/rest/settings/users/add",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#addUserModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#addUserModal').modal('hide');
            location.reload();
        });

    });

});