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
        // Clicks inside the dialog should not bleed outside
        // Buttons and anchors with class close will hide the dialog
        widget.elem.find('aside').click(function(event) {
            if ($(event.target).hasClass('close'))
                widget.hide();

            event.stopPropagation();
        });

        // Clicks on the modal overlay closes the dialog
        if (widget.conf.closeOnOverlayClick !== false) {
            widget.elem.mousedown(function(event) {
                if (event.toElement == widget.elem[0])
                    widget.hide();
            });
        }
    };

    this.show = function() {
        TornadoFaces.animate(widget.elem, true, 'fadeIn', 'fadeOut');
        TornadoFaces.animate(widget.modalContent, true, 'fadeIn', 'fadeOut');

        if (widget.conf.autofocus)
            $(TornadoFaces.escapeClientId(widget.conf.autofocus)).focus();

        if (widget.conf.autoselect)
            $(TornadoFaces.escapeClientId(widget.conf.autoselect)).select();

        TornadoFaces.CurrentModal = this;
    };

    this.hide = function() {
        widget.elem.removeClass('is-active');
        widget.modalContent.removeClass('is-active');

        if (TornadoFaces.CurrentModal === this)
            TornadoFaces.CurrentModal = null;
    }
});