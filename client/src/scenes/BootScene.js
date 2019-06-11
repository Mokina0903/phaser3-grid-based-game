import makeAnimations from '../helpers/animations';
import Client from '../ClientApplication';

class BootScene extends Phaser.Scene {
    constructor(test) {
        super({
            key: 'BootScene'
        });
    }
    preload() {
        const progress = this.add.graphics();
        this.client = new Client();

        // Register a load progress event to show a load bar
        this.load.on('progress', (value) => {
            progress.clear();
            progress.fillStyle(0xffffff, 1);
            progress.fillRect(0, 10, this.sys.game.config.width * value, 60);
        });

        // Register a load complete event to launch the title screen when all files are loaded
        this.load.on('complete', () => {
            // prepare all animations, defined in a separate file
            makeAnimations(this);
            progress.destroy();
            //pass client to next scene
            this.scene.start('TitleScene', {client: this.client});
        });

        // todo load audio

        this.load.bitmapFont('font', 'assets/fonts/font.png', 'assets/fonts/font.fnt');
        this.load.image('tiles', 'assets/tilesets/tileset.png');
        this.load.tilemapTiledJSON('map', 'assets/tilemaps/bigMap.json');
        this.load.atlas('atlas', 'assets/atlas/sprites.png', 'assets/atlas/sprites.json');
    }
}

export default BootScene;
