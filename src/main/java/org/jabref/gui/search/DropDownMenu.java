package org.jabref.gui.search;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import org.jabref.gui.JabRefFrame;
import org.jabref.gui.icon.IconTheme;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;

public class DropDownMenu {
    // DropDown Searchbar
    public PopOver searchbarDropDown;
    public Button authorButton;
    public Button journalButton;
    public Button titleButton;
    public Button yearButton;
    public Button yearRangeButton;
    public Button andButton;
    public Button orButton;
    public Button leftBracketButton;
    public Button rightBracketButton;
    public RecentSearch recentSearch;
    public Button deleteSearchString;
    public Button searchStart;
    // private final Button articleButton;
    // private final Button bookButton;
    // private final Button citationKeyButton;

    // test buttons
    // private final Button testButton;

    public DropDownMenu(JabRefFrame frame, CustomTextField searchField, GlobalSearchBar globalSearchBar, SearchFieldSynchronizer searchFieldSynchronizer) {
//        SearchFieldSynchronizer searchFieldSynchronizer = new SearchFieldSynchronizer(searchField);

        authorButton = new Button("Author");
        journalButton = new Button("Journal");
        titleButton = new Button("Title");
        yearButton = new Button("Year");
        yearRangeButton = new Button("Year-Range");
        andButton = new Button("AND");
        orButton = new Button("OR");
        leftBracketButton = new Button("(");
        rightBracketButton = new Button(")");
        deleteSearchString = IconTheme.JabRefIcons.DELETE_ENTRY.asButton();
        searchStart = IconTheme.JabRefIcons.SEARCH.asButton();

        // articleButton = new Button("Article");
        // bookButton = new Button("Book");
        // citationKeyButton = new Button("CitationKey");
        Text titleLucene = new Text(" Lucene Search");
        Text titleRecent = new Text(" Recent Searches");
        TextField luceneSearch = new TextField();
        luceneSearch.editableProperty().set(false);
        recentSearch = new RecentSearch(globalSearchBar);
        HBox recentSearchBox = recentSearch.getHBox();
        // HBox luceneField = new HBox(luceneSearch, deleteSearchString, searchStart);
        HBox buttonsLucene = new HBox(2, authorButton, journalButton, titleButton,
                yearButton, yearRangeButton);
        HBox andOrButtons = new HBox(2, andButton, orButton);
        HBox bracketButtons = new HBox(2, leftBracketButton, rightBracketButton);

        VBox mainBox = new VBox(4, titleLucene, /* luceneField,*/ buttonsLucene, andOrButtons, bracketButtons, titleRecent, recentSearchBox);
        mainBox.setMinHeight(500);
        mainBox.setMinWidth(searchField.getWidth());
        Node buttonBox = mainBox;
/*
        searchStart.addEventFilter((MouseEvent.MOUSE_CLICKED), event -> {
            searchField.setText(luceneSearch.getText());
            globalSearchBar.performSearch();
        });

        deleteSearchString.addEventFilter((MouseEvent.MOUSE_CLICKED), event -> {
            luceneSearch.clear();
            searchField.clear();
            searchFieldSynchronizer.deleteEntrysInSearchItemList();
        });
*/
        // TODO currently double click necessary to click out of focus (for dropdown and searchfield);
        //  preferred is one click

        searchField.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (searchbarDropDown == null || !searchbarDropDown.isShowing()) {
                searchbarDropDown = new PopOver(buttonBox);
                searchbarDropDown.setWidth(searchField.getWidth());
                searchbarDropDown.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
                searchbarDropDown.setContentNode(buttonBox);
                searchbarDropDown.setDetachable(false); // not detachable
                searchbarDropDown.show(searchField);
            }
        });

        // authorButton action
        authorButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevAttribute(searchField)) { // checks if the search term prior is an attribute and wont queue another if so
//                checkAndAddSpace(searchField); // checks if there is a space prior and if not adds it
//                searchField.insertText(searchField.getCaretPosition(), "author:");
                if (!isPrevOperator(searchFieldSynchronizer)) {
                    searchFieldSynchronizer.addSearchItem("OR", ""); // add OR search by default
                }
                searchFieldSynchronizer.addSearchItem("author:", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }
        });

        // journalButton action
        journalButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevAttribute(searchField)) {
//                checkAndAddSpace(searchField);
//                searchField.insertText(searchField.getCaretPosition(), "journal:");
                if (!isPrevOperator(searchFieldSynchronizer)) {
                    searchFieldSynchronizer.addSearchItem("OR", ""); // add OR search by default
                }
                searchFieldSynchronizer.addSearchItem("journal:", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }
        });

        // titleButton action
        titleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevAttribute(searchField)) {
//                checkAndAddSpace(searchField);
//                searchField.insertText(searchField.getCaretPosition(), "title:");
                if (!isPrevOperator(searchFieldSynchronizer)) {
                    searchFieldSynchronizer.addSearchItem("OR", ""); // add OR search by default
                }
                searchFieldSynchronizer.addSearchItem("title:", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }
        });

        // yearButton action
        yearButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevAttribute(searchField)) {
//                checkAndAddSpace(searchField);
//                searchField.insertText(searchField.getCaretPosition(), "year:");
                if (!isPrevOperator(searchFieldSynchronizer)) {
                    searchFieldSynchronizer.addSearchItem("OR", ""); // add OR search by default
                }
                searchFieldSynchronizer.addSearchItem("year:", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }
        });

        // yearRangeButton action
        yearRangeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

        });

        // andButton action
        andButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevOperator(searchFieldSynchronizer) && !isPrevAttribute(searchField)) {
//                if (searchField.getCaretPosition() != 0) {
//                    if (!searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(" ")) {
//                        searchField.insertText(searchField.getCaretPosition(), " ");
//                        searchField.positionCaret(searchField.getText().length());
//                    }
//                }
//                searchField.insertText(searchField.getCaretPosition(), "AND ");
                searchFieldSynchronizer.addSearchItem("AND", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }

        });

        // orButton action
        orButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isPrevOperator(searchFieldSynchronizer) && !isPrevAttribute(searchField)) {
                if (searchField.getCaretPosition() != 0) {
                    if (!searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(" ")) {
                        searchField.insertText(searchField.getCaretPosition(), " ");
                        searchField.positionCaret(searchField.getText().length());
                    }
                }
//                searchField.insertText(searchField.getCaretPosition(), "OR ");
                searchFieldSynchronizer.addSearchItem("OR", "");
                searchField.setText(searchFieldSynchronizer.searchStringBuilder());
                // luceneSearch.setText(searchFieldSynchronizer.searchStringBuilder());
                searchField.positionCaret(searchField.getText().length());
            }
        });

        // leftBracketButton action
        // TODO implement searchfieldsynchronizer class
        leftBracketButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (searchField.getCaretPosition() != 0) {
                if (!searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(" ")) {
                    searchField.insertText(searchField.getCaretPosition(), " ");
                    searchField.positionCaret(searchField.getText().length());
                }
            }
            searchField.insertText(searchField.getCaretPosition(), "( ");
            searchField.positionCaret(searchField.getText().length());
        });

        // rightBracketButton action
        // TODO implement searchfieldsynchronizer class
        rightBracketButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (searchField.getCaretPosition() != 0) {
                if (!searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(" ")) {
                    searchField.insertText(searchField.getCaretPosition(), " ");
                    searchField.positionCaret(searchField.getText().length());
                }
            }
            searchField.insertText(searchField.getCaretPosition(), ") ");
            searchField.positionCaret(searchField.getText().length());
        });
    }

    private void checkAndAddSpace(CustomTextField searchField) {
        if (searchField.getCaretPosition() != 0) {
            if (!searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(" ")) {
                searchField.insertText(searchField.getCaretPosition(), " ");
                searchField.positionCaret(searchField.getText().length());
            }
        }
    }

    private boolean isPrevAttribute(CustomTextField searchField) {
        if (searchField.getCaretPosition() != 0) {
            if (searchField.getText(searchField.getCaretPosition() - 1, searchField.getCaretPosition()).equals(":")) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrevOperator(SearchFieldSynchronizer searchFieldSynchronizer) {
//        System.out.println("isPrevOperator?  " + searchFieldSynchronizer.isPrevOperator());
        return searchFieldSynchronizer.isPrevOperator();
    }
}
