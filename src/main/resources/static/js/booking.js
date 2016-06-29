
const
  BRANCHES_PATH = "/get_branches",
  BRANCH_DATA_PATH = "/get_branch_data",
  sections = {};

sections.loading = "#loading-content";
sections.profile = "#profile-content";
sections.booking = "#booking-content";
sections.map = "#map-content";
sections.workers = "#workers-content";
sections.branch = "#branch-content";

/*
 * JQuery execute this abstract function when page is totally loaded.
 */
$(function() {
  $('.profile-menu').find('li').click(function() {
    // Update menu items style
    $('.profile-menu').find('li').removeClass('active');
    $(this).addClass('active');

    // Show section
    $('.section').hide();
    $(sections.loading).show();
    updateSection($(this).find('a').attr('data-content'));
  });

});

function updateSection(section) {

  switch (section) {

    case sections.profile:
      break;

    case sections.booking:
      $.get(BRANCHES_PATH, {
        company_id : companyId,
        section : 'booking'
      }, function(data) {
        $(section).html(data);
        updateBranchItems();
      });
      break;

    case sections.branch:
      $.get(BRANCH_DATA_PATH, {
        branch_id : $(section).attr('branchId'),
        section : $(section).attr('section')
      }, function(data) {
        $(section).html(data);
      });
      break;

    default:
      break;
  }

  $(sections.loading).hide();
  $(section).fadeIn();

}

function updateBranchItems() {
  $('.branch-item').click(function() {
    // Show section
    $('.section').hide();
    $(sections.loading).show();
    $(sections.branch).attr('branchId', $(this).attr('branchId'));
    $(sections.branch).attr('section', $(this).attr('section'));
    updateSection(sections.branch);
  });
}
