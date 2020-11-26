$(function () {
    String.prototype.replaceAll = function (search, replace) {
        return this.split(search).join(replace);
    };

    var depProductLinesBlock = "<div id=\"dependentProducts\" class=\"form-group col-md-12\">\n" +
        "                                    <label for=\"depProductLines\">Зависимые товары:</label>\n" +
        "                                    <div class=\"controls-1\">\n" +
        "\n" +
        "                                        <div id=\"dependentProductLine\" class=\"entry\">\n" +
        "                                            <div class=\"form-row\">\n" +
        "\n" +
        "                                                <div class=\"form-group col-md-8\" id=\"inputDepProdNameDiv\">\n" +
        "\n" +
        "                                                    <input hidden\n" +
        "                                                           type=\"number\"\n" +
        "                                                           class=\"form-control\"\n" +
        "                                                           id=\"productLineId\"\n" +
        "                                                           name=\"productLineIdInputDependent\">\n" +
        "\n" +
        "                                                    <label for=\"productLineProductName\">Товар</label>\n" +
        "                                                    <input class=\"form-control is-invalid\"\n" +
        "                                                           id=\"productLineProductName\" type=\"text\"/>\n" +
        "                                                    <div id=\"searchOrderLineProductsList\">\n" +
        "                                                        <ul class=\"list-group product-search-editor-res\"\n" +
        "                                                            id=\"searchProductResult\"></ul>\n" +
        "                                                    </div>\n" +
        "                                                </div>\n" +
        "\n" +
        "                                                <div class=\"form-group col-md-2\">\n" +
        "                                                    <label for=\"productLineProductQua\">Кол-во</label>\n" +
        "                                                    <input class=\"form-control is-invalid\"\n" +
        "                                                           name=\"productLineProductQuaDependent\"\n" +
        "                                                           id=\"productLineProductQua\" type=\"number\"/>\n" +
        "                                                </div>\n" +
        "\n" +
        "                                                <div class=\"form-group col-md-1\">\n" +
        "                                                            <span class=\"input-group-btn\">\n" +
        "                                                            <button class=\"btn btn-success btn-add\" type=\"button\">\n" +
        "                                                                <span class=\"glyphicon glyphicon-plus\"></span>\n" +
        "                                                            </button>\n" +
        "                                                        </span>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "                                        </div>\n" +
        "\n" +
        "                                    </div>\n" +
        "\n" +
        "                                </div>";


    $(document).on('click', '.btn-add', function (e) {
        e.preventDefault();

        $("#searchProductResult").empty();


        var controlForm = $(this).parents('.controls-1:first'),
            currentEntry = $(this).parents('.entry:first'),
            newEntry = $(currentEntry.clone()).prependTo(controlForm);

        newEntry.find('input').val('');
        newEntry.find('input').attr("class", "form-control is-invalid");

        controlForm.find('.entry:not(:last) .btn-add')
            .removeClass('btn-add').addClass('btn-remove')
            .removeClass('btn-success').addClass('btn-danger')
            .html('<span class="glyphicon glyphicon-minus"></span>');
    }).on('click', '.btn-remove', function (e) {
        var entryToRemove = $(this).parents('.entry:first');
        entryToRemove.remove();
        e.preventDefault();
        return false;
    });

    $('#orderForm').on('keyup keypress', function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });


    $('#orderForm').submit(function (e) {
        e.preventDefault();
        if (validateSubmit()) {
            this.submit();
        }

    });

    $(document).on('change', '#selectSupplyStatus', function (e) {

        if ($(this).val() === "WORKSHOP") {
            $('#depProductLines').append(depProductLinesBlock);
        } else $('#dependentProducts').remove();

    });


    $(document).on('click', '#productLineItem', function (e) {

        var searchList = $(this).parent();
        var searchInput = $(this).closest('.form-group').find('#productLineProductName');

        validateAndCloseOrderLineList(searchList, searchInput, $(this));

    });


    $(document).mouseup(function (e) {
        var searcResOrdList = $("#searchOrderLineProductsList");
        if (e.target != searcResOrdList[0] && !searcResOrdList.has(e.target).length) {
            searcResOrdList.empty();
        }
    });


    $(document).on('keydown', '#productLineProductName', function (e) {

        var searchList = $(this).siblings("#searchOrderLineProductsList");

        var hui = searchList.children("#searchProductResult");

        var searchField = $(this).val();

        searchField = encodeRequestReservedSymbols(searchField);

        if (e.keyCode == 13) {
            e.preventDefault();
            var activeItem = searchList.children('.active:first');
            validateAndCloseOrderLineList(searchList, $(this), activeItem);
            return;

        }

        if (e.keyCode == 40 || e.keyCode == 38) {
            arrowActiveItemHandling(e, searchList, $(this));

        } else {
            searchList.html('');
//////Validirovat searchSend !!!!!!!!!!!!!!!!
            if (searchField.length > 1) {
                $.getJSON(location.origin + "/rest/products/?nonFullProductName=" + searchField, function (data) {
                    $.each(data, function (key, value) {
                        searchList.append('<li class="list-group-item product-search-editor-res-item" tabindex ="' + key + '" id="productLineItem" data-prodid = "' + value.id + '"><div class="row"' +
                            '><div class="col-4"><img src="' + value.main_image + '" height="60" width="80" class="img-thumbnail"></div>' +
                            '<div class="col-8"> <p id="prodNamePar" style="overflow: hidden; text-overflow: ellipsis;">' + value.name + '</p> </div>' +
                            '</div></li>');


                    });

                });
            }
        }

    });

//FOCUSOUT_EVENT


    /////////////////////////

    $(document).on('focusout', '#productLineProductName', function (e) {

        var searchField = $(this).val();
        var inputField = $(this);
        var previousItemDomId = inputField.closest("div.form-row").find("input[id='productLineId']");

        searchField = encodeRequestReservedSymbols(searchField);

        $.getJSON(location.origin + "/rest/products?productName=" + searchField).done(function (d) {

            previousItemDomId.val(d.id);
            inputField.val(d.name);
            inputField.attr('class', 'form-control is-valid');


        }).fail(function () {
            inputField.attr('class', 'form-control is-invalid');
            previousItemDomId.val(0);

        });
    });

    $(document).on('change', '#productLineProductQua', function (event) {

        var inputQuaFieldValue = Number($(this).val());

        if (0 < inputQuaFieldValue) {
            $(this).attr('class', 'form-control is-valid');
        } else {
            $(this).attr('class', 'form-control is-invalid');
        }


    });

    $(document).on('focusout', '#productLineProductQua', function (eve) {


        var inputQuaField = $(this);
        var inputQuaFieldValue = inputQuaField.val();

        if (0 < inputQuaFieldValue) {
            $(this).attr('class', 'form-control is-valid');
        } else {
            $(this).attr('class', 'form-control is-invalid');
        }

    });


// function sumDouble(input) {
//
//
//     })
//
// }


    ///////!!!!!!!!!!!!!!!!!!!!!!!!!!!Watch this!!!!!!!!!!!!!!!!!!!!!!!


    function validateAndCloseOrderLineList(searchList, searchInput, selectedItem) {

        var productId = selectedItem.data('prodid');
        var entryClass = searchInput.parents('.entry:first');
        var productOrderLineIdInput = selectedItem.closest("div.form-row").find("input[id='productLineId']");
        // ///Deleting previous object from model
        $.getJSON(location.origin + "/rest/products/" + productId).done(function (data) {

            productOrderLineIdInput.val(data.id);
            searchInput.val(data.name);

            searchInput.attr('class', 'form-control is-valid');

        }).fail(function () {

            productOrderLineIdInput.val(0);
            searchInput.attr('class', 'form-control is-invalid');
        });


        searchList.empty();

    }


    //validating price depending on userStatus and currency


    //Validate main price for subsequent calculation depending on clientStatus


    ///Method check if field has empty value when PaymentMethod isn't PICKUP amd set appendedClass "is-valid" when hasn't
    //and vice versa
    function checkEmptinessInputFieldDependentOnPaymentMethodAndSetValidClassStatus(inputFieldForValidation) {
        if ($(inputFieldForValidation).val().replace(/\s/g, '') === "" && !($(inputSupplyStatusDom).val() === "PICKUP")) {
            inputFieldForValidation.attr('class', 'form-control is-invalid');
        } else {
            inputFieldForValidation.attr('class', 'form-control is-valid');
        }
    }


    function validateSubmit() {

        var formIsValid = true;

        var entryDomItems = $('#productLines').find('.entry');

        var entryDomDepItems = $('#depProductLines').find('.entry');

        entryDomItems.each(function (i, item) {
            if ($(item).find('#productLineProductName:first').hasClass("is-invalid") ||
                $(item).find('#productLineProductQua:first').hasClass("is-invalid")) {

                formIsValid = false;

            }

        });



        entryDomDepItems.each(function (i, item) {
            if ($(item).find('#productLineProductName:first').hasClass("is-invalid") ||
                $(item).find('#productLineProductQua:first').hasClass("is-invalid")) {

                formIsValid = false;

            }

        });

        return formIsValid;

    }

    // function createElementFromHTML(htmlString) {
    //     var div = document.createElement('div');
    //     div.innerHTML = htmlString.trim();
    //
    //     // Change this to div.childNodes to support multiple top-level nodes
    //     return div.firstChild;
    // }

});