import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by oka on 5/5/17.
 */
public class graphColoring {
    //IO variables
    PrintWriter out1;
    Scanner scan1;

    //data structures
    int[][] matrix;
    Node usedColorTop;
    int newColor;
    int numNode;
    int currentNode;

    public static void main(String[] args){
        graphColoring data=new graphColoring();
        data.algorithm(args);
    }

    public graphColoring(){
        usedColorTop=null;
        newColor=0;
        numNode=0;
        currentNode=0;
    }


    void algorithm(String[] args){
        loadMatrix(args);
        while(!allNodesColored())coloring();

        //End of the program printing
        sopl("Number of color used is: "+newColor,out1);
        matrixPrint("Printing Final Matrix",out1);
        sopl("All nodes are colored",out1);

        close();
    }

    //From the input file : load the adjacentMatrix
    void loadMatrix(String[] args) {
        try {
            scan1 = new Scanner(new File(args[0]));
            out1 = new PrintWriter((new File(args[1])));

            int x, y;
            int numEdges = 0;
            numNode = scan1.nextInt();//new node
            matrix = new int[numNode + 1][numNode + 1];
            while (scan1.hasNextInt()) {
                x = scan1.nextInt();
                y = scan1.nextInt();
                matrix[x][y] = 1;
                matrix[y][x] = 1;
                numEdges++;
            }
            sopl("number of nodes: " + numNode, out1);
            sopl("number of edges: " + numEdges, out1);

            matrixPrint("Printing after loading the matrix",out1);

            //prepare for coloring
            newColor=0;
            currentNode=0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     void coloring(){
        currentNode++;
        sopl("current Node is "+ currentNode,out1);
        matrixPrint("Printing before coloring new node",out1);

        int usedColor=findUsedColor(currentNode);//find a color that can be used to color n
        if(usedColor>0){
            sopl("used color found that can be colored: "+usedColor,out1);
            matrix[currentNode][currentNode]=usedColor;
        }//found a color that can be used to color n
        else{
            newColor++;
            sopl("new color has to be used: "+newColor,out1);
            matrix[currentNode][currentNode]=newColor;
            pushUsedColor(newColor);
            listPrint(out1);
        }//did not found a color and therefore use a new color

         matrixPrint("Printing after coloring new node",out1);

    }

    int findUsedColor(int Node){
        int foundColor=0;
        if(!isEmpty()) {
            Node track = usedColorTop.next;
            while (track != null) {
                if (!checkAdjacent(Node, track.ColorID)) {
                    foundColor = track.ColorID;
                    break;
                }//if the adjacent nodes do not have that color, use that color
                track = track.next;
            }
        }
        return foundColor;
    }

    public boolean checkAdjacent(int currentNode, int color) {
        boolean SameColor = false;//no same color
        int adjacentNodeColor=0;
        for (int y = 1; y < matrix[0].length; y++) {
            if (matrix[currentNode][y] == 1) {//if we have an adjacent node
                adjacentNodeColor = matrix[y][y];
                if(adjacentNodeColor>0) {//if the adjacent node is colored, check its color
                    if (adjacentNodeColor==color) {
                        SameColor = true;//same color found
                    }
                }
            }
        }
        return SameColor;
    }


    public boolean allNodesColored(){
        boolean colored=true;
        for(int x=1;x<matrix.length;x++){
            if(matrix[x][x]==0){
                colored=false;
                break;
            }
        }
        return colored;
    }

    //Linked list Stack Functions
    void pushUsedColor(int color){
        Node newColor=new Node(color);
        if(isEmpty()){
            newColor.next=null;
        }
        else{
            newColor.next=usedColorTop;
        }
        usedColorTop=newColor;
    }
    boolean isEmpty(){
        return (usedColorTop==null);
    }


    //close all files
    void close() {
        scan1.close();
        out1.close();
    }

    //Node class for list of colors
    public class Node {
        int ColorID;
        Node next;

        Node(int ID) {
            this.ColorID= ID;
            this.next = null;
        }
    }

    //Printing Functions for Debugging purposes
    void listPrint(PrintWriter out1) {
        Node track;
        track = usedColorTop;
        sop("head->",out1);
        while (track!= null) {
            sop(track.ColorID + "->",out1);
            track = track.next;
        }
        sop("null",out1);
    }


    void matrixPrint(String message,PrintWriter out1) {
        sopl(message,out1);
        //print columns headers
        sop("   ",out1);
        int yCount = 0;
        for (int x = 1; x < matrix.length; x++) {
            if(x<10)sop(" " + x + "  ",out1);
            else sop(" " + x + " ",out1);
            yCount++;
        }
        sopl("",out1);

        int xCount = 0;
        for (int x = 1; x < matrix.length; x++) {
            for (int y = 1; y < matrix[0].length; y++) {
                if (y == 1) {

                    xCount++;
                    if(xCount<10)sop(" " + xCount + "|",out1);
                    else sop("" + xCount + "|",out1);

                }
                if (matrix[x][y] != 0) {
                    sop("[" + matrix[x][y] + "] ",out1);
                } else {
                    sop("[" + " " + "] ",out1);
                }
            }
            sopl("",out1);
        }
    }

    void sop(Object e) {
        System.out.print(e + "");
    }

    void sopl(Object e) {
        System.out.print(e + "\n");
    }

    void sop(Object e, PrintWriter out) {
        out.write(e + "");
    }

    void sopl(Object e, PrintWriter out) {
        out.write(e + "\n");
    }

}
