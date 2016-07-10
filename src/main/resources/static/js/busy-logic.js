/*
 * Selectors constants declaration
 */
const
collapseSwitchSelector = ".collapse-switch";
const
dialogDivSelector = ".dialog";
const
collapseDivSelector = ".collapse";

const
contentAttribute = "data-content";

/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {

    if (typeof message != 'undefined' && message) {
        messageModal(message);
    }

    // Hide 'dialog' and 'collapse' items clicking outside of them
    $(document).mouseup(
            function(e) {

                var dialog_div = $(dialogDivSelector);

                if (!dialog_div.is(e.target) && dialog_div.has(e.target).length === 0) {
                    dialog_div.hide();
                }

                var collapse_switch = $(collapseSwitchSelector);
                var collapse_div = $(collapseDivSelector);

                if (!collapse_switch.is(e.target) && collapse_switch.has(e.target).length === 0
                        && !collapse_div.is(e.target) && collapse_div.has(e.target).length === 0) {
                    collapse_div.collapse('hide');
                }

            });

    /*
     * Autocomplete of the search bar sending AJAX requests to update the list of companies matching
     * the input value.
     */
    $('.search-bar-text').autocomplete({
        serviceUrl : '/get_company_searches',
        minChars : 2,
        lookupLimit : 10,
        paramName : 'partialName',
        triggerSelectOnValidInput : false,
        transformResult : function(response) {

            return {
                // Convert the json list to javascript object
                suggestions : $.map($.parseJSON(response), function(dataItem) {
                    return {
                        value : dataItem.tradeName,
                        data : dataItem.id
                    };
                })
            };
        },
        onSelect : function(suggestion) {
            window.location.href = "/company/" + suggestion.data;
        }
    });

    /*
     * Hide the search icon when the search bar is focused
     */
    $('.search-bar-text').focus(function() {
        var iconDiv = $(this).siblings('.input-group-addon');
        $('.glyphicon-search', iconDiv).hide();
    });
    /*
     * Show the search icon when the search bar lost the focus
     */
    $('.search-bar-text').blur(function() {
        var iconDiv = $(this).siblings('.input-group-addon');
        $('.glyphicon-search', iconDiv).show();
    });
    /*
     * Set focus on input when click anywhere of search bar
     */
    $('.search-bar').click(function() {
        $('.search-bar-text', this).focus();
    });

    setServiceTypesListeners();

});

function messageModal(message) {

    $("#messageModal").find(".modal-body").text(message);
    $("#messageModal").modal();
}

function setServiceTypesListeners() {
    $('.service-type_delete').click(deleteServiceType);
    $('.service-type_modify').click(showServiceTypeForm);
    $('.service-type_add').click(showServiceTypeForm);
}

function showServiceTypeForm() {

    var sTypeId = $(this).attr('sTypeId');

    $.get("/service-type/save", {
        id : sTypeId
    }, function(data) {
        var modalContainer = $('#modalForm');
        $('.modal-content', modalContainer).html(data);
        modalContainer.modal();
    });
}

function saveServiceType() {
    var form = $('#serviceTypeForm');
    $.post("/service-type/save", form.serialize(), function(data) {
        var modalContainer = $('#modalForm');

        if ($(data).is("form")) {
            $('.modal-body', modalContainer).html(data);
            modalContainer.modal('show');
        }

        if($(data).is("div")) {
            var collapseContainer = $('#service-types-collapse');
            collapseContainer.html(data);
            collapseContainer.collapse('show');
            setServiceTypesListeners();
            modalContainer.modal('hide')
        }

    });
}

function deleteServiceType() {

    var sTypeId = $(this).attr('sTypeId');

    var menuItem = $(this).closest('.select-menu-item');
    $.post("/service-type/delete", {
        id : sTypeId
    }, function(data) {
        if (data.success) {
            $(menuItem).remove();
        } else {
            messageModal(data.errorMsg);
        }
    });
}
