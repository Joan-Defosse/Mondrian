package Main;
import TreePck.*;

import java.awt.*;

public class Painter {

    public static void main(String[] args) {

        System.out.println();
    }
    public Tree initTree() {
        TreeSettings settings = new TreeSettings(5, 0.7, 10,10, 1000);
        Tree tree = new Tree(Color.white, settings, 80, 100);
        Tree L = new Tree();
        Tree R = new Tree();
        
        return tree;
    }
    public Tree generateRandomTree(Tree tree) {
        return tree;
    }
}
