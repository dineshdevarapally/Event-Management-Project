var table = $('#users').DataTable();

$.ajax({
   type: 'POST',
   url: 'GetEmployees',
   data: '',
   success: function (data) {
	   if(data=='Please Login to Continue'){
				  window.location.href="index.html";
		  }
       var data = $.parseJSON(data);
       console.log(data);
	   for (var i = 0; i < Object.keys(data).length; i++) {
    	   table.row.add( [ (i+1),
    		   data[i].username,
    		   data[i].firstname,
    		   data[i].lastname,
    		   data[i].contact,
    		   data[i].dob,
    		   data[i].role,
    		   data[i].gender,
    		   '<div style="width: 180px;"><input type="hidden" class="password" value='+data[i].password+'><input type="hidden" class="userId" value='+data[i].id+'><button type="button" class="btn btn-sm btn-info update">Update</button> <button type="button" class="btn btn-sm btn-danger delete">Delete</button> <button type="button" class="btn btn-sm btn-warning reset">Reset</button></div>']).draw(false);
       } 
   }        
});
 

var reset;
$(document.body).on('click','.reset', function(){
	reset=$(this);
  $('input[name="passwordChange"]').val($(this).parent().find('.userId').val());  
  $("#passwordModal").modal('show');
});

$(document.body).on('click','input[name="changePassword"]', function(){
  var oldPass = $('input[name="passwordChange"]').val();
  var newPass = $('input[name="passwordNew"]').val(); 
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
    alert('Please Enter New Password');
    return false;
  }else{

    $.ajax({
    type: 'POST',
    url: 'EditUser',
    data: {firstname:firstname,lastname:lastname,contact:phone,password:newPass,username:username,gender:gender,dob:dob,id:oldPass},
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

$("#dob").datetimepicker({format: 'YYYY-MM-DD hh:mm:ss',minDate: new Date(), useCurrent: false});

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
    alert('Enter Date of Birth');
    return false;
  }else if(gender==''){
    alert('Select Gender');
    return false;
  }else if(password==''){
    alert('Enter Password');
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
    alert('Enter Date of Birth');
    return false;
  }else if(gender==''){
    alert('Select Gender');
    return false;
  }else if(password==''){
    alert('Enter Password');
    return false;
  }else{
    $.ajax({
    type: 'POST',
    url: 'AddUser',
    data: {role:'operator',firstname:firstname,lastname:lastname,contact:phone,password:password,username:username,gender:gender,dob:dob},
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
            alert(data);
          }
      }   
  });
});