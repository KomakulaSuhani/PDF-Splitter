import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends Frame implements ActionListener {
    private TextArea textArea;
    private FileDialog fileDialog;
    
    public TextEditor() {
        setTitle("Text Editor");
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        textArea = new TextArea();
        add(textArea, BorderLayout.CENTER);
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);
        
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);
        
        menuBar.add(fileMenu);
        setMenuBar(menuBar);
        
        fileDialog = new FileDialog(this, "Open/Save", FileDialog.LOAD);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Open")) {
            fileDialog.setMode(FileDialog.LOAD);
            fileDialog.setVisible(true);
            
            String directory = fileDialog.getDirectory();
            String filename = fileDialog.getFile();
            
            if (directory != null && filename != null) {
                String filePath = directory + filename;
                
                try {
                    FileReader reader = new FileReader(filePath);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    
                    StringBuilder content = new StringBuilder();
                    String line;
                    
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    
                    bufferedReader.close();
                    
                    textArea.setText(content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Save")) {
            fileDialog.setMode(FileDialog.SAVE);
            fileDialog.setVisible(true);
            
            String directory = fileDialog.getDirectory();
            String filename = fileDialog.getFile();
            
            if (directory != null && filename != null) {
                String filePath = directory + filename;
                
                try {
                    FileWriter writer = new FileWriter(filePath);
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.setVisible(true);
    }
}