$(function(){    
	$.fn.styleTable = function (options) {
        var defaults = {
                css: 'ui-styled-table'
            };
        options = $.extend(defaults, options);

        return this.each(function () {
            $this = $(this);
            $this.addClass(options.css);

            $this.on('mouseover mouseout', 'tbody tr', function (event) {
                $(this).children().toggleClass("ui-state-hover",
                                               event.type == 'mouseover');
            });

            $this.find("th").addClass("ui-state-default");
            $this.find("td").addClass("ui-widget-content");
            $this.find("tr:last-child").addClass("last-child");
        });
    };
 	$("button").button();	
 	$('input').addClass("ui-corner-all");
});

function cancelDefaultAction(e) {
	 var evt = e ? e:window.event;
	 if (evt.preventDefault) evt.preventDefault();
	 evt.returnValue = false;
	 return false;
}

function collectFormData(fields) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		data[$item.attr('name')] = $item.val(); 
	}
	return data;
}

function addInputError(fieldName, message){
	var $controlGroup = $(fieldName);
	$controlGroup.find('.control-label').addClass('error');
	$controlGroup.find('.help-inline').addClass('error');
	$controlGroup.find('.help-inline').append(message);			
}

function removeInputError(formName){
	var $form = $(formName);	
	$form.find('.control-label').removeClass('error');
	$form.find('.help-inline').removeClass('error');
	$form.find('.help-inline').empty();
	$form.find('.alert').remove();		
}

function formAjaxSubmit(formName, validateUrl, successMethod, failMethod){
	var $form = $(formName);
	var $inputs = $form.find('input');
	var data = collectFormData($inputs);				
	$.post(validateUrl, data, function(response) {
		removeInputError(formName);
		if (response.status == 'FAIL') {
			for (var i = 0; i < response.result.length; i++) {
				var item = response.result[i];
				addInputError("#div_" + item.fieldName, item.message+"<br/>");								
			}
			failMethod(response);
		} else {
			successMethod();
		}
	}, 'json');
}

function enableInput(input){
	input.button('enable'); 
}

function disableInput(input){
	input.button('disable'); 
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

function deleteJQGridRow(grid, rowId){
	grid.jqGrid('delRowData',rowId);
}

