function formLogoutSubmit() {
	document.getElementById("logoutForm").submit();
}

$(document).ready(function() {
	$('.number').keypress(validateNumber);

	var covertAllToICT = function() {
		$(".datetime").each(function() {
			var item = $(this);
			changeToICT(item);
		});
	};
	covertAllToICT();

});

var changeToUTC = function(element) {
	var value = element.val();
	if (value != "") {
		var date = new Date(value);
		var utc = new Date(date.getTime() + date.getTimezoneOffset() * 60000);

		element.val(utc.toLBAString());
	}
};

var changeToICT = function(element) {

	var isDateFormat = function(x) {
		var patt = /\d\d\d\d-\d\d-\d\d \d\d:\d\d/g;
		if (patt.exec(x) != null) {
			return true;
		}
		return false;
	};

	var convert = function(value) {
		var date = new Date(value);
		var ict = new Date(date.getTime() - date.getTimezoneOffset() * 60000);
		return ict.toLBAString();
	};

	var changeForBeterDate = function(check, convert) {
		if (check(element.html()) == true) {
			element.html(convert(element.html()));
		} else if (check(element.val()) == true){
			element.val(convert(element.val()));
		}
	};

	changeForBeterDate(isDateFormat, convert);
};
Date.prototype.toLBAString = function() {
	var date = this;
	var year = date.getFullYear();

	var month = date.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}

	var day = date.getDate();
	if (day < 10) {
		day = "0" + day;
	}

	var hours = date.getHours();
	if (hours < 10) {
		hours = "0" + hours;
	}

	var minutes = date.getMinutes();
	if (minutes < 10) {
		minutes = "0" + minutes;
	}

	return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
};

function validateNumber(event) {
	var key = window.event ? event.keyCode : event.which;

	if (event.keyCode == 8 || event.keyCode == 46 || event.keyCode == 37
			|| event.keyCode == 39) {
		return true;
	} else if (key < 48 || key > 57) {
		return false;
	} else
		return true;
};
jQuery(document).ready(
		function() {

			/* prepend menu icon */
			$('.header').prepend(
					'<div id="menu-icon"><i class="fa fa-th-list"></i></div>');
			/* toggle nav */
			$("#menu-icon").on("click", function() {
				$("#nav").slideToggle();
				$(this).toggleClass("active");
			});

			/* set height for menuMain */
			if ($(".tblMain").height() != 0) {
				var $iheight = $(".tblMain").height() - 120;
				$(".menuMain").css("height", $iheight);
			}

			/* prepend menu icon */
			if ($("#boxscroll1").length)
				$("#boxscroll1").niceScroll({
					touchbehavior : true
				});

			if ($("input:file").length)
				$("input:file").uniform();

			$(".menuMain > ul > li:first-child > a").on("click", function() {
				$(".menuMain ul li ul").slideToggle();
			});

			$("#nav-wrap > ul > li:first-child > a").on("click", function() {
				$("#nav-wrap ul li ul").slideToggle();
			});

			/* Create store form */

			$(".createStore").on("click", function() {
				$(".formPopup").slideToggle();
				$(this).toggleClass("active");
			});
			$(".createAgent").on("click", function() {
				$(".formPopup").slideToggle();
				$(".newAgen").toggleClass("active");
			});

			$(".createCoupon").on("click", function() {
				isShowing = $(".newMessage").is(":visible");
				isShowing1 = $(".newBeacon").is(":visible");
				if (isShowing || isShowing1) {
					$(".formPopup").hide();
				}
				$(".formPopup.newCoupon").slideToggle();

			});
			$(".createMessage").on("click", function() {
				isShowing = $(".newCoupon").is(":visible");
				isShowing1 = $(".newBeaco").is(":visible");
				if (isShowing || isShowing1) {
					$(".formPopup").hide();
				}
				$(".formPopup.newMessage").slideToggle();
			});
			$(".createBeacon").on("click", function() {
				isShowing = $(".newMessage").is(":visible");
				isShowing1 = $(".newCoupon").is(":visible");
				if (isShowing || isShowing1) {
					$(".formPopup").hide();
				}
				$(".formPopup.newBeacon").slideToggle();
			});

			/* show/hide Coupon */
			$('.showCoupon').click(function() {
				$('.slideFloor').toggle("slide");
			});

			$('.searchCt .showCoupon').click(function() {
				$('.formSearch').toggle("slide");
			});

			$(window).resize(function() {
				windowsize = $(window).width();
				/* set height for menuMain */
				if ($(".tblMain").height() != 0) {
					var $iheight = $(".tblMain").height() - 120;
					$(".menuMain").css("height", $iheight);
				}

				if (windowsize > 650) {
					// if the window is greater than 440px wide then turn on
					// jScrollPane..
					if ($('.slideFloor'))
						$('.slideFloor').css({
							display : "block"
						});
					if ($('.formSearch'))
						$('.formSearch').css({
							display : "block"
						});

				} else {
					if ($('.slideFloor'))
						$('.slideFloor').css({
							display : "none"
						});
					if ($('.formSearch'))
						$('.formSearch').css({
							display : "none"
						});

				}

			});

			$('#messageType').on('change', function() {
				if ($('#messageType option:selected').val() == 'CUSTOM')
					jQuery(".teareaCt").removeAttr("disabled");
				else {
					jQuery(".teareaCt").attr("disabled", "disabled");
				}

			});

			/*
			 * if($("#boxscroll").length) { $("#boxscroll").mCustomScrollbar({
			 * scrollButtons:{ enable:true } }); }
			 */

		});
/**
 * 
 */
