import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    public TextEditor() {
        super("Text Editor");
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new NewAction());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenAction());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveAction());
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(new SaveAsAction());
        fileMenu.add(saveAsItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitAction());
        fileMenu.add(exitItem);

        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class NewAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
            currentFile = null;
        }
    }

    private class OpenAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (fileChooser == null) {
                fileChooser = new JFileChooser();
            }
            int returnVal = fileChooser.showOpenDialog(TextEditor.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
                try {
                    FileReader reader = new FileReader(currentFile);
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error reading file: " + ex.getMessage());
                }
            }
        }
    }

    private class SaveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentFile == null) {
                saveAs();
            } else {
                try {
                    FileWriter writer = new FileWriter(currentFile);
                    textArea.write(writer);
                    writer.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error writing file: " + ex.getMessage());
                }
            }
        }
    }

    private class SaveAsAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    }

    private void saveAs() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        int returnVal = fileChooser.showSaveDialog(TextEditor.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(currentFile);
                textArea.write(writer);
                writer.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(TextEditor.this, "Error writing file: " + ex.getMessage());
            }
        }
    }

    private class ExitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
