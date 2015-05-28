import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{

   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   // Find the path, return the first point towards that path.
   protected static int heuristic_cost_estimate(Node start, Node goal)
   {
      int dx = start.x - goal.x;
      int dy = start.y - goal.y;

      return Math.abs(dx) + Math.abs(dy);
   }
/*
   protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      int horiz = Integer.signum(dest_pt.x - getPosition().x);
      Point new_pt = new Point(getPosition().x + horiz, getPosition().y);

      if (horiz == 0 || !canPassThrough(world, new_pt))
      {
         int vert = Integer.signum(dest_pt.y - getPosition().y);
         new_pt = new Point(getPosition().x, getPosition().y + vert);

         if (vert == 0 || !canPassThrough(world, new_pt))
         {
            new_pt = getPosition();
         }
      }

      return new_pt;
   }

*/
   protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      ArrayList<Point> path;
      path = A_swag(world, dest_pt);
      if(path==null)
      {
        System.out.println("the path is null");
         return this.getPosition();
      }
      //Node nextPos= path.get(1);
      //return new Point(nextPos.x,nextPos.y);

      System.out.println(path.size());
      return path.get(1);
   }
   //protected ArrayList<Point>dfs()

   protected ArrayList<Point> A_swag(WorldModel world, Point goal)
   {
      //Set up and initialize a world of nodes
      Node[][] node_world = new Node[40][30];
      for (int i = 0; i < 40; i++)
      {
         for(int j = 0; j < 30; j++)
         {
            if((!world.isOccupied(new Point(i,j)) || (i == this.getPosition().x && j == this.getPosition().y) || (i == goal.x && j == goal.y)))
            {
               Node pos = new Node(i, j, null);
               node_world[i][j] = pos;
            }
         }
      }

      //this helps calculate scores of start
      Point start_pos = this.getPosition();
      Node start_node = node_world[start_pos.x][start_pos.y];
      //Node start_node = new Node(start_pos.x, start_pos.y, null);
      Node goal_node = node_world[goal.x][goal.y];

      ArrayList<Node> closedSet= new ArrayList<Node>();
      ArrayList<Node> openSet= new ArrayList<Node>();
      openSet.add(start_node);

      start_node.setG_score(0);
      start_node.setH_score(goal_node);
      start_node.setF_score(start_node.getG_score());
      //reminder: int f_score = g_score+ heuristic_cost_estimate(start, goal);

      while(openSet.size()>0)
      {   System.out.println("closed set" + " " + closedSet.size());
         System.out.println("openSet" + " " + openSet.size());

         Node current = Node.find_min_F_score(openSet);

         //checks if we have arrive at goal
         if (current.x == goal_node.x && current.y == goal_node.y)
            //reconstruct path
            {
               System.out.println(" Goal is found");
               ArrayList<Point> total_path = new ArrayList<>();
               //total_path.add(new Point(current.x, current.y));
               while(current.getCameFrom() != null)
               {
                  current= current.getCameFrom();
                  System.out.println(" In reconstruction while loop");
                  total_path.add(0, new Point(current.x, current.y));
               }
               return total_path;
            }
            openSet.remove(current);
            closedSet.add(current);


            for(Node neighbor: findNeighbors(world, node_world, current, goal_node))
            {
               if(closedSet.contains(neighbor))
               {
                  continue;
               }
               int tentative_g_score = current.getG_score() + 1;


               if(!(openSet.contains(neighbor))||tentative_g_score < neighbor.getG_score())
               {

                  neighbor.setCameFrom(current);

                  neighbor.setH_score(goal_node);
                  neighbor.setG_score(tentative_g_score); //Gives g score because we moved one
                  neighbor.setF_score(neighbor.getG_score());
                  if (!(openSet.contains(neighbor)))
                  {
                     openSet.add(neighbor);
                  }
               }
            }
       }
      //System.out.println("Open set is not greater than zero");
       return null;
   }
/*
   protected ArrayList<Point> reconstruct_path(Node[][] came_from_map, Node current)
   {
      ArrayList<Point> total_path = new ArrayList();
      total_path.add(new Point(current.x, current.y));
      while (contains(came_from_map, current)) //redefined contains func to look for current node in map
      {
         Node fun = came_from_map[current.x][current.y];
         current = fun.getCameFrom();
         total_path.add(new Point(current.x, current.y));
      }
      return total_path;
   }
   */


   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected ArrayList<Node> findNeighbors(WorldModel world, Node[][] node_world, Node current, Node goal)
   {
      ArrayList<Node> neighbors = new ArrayList<>();
      Point curPt= new Point(current.x,current.y);
      Point goalpt = new Point(goal.x, goal.y);

      for (int x = 0; x < 40; x++)
      {
         for (int y = 0; y < 30; y++)
         {
            if(node_world[x][y]!=null)
            {
               Node n = node_world[x][y];
               Point n_pt = new Point(n.x, n.y);
               if (adjacent(curPt, n_pt))
               {
                  neighbors.add(n);
               }
            }
            else
            {
               if(adjacent(new Point(x,y), curPt) && (x == goalpt.x && y == goalpt.y))
               {
                  Node n = new Node(x, y, null);
                  neighbors.add(n);
               }
            }
         }
      }
      //System.out.println(neighbors.size());
      return neighbors;
   }

   /*
   protected NodeWorld findNeighbors(WorldModel world, Node current, Node[][] came_from){

      ArrayList<Node> neighbors = new ArrayList<>();
      Point pt1= new Point(this.getPosition().x-1, this.getPosition().y);
      Point pt2 = new Point(this.getPosition().x+1,this.getPosition().y);
      Point pt3= new Point(this.getPosition().x, this.getPosition().y+1);
      Point pt4= new Point(this.getPosition().x, this.getPosition().y-1);


      if(canPassThrough(world,pt1)){
         Node n1= new Node(pt1.x, pt1.y,current);
         came_from[pt1.x][pt1.y]=n1;
         neighbors.add(n1);

     }
      if(canPassThrough(world,pt2)){
         Node n2= new Node(pt2.x, pt2.y,current);
         came_from[pt2.x][pt2.y]=n2;
         neighbors.add(n2);

      }
      if(canPassThrough(world,pt3)){
         Node n3= new Node(pt3.x, pt3.y,current);
         came_from[pt3.x][pt3.y]=n3;
         neighbors.add(n3);

      }
      if(canPassThrough(world,pt4)){
         Node n4= new Node(pt4.x, pt4.y,current);
         came_from[pt4.x][pt4.y]=n4;
         neighbors.add(n4);
      }

     // System.out.println(openSet.size());
      return new NodeWorld(neighbors, came_from);
   */

   public boolean contains(Node[][] map, Node n)
   {
      if (n == null)
      {
         return false;
      }
      else
      {
         for (int x = 0; x < 40; x++)
         {
            for (int y = 0; y < 30; y++)
            {
               if (map[x][y].equals(n))
               {
                  return true;
               }
            }
         }
      }
      return false;
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
