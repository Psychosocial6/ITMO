package client.GUI;

import client.Client;
import client.LangManager;
import client.custom_gui_elements.MovieTableModel;
import client.custom_gui_elements.ObjectDrawingPanel;
import client.dataStorage.CollectionView;
import client.eventHandlers.*;
import client.other.DrawableObject;
import client.other.TableElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import static java.lang.Math.abs;

/**
 *The {@code GUI.MainPageGui} describes GUI of main page in Collection Viewer from {@code ProgrammingLab8}.
 * Creates standard configuration with elements table, showing area and various filters.
 * @author Andrey
 * */
public class MainPageGUI {
    public static final Dimension WINDOW_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static  String TITLE = LangManager.get("title");

    public static final Dimension LABEL_SIZE = new Dimension(400, 40);

    public static final Dimension TEXT_FIELD_SIZE = new Dimension(150, 30);

    public static final Dimension MENU_PANEL_SIZE = new Dimension(200, 200);

    public static String SORTING_MENU_LABEL =  LangManager.get("sorting.menu");
    public static String FILTERS_MENU_LABEL =  LangManager.get("filters.menu");
    public static String LANGUAGE_MENU_LABEL = LangManager.get("language.menu.label");
    public static Dimension SMALL_LABEL_SIZE = new Dimension(200, 20);
    public static final String[] SORTING_AND_FILTERING_PARAMETERS = {
            "id",
            "name",
            "coordinates x",
            "coordinates y",
            "creation date",
            "oscars count",
            "total box office",
            "usa box office",
            "genre",
            "operator name",
            "operator height",
            "operator eye color",
            "operator hair color",
            "operator nationality",
            "operator id",
            "location id",
            "location x",
            "location y",
            "location z",
            "location name"
            };
    public static final String[] LANGUAGE_PARAMETERS = {
    		"english",
    		"русский",
    		"český",
    		"українська",
    		"español"
    };
    public static final Dimension MENU_SIZE = new Dimension(200, 100);

    public static final Dimension TABLE_SIZE = new Dimension((int)WINDOW_SIZE.getWidth()/5 * 2, (int)WINDOW_SIZE.getHeight()/3 * 2);

    public static final Dimension BUTTON_SIZE = new Dimension(100, 50);
    public static String NEXT_PAGE_BUTTON_TITLE = LangManager.get("next.page.button.title");
    public static String SORT_BUTTON_TITLE = LangManager.get("sort.button.title");
    public static String FILTERS_BUTTON_TITLE = LangManager.get("filters.button.title");;
    public static String FILTER_HINT = LangManager.get("filter.hint");
    public static String ADD_BUTTON_TITLE = LangManager.get("add.button.title");
    public static String LANGUAGE_BUTTON_TITLE = LangManager.get("language.button.label");
    public static String USER_TITLE = LangManager.get("user.title");
    public static final int VERTICAL_STRUT = 20;
    public static final int SMALL_VERTICAL_STRUT = 5;
    public static final int HORIZONTAL_STRUT = 10;

    public static final Color BUTTON_COLOR = new Color(0, 169, 255);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Color HINT_COLOR = Color.GRAY;

    private JLabel userLabel;
    private JTable elementsTable;
    private JButton nextPageButton;
    private JPanel sortingMenu;
    private JPanel filtersMenu;
    private JFrame window;
    private NextPageHandler nextPageHandler;
    private SortingHandler sortingHandler;
    private FilterHandler filterHandler;
    private InfoHandler infoHandler;
    private UpdateCollectionHandler updateCollectionHandler;
    private JButton addButton;
    private JPanel languageMenu;
    private String currentUser;
    private ObjectDrawingPanel drawingPanel;
    private ArrayList<TableElement> savedElements;
    
    private JLabel clockLabel;
    private Timer clockTimer;

    public MainPageGUI(String user, ArrayList<TableElement> elements) {
        window = new JFrame(TITLE);
        window.setSize(WINDOW_SIZE);
        window.setLayout(new BorderLayout(10, 10));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        nextPageButton = createNextPageButton();
        sortingMenu = createSortingMenu();
        filtersMenu = createFiltersMenu();
        addButton = createAddButton();
        languageMenu = createLanguageMenu();
        currentUser = user;
        savedElements = elements;

        drawingPanel = new ObjectDrawingPanel(infoHandler);
        int index = -1;
        for (Integer x : CollectionView.getMovieCoordinates().keySet()) {
            index += 1;
            Long y = CollectionView.getMovieCoordinates().get(x);
            System.out.println(CollectionView.getCreatedBy().get(index).hashCode()%256);
            drawingPanel.addObject(new DrawableObject(x*10, y*10, 10, new Color(abs(CollectionView.getCreatedBy().get(index).hashCode()-100)%256, abs(CollectionView.getCreatedBy().get(index).hashCode()-50)%256, abs(CollectionView.getCreatedBy().get(index).hashCode())%256)));
        }


        createElements(currentUser, savedElements);
    }

    public void createElements(String user, ArrayList<TableElement> movies) {
        elementsTable = createFilmsTable(movies);
        userLabel = createLabel(USER_TITLE + user);
        nextPageButton = createNextPageButton();
        sortingMenu = createSortingMenu();
        filtersMenu = createFiltersMenu();
        languageMenu = createLanguageMenu();
        addButton = createAddButton();
        clockLabel = createClockLabel(LangManager.getCurrentLocale());

    }

    public void repaint() {
        window.setVisible(false);
        window.getContentPane().removeAll();
        savedElements = ((MovieTableModel) elementsTable.getModel()).getMovies();
        createElements(currentUser, savedElements);
        createAndShowWindow();

        window.getContentPane().revalidate();
        window.getContentPane().repaint();

    }

    /**
     * Method which creates main window's label
     * @return  {@code JLabel} - new {@code JLabel} with pre created configuration and label's text
     * */
    private JLabel createLabel(String user) {
        JLabel label = new JLabel();
        label.setPreferredSize(LABEL_SIZE);
        label.setText(user);
        label.setHorizontalAlignment(SwingConstants.LEFT);

        return label;
    }

    /**
     * Method which creates button to show next page.
     * @return  {@code JButton} - new {@code JButton} with pre created configuration
     * */
    private JButton createNextPageButton() {
        JButton button = new JButton();
        button.setText(NEXT_PAGE_BUTTON_TITLE);
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TableElement> movies = nextPageHandler.getNextPage();
                ((MovieTableModel) elementsTable.getModel()).updateMovies(movies);
                ((MovieTableModel) elementsTable.getModel()).fireTableDataChanged();
            }
        });

        return button;
    }

    private JButton createAddButton() {
        JButton button = new JButton();
        button.setText(ADD_BUTTON_TITLE);
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPageGUI addPageGUI = new AddPageGUI();
                addPageGUI.setAddHandler(new AddHandler(addPageGUI, Client.sender, Client.receiver));
                addPageGUI.createAndShowWindow();
            }
        });

        return button;
    }
    
    private JTable createFilmsTable(ArrayList<TableElement> movies) {
        MovieTableModel tableModel = new MovieTableModel(movies);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);

                if (row >= 0) {
                    TableElement selectedMovie = ((MovieTableModel) table.getModel()).getMovieAt(row);
                    //CurrentMovie movie = sender.send(new Object[]{"load_next_sorted_page", new Object[]{Client.pageCounter}, Client.currentClient.getUserName(), Client.currentClient.getUserPassword()});
                    String movie = infoHandler.info(selectedMovie.getId());
                    
                    ElementInfoPageGUI elementInfoPageGUI = new ElementInfoPageGUI(movie);
                    DeleteHandler deleteHandler = new DeleteHandler(elementInfoPageGUI, Client.sender);
                    elementInfoPageGUI.setDeleteHandler(deleteHandler);
                    elementInfoPageGUI.setId(selectedMovie.getId());
                    elementInfoPageGUI.createAndShowWindow();
                }
            }
        });

        return table;
    }

    private JPanel createSortingMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel();
        label.setText(SORTING_MENU_LABEL);
        label.setPreferredSize(SMALL_LABEL_SIZE);

        JComboBox<String> menu = new JComboBox<>(SORTING_AND_FILTERING_PARAMETERS);
        menu.setPreferredSize(MENU_SIZE);

        JButton button = new JButton();
        button.setText(SORT_BUTTON_TITLE);
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TableElement> elements = sortingHandler.sort((String) menu.getSelectedItem());
                ((MovieTableModel) elementsTable.getModel()).updateMovies(elements);
                ((MovieTableModel) elementsTable.getModel()).fireTableDataChanged();
            }
        });

        panel.add(button);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(label);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(Box.createRigidArea(new Dimension((int) TEXT_FIELD_SIZE.getWidth(), (int) TEXT_FIELD_SIZE.getHeight())));
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(menu);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createFiltersMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel();
        label.setText(FILTERS_MENU_LABEL);
        label.setPreferredSize(SMALL_LABEL_SIZE);

        JTextField textField = new JTextField();
        textField.setPreferredSize(TEXT_FIELD_SIZE);
        textField.setText(FILTER_HINT);
        textField.setForeground(HINT_COLOR);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(FILTER_HINT)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(FILTER_HINT);
                    textField.setForeground(HINT_COLOR);
                }
            }
        });


        JComboBox<String> menu = new JComboBox<>(SORTING_AND_FILTERING_PARAMETERS);
        menu.setPreferredSize(MENU_SIZE);

        JButton button = new JButton();
        button.setText(FILTERS_BUTTON_TITLE);
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TableElement> elements = filterHandler.filter((String) menu.getSelectedItem(), textField.getText());
                ((MovieTableModel) elementsTable.getModel()).updateMovies(elements);
                ((MovieTableModel) elementsTable.getModel()).fireTableDataChanged();
            }
        });

        panel.add(button);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(label);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
        panel.add(menu);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

     public void createAndShowWindow() {
        JPanel leftPanel = new JPanel(new BorderLayout(0, VERTICAL_STRUT));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel userPanel = new JPanel(new GridLayout(2, 1));
        userPanel.add(clockLabel);
        userPanel.add(userLabel);
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(userPanel);

        JScrollPane scrollTable = new JScrollPane(elementsTable);
        scrollTable.setPreferredSize(TABLE_SIZE);
        JPanel tp = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tp.add(scrollTable);
        tp.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(tp);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(nextPageButton);
        buttonPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT));
        buttonPanel.add(addButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(buttonPanel);

        leftPanel.add(panel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        sortingMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortingMenu.setPreferredSize(MENU_PANEL_SIZE);
        filtersMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        filtersMenu.setPreferredSize(MENU_PANEL_SIZE);
         languageMenu.setPreferredSize(MENU_PANEL_SIZE);

        JPanel menusPanel = new JPanel();
        menusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        menusPanel.add(filtersMenu);
        menusPanel.add(sortingMenu);
        menusPanel.add(languageMenu);


        
        menusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        drawingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawingPanel.setPreferredSize(new Dimension(500, 500));

        rightPanel.add(menusPanel);
        rightPanel.add(drawingPanel);
        rightPanel.add(Box.createVerticalGlue());



        window.add(leftPanel, BorderLayout.WEST);
        window.add(rightPanel, BorderLayout.CENTER);
        window.setVisible(true);
        /*
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(userLabel, BorderLayout.WEST);
        topPanel.add(clockLabel, BorderLayout.EAST);
        panel.add(topPanel);
        */
     }
     

     private JPanel createLanguageMenu() {
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

         JLabel label = new JLabel();
         label.setText(LANGUAGE_MENU_LABEL);
         label.setPreferredSize(SMALL_LABEL_SIZE);

         JComboBox<String> menu = new JComboBox<>(LANGUAGE_PARAMETERS);
         menu.setPreferredSize(new Dimension(100, 100));//

         JButton button = new JButton();
         button.setText(LANGUAGE_BUTTON_TITLE);
         button.setPreferredSize(BUTTON_SIZE);
         button.setBackground(BUTTON_COLOR);
         button.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
            	 LangManager.setLanguage((String) menu.getSelectedItem());
            	 setLanguageMainPage();
            	 window.revalidate();
            	 repaint();

             }
         });

         panel.add(button);
         panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
         panel.add(label);
         panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
         panel.add(Box.createRigidArea(new Dimension((int) TEXT_FIELD_SIZE.getWidth(), (int) TEXT_FIELD_SIZE.getHeight())));
         panel.add(Box.createVerticalStrut(SMALL_VERTICAL_STRUT));
         panel.add(menu);
         panel.add(Box.createVerticalGlue());

         return panel;
     }


    public void refreshTable() {
    	 ArrayList<TableElement> elements = updateCollectionHandler.updateCollection();
         System.out.println("----");
         elements.forEach(t -> {System.out.println(t.getName() + t.getId());});
         System.out.println("----");
         ((MovieTableModel) elementsTable.getModel()).updateMovies(elements);
         ((MovieTableModel) elementsTable.getModel()).fireTableDataChanged();
    }
    
    public void setLanguageMainPage() {
    	TITLE = LangManager.get("title");
    	SORTING_MENU_LABEL =  LangManager.get("sorting.menu");
        FILTERS_MENU_LABEL =  LangManager.get("filters.menu");
        NEXT_PAGE_BUTTON_TITLE = LangManager.get("next.page.button.title");
        LANGUAGE_MENU_LABEL = LangManager.get("language.menu.label");
        SORT_BUTTON_TITLE = LangManager.get("sort.button.title");
        FILTERS_BUTTON_TITLE = LangManager.get("filters.button.title");;
        FILTER_HINT = LangManager.get("filter.hint");
        ADD_BUTTON_TITLE = LangManager.get("add.button.title");
        LANGUAGE_BUTTON_TITLE = LangManager.get("language.button.label");
        USER_TITLE = LangManager.get("user.title");
    }


    private JLabel createClockLabel(Locale locale) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(200, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        DateTimeFormatter timeFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(locale);

        Timer clockTimer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = now.format(timeFormatter);
            label.setText(formattedTime);
        });
        clockTimer.start();

        return label;
    }

    public ObjectDrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setNextPageHandler(NextPageHandler nextPageHandler) {
        this.nextPageHandler = nextPageHandler;
    }

    public void setSortingHandler(SortingHandler sortingHandler) {
        this.sortingHandler = sortingHandler;
    }

    public void setFilterHandler(FilterHandler filterHandler) {
        this.filterHandler = filterHandler;
    }
    
    public void setInfoHandler(InfoHandler infoHandler) {
    	this.infoHandler = infoHandler;
    }

	public void setUpdateCollectionHandler(UpdateCollectionHandler updateCollectionHandler) {
		this.updateCollectionHandler = updateCollectionHandler;
	}



    public JTable getElementsTable() {
        return elementsTable;
    }
}
