import {Direction} from "../enum/Direction";

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
        this.anims.play(this.type + '_down');

        this.inHole = false;
        this.holeId = 0;
        this.alive = true;

        //todo Enum
        this.direction = Direction.DOWN;

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

        if (input.left) {
            this.body.setVelocityX(-speed * delta);
            this.direction = Direction.LEFT;
        } else if (input.right) {
            this.body.setVelocityX(speed * delta);
            this.direction = Direction.RIGHT;
        } else if (input.up) {
            this.body.setVelocityY(-speed * delta);
            this.direction = Direction.UP;
        } else if (input.down) {
            this.body.setVelocityY(speed * delta);
            this.direction = Direction.DOWN;
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

    /*enterScene(from, to) {
        const coordinates = this.getPosition();

        from.physics.world.pause();
        this.anims.play(this.player.type + this.direction.description, true);
        from.cameras.main.fadeIn(250);
        from.tweens.add({
            targets: this.player,
            x: coordinates[0],
            y: coordinates[1],
            duration: 1000,
            onComplete: () => {
                from.scene.start(to, {client: this.client, player: this.player, direction: this.player.direction});
            }
        });
    }*/

    getPosition() {
        console.log(this.direction.description);
        const distance = 40;
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
