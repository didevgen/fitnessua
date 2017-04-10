var stompClient = null;
var url = "http://localhost:8000/endpoint";
function connect() {
    var socket = new SockJS(url);
    stompClient = Stomp.over(socket);
    stompClient.connect('lalalala', 'heyhey' , function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/userChannel', function(calResult){
            // showResult(JSON.parse(calResult.body).result);
            console.log('Subscribed');
        });
    });
}
loginModule = function() {
    connect();
};
function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}


