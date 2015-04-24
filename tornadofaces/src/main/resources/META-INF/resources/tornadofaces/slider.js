TornadoFaces.declareWidget('Slider', function() {
    var widget, lowerTarget, upperTarget, headerElem, minLabel, maxLabel, valueLabel, lastVal;

    function update() {
        var lowerVal = widget.lowerElem.val();
        var upperVal = widget.upperElem.val();

        if (lowerTarget)
            lowerTarget.val(lowerVal);
        
        if (upperTarget)
            upperTarget.val(upperVal);

        if (upperVal) {
            widget.formatLabel('value', valueLabel, [lowerVal, upperVal]);
        } else {
            widget.formatLabel('value', valueLabel, lowerVal);
        }
    }

    this.formatLabel = function(type, label, value) {
        if (widget.conf.labelFormatter) {
            widget.conf.labelFormatter(type, label, value, widget);
        } else {
            switch (type) {
                case 'min':
                case 'max':
                    label.html(value);
                    break;

                case 'value':
                    if ($.isArray(value)) {
                        label.html(value[0] + ' - ' + value[1]);
                    } else {
                        label.html(value);
                    }
                    break;
            }
        }
    };

    this.init = function() {
        widget = this;

        widget.sliderElem = widget.elem.find('.slider-content');
        headerElem = widget.elem.find('.slider-header');
        valueLabel = headerElem.find('h4');
        minLabel = headerElem.find('.slider-min');
        maxLabel = headerElem.find('.slider-max');

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

        widget.slider = widget.sliderElem.noUiSlider(settings);

        var hasOnSlide = widget.conf.onSlide != undefined;

        $(function() {
            widget.formatLabel('min', minLabel, widget.conf.settings.range.min);
            widget.formatLabel('max', maxLabel, widget.conf.settings.range.max);
            update();
        });

        widget.sliderElem.on('slide', function() {
            var val = widget.sliderElem.val();

            if (val == lastVal)
                return;

            lastVal = val;

            if ($.isArray(val)) {
                widget.lowerElem.val(val[0]);

                if (val.length > 1)
                    widget.upperElem.val(val[1]);
            } else {
                widget.lowerElem.val(val);
            }

            update();
            
            if (hasOnSlide)
                widget.conf.onSlide(widget.sliderElem, val);
            
            if (widget.conf.behaviors && widget.conf.behaviors.change) {
                var behaviors = widget.conf.behaviors.change;
                for (var i = 0; i < behaviors.length; i++) {
                    var b = behaviors[i];
                    var props = { 'javax.faces.behavior.event': 'change'};
                    
                    if (b.render)
                        props.render = b.render;
                    
                    if (b.execute)
                        props.execute = b.execute;

                    if (b.delay)
                        props.delay = b.delay;

                    jsf.ajax.request(widget.elem.attr('id'), null, props);
                }
            }
        });
    };
});