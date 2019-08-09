import {Direction} from '../enum/Direction';

export default class Player extends Phaser.GameObjects.Sprite {
    constructor(config) {
        super(config.scene, config.x, config.y, config.key);
        config.scene.physics.world.enable(this);
        config.scene.add.existing(this);
        config.scene.physics.add.collider(this, config.scene);

        this.scene = config.scene;
        this.body.maxVelocity.x = 100;
        this.body.maxVelocity.y = 100;

        this.type = config.characterType;
        this.direction = Direction.DOWN;
        this.anims.play(this.type + "_" + this.direction.description);

        this.inHole = false;
        this.holeId = 0;
        this.alive = true;
        this.isMoving = false;
        this.steps = 0;

    }

    update(keys, time, delta) {
        let input = {
            left: keys.left.isDown,
            right: keys.right.isDown,
            down: keys.down.isDown,
            up: keys.up.isDown,
            debug: keys.debug.isDown
        };

        this.setDirection(input);

        if(this.isMoving)
            return;

        // Stop any previous movement from the last frame
        this.body.setVelocity(0);


        if (input.left) {
            this.direction = Direction.LEFT;
            if(!this.scene.isCollision())
                this.moveOneStep();
        } else if (input.right) {
            this.direction = Direction.RIGHT;
            if(!this.scene.isCollision())
                this.moveOneStep();
        } else if (input.up) {
            if(!this.scene.isCollision())
                this.moveOneStep();
        } else if (input.down) {
            if(!this.scene.isCollision())
                this.moveOneStep();
        } else {
            this.anims.stop();
        }

        if (input)
            this.anims.play(this.type + "_" + this.direction.description, true);
    }

    die() {
        // todo
        // this.alive = false;
    }

    enterHole(id) {
        // todo
    }

    moveOneStep() {
        //check if wall
        this.isMoving = true;
        const coordinates = this.getPosition();
        this.scene.physics.world.pause();
        this.anims.play(this.type + this.direction.description, true);
        this.scene.tweens.add({
            targets: this,
            x: coordinates[0],
            y: coordinates[1],
            duration: 500,
            onComplete: () => {
                this.scene.physics.world.resume();
                this.steps++;
                this.isMoving = false;
            }
        });
    }

    setDirection(input) {
        if (input.left)
            this.direction = Direction.LEFT;
        else if(input.right)
                this.direction = Direction.RIGHT;
        else if(input.up)
                this.direction = Direction.UP;
        else if(input.down)
                this.direction = Direction.DOWN;
    }

    getPosition() {
        const distance = 32;
        switch (this.direction) {
            case Direction.LEFT:
                return [this.x - distance, this.y];
            case Direction.RIGHT:
                return [this.x + distance, this.y];
            case Direction.UP:
                return [this.x, this.y - distance];
            case Direction.DOWN:
                return [this.x, this.y + distance];
        }
    }
}
