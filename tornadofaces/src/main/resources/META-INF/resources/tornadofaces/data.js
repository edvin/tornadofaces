TornadoFaces.declareWidget('Data', function() {
    var widget;

    this.init = function() {
        widget = this;
    };

    this.isDataAvailable = function() {
        return window[widget.conf.var] !== undefined;
    };

    this.val = function() {
        return eval(widget.conf.var);
    };

    this.load = function() {
        var id = widget.elem.attr('id');
        jsf.ajax.request(widget.elem[0], null, { execute: id, render: id });
    }
});