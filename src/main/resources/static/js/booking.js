
const
  sections = {},
  BRANCHES_PATH = "/get_branches";

sections.profile = "#profile-content";
sections.booking = "#booking-content";
sections.map = "#map-content";
sections.workers = "#workers-content";

/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {
  $('.profile-menu').find('li').each(function() {
    $(this).click(function() {

      // Update menu items style
      $('.profile-menu').find('li').removeClass('active');
      $(this).addClass('active');

      // Show section
      $('.section').hide();
      $('#loading-content').show();
      updateSection($(this).find('a').attr('data-content'));
    })
  });
});

function updateSection(section) {

  switch (section) {
    case sections.profile:
      $('#loading-content').hide();
      $(section).fadeIn();
      break;
    case sections.booking:
      $.get(BRANCHES_PATH, {
        company_id : companyId
      }, function(data) {
        $('#loading-content').hide();
        $(section).html(data).fadeIn();
      });
      break;
    default:
      $('#loading-content').hide();
      $(section).fadeIn();
      break;
  }
}
