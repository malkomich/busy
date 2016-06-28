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
adminBoxSelector = ".admin-box";
const
adminContentSelector = ".admin-content";
const
adminLoadingSelector = ".admin-box-loading";

const
contentAttribute = "data-content";

const
adminBoxes = {};

adminBoxes.companies = "#admin-companies-content";

/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {

    if (message) {
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

    // Display admin content on admin-box click
    $(adminBoxSelector).click(function() {
        $(adminContentSelector).fadeOut();
        $(adminLoadingSelector, this).css('opacity', '1');
        var targetDiv = $(this).attr(contentAttribute);
        updateBox(targetDiv);
    });

});

/*
 * Send an AJAX request to the controller, with the id of the target div to update.
 */
function updateBox(targetDiv) {

    var tbody = $("tbody", targetDiv);

    switch (targetDiv) {
        case adminBoxes.companies:

            $.getJSON("/get_company_list", function(data) {

                var rows = '';

                for (var i = 0; i < data.length; i++) {
                    var checked;
                    if (data[i].active) {
                        rows += '<tr role="row" class="company-item bg-active">';
                        checked = "checked";
                    } else {
                        rows += '<tr role="row" class="company-item bg-inactive">';
                        checked = "";
                    }

                    var categoryName = (data[i].category) ? data[i].category.name : "";

                    rows += '<input type="hidden" name="company-id" value="' + data[i].id + '"/>';
                    rows += '<td>' + data[i].tradeName + '</td>';
                    rows += '<td>' + data[i].businessName + '</td>';
                    rows += '<td>' + data[i].email + '</td>';
                    rows += '<td>' + data[i].cif + '</td>';
                    rows += '<td>' + categoryName + '</td>';
                    rows +=
                            '<td class="borderless bg-none"><div class="onoffswitch"><input type="checkbox" name="onoffswitch" '
                                    + 'class="onoffswitch-checkbox" id="onoffswitch' + i + '" ' + checked
                                    + ' ><label class="onoffswitch-label" ' + 'for="onoffswitch' + i
                                    + '"></label></div></td>';
                    rows += '</tr>';
                }

                tbody.html(rows); // Add table to DOM

                // Checkbox change listener
                $('tr', tbody).each(function() {
                    var entry = $(this);
                    $('.onoffswitch > :checkbox', this).change(function() {
                        var active = $(this).is(':checked');
                        var companyId = $('input[name=company-id]', entry).val();
                        $.post('/change_company_state', {
                            "id" : companyId,
                            "active" : active
                        });
                        if (active) {
                            $(entry).removeClass("bg-inactive");
                            $(entry).addClass("bg-active");
                        } else {
                            $(entry).removeClass("bg-active");
                            $(entry).addClass("bg-inactive");
                        }
                    });
                });

                $(targetDiv).fadeIn();
                $(adminLoadingSelector).css('opacity', '0');
            });
            break;
        default:
            break;
    }

}

function messageModal(message) {

    $("#messageModal").find(".modal-body").text(message);
    $("#messageModal").modal();
}
