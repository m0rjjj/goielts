$(function() {
    $( "a[data-confirm]" ).on("click", function(ev) {
		var url = $(this).attr('href');
		var type = $(this).data('type');
		var confirmText = $(this).data('confirm');
		var id = $(this).data('id');
		var deleteSelectAttr = $(this).data('delidsel');
		//console.log(confirmText);
		var data = {
				url:url,
				type:type,
				confirmText:confirmText,
				id:id,
				deleteSelectAttr:deleteSelectAttr
			};
		console.log(data)
		switch (type){
		case 'delete' :
			modalFactory.loadModal('delete',data);
			break;
		default :
			
			break;
		}
//		if (!$('#myModal').length) {
//			$('body').append('<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Please Confirm</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button><a class="btn btn-primary" id="dataConfirmOK">OK</a></div></div>');
//		}
//		$('#myModal').find('.modal-body').text($(this).attr('data-confirm'));
//		$('#dataConfirmOK').attr('href', href);
//		$('#myModal').modal({show:true});
		return false;
	});
    
    $('body').on('click','#deleteModal .delete_button',function(){
    	var url = $('#deleteModal .delete_button').data('url');
    	var id = $('#deleteModal .delete_button').data('id');
    	var deleteSelectAttr = $('#deleteModal .delete_button').data('delidsel')?$('#deleteModal .delete_button').data('delidsel'):'#row_';
    	$.ajax({
    		method:'post',
    		url:url,
    		data:{_csrf: _csrf.token},
    		success:function(msg){
    			if(msg.success){
    				alertFactory.success('Row was deleted');
    				console.log(deleteSelectAttr+id)
    				$(deleteSelectAttr+id).slideUp(function(){
    					$(deleteSelectAttr+id).remove();
    				});
    			}else{
    				alertFactory.error('There was an error while deleting the row');
    			}
    			$('#deleteModal').modal('hide');
    		}
    	});
    });
    
    $(".toggle_comment").on("click",'.comment_container',function(e){
    	$(this).removeClass=""
    });
});

var modalFactory = {
	loadModal : function(type, data){
		switch (data.type) {
		case 'delete':
			$('#deleteModal').remove();
			
			$('body').append('\
			<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">\
			  <div class="modal-dialog" role="document">\
			    <div class="modal-content">\
			      <div class="modal-header">\
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
			        <h4 class="modal-title" id="myModalLabel">Delete</h4>\
			      </div>\
			      <div class="modal-body">' + data.confirmText + '</div>\
			      <div class="modal-footer">\
			        <button type="button" class="btn btn-danger delete_button" data-id="'+data.id+'" data-url="'+data.url+'" data-delselattr="'+data.deleteSelectAttr+'">Delete</button>\
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
			      </div>\
			    </div>\
			  </div>\
			</div>');
			
			$('#deleteModal').modal({'show':true});
		}
	}
}
var alertFactory = {
		error : function(message){
			$('#alerts').append($(
				'<div class="alert alert-danger">\
					<button type="button" class="close" data-dismiss="alert">&times;</button>\
					<h4 class="alert_head">Error</h4>\
					<p class="alert_body">'+message+'<p>\
				</div>')
			);
		},
		success : function(message){
		$('#alerts').append($(
				'<div class="alert alert-success">\
					<button type="button" class="close" data-dismiss="alert">&times;</button>\
					<h4 class="alert_head">Success</h4>\
					<p class="alert_body">'+message+'<p>\
				</div>')
			);
		},
}