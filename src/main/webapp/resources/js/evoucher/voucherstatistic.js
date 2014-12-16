/**
 * 
 */
var gridbordercolor = "#eee";
var soldOperation = {
	url : host + '/statistic/sold',
	execute : function(parameter) {
		var operation = this
		jQuery.post(this.url, parameter, function(data) {
			if (data.code === "SUCCESS") {
				vouchersService.onSoldSuccess(data.datas)
			} else {
				vouchersService.onSoldFail()
			}
		})
	},
	callback : vouchersService
}

var revokedOperation = {
	url : host + '/statistic/revoked',
	execute : function(parameter) {
		var operation = this
		jQuery.post(this.url, parameter, function(data) {
			if (data.code === "SUCCESS") {
				vouchersService.onRevokedSuccess(data.datas)
			} else {
				vouchersService.onRevokedFail()
			}
		})
	},
	callback : vouchersService
}

var vouchersService = {
	getSold : function(value) {
		soldOperation.execute(value);
	},
	getRevoked : function(value) {
		revokedOperation.execute(value);
	},
	onRevokedSuccess : function(data) {
		var revokedData = []
		for (key in data) {
			var timeBase = data[key]
			var current = [ dateHelper(timeBase.date), timeBase.value ]
			revokedData.push(current)
		}
		voucherStatistic.onRevokedSuccess(revokedData)
	},
	onRevokedFail : function() {
		voucherStatistic.onRevokedFail()
	},
	onSoldSuccess : function(data) {
		var soldData = []
		for (key in data) {
			var timeBase = data[key]
			var current = [ dateHelper(timeBase.date), timeBase.value ]
			soldData.push(current)
		}
		voucherStatistic.onSoldSuccess(soldData);
	},
	onSoldFail : function() {
		callback.onSoldFail();
	}
}

var voucherStatistic = {
	service : vouchersService,
	chart : barchart,
	soldFinish : false,
	revokedFinish : false,
	getSoldVoucher : function() {
		this.service.getSold(this.getParameter());
	},
	getRevokedVoucher : function() {
		this.service.getRevoked(this.getParameter());
	},
	onRevokedSuccess : function(data) {
		this.revokedFinish = true;
		barchart.setRevoked(data)
		if (this.soldFinish == true) {
			barchart.draw();
			this.soldFinish = false
			this.revokedFinish = false
		}
	},
	onRevokedFail : function() {
		this.revokedFinish = true;
	},
	onSoldSuccess : function(data) {
		this.soldFinish = true
		barchart.setSold(data)
		if (this.revokedFinish == true) {
			barchart.draw();
			this.soldFinish = false
			this.revokedFinish = false
		}
	},
	onSoldFail : function() {
		this.soldFinish = true
	},
	getParameter : function() {
		return {
			mode : $("#mode").val(),
			fromdate : $("#fromDate").val(),
			todate : $("#toDate").val()
		}
	},
	build : function() {
		this.getRevokedVoucher()
		this.getSoldVoucher()
	}
}
var dateHelper = function(value) {
	var date = new Date(value);
	var mode = $("#mode").val()
	date.setHours(0)
	date.setSeconds(0)
	date.setMilliseconds(0)
	if (mode == "MONTH") {
		date.setDate(1)
	} else if (mode === "WEEK") {
	} else if (mode == "YEAR") {
		date.setDate(1)
		date.setMonth(1)
	}
	return date.getTime()
}
var barchart = {
	data : [],
	d1_1 : [],
	d1_2 : [],
	soldFinish : false,
	revokeFinish : false,
	setSold : function(data) {
		this.d1_1 = data
	},
	setRevoked : function(data) {
		this.d1_2 = data
	},
	init : function() {
		return [ {
			label : "Sold",
			data : this.d1_1,
			bars : {
				show : true,
				order : 1,
				fillColor : {
					colors : [ {
						color : themethirdcolor
					}, {
						color : themethirdcolor
					} ]
				}
			},
			color : themethirdcolor
		}, {
			label : "Revoked",
			data : this.d1_2,
			bars : {
				show : true,
				order : 2,
				fillColor : {
					colors : [ {
						color : themesecondary
					}, {
						color : themesecondary
					} ]
				}
			},
			color : themesecondary
		} ];
	},
	draw : function() {
		$.plot($("#bar-chart"), this.init(), {

			bars : {
				barWidth : this.getBarWidth(),
				lineWidth : 1,
				borderWidth : 0,
				fillColor : {
					colors : [ {
						opacity : 0.6
					}, {
						opacity : 1
					} ]
				}
			},
			xaxis : {
				mode : 'time',
				timeformat : this.getTimeFormat(),
				color : gridbordercolor
			},
			yaxis : {
				color : gridbordercolor
			},
			grid : {
				hoverable : true,
				clickable : false,
				borderWidth : 0,
				aboveData : false
			},
			legend : true,
			tooltip : true,
			tooltipOpts : {
				defaultTheme : false,
				content : "<b>%s</b> : <span>%x</span> : <span>%y</span>",
			}
		});
	},
	getBarWidth : function() {
		var mode = $("#mode").val()
		if (mode === "DATE") {
			return 17500000;
		} else if (mode == "MONTH") {
			return 17500000 * 30
		} else if (mode === "WEEK") {
			return 17500000 * 7
		} else {
			return 17500000 * 365
		}
	},
	getTimeFormat : function() {
		var mode = $("#mode").val()
		if (mode === "DATE") {
			return "%d/%m";
		} else if (mode == "MONTH") {
			return "%m/%y"
		} else if (mode === "WEEK") {
			return "%d/%m"
		} else {
			return "20%y"
		}
	}
}