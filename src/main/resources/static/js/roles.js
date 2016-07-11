$(function() {
  $('#email').blur(function(event) {
    var form = $('#roleForm');
    $('a.check-email').show();
    $.post('/role/check_email', form.serialize(), function(data) {
      var modalContainer = $('#modalForm');

      var newForm = $(data);

      $('.modal-content', modalContainer).html(newForm);
      modalContainer.modal('show');

      if($('#user-id', newForm).val() !== '0') {
        $('input.user-dependant', newForm).attr('disabled','disabled');
      } else {
        $('input.user-dependant', newForm).removeAttr('disabled');
      }

      $('a.check-email').hide();

    });
  });
});
