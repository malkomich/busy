/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {

	// Loads the cities when page loads (country can be selected before) and
	// when the country changes.
	var code = $('select#country').val();
	if (code != "") {
		updateCities();
	}
	$('select#country').change(updateCities);

	// Hide Mandatory Mark when input has text, and show otherwise.
	$('input').blur(function() {
		if ($(this).hasClass("mandatory")) {
			if (this.value != "") {
				$(this).removeClass('has-error');
				var error = $(this).next('span').hide();
			} else {
				$(this).addClass('has-error');
				$(this).next('span').show();
			}
		}
	});

	$('select').blur(function() {
		if ($(this).hasClass("mandatory")) {
			if (this.value != "") {
				$(this).removeClass('has-error');
			} else {
				$(this).addClass('has-error');
			}
		}
	});
});

/*
 * Send an AJAX request to the controller, with the code of a country as
 * parameter. Retrieves the cities from this country.
 */
function updateCities() {
	$.getJSON("get_city_list", {
		countryCode : $('select#country').val()
	}, function(data) {
		var options = '';
		var len = data.length;

		if (data.length > 0) {
			$('#cityNotEmptyHeader').show().prop('selected', true);
			$('#cityEmptyHeader').hide();
		} else {
			$('#cityEmptyHeader').show().prop('selected', true);
			$('#cityNotEmptyHeader').hide();
		}

		for (var i = 0; i < len; i++) {
			options += '<option value="' + data[i].id + '">' + data[i].name
					+ '</option>';
		}
		$('select#city option[value!=""]').remove();
		$('select#city').append(options);
	});
}