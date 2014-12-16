// current list content
var currentTextContent = "";
// list sentence after process text content
var currentListSentences = null;

/**
 * Name entity recognition
 */
var testPattern = /[a-zA-Z0-9]*--B-TE(\s[a-z0-9A-Z-]+--I-TE)*/g
var problemPattern = /[a-zA-Z0-9]*--B-PR(\s[a-z0-9A-Z-]+--I-PR)*/g
var treatmentPattern = /[a-zA-Z0-9]*--B-TR(\s[a-z0-9A-Z-]+--I-TR)*/g

var postData = function() {
	var url = host + "/ner/annotation";
	var listFeatures = [];
	jQuery('.featuresGroup').each(function(index, feature) {
		if ($(feature).is(':checked')) {
			listFeatures.push($(feature).val());
		}
	});
	currentTextContent = jQuery("#textContent").val();
	var btn = $('#btnSubmitTextContent');
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        beforeSend: function() {
        	btn.button('annotate');
            showProgressLoad();
        },
        data: {
        	content: currentTextContent,
    		language: jQuery("#selectedLanguage").find(":selected").val(),
    		listFeatures: JSON.stringify(listFeatures)
        },
        success: function(data) {
            $('#clinicDataContent').html('');
            listAnnotatedConcept = [];
            if (data != null) {
            	if (data == "FAILS") {
            		alert('Something error. Please wait!');
            		return;
            	}
                var listSentences = data['listSentences'];
                // set current list sentence
                currentListSentences = jQuery.extend(true, [], listSentences);
                var clinicalText = '';
                //var lastSenSection = '';
                for (var idx = 0; idx < listSentences.length; idx++) {
                    var sentence = listSentences[idx];
                    if ($.trim(sentence.content) != '') {
                        var listConcepts = sentence.concept;
                        if (typeof listConcepts !== 'undefined' && listConcepts.length > 0) {
                            for (var j = 0; j < listConcepts.length; j++) {
                                var concept = listConcepts[j];
                                // add concept to list
                                listAnnotatedConcept.push(concept);
                            }
                            sentence.content = buildHtmlStrForContent(listConcepts, sentence.content);
                        }
                        clinicalText += '<div class="sentenceContainer">';
                        /*if (lastSenSection != sentence.section) {
							clinicalText += '<span class="sentenceHeader">'
								+ changeSenSectionName(sentence.section) + '</span><br>';
							lastSenSection = sentence.section;
						}*/
                        clinicalText += '<span class="sentenceContent">' + sentence.content + '</span>';
                        clinicalText += '<input type="hidden" class="sentenceId" value="' + sentence.id + '">';
                        //clinicalText += '<input type="hidden" class="recordId" value="' + currentSeletedRecord + '">';
                        clinicalText += '</div>';
                    }
                }
                // show record contents
                $('#clinicDataContent').html(clinicalText);
                attachContextMenuForSen('span.sentenceContent');
                attachContextMenuForAnnotatedConcept('span.conceptClassWrapper');
            }
        },
        error: function(error) {
            alert('Something error. Please wait!');
        },
        complete: function() {
        	btn.button('reset');
        	hideProgressLoad();
        }
    });
	
}

var showResult = function(data, result) {
	var listTest = initList(testPattern, result)
	var listProblem = initList(problemPattern, result)
	var listTreatment = initList(treatmentPattern, result)

	data = fillColor(listTest, data, "red");
	data = fillColor(listProblem, data, "blue");
	data = fillColor(listTreatment, data, "violet");

	return data
}

var fillColor = function(list, data, color) {

	for (index in list) {
		var slip = data.split(list[index])
		data = slip[0] + "<font color='" + color + "'>" + list[index]
				+ "</font>" + slip[1]
	}
	return data;
}

var initList = function(pattern, labledData) {
	var result = labledData.match(pattern);
	for (val in result) {
		result[val] = result[val].replace(/--(B|I)-[A-Z]+/g, "")
	}
	return result;
}

/**
 * Contribute new content
 */
function contributeNewContent() {
	// get current text content
	var record = currentTextContent;
	// get current list sentences
	var listSentences = currentListSentences;
	for (var idx = 0; idx < listSentences.length; idx++) {
		listSentences[idx].concept = null;
	}
	var btn = $('#btnContributeContent');
	$.ajax({
        url: 'contributeNewContent',
        type: 'POST',
        dataType: 'json',
        beforeSend: function() {
        	btn.button('contribute');
            showProgressLoad();
        },
        data: {
        	record: record,
        	listSentences: JSON.stringify(listSentences),
        	listConcepts: JSON.stringify(listAnnotatedConcept),
        	language: jQuery("#selectedLanguage").find(":selected").val(),
        },
        success: function(data) {
        	if (data == null) {
				alert('Something error. Please submit you data again, Thanks!');
			} else if (data["result"] == 'fail') {
				alert('Something error. Please submit you data again, Thanks!');
			} else if (data["result"] == 'success') {
				alert('Save list concept successful. Thank for contributing!');
				// close contribute section
				$('#btnCloseContribution').click();
			}
        },
        error: function(error) {
        	alert('Something error. Please submit you data again, Thanks!');
        },
        complete: function() {
        	btn.button('reset');
        	hideProgressLoad();
        }
    });
}

$(document).ready(function () {
	$("#btnSubmitTextContent").click(function(){
		postData();
	});
	// btn close contribution hadle
	$('#btnCloseContribution').click(function() {
		if (!$('#contributionContainer').hasClass('hideContent')) {
			$('#contributionContainer').addClass('hideContent');
		}
	});
	// btn contribute new content handle
	$('#btnContributeContent').click(function() {
		contributeNewContent();
	});
	
	// handle button assign class problem
	//$('#btnAssignClassProblem').click(assignClassProblemHandle);
	// handle button assign class test
	//$('#btnAssignClassTest').click(assignClassTestHandle);
	// handle button assign class treatment
	//$('#btnAssignClassTreatment').click(assignClassTreatmentHandle);
})