$(function() {
    $( "a[data-confirm]" ).on("click", function(ev) {
		var url = $(this).attr('href');
		var type = $(this).data('type');
		var confirmText = $(this).data('confirm');
		var id = $(this).data('id');
		var deleteSelectAttr = $(this).data('delidsel');
		var message = $(this).data('message')
		// console.log(confirmText);
		var data = {
				url:url,
				type:type,
				confirmText:confirmText,
				id:id,
				deleteSelectAttr:deleteSelectAttr,
				message:message
			};
		console.log(data)
		switch (type){
		case 'delete' :
			modalFactory.loadModal('delete',data);
			break;
		default :
			
			break;
		}
// if (!$('#myModal').length) {
// $('body').append('<div id="dataConfirmModal" class="modal" role="dialog"
// aria-labelledby="dataConfirmLabel" aria-hidden="true"><div
// class="modal-header"><button type="button" class="close" data-dismiss="modal"
// aria-hidden="true">×</button><h3 id="dataConfirmLabel">Please
// Confirm</h3></div><div class="modal-body"></div><div
// class="modal-footer"><button class="btn" data-dismiss="modal"
// aria-hidden="true">Cancel</button><a class="btn btn-primary"
// id="dataConfirmOK">OK</a></div></div>');
// }
// $('#myModal').find('.modal-body').text($(this).attr('data-confirm'));
// $('#dataConfirmOK').attr('href', href);
// $('#myModal').modal({show:true});
		return false;
	});
    
    $('body').on('click','#deleteModal .delete_button',function(){
    	var url = $('#deleteModal .delete_button').data('url');
    	var id = $('#deleteModal .delete_button').data('id');
    	var message = $('#deleteModal .delete_button').data('message');
    	message = message?message:'Row was successfully deleted';
    	var deleteSelectAttr = $('#deleteModal .delete_button').data('delidsel')?$('#deleteModal .delete_button').data('delidsel'):'#row_';
    	$.ajax({
    		method:'post',
    		url:url,
    		data:{_csrf: _csrf.token},
    		success:function(msg){
    			if(msg.success){
    				alertFactory.success(message);
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
    
    $('.comment_container').on("click",".toggle_comment",function(e){
    	var toggleButton = $(this);
    	var commentContainer = $(this).parents(".comment_container");
    	var commentBlock = commentContainer.find(".comment_block");
    	if(toggleButton.hasClass("fa-minus")){
    		toggleButton.removeClass("fa-minus").addClass("fa-plus");
    		commentBlock.slideUp();
    	}else{
    		toggleButton.removeClass("fa-plus").addClass("fa-minus");
    		commentBlock.slideDown();
    	}
    });
    $('.comment_container').on("click",".submit_comment",function(e){
    	var submitButton = $(this);
    	var commentContainer = $(this).parents(".comment_container");
    	var commentCount = commentContainer.find(".comment_count");
    	var commentBlock = commentContainer.find(".comment_block");
    	var commentList = commentContainer.find(".comment_list");
    	var formGroup = commentBlock.find(".form-group")
    	var textarea = formGroup.find(".comment_message");
    	var errorBlock = formGroup.find(".error_block")
    	if(textarea.val()==""){
    		formGroup.addClass("has-error");
    		errorBlock.text("Comment message could not be empty");
    		errorBlock.slideDown();
    	}else{
    		formGroup.removeClass("has-error");
    		errorBlock.slideUp();
    		
    		var data = {
    			weekId:submitButton.data("weekId"),
    			message:textarea.val(),
    			_csrf: _csrf.token
    		};
    		$.post("/comment/add",data,function(msg){
    			if(msg.success){
    				alertFactory.success("Comment was successfully added");
    				textarea.val("");
    				var comment = $("<li></li>").addClass("media").hide().append(
    					$("<div></div>").addClass("media-body")
    					.append($("<h4></h4>").addClass("media-heading").text(msg.data.user))
    					.append($("<p></p>").text(msg.data.message))
    				)
    				commentList.append(comment);
    				comment.slideDown();
    				commentCount.text(parseInt(commentCount.text())+1);
    				
    				/*
					 * <li class="media"> <div class="media-body">
					 * <h4 class="media-heading">Joseph</h4> <p>Cras sit amet
					 * nibh libero, in gravida nulla. Nulla vel metus
					 * scelerisque ante sollicitudin commodo. Cras purus odio,
					 * vestibulum in vulputate at, tempus viverra turpis.</p>
					 * </div></li>
					 */
    			}
    		})
    	}
    	
    	
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
			        <button type="button" class="btn btn-danger delete_button" data-id="'+data.id+'" data-url="'+data.url+'" data-delselattr="'+data.deleteSelectAttr+'" data-message="'+data.message+'">Delete</button>\
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


tinymce.init({
	  selector: "textarea.editor",
	  height: 500,
	  plugins: [
	    "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
	    "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
	    "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
	  ],

	  toolbar1: "newdocument | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
	  toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
	  toolbar3: "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",

	  menubar: false,
	  toolbar_items_size: 'small',

	  style_formats: [{
	    title: 'Bold text',
	    inline: 'b'
	  }, {
	    title: 'Red text',
	    inline: 'span',
	    styles: {
	      color: '#ff0000'
	    }
	  }, {
	    title: 'Red header',
	    block: 'h1',
	    styles: {
	      color: '#ff0000'
	    }
	  }, {
	    title: 'Example 1',
	    inline: 'span',
	    classes: 'example1'
	  }, {
	    title: 'Example 2',
	    inline: 'span',
	    classes: 'example2'
	  }, {
	    title: 'Table styles'
	  }, {
	    title: 'Table row 1',
	    selector: 'tr',
	    classes: 'tablerow1'
	  }],

	  templates: [{
	    title: 'Test template 1',
	    content: 'Test 1'
	  }, {
	    title: 'Test template 2',
	    content: 'Test 2'
	  }],
	  content_css: [
	    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
	    '//www.tinymce.com/css/codepen.min.css'
	  ]
	});

tinymce.init({
	  selector: 'textarea.editor-small',
	  height: 200,
	  plugins: [
	    'advlist autolink lists link image charmap',
	    'insertdatetime media'
	  ],
	  toolbar: 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
	  content_css: [
	    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
	    '//www.tinymce.com/css/codepen.min.css'
	  ]
	});