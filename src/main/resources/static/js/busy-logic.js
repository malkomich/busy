/*
 * Selectors constants declaration
 */
const
dialogSwitchSelector = ".dialog-switch";
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

                var dialog_switch = $(dialogSwitchSelector);
                var dialog_div = $(dialogDivSelector);

                if (!dialog_switch.is(e.target) && dialog_switch.has(e.target).length === 0 && !dialog_div.is(e.target)
                        && dialog_div.has(e.target).length === 0) {
                    dialog_div.hide();
                }

                var collapse_switch = $(collapseSwitchSelector);
                var collapse_div = $(collapseDivSelector);

                if (!collapse_switch.is(e.target) && collapse_switch.has(e.target).length === 0
                        && !collapse_div.is(e.target) && collapse_div.has(e.target).length === 0) {
                    collapse_div.collapse('hide');
                }

            });

    // Toggle the div with the selector given in 'data-target' attribute of a
    // 'dialog-switch'
    $(dialogSwitchSelector).click(function() {
        $($(this).data("target")).toggle();
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
});

function messageModal(message) {

    $("#messageModal").find(".modal-body").text(message);
    $("#messageModal").modal();
}
