//** Smooth Navigational Menu- By Dynamic Drive DHTML code library: http://www.dynamicdrive.com
//** May 30th, 2013 (v2.1) Change from version sniffing to means testing for jQuery versions which require added code for click toggle event handling

var selosmenu = {

///////////////////////// Global Configuration Options: /////////////////////////

//Specify full URL to down and right arrow images (23 is padding-right for top level LIs with drop downs, 6 is for vertical top level items with fly outs):
    arrowimages: {down: ['downarrowclass', '../resources/images/shared/down.gif', 23], right: ['rightarrowclass', '../resources/images/shared/right.gif', 6]},
    transition: {overtime: 300, outtime: 300}, //duration of slide in/ out animation, in milliseconds
    shadow: true, //enable shadow? (offsets now set in selosmenu.css stylesheet)
    showhidedelay: {showdelay: 0, hidedelay: 300}, //set delay in milliseconds before sub menus appear and disappear, respectively
    zindexvalue: 500, //set z-index value for menus
    closeonnonmenuclick: true, //when clicking outside of any "toggle" method menu, should all "toggle" menus close?
    closeonmouseout: false, //when leaving a "toggle" menu, should all "toggle" menus close? Will not work on touchscreen

/////////////////////// End Global Configuration Options ////////////////////////

    overarrowre: /(?=\.(gif|jpg|jpeg|png|bmp))/i,
    overarrowaddtofilename: '_over',
    detecttouch: !!('ontouchstart' in window) || !!('ontouchstart' in document.documentElement) || !!window.ontouchstart || !!window.Touch || !!window.onmsgesturechange || (window.DocumentTouch && window.document instanceof window.DocumentTouch),
    detectwebkit: navigator.userAgent.toLowerCase().indexOf("applewebkit") > -1, //detect WebKit browsers (Safari, Chrome etc)
    idevice: /ipad|iphone/i.test(navigator.userAgent),
    detectie6: (function () {
        var ie;
        return (ie = /MSIE (\d+)/.exec(navigator.userAgent)) && ie[1] < 7;
    })(),
    detectie9: (function () {
        var ie;
        return (ie = /MSIE (\d+)/.exec(navigator.userAgent)) && ie[1] > 8;
    })(),
    ie9shadow: function () {
    },
    css3support: typeof document.documentElement.style.boxShadow === 'string' || (!document.all && document.querySelector), //detect browsers that support CSS3 box shadows (ie9+ or FF3.5+, Safari3+, Chrome etc)
    prevobjs: [], menus: null,

    executelink: function ($, prevobjs, e) {
        var prevscount = prevobjs.length, link = e.target;
        while (--prevscount > -1) {
            if (prevobjs[prevscount] === this) {
                prevobjs.splice(prevscount, 1);
                if (link.href !== selosmenu.emptyhash && link.href && $(link).is('a') && !$(link).children('span.' + selosmenu.arrowimages.down[0] + ', span.' + selosmenu.arrowimages.right[0]).size()) {
                    if (link.target && link.target !== '_self') {
                        window.open(link.href, link.target);
                    } else {
                        window.location.href = link.href;
                    }
                    e.stopPropagation();
                }
            }
        }
    },

    updateprev: function ($, prevobjs, $curobj) {
        var prevscount = prevobjs.length, prevobj, $indexobj = $curobj.parents().add(this);
        while (--prevscount > -1) {
            if ($indexobj.index((prevobj = prevobjs[prevscount])) < 0) {
                $(prevobj).trigger('click', [1]);
                prevobjs.splice(prevscount, 1);
            }
        }
        prevobjs.push(this);
    },

    subulpreventemptyclose: function (e) {
        var link = e.target;
        if (link.href === selosmenu.emptyhash && $(link).parent('li').find('ul').size() < 1) {
            e.preventDefault();
            e.stopPropagation();
        }
    },

    getajaxmenu: function ($, setting, nobuild) { //function to fetch external page containing the panel DIVs
        var $menucontainer = $('#' + setting.contentsource[0]); //reference empty div on page that will hold menu
        $menucontainer.html("Loading Menu...");
        $.ajax({
            url: setting.contentsource[1], //path to external menu file
            async: true,
            error: function (ajaxrequest) {
                $menucontainer.html('Error fetching content. Server Response: ' + ajaxrequest.responseText);
            },
            success: function (content) {
                $menucontainer.html(content);
                !!!nobuild && selosmenu.buildmenu($, setting);
            }
        });
    },

    closeall: function (e) {
        var smoothmenu = selosmenu, prevscount;
        if (!smoothmenu.globaltrackopen) {
            return;
        }
        if (e.type === 'mouseleave' || ((e.type === 'click' || e.type === 'touchstart') && smoothmenu.menus.index(e.target) < 0)) {
            prevscount = smoothmenu.prevobjs.length;
            while (--prevscount > -1) {
                $(smoothmenu.prevobjs[prevscount]).trigger('click');
                smoothmenu.prevobjs.splice(prevscount, 1);
            }
        }
    },

    emptyhash: $('<a href="#"></a>').get(0).href,

    buildmenu: function ($, setting) {
        var smoothmenu = selosmenu;
        smoothmenu.globaltrackopen = smoothmenu.closeonnonmenuclick || smoothmenu.closeonmouseout;
        var zsub = 0; //subtractor to be incremented so that each top level menu can be covered by previous one's drop downs
        var prevobjs = smoothmenu.globaltrackopen ? smoothmenu.prevobjs : [];
        var $mainparent = $("#" + setting.mainmenuid).removeClass("selosmenu selosmenu-v").addClass(setting.classname || "selosmenu");
        //setting.$mainparent = $mainparent;
        var $mainmenu = $mainparent.find('>ul'); //reference main menu UL
        var method = smoothmenu.detecttouch ? 'toggle' : setting.method === 'toggle' ? 'toggle' : 'hover';
        var $topheaders = $mainmenu.find('>span>li>ul').parent();//has('ul');
        //$mainparent.data('$headers', $topheaders);
        var orient = setting.orientation != 'v' ? 'down' : 'right', $parentshadow = $(document.body);
        $mainmenu.click(function (e) {
            e.target.href === smoothmenu.emptyhash && e.preventDefault();
        });
        if (method === 'toggle') {
            if (smoothmenu.globaltrackopen) {
                smoothmenu.menus = smoothmenu.menus ? smoothmenu.menus.add($mainmenu.add($mainmenu.find('*'))) : $mainmenu.add($mainmenu.find('*'));
            }
            if (smoothmenu.closeonnonmenuclick) {
                if (orient === 'down') {
                    $mainparent.click(function (e) {
                        e.stopPropagation();
                    });
                }
                $(document).unbind('click.smoothmenu').bind('click.smoothmenu', smoothmenu.closeall);
                if (smoothmenu.idevice) {
                    document.removeEventListener('touchstart', smoothmenu.closeall, false);
                    document.addEventListener('touchstart', smoothmenu.closeall, false);
                }
            } else if (setting.closeonnonmenuclick) {
                if (orient === 'down') {
                    $mainparent.click(function (e) {
                        e.stopPropagation();
                    });
                }
                $(document).bind('click.' + setting.mainmenuid, function (e) {
                    $mainmenu.find('li>a.selected').parent().trigger('click');
                });
                if (smoothmenu.idevice) {
                    document.addEventListener('touchstart', function (e) {
                        $mainmenu.find('li>a.selected').parent().trigger('click');
                    }, false);
                }
            }
            if (smoothmenu.closeonmouseout) {
                var $leaveobj = orient === 'down' ? $mainparent : $mainmenu;
                $leaveobj.bind('mouseleave.smoothmenu', smoothmenu.closeall);
            } else if (setting.closeonmouseout) {
                var $leaveobj = orient === 'down' ? $mainparent : $mainmenu;
                $leaveobj.bind('mouseleave.smoothmenu', function () {
                    $mainmenu.find('li>a.selected').parent().trigger('click');
                });
            }
            if (!$('style[title="selosmenushadowsnone"]').size()) {
                $('head').append('<style title="selosmenushadowsnone" type="text/css">.selosmenushadowsnone{display:none!important;}</style>');
            }
            var shadowstimer;
            $(window).resize(function () {
                clearTimeout(shadowstimer);
                var $selected = $mainmenu.find('li>a.selected').parent(),
                    $shadows = $('.ddshadow').addClass('selosmenushadowsnone');
                $selected.eq(0).trigger('click');
                $selected.trigger('click');
                shadowstimer = setTimeout(function () {
                    $shadows.removeClass('selosmenushadowsnone');
                }, 100);
            });
        }

        $topheaders.each(function () {
            var $curobj = $(this).css({zIndex: (setting.zindexvalue || smoothmenu.zindexvalue) + zsub--}); //reference current LI header
            var $subul = $curobj.children('ul:eq(0)').css({display: 'block'}).data('timers', {});
            var $link = $curobj.children("a:eq(0)").css({paddingRight: smoothmenu.arrowimages[orient][2]}).append( //add arrow images
                '<span style="display: block;" class="' + smoothmenu.arrowimages[orient][0] + '"></span>'
            );
            var dimensions = {
                w: $link.outerWidth(),
                h: $curobj.innerHeight(),
                subulw: $subul.outerWidth(),
                subulh: $subul.outerHeight()
            };
            $subul.css({top: orient === 'down' ? dimensions.h : 0});
            function restore() {
                $link.removeClass('selected');
            }

            method === 'toggle' && $subul.click(smoothmenu.subulpreventemptyclose);
            $curobj[method](
                function (e) {
                    if (!$curobj.data('headers')) {
                        smoothmenu.buildsubheaders($, $subul.find('>li>ul').parent(), setting, method, prevobjs);
                        $curobj.data('headers', true).find('>ul').css({display: 'none', visibility: 'visible'});
                    }
                    method === 'toggle' && smoothmenu.updateprev.call(this, $, prevobjs, $curobj);
                    clearTimeout($subul.data('timers').hidetimer);
                    $link.addClass('selected');
                    $subul.data('timers').showtimer = setTimeout(function () {
                        var menuleft = orient === 'down' ? 0 : dimensions.w;
                        var menumoved = menuleft;
                        menuleft = ($curobj.offset().left + menuleft + dimensions.subulw > $(window).width()) ? (orient === 'down' ? -dimensions.subulw + dimensions.w : -dimensions.w) : menuleft; //calculate this sub menu's offsets from its parent
                        menumoved = menumoved !== menuleft;
                        $subul.css({left: menuleft, width: dimensions.subulw}).stop(true, true).animate({height: 'show', opacity: 'show'}, smoothmenu.transition.overtime, function () {
                            this.style.removeAttribute && this.style.removeAttribute('filter');
                        });
                        if (menumoved) {
                            $subul.addClass('repositioned');
                        } else {
                            $subul.removeClass('repositioned');
                        }
                        if (setting.shadow) {
                            if (!$curobj.data('$shadow')) {
                                $curobj.data('$shadow', $('<div></div>').addClass('ddshadow toplevelshadow').prependTo($parentshadow).css({zIndex: $curobj.css('zIndex')}));  //insert shadow DIV and set it to parent node for the next shadow div
                            }
                            smoothmenu.ie9shadow($curobj.data('$shadow'));
                            var offsets = $subul.offset();
                            var shadowleft = offsets.left;
                            var shadowtop = offsets.top;
                            $curobj.data('$shadow').css({overflow: 'visible', width: dimensions.subulw, left: shadowleft, top: shadowtop}).stop(true, true).animate({height: dimensions.subulh}, smoothmenu.transition.overtime);
                        }
                    }, smoothmenu.showhidedelay.showdelay);
                },
                function (e, speed) {
                    var $shadow = $curobj.data('$shadow');
                    if (method === 'hover') {
                        restore();
                    }
                    else {
                        smoothmenu.executelink.call(this, $, prevobjs, e);
                    }
                    clearTimeout($subul.data('timers').showtimer);
                    $subul.data('timers').hidetimer = setTimeout(function () {
                        $subul.stop(true, true).animate({height: 'hide', opacity: 'hide'}, speed || smoothmenu.transition.outtime, function () {
                            method === 'toggle' && restore();
                        });
                        if ($shadow) {
                            if (!smoothmenu.css3support && smoothmenu.detectwebkit) { //in WebKit browsers, set first child shadow's opacity to 0, as "overflow:hidden" doesn't work in them
                                $shadow.children('div:eq(0)').css({opacity: 0});
                            }
                            $shadow.stop(true, true).animate({height: 0}, speed || smoothmenu.transition.outtime, function () {
                                if (method === 'toggle') {
                                    this.style.overflow = 'hidden';
                                }
                            });
                        }
                    }, smoothmenu.showhidedelay.hidedelay);
                }
            ); //end hover/toggle
        }); //end $topheaders.each()
    },

    buildsubheaders: function ($, $headers, setting, method, prevobjs) {
        //setting.$mainparent.data('$headers').add($headers);
        $headers.each(function () { //loop through each LI header
            var smoothmenu = selosmenu;
            var $curobj = $(this).css({zIndex: $(this).parent('ul').css('z-index')}); //reference current LI header
            var $subul = $curobj.children('ul:eq(0)').css({display: 'block'}).data('timers', {}), $parentshadow;
            method === 'toggle' && $subul.click(smoothmenu.subulpreventemptyclose);
            var $link = $curobj.children("a:eq(0)").append( //add arrow images
                '<span style="display: block;" class="' + smoothmenu.arrowimages['right'][0] + '"></span>'
            );
            var dimensions = {
                w: $link.outerWidth(),
                subulw: $subul.outerWidth(),
                subulh: $subul.outerHeight()
            };
            $subul.css({top: 0});
            function restore() {
                $link.removeClass('selected');
            }

            $curobj[method](
                function (e) {
                    if (!$curobj.data('headers')) {
                        smoothmenu.buildsubheaders($, $subul.find('>li>ul').parent(), setting, method, prevobjs);
                        $curobj.data('headers', true).find('>ul').css({display: 'none', visibility: 'visible'});
                    }
                    method === 'toggle' && smoothmenu.updateprev.call(this, $, prevobjs, $curobj);
                    clearTimeout($subul.data('timers').hidetimer);
                    $link.addClass('selected');
                    $subul.data('timers').showtimer = setTimeout(function () {
                        var menuleft = dimensions.w;
                        var menumoved = menuleft;
                        menuleft = ($curobj.offset().left + menuleft + dimensions.subulw > $(window).width()) ? -dimensions.w : menuleft; //calculate this sub menu's offsets from its parent
                        menumoved = menumoved !== menuleft;
                        $subul.css({left: menuleft, width: dimensions.subulw}).stop(true, true).animate({height: 'show', opacity: 'show'}, smoothmenu.transition.overtime, function () {
                            this.style.removeAttribute && this.style.removeAttribute('filter');
                        });
                        if (menumoved) {
                            $subul.addClass('repositioned');
                        } else {
                            $subul.removeClass('repositioned');
                        }
                        if (setting.shadow) {
                            if (!$curobj.data('$shadow')) {
                                $parentshadow = $curobj.parents("li:eq(0)").data('$shadow');
                                $curobj.data('$shadow', $('<div></div>').addClass('ddshadow').prependTo($parentshadow).css({zIndex: $parentshadow.css('z-index')}));  //insert shadow DIV and set it to parent node for the next shadow div
                            }
                            var offsets = $subul.offset();
                            var shadowleft = menuleft;
                            var shadowtop = $curobj.position().top;
                            if (smoothmenu.detectwebkit && !smoothmenu.css3support) { //in WebKit browsers, restore shadow's opacity to full
                                $curobj.data('$shadow').css({opacity: 1});
                            }
                            $curobj.data('$shadow').css({overflow: 'visible', width: dimensions.subulw, left: shadowleft, top: shadowtop}).stop(true, true).animate({height: dimensions.subulh}, smoothmenu.transition.overtime);
                        }
                    }, smoothmenu.showhidedelay.showdelay);
                },
                function (e, speed) {
                    var $shadow = $curobj.data('$shadow');
                    if (method === 'hover') {
                        restore();
                    }
                    else {
                        smoothmenu.executelink.call(this, $, prevobjs, e);
                    }
                    clearTimeout($subul.data('timers').showtimer);
                    $subul.data('timers').hidetimer = setTimeout(function () {
                        $subul.stop(true, true).animate({height: 'hide', opacity: 'hide'}, speed || smoothmenu.transition.outtime, function () {
                            method === 'toggle' && restore();
                        });
                        if ($shadow) {
                            if (!smoothmenu.css3support && smoothmenu.detectwebkit) { //in WebKit browsers, set first child shadow's opacity to 0, as "overflow:hidden" doesn't work in them
                                $shadow.children('div:eq(0)').css({opacity: 0});
                            }
                            $shadow.stop(true, true).animate({height: 0}, speed || smoothmenu.transition.outtime, function () {
                                if (method === 'toggle') {
                                    this.style.overflow = 'hidden';
                                }
                            });
                        }
                    }, smoothmenu.showhidedelay.hidedelay);
                }
            ); //end hover/toggle for subheaders
        }); //end $headers.each() for subheaders
    },

    init: function (setting) {
        if (this.detectie6 && parseFloat(jQuery.fn.jquery) > 1.3) {
            this.init = function (setting) {
                if (typeof setting.contentsource == "object") { //if external ajax menu
                    jQuery(function ($) {
                        selosmenu.getajaxmenu($, setting, 'nobuild');
                    });
                }
                return false;
            };
            jQuery('link[href*="selosmenu"]').attr('disabled', true);
            jQuery(function ($) {
                alert('You Seriously Need to Update Your Browser!\n\nDynamic Drive Smooth Navigational Menu Showing Text Only Menu(s)\n\nDEVELOPER\'s NOTE: This script will run in IE 6 when using jQuery 1.3.2 or less,\nbut not real well.');
                $('link[href*="selosmenu"]').attr('disabled', true);
            });
            return this.init(setting);
        }
        var mainmenuid = '#' + setting.mainmenuid, right, down, stylestring = ['</style>\n'], stylesleft = setting.arrowswap ? 4 : 2;

        function addstyles() {
            if (stylesleft) {
                return;
            }
            if (typeof setting.customtheme == "object" && setting.customtheme.length == 2) { //override default menu colors (default/hover) with custom set?
                var mainselector = (setting.orientation == "v") ? mainmenuid : mainmenuid + ', ' + mainmenuid;
                stylestring.push([mainselector, ' ul li a {background:', setting.customtheme[0], ';}\n',
                    mainmenuid, ' ul li a:hover {background:', setting.customtheme[1], ';}'].join(''));
            }
            stylestring.push('\n<style type="text/css">');
            stylestring.reverse();
            jQuery('head').append(stylestring.join('\n'));
        }

        if (setting.arrowswap) {
            right = selosmenu.arrowimages.right[1].replace(selosmenu.overarrowre, selosmenu.overarrowaddtofilename);
            down = selosmenu.arrowimages.down[1].replace(selosmenu.overarrowre, selosmenu.overarrowaddtofilename);
            jQuery(new Image()).bind('load error',function (e) {
                setting.rightswap = e.type === 'load';
                if (setting.rightswap) {
                    stylestring.push([mainmenuid, ' ul li a:hover .', selosmenu.arrowimages.right[0], ', ',
                        mainmenuid, ' ul li a.selected .', selosmenu.arrowimages.right[0],
                        ' { background-image: url(', this.src, ');}'].join(''));
                }
                --stylesleft;
                addstyles();
            }).attr('src', right);
            jQuery(new Image()).bind('load error',function (e) {
                setting.downswap = e.type === 'load';
                if (setting.downswap) {
                    stylestring.push([mainmenuid, ' ul li a:hover .', selosmenu.arrowimages.down[0], ', ',
                        mainmenuid, ' ul li a.selected .', selosmenu.arrowimages.down[0],
                        ' { background-image: url(', this.src, ');}'].join(''));
                }
                --stylesleft;
                addstyles();
            }).attr('src', down);
        }
        jQuery(new Image()).bind('load error',function (e) {
            if (e.type === 'load') {
                stylestring.push([mainmenuid + ' ul li a .', selosmenu.arrowimages.right[0], ' { background: url(', this.src, ') no-repeat;width:', this.width, 'px;height:', this.height, 'px;}'].join(''));
            }
            --stylesleft;
            addstyles();
        }).attr('src', selosmenu.arrowimages.right[1]);
        jQuery(new Image()).bind('load error',function (e) {
            if (e.type === 'load') {
                stylestring.push([mainmenuid + ' ul li a .', selosmenu.arrowimages.down[0], ' { background: url(', this.src, ') no-repeat;width:', this.width, 'px;height:', this.height, 'px;}'].join(''));
            }
            --stylesleft;
            addstyles();
        }).attr('src', selosmenu.arrowimages.down[1]);
        setting.shadow = this.detectie6 && (setting.method === 'hover' || setting.orientation === 'v') ? false : setting.shadow || this.shadow; //in IE6, always disable shadow except for horizontal toggle menus
        jQuery(document).ready(function ($) { //ajax menu?
            if (setting.shadow && selosmenu.css3support) {
                $('body').addClass('ddcss3support');
            }
            if (typeof setting.contentsource == "object") { //if external ajax menu
                selosmenu.getajaxmenu($, setting);
            }
            else { //else if markup menu
                selosmenu.buildmenu($, setting);
            }
        });
    }
}; //end selosmenu variable


// Patch for jQuery 1.9+ which lack click toggle (deprecated in 1.8, removed in 1.9)
// Will not run if using another patch like jQuery Migrate, which also takes care of this
if (
    (function ($) {
        var clicktogglable = false;
        try {
            $('<a href="#"></a>').toggle(function () {
            },function () {
                clicktogglable = true;
            }).trigger('click').trigger('click');
        } catch (e) {
        }
        return !clicktogglable;
    })(jQuery)
    ) {
    (function () {
        var toggleDisp = jQuery.fn.toggle; // There's an animation/css method named .toggle() that toggles display. Save a reference to it.
        jQuery.extend(jQuery.fn, {
            toggle: function (fn, fn2) {
                // The method fired depends on the arguments passed.
                if (!jQuery.isFunction(fn) || !jQuery.isFunction(fn2)) {
                    return toggleDisp.apply(this, arguments);
                }
                // Save reference to arguments for access in closure
                var args = arguments, guid = fn.guid || jQuery.guid++,
                    i = 0,
                    toggler = function (event) {
                        // Figure out which function to execute
                        var lastToggle = ( jQuery._data(this, "lastToggle" + fn.guid) || 0 ) % i;
                        jQuery._data(this, "lastToggle" + fn.guid, lastToggle + 1);

                        // Make sure that clicks stop
                        event.preventDefault();

                        // and execute the function
                        return args[ lastToggle ].apply(this, arguments) || false;
                    };

                // link all the functions, so any of them can unbind this click handler
                toggler.guid = guid;
                while (i < args.length) {
                    args[ i++ ].guid = guid;
                }

                return this.click(toggler);
            }
        });
    })();
}

/* TECHNICAL NOTE: To overcome an intermittent layout bug in IE 9+, the script will change margin top and left for the shadows to 
 1px less than their computed values, and the first two values for the box-shadow property will be changed to 1px larger than
 computed, ex: -1px top and left margins and 6px 6px 5px #aaa box-shadow results in what appears to be a 5px box-shadow.
 Other browsers skip this step and it shouldn't affect you in most cases. In some rare cases it will result in
 slightly narrower (by 1px) box shadows for IE 9+ on one or more of the drop downs. Without this, sometimes
 the shadows could be 1px beyond their drop down resulting in a gap. This is the first of the two patches below.
 and also relates to the MS CSSOM which uses decimal fractions of pixels for layout while only reporting rounded values.
 There appears to be no computedStyle workaround for this one. */

//Scripted CSS Patch for IE 9+ intermittent mis-rendering of box-shadow elements (see above TECHNICAL NOTE for more info)
//And jQuery Patch for IE 9+ CSSOM re: offset Width and Height and re: getBoundingClientRect(). Both run only in IE 9 and later.
//IE 9 + uses decimal fractions of pixels internally for layout but only reports rounded values using the offset and getBounding methods.
//These are sometimes rounded inconsistently. This second patch gets the decimal values directly from computedStyle.
if (selosmenu.detectie9) {
    (function ($) { //begin Scripted CSS Patch
        function incdec(v, how) {
            return parseInt(v) + how + 'px';
        }

        selosmenu.ie9shadow = function ($elem) { //runs once
            var getter = document.defaultView.getComputedStyle($elem.get(0), null),
                curshadow = getter.getPropertyValue('box-shadow').split(' '),
                curmargin = {top: getter.getPropertyValue('margin-top'), left: getter.getPropertyValue('margin-left')};
            $('head').append(['\n<style title="ie9shadow" type="text/css">',
                '.ddcss3support .ddshadow {',
                '\tbox-shadow: ' + incdec(curshadow[0], 1) + ' ' + incdec(curshadow[1], 1) + ' ' + curshadow[2] + ' ' + curshadow[3] + ';',
                '}', '.ddcss3support .ddshadow.toplevelshadow {',
                '\topacity: ' + ($('.ddcss3support .ddshadow').css('opacity') - 0.1) + ';',
                '\tmargin-top: ' + incdec(curmargin.top, -1) + ';',
                '\tmargin-left: ' + incdec(curmargin.left, -1) + ';', '}',
                '</style>\n'].join('\n'));
            selosmenu.ie9shadow = function () {
            }; //becomes empty function after running once
        }; //end Scripted CSS Patch
        var jqheight = $.fn.height, jqwidth = $.fn.width; //begin jQuery Patch for IE 9+ .height() and .width()
        $.extend($.fn, {
            height: function () {
                var obj = this.get(0);
                if (this.size() < 1 || arguments.length || obj === window || obj === document) {
                    return jqheight.apply(this, arguments);
                }
                return parseFloat(document.defaultView.getComputedStyle(obj, null).getPropertyValue('height'));
            },
            innerHeight: function () {
                if (this.size() < 1) {
                    return null;
                }
                var val = this.height(), obj = this.get(0), getter = document.defaultView.getComputedStyle(obj, null);
                val += parseInt(getter.getPropertyValue('padding-top'));
                val += parseInt(getter.getPropertyValue('padding-bottom'));
                return val;
            },
            outerHeight: function (bool) {
                if (this.size() < 1) {
                    return null;
                }
                var val = this.innerHeight(), obj = this.get(0), getter = document.defaultView.getComputedStyle(obj, null);
                val += parseInt(getter.getPropertyValue('border-top-width'));
                val += parseInt(getter.getPropertyValue('border-bottom-width'));
                if (bool) {
                    val += parseInt(getter.getPropertyValue('margin-top'));
                    val += parseInt(getter.getPropertyValue('margin-bottom'));
                }
                return val;
            },
            width: function () {
                var obj = this.get(0);
                if (this.size() < 1 || arguments.length || obj === window || obj === document) {
                    return jqwidth.apply(this, arguments);
                }
                return parseFloat(document.defaultView.getComputedStyle(obj, null).getPropertyValue('width'));
            },
            innerWidth: function () {
                if (this.size() < 1) {
                    return null;
                }
                var val = this.width(), obj = this.get(0), getter = document.defaultView.getComputedStyle(obj, null);
                val += parseInt(getter.getPropertyValue('padding-right'));
                val += parseInt(getter.getPropertyValue('padding-left'));
                return val;
            },
            outerWidth: function (bool) {
                if (this.size() < 1) {
                    return null;
                }
                var val = this.innerWidth(), obj = this.get(0), getter = document.defaultView.getComputedStyle(obj, null);
                val += parseInt(getter.getPropertyValue('border-right-width'));
                val += parseInt(getter.getPropertyValue('border-left-width'));
                if (bool) {
                    val += parseInt(getter.getPropertyValue('margin-right'));
                    val += parseInt(getter.getPropertyValue('margin-left'));
                }
                return val;
            }
        }); //end jQuery Patch for IE 9+ .height() and .width()
    })(jQuery);
}