import java.util.ArrayList;

/**
 * Created by James Abundis on 5/27/2015.
 */
public class NodeWorld {

    private Node [][] nWorld;
    private ArrayList<Node> list;

    public NodeWorld( ArrayList<Node> list, Node[][] nWorld)
    {
        this.nWorld = nWorld;
        this.list = list;
    }
    public Node[][] getnWorld()
    {
        return this.nWorld;
    }
    public ArrayList<Node> getList()
    {
        return this.list;
    }
}
