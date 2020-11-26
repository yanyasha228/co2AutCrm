
$(function () {

    $(document).on('show.bs.modal', '#nullifySalaryDecreaseBalanceModal', function (event) {

        var referrerButton = $(event.relatedTarget); // Button that triggered the modal

        var managerId = referrerButton.data('manager-id');
        var managerName = referrerButton.data('manager-name');// Extract productId from data-* attributes


        var modal = $(this);
        modal.find('#nullifySalaryDecreaseBalanceManagerName').text(managerName);
        modal.find('#nullifySalaryDecreaseBalanceModalButton').attr('data-manager-id', managerId )
    });


    $(document).on('click', '#nullifySalaryDecreaseBalanceModalButton', function (ev) {

        var managerIdM = $(this).data('manager-id');
        var dFs = {
            managerId: managerIdM
        };

        $.ajax({
            url: location.origin + "/rest/salary/manager/decreases/nullify",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#addSalaryDecreaseModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#addSalaryDecreaseModal').modal('hide');
            location.reload();
        });

    });

});