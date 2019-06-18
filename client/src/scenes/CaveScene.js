import Player from "../sprites/Player";

class CaveScene extends Phaser.Scene {
    constructor(test) {
        super({
            key: 'CaveScene'
        });
    }

    init(data) {
        this.client = data.client;
        this.player = data.player;
    }

    preload() {
    }

    create() {

        // Add the map + bind the tileset
        this.map = this.make.tilemap({
            key: 'map'
        });
        this.tileset = this.map.addTilesetImage('tileset', 'tiles');
        this.caveLayer = this.map.createStaticLayer('Cave', this.tileset, 0, 0);
        const HOLE = 25;

        // The camera should follow Player

        this.player = new Player({
            scene: this,
            key: 'player',
            x: this.player.x,
            y: this.player.y
        });

        this.caveLayer.setCollisionByExclusion([-1, HOLE]);
        this.caveLayer.setTileIndexCallback(HOLE, () => {
            console.log("leaving cave...");
            //this.scene.start('CaveScene', {newGame: false});
        }, this);

        this.physics.add.collider(this.player, this.caveLayer);

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
            this.caveLayer.renderDebug(graphics, {
                tileColor: null, // Color of non-colliding tiles
                collidingTileColor: new Phaser.Display.Color(243, 134, 48, 255), // Color of colliding tiles
                faceColor: new Phaser.Display.Color(40, 39, 37, 255) // Color of colliding face edges
            });
        });
    }

    leaveCave() {
        console.log("Leaving cave...")
        this.player.moveOneStep();
        const cam = this.cameras.main;
        //cam.fade(250, 0, 0, 0);
        //cam.once("camerafadeoutcomplete", () => {
        this.scene.start('GameScene', {newGame: false});
        //   cam.fade(10, 255, 255, 255);
        //});
        //cam.fade(10, 255, 255, 255);

        /*this.belowLayer.destroy(false);
        this.cave1Layer = this.map.createStaticLayer('Cave1', this.tileset, 0, 0);
        //this.map.setLayer(this.cave1Layer);
        //this.holeLayer = this.map.createStaticLayer('Holes', this.tileset, 0, 0);*/
    }
}

export default CaveScene;
