//load.js
var loadState={
	preload: function(){
		var loadingLabel = game.add.text(10, 10, 'loading...', {font: '30px Courier', fill: '#ffffff'});

		game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
		game.scale.PageAlignHorizonally = true;
		game.scale.PageAlignVertically = true;
		game.stage.backgroundColor = '#000000';

		/**** Load graphics assets ****/
		game.load.spritesheet('characters', 'assets/sprites/sprites.png', 32, 32);
	//	game.load.image('lifebar', 'assets/maps/overground_small.json');
		game.load.tilemap('level', 'assets/maps/overground_small.json', null, Phaser.Tilemap.TILED_JSON);
		game.load.image('tiles', 'assets/sprites/pokemon_emerald_exterior_tileset.png');

		/**** Load audio assets ****/
		game.load.audio('bump', 'assets/sounds/bump.mp3');
		game.load.audio('hit', 'assets/sounds/ratatta.mp3');


	},
 	create: function(){
 		game.state.start('title');
 	}
 };
