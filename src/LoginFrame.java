import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends Frame {
    private TextField tfUsername, tfPassword;
    private Button btnLogin, btnRegister;
    private Label lblMessage;
    public LoginFrame() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        Label lblUsername = new Label("Username:");
        tfUsername = new TextField(20);
        Label lblPassword = new Label("Password:");
        tfPassword = new TextField(20);
        tfPassword.setEchoChar('*');
        btnLogin = new Button("Login");
        btnRegister = new Button("Register");
        lblMessage = new Label();
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        add(lblMessage);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = tfPassword.getText();
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsernameAndPassword(username, password);
                if (user != null) {
                    new MainFrame(user).setVisible(true);
                    dispose();
                } else {
                    lblMessage.setText("Invalid username or password.");
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = tfPassword.getText();
                UserDAO userDAO = new UserDAO();
                if (userDAO.registerUser(username, password)) {
                    lblMessage.setText("Registration successful. Please log in.");
                } else {
                    lblMessage.setText("Registration failed.");
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
