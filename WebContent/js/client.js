var table = $('#users').DataTable();

$.ajax({
	  type: 'POST',
	  url: 'GetProfileDetails',
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
  url: 'GetClients',
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
           '<div><input type="hidden" class="clientId" value='+data[i].id+'><button type="button" class="btn btn-sm btn-info update">Update</button> <button type="button" class="btn btn-sm btn-danger delete">Delete</button></div>']).draw(false);
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
	    alert('First Name Should not be empty');
	    return false;
	  }else if(lastname==''){
	    alert('Last Name Should not be empty');
	    return false;
	  }else if(phone==''){
	    alert('Phone Number Should not be empty');
	    return false;
	  }else if(phone.length!==10 || isNaN(phone)){
	    alert('Enter Vaild Phone Number');
	    return false;
	  }else if(username==''){
	    alert('Enter Address');
	    return false;
	  }else{
    $.ajax({
    type: 'POST',
    url: 'EditClient',
    data: {firstname:firstname,lastname:lastname,phonenumber:phone,address:username,id:id},
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
  $('#userModal .modal-title').text('Add Customer');
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
  $('#userModal .modal-title').text('Update Customer');
  $('.addUser').removeClass('addUser').addClass('updateProfile').text('Update');
  $("#userModal").modal('show');
});

$(document.body).on('click','.addUser', function(){

  var firstname = $('#firstname').val();
  var lastname =  $('#lastname').val();
  var phone = $('#phone').val();
  var username = $('#address').val();
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
    alert('Enter Vaild Phone Number');
    return false;
  }else if(username==''){
    alert('Enter Address');
    return false;
  }else{
    $.ajax({
      type: 'POST',
      url: 'AddClient',
      data: {firstname:firstname,lastname:lastname,phonenumber:phone,address:username},
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
  var username = $(this).parent().find('.clientId').val();
  $.ajax({
  type: 'POST',
  url: 'DeleteClient',
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