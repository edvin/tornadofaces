TornadoFaces.declareWidget('ActionSheet', function() {
    var widget, overlay;

    this.init = function() {
        widget = this;

        var toggleFn = function(event) {
            widget.show();
            event.preventDefault();
            return false;
        };

        widget.elem.find('> div > a').click(toggleFn);
    };

    this.show = function() {
        if (!overlay) {
            overlay = $('<div class="action-sheet-overlay"></div>');
            overlay.click(function() { widget.hide(); });
            $(document.body).append(overlay);
        }

        overlay.addClass('is-active');
        widget.elem.addClass('is-active');
        widget.elem.find('.action-sheet').addClass('is-active');
    };

    this.hide = function() {
        overlay.removeClass('is-active');
        widget.elem.removeClass('is-active');
        widget.elem.find('.action-sheet').removeClass('is-active');
    };

});