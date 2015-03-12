TornadoFaces.declareWidget('FlipPanel', function() {
    var widget, front, back, panel;

    this.init = function() {
        widget = this;
        front = widget.elem.find('.front');
        panel = widget.elem.find('.flip-content');
        back = widget.elem.find('.back');
        widget.elem.height(front.height());
    };

    this.flip = function() {
        var targetHeight, rotation;

        if (widget.isFlipped()) {
            targetHeight = front.height();
            rotation = 0;
        } else {
            targetHeight = back.height();
            rotation = 180;
        }

        TweenLite.to(widget.elem, (widget.conf.duration / 2000), { height:targetHeight, ease: Back.easeOut });
        TweenLite.to(panel, (widget.conf.duration / 1000), { rotationY:rotation, ease: Back.easeOut });

        widget.conf.flipped = !widget.conf.flipped;
        widget.elem.toggleClass('flipped');

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