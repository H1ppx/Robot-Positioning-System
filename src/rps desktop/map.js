var myRobot;
var loaded = false;

var currentX = 50;
var currentY = 50;

// WPI Network tables client
const ntClient = require('wpilib-nt-client');
const client = new ntClient.Client();


function startMatch() {
    myGameArea.start();
}

// Display background for map
var background = new Image();
background.src = "2019-field-blue.jpg";
background.onload = function(){
    myGameArea.context.drawImage(background,0,0);  
    myRobot = new robot("myRobot","lawngreen",50, 50);
    loaded = true;
}

// Setup canvas for map
var myGameArea = {
    canvas : document.createElement("canvas"),
    start : function() {
        this.canvas.width = 272;
        this.canvas.height = 550;
        this.context = this.canvas.getContext("2d");
        document.body.insertBefore(this.canvas, document.body.childNodes[1]);
        this.interval = setInterval(updateGameArea, 20); // Updated every 20 milliseconds
    },
    
    clear : function() {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.context.drawImage(background,0,0);
    }    
}
    
function robot(name, color, x, y) {
    this.name = name;
    this.x = x;
    this.y = y; 

    this.update = function(){
        ctx = myGameArea.context;
        ctx.fillStyle = color;
        ctx.beginPath();
        ctx.arc(currentX, currentY, 15, 0, 2 * Math.PI);
        ctx.stroke();
        ctx.fill();
    }
}

function updateGameArea() {
    myGameArea.clear();
    if(loaded){
        myRobot.update();
    }
}

// Update X position from network tables
NetworkTables.addKeyListener('/SmartDashboard/robot-position/x', (key, value) => {
    this.currentX = value;
});

// // Update Y position from network tables
// NetworkTables.addKeyListener('/SmartDashboard/robot-position/y', (key, value) => {
//     this.currentY = value;
// });

startMatch()