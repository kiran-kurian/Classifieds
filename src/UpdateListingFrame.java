import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateListingFrame extends Frame {
    private User user;
    private Listing listing;
    private ListingDAO listingDAO;
    private TextField tfTitle, tfDescription;
    private Button btnSubmit, btnCancel;
    private MainFrame mainFrame;
    Label lblMessage;

    public UpdateListingFrame(User user, Listing listing, MainFrame mainFrame) {
        this.user = user;
        this.listing = listing;
        this.listingDAO = new ListingDAO();
        this.mainFrame = mainFrame;

        setLayout(new FlowLayout());

        tfTitle = new TextField(listing.getTitle(), 20);
        tfDescription = new TextField(listing.getDescription(), 20);

        btnSubmit = new Button("Submit");
        btnCancel = new Button("Cancel");

        add(new Label("Title:"));
        add(tfTitle);
        add(new Label("Description:"));
        add(tfDescription);
        add(btnSubmit);
        add(btnCancel);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = tfTitle.getText();
                String description = tfDescription.getText();
                if (listingDAO.updateListing(listing.getId(), user.getId(), title, description)) {
                    mainFrame.loadListings();
                    dispose(); 
                } else {
                    lblMessage.setText("Failed to update listing");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setTitle("Update Listing");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
