;(function($, namespace) {
    /**
     * Browsers detect of user agents.
     *
     * @author adlo
     */
    namespace.browser = (function() {
        'use strict';
        
        function isOpera() {
            return (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
        }
        function isFirefox() {
            return typeof InstallTrigger !== 'undefined';
        }
        function isSafari() {
            return /constructor/i.test(window.HTMLElement) || (function (p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));
        }
        function isIE() {
            return /*@cc_on!@*/false || !!document.documentMode;
        }
        function isEdge() {
            return !isIE() && !!window.StyleMedia;
        }
        function isChrome() {
            return !!window.chrome && !!window.chrome.webstore;
        }
        function isBlink() {
            return (isChrome() || isOpera()) && !!window.CSS;
        }
        // Public object
        return {
            isOpera: isOpera,
            isFirefox: isFirefox,
            isSafari: isSafari,
            isIE: isIE ,
            isEdge: isOpera,
            isChrome: isChrome,
            isBlink: isBlink 
        }
    });
    
    /**
     * UI function for MyHealth.
     *
     * @author adlo
     */
    namespace.ui = (function() {
        'use strict';

        // INIT METHODS
        function init() {
            // On first render in phone devices (> 425px L mobile), close navbar
            if ($(window).width() <= 425) {
                $("body").addClass("sidebar-toggled");
                $("body .sidebar.accordion").addClass("toggled");
            }
            reloadComponents();
        }
        function reloadComponents() {
            // Init overlapping menus
            $("[data-toggle=modal]").on("click", function() {
                var $btn = $(this);
                var currentDialog = $btn.closest('.modal-dialog');
                if (currentDialog.length != 0) {
                    var targetDialog = $($btn.attr('data-target'));
                    targetDialog.data('previous-dialog', currentDialog);
                    currentDialog.addClass('aside');
                    var stackedDialogCount = $('.modal .modal-dialog.aside').length;
                    if (stackedDialogCount <= 5){
                        currentDialog.addClass('aside-' + stackedDialogCount);
                    }
                }
            });
            $(".modal").on("hide.bs.modal", function() {
                var $dialog = $(this);
                var previousDialog = $dialog.data('previous-dialog');
                if (previousDialog) {
                    var stackedDialogCount = $('.modal .modal-dialog.aside').length;
                    previousDialog.removeClass('aside');
                    previousDialog.removeClass('aside-' + stackedDialogCount);
                    $dialog.data('previous-dialog', undefined);
                    if (stackedDialogCount > 0) {
                        $("body").addClass("u-modal-open");
                    }
                } else {
                    $("body").removeClass("u-modal-open");
                }
            });
        }
        // PUBLIC OBJECT
        $(function() {
            init();
        });
    }());
}(jQuery, window.myhealth = window.myhealth || {}))
