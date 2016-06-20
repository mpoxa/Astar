/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.rasethe.dstv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author philipr
 */
public class Search {
    
    public static void main(String[] args) {

        //initialize the graph base on map
        Node n1 = new Node("@", 1);
        Node n2 = new Node("^", 2);
        Node n3 = new Node(".", 3);
        Node n4 = new Node("*", 6);
        Node n5 = new Node("X", 2500);

        n1.adjacencies = new Edge[]{
            new Edge(n1, 0),
           
        };

         n2.adjacencies = new Edge[]{
            new Edge(n1, 2),
           
        };
          n3.adjacencies = new Edge[]{
            new Edge(n1, 3),
           
        };
           n4.adjacencies = new Edge[]{
            new Edge(n1, 6),
           
        };
            n5.adjacencies = new Edge[]{
            new Edge(n5, 2500),
           
        };
        AstarSearch(n1, n5);

        List<Node> path = printPath(n5);

        System.out.println("Path: " + path);

    }

    public static List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<Node>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public static void AstarSearch(Node source, Node goal) {

        Set<Node> explored = new HashSet<Node>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>() {
            //override compare method
            public int compare(Node i, Node j) {
                if (i.f_scores > j.f_scores) {
                    return 1;
                } else if (i.f_scores < j.f_scores) {
                    return -1;
                } else {
                    return 0;
                }
            }

        }
        );

        //cost from start
        source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while ((!queue.isEmpty()) && (!found)) {

            //the node in having the lowest f_score value
            Node current = queue.poll();

            explored.add(current);

            //goal found
            if (current.value.equals(goal.value)) {
                found = true;
            }

            //check every child of current node
            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;


                /*if child node has been evaluated and 
                                the newer f_score is higher, skip*/
                if ((explored.contains(child))
                        && (temp_f_scores >= child.f_scores)) {
                    continue;
                } /*else if child node is not in queue or 
                                newer f_score is lower*/ else if ((!queue.contains(child))
                        || (temp_f_scores < child.f_scores)) {

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);

                }

            }

        }

    }

    
}
