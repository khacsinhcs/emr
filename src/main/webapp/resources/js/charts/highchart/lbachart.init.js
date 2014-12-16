var drawChart = function(chart, div) {
	$(div)
			.highcharts(
					{
						chart : {
							type : 'bar'
						},
						title : {
							text : chart.getChartTitle()
						},
						subtitle : {
							text : chart.getChartSubtitle()
						},
						xAxis : {
							categories : chart.buildChartCategories(),
							title : {
								text : null
							}
						},
						yAxis : {
							min : 0,
							title : {
								text : chart.getYAxis(),
								align : 'high'
							},
							labels : {
								overflow : 'justify'
							}
						},
						tooltip : {
							valueSuffix : ''
						},
						plotOptions : {
							bar : {
								dataLabels : {
									enabled : true
								}
							}
						},
						legend : {
							layout : 'vertical',
							align : 'right',
							verticalAlign : 'top',
							x : -40,
							y : 100,
							floating : true,
							borderWidth : 1,
							backgroundColor : (Highcharts.theme
									&& Highcharts.theme.legendBackgroundColor || '#FFFFFF'),
							shadow : true
						},
						credits : {
							enabled : false
						},
						series : [ {
							name : chart.getChartValueName(),
							data : chart.buildChartData()
						} ]
					});
}