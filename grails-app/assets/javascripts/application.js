// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
	(function($) {
		$(document).ajaxStart(function(){
			$('#spinner').fadeIn();
		}).ajaxStop(function(){
			$('#spinner').fadeOut();
		});
	})(jQuery);
}

$(document).ready(function() {
	$(".js-select2").select2({
		closeOnSelect : false,
		placeholder : "Select the domains you want to download:",
		allowHtml: true,
		allowClear: true,
		tags: true // создает новые опции на лету
	});

	$('.icons_select2').select2({
		width: "100%",
		templateSelection: iformat,
		templateResult: iformat,
		allowHtml: true,
		placeholder: "Placeholder",
		dropdownParent: $( '.select-icon' ),//обавили класс
		allowClear: true,
		multiple: false
	});


	function iformat(icon, badge,) {
		var originalOption = icon.element;
		var originalOptionBadge = $(originalOption).data('badge');

		return $('<span><i class="fa ' + $(originalOption).data('icon') + '"></i> ' + icon.text + '<span class="badge">' + originalOptionBadge + '</span></span>');
	}


});
