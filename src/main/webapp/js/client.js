var table = $('#users').DataTable();

$.ajax({
	  type: 'POST',
	  url: 'rest/RestApis/GetProfileDetails',
	  data: '',
	  success: function (data) {
	    var data = $.parseJSON(data);

	      if(data.role=='operator'){
	          $('#side-menu li').eq(1).remove();
	      }
	    
	  }        
});

$.ajax({
  type: 'POST',
  url: 'rest/RestApis/GetClients',
  data: '',
  success: function (data) {
	  if(data=='Please Login to Continue'){
			  window.location.href="index.html";
	  }
    var data = $.parseJSON(data);
    console.log(data);
      for (var i = 0; i < Object.keys(data).length; i++) {
         table.row.add( [ (i+1),
           data[i].firstname,
           data[i].lastname,
           data[i].phonenumber,
           data[i].address,
           '<div><input type="hidden" class="clientId" value='+data[i].id+'><button type="button" class="btn btn-info update"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button> <button type="button" class="btn btn-danger delete"><i class="fa fa-trash" aria-hidden="true"></i></button></div>']).draw(false);
      }
  }        
});


$(document.body).on('click','.updateProfile', function(){
	  var firstname = $('#firstname').val();
	  var lastname =  $('#lastname').val();
	  var phone = $('#phone').val();
	  var username = $('#address').val();
	  var id = $('#clientId').val()
	  if(firstname==''){
		  $.confirm({
			    title: 'Error!',
			    content: 'First Name Should not be empty',
			    type: 'red',
			    typeAnimated: true,
			    buttons: {
			        tryAgain: {
			            text: 'Ok',
			            btnClass: 'btn-red',
			            action: function(){
			            }
			        }
			    }
			});
	    return false;
	  }else if(lastname==''){
		  $.confirm({
			    title: 'Error!',
			    content: 'Last Name Should not be empty',
			    type: 'red',
			    typeAnimated: true,
			    buttons: {
			        tryAgain: {
			            text: 'Ok',
			            btnClass: 'btn-red',
			            action: function(){
			            }
			        }
			    }
			});
	    return false;
	  }else if(phone==''){
		  $.confirm({
			    title: 'Error!',
			    content: 'Phone Number Should not be empty',
			    type: 'red',
			    typeAnimated: true,
			    buttons: {
			        tryAgain: {
			            text: 'Ok',
			            btnClass: 'btn-red',
			            action: function(){
			            }
			        }
			    }
			});
	    return false;
	  }else if(phone.length!==10 || isNaN(phone)){
		  $.confirm({
			    title: 'Error!',
			    content: 'Enter Vaild Phone Number',
			    type: 'red',
			    typeAnimated: true,
			    buttons: {
			        tryAgain: {
			            text: 'Ok',
			            btnClass: 'btn-red',
			            action: function(){
			            }
			        }
			    }
			});
	    return false;
	  }else if(username==''){
		  $.confirm({
			    title: 'Error!',
			    content: 'Enter Address',
			    type: 'red',
			    typeAnimated: true,
			    buttons: {
			        tryAgain: {
			            text: 'Ok',
			            btnClass: 'btn-red',
			            action: function(){
			            }
			        }
			    }
			});
	    return false;
	  }else{
    $.ajax({
    type: 'POST',
    url: 'rest/RestApis/EditClient',
    data: {firstname:firstname,lastname:lastname,phonenumber:phone,address:username,id:id},
      success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
        	  $.confirm({
      		    title: 'Error!',
      		    content: data,
      		    type: 'red',
      		    typeAnimated: true,
      		    buttons: {
      		        tryAgain: {
      		            text: 'Ok',
      		            btnClass: 'btn-red',
      		            action: function(){
      		            }
      		        }
      		    }
      		});
          }
      }        
    });
  }
});

$(document.body).on('click','#addUser', function(){       
  $('#userModal .modal-title').text('');
  $('#userModal .modal-title').text('Add Client');
  $('#userModal input').val('');
  $('.updateProfile').removeClass('updateProfile').addClass('adduser').text('Add');
  $("#userModal").modal('show');
});

$(document.body).on('click','.update', function(){ 
  $('#firstname').val($(this).parent().parent().parent().find('td').eq(1).text());
  $('#lastname').val($(this).parent().parent().parent().find('td').eq(2).text());
  $('#address').val($(this).parent().parent().parent().find('td').eq(4).text());
  $('#phone').val($(this).parent().parent().parent().find('td').eq(3).text());
  console.log($(this).parent().find('.clientId').val());
  $('#clientId').val($(this).parent().find('.clientId').val());
  $('#userModal .modal-title').text('');
  $('#userModal .modal-title').text('Update Client');
  $('.addUser').removeClass('addUser').addClass('updateProfile').text('Update');
  $("#userModal").modal('show');
});

$(document.body).on('click','.addUser', function(){

  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#address').val();
  if(firstname==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'First Name Should not be empty',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        tryAgain: {
		            text: 'Ok',
		            btnClass: 'btn-red',
		            action: function(){
		            }
		        }
		    }
		});
    return false;
  }else if(lastname==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Last Name Should not be empty',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        tryAgain: {
		            text: 'Ok',
		            btnClass: 'btn-red',
		            action: function(){
		            }
		        }
		    }
		});
    return false;
  }else if(phone==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Phone Number Should not be empty',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        tryAgain: {
		            text: 'Ok',
		            btnClass: 'btn-red',
		            action: function(){
		            }
		        }
		    }
		});
    return false;
  }else if(phone.length!==10 || isNaN(phone)){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Vaild Phone Number',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        tryAgain: {
		            text: 'Ok',
		            btnClass: 'btn-red',
		            action: function(){
		            }
		        }
		    }
		});
    return false;
  }else if(username==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Address',
		    type: 'red',
		    typeAnimated: true,
		    buttons: {
		        tryAgain: {
		            text: 'Ok',
		            btnClass: 'btn-red',
		            action: function(){
		            }
		        }
		    }
		});
    return false;
  }else{
    $.ajax({
      type: 'POST',
      url: 'rest/RestApis/AddClient',
      data: {firstname:firstname,lastname:lastname,phonenumber:phone,address:username},
        success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
        	  $.confirm({
      		    title: 'Error!',
      		    content: data,
      		    type: 'red',
      		    typeAnimated: true,
      		    buttons: {
      		        tryAgain: {
      		            text: 'Ok',
      		            btnClass: 'btn-red',
      		            action: function(){
      		            }
      		        }
      		    }
      		});
          }
        }         
    });
  }
}); 

$(document.body).on('click','.delete', function(){
  var username = $(this).parent().find('.clientId').val();
  $.confirm({
	    title: 'Are you sure?',
	    content: 'All the associated data for the client including events will be deleted',
	    type: 'red',
	    typeAnimated: true,
	    buttons: {
	        tryAgain: {
	            text: 'Ok',
	            btnClass: 'btn-red',
	            action: function(){
	            	  $.ajax({
	            		  type: 'POST',
	            		  url: 'rest/RestApis/DeleteClient',
	            		  data: {id:username},
	            		    success: function (data) {
	            		        console.log(data);
	            		        if($.trim(data)=='success'){
	            		          location.reload();
	            		        }else{
	            		        	$.confirm({
	            		    		    title: 'Error!',
	            		    		    content: data,
	            		    		    type: 'red',
	            		    		    typeAnimated: true,
	            		    		    buttons: {
	            		    		        tryAgain: {
	            		    		            text: 'Ok',
	            		    		            btnClass: 'btn-red',
	            		    		            action: function(){
	            		    		            }
	            		    		        }
	            		    		    }
	            		    		});
	            		        }
	            		    }   
	            		  });
	            }
	        }
	    }
	});
});