import Player from '../sprites/Player';

class GameScene extends Phaser.Scene {
    constructor(test) {
        super({
            key: 'GameScene'
        });
    }

    init(data) {
        // todo list of players, list of npcs
        // todo state if new game or leftCave
        this.isNewGame = data.newGame;
    }

    preload() {
        // todo plugin for tile based movement?
        this.load.scenePlugin('GridPhysics', 'plugins/GridPhysics.min.js', 'gridPhysics', 'gridPhysics');
    }

    create() {
        // Add the map + bind the tileset
        this.map = this.make.tilemap({
            key: 'map'
        });
        this.tileset = this.map.addTilesetImage('tileset', 'tiles');

        this.belowLayer = this.map.createStaticLayer('Below Player', this.tileset, 0, 0);
        this.worldLayer = this.map.createStaticLayer('World', this.tileset, 0, 0);
        const HOLE = 25;

        this.gridPhysics.world.enable(this.belowLayer);
        this.gridPhysics.world.enable(this.worldLayer);

        // this.keys will contain all we need to control Player.
        // Any key could just replace the default (like this.key.jump)
        this.keys = {
            left: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.LEFT),
            right: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.RIGHT),
            down: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.DOWN),
            up: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.UP),
            debug: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.D)
        };

        // todo if new Game set sprites on spawnPoints, else load position of server
        this.spawnPoints = this.map.getObjectLayer('Objects').objects;

        //todo create group in titleScene
        this.playerGroup = this.add.group();
        this.npcGroup = this.add.group();

        // CREATE PLAYER!!!
        this.player = new Player({
            scene: this,
            key: 'player',
            x: this.spawnPoints[1].x,
            y: this.spawnPoints[1].y
        });

        this.gridPhysics.world.enable(this.player);
        this.worldLayer.setCollisionByExclusion([-1, HOLE]);
        this.worldLayer.setTileIndexCallback(HOLE, () => {
            console.log("Entering cave...");
            this.scene.start('CaveScene', {client: this.client, player: this.player});
        }, this);

        this.physics.add.collider(this.player, this.worldLayer);

        // The camera should follow Player
        this.cameras.main.startFollow(this.player);
        this.cameras.main.roundPixels = true;

        // Help text that has a "fixed" position on the screen
        this.add
            .text(16, 16, 'Arrow keys to move\nPress "D" to show hitboxes', {
                font: "12px monospace",
                fill: "#000000",
                padding: { x: 20, y: 10 },
                backgroundColor: "#ffffff"
            })
            .setScrollFactor(0)
            .setDepth(30);

        // Debug graphics
        this.input.keyboard.once("keydown_D", event => {
            // Turn on physics debugging to show player's hitbox

            this.physics.world.createDebugGraphic();

            // Create worldLayer collision graphic above the player, but below the help text
            const graphics = this.add
                .graphics()
                .setAlpha(0.75)
                .setDepth(20);
            this.worldLayer.renderDebug(graphics, {
                tileColor: null, // Color of non-colliding tiles
                collidingTileColor: new Phaser.Display.Color(243, 134, 48, 255), // Color of colliding tiles
                faceColor: new Phaser.Display.Color(40, 39, 37, 255) // Color of colliding face edges
            });
        });



    }

 /*   goIntoCave() {
        console.log("Entering cave...")
        //this.player.moveOneStep();
        //const cam = this.cameras.main;
        //cam.fade(250, 0, 0, 0);
        //cam.once("camerafadeoutcomplete", () => {
        //thisScene.scene.start('CaveScene', {client: this.client, player: this.player});
         //   cam.fade(10, 255, 255, 255);
        //});
        //cam.fade(10, 255, 255, 255);

        /!*this.belowLayer.destroy(false);
        this.cave1Layer = this.map.createStaticLayer('Cave1', this.tileset, 0, 0);
        //this.map.setLayer(this.cave1Layer);
        //this.holeLayer = this.map.createStaticLayer('Holes', this.tileset, 0, 0);*!/
    }*/

    update(time, delta) {
        // Run the update method of Player
        this.player.update(this.keys, time, delta);
    }

}

export default GameScene;
