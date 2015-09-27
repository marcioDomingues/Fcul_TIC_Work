   //array para todas cadeiras
   var geral = new Array();

   //array para cadeiras escolhidas
   var myCadeiras = new Array();



function choose($id){
   chooseCadeira($id);
//   if($('#'+$id+'.add').css('display')=="block"){
//     console.log("WEEEEEEEEEEEEEEEEEEE");
//      $('#'+$id+'.add').css('display','none');
//   }
//   if($('#'+$id+',.remove').css('display')=="none"){
//      console.log("WEEEEEEEEEEEEEEEEEEE");
//      $('#'+$id+',.remove').css('display','block');
   //}
}

function removeC($id){                 

   removeCadeira($id);
   
}

//useful
function createCalendar(){
   json="";
   if(myCadeiras.length>0){ 
      var json ='[';
      $.each(myCadeiras,function(i,element){
         
         json = json.concat('{"id":'
            +element.id
            +',"title":"'
            +element.title
            +'","turma":'
            +element.turma
            +',"vagas":'
            +element.vagas
            +',"ects":'
            +element.ects
            +',"ano":'
            +element.ano);

         var $start;
         var $end;

         console.log(element.diaSemana);
         switch(element.diaSemana){

            case '2':
               $start="2013-07-01T"+element.start+".000+00:00";
               $end="2013-07-01T"+element.end+".000+00:00";
            break;
            case '3':
               $start="2013-07-02T"+element.start+".000+00:00";  
               $end="2013-07-02T"+element.end+".000+00:00";       
            break;
            case '4':
               $start="2013-07-03T"+element.start+".000+00:00";
               $end="2013-07-03T"+element.end+".000+00:00";         
            break;
            case '5':
               $start="2013-07-04T"+element.start+".000+00:00";  
               $end="2013-07-04T"+element.end+".000+00:00";       
            break;
            case '6':
               $start="2013-07-05T"+element.start+".000+00:00";   
               $end="2013-07-05T"+element.end+".000+00:00";      
            break;
            default:
            console.log("Erro a ler o dia da semana.");
         }
   //2013-06-02T09:00:00.000+00:00

            json = json.concat(',"start":"'
            +$start
            +'","end":"'
            +$end
            +'","sala":"'
            +element.sala
            +'","docente":"'
            +element.docente
            +'"},');
         
      });
   json=json.slice(0,json.length-1);
      json=json.concat(']');
      console.log(json);
}



   $.ajax({
      type:"POST",
      url:"createCalendar.php",
      dataType:"json",
      data: JSON.stringify(json),
      success: function(msg){console.log(msg);
         $('#calendar').weekCalendar("refresh");
}
   });
}



/*ADDS FROM CADEIRAS.JSON TO GERAL ARRAY ; TRANSFER BETWEEN PHP & JS*/
//NO LEAVE THIS IT'S FINE; FILTER LATER
   function addCadeiras($cadeiras){
         var existe=false;
console.log($cadeiras);
   var res = $cadeiras.split(";");
         //ver se ja existe 
         $.each(res,function(j,cad){
//build object
      var aux =cad.split(",");

      var cadeira = new Object();
      cadeira.id = aux[0];
      cadeira.disciplina = aux[1];
      cadeira.title = aux[2]
      cadeira.turma = aux[3];
      cadeira.vagas = aux[4];
      cadeira.ects = aux[5];
      cadeira.ano = aux[6];
      cadeira.diaSemana = aux[7];
      cadeira.start = aux[8];
      cadeira.end = aux[9];
      cadeira.sala = aux[10];
      cadeira.docente = aux[11];
               //check if exists in geral; if not, add
     
      if(geral.length>0){
         $.each(geral,function(i,elem){
            console.log(elem.id);
            if(elem==cadeira) existe=true;
         });
      }
      if(!existe){geral.push(cadeira);console.log("yeey");}
   });   
         
   }

   /*chooses from geral, adds to myCadeiras*/
   function chooseCadeira($id){
      var exists=false;
      $.each(geral,function(i,elem){
         if(elem.id==$id ){
            $.each(myCadeiras,function(j,element){
               if(elem.id==element.id){exists=true;}
            });
            if(!exists){
            myCadeiras.push(elem);
            createCalendar();
            console.log("worked");
            }
          
         }
      });
   }

/*removes from myCadeiras*/
   function removeCadeira($id){

      $.each(myCadeiras,function(i,elem){
         if(elem.id==$id){   

            var index=myCadeiras.indexOf(elem);
            myCadeiras.splice(index,1);
            //remove from calendar


            
         }
      });
      createCalendar();
   }

$(document).ready(function() {


   var $calendar = $('#calendar');



   $calendar.weekCalendar({
      timeslotsPerHour : 2,
      allowCalEventOverlap : true,
      overlapEventsSeparate: true,
      firstDayOfWeek : 1,
      businessHours :{start: 8, end: 21, limitDisplay: true },
      daysToShow : 5,
      height : function($calendar) {
         return $(window).height() - $("h1").outerHeight() - 1;
      },
      eventRender : function(calEvent, $event) {
         if (calEvent.end.getTime() < new Date().getTime()) {
            $event.css("backgroundColor", "#");
            $event.find(".wc-time").css({
               "backgroundColor" : "#2b2b2b",
               "border" : "1px solid #888"
            });
         }
      },
      draggable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      resizable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      
      eventDrop : function(calEvent, $event) {
        
      },
      eventResize : function(calEvent, $event) {
      },
      horarioRefresh : function(calEvent, $event) {
         console.log("YOLO");
      },
      
      eventMouseover : function(calEvent, $event) {
      },
      eventMouseout : function(calEvent, $event) {
      },
      noEvents : function() {

      },
      

     // data : calendar
     data:"createCalendar.php?calendar"
     
   });




   /*
    * Sets up the start and end time fields in the calendar event
    * form for editing based on the calendar event being edited
    */
   function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

      for (var i = 0; i < timeslotTimes.length; i++) {
         var startTime = timeslotTimes[i].start;
         var endTime = timeslotTimes[i].end;
         var startSelected = "";
         if (startTime.getTime() === calEvent.start.getTime()) {
            startSelected = "selected=\"selected\"";
         }
         var endSelected = "";
         if (endTime.getTime() === calEvent.end.getTime()) {
            endSelected = "selected=\"selected\"";
         }
         $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
         $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

      }
      $endTimeOptions = $endTimeField.find("option");
      $startTimeField.trigger("change");
   }

   var $endTimeField = $("select[name='end']");
   var $endTimeOptions = $endTimeField.find("option");

   //reduces the end time options to be only after the start time options.
   $("select[name='start']").change(function() {
      var startTime = $(this).find(":selected").val();
      var currentEndTime = $endTimeField.find("option:selected").val();
      $endTimeField.html(
            $endTimeOptions.filter(function() {
               return startTime < $(this).val();
            })
            );

      var endTimeSelected = false;
      $endTimeField.find("option").each(function() {
         if ($(this).val() === currentEndTime) {
            $(this).attr("selected", "selected");
            endTimeSelected = true;
            return false;
         }
      });

      if (!endTimeSelected) {
         //automatically select an end date 2 slots away.
         $endTimeField.find("option:eq(1)").attr("selected", "selected");
      }

   });


   var $about = $("#about");

   $("#about_button").click(function() {
      $about.dialog({
         title: "About this calendar demo",
         width: 600,
         close: function() {
            $about.dialog("destroy");
            $about.hide();
         },
         buttons: {
            close : function() {
               $about.dialog("close");
            }
         }
      }).show();
   });


});
