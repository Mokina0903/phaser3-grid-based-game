//load.js
var loadState={
	preload: function(){
		var loadingLabel = game.add.text(10, 10, 'loading...', {font: '30px Courier', fill: '#ffffff'});

		game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
		game.scale.PageAlignHorizonally = true;
		game.scale.PageAlignVertically = true;
		game.stage.backgroundColor = '#000000';

		/**** Load graphics assets1 ****/
		game.load.spritesheet('characters', 'assets1/sprites/sprites.png', 32, 32);
	//	game.load.image('lifebar', 'assets1/maps/overground_small.json');
		game.load.tilemap('level', 'assets1/maps/overground_small.json', null, Phaser.Tilemap.TILED_JSON);
		game.load.image('tiles', 'assets1/sprites/pokemon_emerald_exterior_tileset.png');

		/**** Load audio assets1 ****/
		game.load.audio('bump', 'assets1/sounds/bump.mp3');
		game.load.audio('hit', 'assets1/sounds/ratatta.mp3');


	},
 	create: function(){
 		game.state.start('title');
 	}
 };
