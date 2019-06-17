import 'phaser';
import BootScene from './scenes/BootScene';
import GameScene from './scenes/GameScene';
import TitleScene from './scenes/TitleScene';
import CaveScene from './scenes/CaveScene'

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
        GameScene,
        CaveScene
    ]
};

const game = new Phaser.Game(config); // eslint-disable-line no-unused-vars
