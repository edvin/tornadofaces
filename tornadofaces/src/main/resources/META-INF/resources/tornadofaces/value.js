TornadoFaces.declareWidget('Value', function() {
    var widget;

    this.init = function() {
        widget = this;

        var basename = widget.conf.name.charAt(0).toUpperCase() + widget.conf.name.slice(1);
        var getter = "get" + basename;
        var setter = "set" + basename;

        window[getter] = widget.get;
        window[setter] = widget.set;
    };

    this.set = function(value) {
        widget.elem.val(value);

        if (widget.conf.behaviors && widget.conf.behaviors.valueChange) {
            var behaviors = widget.conf.behaviors.valueChange;
            for (var i = 0; i < behaviors.length; i++) {
                var b = behaviors[i];
                var props = { 'javax.faces.behavior.event': 'valueChange'};

                if (b.render)
                    props.render = b.render;

                if (b.execute)
                    props.execute = b.execute;

                jsf.ajax.request(widget.elem.attr('id'), null, props);
            }
        }
    };

    this.get = function(value) {
        return widget.elem.val();
    };

});