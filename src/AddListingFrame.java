import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddListingFrame extends Frame {
    private User user;
    private ListingDAO listingDAO;
    private TextField tfTitle, tfDescription;
    private Button btnSubmit, btnCancel;
    private MainFrame mainFrame;
    private Label lblmessage;

    public AddListingFrame(User user, MainFrame mainFrame) {
        this.user = user;
        this.listingDAO = new ListingDAO();
        this.mainFrame = mainFrame;

        setLayout(new FlowLayout());

        tfTitle = new TextField(20);
        tfDescription = new TextField(20);

        btnSubmit = new Button("Submit");
        btnCancel = new Button("Cancel");
        lblmessage = new Label();

        add(new Label("Title:"));
        add(tfTitle);
        add(new Label("Description:"));
        add(tfDescription);
        add(btnSubmit);
        add(btnCancel);
        add(lblmessage);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tfTitle.getText();
                String description = tfDescription.getText();
                if (listingDAO.createListing(user.getId(), title, description)) {
                    mainFrame.loadListings();
                    dispose();
                } else {
                    lblmessage.setText("Error adding data");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setTitle("Add Listing");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
