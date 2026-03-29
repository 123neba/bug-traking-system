import javax.swing.*;
import java.awt.*;

public class Login {

    public static void showLoginScreen() {
        JFrame frame = new JFrame("Bug Tracker - Login");
        frame.setSize(400, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        

        frame.getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLbl = new JLabel("Welcome Back", SwingConstants.CENTER);
        titleLbl.setBounds(0, 30, 400, 40);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLbl.setForeground(Color.WHITE);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(70, 90, 100, 30);
        userLbl.setForeground(Color.LIGHT_GRAY);
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField userField = new JTextField();
        userField.setBounds(70, 120, 260, 35);
        userField.setBackground(new Color(50, 50, 50));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        userField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(70, 170, 100, 30);
        passLbl.setForeground(Color.LIGHT_GRAY);
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(70, 200, 260, 35);
        passField.setBackground(new Color(50, 50, 50));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(70, 260, 260, 40);
        loginBtn.setBackground(new Color(37, 99, 235));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBorder(BorderFactory.createEmptyBorder());
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel msgLbl = new JLabel("", SwingConstants.CENTER);
        msgLbl.setBounds(0, 310, 400, 20);
        msgLbl.setForeground(new Color(239, 68, 68));

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            String userRole = UserDAO.login(username, password);

            if (userRole != null) {

                frame.dispose();

                UI.openDashboard(userRole);
            } else {
                msgLbl.setText("Invalid username or password.");
            }
        });

        frame.add(titleLbl);
        frame.add(userLbl);
        frame.add(userField);
        frame.add(passLbl);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(msgLbl);

        frame.setVisible(true);
    }
}
