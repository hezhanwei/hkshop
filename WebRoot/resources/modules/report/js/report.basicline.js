Highcharts.setOptions({
    global: {
        useUTC: false
    },
    lang : {
        resetZoom : '<s:text name="highcharts.lang.resetZoom"></s:text>'    
    }
});
var chart;
$(document).ready(function() {
	
	chart = new Highcharts.Chart({
		chart: {
	         renderTo: 'container',
	         zoomType: 'x',
	         spacingRight: 20,
	         type: type
	      },

	        title: {
	            text: 'Monthly test',
	            x: -20 //center
	        },
	        subtitle: {
	            text: 'Source: WorldClimate.com',
	            x: -20
	        },
	        xAxis: {
	            categories: categories
	        },
	        yAxis: {
	            title: {
	                text: title
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#FFFFF5'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '°C'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        /*series: [{
	            "name": "Tokyo",
	            "data": [7.1, 6.0, 9.0, 14.0, 18.0, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9,1.0]
	        }, {
	            name: 'New York',
	            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.0]
	        }, {
	            name: 'Berlin',
	            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 3.0]
	        }, {
	            name: 'London',
	            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.0]
	        }]*/
	        series: series
	    
	});
});