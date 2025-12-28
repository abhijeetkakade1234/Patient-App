package view.util;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for creating consistently styled buttons across all UIs
 */
public class ButtonStyleUtil {
    
    public static final Color PRIMARY_COLOR = new Color(0, 102, 204);
    public static final Color SUCCESS_COLOR = new Color(0, 150, 136);
    public static final Color WARNING_COLOR = new Color(255, 193, 7);
    public static final Color DANGER_COLOR = new Color(220, 53, 69);
    public static final Color SECONDARY_COLOR = new Color(150, 150, 150);
    public static final Color LIGHT_COLOR = new Color(200, 200, 200);
    
    /**
     * Create a styled button with background and text colors
     * @param text Button text
     * @param bgColor Background color
     * @param fgColor Foreground color (text color)
     * @return Styled JButton
     */
    public static JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Disable default L&F behaviors that can override custom colors
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setRolloverEnabled(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        
        // Set colors explicitly
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        
        return button;
    }
    
    /**
     * Create a styled button with default white foreground
     * @param text Button text
     * @param bgColor Background color
     * @return Styled JButton
     */
    public static JButton createButton(String text, Color bgColor) {
        return createButton(text, bgColor, Color.WHITE);
    }
    
    /**
     * Create primary button (blue with white text)
     */
    public static JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY_COLOR, Color.WHITE);
    }
    
    /**
     * Create success button (green/teal with white text)
     */
    public static JButton createSuccessButton(String text) {
        return createButton(text, SUCCESS_COLOR, Color.WHITE);
    }
    
    /**
     * Create danger button (red with white text)
     */
    public static JButton createDangerButton(String text) {
        return createButton(text, DANGER_COLOR, Color.WHITE);
    }
    
    /**
     * Create secondary button (gray with white text)
     */
    public static JButton createSecondaryButton(String text) {
        return createButton(text, SECONDARY_COLOR, Color.WHITE);
    }
    
    /**
     * Create light button (light gray with black text)
     */
    public static JButton createLightButton(String text) {
        return createButton(text, LIGHT_COLOR, Color.BLACK);
    }
}
