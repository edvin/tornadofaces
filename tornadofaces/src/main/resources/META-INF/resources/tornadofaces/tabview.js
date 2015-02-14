TornadoFaces.declareWidget('TabView', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.items = widget.elem.children('.tab-item');
        widget.contentElem = widget.elem.next();

        if (widget.isDynamic() && widget.isCache())
            widget.cachedContent = widget.getActiveIndexes();

        widget.bindEvents();
    };

    this.getActiveIndexes = function() {
        var activeIndexes = [];
        widget.items.filter('.is-active').each(function() {
            activeIndexes.push($(this).index());
        });
        return activeIndexes;
    };

    this.isDynamic = function() {
        return widget.conf.dynamic === true;
    };

    this.isCache = function() {
        return widget.conf.cache === true;
    };

    this.isContentCached = function(index) {
        return widget.cachedContent.indexOf(index) > -1;
    };

    this.setActiveState = function(item) {
        // Remove other active contents
        widget.items.filter('.is-active').removeClass('is-active');
        widget.contentElem.children('.is-active').removeClass('is-active');

        // Activate tab
        item.addClass('is-active');

        // Activate content, items has form as first child, tab-contents are alone in their container
        var itemIndex = item.index();
        $(widget.contentElem.children()[itemIndex]).addClass('is-active');

        // Call change listener
        if (widget.conf.onItemChange)
            widget.conf.onItemChange(item);
    };

    this.bindEvents = function() {
        widget.items.click(function() {
            var item = $(this);
            var itemIndex = item.index();
            widget.select(itemIndex);
        });
    };

    this.select = function(index) {
        var item = $(widget.items[index]);

        if (item.hasClass('is-active'))
            return;

        var itemIndex = item.index();

        if (widget.isDynamic()) {
            var content = item.children('.tab-contents:first');

            // Cached content, just show
            if (widget.isCache() && widget.isContentCached(itemIndex)) {
                widget.setActiveState(item);
            } else {
                // We need to load content
                var tabViewId = widget.elem.attr('id');
                var stateholderId = tabViewId + ':stateholder';
                var contentId = $(widget.contentElem.children()[itemIndex]).attr('id');

                var props = {
                    execute: tabViewId,
                    render: contentId
                };

                props[widget.elem.attr('id') + '_active'] = itemIndex;

                props.onevent = function(event) {
                    if (event.status == 'complete') {
                        if (widget.isCache())
                            widget.cachedContent.push(itemIndex);
                    }

                    if (event.status == 'success')
                        widget.setActiveState(item);
                };

                jsf.ajax.request(stateholderId, null, props);
            }
        } else {
            // Element already rendered with pageload, just show
            widget.setActiveState(item);
        }
    };
});