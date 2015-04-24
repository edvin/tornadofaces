TornadoFaces.declareWidget('Button', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.bindEvents();
    };
    
    this.initLadda = function() {
        if (!widget.conf.laddaInitialized && widget.conf.spinnerStyle) {
            widget.elem.addClass('ladda-button');
            
            widget.elem.attr('data-style', widget.conf.spinnerStyle);

            if (widget.conf.spinnerColor)
                widget.elem.attr('data-spinner-color', widget.conf.spinnerColor);

            if (widget.conf.spinnerSize)
                widget.elem.attr('data-spinner-size', widget.conf.spinnerSize);

            widget.elem.html('<span class="ladda-label">' + widget.elem.html() + '</span>');

            widget.laddaInitialized = true;
        }

        if (widget.ladda) {
            widget.ladda.remove();
            widget.ladda = null;
        }
    };
    
    this.bindEvents = function() {
        widget.elem.click(function(event) {
            if (widget.elem.attr('disabled')) {
                widget.elem.addClass('wiggle');
                setTimeout(function() { widget.elem.removeClass('wiggle')}, 1000);
                event.preventDefault();
                return false;
            }

            if (widget.conf.beforebegin) {
                var bbResult = eval(widget.conf.beforebegin);
                if (bbResult === false) {
                    event.preventDefault();
                    return false;
                }
            }

            widget.initLadda();
            
            var options = {
                execute: widget.conf.execute,
                render: widget.conf.render
            };

            if (widget.laddaInitialized) {
                widget.ladda = Ladda.create(widget.elem[0]);
                widget.ladda.start();
            }

            options.onevent = function(event) {

                if (event.status == 'begin') {
                    if (widget.conf.onbegin) {
                        var obResult = eval(widget.conf.onbegin);
                        if (obResult === false)
                            return false;
                    }
                }

                if (event.status == 'complete') {
                    if (widget.ladda)
                        widget.ladda.stop();
                    
                    if (widget.conf.oncomplete) {
                        var args = widget.getResponseArgs(event);
                        eval(widget.conf.oncomplete);
                    }

                }

                if (event.status == 'success') {
                    if (widget.conf.onsuccess)
                        eval(widget.conf.onsuccess);
                }
            };

            jsf.ajax.request(widget.elem[0], event, options);
            event.preventDefault();
            return false;
        });
    }
});