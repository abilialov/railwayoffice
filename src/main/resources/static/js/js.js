
$(document).ready(function(){



$('.delete_station').on('click', function(e){

    e.preventDefault();

    var st_id = $(this).attr('id')
    var cid = st_id.substring(3);

    $('#dialog-confirm').dialog({
      resizable: false,
      height: 'auto',
      width: 400,
      modal: true,
      buttons: {
        'Yes':function() {
                            $(this).dialog('close');
                             $('#form_id_'+cid).submit();
                          },

        'No': function() {
          $(this).dialog('close');
        }
      }
    });

});






$('#submit_buy_ticket').on('click', function(e){

    e.preventDefault();

    $('#dialog-confirm').dialog({
      resizable: false,
      height: 'auto',
      width: 400,
      modal: true,
      buttons: {
        'Yes':function() {
                            $(this).dialog('close');
                            $('#buy_ticket_form').submit();
                          },

        'No': function() {
          $(this).dialog('close');
        }
      }
    });

	});


});