/**
 * 
 */
var testPattern = /[a-zA-Z0-9]*--B-TE(\s[a-z0-9A-Z-]+--I-TE)*/g
var problemPattern = /[a-zA-Z0-9]*--B-PR(\s[a-z0-9A-Z-]+--I-PR)*/g
var treatmentPattern = /[a-zA-Z0-9]*--B-TR(\s[a-z0-9A-Z-]+--I-TR)*/g

var postData = function() {
	url = "";
	jQuery.post(function() {

	})
}

var showResult = function(data, result) {
	var listTest = initList(testPattern, result)
	var listProblem = initList(problemPattern, result)
	var listTreatment = initList(treatmentPattern, result)
	
	data = fillColor(listTest, data, "red");
	data = fillColor(listProblem, data, "blue");
	data = fillColor(listTreatment, data, "yellow");
	
	console.log(data)
}

var fillColor = function(list, data, color) {
	for (index in list) {
		var slip = data.slip(list[index])
		data = slip[0] + "<font color='" + color + "'>" + list[index] + "</font>"
	}
	return data;
}

var initList = function(pattern, labledData) {
	var result = labledData.match(pattern);
	for (val in result) {
		result[val] = result[val].replace(/--(B|I)-[A-Z]+/g, "")
	}
}