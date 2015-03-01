TornadoFaces.declareWidget('Switch', function() {
    var widget;
    var checkbox;
    
    this.init = function() {
        widget = this;
        checkbox = widget.elem.find('input[type=checkbox]');
        this.bindEvents();
    };

    var doChange = function() {
        if (widget.conf.behaviors && widget.conf.behaviors.change) {
            var behaviors = widget.conf.behaviors.change;
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

            jsf.ajax.request(widget.elem.attr('id'), null, props);
        }

        if (widget.conf.onChange)
            widget.conf.onChange(widget);
    };
    
    this.getCheckbox = function() {
        return checkbox;
    };
    
    this.bindEvents = function() {
        checkbox.change(doChange);
    };
    
    this.toggle = function() {
        checkbox.prop('checked', !checkbox.prop('checked'));
        doChange();
    };
    
    this.isChecked = function() {
        return checkbox.prop('checked');
    };
    
});