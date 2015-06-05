import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.*;

public class OreBlob extends Blob
{
   private static final int QUAKE_ANIMATION_RATE = 100;
   private boolean lava;
   public OreBlob(String name, Point position, int rate, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, Vein.class, imgs);
      this.lava = false;

   }


   protected boolean move(WorldModel world, WorldEntity vein, ImageStore imagestore, long ticks)
   {
      if (vein == null)
      {
         return false;
      }



      if (adjacent(getPosition(), vein.getPosition()))
      {

         Quake quake = createQuake(world, vein.getPosition(), ticks, imagestore);
         world.addEntity(quake);
         quake.scheduleAnimation(world);
         vein.remove(world);
         return true;

      }
      else
      {
         Point new_pt = nextPosition(world, vein.getPosition());
         WorldEntity old_entity = world.getTileOccupant(new_pt);
         if (old_entity != null && old_entity != this)
         {
            old_entity.remove(world);
         }
         world.moveEntity(this, new_pt);
         return false;
      }
   }

   /*
   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);



            WorldEntity target = world.findNearest(getPosition(), Vein.class);
            long nextTime = ticks + getRate();

            if (target != null) {
               Point tPt = target.getPosition();

               if (move(world, target)) {
                  Quake quake = createQuake(world, tPt, ticks, imageStore);
                  world.addEntity(quake);
                  nextTime = nextTime + getRate();
               }
            }

            MobileAnimatedActor blob =transform(world);
            if(blob instanceof FlashBlob){
               this.remove(world);
               System.out.println(" instance of flashbob has been created");
               FlashBlob transformed = (FlashBlob) blob;
               world.addEntity(transformed);
               transformed.scheduleAnimation(world);
               transformed.setimgs(imageStore);
            }

            scheduleAction(world, blob, createAction(world, imageStore),
                    nextTime);

      };
      return action[0];
   }
 */


   protected Blob transform(WorldModel world)
   {
      if (world.getLava()==false)
      {
         return this;
      }
      else
      {
         return new FlashBlob(getName(), getPosition(), getRate(), getAnimationRate(), getImages());
      }
   }


}
