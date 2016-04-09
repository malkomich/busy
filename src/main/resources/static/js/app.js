const
BOOKINGS_PATH = "/get_month_bookings";

var month;

var calendar;

$(function() {

    date = new Date();
    month = date.getMonth() + 1;

    options = {
        events_source : BOOKINGS_PATH + "?month=" + month,
        view : 'month',
        tmpl_path : '/tmpls/',
        tmpl_cache : false,
        day : date.toString('yyyy-MM-dd'),
        onAfterViewLoad : function(view) {
            $('.page-header h3').text(this.getTitle());
            $('.btn-group button').removeClass('active');
            $('button[data-calendar-view="' + view + '"]').addClass('active');
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

    $('.btn-group button[data-calendar-nav]').each(function() {
        var $this = $(this);
        $this.click(function() {
            calendar.navigate($this.data('calendar-nav'));
            
            // Update events when the month is changed
            var date = calendar.getStartDate();
            var requestedMonth = date.getMonth() + 1;

            if (month !== requestedMonth) {
                month = requestedMonth;

                var url = BOOKINGS_PATH + "?month=" + requestedMonth;
                calendar.options.events_source = url;
                calendar.view();
            }
        });
    });

    $('.btn-group button[data-calendar-view]').each(function() {
        var $this = $(this);
        $this.click(function() {
            calendar.view($this.data('calendar-view'));
        });
    });

});
