TornadoFaces.declareWidget('LineChart', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.render();
    };

    this.render = function() {
        nv.addGraph(function() {
            var chart = nv.models.lineChart()
                    .margin({left: 100})
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

            d3.select(widget.elem.find('svg')[0])
                .datum(widget.conf.datum)
                .call(chart);

            nv.utils.windowResize(chart.update);
            return chart;
        });
    }
});