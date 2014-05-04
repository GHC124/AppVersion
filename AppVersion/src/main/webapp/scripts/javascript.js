$(function(){ 
 	$("button").button();	
 	$('input').addClass("ui-corner-all");
});

function log(message){
	if(console && console.log){
		console.log(message);
	}
}

function spliceArray(data, id){
	delete data[id];
}

function cancelDefaultAction(e) {
	 var evt = e ? e:window.event;
	 if (evt.preventDefault) evt.preventDefault();
	 evt.returnValue = false;
	 return false;
}

function collectFormData(formId) {
	var data = {};
	var form = $(formId);
	var inputs = form.find('input[type=text],input[type=hidden],select,textarea');
	for (var i = 0; i < inputs.length; i++) {
		var $item = $(inputs[i]);
		data[$item.attr('name')] = $item.val(); 
	}
	return data;
}

function populateFormData(formId, data) {
	var form = $(formId);
	var inputs = form.find('input[type=text],input[type=hidden],select,textarea');
	for (var i = 0; i < inputs.length; i++) {
		var $item = $(inputs[i]);
		 $item.val(data[$item.attr('name')]);
	}
}

function addInputError(fieldId, message){
	var $controlGroup = $(fieldId);
	$controlGroup.find('.control-label').addClass('error');
	$controlGroup.find('.help-inline').addClass('error');
	$controlGroup.find('.help-inline').append(message);			
}

function removeInputError(formId){
	var $form = $(formId);	
	$form.find('.control-label').removeClass('error');
	$form.find('.help-inline').removeClass('error');
	$form.find('.help-inline').empty();
	$form.find('.alert').remove();		
}

function getAjaxRequest(validateUrl, data, successMethod, doneMethod, failMethod){
	$.ajax({
	    url: validateUrl,
	    data: data,
	    dataType: 'json',
	    processData: true,
	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	    type: 'GET',
	    success: function(response){
			if(successMethod){
				successMethod(response);
			}
	    }
	}).done(function(){
		if(doneMethod){
			doneMethod();
		}
	}).fail(function(){
		if(failMethod){
			failMethod();
		}
	});
}

function postAjaxRequest(validateUrl, data, successMethod, doneMethod, failMethod){
	$.ajax({
	    url: validateUrl,
	    data: data,
	    dataType: 'json',
	    processData: true,
	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	    type: 'POST',
	    success: function(response, status, jqXHR){
			if(successMethod){
				successMethod(response);
			}
	    }
	}).done(function(){
		if(doneMethod){
			doneMethod();
		}
	}).fail(function(jqXHR, textStatus, errorThrown){
		if(jqXHR.status == 200){
			if(doneMethod){
				doneMethod();
			}
		}else if(failMethod){
			failMethod();
		}
	});
}

function formAjaxSubmit(formId, validateUrl, successMethod, failMethod, doneMethod, errorMethod, replaceData){
	var data = collectFormData(formId);
	if(replaceData){
		for(var key in replaceData){
			data[key]=replaceData[key];
		}
	}
	$.ajax({
	    url: validateUrl,
	    data: data,
	    dataType: 'json',
	    processData: true,
	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
	    type: 'POST',
	    success: function(response){
	    	removeInputError(formId);
			if (response.status == 'FAIL') {
				for (var i = 0; i < response.result.length; i++) {
					var item = response.result[i];
					addInputError(formId + " #div_" + item.fieldName, item.message+"<br/>");								
				}
				if(failMethod){
					failMethod(response);
				}
			} else {
				if(successMethod){
					successMethod(response);
				}
			}
	    }
	}).done(function(){
		if(doneMethod){
			doneMethod();
		}
	}).fail(function(){
		if(errorMethod){
			errorMethod();
		}
	});
}

function fileAjaxSubmit(formId, validateUrl, successMethod, failMethod, doneMethod, errorMethod, replaceData){
	var data = collectFormData(formId);
	if(replaceData){
		for(var key in replaceData){
			data[key]=replaceData[key];
		}
	}
	var formData = new FormData();
	for(var key in data){
		formData.append(key, data[key]);
	}
	$.each($(formId + ' input[type=file]'), function(index, value){
		var item = $(value)[0];
		$.each(item.files, function(i, file) {
			formData.append('file-' + i, file);
		});
	});	
	$.ajax({
	    url: validateUrl,
	    data: formData,
	    dataType: 'json',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(response){
	    	removeInputError(formId);
			if (response.status == 'FAIL') {
				for (var i = 0; i < response.result.length; i++) {
					var item = response.result[i];
					addInputError(formId + " #div_" + item.fieldName, item.message+"<br/>");								
				}
				if(failMethod){
					failMethod(response);
				}
			} else {
				if(successMethod){
					successMethod(response);
				}
			}
	    }
	}).done(function(){
		if(doneMethod){
			doneMethod();
		}
	}).fail(function(){
		if(errorMethod){
			errorMethod();
		}
	});
}

function enableInput(input){
	if(input.attr('role') == 'button'){
		input.button('enable');
	}else{
		input.removeAttr('disabled');
	}	 
}

function disableInput(input){
	if(input.attr('role') == 'button'){
		input.button('disable'); 
	}else{
		input.attr("disabled", "disabled");
	}
}

function disableInputs(formName, type){
	var $form = $(formName);
	var $inputs = $form.find('input[type="' + type + '"]');
	for (var i = 0; i < $inputs.length; i++) {
		var $item = $($inputs[i]);
		disableInput($item);
	}
}

function enableInputs(formName, type){
	var $form = $(formName);
	var $inputs = $form.find('input[type="' + type + '"]');
	for (var i = 0; i < $inputs.length; i++) {
		var $item = $($inputs[i]);
		enableInput($item);
	}
}

/* JQGrid*/
function refreshJQGrid(listId, data){
	if(data){
		$(listId).setGridParam(data).trigger('reloadGrid');
	}else{
		$(listId).setGridParam({ page: 1, datatype: "json" }).trigger('reloadGrid');
	}
}

function getJQGridColumnIndexByName(grid,columnName) {
    var cm = grid.jqGrid('getGridParam','colModel');
    for(var i in cm){
    	if (cm[i].name==columnName) {
            return parseInt(i);
        }
    }
    return -1;
}

function removeActionJQGrid(grid, actionCol){
	var iCol = getJQGridColumnIndexByName(grid, actionCol) + 1;
	if(iCol == -1){
		return;
	}
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child("+iCol+")")
        .each(function() {
        	$(this).html("");
        });
}

function addAction2JQGrid(grid, valueCol, actionCol, title, icon, action){
	var iCol = getJQGridColumnIndexByName(grid, actionCol) + 1;
	var vCol = getJQGridColumnIndexByName(grid, valueCol) + 1;
	if(iCol == -1 || vCol == -1){
		return;
	}
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child("+iCol+")")
        .each(function() {
        	var div = 
        	$("<div></div>",
                {
                    title: title,
                    mouseover: function() {
                        $(this).addClass('ui-state-hover');
                    },
                    mouseout: function() {
                        $(this).removeClass('ui-state-hover');
                    },
                    click: function(e) {
                    	var id = $(e.target).closest("tr.jqgrow").attr("id"); 
                    	var value = $(e.target).closest("tr.jqgrow").children("td:nth-child("+vCol+")").html();  
                        action(id, value);
                        return cancelDefaultAction(e);
                    }
                }
              ).css({"margin-left": "5px", float:"left"})
               .addClass("ui-pg-div ui-inline-custom")
               .append('<span class="ui-icon '+ icon +'"></span>');
             $(this).append(div);
    });
}

function moveJQGridPagerInfo2Bottom(grid){
	var pager = $(grid[0].p.pager);
    var pagerInfo = $(grid[0].p.pager + '_right');
    var divPagerInfo = pager.find("#divPagerInfo");
   	if(divPagerInfo.length == 0){
   		var tableGroup = pager.find("table.ui-pg-table:first");
   		divPagerInfo = $("<div></div>");
   		divPagerInfo.attr("id","divPagerInfo");
   		divPagerInfo.append(pagerInfo.html());
        $(tableGroup).after(divPagerInfo);
        pagerInfo.html("");
        
        var pagerCenter = $(grid[0].p.pager + '_center');
       	pagerCenter.css({"width":"230px"});
    }		     
}

function clearJQGridData(grid, data){
	if(data){
		grid.jqGrid("clearGridData", true).setGridParam(data);
	}else{
		grid.jqGrid("clearGridData", true);
	}
}

function getJQGridSelectedRow(grid){
	return grid.jqGrid('getGridParam','selrow');
}

function setJQGridSelectedRow(grid, rowId){
	grid.jqGrid('setSelection',rowId); 
}

function deleteJQGridRow(grid, rowId){
	grid.jqGrid('delRowData',rowId);
}

function getJQGridUserData(grid){
	return grid.getGridParam('userData');
}

