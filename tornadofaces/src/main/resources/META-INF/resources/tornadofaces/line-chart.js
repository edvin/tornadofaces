TornadoFaces.declareWidget('LineChart', function() {
    var widget, chart;

    this.init = function() {
        widget = this;
        widget.render();
    };

    this.render = function() {
        nv.addGraph(function () {
            chart = nv.models.lineChart()
                .useInteractiveGuideline(true)
                .duration(350)
                .showLegend(true)
                .showYAxis(true)
                .showXAxis(true)
            ;

            chart.xAxis
                .axisLabel(widget.conf.xLabel)
                .tickFormat(widget.conf.xTickFormat);

            chart.yAxis
                .axisLabel(widget.conf.yLabel)
                .tickFormat(widget.conf.yTickFormat);

            if (widget.conf.beforeConfig)
                widget.conf.beforeConfig(widget, chart);

            widget.rerender();

            nv.utils.windowResize(chart.update);

            return chart;
        });
    };


    this.getChart = function() {

        return chart;
    };

    this.rerender = function() {
        d3.select(widget.elem.find('svg')[0])
            .datum(widget.conf.datum)
            .call(chart);
    };
});