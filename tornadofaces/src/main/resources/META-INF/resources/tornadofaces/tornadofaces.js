// ExtendJS
!function(a){"use strict";function b(a){a.parent instanceof Function&&(b.apply(this,[a.parent]),this.super=c(this,d(this,this.constructor))),a.apply(this,arguments)}function c(a,b){for(var c in a)"super"!==c&&a[c]instanceof Function&&(b[c]=a[c].super||d(a,a[c]));return b}function d(a,b){var c=a.super;return b.super=function(){return a.super=c,b.apply(a,arguments)}}a.Class=function(){},a.Class.extend=function e(a){function d(){b!==arguments[0]&&(b.apply(this,[a]),c(this,this),this.initializer instanceof Function&&this.initializer.apply(this),this.constructor.apply(this,arguments))}return d.prototype=new this(b),d.prototype.constructor=d,d.toString=function(){return a.toString()},d.extend=function(b){return b.parent=a,e.apply(d,arguments)},d},a.Class=a.Class.extend(function(){this.constructor=function(){}})}(this);

TornadoFaces = {
    widget: {},
    widgets: {},
    animations: [],

    escapeClientId: function(id) {
        return "#" + id.replace(/:/g, "\\:");
    },

    debug: function(message) {
        if (console)
            console.debug(message);
    },

    cw: function(widgetName, conf) {
        if (TornadoFaces.widget[widgetName]) {
            if (TornadoFaces.widgets[conf.widgetVar])
                TornadoFaces.widgets[conf.widgetVar].refresh(conf);
            else
                TornadoFaces.widgets[conf.widgetVar] = new TornadoFaces.widget[widgetName](conf);

            return TornadoFaces.widgets[conf.widgetVar];
        } else {
            throw('No TornadoFaces Widget available named ' + widgetName);
        }
    },

    declareWidget: function(widgetName, widget) {
        TornadoFaces.widget[widgetName] = TornadoFaces.widget.BaseWidget.extend(widget);
    },

    // Animations from Foundations for Apps
    animate: function(element, futureState, animationIn, animationOut, endCallback) {
        var initClasses = ['ng-enter', 'ng-leave'];
        var activeClasses = ['ng-enter-active', 'ng-leave-active'];
        var activeGenericClass = 'is-active';
        var events = ['webkitAnimationEnd', 'mozAnimationEnd', 'MSAnimationEnd', 'oanimationend', 'animationend',
            'webkitTransitionEnd', 'otransitionend', 'transitionend'];
        var timedOut = true;
        var self = this;
        self.cancelAnimation = cancelAnimation;

        animateElement(futureState ? animationIn : animationOut, futureState, endCallback);

        function cancelAnimation() {
            deregisterElement(element);
            element.off(events.join(' ')); //kill all animation event handlers
            timedOut = false;
        }

        function registerElement(el) {
            var elObj = {
                el: el,
                animation: self
            };

            //kill in progress animations
            var inProgress = TornadoFaces.animations.filter(function(obj) {
                return obj.el === el;
            });

            if (inProgress.length > 0)
                inProgress[0].animation.cancelAnimation();

            TornadoFaces.animations.push(elObj);
        }

        function deregisterElement(el) {
            var index;
            TornadoFaces.animations.filter(function(obj, ind) {
                if (obj.el === el) {
                    index = ind;
                }
            });

            if (index >= 0)
                TornadoFaces.animations.splice(index, 1);
        }

        function reflow() {
            return element[0].offsetWidth;
        }

        function reset() {
            element[0].style.transitionDuration = 0;
            element.removeClass(initClasses.join(' ') + ' ' + activeClasses.join(' ') + ' ' + animationIn + ' ' + animationOut);
        }

        function animateElement(animationClass, activation, endCallback) {
            var initClass = activation ? initClasses[0] : initClasses[1];
            var activeClass = activation ? activeClasses[0] : activeClasses[1];

            var finishAnimation = function() {
                deregisterElement(element);
                reset(); //reset all classes
                if (!activation)
                    element.removeClass(activeGenericClass); //if not active, remove active class
                reflow();
                timedOut = false;
                if (endCallback)
                    endCallback();
            };

            //stop animation
            registerElement(element);
            reset();
            element.addClass(animationClass);
            element.addClass(initClass);
            element.addClass(activeGenericClass);

            //force a "tick"
            reflow();

            //activate
            element[0].style.transitionDuration = '';
            element.addClass(activeClass);

            element.one(events.join(' '), function() {
                finishAnimation();
            });

            setTimeout(function() {
                if (timedOut) {
                    finishAnimation();
                }
            }, 3000);
        }

    }

};

TornadoFaces.widget.BaseWidget = Class.extend(function() {
    this.constructor = function(conf) {
        this.conf = conf;
        this.elem = $(TornadoFaces.escapeClientId(conf.id));

        this.removeScriptElement(conf.id);
        this.init(conf);
    };

    //used in ajax updates, reloads the widget configuration
    this.refresh = function(conf) {
        return this.constructor(conf);
    };

    // remove from DOM and unregister
    this.destroy = function() {
        this.elem.remove();
        delete TornadoFaces.widgets[this.conf.widgetVar];
    };

    //checks if the given widget is detached
    this.isDetached = function() {
        return document.getElementById(this.id) === null;
    };

    this.removeScriptElement = function(clientId) {
        $(TornadoFaces.escapeClientId(clientId) + '_s').remove();
    };
});

TW = function(widgetName) {
    return TornadoFaces.widgets[widgetName];
};

// FixViewState copied from OmniFaces 2.0
TornadoFaces.FixViewState = function() {
    function r(e) {
        var t = e.getElementsByTagName("update");
        for (var r = 0; r < t.length; r++) {
            if (n.exec(t[r].getAttribute("id"))) {
                return t[r].firstChild.nodeValue
            }
        }
        return null
    }

    function i(e) {
        var n = e.elements;
        for (var r = 0; r < n.length; r++) {
            if (n[r].name == t) {
                return n[r]
            }
        }
        return null
    }

    function s(e, n) {
        var r;
        try {
            r = document.createElement("<input name='" + t + "'>")
        } catch (i) {
            r = document.createElement("input");
            r.setAttribute("name", t)
        }
        r.setAttribute("type", "hidden");
        r.setAttribute("value", n);
        r.setAttribute("autocomplete", "off");
        e.appendChild(r)
    }

    var e = {};
    var t = "javax.faces.ViewState";
    var n = new RegExp("^([\\w]+:)?" + t.replace(/\./g, "\\.") + "(:[0-9]+)?$");
    e.apply = function(e) {
        if (typeof e === "undefined") {
            return
        }
        var t = r(e);
        if (!t) {
            return
        }
        for (var n = 0; n < document.forms.length; n++) {
            var o = document.forms[n];
            var u = i(o);
            if (o.method == "post" && !u) {
                s(o, t)
            } else if (o.method == "get" && u) {
                u.parentNode.removeChild(u)
            }
        }
    };
    return e
}();

$(function() {
    if (typeof jsf !== "undefined") {
        jsf.ajax.addOnEvent(function(e) {
            if (e.status == "success") {
                TornadoFaces.FixViewState.apply(e.responseXML)
            }
        })
    }
    $(document).ajaxComplete(function(e, t) {
        if (typeof t !== "undefined") {
            TornadoFaces.FixViewState.apply(t.responseXML)
        }
    });
});