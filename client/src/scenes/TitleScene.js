class TitleScene extends Phaser.Scene {
    constructor(test) {
        super({
            key: 'TitleScene'
        });
    }

    //retrieve data from previous scene
    init(data) {
        this.client = data.client;
    }

    preload() {
    }

    create() {
        let config = {
            key: 'title',
            frames: [{
                frame: 'title',
                key: 'player-sprites'
            }]
        };

        this.scene.bringToTop();

        this.registry.set('restartScene', false);

        // set window size
        let sh = window.screen.availHeight;
        let sw = window.screen.availWidth;
        let multiplier = 1;
        if (sh / sw > 0.6) {
            // Portrait, fit width
            multiplier = sw / 400;
        } else {
            multiplier = sh / 240;
        }
        multiplier = Math.floor(multiplier);
        let el = document.getElementsByTagName('canvas')[0];
        el.style.width = 400 * multiplier + 'px';
        el.style.height = 240 * multiplier + 'px';

        //cat and mouse to choose character
        this.mouseSprite = this.add.sprite(16 * 8 + 4, 4 * 16, "atlas", "mouse_down");
        this.catSprite = this.add.sprite(16 * 16 + 4, 4 * 16, "atlas", "cat_down");
        this.characterType = "";
        this.selectMouse(true);

        /*
        // highlite selection, but circle does not update
        this.mouseSprite.setDepth(10);
        this.catSprite.setDepth(10);
        this.graphics = this.add.graphics({ fillStyle: { color: 0xffffff } });
        this.circle = new Phaser.Geom.Circle(this.mouseSprite.x, this.mouseSprite.y, 16);
        this.graphics.fillCircleShape(this.circle);*/

        this.leftKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.LEFT);
        this.rightKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.RIGHT);

        this.pressX = this.add.bitmapText(16 * 8 + 4, 8 * 16, 'font', 'PRESS X TO START', 8);

        //todo get all players and add text

        this.blink = 1000;

        this.startKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.X);
    }

    update(time, delta) {
        if (this.registry.get('restartScene')) {
            this.restartScene();
        }
        this.blink -= delta;
        if (this.blink < 0) {
            this.pressX.alpha = this.pressX.alpha === 1 ? 0 : 1;
            this.blink = 500;
        }

        if(this.leftKey.isDown) {
            this.selectMouse(true);
        }

        if(this.rightKey.isDown) {
            this.selectMouse(false);
        }

        if (this.startKey.isDown) {
            this.startGame();
        }
    }

    selectMouse(isMouse) {
        if (isMouse) {
            this.mouseSprite.anims.play("mouse_down");
            this.catSprite.anims.stop();
            //this.circle = this.circle.setTo(this.mouseSprite.x, this.mouseSprite.y, 18);
            this.characterType = "mouse"
        }
        else {
            this.catSprite.anims.play("cat_down");
            this.mouseSprite.anims.stop();
            //this.circle = this.circle.setTo(this.catSprite.x, this.catSprite.y, 18);
            this.characterType = "cat";
        }

    }

    startGame() {
        this.scene.start('GameScene');
        //todo create player and send to server
    }

    restartScene() {
       // this.scene.stop('GameScene');
        this.scene.launch('GameScene');
        this.scene.bringToTop();

        this.registry.set('restartScene', false);
    }
}

export default TitleScene;
