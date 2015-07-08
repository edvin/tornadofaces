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
                    item.removeClass('is-hidden');
                    if (doHighlight)
                        item.highlight(query);
                } else {
                    item.addClass('is-hidden');
                }
            }
        }
    };

    this.onExpandRow = function(event) {
        var tr = $(event.target).closest('tr');
        this.expandRow(tr);
        event.preventDefault();
        return false;
    };

    this.expandRow = function(tr) {
        var toggle = tr.find('.row-toggler:first');
        var rowKey = tr.data('rk');

        if (toggle.hasClass('open')) {
            toggle.removeClass('open');
            var expand = tr.next();
            if (expand.hasClass('table-expand-row'))
                expand.remove();
        } else {
            toggle.addClass('open');

            var tableId = widget.elem.attr('id');
            var expandId = widget.elem.find('tbody.expand').attr('id');

            var props = {
                'javax.faces.behavior.event': 'rowToggle',
                execute: tableId,
                render: expandId,
                onevent: function(e) {
                    if (e.status == 'success')
                        widget.elem.find('tbody.expand tr:first').detach().insertAfter(tr);
                }
            };

            props[tableId + '_expand'] = rowKey;

            jsf.ajax.request(tableId, null, props);
        }
    };

    this.onSelectRow = function(event) {
        var tr = $(event.target).closest('tr');
        this.selectRow(tr);
    };

    this.selectRow = function(tr) {
        var doSelect = !tr.hasClass('selected');

        if (doSelect) {
            if ('single' == widget.conf.selectionMode)
                this.unselectAllRows();

            tr.addClass('selected');

            if (widget.conf.behaviors && widget.conf.behaviors.rowSelect) {
                var behaviors = widget.conf.behaviors.rowSelect;
                var render = "", execute = "", onevent, onerror;

                for (var i = 0; i < behaviors.length; i++) {
                    var b = behaviors[i];
                    if (b.render)
                        render = (render + " " + b.render).trim();
                    if (b.execute)
                        execute = (execute + " " + b.execute).trim();
                    if (b.onevent)
                        onevent = b.onevent;
                    if (b.onerror)
                        onerror = b.onerror;
                }

                var props = {
                    'javax.faces.behavior.event': 'rowSelect',
                    render: render,
                    execute: execute
                };

                if (onevent)
                    props.onevent = new Function('data', onevent);

                if (onerror)
                    props.onerror = new Function('data', onerror);

                var id = widget.elem.attr('id');

                props[id + '_selected'] = tr.data('rk');
                props[id + '_selection'] = widget.getSelectedRowKeys().join();

                jsf.ajax.request(id, null, props);
            }
        } else {
            tr.removeClass('selected');
        }
    };

    this.getSelectedRowKeys = function() {
        return items.filter('.selected').map(function(){return $(this).data('rk');}).get();
    };

    this.unselectAllRows = function() {
        items.removeClass('selected');
    };

    this.selectPreviousRow = function() {
        var selected = items.filter('.selected:first');
        var prev = selected.prev('tr');
        this.selectRow(prev.length == 1 ? prev : items.last());
    };

    this.selectNextRow = function() {
        var selected = items.filter('.selected:first');
        var next = selected.next('tr');
        this.selectRow(next.length == 1 ? next : items.first());
    };

    this.bindEvents = function() {
        if (widget.conf.selectionMode) {
            // click to select row
            items.click(this.onSelectRow);

            if (!widget.elem.attr('tabindex'))
                widget.elem.attr('tabindex', 0);

            // keyboard navigation
            widget.elem.on('keydown', function(e) {
                switch (e.keyCode) {
                    case 38:
                        widget.selectPreviousRow();
                        e.preventDefault();
                        return false;
                    case 40:
                        widget.selectNextRow();
                        e.preventDefault();
                        return false;
                }
            });
        }

        // hook row-toggler action
        items.find('.row-toggler').click(this.onExpandRow);
    };
});