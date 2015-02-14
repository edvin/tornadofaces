TornadoFaces.declareWidget('Datepicker', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.picker = widget.elem.pickadate(widget.conf.settings).pickadate('picker');
        this.bindEvents();
    };

    this.bindEvents = function() {
        widget.elem.click(function(event) {
            widget.picker.open();
            event.preventDefault();
            return false;
        });
        widget.picker.on('set', function() {
            this.close(true);
        });
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