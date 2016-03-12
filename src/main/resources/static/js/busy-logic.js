/*
 * Selectors constants declaration
 */
const
msgModalSelector = "#messageModal";
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
contentAttribute = "data-content";

const
adminBoxes = {};

adminBoxes.companies = "#admin-companies";

/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {
	if (message) {
		$(msgModalSelector).find(".modal-body").text(message);
		$(msgModalSelector).modal();
	}

	// Hide 'dialog' and 'collapse' items clicking outside of them
	$(document).mouseup(
			function(e) {

				var dialog_switch = $(dialogSwitchSelector);
				var dialog_div = $(dialogDivSelector);

				if (!dialog_switch.is(e.target)
						&& dialog_switch.has(e.target).length === 0
						&& !dialog_div.is(e.target)
						&& dialog_div.has(e.target).length === 0) {
					dialog_div.hide();
				}

				var collapse_switch = $(collapseSwitchSelector);
				var collapse_div = $(collapseDivSelector);

				if (!collapse_switch.is(e.target)
						&& collapse_switch.has(e.target).length === 0
						&& !collapse_div.is(e.target)
						&& collapse_div.has(e.target).length === 0) {
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
		$(adminContentSelector).hide();
		var targetDiv = $(this).attr(contentAttribute);
		updateBox(targetDiv);
	});
});

/*
 * Send an AJAX request to the controller, with the id of the target div to
 * update.
 */
function updateBox(targetDiv) {

	var path;
	switch (targetDiv) {
	case adminBoxes.companies:
		path = "get_company_list";
		break;
	default:
		path = null;
	}
	
	var tbody = $("tbody",targetDiv);
	
	if(path) {
		$.getJSON(path, function(data) {
			
			var rows = '';
	
			for (var i = 0; i < data.length; i++) {
				rows += '<tr role="row">';
				rows += '<td>' + data[i].tradeName + '</td>';
				rows += '<td>' + data[i].businessName + '</td>';
				rows += '<td>' + data[i].email + '</td>';
				rows += '<td>' + data[i].cif + '</td>';
				rows += '<td>' + data[i].active + '</td>';
				rows += '<td>' + data[i].category.name + '</td>';
				rows += '</tr>'
			}
			tbody.html(rows);
		});
		
		$(targetDiv).show();
	}
	
}
