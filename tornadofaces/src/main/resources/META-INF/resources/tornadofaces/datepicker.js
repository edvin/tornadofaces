TornadoFaces.declareWidget('Datepicker', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.conf.settings.field = widget.elem[0];
        widget.picker = new Pikaday(widget.conf.settings);
    };


    this.open = function() {
        this.picker.open();
    };

    this.close = function(keepFocus) {
        this.picker.close(keepFocus);
    };

    this.stop = function() {
        this.picker.stop();
    };

    this.start = function() {
        this.picker.start();
    };

    this.render = function(renderWithRoot) {
        this.picker.render(renderWithRoot);
    };

    this.clear = function() {
        this.picker.clear();
    };

    /**
     * Get the properties, objects, and states of the picker.
     *
     * The thing argument is an optional string and can be one of the following:
     *
     * value (default)
     * select
     * highlight
     * view
     * min
     * max
     * open
     * start
     * id
     * disable
     * enable
     * @param thing
     */
    this.get = function(thing) {
        this.picker.get(thing);
    };

});