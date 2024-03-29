TornadoFaces.declareWidget('Accordion', function() {
    var widget;
    var duration = 145;

    this.init = function() {
        widget = this;

        widget.items = widget.elem.children('.accordion-item');

        if (widget.isDynamic() && widget.isCache())
            widget.cachedContent = widget.getActiveIndexes();

        widget.bindEvents();
    };

    this.getActiveIndexes = function(){
        var activeIndexes = [];
        widget.items.filter('.active').each(function() {
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

    this.isCollapsible = function() {
        return widget.conf.collapsible === true;
    };

    this.isContentCached = function(index) {
        return widget.cachedContent.indexOf(index) > -1;
    };

    this.isMulti = function() {
        return widget.conf.multi === true;
    };

    this.hasTabChangeBehavior = function() {
        return widget.conf.behaviors && widget.conf.behaviors.tabChange;
    };

    this.setActiveState = function(item) {
        // Remove other active contents
        if(!widget.isMulti()) {
            widget.items.filter('.is-active').each(function() {
                var _item = $(this);
                _item.children('.accordion-content').slideUp({ duration: duration, complete: function() {
                        _item.removeClass('is-active');
                    }
                });
            });
        }

        // Animate and show this content
        item.children('.accordion-content').slideDown({duration: duration, complete: function() {
            item.addClass('is-active');
        }});

        // Call change listener
        if (widget.conf.onItemChange)
            widget.conf.onItemChange(item, widget);
    };

    this.select = function(itemIndex) {
        if (isNaN(itemIndex)) {
            widget.items.each(function(index, item) {
                if ($(item).find('.accordion-content').attr('id') === itemIndex) {
                    itemIndex = index;
                }
            });
        }
        var item = $(widget.items[itemIndex]);
        var activation = !item.hasClass('is-active');

        if (activation) {
            if (widget.isDynamic()) {
                var content = item.children('.accordion-content:first');
                var contentId = content.attr('id');

                // Cached content, just show
                if (!widget.hasBehaviors() && widget.isCache() && widget.isContentCached(itemIndex)) {
                    widget.setActiveState(item);
                } else {
                    // We need to load content
                    var spinner = $('<div class="spinner float-right"></div>');
                    spinner.appendTo(item.find('.accordion-title'));

                    var accId = widget.elem.attr('id');

                    var props = {
                        execute: accId,
                        render: contentId
                    };

                    if (widget.hasTabChangeBehavior()) {
                        var behaviors = widget.conf.behaviors.tabChange;

                        for (var i = 0; i < behaviors.length; i++) {
                            var b = behaviors[i];
                            if (b.render)
                                props.render = (props.render + " " + b.render).trim();
                            if (b.execute)
                                props.execute = (props.execute + " " + b.execute).trim();
                        }

                        props['javax.faces.behavior.event'] = 'tabChange';
                    }

                    var activateBehaviorName = 'activate_' + itemIndex;

                    if (widget.conf.behaviors && widget.conf.behaviors.hasOwnProperty(activateBehaviorName)) {
                        var activateBehaviors = widget.conf.behaviors[activateBehaviorName];
                        for (var i = 0; i < activateBehaviors.length; i++) {
                            var b = activateBehaviors[i];
                            if (b.render)
                                props.render = (props.render + " " + b.render).trim();
                            if (b.execute)
                                props.execute = (props.execute + " " + b.execute).trim();
                        }
                        props['javax.faces.behavior.event'] = 'tabChange';
                    }

                    props[accId + '_active'] = itemIndex;

                    props.onevent = function(event) {
                        if (event.status == 'complete') {
                            if (widget.isCache())
                                widget.cachedContent.push(itemIndex);
                        }

                        if (event.status == 'success') {
                            widget.setActiveState(item);
                            spinner.remove();
                        }
                    };

                    jsf.ajax.request(accId, null, props);
                }
            } else {
                // Element already rendered with pageload, just show
                widget.setActiveState(item);
            }
        } else {
            item.find('.accordion-content').slideUp({duration: duration, complete: function() {
                item.removeClass('is-active');
            }});
        }

        return activation;
    };

    this.bindEvents = function() {
        widget.items.children('.accordion-title').click(function(event) {
            var item = $(this).parent();
            var itemIndex = item.index();
            widget.select(itemIndex);
            event.preventDefault();
            return false;
        });
    }
});