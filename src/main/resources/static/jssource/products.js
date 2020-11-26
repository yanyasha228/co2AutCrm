$(function () {
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

    $(document).on('show.bs.modal', '#correctProductAmountModal', function (event) {

        var referrerItem = $(event.relatedTarget);

        var modal = $(this);


        var productId = referrerItem.data('product-id');
        var productName = referrerItem.data('product-name');
        var productPreviousAmmount = referrerItem.data('previous-amount');


        modal.find('#correctProductAmountModalProductAmountLabel').text('Текущее кол-во : ' + productPreviousAmmount);
        modal.find('#correctProductAmountModalProductNameLabel').text(productName);
        modal.find('#inputProductNameModal').val(productName);
        modal.find('#inputProductAmountModal').val(productPreviousAmmount);
        modal.find('#correctProductAmountButton').attr('data-product-id', productId);


    });


    $(document).on('click', '#correctProductAmountButton', function (ev) {

        var amount = $('#inputProductAmountModal').val();
        var description = $('#inputProductCorrectionDescriptionModal').val();
        var productId = $(this).data('product-id');

        var dFs = {
            amount: amount,
            description : description
        };

        $.ajax({
            url: location.origin + "/rest/products/" + productId + "/correctAmount",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#changeWholeSalePriceModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#changeWholeSalePriceModal').modal('hide');
            location.reload();
        });

    });

    $(document).on('show.bs.modal', '#addProductModal', function (event) {
    });


    $(document).on('click', '#addProductLink', function (ev) {

        var productId = $('#inputProductId').val();
        var dFs = {
            id: productId
        };

        $.ajax({
            url: location.origin + "/rest/products/add",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#addProductModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#addProductModal').modal('hide');
            location.reload();
        });

    });

    $(document).on('show.bs.modal', '#changeWholeSalePriceModal', function (event) {

        var referrerItem = $(event.relatedTarget);

        var modal = $(this);


        var productId = referrerItem.data('product-id');
        var productName = referrerItem.data('product-name');
        var productCurrentWholeSalePrice = referrerItem.data('previous-wholesale-price');


        modal.find('#changeWholeSalePriceModalProductNameLabel').text(productName);
        modal.find('#inputNewWholeSalePrice').val(productCurrentWholeSalePrice);
        modal.find('#changeWholeSalePriceButton').attr('data-product-id', productId);


    });


    $(document).on('click', '#changeWholeSalePriceButton', function (ev) {

        var wholeSalePriceNew = $('#inputNewWholeSalePrice').val();
        var productId = $(this).data('product-id');

        var dFs = {
            wholeSalePrice: wholeSalePriceNew
        };

        $.ajax({
            url: location.origin + "/rest/products/" + productId + "/edit",
            dataType: 'json',
            type: 'POST',
            data: dFs
        }).done(function (d) {
            $('#changeWholeSalePriceModal').modal('hide');
            location.reload();
        }).fail(function () {
            $('#changeWholeSalePriceModal').modal('hide');
            location.reload();
        });

    });

    $(document).mouseup(function (e) {
        var searcResOrdList = $("#searchProductResult");
        if (searcResOrdList.has(e.target).length === 0) {
            searcResOrdList.empty();
        }
    });

    $(document).on('click', '#orderLineItem', function (e) {

        // var prodEditLink = location.origin + "/supplies/create?productId=" + $(this).data('prodid');
        // window.open(prodEditLink, "_top");

        var productName = $(this).find('#prodNamePar').text();

        $('#productNameSearchInput').val(productName);
        $('#searchInputForm').submit();

    });


    $(document).on('keydown', '#productNameSearchInput', function (e) {

        var searchList = $(this).siblings("#searchProductsList");

        var searchField = $(this).val();

        searchField = encodeRequestReservedSymbols(searchField);

        if (e.keyCode == 13) {
            e.preventDefault();
            var activeItem = searchList.children('.active:first');
            if (activeItem.length <= 0) {

                $('#searchInputForm').submit();
                return;
            } else {
                var prodEditLink = location.origin + "/supplies/create?productId=" + activeItem.data('prodid');
                window.open(prodEditLink, "_top");
                return;
            }

        }

        if (e.keyCode == 40 || e.keyCode == 38) {
            arrowActiveItemHandling(e, searchList, $(this));

        } else {
            searchList.html('');
//////Validirovat searchSend !!!!!!!!!!!!!!!!
            if (searchField.length > 1) {
                $.getJSON(location.origin + "/rest/products/?nonFullProductName=" + searchField, function (data) {
                    $.each(data, function (key, value) {
                        var prodStatus;

                        if (value.availability == true) {
                            prodStatus = "text-success";
                        } else {
                            prodStatus = "text-danger";
                        }
                        if (value.validationStatus === true) prodStatus = "test-success";

                        searchList.append('<li class="list-group-item product-search-editor-res-item" tabindex ="' + key + '" id="orderLineItem" data-prodid = "' + value.id + '"><div class="row"' +
                            '><div class="col-4"><img src="' + value.main_image + '" height="60" width="80" class="img-thumbnail"></div>' +
                            '<div class="col-1"><span class="status ' + prodStatus +
                            '">&bull;</span></div>' +
                            '<div class="col-7"> <p id="prodNamePar" style="overflow: hidden; text-overflow: ellipsis;">' + value.name + '</p> </div>' +
                            '</div></li>');


                    });

                });
            }
        }

    });

});



