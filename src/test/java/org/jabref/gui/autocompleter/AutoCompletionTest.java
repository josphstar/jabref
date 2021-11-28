package org.jabref.gui.autocompleter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
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

@Tag("dropdown")
public class AutoCompletionTest {

    private WordSuggestionProvider autoCompleter;
    private BibDatabase database;

    @BeforeEach
    void setUp() throws Exception {
        database = new BibDatabase();
        autoCompleter = new WordSuggestionProvider(StandardField.TITLE, database);
    }

    @Test
    void completeSearchbarInDropdown() {    //tests autocomplete word in searchbar
       // assertThrows(.class, () -> new WordSuggestionProvider(null, database));
    }

    @Test
    void completeWithoutAddingAnythingReturnsSomething() {  //tests recommended words from past
        Collection<String> result = autoCompleter.provideSuggestions(getRequest(("test")));
        assertEquals(Collections.emptyList(), result);

    }

    @Test
    void completeReturnsMultipleResultsInDropdown() {   //tests list of recommended words below searchbar
        BibEntry entryOne = new BibEntry();
        entryOne.setField(StandardField.TITLE, "testValueOne");
        database.insertEntry(entryOne);
        BibEntry entryTwo = new BibEntry();
        entryTwo.setField(StandardField.TITLE, "testValueTwo");
        database.insertEntry(entryTwo);

        Collection<String> result = autoCompleter.provideSuggestions(getRequest(("testValue")));
        assertEquals(Arrays.asList("testValueOne", "testValueTwo"), result);
    }
}
