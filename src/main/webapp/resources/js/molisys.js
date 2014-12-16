var x;
jQuery(document).ready(function($) {
	
	$(".page-breadcrumbs").html($("#page-breadcrumbs-container").html())
	$("#header-title-detail").html($("#header-title-detail-container").html())
	$(".fileupload").each(function(index, val) {
		 $(this).fileinput({
			browseClass: "btn btn-primary btn-block",
			showCaption: false,
			showRemove: false,
			showUpload: false
		});	
	});
	
	$(".deletefloorplan").each(function(index, val){
		$(this).click(function(event){
			alert("Do you want to delete this Floor plan")
			$("#deleteFloorplan_" + this.id).submit()
		});
	});
	
	$(".page-content")
    .delegate(".widget-header", "click", function (event) {
    	var widgetHeader = $(this);
    	var widget = widgetHeader.find(".widget-toolbar > [data-action='collapse']")
    	var widgetbox = widget.closest(".widget-box");
    	close(widgetbox, widget)
    });
	var close = function(widgetbox, widget) {
		var widgetbody = widgetbox.find(".widget-body");
        button = widget.find(".fa")
            .eq(0);
        var down = "fa-plus";
        var up = "fa-minus";
        var widgetbodyinner = widgetbody.find(".widget-body-inner");
        if (widgetbodyinner.length == 0) {
            widgetbody = widgetbody.wrapInner('<div class="widget-body-inner"></div>')
                .find(":first-child")
                .eq(0);
        } else {
            widgetbody = widgetbodyinner.eq(0);
        }
        var slidedowninterval = 300;
        var slideupinterval = 200;
        if (widgetbox.hasClass("collapsed")) {
            if (button) {
                button.addClass(up)
                    .removeClass(down);
            }
            widgetbox.removeClass("collapsed");
            widgetbody.slideUp(0, function () {
                widgetbody.slideDown(slidedowninterval);
            });
        } else {
            if (button) {
                button.addClass(down)
                    .removeClass(up);
            }
            widgetbody.slideUp(slideupinterval, function () {
                widgetbox.addClass("collapsed");
            });
        }
	}
});