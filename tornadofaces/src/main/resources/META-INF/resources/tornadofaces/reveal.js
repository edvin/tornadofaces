TornadoFaces.declareWidget('Reveal', function() {
    var widget, elem, duration;

    this.init = function() {
        widget = this;
        elem = widget.elem[0];
        duration = widget.conf.duration / 1000;

        if (widget.conf.onload && !widget.onloadPerformed) {
            widget.onloadPerformed = true;
            $(function() {
                widget.show();
            });
        }
    };

    this.show = function() {
        var animComplete = function() {
            widget.elem.addClass('is-active');
        };

        if (widget.conf.effect) {
            if (widget.conf.mode == 'display') {
                animComplete();
                TweenLite.set(elem, { autoAlpha: 0 });
            }

            switch (widget.conf.effect) {
                case "fade":
                    TweenLite.to(elem, duration, { autoAlpha: 1, onComplete: animComplete });
                    break;
            }
        } else {
            animComplete();
        }
    };

    this.hide = function() {
        var animComplete = function() {
            widget.elem.removeClass('is-active');
        };

        if (widget.conf.effect) {
            switch (widget.conf.effect) {
                case "fade":
                    TweenMax.to(elem, duration, {autoAlpha:0 , onComplete: animComplete});
                    break;
            }
        } else {
            animComplete();
        }
    };

    this.toggle = function() {
        if (widget.elem.hasClass('is-active'))
            widget.hide();
        else
            widget.show();
    };
});