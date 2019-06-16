export default class Player extends Phaser.GameObjects.Sprite {
    constructor(config) {
        super(config.scene, config.x, config.y, config.key);
        config.scene.physics.world.enable(this);
        config.scene.add.existing(this);

        this.acceleration = 0;
        this.body.maxVelocity.x = 100;
        this.body.maxVelocity.y = 100;
        this.inHole = false;
        this.holeId = 0;
        this.anims.play('mouse_left');
        this.alive = true;
        this.type = 'mouse';
        //config.scene.gridPhysics.world.enable(this);
        //this.body.collideWorldBounds = true;

        // config.scene.gridPhysics.world.addToQue(this);
    }

    update(keys, time, delta) {
        let input = {
            left: keys.left.isDown,
            right: keys.right.isDown,
            down: keys.down.isDown,
            up: keys.up.isDown
        };

        const speed = 100;

        // Stop any previous movement from the last frame
        this.body.setVelocity(0);

        if (input.left) {
            this.body.setVelocityX(-speed);
        } else if (input.right) {
            this.body.setVelocityX(speed);
        } else if (input.up) {
            this.body.setVelocityY(-speed);
        } else if (input.down) {
            this.body.setVelocityY(speed);
        }

        if (input.left) {
            this.anims.play('mouse_left', true);
        } else if (input.right) {
            this.anims.play('mouse_right', true);
        } else if (input.up) {
            this.anims.play('mouse_up', true);
        } else if (input.down) {
            this.anims.play('mouse_down', true);
        } else {
            this.anims.stop();
        }
    }

    freeze() {
        this.body.moves = false;
    }

    moveOneStep(){
        this.body.setVelocityY(100);
    }

    die() {
        // todo
        // this.alive = false;
    }

    enterHole(id) {
        // todo
    }
}
