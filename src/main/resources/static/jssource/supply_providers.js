$(function () {

    $(document).on('show.bs.modal', '#deleteSupplyProviderModal', function (event) {

        var referrerButton = $(event.relatedTarget); // Button that triggered the modal

        var suppProvId = referrerButton.data('supply-provider-id');
        var suppProvName = referrerButton.data('supply-provider-name');// Extract productId from data-* attributes


        var modal = $(this);
        modal.find('#deleteSupplyProviderModalLabel').text(suppProvName);
        modal.find('#deleteSupplyProviderButton').attr('data-supply-provider-id', suppProvId )

    });

    $(document).on('click', '#deleteSupplyProviderButton', function (ev) {

        var suppPId = $(this).data('supply-provider-id');
        var dFs= {
            id : suppPId
        };

        $.ajax({
            url: location.origin + "/rest/supplies/providers/delete",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#deleteSupplyProviderModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#deleteSupplyProviderModal').modal('hide');
            location.reload();
        });

    });

});