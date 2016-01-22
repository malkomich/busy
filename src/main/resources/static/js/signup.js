
function updateCities() {
    $.getJSON(
    		"getCityList", 
    		{countryCode: $('select#country').val()},
    		function(data) {
    			var options = '';
    			var len = data.length;
    			for (var i = 0; i < data.length; i++) {
    				options += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
    			}
    			$('select#city').html(options);
    		});
}