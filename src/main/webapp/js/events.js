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

$("#eventDateTime").datetimepicker({format: 'YYYY-MM-DD hh:mm:ss',minDate: new Date(), useCurrent: false});

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
    var clientname='';
      for (var i = 0; i < Object.keys(data).length; i++) {
        clientname+='<option value="'+data[i].id+'">'+data[i].firstname+'</option>'
      }
      $('.clientname').html('').append(clientname)
  }        
});

$.ajax({
  type: 'POST',
  url: 'rest/RestApis/GetEvents',
  data: '',
  success: function (data) {
	  if(data=='Please Login to Continue'){
			  window.location.href="index.html";
	  }
    var data = $.parseJSON(data);
    console.log(data);
      for (var i = 0; i < Object.keys(data).length; i++) {
    	  if(data[i].nGen=='100'){
    		  data[i].nGen='Low';
    	  }
    	  else if(data[i].nGen=='1000'){
    		  data[i].nGen='Medium';
    	  }
    	  else if(data[i].nGen=='10000'){
    		  data[i].nGen='High';
    	  }
         table.row.add( [ (i+1),
           data[i].eventName,
           data[i].clientName,
           data[i].eventDateTime,
           data[i].seatsInTable,
           data[i].emptySeatsInTable,
           data[i].nGen,
           data[i].score,
           '<div><input type="hidden" class="id" value='+data[i].eventId+'> <a href="javascript:void(0)" class="btn downloadRules"><i class="fa fa-download" aria-hidden="true"></i> Seating Plan </a> <a href="javascript:void(0)" class="btn downloadList"><i class="fa fa-download" aria-hidden="true"></i> Place Cards </a> <button type="button" class="btn btn-danger delete"><i class="fa fa-trash" aria-hidden="true"></i></button></div>']).draw(false);
      }
  }        
});

$(document.body).on('click','#addEvents', function(){       
  $('#userModal .modal-title').text('');
  $('#userModal .modal-title').text('Add Event');
  $('#userModal input').val('');
  $("#userModal").modal('show');
});

function processSelectedFiles(fileInput) {
  var files = fileInput.files;
  var arrExt = ['csv']
  for (var i = 0; i < files.length; i++) {
    var ext = files[i].name.split('.').pop().toLowerCase();
    
    if($.inArray(ext, arrExt) == -1) {
      $.confirm({
        title: 'Error!',
        content: 'File Format Should be in CSV Only',
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
      $('input[name="importGuestList"]').val('');
      return false;
    }
  }
}

$(document.body).on('click','.addEvent', function(){

  var firstname = $('.eventname').val();
  var lastname =  $('.clientname').val();
  var phone = $('.eventDateTime').val();
  var username = $('#eventVenue').val();
  var seatsInTable = $('.seatsInTable').val();
  var emptySeatsInTable = $('.emptySeatsInTable').val();
  var nGen = $('.nGen').val();
  if(firstname==''){
	  $.confirm({
		    title: 'Error!',
		    content: 'Event Name Should not be empty',
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
        content: 'Select Client',
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
		    content: 'Event Date Should not be empty',
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
		    content: 'Venue Should not be empty',
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
  }else if(seatsInTable==''){
    $.confirm({
        title: 'Error!',
        content: 'Enter Number Seats in Table',
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
  }else if(emptySeatsInTable==''){
    $.confirm({
        title: 'Error!',
        content: 'Enter Number Empty Seats in Table',
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
  }else if(nGen==0){
	    $.confirm({
	        title: 'Error!',
	        content: 'Select Priority',
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
    var formdata = new FormData($("#register")[0]);
    $.ajax({
      type: 'POST',
      url: 'rest/RestApis/AddEvent',
      data: formdata,
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
        },
        cache: false,
        contentType: false,
        processData: false        
    });
  }
}); 

$(document.body).on('click','.delete', function(){
  var eventId = $(this).parent().find('.id').val();
  $.ajax({
  type: 'POST',
  url: 'rest/RestApis/DeleteEvent', 
  data: {eventId:eventId},
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

$(document.body).on('click','.downloadList', function(){
	  var eventId = $(this).parent().find('.id').val();
	  $.ajax({
	  type: 'POST',
	  url: 'rest/RestApis/GetOutput', 
	  data: {eventId:eventId},
	    success: function (data) {

	        if($.trim(data)=='Unable to Get Output Data'){
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
	        }else{
	        	var data = $.parseJSON(data);
	            console.log(data);
	            var doc = new jsPDF();
	            
	              for (var i = 0; i < Object.keys(data).length; i++) {
	            	  doc.rect(30, 70, doc.internal.pageSize.width - 60, doc.internal.pageSize.height - 140, 'S');
	            	  doc.setFontSize(25);
		        	    doc.text('Person Name:'+data[i].personName,35, 100 );

		        	    doc.setFontSize(16)

		        	    doc.text( 'Table Number:'+data[i].tableNumber,35, 125);
		        	    if(i<(Object.keys(data).length-1)){
			        	    doc.addPage()		        	    	
		        	    }
	              }
	        	  
	        	    doc.save('Test.pdf');

	        	}
	        }
	  });
	});

$(document.body).on('click','.downloadRules', function(){
	  var eventId = $(this).parent().find('.id').val();
	  $.ajax({
	  type: 'POST',
	  url: 'rest/RestApis/GetOutput', 
	  data: {eventId:eventId},
	    success: function (data) {

	        if($.trim(data)=='Unable to Get Output Data'){
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
	        }else{
	        	var data = $.parseJSON(data);
	            var columns = ["Sr.No", "Person name", "Table Number"];
	            
	            var tableData = [];
	            for (var i = 0; i < Object.keys(data).length; i++) {
	            	tableData.push([i+1, data[i].personName, data[i].tableNumber]);
	            }
	             var doc = new jsPDF('p', 'pt');
	             doc.autoTable(columns, tableData);
	             doc.save("table.pdf");
	            
//	            var doc = new jsPDF('p', 'pt', 'a4');
//	            var specialElementHandlers = {
//		             '#editor': function(element, renderer){
//		                 return true;
//		             }
//		         };
//
//
//
//		         doc.fromHTML($('#downlaodPDF').get(0), 15, 15, {
//	
//		             'width': 170, 
//	
//		             'elementHandlers': specialElementHandlers
//	
//		         });
//	           doc.save('test.pdf');
	         }
	        }
	  });
	});