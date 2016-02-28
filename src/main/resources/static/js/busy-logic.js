/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {
	if (message) {
		$("#messageModal").find(".modal-body").text(message);
		$("#messageModal").modal();
	}

	// Hide 'dialog' and 'collapse' items clicking outside of them
	$(document).mouseup(
			function(e) {

				var dialog_switch = $(".dialog-switch");
				var dialog_div = $(".dialog");

				if (!dialog_switch.is(e.target)
						&& dialog_switch.has(e.target).length === 0
						&& !dialog_div.is(e.target)
						&& dialog_div.has(e.target).length === 0) {
					dialog_div.hide();
				}

				var collapse_switch = $(".collapse-switch");
				var collapse_div = $(".collapse");

				if (!collapse_switch.is(e.target)
						&& collapse_switch.has(e.target).length === 0
						&& !collapse_div.is(e.target)
						&& collapse_div.has(e.target).length === 0) {
					collapse_div.collapse('hide');
				}

			});

	// Toggle the div with the selector given in 'data-target' attribute of a
	// 'dialog-switch'
	$(".dialog-switch").click(function() {
		$($(this).data("target")).toggle();
	});
});