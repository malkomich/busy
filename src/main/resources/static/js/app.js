
var calendar;

$(function() {

    var date = new Date();

    options = {
        events_source : EVENTS_PATH + "?role=" + roleId + "&is_booking=" + isBooking,
        view : 'month',
        tmpl_path : '/tmpls/',
        tmpl_cache : false,
        day : date.toString('yyyy-MM-dd'),
        onAfterViewLoad : function(view) {
            $('.page-header h3').text(this.getTitle());
            $('.btn-group button').removeClass('active');
            $('button[data-calendar-view="' + view + '"]').addClass('active');
            setupListeners();
        },
        classes : {
            months : {
                general : 'label'
            }
        },
        language : 'es-ES',
        modal : "#events-modal",
        modal_title : function(event) {
            return event.title
        }
    };

    calendar = $('#calendar').calendar(options);

    // Navigation buttons
    $('.btn-group button[data-calendar-nav]').each(function() {
        var $this = $(this);
        $this.click(function() {
            calendar.navigate($this.data('calendar-nav'));
        });
    });

    // View mode option buttons
    $('.btn-group button[data-calendar-view]').each(function() {
        var $this = $(this);
        $this.click(function() {
            calendar.view($this.data('calendar-view'));
        });
    });

});

/*
 * Initialize the event listeners for the calendar items.
 */
function setupListeners() {

    // Day cells
    $('.cal-cell').dblclick(function(event) {
      event.preventDefault();
      event.stopPropagation();

      var date = $('[data-cal-date]', this).data('cal-date');
      openServiceForm(date);
    });
    $('*[data-cal-date]').click(function(event) {
      event.preventDefault();
      event.stopPropagation();

      var date = $(this).data('cal-date');
      openServiceForm(date);
    });

    $('a[data-event-id]').on('click', function(event) {
      event.preventDefault();
      event.stopPropagation();

      var full = $(this).data('event-class') === "event-important";
      if (isBooking && !full) {
        var timeSlotId = $(this).data('event-id');
        openBookingForm(timeSlotId);
      }
    });
}

/*
 * Open the service modal form for a given day.
 */
function openServiceForm(date) {

  calendar.options.day = date;

  if(DBL_CLICK_PATH != undefined) {
    $.get(DBL_CLICK_PATH, {
      date : calendar.options.day
    }, function(data) {

      if ($(data).is("p")) {
        messageModal($(data).text());

      } else if ($(data).is("form")) {
        var modalContainer = $('#modalForm');
        $('.modal-content', modalContainer).html(data);
        modalContainer.modal();
      }
    });
  }
}

function openBookingForm(timeSlotId) {
  $.get("/booking_form", {
    time_slot_id : timeSlotId
  }, function(data) {

    var modalContainer = $('#modalForm');
    $('.modal-content', modalContainer).html(data);
    modalContainer.modal();
  });
}
