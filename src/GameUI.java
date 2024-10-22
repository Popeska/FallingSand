import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI extends JPanel {

    // Variables to control
    int pixelsPerGrid = 10;
    boolean drawGrid = false;
    int frameRefresh = 1;

    // UI components
    JButton settingsButton;
    JPanel dropdownPanel;
    JSpinner gridSizeSpinner, frameRefreshSpinner;
    JCheckBox gridCheckBox;

    public GameUI() {
        setLayout(null);  // We'll use absolute positioning for this example

        // Create the settings button
        settingsButton = new JButton("Settings");
        settingsButton.setBounds(400, 400, 80, 30);  // Positioned near the top-right
        settingsButton.addActionListener(e -> toggleDropdown());
        settingsButton.setBackground(Color.white);

        // Create the dropdown panel
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(3, 2));  // 3 rows for our controls
        dropdownPanel.setBounds(600, 60, 180, 100);  // Positioned under the button
        dropdownPanel.setVisible(false);  // Initially hidden

        // Add controls to the dropdown panel
        dropdownPanel.add(new JLabel("Pixels per Grid:"));
        gridSizeSpinner = new JSpinner(new SpinnerNumberModel(pixelsPerGrid, 5, 50, 1));
        dropdownPanel.add(gridSizeSpinner);

        dropdownPanel.add(new JLabel("Frame Refresh:"));
        frameRefreshSpinner = new JSpinner(new SpinnerNumberModel(frameRefresh, 1, 10, 1));
        dropdownPanel.add(frameRefreshSpinner);

        dropdownPanel.add(new JLabel("Draw Grid:"));
        gridCheckBox = new JCheckBox();
        gridCheckBox.setSelected(drawGrid);
        dropdownPanel.add(gridCheckBox);

        // Add components to the main panel
        add(settingsButton);
        add(dropdownPanel);
    }

    // Toggle the dropdown panel visibility
    private void toggleDropdown() {
        dropdownPanel.setVisible(!dropdownPanel.isVisible());
    }

    // Call this method to update the variables based on UI changes
    public void applySettings() {
        pixelsPerGrid = (int) gridSizeSpinner.getValue();
        frameRefresh = (int) frameRefreshSpinner.getValue();
        drawGrid = gridCheckBox.isSelected();
    }
}