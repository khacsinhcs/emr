//var currentPIC = "";

/**
 * Remove a concept out of list
 * 
 * @param text
 *            concept text
 * @param type
 *            concept type
 * @author dao.diep
 */
function removeConceptFromList(text, type) {
	var removeIndex = -1;
	for (var idx = 0; idx < listAnnotatedConcept.length; idx++) {
		var concept = listAnnotatedConcept[idx];
		if (concept.text == text && concept.type == type) {
			removeIndex = idx;
			break;
		}
	}
	if (removeIndex != -1) {
		listAnnotatedConcept.splice(removeIndex, 1);
	}
}

/**
 * Get text that has been selected in box
 * 
 * @returns text that has been selected in box
 * @author dao.diep
 */
function getSelectedTextOnTextarea() {
	var textComponent = document.getElementById('clinicDataContent');
	var selectedText;
	// IE version
	if (document.selection != undefined) {
		textComponent.focus();
		var sel = document.selection.createRange();
		selectedText = sel.text;
	}
	// Mozilla version
	else if (textComponent.selectionStart != undefined) {
		var startPos = textComponent.selectionStart;
		var endPos = textComponent.selectionEnd;
		selectedText = textComponent.value.substring(startPos, endPos);
	}
	return selectedText;
}

/**
 * Get text that has been selected in box
 * 
 * @returns text that has been selected in box
 * @author dao.diep
 */
function getSelectionText() {
	var text = "";
	if (window.getSelection) {
		text = window.getSelection().toString();
	} else if (document.selection && document.selection.type != "Control") {
		text = document.selection.createRange().text;
	}
	return text;
}

/**
 * Open popup class tagging
 */
function openPopupClassTagging() {
	$('#popupClassTagging').modal('show');
}

/**
 * Close popup class tagging
 */
function closePopupClassTagging() {
	// reset default clinic class
	$("#clinicClass").val('PR');
	$('#popupClassTagging').modal('hide');
}

/**
 * Reset list concept
 */
function resetListAnnotatedConcept() {
	listAnnotatedConcept = [];
	removeAllConceptFromContainer();
}

/**
 * get new record content
 */
function getNewRecordContent() {
	// reset list annotated concept and load new data
	resetListAnnotatedConcept();
	getRecordContentForAnnotated();
}

/**
 * Submit list concept
 */
function submitListConcept() {
	// check if list annotated concept is empty
	if (listAnnotatedConcept.length == 0) {
		var con = confirm('This record does not have any concept! Are you sure?');
		if (con == false) {
			return;
		}
	}
	// check current user
	//initPromptPic();
	var btn = $(this);
    btn.button('loading');
	$.ajax({
		url: 'saveConcept',
		type: 'POST',
		dataType: 'json',
		data: {
			listConcepts: JSON.stringify(listAnnotatedConcept),
			recordId: currentHandleRecordId
			//pic: currentPIC
		},
		beforeSend: function() {
			showProgressLoad();
		},
		success: function(data, textStatus, jqXHR) {
			if (data == null) {
				alert('Something error. Please submit you data again, Thanks!');
			} else if (data[data.length - 1] == 'fail') {
				alert('Something error. Please submit you data again, Thanks!');
			} else if (data[data.length - 1] == 'success') {
				alert('Save list concept successful. Thank you!');
				getNewRecordContent();
			}
		},
		error: function() {
			alert('Something error. Please submit you data again, Thanks!');
		},
		complete: function() {
			btn.button('reset');
			hideProgressLoad();
		}
	});
}

/**
 * Get current focused node
 * 
 * @returns current focused node
 * @auther dao.diep
 */
function getCurrentFocusedNode() {
	var node = window.getSelection ? window.getSelection().focusNode.parentNode
			: document.selection.createRange().parentElement();
	return node;
}

/**
 * Add clinical text to container
 * 
 * @param container
 *            container
 * @param type
 *            type of container
 * @param text
 *            text to be added
 * @author dao.diep
 */
function addConceptToContainer(container, type, text) {
	var strHtml = "";
	strHtml += '<li class="list-group-item">';
	strHtml += '<a class="glyphicon glyphicon-remove removeConceptIcon" onclick="removeConceptIconHandle(this)"></a>&nbsp;&nbsp;';
	strHtml += text;
	strHtml += '<input type="hidden" class="txtConceptType" value="' + type
			+ '">';
	strHtml += '</li>';
	$(container).find('.list-group').append(strHtml);
}

/**
 * @param container container to be cleared
 */
function removeAllConceptFromContainer() {
	$('#listClassProblemContainer').find('.list-group').html('');
	$('#listClassTestContainer').find('.list-group').html('');
	$('#listClassTreatmentContainer').find('.list-group').html('');
}

/**
 * Remove concept item handle
 */
function removeConceptIconHandle(control) {
	var text = $(control).parent().find('.txtConceptText').val();
	var type = $(control).parent().find('.txtConceptType').val();
	// remove from list
	removeConceptFromList(text, type);
	// remove view
	$(control).parent().remove();
}

/**
 * On change selected record handle
 */
function onChangeSelectedRecordHandle() {
	currentHandleRecordId = $('#listRecordId').find(':selected').text();
	$.ajax({
		url : 'getRecord',
		type : 'GET',
		dataType : 'json',
		data : {
			recordId : currentHandleRecordId
		},
		success : function(data) {
			$('#clinicDataContent').val('');
			if (data != null) {
				var clinicalText = '';
				for (var idx = 0; idx < data.length; idx++) {
					var sentence = data[idx];
					if (sentence != null) {
						if ($.trim(sentence.content) != '') {
							clinicalText += '<div class="sentenceContainer">';
							clinicalText += '<span class="sentenceHeader">'
									+ sentence.section + '</span><br>';
							clinicalText += '<span class="sentenceContent">'
									+ sentence.content + '</span>';
							clinicalText += '<input type="hidden" class="sentenceId" value="'
									+ sentenceId + '">';
							clinicalText += '<input type="hidden" class="recordId" value="'
									+ currentHandleRecordId + '">';
							clinicalText += '</div>';
						}
					}
				}
				$('#clinicDataContent').html(clinicalText);
			}
		},
		error : function(error) {
			alert('Something error. Please wait!');
		}
	});
}

/**
 * Get record content
 */
function getRecordContentForAnnotated() {
	var btn = $('#btnSubmitListConcept');
	var helperFlag = $('#chkHelper').is(':checked');
	$.ajax({
		url : 'getRecord',
		type : 'GET',
		dataType : 'json',
		data: {
			helper: helperFlag,
			dataSet: "vn"
		},
		beforeSend: function() {
		    btn.button('record');
			showProgressLoad();
		},
		success : function(data) {
			$('#clinicDataContent').html('');
			if (data != null) {
				// list sentence
				var listSentences = data['listSentences'];
				// current record handle
				currentHandleRecordId = listSentences[0].recordId;
				// number of annotated record
				var annotatedRecordCount = data['annotatedRecordCount'];
				// show record contents
				$('#clinicDataContent').html(buildRecordContent(listSentences));
				// set num of annotated record of current user
				$('#annotatedRecordCount').text(annotatedRecordCount);
				$('#currentRecordId').text(currentHandleRecordId);
				attachContextMenuForSen('span.sentenceContent');
				attachContextMenuForAnnotatedConcept('span.conceptClassWrapper');
			}
		},
		error : function(error) {
			alert('Something error. Please wait!');
		},
		complete: function() {
			btn.button('reset');
			hideProgressLoad();
		}
	});
}

/**
 * Get PIC person
 */
/*function initPromptPic() {
	while ($.trim(currentPIC) == '') {
		currentPIC = prompt('Please enter your name please, we will count your distribution on each data submission! Thanks');
	}
}*/

$(document).ready(function() {
	/*
	 * $("#submitTextClass").click(function(){ postData();
	 * closePopupClassTagging(); });
	 */
	/*
	 * $('#clinicDataContent').bind('keydown', function(e) { if (e.keyCode ==
	 * 13) { e.preventDefault();
	 * $('#selectedClinicText').text(getSelectionText());
	 * openPopupClassTagging(); } });
	 */
	// handle button assign class problem
	$('#btnAssignClassProblem').click(assignClassProblemHandle);
	// handle button assign class test
	$('#btnAssignClassTest').click(assignClassTestHandle);
	// handle button assign class treatment
	$('#btnAssignClassTreatment').click(assignClassTreatmentHandle);
	// handle button submit list concept
	$('#btnSubmitListConcept').click(submitListConcept);
	// handle onChange selected record id in list records
	// $('#listRecordId').change(onChangeSelectedRecordHandle);
	// run get record contents on first load
	// onChangeSelectedRecordHandle();
	$('#btnGetRecordContent').click(getNewRecordContent);
	// get record contents on first load
	getRecordContentForAnnotated();
	// prompt user to enter they name
	//initPromptPic();
})
