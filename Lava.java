import processing.core.PImage;
import java.util.List;

/**
 * Created by James Abundis on 6/4/2015.
 */
public class Lava extends Background
{
    private Point position;
    public Lava(String name, List< PImage > imgs, Point position)
    {
        super(name, imgs);
        this.position = position;
    }

    public Point getPosition()
    {
        return position;
    }
    public void setPosition(Point pos)
    {
        this.position = pos;
    }
}
