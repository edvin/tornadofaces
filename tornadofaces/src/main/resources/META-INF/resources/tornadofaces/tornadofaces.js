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

            if (index && index >= 0)
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

    this.getResponseArgs = function(event) {
        if (event.responseText != null) {
            var xml = $.parseXML(event.responseText);
            var extension = $(xml).find('#tornadofaces');
            return extension.length > 0 ? JSON.parse(extension.text()) : null;
        }
    };

    //used in ajax updates, reloads the widget configuration
    this.refresh = function(conf) {
        return this.constructor(conf);
    };

    // remove from DOM and unregister
    this.destroy = function() {
        if (this.cleanup)
            this.cleanup();
        
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
    
    this.hasBehaviors = function() {
        return this.conf.behaviors;
    }
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

// spin.js
(function(t,e){"object"==typeof exports?module.exports=e():"function"==typeof define&&define.amd?define(e):t.Spinner=e()})(this,function(){"use strict";function t(t,e){var i,n=document.createElement(t||"div");for(i in e)n[i]=e[i];return n}function e(t){for(var e=1,i=arguments.length;i>e;e++)t.appendChild(arguments[e]);return t}function i(t,e,i,n){var r=["opacity",e,~~(100*t),i,n].join("-"),o=.01+100*(i/n),a=Math.max(1-(1-t)/e*(100-o),t),s=u.substring(0,u.indexOf("Animation")).toLowerCase(),l=s&&"-"+s+"-"||"";return c[r]||(p.insertRule("@"+l+"keyframes "+r+"{"+"0%{opacity:"+a+"}"+o+"%{opacity:"+t+"}"+(o+.01)+"%{opacity:1}"+(o+e)%100+"%{opacity:"+t+"}"+"100%{opacity:"+a+"}"+"}",p.cssRules.length),c[r]=1),r}function n(t,e){var i,n,r=t.style;for(e=e.charAt(0).toUpperCase()+e.slice(1),n=0;d.length>n;n++)if(i=d[n]+e,void 0!==r[i])return i;return void 0!==r[e]?e:void 0}function r(t,e){for(var i in e)t.style[n(t,i)||i]=e[i];return t}function o(t){for(var e=1;arguments.length>e;e++){var i=arguments[e];for(var n in i)void 0===t[n]&&(t[n]=i[n])}return t}function a(t,e){return"string"==typeof t?t:t[e%t.length]}function s(t){this.opts=o(t||{},s.defaults,f)}function l(){function i(e,i){return t("<"+e+' xmlns="urn:schemas-microsoft.com:vml" class="spin-vml">',i)}p.addRule(".spin-vml","behavior:url(#default#VML)"),s.prototype.lines=function(t,n){function o(){return r(i("group",{coordsize:d+" "+d,coordorigin:-u+" "+-u}),{width:d,height:d})}function s(t,s,l){e(p,e(r(o(),{rotation:360/n.lines*t+"deg",left:~~s}),e(r(i("roundrect",{arcsize:n.corners}),{width:u,height:n.width,left:n.radius,top:-n.width>>1,filter:l}),i("fill",{color:a(n.color,t),opacity:n.opacity}),i("stroke",{opacity:0}))))}var l,u=n.length+n.width,d=2*u,c=2*-(n.width+n.length)+"px",p=r(o(),{position:"absolute",top:c,left:c});if(n.shadow)for(l=1;n.lines>=l;l++)s(l,-2,"progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");for(l=1;n.lines>=l;l++)s(l);return e(t,p)},s.prototype.opacity=function(t,e,i,n){var r=t.firstChild;n=n.shadow&&n.lines||0,r&&r.childNodes.length>e+n&&(r=r.childNodes[e+n],r=r&&r.firstChild,r=r&&r.firstChild,r&&(r.opacity=i))}}var u,d=["webkit","Moz","ms","O"],c={},p=function(){var i=t("style",{type:"text/css"});return e(document.getElementsByTagName("head")[0],i),i.sheet||i.styleSheet}(),f={lines:12,length:7,width:5,radius:10,rotate:0,corners:1,color:"#000",direction:1,speed:1,trail:100,opacity:.25,fps:20,zIndex:2e9,className:"spinner",top:"50%",left:"50%",position:"absolute"};s.defaults={},o(s.prototype,{spin:function(e){this.stop();var i=this,n=i.opts,o=i.el=r(t(0,{className:n.className}),{position:n.position,width:0,zIndex:n.zIndex});if(n.radius+n.length+n.width,r(o,{left:n.left,top:n.top}),e&&e.insertBefore(o,e.firstChild||null),o.setAttribute("role","progressbar"),i.lines(o,i.opts),!u){var a,s=0,l=(n.lines-1)*(1-n.direction)/2,d=n.fps,c=d/n.speed,p=(1-n.opacity)/(c*n.trail/100),f=c/n.lines;(function h(){s++;for(var t=0;n.lines>t;t++)a=Math.max(1-(s+(n.lines-t)*f)%c*p,n.opacity),i.opacity(o,t*n.direction+l,a,n);i.timeout=i.el&&setTimeout(h,~~(1e3/d))})()}return i},stop:function(){var t=this.el;return t&&(clearTimeout(this.timeout),t.parentNode&&t.parentNode.removeChild(t),this.el=void 0),this},lines:function(n,o){function s(e,i){return r(t(),{position:"absolute",width:o.length+o.width+"px",height:o.width+"px",background:e,boxShadow:i,transformOrigin:"left",transform:"rotate("+~~(360/o.lines*d+o.rotate)+"deg) translate("+o.radius+"px"+",0)",borderRadius:(o.corners*o.width>>1)+"px"})}for(var l,d=0,c=(o.lines-1)*(1-o.direction)/2;o.lines>d;d++)l=r(t(),{position:"absolute",top:1+~(o.width/2)+"px",transform:o.hwaccel?"translate3d(0,0,0)":"",opacity:o.opacity,animation:u&&i(o.opacity,o.trail,c+d*o.direction,o.lines)+" "+1/o.speed+"s linear infinite"}),o.shadow&&e(l,r(s("#000","0 0 4px #000"),{top:"2px"})),e(n,e(l,s(a(o.color,d),"0 0 1px rgba(0,0,0,.1)")));return n},opacity:function(t,e,i){t.childNodes.length>e&&(t.childNodes[e].style.opacity=i)}});var h=r(t("group"),{behavior:"url(#default#VML)"});return!n(h,"transform")&&h.adj?l():u=n(h,"animation"),s});

/*!
 * Ladda 0.9.7 (2015-01-17, 11:24)
 * http://lab.hakim.se/ladda
 * MIT licensed
 *
 * Copyright (C) 2015 Hakim El Hattab, http://hakim.se
 */
(function(t,e){"object"==typeof exports?module.exports=e(require("spin.js")):"function"==typeof define&&define.amd?define(["spin"],e):t.Ladda=e(t.Spinner)})(this,function(t){"use strict";function e(t){if(t===void 0)return console.warn("Ladda button target must be defined."),void 0;t.querySelector(".ladda-label")||(t.innerHTML='<span class="ladda-label">'+t.innerHTML+"</span>");var e,n=t.querySelector(".ladda-spinner");n||(n=document.createElement("span"),n.className="ladda-spinner"),t.appendChild(n);var r,a={start:function(){return e||(e=o(t)),t.setAttribute("disabled",""),t.setAttribute("data-loading",""),clearTimeout(r),e.spin(n),this.setProgress(0),this},startAfter:function(t){return clearTimeout(r),r=setTimeout(function(){a.start()},t),this},stop:function(){return t.removeAttribute("disabled"),t.removeAttribute("data-loading"),clearTimeout(r),e&&(r=setTimeout(function(){e.stop()},1e3)),this},toggle:function(){return this.isLoading()?this.stop():this.start(),this},setProgress:function(e){e=Math.max(Math.min(e,1),0);var n=t.querySelector(".ladda-progress");0===e&&n&&n.parentNode?n.parentNode.removeChild(n):(n||(n=document.createElement("div"),n.className="ladda-progress",t.appendChild(n)),n.style.width=(e||0)*t.offsetWidth+"px")},enable:function(){return this.stop(),this},disable:function(){return this.stop(),t.setAttribute("disabled",""),this},isLoading:function(){return t.hasAttribute("data-loading")},remove:function(){clearTimeout(r),t.removeAttribute("disabled",""),t.removeAttribute("data-loading",""),e&&(e.stop(),e=null);for(var n=0,i=u.length;i>n;n++)if(a===u[n]){u.splice(n,1);break}}};return u.push(a),a}function n(t,e){for(;t.parentNode&&t.tagName!==e;)t=t.parentNode;return e===t.tagName?t:void 0}function r(t){for(var e=["input","textarea","select"],n=[],r=0;e.length>r;r++)for(var a=t.getElementsByTagName(e[r]),i=0;a.length>i;i++)a[i].hasAttribute("required")&&n.push(a[i]);return n}function a(t,a){a=a||{};var i=[];"string"==typeof t?i=s(document.querySelectorAll(t)):"object"==typeof t&&"string"==typeof t.nodeName&&(i=[t]);for(var o=0,u=i.length;u>o;o++)(function(){var t=i[o];if("function"==typeof t.addEventListener){var s=e(t),u=-1;t.addEventListener("click",function(){var e=!0,i=n(t,"FORM");if(i!==void 0)for(var o=r(i),d=0;o.length>d;d++)""===o[d].value.replace(/^\s+|\s+$/g,"")&&(e=!1),"checkbox"!==o[d].type&&"radio"!==o[d].type||o[d].checked||(e=!1);e&&(s.startAfter(1),"number"==typeof a.timeout&&(clearTimeout(u),u=setTimeout(s.stop,a.timeout)),"function"==typeof a.callback&&a.callback.apply(null,[s]))},!1)}})()}function i(){for(var t=0,e=u.length;e>t;t++)u[t].stop()}function o(e){var n,r=e.offsetHeight;0===r&&(r=parseFloat(window.getComputedStyle(e).height)),r>32&&(r*=.8),e.hasAttribute("data-spinner-size")&&(r=parseInt(e.getAttribute("data-spinner-size"),10)),e.hasAttribute("data-spinner-color")&&(n=e.getAttribute("data-spinner-color"));var a=12,i=.2*r,o=.6*i,s=7>i?2:3;return new t({color:n||"#fff",lines:a,radius:i,length:o,width:s,zIndex:"auto",top:"auto",left:"auto",className:""})}function s(t){for(var e=[],n=0;t.length>n;n++)e.push(t[n]);return e}var u=[];return{bind:a,create:e,stopAll:i}});