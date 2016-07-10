$(function() {
  // Listener for each notification item to mark them as read
  $('.notifications-content > .item-notification-content > a').click(function() {
    var notificationId = $(this).attr('notificationId');
    var notificationItem = $(this).parent('.item-notification-content');
    $.post('/notification/read', {
      id : notificationId
    }, function(success) {
      if(success) {
        $(notificationItem).remove();
      }
    });
  });

  // Listener to mark all notifications as read
  $('.notifications-content > .footer > a').click(function() {
    $.post('/notification/read/all',
    function(success) {
      if(success) {
        $('.item-notification-content').remove();
      }
    });
  });
});
