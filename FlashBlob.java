import processing.core.PImage;

import java.util.List;


public class FlashBlob extends Blob {

    private static final int QUAKE_ANIMATION_RATE = 100;
    private boolean lava;

    public FlashBlob(String name, Point position, int rate, int animation_rate,
                     List<PImage> imgs) {
        super(name, position, rate, animation_rate, LavaEntity.class, imgs);
        this.lava = false;

    }

    protected Blob transform(WorldModel world) {
        return this;
    }


    /*public Action createAction(WorldModel world, ImageStore imageStore)

    {
        Action[] action = {null};
        action[0] = ticks -> {
            removePendingAction(action[0]);

            System.out.println("lava is true");

            WorldEntity target = world.findNearest(getPosition(), LavaEntity.class);
            long nextTime = ticks + getRate();


            if (target != null) {
                Point tPt = target.getPosition();

                if (move(world, target)) {
                    int rand = ((int) (Math.random() * 3));
                    System.out.println(rand);

                    //Insert action to remove blob and place quake in its position
                    if (!(world.isOccupied(new Point(0, 0)) || rand == 0)) {
                        this.setPosition(new Point(0, 0));
                    } else if (!(world.isOccupied(new Point(0, 5)) || rand == 1)) {
                        System.out.println("position set to 2nd portal");
                        this.setPosition(new Point(0, 5));
                    } else if (!(world.isOccupied(new Point(0, 10)) || rand == 2)) {
                        System.out.println("position set to 3rd portal");

                        this.setPosition(new Point(0, 10));
                    }
                    //  Quake quake = createQuake(world, getPosition(), ticks, imageStore);
                    nextTime = nextTime + getRate();
                }

            }
            scheduleAction(world, this, createAction(world, imageStore), nextTime);
        };
        return action[0];


    }
    */

    protected boolean move(WorldModel world, WorldEntity lava, ImageStore imagestore, long ticks) {
        if (lava == null) {
            return false;
        }


        if (adjacent(getPosition(), lava.getPosition())) {

            int rand = ((int) (Math.random() * 3));
            System.out.println(rand);

            //Insert action to remove blob and place quake in its position
            if (!(world.isOccupied(new Point(0, 0)) || rand == 0)) {
                this.setPosition(new Point(0, 0));
            } else if (!(world.isOccupied(new Point(0, 5)) || rand == 1)) {
                System.out.println("position set to 2nd portal");
                this.setPosition(new Point(0, 5));
            } else if (!(world.isOccupied(new Point(0, 10)) || rand == 2)) {
                System.out.println("position set to 3rd portal");

                this.setPosition(new Point(0, 10));
            }
            return true;
        }

        else{
            Point new_pt = nextPosition(world, lava.getPosition());
            WorldEntity old_entity = world.getTileOccupant(new_pt);
            if (old_entity != null && old_entity != this)
            {
                old_entity.remove(world);
            }
            world.moveEntity(this, new_pt);
            return false;

        }
    }


}
