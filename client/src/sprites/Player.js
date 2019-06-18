export default class Player extends Phaser.GameObjects.Sprite {
    constructor(config) {
        super(config.scene, config.x, config.y, config.key);
        config.scene.physics.world.enable(this);
        config.scene.add.existing(this);
        config.scene.physics.add.collider(this, config.scene);

        this.scene = config.scene;

        //config.scene.gridPhysics.world.enable(this);

        this.acceleration = 0;
        this.body.maxVelocity.x = 100;
        this.body.maxVelocity.y = 100;

        this.type = 'mouse';
        this.anims.play(this.type + '_left');

        this.inHole = false;
        this.holeId = 0;
        this.alive = true;

        //todo Enum
        this.direction = "";

        // config.scene.gridPhysics.world.addToQue(this);
    }

    update(keys, time, delta) {
        let input = {
            left: keys.left.isDown,
            right: keys.right.isDown,
            down: keys.down.isDown,
            up: keys.up.isDown,
            debug: keys.debug.isDown
        };

        const speed = 60;

        // Stop any previous movement from the last frame
        this.body.setVelocity(0);

        /*if(input.debug) {
            this.moveOneStep(this.scene)
        }
*/
        if (input.left) {
            this.body.setVelocityX(-speed * delta);
            this.direction = "left";
        } else if (input.right) {
            this.body.setVelocityX(speed * delta);
            this.direction = "right";
        } else if (input.up) {
            this.body.setVelocityY(-speed * delta);
            this.direction = "up";
        } else if (input.down) {
            this.body.setVelocityY(speed * delta);
            this.direction = "down";
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
        this.body.setVelocity(0);
    }

    moveOneStep(scene){
        console.log("move a step down");
        scene.physics.moveTo(this, this.x, this.y + 32, 100, 100);
    }

    die() {
        // todo
        // this.alive = false;
    }

    enterHole(id) {
        // todo
    }
}
