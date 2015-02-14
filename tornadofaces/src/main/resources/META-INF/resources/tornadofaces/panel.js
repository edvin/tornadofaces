TornadoFaces.declareWidget('Panel', function() {
    var widget;

    this.init = function() {
        widget = this;
        widget.currentState = false;

        this.bindEvents();

        if (this.conf.open)
            this.show();
    };

    var setState = function(futureState) {
        widget.currentState = futureState;
        TornadoFaces.animate(widget.elem, futureState, widget.getShowEffect(), widget.getHideEffect());
    };

    var effects = {
        top: {
            show: 'slideInDown',
            hide: 'slideOutUp'
        },
        bottom: {
            show: 'slideInUp',
            hide: 'slideOutBottom'
        },
        left: {
            show: 'slideInRight',
            hide: 'slideOutLeft'
        },
        right: {
            show: 'slideInLeft',
            hide: 'slideOutRight'
        }
    };

    this.bindEvents = function() {
        this.elem.find('.close-button').click(this.hide);
    };

    this.getShowEffect = function() {
        return effects[this.conf.position].show;
    };

    this.getHideEffect = function() {
        return effects[this.conf.position].hide;
    };

    this.show = function() {
        setState(true);
    };

    this.hide = function() {
        setState(false);
    };

    this.toggle = function() {
        setState(!widget.currentState);
    };

});