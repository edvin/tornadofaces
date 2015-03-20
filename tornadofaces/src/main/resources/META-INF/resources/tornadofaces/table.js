TornadoFaces.declareWidget('Table', function() {
    var widget;

    function isFunction(functionToCheck) {
        var getType = {};
        return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
    }

    this.init = function() {
        widget = this;

        widget.items = widget.elem.find('tr').not('th');
    };

    this.getFilterFn = function() {
        var filterFn = widget.conf.filterFn;
        if (!filterFn) {
            filterFn = function(item, query) {
                return item.text().toUpperCase().indexOf(query.toUpperCase()) > -1;
            };
            widget.conf.filterFn = filterFn;
        }
        return filterFn;
    };

    this.setFilterFn = function(filterFn) {
        widget.conf.filterFn = filterFn;
    };

    this.filter = function(query) {
        var doHighlight = widget.conf.highlightFilter;

        if (!query || query.length == 0 || query === '') {
            widget.items.show();
            if (doHighlight)
                widget.items.unhighlight();
        } else {
            var filterFn = widget.getFilterFn();
            for (var idx = 0; idx < widget.items.length; idx++) {
                var item = $(widget.items[idx]);
                if (doHighlight)
                    item.unhighlight();

                if (filterFn(item, query, idx)) {
                    item.show();
                    if (doHighlight)
                        item.highlight(query);
                } else {
                    item.hide();
                }
            }
        }
    };

});