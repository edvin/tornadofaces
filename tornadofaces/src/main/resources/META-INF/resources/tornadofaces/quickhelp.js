var Quickhelp = {
    template: '<div class="label-help-dialog"><a class="icon-cross" onclick="Quickhelp.close(this)"></a>{0}</div>',

    show: function (target, text) {
        $(Quickhelp.template.replace('{0}', text)).insertBefore($(target).parent());
    },

    close: function(target) {
        $(target).parent().remove();
    }
};