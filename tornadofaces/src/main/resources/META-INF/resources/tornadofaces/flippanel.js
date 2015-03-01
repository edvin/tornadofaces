TornadoFaces.declareWidget('FlipPanel', function() {
    var widget;

    this.init = function() {
        widget = this;
    };


    this.flip = function() {
        widget.elem.toggleClass("flipped");

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
        return widget.elem.hasClass("flipped");
    };
    
});