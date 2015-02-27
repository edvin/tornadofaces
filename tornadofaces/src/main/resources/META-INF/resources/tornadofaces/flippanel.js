TornadoFaces.declareWidget('FlipPanel', function() {
    var widget;

    this.init = function() {
        widget = this;
    };


    this.flip = function() {
        widget.elem.toggleClass("flipped");
        if (widget.conf.onFlip)
            widget.conf.onFlip();
    };
    
    this.isFlipped = function() {
        return widget.elem.hasClass("flipped");
    };
    
});