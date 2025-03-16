import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class PatientGraph extends JPanel {

    private final Map<String, Integer> patientData;

    public PatientGraph(Map<String, Integer> data) {
        this.patientData = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Set anti-aliasing for smooth graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Graph dimensions
        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int originX = padding;
        int originY = height - padding;
        int maxPatients = 15; // Adjust based on your max count

        // Draw axes
        g2.drawLine(originX, padding, originX, originY); // Y-axis
        g2.drawLine(originX, originY, width - padding, originY); // X-axis

        // Draw Data Points and Lines
        int prevX = -1, prevY = -1;
        int stepX = (width - 2 * padding) / patientData.size();
        int index = 0;

        for (Map.Entry<String, Integer> entry : patientData.entrySet()) {
            int x = originX + index * stepX;
            int y = originY - (entry.getValue() * (height - 2 * padding) / maxPatients);

            // Draw data point
            g2.fillOval(x - 3, y - 3, 6, 6);

            // Draw connecting lines
            if (prevX != -1 && prevY != -1) {
                g2.drawLine(prevX, prevY, x, y);
            }

            // Draw labels
            g2.drawString(entry.getKey(), x - 10, originY + 20);
            g2.drawString(String.valueOf(entry.getValue()), x - 10, y - 10);

            prevX = x;
            prevY = y;
            index++;
        }
    }

    public static void showGraph(String title, Map<String, Integer> data) {
        JFrame frame = new JFrame(title);
        PatientGraph panel = new PatientGraph(data);
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
