TornadoFaces.declareWidget('Slider', function() {
    var widget, lowerTarget, upperTarget;

    function syncTargets() {
        if (lowerTarget)
            lowerTarget.val(widget.lowerElem.val());
        
        if (upperTarget)
            upperTarget.val(widget.upperElem.val());
    }

    this.init = function() {
        widget = this;

        this.lowerElem = this.elem.find(TornadoFaces.escapeClientId(this.conf.lowerId));
        this.upperElem = this.elem.find(TornadoFaces.escapeClientId(this.conf.upperId));

        if (this.conf.lowerTarget) {
            lowerTarget = $(TornadoFaces.escapeClientId(this.conf.lowerTarget));
            lowerTarget.on('keyup', function() {
                var v = lowerTarget.val();
                widget.lowerElem.val(v);
                widget.slider.val([v, null]);
            });
        }
        
        if (this.conf.upperTarget) {
            upperTarget = $(TornadoFaces.escapeClientId(this.conf.upperTarget));
            upperTarget.on('keyup', function() {
                var v = upperTarget.val();
                widget.upperElem.val(v);
                widget.slider.val([null, v]);
            });
        }

        var settings = this.conf.settings;

        if (settings.format == 'int') {
            settings.format = {
                to: function(value) { return parseInt(value) },
                from: function(value) { return parseInt(value) }
            }
        }

        widget.slider = widget.elem.noUiSlider(settings);

        var hasOnSlide = widget.conf.onSlide != undefined;

        syncTargets();
        
        widget.elem.on('slide', function() {
            var val = widget.elem.val();

            if ($.isArray(val)) {
                widget.lowerElem.val(val[0]);

                if (val.length > 1)
                    widget.upperElem.val(val[1]);
            } else {
                widget.lowerElem.val(val);
            }

            syncTargets();
            
            if (hasOnSlide)
                widget.conf.onSlide(widget.elem, val);
            
            if (widget.conf.behaviors && widget.conf.behaviors.change) {
                var behaviors = widget.conf.behaviors.change;
                for (var i = 0; i < behaviors.length; i++) {
                    var b = behaviors[i];
                    var props = { 'javax.faces.behavior.event': 'change'};
                    
                    if (b.render)
                        props.render = b.render;
                    
                    if (b.execute)
                        props.execute = b.execute;
                    
                    jsf.ajax.request(widget.elem.attr('id'), null, props);
                }
            }
        });
    };
});