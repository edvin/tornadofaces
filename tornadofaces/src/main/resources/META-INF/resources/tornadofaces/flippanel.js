TornadoFaces.declareWidget('FlipPanel', function() {
    var widget, front, back, panel;

    this.init = function() {
        widget = this;
        front = widget.elem.find('.front');
        panel = widget.elem.find('.flip-content');
        back = widget.elem.find('.back');
        var heightTarget = widget.isFlipped() ? back : front;
        widget.elem.height(heightTarget.outerHeight());
    };

    this.flip = function() {
        var toshow, tohide, x;

        if (widget.isFlipped()) {
            toshow = front;
            tohide = back;
            x = widget.conf.mode == 'reverse' ? -1 : 1;
        } else {
            toshow = back;
            tohide = front;
            x = 1;
        }

        TweenLite.set(tohide, { rotationY: 0 });
        TweenLite.set(toshow, { rotationY: -90 * x });

        var halfDuration = parseFloat(widget.conf.duration / 2000);

        var tl = new TimelineLite();
        tl.to(tohide, halfDuration / 4, { rotationY: 90 * x, ease: Linear.easeNone});
        tl.append(function() {
            if (widget.isFlipped()) {
                widget.conf.flipped = false;
                widget.elem.removeClass('flipped');
            } else {
                widget.conf.flipped = true;
                widget.elem.addClass('flipped');
            }
        });
        tl.to(toshow, halfDuration, {rotationY: 0, ease: Back.easeOut.config(2)});
        tl.to(widget.elem, halfDuration / 2, {height: toshow.outerHeight()}, "-=" + halfDuration);

        if (widget.conf.behaviors && widget.conf.behaviors.flip) {
            var behaviors = widget.conf.behaviors.flip;
            var render = "", execute = "";
            
            for (var i = 0; i < behaviors.length; i++) {
                var b = behaviors[i];
                if (b.render)
                    render = (render + " " + b.render).trim();
                if (b.execute)
                    execute = (execute + " " + b.execute).trim();
            }
            
            var props = { 
                'javax.faces.behavior.event': 'flip', 
                render: render, 
                execute: execute
            };
            
            props[widget.elem.attr('id') + '_flipped'] = widget.elem.hasClass('flipped');

            jsf.ajax.request(widget.elem.attr('id'), null, props);
        }

        if (widget.conf.onFlip)
            widget.conf.onFlip(widget);
    };
    
    this.isFlipped = function() {
        return widget.conf.flipped;
    };
    
});