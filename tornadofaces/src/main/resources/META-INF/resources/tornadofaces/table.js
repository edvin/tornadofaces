TornadoFaces.declareWidget('Table', function() {
    var widget, items;

    this.init = function() {
        widget = this;
        items = widget.elem.find('tbody tr').not('.table-expand-row');
        widget.bindEvents();
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
            items.show();
            if (doHighlight)
                items.unhighlight();
        } else {
            var filterFn = widget.getFilterFn();
            for (var idx = 0; idx < items.length; idx++) {
                var item = $(items[idx]);
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

    this.expandRow = function(event) {
        var toggle = $(event.target).closest('.row-toggler');

        var tr = $(event.target).closest('tr');
        var rowKey = tr.data('rk');

        if (toggle.hasClass('open')) {
            console.log('has class open!');
            toggle.removeClass('open');
        } else {
            console.log('does not have class open. adding it.');
            toggle.addClass('open');
            console.log(toggle.hasClass('open'));
        }
    };

    this.selectRow = function(event) {
        if ('single' == widget.conf.selectionMode)
            this.unselectAllRows();

        var tr = $(event.target).closest('tr');
        tr.addClass('selected');

        if (widget.conf.behaviors && widget.conf.behaviors.rowSelect) {
            var behaviors = widget.conf.behaviors.rowSelect;
            var render = "", execute = "";

            for (var i = 0; i < behaviors.length; i++) {
                var b = behaviors[i];
                if (b.render)
                    render = (render + " " + b.render).trim();
                if (b.execute)
                    execute = (execute + " " + b.execute).trim();
            }

            var props = {
                'javax.faces.behavior.event': 'rowSelect',
                render: render,
                execute: execute
            };

            var id = widget.elem.attr('id');

            props[id + '_selected'] = tr.data('rk');
            props[id + '_selection'] = widget.getSelectedRowKeys().join();

            jsf.ajax.request(id, null, props);
        }
    };

    this.getSelectedRowKeys = function() {
        return items.filter('.selected').map(function(){return $(this).data('rk');}).get();
    };

    this.unselectAllRows = function() {
        items.removeClass('selected');
    };

    this.bindEvents = function() {
        if (widget.conf.selectionMode)
            items.click(this.selectRow);

        items.find('.row-toggler').click(this.expandRow);
    };
});