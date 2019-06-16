import makeAnimations from '../helpers/animations';
import Client from '../ClientApplication';

class BootScene extends Phaser.Scene {
    constructor(test) {
        super({
            key: 'BootScene'
        });
    }

    preload() {
        this.client = new Client();

        const progressBar = this.add.graphics();
        const progressBox = this.add.graphics();
        progressBox.fillStyle(0x222222, 0.8);
        progressBox.fillRect(240, 270, 320, 50);

        // Register a load progress event to show a load bar
        this.load.on('progress', (value) => {
            progressBar.clear();
            progressBar.fillStyle(0xffffff, 1);
            progressBar.fillRect(250, 280, 300 * value, 30);
        });

        // Register a load complete event to launch the title screen when all files are loaded
        this.load.on('complete', () => {
            // prepare all animations, defined in a separate file
            makeAnimations(this);
            progressBar.destroy();
            progressBox.destroy();
            //pass client to next scene
            this.scene.start('TitleScene', {client: this.client});
        });

        this.load.bitmapFont('font', 'assets/fonts/font.png', 'assets/fonts/font.fnt');
        this.load.image('tiles', 'assets/tilesets/tileset.png');
        this.load.tilemapTiledJSON('map', 'assets/tilemaps/bigMap.json');
        this.load.atlas('atlas', 'assets/atlas/sprites.png', 'assets/atlas/sprites.json');
        this.load.audio('bump', 'assets/sounds/bump.mp3');
        this.load.audio('cry', 'assets/sounds/ratatta.mp3');
    }
}

export default BootScene;
