TornadoFaces.declareWidget('Command', function() {
    var widget;

    this.init = function() {
        widget = this;
        window[widget.conf.name] = widget.run;

        if (widget.conf.onload && !widget.onloadPerformed) {
            widget.onloadPerformed = true;
            $(function() {
                widget.run();
            });
        }
    };

    this.run = function() {
        var options = {
            execute: widget.conf.execute,
            render: widget.conf.render
        };

        options.onevent = function(event) {
            if (event.status == 'begin') {
                if (widget.conf.onbegin)
                    eval(widget.conf.onbegin);
            }

            if (event.status == 'complete') {
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

        jsf.ajax.request(widget.elem[0], null, options);
    }
});