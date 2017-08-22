$.ajax({
	type: 'POST',
	url: 'rest/RestApis/Initialize',
	success: function (data) {
		if($.trim(data)=='success'){
			console.log(data);
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
$(document.body).on('click','#login', function(){
  var username = $('input[name="username"]').val(),
  password = $('input[name="password"]').val();

  if(username==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Enter Username',
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
     url: 'rest/RestApis/LoginServlet',
     data: {username:username,password:password},
       success: function (data) {
           if($.trim(data)=='success'){
        	   
             window.location.href='dashboard.html';  
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