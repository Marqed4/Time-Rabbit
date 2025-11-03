import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Stopwatch implements ActionListener {

    JFrame frame = new JFrame("Time Rabbit");
    JButton startButton = new JButton("Start");
    JButton resetButton = new JButton("Reset");
    JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    boolean started = false;
    String seconds_String = String.format("%02d", seconds);
    String minutes_String = String.format("%02d", minutes);
    String hours_String = String.format("%02d", hours);

    Timer timer = new Timer(1000, e -> {
        elapsedTime += 1000;
        hours = (elapsedTime / 3600000);
        minutes = (elapsedTime / 60000) % 60;
        seconds = (elapsedTime / 1000) % 60;
        seconds_String = String.format("%02d", seconds);
        minutes_String = String.format("%02d", minutes);
        hours_String = String.format("%02d", hours);
        timeLabel.setText(hours_String + ":" + minutes_String + ":" + seconds_String);
    });

    Stopwatch() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 350);
        frame.setMaximumSize(new Dimension(500, 500));

        // Load icon from installed resources folder
        File iconFile = new File("C:\\Program Files (x86)\\Time Rabbit\\resources\\Hand & Watch.png");
        if (iconFile.exists()) {
            frame.setIconImage(new ImageIcon(iconFile.getAbsolutePath()).getImage());
        } else {
            System.err.println("Icon not found at: " + iconFile.getAbsolutePath());
        }

        JPanel backgroundPanel = new JPanel() {
            Image background;

            {
                // Load background from installed resources folder
                File bgFile = new File("C:\\Program Files (x86)\\Time Rabbit\\resources\\Time Rabbit.png");
                if (bgFile.exists()) {
                    background = new ImageIcon(bgFile.getAbsolutePath()).getImage();
                } else {
                    System.err.println("Background image not found at: " + bgFile.getAbsolutePath());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        backgroundPanel.setOpaque(false);

        timeLabel.setText(hours_String + ":" + minutes_String + ":" + seconds_String);
        timeLabel.setFont(new Font("Agency FB", Font.PLAIN, 30));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(Color.BLACK);
        backgroundPanel.add(timeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        startButton.setFont(new Font("Agency FB", Font.PLAIN, 20));
        resetButton.setFont(new Font("Agency FB", Font.PLAIN, 20));
        startButton.setFocusable(false);
        resetButton.setFocusable(false);
        startButton.addActionListener(this);
        resetButton.addActionListener(this);

        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);

        startButton.setOpaque(true);
        resetButton.setOpaque(true);

        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.setContentPane(backgroundPanel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (!started) {
                started = true;
                startButton.setText("Stop");
                start();
            } else {
                started = false;
                startButton.setText("Start");
                stop();
            }
        }

        if (e.getSource() == resetButton) {
            started = false;
            startButton.setText("Start");
            reset();
        }
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    void reset() {
        timer.stop();
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        seconds_String = String.format("%02d", seconds);
        minutes_String = String.format("%02d", minutes);
        hours_String = String.format("%02d", hours);
        timeLabel.setText(hours_String + ":" + minutes_String + ":" + seconds_String);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Stopwatch::new);
    }
}
