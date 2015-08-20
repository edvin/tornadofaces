TornadoFaces.declareWidget('LineChart', function() {
    var widget;

    this.init = function() {
        widget = this;

        widget.render();
    };

    this.render = function() {
        nv.addGraph(function() {
            var chart = nv.models.lineChart()
                    .margin({left: 100})            //Adjust chart margins to give the x-axis some breathing room.
                    .useInteractiveGuideline(true)  //We want nice looking tooltips and a guideline!
                    //.transitionDuration(350)        //how fast do you want the lines to transition?
                    .showLegend(true)               //Show the legend, allowing users to turn on/off line series.
                    .showYAxis(true)                //Show the y-axis
                    .showXAxis(true)                //Show the x-axis
                ;

            chart.xAxis
                .axisLabel(widget.conf.xLabel)
                .tickFormat(d3.format(widget.conf.xTickFormat));

            chart.yAxis
                .axisLabel(widget.conf.yLabel)
                .tickFormat(d3.format(widget.conf.yTickFormat));

            d3.select(TornadoFaces.escapeClientId(widget.conf.id) + ' svg')    //Select the <svg> element you want to render the chart in.
                .datum(widget.conf.datum)         //Populate the <svg> element with chart data...
                .call(chart);          //Finally, render the chart!

            nv.utils.windowResize(function() {
                chart.update()
            });
            return chart;
        });
    }
});