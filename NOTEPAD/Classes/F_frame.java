package Classes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.border.Border;


public class F_frame extends JFrame {
    private int mouseX, mouseY;
    private JTextArea textArea;

    public F_frame() {
        initializeUI();
    }

    private void initializeUI() {
        setSize(1080, 720);																				 // Setting the size of the frame
        setResizable(false);																			 // Preventing frame resize
        setUndecorated(true);																			 // Remove default title bar

        JPanel titleBar = new JPanel();																	 // Title bar panel
        titleBar.setBackground(new Color(52, 152, 219));												 // Setting background color for title bar
        titleBar.setPreferredSize(new Dimension(getWidth(), 40)); 										 // Setting preferred size for title bar
        titleBar.setLayout(null);																		 // Setting layout to null
        add(titleBar, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Notepad"); 														// Creating a JLabel for the title
        titleLabel.setForeground(Color.WHITE);															 // Setting text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));											 // Setting font
        titleLabel.setBounds(10, 0, 100, 40); 															// Setting bounds for title label
        titleBar.add(titleLabel);																		 // Adding titleLabel to titleBar

        JButton minimizeButton = new JButton("-"); 														// Creating a minimize button
        minimizeButton.setForeground(Color.WHITE);														 // Setting text color
        minimizeButton.setBackground(new Color(52, 152, 219));											 // Setting background color
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));										 // Setting font
        minimizeButton.setFocusable(false);																 // Removing focus
        minimizeButton.setBorder(null);																	 // Removing border
        minimizeButton.setBounds(getWidth() - 100, 0, 40, 40); 											 // Setting bounds for minimize button
        minimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);														 // Minimize the frame when minimize button is clicked
            }
        });
        titleBar.add(minimizeButton);																	 // Adding minimizeButton to titleBar

        JButton closeButton = new JButton("X");															 // Creating a close button
        closeButton.setForeground(Color.WHITE);															 // Setting text color
        closeButton.setBackground(new Color(52, 152, 219)); 											// Setting background color
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));											 // Setting font
        closeButton.setFocusable(false); 																// Removing focus
        closeButton.setBorder(null); 																	// Removing border
        closeButton.setBounds(getWidth() - 50, 0, 40, 40); 												// Setting bounds for close button
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 																		// Exit the frame when close button is clicked
            }
        });
        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.RED);													 // Highlight the close button on mouse enter
            }
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(52, 152, 219)); 									// Restore the close button color on mouse exit
            }
        });
        titleBar.add(closeButton);																		 // Adding closeButton to titleBar

        minimizeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setBackground(Color.GRAY);												 // Highlight the minimize button on mouse enter
            }
            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(new Color(52, 152, 219));									// Restore the minimize button color on mouse exit
            }
        });
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });

        JPanel leftPanel = new JPanel(); 															// Creating a JPanel for the left side
        leftPanel.setBackground(new Color(220, 242, 241));											 // Setting background color
        leftPanel.setBounds(0, 40, (getWidth() * 2 / 5) + 70, getHeight())	; 						// Setting bounds
        leftPanel.setLayout(null); 																	// Using null layout
        add(leftPanel);																				 // Adding leftPanel to the frame

        textArea = new JTextArea();																	 // Creating a JTextArea for note-taking
        JScrollPane scrollPane = new JScrollPane(textArea);											 // Adding a scroll pane to handle scrolling
        scrollPane.setBounds(10, 50, (getWidth() - 100) + 80, (getHeight() - 120));					 // Setting bounds for scroll pane
        leftPanel.add(scrollPane);																	 // Adding the scroll pane to the leftPanel

        JLabel wordCountLabel = new JLabel("Word Count: 0");										 // Creating a JLabel to display word count
        wordCountLabel.setBounds(970, 650, 150, 30);												 // Setting bounds for word count label
        leftPanel.add(wordCountLabel);																 // Adding wordCountLabel to leftPanel

        JButton submitButton = new JButton("Save");													 // Creating a JButton with label "Save"
        submitButton.setBounds(10, 10, 100, 30); 													 // Setting bounds for the button
        customizeButton(submitButton); 																 // Customizing button appearance
        leftPanel.add(submitButton);																 // Adding submitButton to leftPanel

																									// Add ActionListener to the "Save" button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); 										// Create a file chooser
                int result = fileChooser.showSaveDialog(F_frame.this);								 // Show the file chooser dialog

                if (result == JFileChooser.APPROVE_OPTION) { 										// If a file is selected
                    try {
                        String fileName = fileChooser.getSelectedFile().getAbsolutePath();			 // Get the selected file path
                        FileWriter fileWriter = new FileWriter(fileName + ".txt"); 					// Create a FileWriter with .txt extension
                        BufferedWriter writer = new BufferedWriter(fileWriter);
                        writer.write(textArea.getText());											 // Write the text from the JTextArea to the file
                        writer.close(); 															// Close the writer
                        JOptionPane.showMessageDialog(F_frame.this, "File saved successfully!"); // Show success message
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(F_frame.this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                    }
                }
            }
        });

        JButton newButton = new JButton("New"); 												// Creating a JButton with label "(New)"
        newButton.setBounds(120, 10, 100, 30);													 // Setting bounds for the button
        customizeButton(newButton);																 // Customizing button appearance
        leftPanel.add(newButton);																 // Adding newButton to leftPanel

																								// Add ActionListener to the "(New)" button
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
																								// Creating an instance of F2_frame
                F2_frame frame2 = new F2_frame();
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);						 // Disposes only the F2_frame on close
                frame2.setVisible(true); 														// Setting frame visibility
            }
        });

																								// Add DocumentListener to JTextArea to update word count
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateWordCount();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateWordCount();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateWordCount();
            }

            private void updateWordCount() {
                String text = textArea.getText();
                int wordCount = text.trim().isEmpty() ? 0 : text.split("\\s+").length;
                wordCountLabel.setText("Word Count: " + wordCount);
            }
        });
    }

    public void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);													 // Setting text color
        button.setBackground(new Color(52, 152, 219));										 // Setting background color
        button.setFont(new Font("Arial", Font.PLAIN, 16));									 // Setting font
        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);						 // Creating a rounded border
        button.setBorder(roundedBorder);													 // Setting button border
    }
}
