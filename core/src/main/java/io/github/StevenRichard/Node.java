package io.github.StevenRichard;

public class Node {
    int x;
    int y;
    double qValue;

    public Node(){
        this.x = 0;
        this.y = 0;
        this.qValue = 0;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        qValue = 0;
    }


    public Node(int x, int y, double qValue){
        this.x = x;
        this.y = y;
        this.qValue = qValue;
    }

    public boolean compare(Node node){
        return x == node.x && y == node.y;
    }

}
