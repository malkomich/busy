$(function() {

    var service_form = $('.service-form');
    
    if(!$('.service-row', service_form).length) {
        newService();
    }

    $('.service-row', service_form).each(function() {
        var row = $(this);
        var startTimeInput = $('#startTime', this);

        var typeSelect = $('#serviceType', this);
        var roleCheckboxes = $('.role-item input:checkbox', this);
        var repetitionSelect = $('#repetitionType', this);

        updateEndTime(row);

        $(startTimeInput).change(function() {
            updateEndTime(row);
        });

        $(typeSelect).change(function() {
            updateEndTime(row);
        });

        $(roleCheckboxes).change(function() {
            var list = $('.service-roles-summary ul', row);
            var roleId = $(this).val();

            var roleTipItem = $('#' + roleId, list);
            var exists = $(roleTipItem).attr('id') === roleId;

            if ($(this).is(':checked') && !exists) {
                var roleLabel = $(this).next('label').text();
                $(list).append('<li id="' + roleId + '" class="fs-xs">' + roleLabel + '</li>')
            } else {
                $(roleTipItem).remove();
            }
        });

        $(repetitionSelect).change(function() {

        });

    });
});

/*
 * Request the updated form, with a new blank service created, to the controller.
 */
function newService() {
    var form = $('#serviceForm');
    var modalContainer = $('#modalForm');
    $.get("/service_form/new", {
        serviceForm : form.serialize()
    }, function(data) {
        var modalContainer = $('#modalForm');
        $('.modal-content', modalContainer).html(data);
        modalContainer.modal();
    });
}

/*
 * Submit the service form to create the list of services.
 */
function saveServices() {
    var form = $('#serviceForm');
    $.post("/service_form/save", form.serialize(), function(data) {

        var modalContainer = $('#modalForm');

        if ($(data).is("form")) {
            $('.modal-content', modalContainer).html(data);
            modalContainer.modal('show');
        } else {
            modalContainer.modal('hide')

            calendar.view();
        }

    });
}

/*
 * Update the end time input of a specific row according to start time and service type values.
 */
function updateEndTime(row) {
    var endInput = $('#endTime', row);

    var startTime = $('#startTime', row).val();
    var duration = $('#serviceType option:selected', row).attr('duration');

    if (startTime && duration) {

        var endTime = moment(startTime, "HH:mm").add(duration, 'minutes');
        $(endInput).val(endTime.format('HH:mm')).show();

    } else {

        $('#endTime', row).hide();
    }
}
