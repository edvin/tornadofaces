TornadoFaces.declareWidget('Modal', function() {
    var widget;

    this.init = function() {
        widget = this;
        this.modalContent = widget.elem.children('.modal');
        this.bindEvents();

        if (this.conf.open)
            this.show();
    };

    this.bindEvents = function() {
        // Buttons and anchors with class close will hide the dialog
        widget.elem.find('.close').click(widget.hide);

        // Clicks inside the dialog should not bleed outside
        widget.elem.find('aside').click(function() {
            event.stopPropagation();
        });

        // Clicks on the modal overlay closes the dialog
        widget.elem.click(widget.hide);
    };

    this.show = function() {
        TornadoFaces.animate(widget.elem, true, 'fadeIn', 'fadeOut');
        TornadoFaces.animate(widget.modalContent, true, 'fadeIn', 'fadeOut');
    };

    this.hide = function() {
        widget.elem.removeClass('is-active');
        widget.modalContent.removeClass('is-active');
    }
});