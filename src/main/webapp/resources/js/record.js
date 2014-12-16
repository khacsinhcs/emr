// global variables
var currentSeletedUser = "";
var currentSeletedRecord = "";

/**
 * Get record content
 */
function getRecordContentForUpdate() {
	if ($.trim(currentSeletedRecord) == '' || $.trim(currentSeletedUser) == '') {
		$('#clinicDataContent').html('');
		return;
	}
	var btn = $('#btnUpdatetListConcept');
    $.ajax({
        url: 'getRecordContent',
        type: 'GET',
        dataType: 'json',
        beforeSend: function() {
        	btn.button('record');
            showProgressLoad();
        },
        data: {
            username: currentSeletedUser,
            recordId: currentSeletedRecord
        },
        success: function(data) {
            $('#clinicDataContent').html('');
            if (data != null) {
                var listSentences = data['listSentences'];
                // show record contents
                $('#clinicDataContent').html(buildRecordContent(listSentences));
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

function getListRecord(username) {
    $.ajax({
        url: 'getListRecord',
        type: 'GET',
        dataType: 'json',
        data: {
            username: username
        },
        success: function(data) {
        	// reset list records
        	$('#listRecords').html('');
            if (data != null && data.length > 0) {
            	// set current selected record
            	currentSeletedRecord = data[0].id;
            	for (var i = 0; i < data.length; i++) {
            		var recvn = data[i];
            		var anOption = '<option value="' + recvn.id + '">' + recvn.id + '</option>';
            		// append new record id
            		$('#listRecords').append(anOption);
            	}
            	// set number of user annotated records
            	$('#userAnnotatedRecordNum').text(data.length);
            } else {
            	$('#userAnnotatedRecordNum').text(0);
            	currentSeletedRecord = '';
            }
            getRecordContentForUpdate();
        },
        error: function() {
        	alert('Something error, try again!');
        },
        complete: function() {
        }
    });
}

/**
 * Submit list concept
 */
function updateListConcept() {
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
		url: 'updateConcept',
		type: 'POST',
		dataType: 'json',
		data: {
			listConcepts: JSON.stringify(listAnnotatedConcept),
			recordId: currentSeletedRecord,
			userRec: currentSeletedUser
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
			} else if (data[data.length - 1] == 'login') {
				alert('You must login to update record!');
				window.location.href = '../login.html';
			} else if (data[data.length - 1] == 'autho') {
				alert('You can not update records of other user');
			} else if (data[data.length - 1] == 'success') {
				alert('Save list concept successful. Thank you!');
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

function onChangeSeletedUserHandle() {
    var selectedUser = $('#listUsers').find('option:selected').val();
    if ($.trim(selectedUser) == '') {
        selectedUser = $('#listUsers').val();
    }
    currentSeletedUser = selectedUser;
    getListRecord(selectedUser);
    checkValidUpdateConcept(selectedUser);
}

function checkValidUpdateConcept(selectedUser) {
	if (typeof $('#hasRoleAdmin').val() != 'undefined') {
		$('#btnUpdatetListConcept').prop('disabled', false);
		return;
	} else if (typeof $('#hasRoleUser').val() != 'undefined') {
		if ($.trim(selectedUser) != $('#loggedUserId').val()) {
			$('#btnUpdatetListConcept').prop('disabled', true);
		} else {
			$('#btnUpdatetListConcept').prop('disabled', false);
		}
	} else {
		$('#btnUpdatetListConcept').prop('disabled', true);
	}
}

function onChangeSeletedRecordHandle() {
    var selectedRecord = $('#listRecords').find('option:selected').val();
    if ($.trim(selectedRecord) == '') {
        selectedRecord = $('#listRecords').first().val();
    }
    currentSeletedRecord = selectedRecord;
    getRecordContentForUpdate();
}

function setDefaultSelectedUser() {
	if (typeof $('#loggedUserId') == 'undefined' || $.trim($('#loggedUserId').val()) == '') {
		$("#listUsers").val($("#listUsers option:first").val());
	} else {
		$("#listUsers").val($("#loggedUserId").val());
	}
}

$(document).ready(function() {
    $('#listUsers').change(onChangeSeletedUserHandle);
    // handle on change record
    $('#listRecords').change(onChangeSeletedRecordHandle);
    // handle button submit list concept
	$('#btnUpdatetListConcept').click(updateListConcept);
	// set current user is selected
	setDefaultSelectedUser();
	// handle on change selected user
	onChangeSeletedUserHandle();
})
