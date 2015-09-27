
///////////////////////////////////////////////
function win1()
{
  var win = new Window(
  {
    id: "win1", 
    className: "GREENwindow", 
    title: "Sample", 
    width:190, height:140, top:200, left: 50, 
    destroyOnClose: true,
    parent:$('container')
  }); 
  win.getContent().innerHTML = "<h2>Constraint inside  a div !!</h2>constraint: {left:10, right:20}<br><a href='#' onclick='Windows.getWindow(\"win1\").destroy()'><h2>destroy me</h2></a> <ul class=\"try\"> <a href='#' onclick='newWindow()'>open a new window</a> </ul>"; 
  //<div id='inside_buttons'> <ul class='try'> <a href='#' onclick='newWindow()'>open a new window</a></ul></div>
  //win.setContent("content");
  win.setDestroyOnClose(); 
  win.show();
  win.setConstraint(true, {left:10, right:30, top: 30, bottom:10})
  //win.setConstraint(true, {left:10, right:20})
  win.toFront();

}

  function newWindow() {
    var win = new Window(
                        {
                          className: "GREENwindow", 
                          top:170, left:200, width:200, height:100, 
                          title:"Other Msg Board",
                          maximizable: false, minimizable: false
                        });
    win.setContent("content");
    win.setDestroyOnClose();
    win.show();
  }



function win2()
{
  var win = new Window({id: "win2", className: "GREENwindow", title: "Sample", width:200, height:150}); 
  win.getContent().innerHTML = "<h2>Constraint inside page !!</h2>constraint: {top: 30, bottom:10}<br><a href='#' onclick='Windows.getWindow(\"win2\").maximize()'>Maximize me</a>"; 

  win.setDestroyOnClose(); 
  win.showCenter();
  win.setConstraint(true, {left:0, right:0, top: 30, bottom:10})
  win.toFront();
}

function win3()
{
  var win = new Window({id: "win3", className: "GREENwindow", title: "Sample", width:250, height:150, wiredDrag: true}); 
  win.getContent().innerHTML = "<h2>No Constraint</h2>Wired mode<br><a href='#' onclick='Windows.getWindow(\"win3\").maximize()'>Maximize me</a>"; 
  win.setDestroyOnClose(); 
  win.setLocation(10, 500);
  win.show();
  win.toFront();
}
///////////////////////////////////////////////
function openWindowLogin(){
  var win = new Window(
  { id: "login",
    className: "GREENwindow", title: "Login", 
    top:80, left:550, width:200, height:170, 
	resizable: false,  closable: false, maximizable: false,
    url: "log.php", 
    showEffectOptions: {duration:1.5}
  })
  win.show(); 

}

function openWindow() {
   var win = new Window(
   {
    className: "GREENwindow", 
    title: "Project Info",
    resizable: true,
    draggable:true
   })
win.setLocation(150, 700);
win.setSize(450,200);

win.getContent().innerHTML= "<div style='padding:10px'>  A aplicação proposta para o desenvolvimento no âmbito de disciplina de ASW ajuda os utilizadores a comunicar de forma rápida. Para a realização da mesma foram escolhidas as ferramentas que nos achamos serem a mais convenientes, PHP, MySQL e algum Javascript.<br>No final da segunda fase criamos os processos para os utilizadores poderem criar, ver e etiquetar mensagens, usando uma base de dados para guardar a informação.<br>Para a fase final introduzimos uma forma de filtrar utilizadores e a possibilidade de envio de imagens. Nesta fase o foco passou pela criação de uma interface de utilizador em concordância com o que tínhamos planeado inicialmente.<br>Foi decidido limitar o tamanho das imagens enviadas a 50 por 50 [pixels].<br>Existiram algumas dificuldades na verificação do email introduzido pelos utilizadores devido ao facto de termos utilizado uma função de PHP não existente na versão instalada no appserver tendo por isso sido retirada a possibilidade de introdução de email de modo a evitar injecções de SQL.<br>O trabalho foi organizado em grupo, todos os elementos do mesmo participaram e foi preciso aproximadamente 20h de trabalho para a elaboração do projecto. </div>";
//win.setStatusBar("Status bar info, window ID: "+ win.getId());
win.show();        
}   

function mainMsgBoard()
{
  MainMsgBoard = new Window('MainMsgBoard', 
                    {
                      className: "GREENwindow", 
                      title: "Main Message Board",
                      top:280, left:250, width:580, height:700,  
                      closable: false, resizable: true, draggable: true,
                      url: "http://appserver.di.fc.ul.pt/~asw42482/messages.php"
                    })

  MainMsgBoard.show();

}



