TornadoFaces.declareWidget('Reveal', function() {
    var widget, content, duration;

    this.init = function() {
        widget = this;
        content = widget.elem.find('.reveal-content');
        duration = widget.conf.duration / 1000;

        if (widget.conf.onload && !widget.onloadPerformed) {
            widget.onloadPerformed = true;
            $(function() {
                widget.show();
            });
        }
    };

    this.show = function() {
        TweenLite.set(widget.elem, { opacity: 0 });
        widget.elem.addClass('is-active');

        if (widget.conf.effect) {
            switch (widget.conf.effect) {
                case "fade":
                    TweenLite.to(widget.elem[0], duration, { opacity: 1 });
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
                    TweenLite.to(widget.elem[0], duration, { opacity: 0.2, onComplete: animComplete });
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