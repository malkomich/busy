var selectors = {
    'MODAL' : '#modalForm',
    'SERV_FORM' : '.service-form',
    'SERV_ROW' : '.service-row',
    'START_TIME_INPUT' : '.service-start-time',
    'END_TIME_INPUT' : '.service-end-time',
    'SERV_TYPE_SELECT' : '.service-type',
    'ROLE_SELECT' : '.roles-select',
    'ROLE_CHECKBOX' : '.role-item input:checkbox',
    'ROLE_TIPS_LIST' : '.service-roles-summary ul',
    'REP_TYPE_SELECT' : '.service-repetition-type',
    'TIMESLOT_ROW' : '.time-slot'
};

$(function() {

    var service_form = $(selectors.SERV_FORM);

    $(selectors.SERV_ROW, service_form).each(function(index) {

        var row = $(this);

        // Init setup
        setUpStartTime(row);
        setUpInputListeners(row, index);
        setUpButtonListeners(row, index);

        $(selectors.TIMESLOT_ROW, row).each(function() {
            setUpMultiSelect($(this));

            // Update for initial visibility of end_time input
            updateEndTime($(this));
        });
    });
});

/*
 * Request the updated form, with a new blank service created, to the controller.
 */
function newService() {
    var form = $(selectors.SERV_FORM);
    var modalContainer = $('selectors.MODAL');
    $.post("/service_form/new", form.serialize(), function(data) {
        $('.modal-content', modalContainer).html(data);
        setUpMultiSelect($(selectors.SERV_ROW, form).last().find(selectors.TIMESLOT_ROW).last());
        modalContainer.modal();
    });
}

/*
 * Submit the service form to create the list of services.
 */
function saveServices() {
    var form = $(selectors.SERV_FORM);
    $.post("/service_form/save", form.serialize(), function(data) {

        var modalContainer = $('selectors.MODAL');

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
    $(selectors.START_TIME_INPUT, row).each(function() {
      $(this).timepicker({
          template: false,
          showInputs: false,
          showMeridian: false,
          defaultTime: false,
          minuteStep: 5
      });
    });
}

/*
 * Update the end time input of a specific row according to start time and service type values.
 */
function updateEndTime(row) {

    var endInput = $(selectors.END_TIME_INPUT, row);

    var startTime = $(selectors.START_TIME_INPUT, row).val();
    var serviceRow = $(row).closest(selectors.SERV_ROW);
    var duration = $(selectors.SERV_TYPE_SELECT + ' option:selected', serviceRow).attr('duration');

    if (startTime && duration) {
        var endTime = moment(startTime, "HH:mm").add(duration, 'minutes');
        $(endInput).val(endTime.format('HH:mm')).parent().css('visibility', 'visible');
    } else {
        $(endInput).parent().css('visibility', 'hidden');
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
        allSelected: txtSelectedAll
    });
}

function setUpInputListeners(row, index) {

    var startTimeInput = $(selectors.START_TIME_INPUT, row);
    var typeSelect = $(selectors.SERV_TYPE_SELECT, row);
    var repetitionTypeSelect = $(selectors.REP_TYPE_SELECT, row);

    $(startTimeInput).change(function() {
        var timeSlotRow = $(this).closest(selectors.TIMESLOT_ROW);
        updateEndTime(timeSlotRow);
    });

    $(typeSelect).change(function() {
        var timeSlotRow = $(this).closest(selectors.TIMESLOT_ROW);
        updateEndTime(timeSlotRow);
    });

}

function setUpButtonListeners(row, index) {

    var form = $(selectors.SERV_FORM);
    var modalContainer = $('selectors.MODAL');

    $('.add_timeslot', row).click(function() {

      $.post("/service_form/add_timeslot", form.serialize(), function(data) {

          $('.modal-content', modalContainer).html(data);
          var newRow = $(selectors.SERV_ROW, form).find(selectors.TIMESLOT_ROW)[index + 1];
          alert($(selectors.TIMESLOT_ROW, newRow).last().html());
          setUpMultiSelect($(selectors.TIMESLOT_ROW, newRow).last());
          modalContainer.modal();

      });
    });
}
