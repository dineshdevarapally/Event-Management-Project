$.ajax({
  type: 'POST',
  url: 'GetProfileDetails',
  data: '',
  success: function (data) {
	  if($.trim(data)=='Please Login to Continue'){
			  window.location.href="index.html";
	  }
    var data = $.parseJSON(data);
    console.log(data);
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

$("#dob").datetimepicker({format: 'YYYY-MM-DD hh:mm:ss',minDate: new Date(), useCurrent: false});


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
    alert('Please Enter Old Password');
    return false;
  }else if(newPass==''){
    alert('Please Enter New Password');
    return false;
  }else if(passwordOld!==oldPass){
    alert('Old Password Not Matching');
    return false;
  }else{

    $.ajax({
    type: 'POST',
    url: 'EditUser',
    data: {firstname:firstname,lastname:lastname,contact:phone,password:newPass,username:username,gender:gender,dob:dob,id:id},
      success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
            alert(data);
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
    alert('First Name Should not be empty');
    return false;
  }else if(lastname==''){
    alert('Last Name Should not be empty');
    return false;
  }else if(phone==''){
    alert('Phone Number Should not be empty');
    return false;
  }else if(phone.length!==10 || isNaN(phone)){
    alert('Enter Valid Number');
    return false;
  }else if(dob==''){
    alert('Date of Birth should not be empety');
    return false;
  }else if(gender==''){
    alert('Select gender');
    return false;
  }else{
    $.ajax({
    type: 'POST',
    url: 'EditUser',
    data: {firstname:firstname,lastname:lastname,contact:phone,password:password,username:username,gender:gender,dob:dob,id:id},
      success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
            alert(data);
          }
      }        
    });
  }
});