import processing.core.PImage;

import java.util.List;

/**
 * Created by James Abundis on 6/4/2015.
 */
public class LavaEntity extends WorldEntity
{
    public LavaEntity(String name, Point position, List<PImage> imgs)
    {
        super(name, position, imgs);
    }

    public String toString()
    {
        return String.format("LavaEntity %s %d %d", this.getName(),
                this.getPosition().x, this.getPosition().y);
    }
}
