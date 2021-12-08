package org.jabref.gui.autocompleter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.jabref.gui.search.SearchFieldSynchronizer;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import org.jabref.gui.icon.IconTheme;
import org.jabref.gui.search.DropDownMenu;
import org.jabref.gui.search.GlobalSearchBar;
import org.jabref.gui.search.RecentSearch;
import org.jabref.gui.search.SearchFieldSynchronizer;
import org.jabref.gui.search.SearchTextField;
import org.jabref.model.entry.field.Field;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.jabref.model.database.BibDatabase;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.jabref.gui.autocompleter.AutoCompleterUtil.getRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(ApplicationExtension.class)
@Tag("dropdown")
class AutoCompletionTest {

    private WordSuggestionProvider autoCompleter;
    private BibDatabase database;

    private CustomTextField textfield;
    private TextField searchString;
    private Button addString;
    private Button deleteButton;

    @BeforeEach
    void setUp() throws Exception {
        database = new BibDatabase();
        autoCompleter = new WordSuggestionProvider(StandardField.TITLE, database);
    }

    @Start
    public void start(Stage stage) {

        this.textfield = new CustomTextField();
        this.searchString = new TextField();
        addString = IconTheme.JabRefIcons.ADD_ENTRY.asButton();
        deleteButton = IconTheme.JabRefIcons.DELETE_ENTRY.asButton();

        textfield.setOnAction(actionEvent -> {
            textfield.setText("testValue");
        });

        stage.setScene(new Scene(new StackPane(new HBox(textfield, addString, deleteButton)), 500, 500));
        stage.show();


    }

    @Test
    void testEntryInDropdownSearchbar(FxRobot robot) {

        BibEntry entry = new BibEntry();
        entry.setField(StandardField.TITLE, "testValue");
        database.insertEntry(entry);
        robot.write(entry.getFieldValues().toString());
        robot.clickOn(addString);
        Collection<String> result = autoCompleter.provideSuggestions(getRequest(("testValue")));
        assertEquals(Arrays.asList("testValue"), result);
        assertEquals(entry.getFieldValues().toString(), textfield.getText());
    }


    @Test
    void completeWithoutAddingAnythingReturnsSomething(FxRobot robot) {

        BibEntry entry = new BibEntry();
        entry.setField(StandardField.TITLE, "");
        database.insertEntry(entry);
        robot.write(Collections.emptyList().toString());
        robot.clickOn(addString).toString();
        Collection<String> result = autoCompleter.provideSuggestions(getRequest(("")));
        assertEquals(Collections.emptyList(), result);
        assertEquals(entry.getFieldValues().toString(), textfield.getText());
    }

    @Test
    void completeReturnsMultipleResultsInDropdown(FxRobot robot) {

        String tester = "testValueOne";

        BibEntry entryOne = new BibEntry();
        entryOne.setField(StandardField.TITLE, tester);
        database.insertEntry(entryOne);
        robot.write(entryOne.getFieldValues().toString());
        BibEntry entryTwo = new BibEntry();
      //robot.clickOn(deleteButton);
      //robot.point(textfield);
        robot.eraseText(tester.length()+2);
        entryTwo.setField(StandardField.TITLE, "testValueTwo");
        database.insertEntry(entryTwo);
        robot.write(entryTwo.getFieldValues().toString());
        Collection<String> result = autoCompleter.provideSuggestions(getRequest(("testValue")));
        assertEquals(Arrays.asList("testValueOne", "testValueTwo"), result);
    }
}
