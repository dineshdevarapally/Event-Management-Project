var table = $('#users').DataTable();

$.ajax({
   type: 'POST',
   url: 'rest/RestApis/GetEmployees',
   data: '',
   success: function (data) {
	   if(data=='Please Login to Continue'){
				  window.location.href="index.html";
		  }
       var data = $.parseJSON(data);
       console.log(data);
	   for (var i = 0; i < Object.keys(data).length; i++) {
		   if(data[i].role=='manager'){
			   table.row.add( [ (i+1),
	    		   data[i].username,
	    		   data[i].firstname,
	    		   data[i].lastname,
	    		   data[i].dob,
	    		   data[i].contact,
	    		   data[i].role,
	    		   data[i].gender,
	    		   '<div style="width: 180px;"><input type="hidden" class="password" value='+data[i].password+'><input type="hidden" class="userId" value='+data[i].id+'><button type="button" class="btn btn-info update"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button> <button type="button" class="btn btn-warning reset"><i class="fa fa-refresh" aria-hidden="true"></i></button></div>']).draw(false);
		   }else{
			   table.row.add( [ (i+1),
	    		   data[i].username,
	    		   data[i].firstname,
	    		   data[i].lastname,
	    		   data[i].dob,
	    		   data[i].contact,
	    		   data[i].role,
	    		   data[i].gender,
	    		   '<div style="width: 180px;"><input type="hidden" class="password" value='+data[i].password+'><input type="hidden" class="userId" value='+data[i].id+'><button type="button" class="btn btn-info update"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button> <button type="button" class="btn btn-danger delete"><i class="fa fa-trash" aria-hidden="true"></i></button> <button type="button" class="btn btn-warning reset"><i class="fa fa-refresh" aria-hidden="true"></i></button></div>']).draw(false);
		   }
    	   
       } 
   }        
});
 

var reset;
$(document.body).on('click','.reset', function(){
	reset=$(this);
	$('#userId').val($(this).parent().find('.userId').val());
  $('input[name="passwordChange"]').val($(this).parent().parent().parent().find('td').eq(1).text());  
  $("#passwordModal").modal('show');
});

$(document.body).on('click','input[name="changePassword"]', function(){
  var oldPass = $('input[name="passwordChange"]').val();
  var newPass = $('input[name="passwordNew"]').val(); 
  var confirm = $('#conformresetpassword').val();
  console.log(reset);
  var firstname=($(reset).parent().parent().parent().find('td').eq(2).text());
  var lastname=($(reset).parent().parent().parent().find('td').eq(3).text());
  var username=($(reset).parent().parent().parent().find('td').eq(1).text());
  var phone=($(reset).parent().parent().parent().find('td').eq(5).text());
  var dob=($(reset).parent().parent().parent().find('td').eq(4).text());
  
  if($(reset).parent().parent().parent().find('td').eq(7).text()=='Male'){
	  var gender='Male';  
  }else if($(reset).parent().parent().parent().find('td').eq(7).text()=='Female'){
	  var gender='Female';
  }

  if(newPass==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Please Enter New Password',
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
  }else if(confirm==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Please Enter Confirm Password',
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
}else if(newPass!==confirm){
	  $.confirm({
		    title: 'Error!',
		    content: 'Password not matched',
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
    url: 'rest/RestApis/EditUser',
    data: {firstname:firstname,lastname:lastname,contact:phone,password:newPass,username:username,gender:gender,dob:dob,id:$('#userId').val()},
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

$("#dob").datetimepicker({format: 'YYYY-MM-DD',maxDate: new Date(), useCurrent: false});

$(document.body).on('click','.updateProfile', function(){
  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#username').val();
  var dob = $('#dob').val();
  var password =  $('#password').val();
  var gender = $('input[name="gender"]:checked').val();
  var id = $('#userId').val();
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
		    content: 'Enter Valid Number',
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
  }else if(dob==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Date of Birth',
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
  }else if(gender==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Select Gender',
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
  }else if(password==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Password',
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
    url: 'rest/RestApis/EditUser',
    data: {firstname:firstname,lastname:lastname,contact:phone,password:password,username:username,gender:gender,dob:dob,id:id},
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
  $('#userModal .modal-title').text('Add User');
  $('#username').removeAttr('readonly');
  $('#userModal input[type=text]').val('');
  $('.updateProfile').removeClass('updateProfile').addClass('adduser').text('Add');
  $("#userModal").modal('show');
});

$(document.body).on('click','.update', function(){ 
  $('#firstname').val($(this).parent().parent().parent().find('td').eq(2).text());
  $('#lastname').val($(this).parent().parent().parent().find('td').eq(3).text());
  $('#username').val($(this).parent().parent().parent().find('td').eq(1).text());
  $('#phone').val($(this).parent().parent().parent().find('td').eq(5).text());
  $('#dob').val($(this).parent().parent().parent().find('td').eq(4).text());
  if($(this).parent().parent().parent().find('td').eq(7).text()=='Male'){
	  $('input[name="gender"]').eq(0).prop('checked',true)  
  }else if($(this).parent().parent().parent().find('td').eq(7).text()=='Female'){
	  $('input[name="gender"]').eq(1).prop('checked',true)
  }
  $('#userId').val($(this).parent().find('.userId').val());
  $('#userModal .modal-title').text('');
  $('#userModal .modal-title').text('Update User');
  $('#password').val($(this).parent().find('.password').val());
  $('#username').attr('readonly',true);
  $('.addUser').removeClass('addUser').addClass('updateProfile').text('Update');
  $("#userModal").modal('show');
});

$(document.body).on('click','.addUser', function(){
  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#username').val();
  var dob = $('#dob').val();
  var password =  $('#password').val();
  var gender = $('input[name="gender"]:checked').val();
  
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
		    content: 'Enter Valid Number',
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
  }else if(dob==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Date of Birth',
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
  }else if(gender==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Select Gender',
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
  }else if(password==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Password',
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
    url: 'rest/RestApis/AddUser',
    data: {role:'operator',firstname:firstname,lastname:lastname,contact:phone,password:password,username:username,gender:gender,dob:dob},
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
  var username = $(this).parent().find('.userId').val();
  $.ajax({
    type: 'POST',
    url: 'DeleteUser',
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
});