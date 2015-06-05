import processing.core.PImage;

import java.util.List;

/**
 * Created by James Abundis on 6/5/2015.
 */
public abstract class Blob extends MobileAnimatedActor {

    private static final int QUAKE_ANIMATION_RATE = 100;
    private boolean lava;
    private Class<?> seeking;
    public Blob(String name, Point position, int rate, int animation_rate, Class<?> seeking,
                   List<PImage> imgs)
    {
        super(name, position, rate, animation_rate, imgs);
        this.lava = false;
        this.seeking=seeking;

    }
    public void setimgs(ImageStore i_store)
    {
        this.imgs = i_store.get("newblob");
    }

    protected boolean canPassThrough(WorldModel world, Point pt)
    {
        return !world.isOccupied(pt) || world.getTileOccupant(pt) instanceof Ore;
    }

    public Action createAction(WorldModel world, ImageStore imageStore)
    {
        Action[] action = { null };
        action[0] = ticks -> {
            removePendingAction(action[0]);

            WorldEntity target = world.findNearest(getPosition(), seeking);

            Blob newEntity = this;
            if (move(world, target, imageStore, ticks) || world.getLava() == true)
            {
                newEntity = tryTransform(world);
                if(newEntity instanceof FlashBlob){
                    newEntity.setimgs(imageStore);
                }
            }

            scheduleAction(world, newEntity,
                    newEntity.createAction(world, imageStore),
                    ticks + newEntity.getRate());
        };
        return action[0];
    }
    //Methods to be implemented in subclasses
    protected abstract boolean move(WorldModel world, WorldEntity target, ImageStore i, long ticks);

    protected abstract Blob transform(WorldModel world);


    private Blob tryTransform(WorldModel world)
    {
        Blob newEntity = transform(world);
        if (this != newEntity)
        {
            this.remove(world);
            world.addEntity(newEntity);
            newEntity.scheduleAnimation(world);
        }
        return newEntity;
    }

    protected Quake createQuake(WorldModel world, Point pt, long ticks,
                              ImageStore imageStore)
    {
        Quake quake = new Quake("quake", pt, QUAKE_ANIMATION_RATE,
                imageStore.get("quake"));
        quake.schedule(world, ticks, imageStore);
        return quake;
    }




}
