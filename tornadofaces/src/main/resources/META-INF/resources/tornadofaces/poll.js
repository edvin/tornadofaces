TornadoFaces.declareWidget('Poll', function() {
    var widget, stopRequested = false, running = false;

    this.init = function() {
        widget = this;
        window[widget.conf.name] = widget.run;

        if (widget.conf.onload && !widget.onloadPerformed) {
            widget.onloadPerformed = true;
            $(function() {
                widget.start();
            });
        }
    };

    this.start = function() {
        if (running)
            return false;

        running = true;
        stopRequested = false;
        var first = true;

        var runner = function() {
            if (stopRequested) {
                running = false;
            } else {
                if (first && widget.conf.delay) {
                    setTimeout(runner, parseInt(widget.conf.delay));
                } else {
                    widget.run();
                    setTimeout(runner, parseInt(widget.conf.interval));
                }
                first = false;
            }
        };

        runner();
        return true;
    };

    this.stop = function() {
        if (running) {
            stopRequested = true;
            return true;
        } else {
            return false;
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