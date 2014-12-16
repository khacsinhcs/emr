/*Sets Themed Colors Based on Themes*/

var themeprimary = getThemeColorFromCss('themeprimary');
var themesecondary = getThemeColorFromCss('themesecondary');
var themethirdcolor = getThemeColorFromCss('themethirdcolor');
var themefourthcolor = getThemeColorFromCss('themefourthcolor');
var themefifthcolor = getThemeColorFromCss('themefifthcolor');

//Gets Theme Colors From Selected Skin To Use For Drawing Charts
function getThemeColorFromCss(style) {
    var $span = $("<span></span>").hide().appendTo("body");
    $span.addClass(style);
    var color = $span.css("color");
    $span.remove();
    return color;
}

//Handle RTL SUpport for Changer CheckBox
$("#skin-changer li a").click(function () {
    createCookie("current-skin", $(this).attr('rel'), 10);
    window.location.reload();
});

//Checks Not to Do rtl-support for Arabic and Persian Demo Pages
if (location.pathname != "/index-rtl-fa.html" && location.pathname != "/index-rtl-ar.html") {
    if (readCookie("rtl-support")) {
        document.getElementById('rtl-changer').checked = true;
        switchClasses("pull-right", "pull-left");
        switchClasses("databox-right", "databox-left");
        switchClasses("item-right", "item-left");
        $('.navbar-brand small img').attr('src', 'assets/img/logo-rtl.png')
    }
    else {
        document.getElementById('rtl-changer').checked = false;
    }


    document.getElementById("rtl-changer").onchange = function () {
        if (this.checked) {
            createCookie("rtl-support", "true", 10);
        }
        else {
            eraseCookie("rtl-support");
        }
        setTimeout(function () {
            window.location.reload();
        }, 600);

    };
}
/*Loading*/
$(window)
    .load(function () {
        if (location.pathname != "/index.html")
            setTimeout(function () {
                $('.loading-container')
                    .addClass('loading-inactive');
            }, 0);
        else
            /*--------------------Loading Indicator::Demo Only------------------*/
            /*--Makes Loading indiator visible for 2 seconds in index page:: demo purpose only--*/
            setTimeout(function () {
                $('.loading-container')
                    .addClass('loading-inactive');
            }, 2000);
    });


/*Account Area --> Setting Button*/
$('#btn-setting')
    .on('click', function (e) {
        $('.navbar-account')
            .toggleClass('setting-open');
    });

/*Toggle FullScreen*/
$('#fullscreen-toggler')
    .on('click', function (e) {
        var element = document.documentElement;
        if (!$('body')
            .hasClass("full-screen")) {

            $('body')
                .addClass("full-screen");
            $('#fullscreen-toggler')
                .addClass("active");
            if (element.requestFullscreen) {
                element.requestFullscreen();
            } else if (element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
            } else if (element.webkitRequestFullscreen) {
                element.webkitRequestFullscreen();
            } else if (element.msRequestFullscreen) {
                element.msRequestFullscreen();
            }

        } else {

            $('body')
                .removeClass("full-screen");
            $('#fullscreen-toggler')
                .removeClass("active");

            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
            }

        }
    });

/*Handles Popovers*/
var popovers = $('[data-toggle=popover]');
$.each(popovers, function () {
    $(this)
        .popover({
            html: true,
            template: '<div class="popover ' + $(this)
                .data("class") +
                '"><div class="arrow"></div><h3 class="popover-title ' +
                $(this)
                .data("titleclass") + '">Popover right</h3><div class="popover-content"></div></div>'
        });
});

var hoverpopovers = $('[data-toggle=popover-hover]');
$.each(hoverpopovers, function () {
    $(this)
        .popover({
            html: true,
            template: '<div class="popover ' + $(this)
                .data("class") +
                '"><div class="arrow"></div><h3 class="popover-title ' +
                $(this)
                .data("titleclass") + '">Popover right</h3><div class="popover-content"></div></div>',
            trigger: "hover"
        });
});


/*Handles ToolTips*/
$("[data-toggle=tooltip]")
    .tooltip({
        html: true
    });

handleSideMenu();
handleSettings();
initiateWidgetBoxes();
initiateColorPickers();

function handleSideMenu() {

    //Sidebar Toggler
    $(".sidebar-toggler")
        .on('click', function () {
            $("#sidebar")
                .toggleClass("hide");
            $(".sidebar-toggler")
                .toggleClass("active");

            return false;
        });
    //End Sidebar Toggler

    //Sidebar Collapse
    var b = $("#sidebar")
        .hasClass("menu-compact");
    $("#sidebar-collapse")
        .find('.collapse-icon')
        .on('click', function () {
            $("#sidebar")
                .toggleClass("menu-compact");
            if ($(this)
                .hasClass("fa-arrow-right")) {
                $(this)
                    .removeClass("fa-arrow-right")
                    .addClass("fa-arrow-left");
            } else {
                $(this)
                    .removeClass("fa-arrow-left")
                    .addClass("fa-arrow-right");
            }
            b = $("#sidebar")
                .hasClass("menu-compact");
            if (b) {
                $(".open > .submenu")
                    .removeClass("open");
            }
        });
    //End Sidebar Collapse


    //Sidebar Menu Handle
    $(".sidebar-menu")
        .on('click', function (g) {
            var menuLink = $(g.target)
                .closest("a");
            if (!menuLink || menuLink.length == 0) {
                return;
            }
            if (!menuLink.hasClass("menu-dropdown")) {
                if (b && menuLink.get(0)
                    .parentNode.parentNode == this) {
                    var menuText = menuLink.find(".menu-text")
                        .get(0);
                    if (g.target != menuText && !$.contains(menuText, g.target)) {
                        return false;
                    }
                }
                return;
            }
            var d = menuLink.next()
                .get(0);
            if (!$(d)
                .is(":visible")) {
                var c = $(d.parentNode)
                    .closest("ul");
                if (b && c.hasClass("sidebar-menu")) {
                    return;
                }
                c.find("> .open > .submenu")
                    .each(function () {
                        if (this != d && !$(this.parentNode)
                            .hasClass("active")) {
                            $(this)
                                .slideUp(200)
                                .parent()
                                .removeClass("open");
                        }
                    });
            } else { }
            if (b && $(d.parentNode.parentNode)
                .hasClass("sidebar-menu")) {
                return false;
            }
            $(d)
                .slideToggle(200)
                .parent()
                .toggleClass("open");
            return false;
        });
    //End Sidebar Menu Handle
}

function initiateWidgetBoxes() {
    $(".page-content")
        .delegate(".widget-toolbar > [data-action]", "click", function (event) {
        	console.log("hello")
            event.preventDefault();
            var widget = $(this);
            console.log(widget)
            var action = widget.data("action");
            var widgetbox = widget.closest(".widget-box");
            var button;
            switch (action) {
                //Collapse Button
                case "collapse":
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
                    break;
                    //Dispose Button
                case "dispose":
                    var n = parseInt(widget.data("dispose-speed")) || 300;
                    widgetbox.hide(n, function () {
                        widgetbox.remove();
                    });
                    break;
                    //Refresh Button
                case "refresh":
                    widget.blur();
                    var m = false;
                    if (widgetbox.css("position") == "static") {
                        m = true;
                        widgetbox.addClass("position-relative");
                    }
                    widgetbox.append('<div class="widget-box-layer"><i class="fa fa-refresh fa-spin fa-2x white"></i></div>');
                    setTimeout(function () {
                        widgetbox.find(".widget-box-layer")
                            .remove();
                        if (m) {
                            widgetbox.removeClass("position-relative");
                        }
                    }, parseInt(Math.random() * 1000 + 1000));
                    break;
                    //Config Button
                case "config":
                    break;
                    //Maximize Button
                case "maximize":
                    button = widget.find("[class*=fa-]")
                        .eq(0);
                    var compress = "fa-compress";
                    var expand = "fa-expand";
                    if (widgetbox.hasClass("maximized")) {
                        if (button) {
                            button.addClass(expand)
                                .removeClass(compress);
                        }
                        widgetbox.removeClass("maximized");
                        widgetbox.find(".widget-body")
                            .css("height", "auto");
                    } else {
                        if (button) {
                            button.addClass(compress)
                                .removeClass(expand);
                        }
                        widgetbox.addClass("maximized");
                        maximize(widgetbox);
                    }
                    break;
                default:
                    break;

            }
        });
}

// Fullscreen Widget
function maximize(widgetbox) {
    if (widgetbox) {
        var windowHeight = $(window)
            .height();
        var headerHeight = widgetbox.find(".widget-header")
            .height();
        widgetbox.find(".widget-body")
            .height(windowHeight - headerHeight);
    }
}

/* Scroll To */
function scrollTo(el, offeset) {
    var pos = (el && el.size() > 0) ? el.offset()
        .top : 0;
    jQuery('html,body')
        .animate({
            scrollTop: pos + (offeset ? offeset : 0)
        }, 'slow');
}



/*Show Notification*/
function Notify(message, position, timeout, theme, icon, closable) {
    toastr.options.positionClass = 'toast-' + position;
    toastr.options.extendedTimeOut = 0; //1000;
    toastr.options.timeOut = timeout;
    toastr.options.closeButton = closable;
    toastr.options.iconClass = icon + ' toast-' + theme;
    toastr['custom'](message);
}

/*#region Simple ColorPicker*/
function initiateColorPickers() {
    var colorpickers = $('[data-toggle=simplecolorpicker]');
    $.each(colorpickers, function () {
        var colorpicker = $(this);
        var colorlist = "";
        var target = $(this)
            .data("target");
        var selectedcolor = "";
        colorpicker.hide()
            .find("option")
            .each(function () {
                var colorpickbtn = "colorpick-btn";
                if (this.selected) {
                    colorpickbtn += " selected";
                    selectedcolor = this.value;
                }
                colorlist += '<li><a class="' + colorpickbtn + '" href="#" style="background-color:' + this.value + ';" data-color="' + this.value + '" data-toggle="tooltip" data-placement="bottom" data-original-title="' + $(this)
                    .attr('title') + '"></a></li>';
            })
            .end()
            .on('change', function () {
                $(this)
                    .next()
                    .find(".btn-colorpicker")
                    .css("background-color", this.value);
                $(target)
                    .removeClass(function (index, css) {
                        return (css.match(/\bbg-\S+/g) || [])
                            .join(' ');
                    });
                var bgclass = $(this)
                    .find(":selected")
                    .data("class");
                if (bgclass != 'clear')
                    $(target)
                    .addClass('bg-' + $(this)
                        .find(":selected")
                        .data("class"));
            })
            .after('<div class="dropdown dropdown-colorpicker"><a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="btn-colorpicker" style="background-color:' + selectedcolor + '"></span></a><ul class="dropdown-menu dropdown-arrow" >' + colorlist + "</ul></div>")
            .next()
            .find(".dropdown-menu")
            .on('click', function (clickevent) {
                var clicktarget = $(clickevent.target);
                if (!clicktarget.is(".colorpick-btn")) {
                    return false;
                }
                clicktarget.closest("ul")
                    .find(".selected")
                    .removeClass("selected");
                clicktarget.addClass("selected");
                var clicktargetcolor = clicktarget.data("color");
                colorpicker.val(clicktargetcolor)
                    .change();
                clickevent.preventDefault();
                return true;
            });
    });
}
/*#endregion Simple ColorPicker*/

/*#region handle Settings*/
function handleSettings() {
    $('#checkbox_fixednavbar')
        .change(function () {
            $('.navbar')
                .toggleClass('navbar-fixed-top');

            if (($('#checkbox_fixedsidebar')
                .is(":checked"))) {
                $('#checkbox_fixedsidebar')
                    .prop('checked', false);
                $('.page-sidebar')
                    .toggleClass('sidebar-fixed');
            }

            if (($('#checkbox_fixedbreadcrumbs')
                .is(":checked")) && !($(this)
                .is(":checked"))) {
                $('#checkbox_fixedbreadcrumbs')
                    .prop('checked', false);
                $('.page-breadcrumbs')
                    .toggleClass('breadcrumbs-fixed');
            }

            if (($('#checkbox_fixedheader')
                .is(":checked")) && !($(this)
                .is(":checked"))) {
                $('#checkbox_fixedheader')
                    .prop('checked', false);
                $('.page-header')
                    .toggleClass('page-header-fixed');
            }
        });

    $('#checkbox_fixedsidebar')
        .change(function () {

            $('.page-sidebar')
                .toggleClass('sidebar-fixed');

            if (!($('#checkbox_fixednavbar')
                .is(":checked"))) {
                $('#checkbox_fixednavbar')
                    .prop('checked', true);
                $('.navbar')
                    .toggleClass('navbar-fixed-top');
            }
            if (($('#checkbox_fixedbreadcrumbs')
                .is(":checked")) && !($(this)
                .is(":checked"))) {
                $('#checkbox_fixedbreadcrumbs')
                    .prop('checked', false);
                $('.page-breadcrumbs')
                    .toggleClass('breadcrumbs-fixed');
            }

            if (($('#checkbox_fixedheader')
                .is(":checked")) && !($(this)
                .is(":checked"))) {
                $('#checkbox_fixedheader')
                    .prop('checked', false);
                $('.page-header')
                    .toggleClass('page-header-fixed');
            }
        });
    $('#checkbox_fixedbreadcrumbs')
        .change(function () {

            $('.page-breadcrumbs')
                .toggleClass('breadcrumbs-fixed');

            if (!($('#checkbox_fixedsidebar')
                .is(":checked"))) {
                $('#checkbox_fixedsidebar')
                    .prop('checked', true);
                $('.page-sidebar')
                    .toggleClass('sidebar-fixed');
            }
            if (!($('#checkbox_fixednavbar')
                .is(":checked"))) {
                $('#checkbox_fixednavbar')
                    .prop('checked', true);
                $('.navbar')
                    .toggleClass('navbar-fixed-top');
            }
            if (($('#checkbox_fixedheader')
                .is(":checked")) && !($(this)
                .is(":checked"))) {
                $('#checkbox_fixedheader')
                    .prop('checked', false);
                $('.page-header')
                    .toggleClass('page-header-fixed');
            }
        });

    $('#checkbox_fixedheader')
        .change(function () {

            $('.page-header')
                .toggleClass('page-header-fixed');

            if (!($('#checkbox_fixedbreadcrumbs')
                .is(":checked"))) {
                $('#checkbox_fixedbreadcrumbs')
                    .prop('checked', true);
                $('.page-breadcrumbs')
                    .toggleClass('breadcrumbs-fixed');
            }

            if (!($('#checkbox_fixedsidebar')
                .is(":checked"))) {
                $('#checkbox_fixedsidebar')
                    .prop('checked', true);
                $('.page-sidebar')
                    .toggleClass('sidebar-fixed');
            }
            if (!($('#checkbox_fixednavbar')
                .is(":checked"))) {
                $('#checkbox_fixednavbar')
                    .prop('checked', true);
                $('.navbar')
                    .toggleClass('navbar-fixed-top');
            }
        });
}
/*#endregion handle Settings*/

/*#region Get Colors*/
//Get colors from a string base on theme colors
function getcolor(colorString) {
    switch (colorString) {
        case ("themeprimary"):
            return themeprimary;
        case ("themesecondary"):
            return themesecondary;
        case ("themethirdcolor"):
            return themethirdcolor;
        case ("themefourthcolor"):
            return themefourthcolor;
        case ("themefifthcolor"):
            return themefifthcolor;
        default:
            return colorString;
    }
}
/*#endregion Get Colors*/

//Switch Classes Function
function switchClasses(firstClass, secondClass) {

    var firstclasses = document.getElementsByClassName(firstClass);

    for (i = firstclasses.length - 1; i >= 0; i--) {
        if (!hasClass(firstclasses[i], 'dropdown-menu')) {
            addClass(firstclasses[i], firstClass + '-temp');
            removeClass(firstclasses[i], firstClass);
        }
    }

    var secondclasses = document.getElementsByClassName(secondClass);

    for (i = secondclasses.length - 1; i >= 0; i--) {
        if (!hasClass(secondclasses[i], 'dropdown-menu')) {
            addClass(secondclasses[i], firstClass);
            removeClass(secondclasses[i], secondClass);
        }
    }

    tempClasses = document.getElementsByClassName(firstClass + '-temp');

    for (i = tempClasses.length - 1; i >= 0; i--) {
        if (!hasClass(tempClasses[i], 'dropdown-menu')) {
            addClass(tempClasses[i], secondClass);
            removeClass(tempClasses[i], firstClass + '-temp');
        }
    }
}

//Add Classes Function
function addClass(elem, cls) {
    var oldCls = elem.className;
    if (oldCls) {
        oldCls += " ";
    }
    elem.className = oldCls + cls;
}

//Remove Classes Function
function removeClass(elem, cls) {
    var str = " " + elem.className + " ";
    elem.className = str.replace(" " + cls, "").replace(/^\s+/g, "").replace(/\s+$/g, "");
}

//Has Classes Function
function hasClass(elem, cls) {
    var str = " " + elem.className + " ";
    var testCls = " " + cls + " ";
    return (str.indexOf(testCls) != -1);
}