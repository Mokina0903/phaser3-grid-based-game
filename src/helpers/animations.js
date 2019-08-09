export default function makeAnimations(scene) {
    let config = {

    };

    ['mouse', 'cat'].forEach((char) => {
        ['left', 'right', 'up', 'down'].forEach(
            (dir) => {
                config = {
                    key: char + '_' + dir,
                    frames: scene.anims.generateFrameNames('atlas', {
                        prefix: char + '_' + dir + '.',
                        start: 0,
                        end: 1,
                        zeroPad: 3
                    }),
                    frameRate: 10,
                    repeat: -1
                };
                scene.anims.create(config);
            }
        );
    });
}
