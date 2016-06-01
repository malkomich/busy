var selectors = {
    'SERV_FORM' : '.service-form',
    'SERV_ROW' : '.service-row',
    'START_TIME_INPUT' : '#service-start-time',
    'END_TIME_INPUT' : '#service-end-time',
    'SERV_TYPE_SELECT' : '#service-type',
    'ROLE_CHECKBOX' : '.role-item input:checkbox',
    'ROLE_TIPS_LIST' : '.service-roles-summary ul',
    'REP_TYPE_SELECT' : '#service-repetition-type',
    'REP_DATE_INPUT' : '#service-repetition-date',
};

$(function() {

    var service_form = $(selectors.SERV_FORM);

    $(selectors.SERV_ROW, service_form).each(function() {
        var row = $(this);
        var startTimeInput = $(selectors.START_TIME_INPUT, this);

        var typeSelect = $(selectors.SERV_TYPE_SELECT, this);
        var roleCheckboxes = $(selectors.ROLE_CHECKBOX, this);
        var repetitionTypeSelect = $(selectors.REP_TYPE_SELECT, this);

        // Init update
        updateEndTime(row);
        updateRepetitionDate(row);

        // Set up event listeners
        $(startTimeInput).change(function() {
            updateEndTime(row);
        });

        $(typeSelect).change(function() {
            updateEndTime(row);
        });

        $(roleCheckboxes).change(function() {
            var list = $(selectors.ROLE_TIPS_LIST, row);
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

        $(repetitionTypeSelect).change(function() {
            updateRepetitionDate(row);
        });

    });
});

/*
 * Request the updated form, with a new blank service created, to the controller.
 */
function newService() {
    var form = $(selectors.SERV_FORM);
    var modalContainer = $('#modalForm');
    $.post("/service_form/new", form.serialize(), function(data) {
        var modalContainer = $('#modalForm');
        $('.modal-content', modalContainer).html(data);
        modalContainer.modal();
    });
}

/*
 * Submit the service form to create the list of services.
 */
function saveServices() {
    var form = $(selectors.SERV_FORM);
    $.post("/service_form/save", form.serialize(), function(data) {

        var modalContainer = $('#modalForm');

        if ($(data).is("form")) {
            $('.modal-content', modalContainer).html(data);
            modalContainer.modal('show');
        } else {
            modalContainer.modal('hide');

            calendar.view();
        }

    });
}

/*
 * Update the end time input of a specific row according to start time and service type values.
 */
function updateEndTime(row) {
    var endInput = $(selectors.END_TIME_INPUT, row);

    var startTime = $(selectors.START_TIME_INPUT, row).val();
    var duration = $(selectors.SERV_TYPE_SELECT + ' option:selected', row).attr('duration');

    if (startTime && duration) {

        var endTime = moment(startTime, "HH:mm").add(duration, 'minutes');
        $(endInput).val(endTime.format('HH:mm')).css('visibility', 'visible');

    } else {

        $(selectors.END_TIME_INPUT, row).css('visibility', 'hidden');
    }
}

/*
 * Update the repetition date field visibility, depending if a repetition type has been chosen.
 */
function updateRepetitionDate(row) {

    var repetitionType = $(selectors.REP_TYPE_SELECT, row).val();
    var repetitionDateInput = $(selectors.REP_DATE_INPUT, row);

    if (repetitionType != 'NONE') {
        $(repetitionDateInput).css('visibility', 'visible');
        $('#rep-date-error').show();
    } else {
        $(repetitionDateInput).css('visibility', 'hidden');
        $('#rep-date-error').hide();
    }
}
