TornadoFaces.declareWidget('ActionSheet', function() {
    var widget;

    this.init = function() {
        widget = this;

        var toggleFn = function(event) {
            console.log(widget.elem);
            widget.elem.toggleClass('is-active');
            widget.elem.find('.action-sheet').toggleClass('is-active');
            event.preventDefault();
            return false;
        };

        widget.elem.find('> div > a').click(toggleFn);
    };

    this.show = function() {
        widget.elem.addClass('is-active');
        widget.elem.find('.action-sheet').addClass('is-active');
    };

    this.hide = function() {
        widget.elem.removeClass('is-active');
        widget.elem.find('.action-sheet').removeClass('is-active');
    };

});