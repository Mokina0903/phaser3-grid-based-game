# Generic Platformer and Phaser Bootstrap Project
#### Phaser 3 + ES6 + Webpack

The code is based on the project https://github.com/nkholski/phaser3-es6-webpack

# Parts of API used:
A messy list of things used from the Phaser API.

**Preloader**
- image, tilemapTiledJSON, spritesheet, atlas, audio, audiosprite, bitmapFont, plugin

**Input**
- Phaser.Input.Keyboard

**Audio**
(not yet implemented)
- Audioatlas (including some event listeners)
- Music (pause/resume/rate)

**Animations**
- Animating sprites

**Tilemaps**
- Multiple layers

**Sprites**
- All sprites are ES6 extensions of native Phaser.Sprite

**Physics**
- Acceleration
- collisions

**Groups**
(not yet implemented)
- Sprites are put in groups

**BitmapText**
(not yet implemented)
- For player overview and infos

**Tweens**
- entering pipes, ending the world etc.

# Setup
You’ll need to install a few things before you have a working copy of the project.

## 1. Clone this repo:

Navigate into your workspace directory.

Run:

```git clone https://github.com/Mokina0903/phaser3-grid-based-game.git```

## 2. Install node.js and npm:

https://nodejs.org/en/


## 3. Install dependencies (optionally you could install [yarn](https://yarnpkg.com/)):

Navigate to the cloned repo’s directory.

Run:

```npm install```

or if you choose yarn, just run ```yarn```

## 4. Run the development server:

Run:

```npm run dev```

This will run a server so you can run the game in a browser.

Open your browser and enter localhost:3000 into the address bar.

Also this will start a watch process, so you can change the source and the process will recompile and refresh the browser.


## Build for deployment:

Run:

```npm run deploy```

This will optimize and minimize the compiled bundle.

## Next steps:

- connect to backend
- player join + character selection in the title scene
- add multiple players as mice or cars
- tiled based movement and step count
- hole system for mice
- collision handling for mice vs. cats
- how to win the game?
- AI for NPC characters (path finding etc.)
