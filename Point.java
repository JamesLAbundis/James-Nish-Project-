import java.util.ArrayList;

public class Point
{
   public final int x;
   public final int y;
   private int h_score;
   private int f_score;

   private int g_score;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

}

