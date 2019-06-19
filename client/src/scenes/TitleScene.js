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

        this.leftKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.LEFT);
        this.rightKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.RIGHT);

        this.add.bitmapText(16 * 7, 6 * 16, 'font', 'CHOOSE YOUR CHARACTER', 8);
        this.pressX = this.add.bitmapText(16 * 8 + 4, 8 * 16, 'font', 'PRESS X IF READY', 8);
        this.playerList = '';
        this.blink = 1000;

        this.startKey = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.X);

       // this.players = this.updatePlayerList()
        //todo get all players and add info text
    }

    async updatePlayerList(players) {
        console.log("update players method...")

        await fetch(this.client.getAllPlayers())
            .then(response => {
                console.log("All my players: ")
                console.log(response)
                return response;
            });
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
            this.catSprite.setFrame("cat_up");
            this.characterType = "mouse"
        }
        else {
            this.catSprite.anims.play("cat_down");
            this.mouseSprite.anims.stop();
            this.mouseSprite.setFrame("mouse_up");
            this.characterType = "cat";
        }
    }

    startGame() {
        this.scene.start('SurfaceScene', {newGame: true});
    }

    restartScene() {
        this.scene.launch('SurfaceScene');
        this.scene.bringToTop();
        this.registry.set('restartScene', false);
    }
}

export default TitleScene;
