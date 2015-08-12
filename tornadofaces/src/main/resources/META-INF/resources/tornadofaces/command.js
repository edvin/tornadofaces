TornadoFaces.declareWidget('Command', function() {
    var widget;

    this.init = function() {
        widget = this;
        window[widget.conf.name] = widget.run;

        if (widget.conf.onload && (!widget.onloadPerformed || widget.conf.repeatOnReload === true)) {
            widget.onloadPerformed = true;
            $(function() {
                widget.run();
            });
        }
    };

    this.run = function(params) {
        if (widget.conf.delay) {
            setTimeout(function() {
                widget.runInternal(params);
            }, widget.conf.delay);
        } else {
            widget.runInternal(params);
        }
    };

    this.runInternal = function(params) {
        if (widget.conf.beforebegin)
            eval(widget.conf.beforebegin);

        var options = {
            execute: widget.conf.execute,
            render: widget.conf.render
        };

        if (params) {
            for (var k in params) {
                if (params.hasOwnProperty(k))
                    options[k] = params[k];
            }
        }

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