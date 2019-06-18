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

        this.worldLayer.setCollisionByExclusion([-1, HOLE]);
        this.worldLayer.setTileIndexCallback(HOLE, () => {
            this.goIntoCave();
            //this.scene.start('CaveScene', {client: this.client, player: this.player});
        }, this);

        //this.gridPhysics.world.enable(this.belowLayer);
        //this.gridPhysics.world.enable(this.worldLayer);

        // todo if new Game set sprites on spawnPoints, else load position of server
        this.spawnPoints = this.map.getObjectLayer('Objects').objects;

        //todo create group in titleScene
        this.playerGroup = this.add.group();
        this.npcGroup = this.add.group();

        // CREATE PLAYER!!!
        this.playerGroup.add(
            this.player = new Player({
                scene: this,
                key: 'player',
                x: this.spawnPoints[1].x,
                y: this.spawnPoints[1].y
            })
        );

        // Run the update method of all players


        this.physics.add.collider(this.player, this.worldLayer);

        // The camera should follow Player
        this.cameras.main.startFollow(this.player);
        this.cameras.main.roundPixels = true;

        // Help text that has a "fixed" position on the screen
        this.add
            .text(16, 16, 'Arrow keys to move\nPress "D" to show hitboxes', {
                font: "12px monospace",
                fill: "#000000",
                padding: {x: 20, y: 10},
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

        // this.keys will contain all we need to control Player.
        this.keys = {
            left: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.LEFT),
            right: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.RIGHT),
            down: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.DOWN),
            up: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.UP),
            debug: this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.D)
        };

    }

    update(time, delta) {
        // Run the update method of Player
        //this.player.update(this.keys, time, delta);
        this.playerGroup.children.entries.forEach(
            (sprite) => {
                sprite.update(this.keys, time, delta);
            }
        );
    }

    goIntoCave() {
        console.log("direction: " + this.player.direction)
        let x, y;
        if (this.player.direction === "left") {
            x = this.player.x - 40;
            y = this.player.y;
        }
        console.log(this.player.x + ", " + this.player.y)
        console.log(x + ", " + y);

        this.physics.world.pause();
        this.player.anims.play(this.player.type + this.player.direction, true);
        this.cameras.main.fadeOut(250);
        this.tweens.add({
            targets: this.player,
            x: x,
            y: y,
            duration: 1000,
            onComplete: () => {
                this.scene.start('CaveScene', {client: this.client, player: this.player, direction: this.player.direction});
            }
        });
    }
}

export default GameScene;
