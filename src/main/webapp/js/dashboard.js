$.ajax({
  type: 'POST',
  url: 'rest/RestApis/GetProfileDetails',
  data: '',
  success: function (data) {
	  if($.trim(data)=='Please Login to Continue'){
			  window.location.href="index.html";
	  }
	  var data = $.parseJSON(data);
      $('#firstname').val(data.firstname);
      $('#lastname').val(data.lastname);
      $('#username').val(data.username);
      $('#phone').val(data.contact);
      $('#role').val(data.role);
      $('#userId').val(data.id);
      $('#passwordold').val(data.password);
      $('#dob').val(data.dob);
      if(data.gender=='Male'){
    	  $('input[name="gender"]').eq(0).prop('checked',true)  
      }else if(data.gender=='Female'){
    	  $('input[name="gender"]').eq(1).prop('checked',true)
      }
      if(data.role=='operator'){
          $('#side-menu li').eq(1).remove();
      }
    
  }        
});

$("#dob").datetimepicker({format: 'YYYY-MM-DD',maxDate: new Date(), useCurrent: false});


$(document.body).on('click','.changePassword', function(){       
  $('#passwordModal input[name="passwordChange"]').val('');
  $('#passwordModal input[name="passwordNew"]').val('');
  $('#passwordModal input[name="passwordConfirm"]').val('');
  $("#passwordModal").modal('show');
});

$(document.body).on('click','button[name="changePassword"]', function(){
  var oldPass = $('input[name="passwordChange"]').val();
  var newPass = $('input[name="passwordNew"]').val();     
  var passwordOld = $('#passwordold').val();
  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#username').val();
  var dob = $('#dob').val();
  var gender = $('input[name="gender"]:checked').val();
  var id = $('#userId').val();
  
  if(oldPass==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Please Enter Old Password',
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
  }else if(newPass==''){
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
  }else if(passwordOld!==oldPass){
	  $.confirm({
		    title: 'Error!',
		    content: 'Old Password Not Matching',
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
    data: {firstname:firstname,lastname:lastname,contact:phone,password:newPass,username:username,gender:gender,dob:dob,id:id},
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
$(document.body).on('click','.updateProfile', function(){
  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#username').val();
  var dob = $('#dob').val();
  var gender = $('input[name="gender"]:checked').val();
  var id = $('#userId').val();
  var password = $('#passwordold').val();
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
		    content: 'Date of Birth should not be empty',
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