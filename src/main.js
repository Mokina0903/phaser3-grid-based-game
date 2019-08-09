import 'phaser';
import BootScene from './scenes/BootScene';
import SurfaceScene from './scenes/SurfaceScene';
import TitleScene from './scenes/TitleScene';
import TunnelScene from './scenes/TunnelScene'

const config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    parent: "game-container",
    pixelArt: true,
    physics: {
        default: "arcade",
        arcade: {
            gravity: { y: 0 }
        }
    },
    scene: [
        BootScene,
        TitleScene,
        SurfaceScene,
        TunnelScene
    ]
};

const game = new Phaser.Game(config); // eslint-disable-line no-unused-vars
