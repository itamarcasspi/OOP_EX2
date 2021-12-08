import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The GUI class builds The Graphical User Interface of the current Graph.
 * The class gets an input of a Directed Weighted Graph Algorithm and print
 * the graph that is loaded on the algorithm the user interface.
 * We used JPanel interface to display the current graph.
 */
public class GUI extends JPanel {

    private Directed_Weighted_Graph_Algorithms GUIgraph;
    private int minXid, maxXid, minYid, maxYid;
    private double maxX = Integer.MIN_VALUE;
    private double minX = Integer.MAX_VALUE;
    private double maxY = Integer.MIN_VALUE;
    private double minY = Integer.MAX_VALUE;
    private double absX = 0;
    private double absY = 0;
    private int resizey = 70;
    private int resizex = 70;
    private int center = -1;
    private String msg = "";
    private List<NodeData> currPath = new ArrayList<>();
    private List<Integer> ids = new ArrayList();

    private final JButton connectButton = new JButton();
    private final JButton disconnectButton = new JButton();
    private final JCheckBox nodesToggle = new JCheckBox("Nodes");
    private final JCheckBox arrowsToggle = new JCheckBox("Arrows");
    private final JCheckBox edgesToggle = new JCheckBox("Edges");
    private final JButton tspButton = new JButton();
    private final JButton saveButton = new JButton();
    private final JButton removeButton = new JButton();
    private final JButton addButton = new JButton();
    private final JButton pathDistButton = new JButton();
    private final JButton pathButton = new JButton();
    private final JButton centerButton = new JButton();
    private final JButton loadButton = new JButton();

    /**
     * The constructor gets an input of a Graph Algorithm.
     * 
     * @param graph
     */
    public GUI(DirectedWeightedGraphAlgorithms graph) {
        this.GUIgraph = (Directed_Weighted_Graph_Algorithms) graph;
        updateMinMax();
        nodesToggle.setSelected(true);
        if (GUIgraph.getGraph().edgeSize() > 100)
            arrowsToggle.setSelected(false);
        else
            arrowsToggle.setSelected(true);
        if (GUIgraph.getGraph().edgeSize() > 200)
            edgesToggle.setSelected(false);
        else
            edgesToggle.setSelected(true);

    }

    /** FOLLOWING METHODS ARE CALLED FROM THE CONSTRUCTOR **/

    public void updateMinMax() {

        Iterator<NodeData> NodeI = GUIgraph.getGraph().nodeIter();

        while (NodeI.hasNext()) {
            NodeData currNode = NodeI.next();
            if (currNode.getLocation().x() < minX) {
                minX = currNode.getLocation().x();
                minXid = currNode.getKey();
            } else if (currNode.getLocation().x() > maxX) {
                maxX = currNode.getLocation().x();
                maxXid = currNode.getKey();
            }
            if (currNode.getLocation().y() < minY) {
                minY = currNode.getLocation().y();
                minYid = currNode.getKey();
            } else if (currNode.getLocation().y() > maxY) {
                maxY = currNode.getLocation().y();
                maxYid = currNode.getKey();
            }
        }

        absX = Math.abs(maxX - minX);
        absY = Math.abs(maxY - minY);

    } // Called from the constructor

    /** FOLLOWING METHODS ARE CALLED FROM PAINTCOMPONENT **/
    /**
     * this method creates buttons that we use in the GUI.
     * the buttons that we create are: Remove, Path, Show Center and load new Graph.
     */
    private void createButtons() {

        edgesToggle.setLocation(0, 40);
        this.add(edgesToggle);

        nodesToggle.setLocation(0, 60);
        this.add(nodesToggle);

        arrowsToggle.setLocation(0, 80);
        this.add(arrowsToggle);

        connectButton.setLocation(400, 0);
        connectButton.setSize(100, 20);
        connectButton.setText("Connect");
        this.add(connectButton);
        if (connectButton.getActionListeners().length == 0) {
            connectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int src = -1;
                    int dest = -1;
                    double w = -1;
                    String srcString = JOptionPane.showInputDialog("Insert source ID");
                    if (srcString != null)
                        src = Integer.parseInt(srcString);
                    if (GUIgraph.getGraph().getNode(src) != null && src != -1) {
                        String destString = JOptionPane.showInputDialog("Insert destination ID");
                        if (destString != null)
                            dest = Integer.parseInt(destString);
                        if (dest != -1 && GUIgraph.getGraph().getNode(dest) != null) {
                            String wString = JOptionPane.showInputDialog("Insert weight");
                            if (wString != null)
                                w = Double.parseDouble(wString);
                            if (w != -1) {
                                GUIgraph.getGraph().connect(src, dest, w);
                                JOptionPane.showMessageDialog(null, msg = "Edge added");
                                GUIgraph.setPathCalculated(false);
                                GUIgraph.isConnected = true;
                                return;
                            }
                        }

                    }
                    JOptionPane.showMessageDialog(null, msg = "Connection aborted");

                }
            });
        }

        disconnectButton.setLocation(400, 20);
        disconnectButton.setSize(100, 20);
        disconnectButton.setText("Disconnect");
        this.add(disconnectButton);
        if (disconnectButton.getActionListeners().length == 0) {
            disconnectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int src = -1;
                    int dest = -1;
                    String srcString = JOptionPane.showInputDialog("Insert source ID of the edge you want to remove");
                    if (srcString != null)
                        src = Integer.parseInt(srcString);
                    if (GUIgraph.getGraph().getNode(src) != null && src != -1) {
                        String destString = JOptionPane
                                .showInputDialog("Insert destination ID of the edge you want to remove");
                        if (destString != null)
                            dest = Integer.parseInt(destString);
                        if (dest != -1 && GUIgraph.getGraph().getNode(dest) != null) {
                            if (GUIgraph.getGraph().removeEdge(src, dest) != null) {
                                JOptionPane.showMessageDialog(null, msg = "Edge removed");
                                GUIgraph.setPathCalculated(false);
                                GUIgraph.isConnected = true;
                                return;
                            } else {
                                JOptionPane.showMessageDialog(null, msg = "Edge could not be removed");
                                return;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, msg = "Disconnection aborted");
                }
            });
        }

        removeButton.setLocation(0, 0);
        removeButton.setSize(100, 20);
        removeButton.setText("Remove");
        this.add(removeButton);
        if (removeButton.getActionListeners().length == 0) {
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int target = -1;
                    String targetString = JOptionPane.showInputDialog("Insert ID to remove");
                    if (targetString != null)
                        target = Integer.parseInt(targetString);
                    if (GUIgraph.getGraph().getNode(target) != null && target != -1) {
                        GUIgraph.getGraph().removeNode(target);
                        center = -1;
                        GUIgraph.setPathCalculated(false);
                        GUIgraph.isConnected = true;
                        JOptionPane.showMessageDialog(null, msg = "Node " + target + " removed");
                    } else if (target != -1)
                        JOptionPane.showMessageDialog(null, msg = "Failed to remove node " + target);

                }
            });
        }

        addButton.setLocation(0, 20);
        addButton.setSize(100, 20);
        addButton.setText("Add");
        this.add(addButton);
        if (addButton.getActionListeners().length == 0) {
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = -1;
                    String idString = JOptionPane
                            .showInputDialog("Insert ID to add\nMake sure you don't override any existing nodes");
                    if (idString != null)
                        id = Integer.parseInt(idString);
                    if (id != -1) {
                        String posString = JOptionPane
                                .showInputDialog("Insert Location\nMake sure you write it as 'x,y,z'");
                        if (posString != null) {
                            GUIgraph.getGraph().addNode(new Node_Data(id, posString));
                            maxX = Integer.MIN_VALUE;
                            minX = Integer.MAX_VALUE;
                            maxY = Integer.MIN_VALUE;
                            minY = Integer.MAX_VALUE;
                            absX = 0;
                            absY = 0;
                            updateMinMax();
                            center = -1;
                            GUIgraph.setPathCalculated(false);
                            GUIgraph.isConnected = true;
                            JOptionPane.showMessageDialog(null, msg = "Node " + id + " added");
                        }
                    } else if (id != -1)
                        JOptionPane.showMessageDialog(null, msg = "Failed to add node " + id);

                }
            });
        }

        pathDistButton.setLocation(300, 20);
        pathDistButton.setSize(100, 20);
        pathDistButton.setText("Path Dist");

        this.add(pathDistButton);
        if (pathDistButton.getActionListeners().length == 0) {
            pathDistButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int src = -1;
                    int dest = -1;
                    String srcString = JOptionPane.showInputDialog("Insert source");
                    if (srcString != null)
                        src = Integer.parseInt(srcString);
                    if (src != -1) {
                        String destString = JOptionPane.showInputDialog("Insert destination");
                        if (destString != null)
                            dest = Integer.parseInt(destString);
                    }
                    if (src != -1 && dest != -1)
                        JOptionPane.showMessageDialog(null, msg = "Cost: " + GUIgraph.shortestPathDist(src, dest));
                }
            });
        }

        centerButton.setLocation(200, 0);
        centerButton.setSize(100, 20);
        centerButton.setText("Center");

        this.add(centerButton);
        if (centerButton.getActionListeners().length == 0) {
            centerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (GUIgraph.isConnected()) {
                        center = GUIgraph.center().getKey();
                        msg = "The graph's center is now blue";
                    } else {
                        center = -1;
                        msg = "no center found";
                    }

                }
            });
        }

        loadButton.setLocation(100, 0);
        loadButton.setSize(100, 20);
        loadButton.setText("Load");
        this.add(loadButton);
        if (loadButton.getActionListeners().length == 0) {
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String NameOfGraph = JOptionPane.showInputDialog(
                            "Insert name of existing graph\nThe graph should be inside the data folder");
                    if (NameOfGraph != null && GUIgraph.load("data/" + NameOfGraph + ".json")) {
                        msg = "Loaded!";
                        maxX = Integer.MIN_VALUE;
                        minX = Integer.MAX_VALUE;
                        maxY = Integer.MIN_VALUE;
                        minY = Integer.MAX_VALUE;
                        absX = 0;
                        absY = 0;
                        updateMinMax();
                        center = -1;
                        GUIgraph.setPathCalculated(false);
                        nodesToggle.setSelected(true);
                        if (GUIgraph.getGraph().edgeSize() > 100)
                            arrowsToggle.setSelected(false);
                        else
                            arrowsToggle.setSelected(true);
                        if (GUIgraph.getGraph().edgeSize() > 200)
                            edgesToggle.setSelected(false);
                        else
                            edgesToggle.setSelected(true);
                    } else
                        msg = "Load failed";
                }
            });
        }

        saveButton.setLocation(100, 20);
        saveButton.setSize(100, 20);
        saveButton.setText("Save");
        this.add(saveButton);
        if (saveButton.getActionListeners().length == 0) {
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String NameOfGraph = JOptionPane.showInputDialog(
                            "Insert name for the graph\nThe graph will be inside the data folder\nThe save will override existing graphs");
                    if (NameOfGraph != null && GUIgraph.save("data/" + NameOfGraph + ".json"))
                        msg = "Saved!";
                    else
                        msg = "Save failed";

                }
            });
        }

        pathButton.setLocation(300, 0);
        pathButton.setSize(100, 20);
        pathButton.setText("Path");
        this.add(pathButton);
        if (pathButton.getActionListeners().length == 0) {
            pathButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int src = -1;
                    int dest = -1;
                    String srcString = JOptionPane.showInputDialog("Insert source");
                    if (srcString != null)
                        src = Integer.parseInt(srcString);
                    if (src != -1) {
                        String destString = JOptionPane.showInputDialog("Insert destination");
                        if (destString != null)
                            dest = Integer.parseInt(destString);
                    }
                    if (src != -1 && dest != -1) {
                        currPath.clear();
                        ids.clear();
                        currPath = GUIgraph.shortestPath(src, dest);

                        for (int i = 0; i < currPath.size(); i++) {
                            ids.add(currPath.get(i).getKey());
                        }
                    }

                }
            });
        }

        tspButton.setLocation(200, 20);
        tspButton.setSize(100, 20);
        tspButton.setText("TSP");
        this.add(tspButton);
        if (tspButton.getActionListeners().length == 0) {
            tspButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String srcString = JOptionPane.showInputDialog("How many nodes do you want to visit?");
                    int n = Integer.parseInt(srcString);
                    List<NodeData> cities = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        String stop = JOptionPane.showInputDialog("Insert a stop");
                        cities.add(GUIgraph.getGraph().getNode(Integer.parseInt(stop)));

                    }
                    currPath.clear();
                    currPath = GUIgraph.tsp(cities);

                    ids.clear();
                    for (int i = 0; i < currPath.size(); i++) {
                        ids.add(currPath.get(i).getKey());
                    }
                    JOptionPane.showMessageDialog(null, msg = "Order: " + ids);
                }
            });
        }

    }

    /**
     * The following function calculate where each node is located in the screen by
     * the X,Y coordinates of the Geographic location of the node.
     * 
     * @param g
     */
    private void paintNodes(Graphics g) {
        double tX = getWidth() / absX * 0.8;
        double tY = getHeight() / absY * 0.8;
        Iterator<NodeData> NodeI = GUIgraph.getGraph().nodeIter();
        g.setColor(Color.red);
        while (NodeI.hasNext()) {
            NodeData currNode = NodeI.next();
            double Geox = currNode.getLocation().x();
            double Geoy = currNode.getLocation().y();

            double x = (Geox - minX) * tX + resizex;
            double y = (Geoy - minY) * tY + resizey;
            if (currNode.getKey() == center)
                g.setColor(Color.blue);
            if (ids.contains(currNode.getKey()))
                g.setColor(Color.magenta);

            g.fillOval((int) x - 5, (int) y - 5, 11, 11);
            g.setColor(Color.red);
            g.drawString(currNode.getKey() + "", (int) x - 5, (int) y - 5);
        }

    } // Called from paintComponent

    /**
     * The following function calculate where each Edge is located in the screen by
     * * the X,Y coordinates of the Geographic location of the source node and
     * destination node.
     * 
     * @param g
     */
    private void paintEdges(Graphics g) {
        double tX = getWidth() / absX * 0.8;
        double tY = getHeight() / absY * 0.8;
        Iterator<EdgeData> EdgeI = GUIgraph.getGraph().edgeIter();
        g.setColor(Color.black);
        while (EdgeI.hasNext()) {
            EdgeData currEdge = EdgeI.next();

            double srcGeox = GUIgraph.getGraph().getNode(currEdge.getSrc()).getLocation().x();
            double srcGeoy = GUIgraph.getGraph().getNode(currEdge.getSrc()).getLocation().y();
            double destGeox = GUIgraph.getGraph().getNode(currEdge.getDest()).getLocation().x();
            double destGeoy = GUIgraph.getGraph().getNode(currEdge.getDest()).getLocation().y();

            double srcx = (srcGeox - minX) * tX + resizex;
            double srcy = (srcGeoy - minY) * tY + resizey;
            double destx = (destGeox - minX) * tX + resizex;
            double desty = (destGeoy - minY) * tY + resizey;
            g.drawLine((int) srcx, (int) srcy, (int) destx, (int) desty);
            // if (srcx>destx) g.drawString("R:"+currEdge.getWeight(),
            // (int)((destx+srcx)/2),(int)((desty+srcy)/2));
            // else g.drawString("L:"+currEdge.getWeight(),
            // (int)(((destx+srcx)/2)),(int)((desty+srcy)/2)-25);
            if (arrowsToggle.isSelected())
                paintArrows(destx, srcx, desty, srcy, g);
        }

    } // Called from paintComponent

    /**
     * the following function draws an image of an arrow that represent the
     * direction of an edge.
     * the function gets X and Y of the destination and source nodes.
     * then calculates the rotation angle that we need to rotate the arrow image.
     * 
     * @param destx
     * @param srcx
     * @param desty
     * @param srcy
     * @param g
     */
    public void paintArrows(double destx, double srcx, double desty, double srcy, Graphics g) {
        double arrowX = (destx - srcx) * 0.8 + srcx;
        double arrowY = (desty - srcy) * 0.8 + srcy;

        Image icon = null;
        try {
            icon = ImageIO.read(new File("data/arrow-left-icon-17.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Line2D lineA = new Line2D.Double(srcx, srcy, destx, desty);
        Line2D lineB = new Line2D.Double(destx, srcy, destx, srcy);

        double tanAngle = angleBetween2Lines(lineA, lineB);
        BufferedImage bufferedIcon = toBufferedImage(icon);

        bufferedIcon = rotate(bufferedIcon, Math.toDegrees(tanAngle));
        g.drawImage(bufferedIcon, (int) arrowX - 5, (int) arrowY - 5, (int) (0.0158 * getWidth()),
                (int) (0.0158 * getHeight()), null);
    }

    /**
     * the function calculate the angle between 2 lines.
     * the calculate returns how much we need to rotate by tangents.
     * 
     * @param line1
     * @param line2
     * @return
     */
    public static double angleBetween2Lines(Line2D line1, Line2D line2) {
        double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                line1.getX1() - line1.getX2());
        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                line2.getX1() - line2.getX2());
        return angle1 - angle2;
    }

    /**
     * we need to convert the arrow Image to buffered image, to be able to use
     * buffered Image rotation function.
     * so we use this function to convert regular Image to Buffered Image
     * 
     * @param img
     * @return
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /**
     * this function is getting Buffered Image and rotate it with the angle needed.
     * 
     * @param bimg
     * @param angle
     * @return
     */
    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin),
                newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww - w) / 2, (newh - h) / 2);
        graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }

    /**
     * function to create background.
     * not used currently in the project.
     * 
     * @param g
     */
    private void paintBackground(Graphics g) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Image map = t.getImage("data/map.png");
        g.drawImage(map, 10, 10, getWidth(), getHeight(), null); // loads ariel's map to the background, and sticks it
                                                                 // to the size of the screen
    } // Called from paintComponent

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setVisible(false);

        g.drawString(msg, 80, 53);
        // paintBackground(g);
        createButtons();
        if (nodesToggle.isSelected())
            paintNodes(g);
        if (edgesToggle.isSelected())
            paintEdges(g);
        this.setVisible(true);

    }
}
