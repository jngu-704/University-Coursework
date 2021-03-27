// Johnson Nguyen 27860396 jngu73@student.monash.edu
// FIT2102 2019 Assignment 1
// https://docs.google.com/document/d/1Gr-M6LTU-tfm4yabqZWJYg-zTjEVqHKKTCvePGCYsUA/edit?usp=sharing
// This game uses observables, by subscribing the values are watched for changes
// If changes are detected these values will be streamed through
// This game works by watching for keyboard presses and mouse clicks/movements
// If changes are detected the values will be passed through which will change
// the way the svg elements behaves.
// NOTE: shooting at different angles does not work and couldn't be implemented 
// I was unable to get the correct coordinates of the lasers using g.elem transform attribute.
// This caused trouble in collision detection and firing in a straight line hence why i had to 
// remove the use of transform and instead I used the x and y attributes. 
// NO changes was made to any other file except the HTML file
function asteroids() {
    // Explain which ideas you have used ideas from the lectures to 
    // create reusable, generic functions.
    var svg = document.getElementById("canvas");
    var asteroids = [];
    var shipPos = {
        x: 300,
        y: 300,
        deg: 0
    };
    var g = new Elem(svg, 'g')
        //.attr("transform","translate(300 300) rotate(170)")  
        .attr("transform", "translate(0 0) rotate(0)");
    // create a polygon shape for the space ship as a child of the transform group
    var ship = new Elem(svg, 'polygon', g.elem)
        .attr("points", "-15,20 15,20 0,-20")
        .attr("style", "fill:lime;stroke:purple;stroke-width:1");
    ship.attr('transform', move(shipPos.x, shipPos.y, shipPos.deg));
    // create new laser
    function new_laser(x, y, deg, asteroids) {
        var laser = new Elem(svg, "rect", g.elem)
            .attr("fill", "#FF0000")
            .attr('width', 2).attr('height', 10)
            .attr('x', x).attr('y', y);
        //laser.attr('transform', move(x, y, deg));
        Observable.interval(5)
            .takeUntil(Observable.interval(3500))
            .subscribe(function () {
            laser
                .attr('y', Number(laser.attr('y')) - 1),
                //console.log(laser.attr("")),
                out_ofbound(laser),
                collision(laser, asteroids);
        });
        return laser;
    }
    // Create asteroids
    function create_asteroid(x, y, width, height, asteroids, speed) {
        var asteroid = new Elem(svg, "rect")
            .attr("fill", "#654321")
            .attr("x", x).attr("y", y)
            .attr('width', width).attr('height', height);
        // set a random direction
        var x_incre = get_randomnum(), y_incre = get_randomnum();
        Observable.interval(speed)
            .subscribe(function () {
            asteroid
                .attr('x', x_incre + Number(asteroid.attr('x')))
                .attr('y', y_incre + Number(asteroid.attr('y'))),
                warping(asteroid);
        });
        asteroids.unshift(asteroid);
    }
    var mousemove = Observable.fromEvent(svg, 'mousemove');
    // click to grab ship and move ship using mouse movements
    ship.observe('mousedown')
        .flatMap(function (_a) {
        var clientX = _a.clientX, clientY = _a.clientY;
        return mousemove
            .map(function (_a) {
            var clientX = _a.clientX, clientY = _a.clientY;
            return ({
                x: clientX - 5,
                y: clientY - 75
            });
        });
    })
        .subscribe(function (_a) {
        var x = _a.x, y = _a.y;
        ship.attr('transform', move(x, y, shipPos.deg)),
            shipPos.x = x,
            shipPos.y = y,
            generate_asteroids(asteroids);
        ship_collision(ship, asteroids, shipPos.x, shipPos.y);
    });
    // maintain an acceptable amount of asteroids
    function generate_asteroids(asteroids) {
        if (asteroids.length <= 0) {
            create_asteroid(100, 100, 60, 60, asteroids, 10);
            create_asteroid(100, 500, 60, 60, asteroids, 10);
            create_asteroid(500, 100, 60, 60, asteroids, 10);
            create_asteroid(500, 500, 60, 60, asteroids, 10);
            create_asteroid(270, 50, 60, 60, asteroids, 10);
        }
        else if (asteroids.length < 4)
            create_asteroid(300, 0, 60, 60, asteroids, 10);
    }
    // Rotate ship
    var keydown = Observable.fromEvent(document, "keydown"), keyup = Observable.fromEvent(document, "keyup");
    // rotate ship to the left if the a key is pressed
    keydown
        .filter(function (x) { return x.keyCode === 65; })
        //.takeUntil(keyup)
        .subscribe(function (_) {
        shipPos.deg = shipPos.deg - 20,
            ship.attr('transform', move(shipPos.x, shipPos.y, shipPos.deg));
    });
    //rotate ship to the right if the d key is pressed
    keydown
        .filter(function (x) { return x.keyCode === 68; })
        .subscribe(function (_) {
        shipPos.deg = shipPos.deg + 20,
            ship.attr('transform', move(shipPos.x, shipPos.y, shipPos.deg));
    });
    // shoot laser if spacebar press is detected
    keydown
        .filter(function (x) { return x.keyCode === 32; })
        .subscribe(function (_) {
        new_laser(shipPos.x, shipPos.y, shipPos.deg, asteroids);
    });
    // get random number between -1 and 1
    function get_randomnum() {
        var num = Math.random();
        num *= Math.floor(Math.random() * 2) == 1 ? 1 : -1;
        return num;
    }
    // Warping function by checking if the obj has reached any walls
    function warping(obj) {
        if (Number(obj.attr('x')) < -60) {
            obj.attr('x', 600);
        }
        else if (Number(obj.attr('x')) > 600) {
            obj.attr('x', 0);
        }
        else if (Number(obj.attr('y')) < -60) {
            obj.attr('y', 600);
        }
        else if (Number(obj.attr('y')) > 600) {
            obj.attr('y', 0);
        }
    }
    // function to move and rotate ship
    function move(x, y, r) {
        var translate = "translate (".concat((x).toString(), " ", (y).toString(), ")"), rotate = " rotate (".concat(r.toString(), ")");
        return translate + rotate;
    }
    // check if an object has collided with any asteroids
    function collision(obj, asteroids) {
        asteroids.forEach(function (asteroid) {
            var x = Number(obj.attr('x')), y = Number(obj.attr('y')), x_coord = Number(asteroid.attr('x')), y_coord = Number(asteroid.attr('y')), width = Number(asteroid.attr('width')), height = Number(asteroid.attr('height'));
            //console.log(x, x_coord)
            if ((x >= x_coord) && (x <= x_coord + width)) {
                //console.log(asteroid)
                if ((y >= y_coord) && (y <= y_coord + height)) {
                    if (obj === ship) {
                        console.log(x, y);
                        obj.elem.remove();
                    }
                    else {
                        // remove asteroid from the array
                        var i = asteroids.indexOf(asteroid);
                        if (i > -1) {
                            asteroids.splice(i, 1);
                        }
                        obj.elem.remove();
                        asteroid.elem.remove();
                        if (width === 60) {
                            create_asteroid(x_coord, y_coord, 30, 30, asteroids, 0);
                            create_asteroid(x_coord, y_coord, 30, 30, asteroids, 0);
                        }
                    }
                }
            }
        });
    }
    // check if an object has collided with any asteroids
    function ship_collision(obj, asteroids, x, y) {
        asteroids.forEach(function (asteroid) {
            var x_coord = Number(asteroid.attr('x')), y_coord = Number(asteroid.attr('y')), width = Number(asteroid.attr('width')), height = Number(asteroid.attr('height'));
            if ((x >= x_coord) && (x <= x_coord + width)) {
                if ((y >= y_coord) && (y <= y_coord + height)) {
                    obj.elem.remove();
                    alert("GAME OVER YOU LOSE");
                    clearInterval();
                }
            }
        });
    }
}
// check if element is out of bound
function out_ofbound(obj) {
    if (Number(obj.attr('x')) < -10) {
        obj.elem.remove();
    }
    else if (Number(obj.attr('x')) > 600) {
        obj.elem.remove();
    }
    else if (Number(obj.attr('y')) < -10) {
        obj.elem.remove();
    }
    else if (Number(obj.attr('y')) > 600) {
        obj.elem.remove();
    }
}
// the following simply runs your asteroids function on window load.  Make sure to leave it in place.
if (typeof window != 'undefined')
    window.onload = function () {
        asteroids();
    };
