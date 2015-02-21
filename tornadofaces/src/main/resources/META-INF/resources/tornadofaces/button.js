TornadoFaces.declareWidget('Button', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.bindEvents();
    };
    
    this.bindEvents = function() {
        widget.elem.click(function(event) {
            widget.elem.removeClass('wiggle');

            if (widget.elem.attr('disabled')) {
                widget.elem.addClass('wiggle');
                event.preventDefault();
                return false;
            }
            
            if (widget.ladda)
                widget.ladda.remove();

            var options = {
                execute: widget.conf.execute,
                render: widget.conf.render
            };

            if (widget.elem.data('style'))
                widget.ladda = Ladda.create(widget.elem[0]);

            if (widget.ladda)
                widget.ladda.start();

            options.onevent = function(event) {
                if (event.status == 'begin') {
                    if (widget.conf.onbegin)
                        widget.conf.onbegin();
                }

                if (event.status == 'complete') {
                    if (widget.ladda)
                        widget.ladda.stop();
                    
                    if (widget.conf.oncomplete)
                        widget.conf.oncomplete();
                }

                if (event.status == 'success') {
                    if (widget.conf.onsuccess)
                        widget.conf.onsuccess();
                }
            };

            jsf.ajax.request(widget.elem[0], event, options);
        });
    }
});