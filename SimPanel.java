import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class SimPanel extends JPanel {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private Graph<Integer> graph;
    private Map<Integer, Vector> pos;
    private FruchtermanReingold fruchtermanReingold;
    private static float k = 80;
    private static int numNodes = 30;
    private static float p = 0.3f;
    Timer t;
    DrawPanel draw;


    //Generates and renders the FTR algo on a graph with n nodes, randomly generated based on constant p
    public SimPanel() {



        setLayout(new GridBagLayout());


        JPanel inputFieldPane = new JPanel();
        inputFieldPane.setLayout(new BoxLayout(inputFieldPane, BoxLayout.Y_AXIS));

        JLabel kFieldLabel = new JLabel("K Value:");

        NumberFormat kFormat = new DecimalFormat("#0.0#");
        NumberFormatter kFormatter = new NumberFormatter(kFormat);
        kFormatter.setValueClass(Float.class);
        kFormatter.setMinimum(0.0f); // Ensure positive values
        kFormatter.setMaximum(Float.MAX_VALUE);
        kFormatter.setAllowsInvalid(false); // Prevent non-numeric input
        kFormatter.setCommitsOnValidEdit(true);

        JFormattedTextField kField = new JFormattedTextField(kFormatter);
        kField.setValue(k);

        inputFieldPane.add(kFieldLabel);
        inputFieldPane.add(kField);


        JLabel numFieldLabel = new JLabel("Number of nodes");

        NumberFormat numFormat = new DecimalFormat();
        NumberFormatter numFormatter = new NumberFormatter(numFormat);
        numFormatter.setValueClass(Integer.class);
        numFormatter.setMinimum(1); // Ensure positive values
        numFormatter.setMaximum(500);
        numFormatter.setAllowsInvalid(false); // Prevent non-numeric input
        numFormatter.setCommitsOnValidEdit(true);

        JFormattedTextField numField = new JFormattedTextField(numFormatter);
        numField.setValue(numNodes);

        inputFieldPane.add(numFieldLabel);
        inputFieldPane.add(numField);
        
        JLabel pFieldLabel = new JLabel("Probability of an edge between each node");

        NumberFormat pFormat = new DecimalFormat("#0.0#");
        NumberFormatter pFormatter = new NumberFormatter(pFormat);
        pFormatter.setValueClass(Float.class);
        pFormatter.setMinimum(0.0f); // Ensure positive values
        pFormatter.setMaximum(1.0f);
        pFormatter.setAllowsInvalid(true); // Prevent non-numeric input
        pFormatter.setCommitsOnValidEdit(true);

        JFormattedTextField pField = new JFormattedTextField(pFormatter);
        pField.setValue(p);

        inputFieldPane.add(pFieldLabel);
        inputFieldPane.add(pField);

        GridBagConstraints inputFieldConstraints = new GridBagConstraints();
        inputFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputFieldConstraints.gridx = 2;
        inputFieldConstraints.gridy = 1;

        add(inputFieldPane, inputFieldConstraints);


        draw = new DrawPanel();
        draw.setSize(WIDTH, HEIGHT);
        GridBagConstraints drawPanelConstraints = new GridBagConstraints();

        drawPanelConstraints.gridx = 1;
        drawPanelConstraints.gridy = 1;
        drawPanelConstraints.ipadx = WIDTH;
        drawPanelConstraints.ipady = HEIGHT;
        add(draw, drawPanelConstraints);

        JButton resetButton = getjButton(kField, numField, pField);

        GridBagConstraints resetButtonConstraints = new GridBagConstraints();
        resetButtonConstraints.gridx = 1;
        resetButtonConstraints.gridy = 2;

        add(resetButton, resetButtonConstraints);

        Pair<Graph<Integer>, Map<Integer, Vector>> newGraph = generateRandomGraph(numNodes, p);

        graph = newGraph.getKey();
        pos = newGraph.getValue();

        fruchtermanReingold = new FruchtermanReingold(graph, WIDTH, HEIGHT);

        t = new Timer(10, e -> draw.update());

        t.start();
    }

    private JButton getjButton(JFormattedTextField kField, JFormattedTextField numField, JFormattedTextField pField) {
        JButton resetButton = new JButton("Reset Sim");
        resetButton.addActionListener(e -> {
            t.stop();

            k = (float) kField.getValue();
            numNodes = (int) numField.getValue();
            p = (float) pField.getValue();
            Pair<Graph<Integer>, Map<Integer, Vector>> newGraph = generateRandomGraph(numNodes, p);

            graph = newGraph.getKey();
            pos = newGraph.getValue();

            fruchtermanReingold = new FruchtermanReingold(graph, WIDTH, HEIGHT);

            t.start();}
        );
        return resetButton;
    }


    class DrawPanel extends JPanel{

        public DrawPanel() {
            Dimension expectedDimension = new Dimension(WIDTH, HEIGHT);

            setPreferredSize(expectedDimension);
            setMaximumSize(expectedDimension);
            setMinimumSize(expectedDimension);

            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        public void drawGraph(Graph<Integer> graph, Map<Integer, Vector> pos, int nodeRadius, Graphics g) {
            //Draw edges
            g.setColor(Color.BLACK);

            for (Integer node1 : graph.getVertices()) {
                for (Integer node2 : graph.getNeighbors(node1)) {
                    Vector loc1 = pos.get(node1);
                    Vector loc2 = pos.get(node2);

                    g.drawLine((int) (loc1.x()), (int) (loc1.y()),
                            (int) (loc2.x()), (int) (loc2.y()));
                }
            }
            //Draw nodes
            for (Integer node : graph.getVertices()) {
                Vector loc = pos.get(node);

                g.fillOval((int) (loc.x()) - nodeRadius, (int) (loc.y()) - nodeRadius,
                        2 * nodeRadius, 2 * nodeRadius);
            }
        }

        public void update() {
            fruchtermanReingold.FruchtermanReingoldIteration(pos, k);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGraph(graph, pos, 10, g);
        }
    }
    /**
     * Generates a graph containing numOfNodes nodes, who's positions are randomly generated within the frame.
     * Then for each pair of nodes, generate a random number x between [0, 1). If x < p, add an edge between those nodes
     * @param numOfNodes
     * @param p
     * @return a pair containing the generated graph and a map with each node and its corresponding position
     */
    private Pair<Graph<Integer>, Map<Integer, Vector>> generateRandomGraph(int numOfNodes, float p) {
        Graph<Integer> g = new Graph<>();
        Map<Integer, Vector> pos = new HashMap<>();

        for (int i = 1; i < numOfNodes + 1; i++) {
            g.addVertex(i);

            Vector loc = new Vector((float) (WIDTH * .25f + Math.random() * (WIDTH * 0.5f)),
                    (float) (WIDTH * 0.25f + Math.random() * (WIDTH * 0.5f)));

            pos.put(i, loc);
        }

        for (int i = 1; i < numOfNodes + 1; i++) {
            for (int j = i + 1; j < numOfNodes + 1; j++) {
                if ((float) Math.random() < p)
                    g.addEdge(i, j, 1);
            }
        }
        return new Pair<>(g, pos);
    }
}
