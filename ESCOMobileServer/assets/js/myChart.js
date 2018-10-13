var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
	type: 'line',
	data: {
		//labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"],
		labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"],
		datasets: [{
			//data: [27, 34, 21, 38, 16, 42, 29, 32, 23, 37, 40, 25],
			data: [27, 34, 21, 38, 16, 42],
			lineTension: 0,
			backgroundColor: 'transparent',
			borderColor: '#007bff',
			borderWidth: 4,
			pointBackgroundColor: '#007bff'
		}]
	},
	options: {
		scales: {
			yAxes: [{
				ticks: {
					beginAtZero: false
				}
			}]
		},
		legend: {
			display: false,
		}
	}
});