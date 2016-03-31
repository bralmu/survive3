// Basic configuration
var SERVER_HOST = "http://167.114.114.154/survive3/api"; // server address, for example http://localhost:8080
var AJAX_TIMEOUT = 5000; //maximum server response waiting time in miliseconds.
var SERVER_UPDATE_FRECUENCY = 2; // updates per second when requesting remote game status from server.
var LOCAL_UPDATE_FRECUENCY = 10; // updates per second for local processing/rendering (it must be the same of the server).
var TEXTURES = {
    'default' : PIXI.Texture.fromImage('images/free_territory.jpg'),
    'free_territory' : PIXI.Texture.fromImage('images/free_territory.jpg'),
    'daesh_flag' : PIXI.Texture.fromImage('images/daesh_flag.png'),
    'daesh_territory' : PIXI.Texture.fromImage('images/daesh_territory.png'),
    'israel_flag' : PIXI.Texture.fromImage('images/israel_flag.png'),
    'israel_territory' : PIXI.Texture.fromImage('images/israel_territory.png'),
    'lgbt_flag' : PIXI.Texture.fromImage('images/lgbt_flag.png'),
    'lgbt_territory' : PIXI.Texture.fromImage('images/lgbt_territory.png'),
    'usa_flag' : PIXI.Texture.fromImage('images/usa_flag.png'),
    'usa_territory' : PIXI.Texture.fromImage('images/usa_territory.png'),    
    'russia_flag' : PIXI.Texture.fromImage('images/russia_flag.png'),
    'russia_territory' : PIXI.Texture.fromImage('images/russia_territory.png'),
    'eu_flag' : PIXI.Texture.fromImage('images/eu_flag.png'),
    'eu_territory' : PIXI.Texture.fromImage('images/eu_territory.png'),
    'china_flag' : PIXI.Texture.fromImage('images/china_flag.png'),
    'china_territory' : PIXI.Texture.fromImage('images/china_territory.png'),
    'india_flag' : PIXI.Texture.fromImage('images/india_flag.png'),
    'india_territory' : PIXI.Texture.fromImage('images/india_territory.png'),
    'brazil_flag' : PIXI.Texture.fromImage('images/brazil_flag.png'),
    'brazil_territory' : PIXI.Texture.fromImage('images/brazil_territory.png'),
};

// Classes
function Unit(posX, posY, speed, team, id) { //ok
    this.posX = posX;
    this.posY = posY;
    this.targetX = posX;
    this.targetY = posY;
    this.speed = speed;
    this.team = team;
    this.id = id;
    var texture = TEXTURES[team.t_name+'_flag'];
    this.sprite = new PIXI.Sprite(texture);
    this.sprite.anchor.x = 0.5;
    this.sprite.anchor.y = 0.5;
    this.sprite.scale.x = worldX2canvasX(world.cell_size) / 64;
    this.sprite.scale.y = worldY2canvasY(world.cell_size) / 64;
    stage.addChild(this.sprite); 
}
function Team(t_name) { //ok
    this.t_name = t_name;
    this.score = 0;
    this.id = teamCount++;
}
function Cell(posX, posY) { //ok
    this.posX = posX;
    this.posY = posY;
    var texture = TEXTURES['default'];
    this.sprite = new PIXI.Sprite(texture);
    this.sprite.anchor.x = 0.5;
    this.sprite.anchor.y = 0.5;
    this.sprite.position.x = worldX2canvasX(this.posX);
    this.sprite.position.y = worldX2canvasX(this.posY);
    this.sprite.scale.x = worldX2canvasX(world.cell_size) / 128;
    this.sprite.scale.y = worldY2canvasY(world.cell_size) / 128;
    stage.addChild(this.sprite);
    this.setTeam = function(team) {
        this.sprite.texture = TEXTURES[team.t_name+'_territory'];
    }
}
function World(sizeX, sizeY, cell_size) { //ok
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.cell_size = cell_size;
    if(sizeX % cell_size != 0 || sizeY % cell_size != 0)
        console.log("Warning: cell_size must be a common divisor of world.sizeX and world.sizeY.");
}

// Global variables
var units = {};
var selectedUnit;
var teamCount = 0;
var teams = [];
var cells = [];
var world;
var score_board = [];

var renderer;
var stage;
var zoom = 1;
var offset_X = 0;
var offset_Y = 0;

// Starting
function starter2() {
    //Pixi start
    $(document).on("contextmenu", function() {return false;})
    renderer = PIXI.autoDetectRenderer(document.body.clientWidth, document.body.clientHeight,{backgroundColor : 0x1099bb});
    document.body.appendChild(renderer.view);
    stage = new PIXI.Container();
    //Get data from server
    $.ajaxSetup({
        async: false,
        cache: false
    });
    var externData = $.getJSON(SERVER_HOST+"/game", function(answer) {
        //World
        world = new World(answer.gameWidth, answer.gameHeight, answer.cellSize);
        //Teams
        for(t in answer.teams) {
            teams.push(new Team(answer.teams[t].name));
            teams[t].score = answer.teams[t].score;
        }
        //Cells
        for (c in answer.cells) {
            cells.push(new Cell(answer.cells[c].posX, answer.cells[c].posY));
            cells[c].setTeam(teams[answer.cells[c].teamId]);
        }
        //Units
        for(u in answer.units) {        
            var posX = answer.units[u].posX;
            var posY = answer.units[u].posY;
            var speed = answer.units[u].speed;
            var t_name = teams[answer.units[u].teamId];
            units[u] = new Unit(posX, posY, speed, t_name, u);
        }
    });
    $.ajaxSetup({
        async: true,
        timeout: AJAX_TIMEOUT
    });
    selectUnit(myUID);
    //Scoreboard
    createScoreBoard();
    //Mouse event controllers    
    $(document).mousedown(clickHandler);
    $(window).bind('touchstart', touchHandler);
    $(window).bind('mousewheel DOMMouseScroll', scrollHandler);
    //Window resize controller;
    $(window).resize(resizeCanvas);
    //Processor
    var processor2 = setInterval(process2, 1000/SERVER_UPDATE_FRECUENCY);
    var processor3 = setInterval(process3, 1000/LOCAL_UPDATE_FRECUENCY);
}

function clickHandler(event) {
    var x = event.pageX;
    var y = event.pageY;
    applyNewUnitTarget(x, y);
}

function touchHandler(event) {
    var x = event.originalEvent.changedTouches[0].clientX;
    var y = event.originalEvent.changedTouches[0].clientY;
    applyNewUnitTarget(x, y);
}

function applyNewUnitTarget(targetXcanvas, targetYcanvas) {
    var wx = canvasX2worldX(targetXcanvas);
    var wy = canvasY2worldY(targetYcanvas);
    selectedUnit.targetX = wx;
    selectedUnit.targetY = wy;
    $.getJSON(SERVER_HOST+"/move", {uid: selectedUnit.id, targetx: selectedUnit.targetX, targety: selectedUnit.targetY}, function(answer){var success=answer.valid;});
}

function scrollHandler(event) {
    if (event.originalEvent.wheelDelta > 0 || event.originalEvent.detail < 0) {
        zoom += .33;
        updateSpritePositions();
        updateSpriteSizes(true, true);
    }
    else {
        if(zoom > .66) {
            zoom -= .33;
        }
        else {
            zoom = .33;
        }
        updateSpritePositions();
        updateSpriteSizes(true, true);
    }
}   

function selectUnit(unitId) {
    selectedUnit = units[unitId];
}

// Scoreboard
function createScoreBoard() {
    var posY = 0
    for(t in teams) {
        var team_score = new PIXI.Text(teams[t].t_name, {font:"32px Arial", fill:"white"});
        team_score.position.y = posY;
        score_board.push(team_score);
        stage.addChild(team_score);
        posY += 40;
    }
}

function updateScoreBoard() {
    var highestScore = 0;
    for(t in teams) {
        if(teams[t].score > highestScore)
            highestScore = teams[t].score;
    }
    for(t in teams) {
        score_board[t].text = teams[t].t_name + ": "+(teams[t].score/cells.length*100).toFixed(2)+"%";
        score_board[t].position.y = (1 - teams[t].score/highestScore) * document.body.clientHeight;
    }
}

// Process and draw
function process2() {
    updateCellsAndTeamsAndOtherUnitsFromServer();
    updateScoreBoard();        
}

function process3() {
    moveMyUnit();
    moveCamera();
    updateSpritePositions();
    renderer.render(stage);
}

var updateCellsAndTeamsAndOtherUnitsFromServerSemaphore = true;
function updateCellsAndTeamsAndOtherUnitsFromServer() {
    var externData = $.getJSON(SERVER_HOST+"/gameupdate", function(answer){
        if(updateCellsAndTeamsAndOtherUnitsFromServerSemaphore) {
            updateCellsAndTeamsAndOtherUnitsFromServerSemaphore = false;
            //Teams
            for(t in answer.teams) {
                teams[t].score = answer.teams[t].score;
            }
            //Cells
            for (c in answer.cellsTeamId) {
                cells[c].setTeam(teams[answer.cellsTeamId[c]]);
            }
            //Units
            if(!answer.units[selectedUnit.id]) { // we've been kicked
                location.reload();
            }
            for(u in units) {
                if(u != selectedUnit.id) {
                    if(answer.units[u]) {
                        units[u].posX = answer.units[u].posX;
                        units[u].posY = answer.units[u].posY;
                    }else {
                        stage.removeChild(units[u].sprite);
                        delete units[u];
                    }            
                } else {
                    if(distance2D(units[u].posX, units[u].posY, units[u].targetX, units[u].targetY) > distance2D(answer.units[u].posX, answer.units[u].posY, units[u].targetX, units[u].targetY)) {
                        units[u].posX = answer.units[u].posX;
                        units[u].posY = answer.units[u].posY;
                    }
                }                
            }
            if(Object.keys(answer.units).length > Object.keys(units).length) { // new players
                for(ua in answer.units) {
                    var uid = answer.units[ua].id;
                    var found = false;
                    for(ub in units) {
                        if(units[ub].id == uid) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        var posX = answer.units[ua].posX;
                        var posY = answer.units[ua].posY;
                        var speed = answer.units[ua].speed;
                        var t_name = teams[answer.units[ua].teamId];
                        units[uid] = new Unit(posX, posY, speed, t_name, uid);
                    }
                }
                updateSpriteSizes(false, true);
            }            
            updateCellsAndTeamsAndOtherUnitsFromServerSemaphore = true;
        }        
    });
}
function resizeCanvas() {
    renderer.resize(document.body.clientWidth, document.body.clientHeight);
    updateSpritePositions();
    updateSpriteSizes(true, true);
}
function updateSpriteSizes(u_cells, u_units) {
    if(u_cells) {
        for (c in cells) {     
            cells[c].sprite.scale.x = worldX2canvasX(world.cell_size,  true) / 128;
            cells[c].sprite.scale.y = worldY2canvasY(world.cell_size, true) / 128;     
        }
    }
    if(u_units) {
        for (u in units) {
            units[u].sprite.scale.x = worldX2canvasX(world.cell_size, true) / 64;
            units[u].sprite.scale.y = worldY2canvasY(world.cell_size, true) / 64;
        } 
    }
}
function updateSpritePositions() {
    for (c in cells) {     
        posXcanvas = worldX2canvasX(cells[c].posX);
        posYcanvas = worldY2canvasY(cells[c].posY);
        if(posXcanvas < 0 || posXcanvas > document.body.clientWidth || posYcanvas < 0 || posYcanvas > document.body.clientHeight) {
            cells[c].sprite.renderable = false;
        }
        else {
            cells[c].sprite.renderable = true;
            cells[c].sprite.position.x = posXcanvas;
            cells[c].sprite.position.y = posYcanvas;
        }
    }
    for (u in units) {
        posXcanvas = worldX2canvasX(units[u].posX);
        posYcanvas = worldY2canvasY(units[u].posY);
        units[u].sprite.position.x = posXcanvas;
        units[u].sprite.position.y = posYcanvas;
    }    
}
function moveCamera() {
    var mf = 4; //margin factor
    offset_X = (world.sizeX/2 - selectedUnit.posX);
    if(offset_X > world.sizeX/mf) {
        offset_X = world.sizeX/mf;
    }else if(offset_X < -world.sizeX/mf) {
        offset_X = -world.sizeX/mf;
    }
    offset_Y = (world.sizeY/2 - selectedUnit.posY);
    if(offset_Y > world.sizeY/mf) {
        offset_Y = world.sizeY/mf;
    }else if(offset_Y < -world.sizeY/mf) {
        offset_Y = -world.sizeY/mf;
    }
}
function moveMyUnit() {
    var unit = selectedUnit;
    var deltaX = unit.targetX - unit.posX;
    var deltaY = unit.targetY - unit.posY;
    var deltaDistance = distance2D(unit.targetX, unit.targetY, unit.posX, unit.posY)
    if(deltaDistance >= 10) {
        unit.posX += deltaX / deltaDistance * unit.speed;
        unit.posY += deltaY / deltaDistance * unit.speed;
    }else if (deltaDistance > 1) {
        unit.posX += deltaX / deltaDistance;
        unit.posY += deltaY / deltaDistance;
    }
}
// World<->Canvas coordinates and size transformation
function worldX2canvasX(worldX, ignore_offset) {
    if(ignore_offset)
        return worldX * document.body.clientWidth * zoom / world.sizeX;
    return (worldX + offset_X) * document.body.clientWidth * zoom / world.sizeX - document.body.clientWidth * (0.5) * (zoom - 1);    
}
function worldY2canvasY(worldY, ignore_offset) {
    if(ignore_offset)
        return worldY * document.body.clientHeight * zoom / world.sizeY;
    return (worldY + offset_Y) * document.body.clientHeight * zoom / world.sizeY - document.body.clientHeight * (0.5) * (zoom - 1);
}
function canvasX2worldX(canvasX) {
    return (canvasX + document.body.clientWidth * 0.5 * (zoom - 1)) * world.sizeX / zoom / document.body.clientWidth - offset_X;
}
function canvasY2worldY(canvasY) {
    return (canvasY + document.body.clientHeight * 0.5 * (zoom - 1)) * world.sizeY / zoom / document.body.clientHeight - offset_Y;
}
// 2D points distance
function distance2D(ax, ay, bx, by) {
    return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
}
