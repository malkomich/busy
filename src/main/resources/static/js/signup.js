$(function() {
	var code = $('select#country').val();
	if (code != "") {
		updateCities();
	}
	$('select#country').change(updateCities);
});

function updateCities() {
	$.getJSON("get_city_list", {
		countryCode : $('select#country').val()
	}, function(data) {
		var options = '';
		var len = data.length;
		for (var i = 0; i < data.length; i++) {
			options += '<option value="' + data[i].id + '">' + data[i].name
					+ '</option>';
		}
		$('select#city').html(options);
	});
}