const
notificationSwitch = ".dialog-switch";

$(function() {
  // Listener for each notification item to mark them as read
  $('.notifications-content > .item-notification-content > a').click(function() {
    var notificationId = $(this).attr('notificationId');
    var notificationItem = $(this).parent('.item-notification-content');
    $.post('/notification/read', {
      id : notificationId
    }, function(data) {
      $('.notification-section').html(data);
      $('.notifications-content').show();
    });
  });

  // Listener to mark all notifications as read
  $('.notifications-content > .footer > a').click(function() {
    $.post('/notification/read/all',
    function(data) {
      $('.notification-section').html(data);
      $('.notifications-content').show();
    });
  });

  // Toggle notifications when icon is clicked
  $(notificationSwitch).click(function() {
      $($(this).data("target")).toggle();
  });

  $(document).mouseup(
    function(e) {
      if (!$('.notification-section').is(e.target) && $('.notification-section').has(e.target).length === 0) {
          $('.notifications-content').hide();
      }
    }
  );

});
