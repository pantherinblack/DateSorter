import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class SelectionGui extends JFrame {
    private final JPanel upperPanel = new JPanel();
    private final JPanel options = new JPanel();
    private final JPanel filePanel = new JPanel();
    private final JButton fileButton = new JButton("Browse");
    private final JTextField dateField = new JTextField("YYYY-MM.DD");
    private final JTextField fileField = new JTextField("C:\\");
    private final JPanel finishPanel = new JPanel();
    private final JButton finishButton = new JButton("Finish");

    public SelectionGui() {
        setTitle("PictureSorter");
        init();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        new SelectionGui();
    }

    private void init() {
        setLayout(new BorderLayout());
        add(upperPanel, BorderLayout.NORTH);
        options.add(new JLabel("File:"));
        options.add(filePanel);

        filePanel.setLayout(new BorderLayout(0, 20));
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(fileButton, BorderLayout.EAST);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(fileField.getText());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(fileField) == JFileChooser.APPROVE_OPTION)
                fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });

        options.add(new JLabel("Format:"));
        options.setLayout(new GridLayout(4, 1));
        options.add(dateField);
        JTextArea info = new JTextArea("\n\"-\"willcreateasubfolder.\n\".\"willstayinfolder-name.\nDisusedfortheday.\nMisusedforthemonth.\nYisusedforTheYear.\nAnythingelsewillbeignored.\n");
        info.setEditable(false);
        info.setBackground(null);
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(options, BorderLayout.CENTER);
        upperPanel.add(info, BorderLayout.SOUTH);
        dateField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    execute();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (isCorrectFormatInput()) dateField.setBackground(Color.WHITE);
                else dateField.setBackground(Color.RED);
            }
        });

        fileField.setColumns(20);
        fileField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (new File(fileField.getText()).exists()) fileField.setBackground(Color.WHITE);
                else fileField.setBackground(Color.RED);
            }
        });

        add(finishPanel, BorderLayout.CENTER);

        finishPanel.setLayout(new BorderLayout());
        finishPanel.add(finishButton, BorderLayout.WEST);
        finishButton.addActionListener(e -> execute());
    }

    private void execute() {
        if (isCorrectFormatInput()) {
            new FileController(fileField.getText(), dateField.getText());
        }
    }

    private boolean isCorrectFormatInput() {
        return (dateField.getText().replaceAll("[^Dd]", "").length() == 2 ||
                dateField.getText().replaceAll("[^Dd]", "").length() == 0) &&
                (dateField.getText().replaceAll("[^Mm]", "").length() == 2 ||
                        dateField.getText().replaceAll("[^Mm]", "").length() == 0) &&
                dateField.getText().replaceAll("[^Yy]", "").length() == 4;
    }
}
