var selectors = {
    'SERV_FORM' : '.service-form',
    'SERV_ROW' : '.service-row',
    'START_TIME_INPUT' : '#service-start-time',
    'END_TIME_INPUT' : '#service-end-time',
    'SERV_TYPE_SELECT' : '#service-type',
    'ROLE_SELECT' : '#roles-select',
    'ROLE_CHECKBOX' : '.role-item input:checkbox',
    'ROLE_TIPS_LIST' : '.service-roles-summary ul',
    'REP_TYPE_SELECT' : '#service-repetition-type',
    'REP_DATE_INPUT' : '#service-repetition-date',
};

$(function() {

    var service_form = $(selectors.SERV_FORM);

    $(selectors.SERV_ROW, service_form).each(function() {

        var row = $(this);

        // Init setup
        setUpStartTime(row);
        setUpMultiSelect(row);
        setUpInputListeners(row);
        setUpButtonListeners(row);

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
        setUpMultiSelect($(selectors.SERV_ROW, form).last());
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
 * Update the format and behaviour of the start time input
 */
function setUpStartTime(row) {
    $('#service-start-time', row).timepicker({
        template: false,
        showInputs: false,
        showMeridian: false,
        defaultTime: false,
        minuteStep: 5
    });
}

/*
 * Update the end time input of a specific row according to start time and service type values.
 */
function updateEndTime(row) {
    var endInput = $(selectors.END_TIME_INPUT, row);
    var servicesCount = $('#service-count', row);

    var startTime = $(selectors.START_TIME_INPUT, row).val();
    var duration = $(selectors.SERV_TYPE_SELECT + ' option:selected', row).attr('duration');

    if (startTime && duration) {
        var endTime = moment(startTime, "HH:mm").add(duration, 'minutes');
        $(endInput).val(endTime.format('HH:mm')).parent().css('visibility', 'visible');
        $('.total', servicesCount).text('1');
        $(servicesCount).css('visibility', 'visible');
    } else {
        $(endInput).parent().css('visibility', 'hidden');
        $(servicesCount).css('visibility', 'hidden');
    }
}

/*
 * Update the repetition date field visibility, depending if a repetition type has been chosen.
 */
function updateRepetitionDate(row) {

    var repetitionType = $(selectors.REP_TYPE_SELECT, row).val();
    var repetitionDateInput = $(selectors.REP_DATE_INPUT, row);

    if (repetitionType != 'NONE') {
        $(repetitionDateInput).css('visibility', 'visible').datepicker();
        $('#rep-date-error').show();
    } else {
        $(repetitionDateInput).css('visibility', 'hidden');
        $('#rep-date-error').hide();
    }
}

/*
 * Set the behaviour and custom style for each multiSelect field in the given row.
 */
function setUpMultiSelect(row) {

    $(selectors.ROLE_SELECT, row).multipleSelect({
      placeholder: placeholder,
      selectAll: false,
      filter: true,
      width: '100%',
      selectAllText: txtSelectAll,
      allSelected: txtSelectedAll,
      onClick: function(view) {
        var list = $(selectors.ROLE_TIPS_LIST, row);
        var roleId = view.value;
        var roleTipItem = $('#' + roleId, list);
        var exists = $(roleTipItem).attr('id') === roleId;

        if (view.checked && !exists) {
            var roleLabel = view.label;
            $(list).append('<li id="' + roleId + '" class="fs-xs">' + roleLabel + '</li>')
        } else {
            $(roleTipItem).remove();
        }
      }
    });
}

function setUpInputListeners(row) {

    var startTimeInput = $(selectors.START_TIME_INPUT, row);
    var typeSelect = $(selectors.SERV_TYPE_SELECT, row);
    var repetitionTypeSelect = $(selectors.REP_TYPE_SELECT, row);

    $(startTimeInput).change(function() {
        updateEndTime(row);
    });

    $(typeSelect).change(function() {
        updateEndTime(row);
    });

    $(repetitionTypeSelect).change(function() {
        updateRepetitionDate(row);
    });


}

function setUpButtonListeners(row) {

    var endInput = $(selectors.END_TIME_INPUT, row);
    var servicesCount = $('#service-count', row);
    var duration = $(selectors.SERV_TYPE_SELECT + ' option:selected', row).attr('duration');

    $('.add', servicesCount).click(function() {
      var endTime = moment($(endInput).val(), "HH:mm").add(duration, 'minutes');
      $(endInput).val(endTime.format('HH:mm'));
      var count = parseInt($('.total').text());
      $('.total').text(count + 1);
    });
    $('.reduce', servicesCount).click(function() {
      var count = parseInt($('.total').text());
      if(count > 1) {
        var endTime = moment($(endInput).val(), "HH:mm").subtract(duration, 'minutes');
        $(endInput).val(endTime.format('HH:mm'));
        $('.total').text(count - 1);
      }
    });
}
