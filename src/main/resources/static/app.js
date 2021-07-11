var stompClient = null;

function connect() {
    var socket = new SockJS('/martian-robots');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/output/robot', function (output) {
           var outputToShow = "";
           for (el of JSON.parse(output.body).robots){
              outputToShow += el.xpos + " " + el.ypos + " " + el.orientation + (el.lost ? " LOST" : "") + "\n";
           }
           showOutput(outputToShow);
           disconnect();
        });
        sendRobots();
    }, );
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendRobots() {
    var splitted = $("#robot-input").val().split("\n")
    var firstLineSplitted = splitted.shift().split(' ');
    var input = {maxX: firstLineSplitted[0], maxY: firstLineSplitted[1], robots: []}
    while(splitted.length != 0){
       var lineInitialPositionSplitted = splitted.shift().split(' ');
       if (splitted.length == 0){
         break;
       }
       var movements = splitted.shift();
       input.robots.push({initialX: lineInitialPositionSplitted[0], initialY: lineInitialPositionSplitted[1], initialOrientation: lineInitialPositionSplitted[2], movements: movements.split('')})
    }

    stompClient.send("/input/robot", {}, JSON.stringify(input));
}

function showOutput(message) {
    $("#output").val(message);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { connect(); });
});

