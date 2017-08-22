$(document.body).on('click','#login', function(){
  var username = $('input[name="username"]').val(),
  password = $('input[name="password"]').val();

  if(username==''){
    alert('Enter Username');
    return false;
  }else if(password==''){
    alert('Enter Password');
    return false;
  }else{
	 $.ajax({
     type: 'POST',
     url: 'Login',
     data: {username:username,password:password},
       success: function (data) {
           console.log(data);
           if($.trim(data)=='success'){
             window.location.href='dashboard.html';  
           }else{
             alert(data);
           }
       }
   });
  }
});