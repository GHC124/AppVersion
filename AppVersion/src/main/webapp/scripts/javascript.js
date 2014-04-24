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

function disableInputs(formName, type){
	var $form = $(formName);
	var $inputs = $form.find('input[type="' + type + '"]');
	for (var i = 0; i < $inputs.length; i++) {
		var $item = $($inputs[i]);
		$item.attr("disabled", "disabled"); 
	}
}

function enableInputs(formName, type){
	var $form = $(formName);
	var $inputs = $form.find('input[type="' + type + '"]');
	for (var i = 0; i < $inputs.length; i++) {
		var $item = $($inputs[i]);
		$item.removeAttr("disabled"); 
	}
}

/* JQGrid*/
function refreshJQGrid(listId){
	$(listId).setGridParam({ page: 1, datatype: "json" }).trigger('reloadGrid');
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

function addDeleteAction2JQGrid(grid, valueCol, actionCol, deleteLabel, deleteAction){
	var iCol = getJQGridColumnIndexByName(grid, actionCol) + 1;
	var vCol = getJQGridColumnIndexByName(grid, valueCol) + 1;
	if(iCol == -1 || vCol == -1){
		return;
	}
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child("+iCol+")")
        .each(function() {
        	$(this).html("");
        	var divDelete = 
        	$("<div></div>",
                {
                    title: deleteLabel,
                    mouseover: function() {
                        $(this).addClass('ui-state-hover');
                    },
                    mouseout: function() {
                        $(this).removeClass('ui-state-hover');
                    },
                    click: function(e) {
                    	var id = $(e.target).closest("tr.jqgrow").attr("id"); 
                    	var value = $(e.target).closest("tr.jqgrow").children("td:nth-child("+vCol+")").html();  
                        deleteAction(id, value);
                        return cancelDefaultAction(e);
                    }
                }
              ).css({"margin-left": "5px", float:"left"})
               .addClass("ui-pg-div ui-inline-custom")
               .append('<span class="ui-icon ui-icon-trash"></span>');
             $(this).append(divDelete);
    });
}

