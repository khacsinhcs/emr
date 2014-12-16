//variable contain list of annotated concept
var listAnnotatedConcept = [];
var currentHandleRecordId;

/**
 * Highline a concept
 * @param  {String} text concept text
 * @param  {String} type concept type
 * @return {String}      a concept that has been highlined
 */
function hightlineConcept(text, type, wordFromTo) {
    var conceptPattern = '<span data-fromWord="{2}" data-toWord="{3}" class="{0} conceptClassWrapper">{1}</span>';
    switch (type) {
        case 'PR':
            conceptPattern = conceptPattern.replace('{0}', 'problemHighlineClass');
            break;
        case 'TE':
            conceptPattern = conceptPattern.replace('{0}', 'testHighlineClass');
            break;
        case 'TR':
            conceptPattern = conceptPattern.replace('{0}', 'treatmentHighlineClass');
            break;
    }
    conceptPattern = conceptPattern.replace('{1}', text);
    conceptPattern = conceptPattern.replace('{2}', wordFromTo.fromWord);
    conceptPattern = conceptPattern.replace('{3}', wordFromTo.toWord);
    return conceptPattern;
}

/**
 * @param orginalText original text to be replace
 * @param replaceText text to be replace
 * @param start start char
 * @param end end char
 * @returns new string that has been replaced
 */
function replaceTextAtPos(orginalText, replaceText, start, end) {
    return orginalText.substring(0, start) + replaceText + orginalText.substring(end);
}

/**
 * Highline a concept in container
 * @param content concept content to be highlined
 * @param type type of concept
 * @param container container that contain concept (DOM element)
 * @param start start pos
 * @param end end pos
 */
function highlineConceptInContainer(content, type, container, wordFromTo) {
	var senHtml = $(container).text();
	senHtml = highlineAllConceptInSen($(container).parent().find('.sentenceId').val(), senHtml);
	$(container).html(senHtml);
	attachContextMenuForAnnotatedConcept('span.conceptClassWrapper');
}

function highlineAllConceptInSen(sentenceId, senContent) {
	var senConcepts = [];
	for (var idx = 0; idx < listAnnotatedConcept.length; idx++) {
		var concept = listAnnotatedConcept[idx];
		if (concept.sentenceId == sentenceId) {
			senConcepts.push(concept);
		}
	}
	return buildHtmlStrForContent(senConcepts, senContent);
}

function buildHtmlStrForContent(senConcepts, senContent) {
	senConcepts.sort(function(c1, c2) {
		return (c1.fromWord - c2.fromWord);
	})
	var senHtml = '';
	var preConcept = null;
	for (var idx = 0; idx < senConcepts.length; idx++) {
		var concept = jQuery.extend(true, {}, senConcepts[idx]);
		var wordFromTo = {
				fromWord: concept.fromWord,
				toWord:concept.toWord
		};
		concept.content = hightlineConcept(concept.content, concept.type, wordFromTo);
		if (preConcept == null) {
			senHtml += senContent.substring(0, concept.fromChar);
		} else {
			senHtml += senContent.substring(preConcept.toChar, concept.fromChar);
		}
		senHtml += concept.content;
		preConcept = concept;
	}
	senHtml += senContent.substring(preConcept.toChar);
	return senHtml;
}

function attachContextMenuForAnnotatedConcept(selector) {
	$.contextMenu({
		selector: selector,
		trigger: 'left',
		callback: function(key, options) {
			var fromWord = $(this).attr('data-fromWord');
			var toWord = $(this).attr('data-toWord');
			var sentenceId = $(this).parent().parent().find('.sentenceId').val();
			switch (key) {
			case 'PR':
				changeHighlineClassConceptHighlineClass($(this), 'problemHighlineClass');
				changeConceptTypeInList(sentenceId, fromWord, toWord, 'PR');
				break;
			case 'TE':
				changeHighlineClassConceptHighlineClass($(this), 'testHighlineClass');
				changeConceptTypeInList(sentenceId, fromWord, toWord, 'TE');
				break;
			case 'TR':
				changeHighlineClassConceptHighlineClass($(this), 'treatmentHighlineClass');
				changeConceptTypeInList(sentenceId, fromWord, toWord, 'TR');
				break;
			default:
				$(this).contents().unwrap();
			changeConceptTypeInList(sentenceId, fromWord, toWord, 'NO');
			break;
			}
			showContributionSection();
		},
		items: {
			"PR": {name: "Problem", className: 'problemHighlineClass'},
			"TE": {name: "Test", className: 'testHighlineClass'},
			"TR": {name: "Treatment", className: 'treatmentHighlineClass'},
			"sep1": "---------",
			"NO": {name: "Clear"}
		}
	});
	
}

/**
 * Remove a concept out of list
 * 
 * @param text
 *            concept text
 * @param type
 *            concept type
 * @author dao.diep
 */
function changeConceptTypeInList(sentenceId, fromWord, toWord, newType) {
	var changeIndex = -1;
	for (var idx = 0; idx < listAnnotatedConcept.length; idx++) {
		var concept = listAnnotatedConcept[idx];
		if (concept.sentenceId == sentenceId && concept.fromWord == fromWord && concept.toWord == toWord) {
			changeIndex = idx;
			break;
		}
	}
	if (changeIndex != -1) {
		if (newType == 'NO') {
			listAnnotatedConcept.splice(changeIndex, 1);
		} else {
			listAnnotatedConcept[changeIndex].type = newType;
		}
	}
}

/**
 * @param element element
 * @param className class name want to be changed
 */
function changeHighlineClassConceptHighlineClass(element, className) {
	if (element.hasClass('problemHighlineClass')) {
		element.removeClass('problemHighlineClass');
	} else if (element.hasClass('testHighlineClass')) {
		element.removeClass('testHighlineClass');
	} else if (element.hasClass('treatmentHighlineClass')) {
		element.removeClass('treatmentHighlineClass');
	}
	element.addClass(className);
}

/**
 * Attach context menu for sen
 * @param senSelector sentence selector
 */
function attachContextMenuForSen(senSelector) {
	$.contextMenu({
        selector: senSelector,
        callback: function(key, options) {
        	switch (key) {
			case 'PR':
				assignClassProblemHandle();
				break;
			case 'TE':
				assignClassTestHandle();
				break;
			case 'TR':
				assignClassTreatmentHandle();
				break;
			default:
				break;
			}
        	showContributionSection();
        },
        items: {
            "PR": {name: "Problem", className: 'problemHighlineClass'},
            "TE": {name: "Test", className: 'testHighlineClass'},
            "TR": {name: "Treatment", className: 'treatmentHighlineClass'},
            "sep1": "---------",
            "NO": {name: "Clear"}
        }
    });
}

/**
 * Show contribution section
 */
function showContributionSection() {
	if ($('#contributionContainer').length) {
		if ($('#contributionContainer').hasClass('hideContent')) {
			$('#contributionContainer').removeClass('hideContent');
		}
	}
}

/**
 * Show progress loading
 */
function showProgressLoad() {
	$('.loading-container').removeClass('loading-inactive');
	$('.loading-container').addClass('loading-progess');
}

/**
 * Hide progress loading
 */
function hideProgressLoad() {
	$('.loading-container').removeClass('loading-progess');
	$('.loading-container').addClass('loading-inactive');
}

/**
 * Get selection word from to in a text
 * @param  {String} text     text
 * @param  {String} fromChar from char
 * @param  {String} toChar   to char
 * @return {Object}          object contain fromWord and toWord properties
 */
function getSelectionWordFromTo(text, fromSelectChar, toSelectChar) {
	var fromWord = 0, toWord = 0, fromChar = 0, toChar = 0;
	var wordArr = text.split(' ');
	var countChar = 0;
	var selectedContent = [];
	var startWordFlag = false;
	for (var i = 0; i < wordArr.length; i++) {
		var word = wordArr[i];
		// count char
		countChar += word.length + 1;
		// set from word
		if (fromWord == 0 && countChar > fromSelectChar) {
			fromWord = i + 1;
			startWordFlag = true;
			fromChar = countChar - (word.length + 1);
		}
		if (startWordFlag) {
			selectedContent.push(word);
		}
		// set to word
		if (countChar >= toSelectChar) {
			toWord = i + 1;
			toChar = countChar - 1;
			break;
		}
	};
	return {
		fromWord: fromWord,
		toWord: toWord,
		fromChar: fromChar,
		toChar: toChar,
		content: selectedContent.join(' ')
	}
}

/**
 * @returns {Selected text information}
 */
function getTextSelectionInfo() {
	// get text from to
	var charFromTo = getSelectionCharFromTo();
	if (!charFromTo) {
		return false;
	}
	// check if selected text is blank
	content = $.trim(charFromTo.content);
	if (content == '') {
		alert('You have selected a blank text. Try again!')
		return false;
	}
	// get current focus node
	var sentenceContainer = charFromTo.focusNode;
	var wordFromTo = getSelectionWordFromTo($(sentenceContainer).text(), charFromTo.start, charFromTo.end);
	var posProcessedContent = $.trim(wordFromTo.content);
	if (posProcessedContent == '' || wordFromTo.fromWord == 0
			|| wordFromTo.toWord == 0
			|| wordFromTo.fromWord > wordFromTo.toWord) {
		alert('You have selected an invalid text. Please select another!');
		return false;
	}
	var sentenceId = $(sentenceContainer.parentNode).find('.sentenceId').val();
	var recordId = $(sentenceContainer.parentNode).find('.recordId').val();
	// check if concept has exist in list
	if (validateConceptInList(sentenceId, wordFromTo.fromWord, wordFromTo.toWord)) {
		alert('Your selected text is existed or conflic with other concept. Please select another!');
		return false;
	};
	return {
		content: posProcessedContent,
		sentenceId: sentenceId,
		recordId: recordId,
		wordFromTo: wordFromTo,
		container: sentenceContainer
	}
}

/**
 * @param sentenceId
 * @param fromWord
 * @param toWord
 */
function validateConceptInList(sentenceId, fromWord, toWord) {
	for (var idx = 0; idx < listAnnotatedConcept.length; idx++) {
		var concept = listAnnotatedConcept[idx];
		if (concept.sentenceId == sentenceId) {
			// duplicate concept
			if (concept.fromWord == fromWord && concept.toWord == toWord) {
				return true;
			}
			// invalid fromWord
			if (fromWord >= concept.fromWord && fromWord <= concept.toWord) {
				return true;
			}
			// invalid toWord
			if (toWord >= concept.fromWord && toWord <= concept.toWord) {
				return true;
			}
		}
	}
	return false;
}

/**
 * Assign class problem
 * 
 * @author dao.diep
 */
function assignClassProblemHandle() {
	var txtInfo = getTextSelectionInfo();
	if (!txtInfo) {
		return false;
	}
	addNewConceptToList(txtInfo.content, 'PR', txtInfo.sentenceId, txtInfo.recordId, txtInfo.wordFromTo);
	// add concept to Problem panel
	//addConceptToContainer('#listClassProblemContainer', 'PR', txtInfo.content);
	// highline concept tagging
	highlineConceptInContainer(txtInfo.content, 'PR', txtInfo.container, txtInfo.wordFromTo);
}


/**
 * Assign class test
 * 
 * @author dao.diep
 */
function assignClassTestHandle() {
	var txtInfo = getTextSelectionInfo();
	if (!txtInfo) {
		return false;
	}
	addNewConceptToList(txtInfo.content, 'TE', txtInfo.sentenceId, txtInfo.recordId, txtInfo.wordFromTo);
	// add concept to Test panel
	//addConceptToContainer('#listClassTestContainer', 'TE', txtInfo.content);
	// highline concept tagging
	highlineConceptInContainer(txtInfo.content, 'TE', txtInfo.container, txtInfo.wordFromTo);
}

/**
 * @returns {from: fromWord, to: toWord}
 */
function getSelectionCharFromTo() {
    var start = 0, end = 0;
    var content = '';
    var sel, range, priorRange;
    var focusNode = null;
    if (window.getSelection) {
    	content = window.getSelection().toString();
    	if (window.getSelection().focusNode == null) {
    		alert('Please select a text!');
    		return false;
    	}
    	focusNode = window.getSelection().focusNode.parentNode;
    	if (focusNode == null) {
    		alert('Please select a text!');
    		return false;
    	}
    	try {
    		while (!$(focusNode).hasClass('sentenceContent')) {
    			focusNode = focusNode.parentNode;
    		}
    	} catch (err) {
    		alert('Your text selection area is wrong. Please select text in correct range!');
    		return false;
    	}
        range = window.getSelection().getRangeAt(0);
        priorRange = range.cloneRange();
        priorRange.selectNodeContents(focusNode);
        priorRange.setEnd(range.startContainer, range.startOffset);
        start = priorRange.toString().length;
        end = start + range.toString().length;
    } else if (typeof document.selection != "undefined" &&
            (sel = document.selection).type != "Control") {
    	content = document.selection.createRange().text;
    	focusNode = document.selection.createRange().parentElement();
    	if (focusNode == null) {
    		alert('Please select a text!');
    		return false;
    	}
    	try {
    		while (!$(focusNode).hasClass('sentenceContent')) {
    			focusNode = focusNode.parentNode;
    		}
    	} catch (err) {
    		alert('Your text selection area is wrong. Please select text in correct range!');
    	}
        range = sel.createRange();
        priorRange = document.body.createTextRange();
        priorRange.moveToElementText(focusNode);
        priorRange.setEndPoint("EndToStart", range);
        start = priorRange.text.length;
        end = start + range.text.length;
    }
    if ($(focusNode).text().charAt(start) == ' ') {
    	start = start + 1;
    }
    if ($(focusNode).text().charAt(end - 1) == ' ') {
    	end = end - 1;
    }
    return {
        start: start + 1,
        end: end,
        content: content,
        focusNode: focusNode
    };
}

/**
 * Add new concept to list
 * 
 * @param text
 *            concept text
 * @param type
 *            concept type
 * @author dao.diep
 */
function addNewConceptToList(content, type, sentenceId, recordId, wordFromTo) {
	var concept = {};
	concept.content = content;
	concept.type = type;
	concept.sentenceId = sentenceId;
	concept.recordId = recordId;
	concept.fromWord = wordFromTo.fromWord;
	concept.toWord = wordFromTo.toWord;
	concept.fromChar = wordFromTo.fromChar;
	concept.toChar = wordFromTo.toChar;
	listAnnotatedConcept.push(concept);
}

/**
 * Assign class treatment
 * 
 * @author dao.diep
 */
function assignClassTreatmentHandle() {
	var txtInfo = getTextSelectionInfo();
	if (!txtInfo) {
		return false;
	}
	addNewConceptToList(txtInfo.content, 'TR', txtInfo.sentenceId, txtInfo.recordId, txtInfo.wordFromTo);
	// add concept to Treatment panel
	//addConceptToContainer('#listClassTreatmentContainer', 'TR', txtInfo.content);
	highlineConceptInContainer(txtInfo.content, 'TR', txtInfo.container, txtInfo.wordFromTo);
}

/**
 * Change section header name
 * @param  {String} section section header
 * @return {String}         section header string
 */
function changeSenSectionName(section) {
    var sectionStr = section;
    switch(section) {
        case 'LYDO':
            sectionStr = 'Lý Do';
            break;
        case 'HB_BENHLY':
            sectionStr = 'Bệnh Lý';
            break;
        case 'HB_BANTHAN':
            sectionStr = 'Tiền Sử Bệnh';
            break;
        case 'HB_GIADINH':
            sectionStr = 'Tiền Sử Gia Đình';
            break;
        case 'KB_TOANTHAN':
            sectionStr = 'Khám Toàn Thân';
            break;
        case 'KB_TUANHOAN':
            sectionStr = 'Khám Tuần Hoàn';
            break;
        case 'KB_HOHAP':
            sectionStr = 'Khám Hệ Hô Hấp';
            break;
        case 'KB_TIEUHOA':
            sectionStr = 'Khám Hệ Tiêu Hóa';
            break;
        case 'KB_THAN':
            sectionStr = 'Khám Thận';
            break;
        case 'KB_THANKINH':
            sectionStr = 'Khám Thần Kinh';
            break;
        case 'KB_CO':
            sectionStr = 'Khám Hệ Cơ';
            break;
        case 'KB_NOITIET':
            sectionStr = 'Khám Nội Tiết';
            break;
        case 'TIENLUONG':
            sectionStr = 'Tiên Lượng';
            break;
        case 'DIEUTRI':
            sectionStr = 'Điều Trị';
            break;
        case 'KB_KHAC':
            sectionStr = 'Khám Bệnh Khác';
            break;
        case 'KB_TOMTAT':
            sectionStr = 'Tóm Tắt Khám Bệnh';
            break;
        case 'KB_TMH':
            sectionStr = 'Tai Mũi Họng';
            break;
        case 'KB_RHM':
            sectionStr = 'Răng Hàm Mặt';
            break;
        case 'KB_MAT':
            sectionStr = 'Mắt';
            break;
        case 'TK_BENHLY':
            sectionStr = 'Tổng Kết Bệnh Lý';
            break;
        case 'TK_TOMTAT':
            sectionStr = 'Tóm Tắt';
            break;
        case 'TK_PHUONGPHAP':
            sectionStr = 'Tổng Kết Phương Pháp Điều Trị';
            break;
        case 'TK_TINHTRANG':
            sectionStr = 'Tình Trạng Ra Viện';
            break;
        case 'TK_DIEUTRI':
            sectionStr = 'Điều Trị Sau Khi Ra Viện';
            break;
        case 'PHANBIET':
            sectionStr = 'Bệnh Dễ Lây Nhiễm - Nguy Hiểm';
            break;
        case 'KB_NGOAI':
            sectionStr = 'Khám Bệnh Ngoại Khoa';
            break;
    }
    return sectionStr;
}
/**
 * buil record content
 * @param listSentences list sentence
 */
function buildRecordContent(listSentences) {
	// reset list annotated record
	listAnnotatedConcept = [];
	// reset record content
    var clinicalText = '';
    var lastSenSection = '';
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
            if (lastSenSection != sentence.section) {
				clinicalText += '<span class="sentenceHeader">'
					+ changeSenSectionName(sentence.section) + '</span><br>';
				lastSenSection = sentence.section;
			}
            clinicalText += '<span class="sentenceContent">' + sentence.content + '</span>';
            clinicalText += '<input type="hidden" class="sentenceId" value="' + sentence.id + '">';
            clinicalText += '<input type="hidden" class="recordId" value="' + sentence.recordId + '">';
            clinicalText += '</div>';
        }
    }
    return clinicalText;
}