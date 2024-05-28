import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends Frame {
    private User user;
    private ListingDAO listingDAO;
    private List<Listing> listings;
    private java.awt.List list;
    private Button btnAdd, btnDelete, btnUpdate, btnLogout;
    private Label lblmessage;

    public MainFrame(User user) {
        this.user = user;
        this.listingDAO = new ListingDAO();

        setLayout(new FlowLayout());

        list = new java.awt.List(10);
        loadListings();

        btnAdd = new Button("Add Listing");
        btnDelete = new Button("Delete Listing");
        btnUpdate = new Button("Update Listing");
        btnLogout = new Button("Logout");

        add(list);
        add(btnAdd);
        add(btnDelete);
        add(btnUpdate);
        add(btnLogout);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddListingFrame(user, MainFrame.this);
            }
        });

        btnDelete.addActionListener((ActionEvent e) -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex >= 0) {
                Listing selectedListing = listings.get(selectedIndex);
                if (listingDAO.deleteListing(selectedListing.getId(), user.getId())) {
                    loadListings();
                } else {
                    lblmessage.setText("Failed to delete listing");
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Listing selectedListing = listings.get(selectedIndex);
                    if (selectedListing.getUserId() == user.getId()) {
                        new UpdateListingFrame(user, selectedListing, MainFrame.this); // Pass the listing to update
                    } else {
                        // Handle error: only the owner can update
                    }
                }
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the MainFrame
                new LoginFrame(); // Show the login frame again
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setTitle("Classifieds");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void loadListings() {
        listings = listingDAO.getListings();
        list.removeAll();
        for (Listing listing : listings) {
            list.add(listing.getTitle() + " - " + listing.getDescription());
        }
    }
}
