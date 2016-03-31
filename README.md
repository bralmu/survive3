# survive3

Survive3 is a simple multiplayer game based on html5, jquery, pixi.js, java and spring where you can:

  - Select a team and join the game.
  - Move your unit along the map.
  - Conquer territory (it belongs to the closest unit).
  
For testing purposes there is a server running at http://167.114.114.154/survive3/api and a client at [http://167.114.114.154/survive3/](http://167.114.114.154/survive3/).

### Installation
Server:
   - Copy survive.jar and map.png to the same folder.
   - $ java -jar survive.jar
   - The server is now running and listening at port 8080.

Client:
   - Extract survive3_client.tar.gz
   - Open js/scripts.js and edit the configuration parameters. For example, by default it connects to my server but you can change SERVER_HOST to "http://localhost:8080" if you are running your own local server.
   - Open index.html on a browser.
