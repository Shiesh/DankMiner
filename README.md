# DankMiner
A Minecraft Server Plugin to display Memes from reddit on Map-Items.

+ #### Minecraft-Version: 1.16.x

This is a _just for fun_ plugin. Most of the images displayed are quite hard to read, due to the 
small resolution of maps.
On load a List of the first **40** posts in the top section of the subreddit gets loaded.
The Images are loaded then from the internet and scaled to map size _(128x128 px)_, whenever a player interacts with the Meme-Map.
Because of rate limiting the player can only get a new Image every 2 seconds by default.

### Functionality
* Get a Meme-Map by renaming a (empty or filled) Map to `MEME`
* *Left-Click* in Air to get a new random Image
* `/meme` to get a set up Meme-Map
* with every new Image the title of the post is shown in the chat
* click on the message to get redirected to the web page in your browser

### Configuration
You can change the source subreddit, cooldown and permission of the /meme command in the
`DankMiner.yml` in the server's plugin folder.

**Entry** | Description | default
---|---|---|
`subreddit` | the subreddit the DankMiner gets it's images | _dankmemes_
`cooldown` | the time in seconds a player has to wait until he can use the Meme-Map again | _2_
`op` | boolean if the player needs op to execute the _/meme_ command | _true_

A default config-file gets created when the server starts and no config file exists.

### More Information
* if post does not contain a image nothing happens
* if DankMiner failed to load an image the `STANDBY.png` gets loaded
* rate limiting can lead to a server crash
* Please report any bugs and feel free to contribute to this project

### Planed features
* combined maps to achieve a larger image
