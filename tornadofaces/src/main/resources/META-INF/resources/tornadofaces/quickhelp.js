var Quickhelp = {
    template: '<div class="label-help-dialog"><a class="icon-cross" onclick="Quickhelp.close(this)"></a>{0}</div>',

    show: function (target, text) {
        var t = $(target).parent().parent().find(".label-help-dialog");

        if(t.length > 0){
            t[0].remove();
        }else{
            $(Quickhelp.template.replace('{0}', text)).insertBefore($(target).parent());
        }
    },

    close: function(target) {
        $(target).parent().remove();
    }
};