TornadoFaces.declareWidget('Colorpicker', function() {
    var widget;
    var picker;

    this.init = function() {
        widget = this;

        var onChange = function(color) {
            if (widget.conf.behaviors && widget.conf.behaviors.change) {
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
                    'javax.faces.behavior.event': 'change',
                    render: render,
                    execute: execute
                };

                props[widget.elem.attr('id') + '_color'] = color;

                jsf.ajax.request(widget.elem.attr('id'), null, props);
            }

            if (widget.conf.onChange)
                widget.conf.onChange(color);
        };

        widget.conf.change = onChange;
        picker = widget.elem.spectrum(widget.conf.settings);
        if (widget.conf.showTextField === true)
            widget.elem.show();
    };

    this.show = function() {
        this.elem.spectrum('show');
    };

    this.hide = function() {
        this.elem.spectrum('hide');
    };

    this.toggle = function() {
        this.elem.spectrum('toggle');
    };

});