// $.ajax({
//   type: 'POST',
//   url: 'utils/getuserdetails.php',
//   data: '',
//   success: function (data) {
//     var data = $.parseJSON(data);
//     console.log(data);
//     if($.trim(data)!=='F'){
//       $('#firstname').val(data[0].firstname);
//       $('#lastname').val(data[0].lastname);
//       $('#username').val(data[0].username);
//       $('#phone').val(data[0].phonenumber);
//       $('#role').val(data[0].role);
//       if(data[0].role=='operator'){
//           $('.navBarList li').eq(1).remove();
//           $.ajax({
//             type: 'POST',
//             url: 'utils/getuserannouncements.php',
//             data: '',
//             success: function (data) {
//               if($.trim(data)!=='F'){
//                 var data = $.parseJSON(data);
//                 console.log(data);
//                 $('.contentBlock').append('');
//                 var query=''
//                 for (var i = 0; i < data.length; i++) {
//                   query+=' Announcement - '+(i+1)+': '+data[i].content+'.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
//                 }
//                 $('.contentBlock').append('<marquee>'+query+'</marquee>');
//               }else{
//                 alert('Server Error');
//               }
              
//             }        
//           });
//       }  
//     }else{
//       alert('Server Error');
//     }
    
//   }        
// });



$(document.body).on('click','.changePassword', function(){       
  $('#passwordModal input[name="passwordChange"]').val('');
  $('#passwordModal input[name="passwordNew"]').val('');
  $('#passwordModal input[name="passwordConfirm"]').val('');
  $("#passwordModal").modal('show');
});

$(document.body).on('click','button[name="changePassword"]', function(){
  var oldPass = $('input[name="passwordChange"]').val();
  var newPass = $('input[name="passwordNew"]').val();     
  var confirmPass = $('input[name="passwordConfirm"]').val();
  
  if(oldPass==''){
    alert('Please Enter Old Password');
    return false;
  }else if(newPass==''){
    alert('Please Enter New Password');
    return false;
  }else if(confirmPass==''){
    alert('Please Confirm New Password');
    return false;
  }if(newPass!==confirmPass){
    alert('New Password and Confirm password Should Match');
    return false;
  }else{

    $.ajax({
    type: 'POST',
    url: 'utils/updatepassword.php',
    data: {old_password:oldPass,new_password:newPass},
      success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
            alert('Failed to Update');
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
  if(firstname==''){
    alert('First Name Should not be empty');
    return false;
  }else if(lastname==''){
    alert('Last Name Should not be empty');
    return false;
  }else if(phone==''){
    alert('Phone Number Should not be empty');
    return false;
  }else{
    $.ajax({
    type: 'POST',
    url: 'utils/updatestaff.php',
    data: {firstname:firstname,lastname:lastname,phonenumber:phone,username:username},
      success: function (data) {
          console.log(data);
          if($.trim(data)=='success'){
            location.reload();
          }else{
            alert('Failed to Update');
          }
      }        
    });
  }
});