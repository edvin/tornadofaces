TornadoFaces.declareWidget('Message', function() {
    var widget;

    this.init = function() {
        widget = this;
        var messages = widget.conf.messages;
        var size = messages.length;
        for (var i = 0; i < size; i++)
            widget.show(messages[i]);
    };

    this.isClosable = function() {
        return widget.conf.closable === true;
    };

    this.getTimeout = function() {
        return widget.conf.timeout;
    };

    this.destroyNotification = function(elem) {
        TornadoFaces.animate(elem, false, 'fadeIn', 'fadeOut', function() {
            elem.remove()
        });
    };

    this.show = function() {
        var message;
        if (typeof arguments[0] === 'string') {
            message = {};
            message.summary = arguments[0];

            if (arguments.length > 1)
                message.detail = arguments[1];

            if (arguments.length > 2)
                message.severity = arguments[2];

            if (arguments.length > 3)
                message.image = arguments[3];
        } else {
            message = arguments[0];
        }

        if (typeof message.closable == "undefined")
            message.closable = widget.isClosable();

        var e = $('<div class="label grid-block">' + message.summary + '</div>');

        if (message.detail && message.detail != message.summary)
            e.append(' <span class="detail">' + message.detail + '</span>');

        if (message.image)
            e.prepend('<img src="' + message.image + '" style="padding-right: 10px"/>');

        if (message.closable) {
            var closebutton = $('<a href="#" class="close-button">×</a>');
            closebutton.click(function() {
                widget.destroyNotification(e);
                event.stopPropagation();
                return false;
            });
            e.prepend(closebutton);
        }

        switch (message.severity) {
            case 'INFO':
                e.addClass('primary');
                break;
            case 'FATAL':
            case 'ERROR':
                e.addClass('alert');
                break;
            case 'WARN':
                e.addClass('warning');
                break;
            default:
                e.addClass('success');
        }

        widget.elem.append(e);
        TornadoFaces.animate(e, true, 'fadeIn', 'fadeOut');

        var timeout = widget.getTimeout();
        if (timeout > 0) {
            setTimeout(function() {
                widget.destroyNotification(e);
            }, timeout);
        }
    }
});
