import java.util.ArrayList;

/**
 * Created by James Abundis on 5/26/2015.
 */
public class Node extends Point
{
    private Node cameFrom;

    private int h_score;
    private int f_score;
    private int g_score;

    public Node(int x, int y, Node came)
    {
        super(x, y);
        this.cameFrom = came;
    }

    public Node getCameFrom(){
        return this.cameFrom;

    }

    public void setCameFrom(Node input)
    {
        this.cameFrom = input;
    }

    public void setH_score(Node other)
    {
        this.h_score = MobileAnimatedActor.heuristic_cost_estimate(this, other);
    }
    public void setF_score(int G_score)
    {
        this.f_score = this.h_score + G_score;
    }
    public int getH_score()
    {
        return this.h_score;
    }
    public int getF_score()
    {
        return this.f_score;
    }

    public void setG_score(int moves){
        g_score = moves;
    }

    public void setG_score_manual(int score)
    {
        g_score = score;
    }

    public int getG_score(){
        return g_score;
    }
    public static Node find_min_F_score(ArrayList<Node> nodes)
    {
        int mindex = 0;
        for(int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).getF_score() < nodes.get(mindex).getF_score())
            {
                mindex = i;
            }
        }
        return nodes.get(mindex);
    }
    public boolean equals(Object other)
    {   if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Node))return false;

        Node n= (Node) other;

        return  (this.x == n.x && this.y == n.y);
    }
}
