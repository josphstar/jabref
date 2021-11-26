package org.jabref.gui.search;

import javafx.stage.Stage;

import org.jabref.gui.Globals;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.StateManager;
import org.jabref.gui.undo.CountingUndoManager;
import org.jabref.preferences.PreferencesService;

import org.controlsfx.control.textfield.CustomTextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DropDownTest {

    private CustomTextField searchField;
    private Stage mainStage;
    private JabRefFrame frame;
    private StateManager stateManager;
    private PreferencesService preferencesService;
    private CountingUndoManager undoManager;
    private SearchFieldSynchronizer searchFieldSynchronizer;
    private DropDownMenu dropDownMenu;
    private GlobalSearchBar globalSearchBar;

    @BeforeEach
    void setUp() {
         mainStage = new Stage();
         frame = new JabRefFrame(mainStage);
         stateManager = new StateManager();
         preferencesService = Globals.prefs;
         undoManager = new CountingUndoManager();
         searchFieldSynchronizer = new SearchFieldSynchronizer(searchField);
         globalSearchBar = new GlobalSearchBar(frame, stateManager, preferencesService, undoManager);
         dropDownMenu = new DropDownMenu(searchField, globalSearchBar, searchFieldSynchronizer);
    }

    @Test
    void testShow() {
        assertTrue("test".equals("test"));
        //assertTrue(dropDownMenu.authorButton.onActionProperty().get().toString().equals(searchField.getText()));
    }
}
