import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;


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

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime += 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_String = String.format("%02d", seconds);
            minutes_String = String.format("%02d", minutes);
            hours_String = String.format("%02d", hours);
            timeLabel.setText(String.format(hours_String + ":" + minutes_String + ":" + seconds_String));
        }
    });

    Stopwatch() throws URISyntaxException {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 350);
        frame.setMaximumSize(new Dimension(500, 500));

        JPanel backgroundPanel = new JPanel() {
            Image background;
            {

                File bgFile = new File(appDir, "resources/TimeRabbit.png");

                File exeDir = new File(Stopwatch.class.getProtectionDomain()
                        .getCodeSource().getLocation().toURI()).getParentFile();

                if (exeDir.exists()) {
                    background = new ImageIcon(bgFile.getAbsolutePath()).getImage();
                } else {
                    background = new ImageIcon("C:\\Users\\Derpe\\IdeaProjects\\Stopwatch\\src\\Time Rabbit.png").getImage();
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
        Image appIcon;

        {
            File iconFile = new File(appDir, "resources/Time-Rabbit.ico");
            if (iconFile.exists()) {
                appIcon = new ImageIcon(iconFile.getAbsolutePath()).getImage();
            } else {
                appIcon = new ImageIcon("C:\\Users\\Derpe\\IdeaProjects\\Stopwatch\\src\\Hand & Watch.png").getImage();
            }
        }

        frame.setIconImage(appIcon);

        frame.setVisible(true);
    }

    File appDir = new File(System.getProperty("user.dir"));

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {
            start();
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
        timeLabel.setText(String.format(hours_String + ":" + minutes_String + ":" + seconds_String));
    }

    class ScaledBackground extends JPanel {
        private Image background;

        public ScaledBackground(String path) {
            ImageIcon Icon = new ImageIcon(path);
            background = Icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        new Stopwatch();
    }
}