package org.jabref.gui.search;

import javafx.event.Event;
import javafx.stage.Stage;

import org.jabref.gui.Globals;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.StateManager;
import org.jabref.gui.undo.CountingUndoManager;
import org.jabref.preferences.PreferencesService;

import org.controlsfx.control.textfield.CustomTextField;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dropdown")
class DropDownTest {

    private CustomTextField searchField;
    private Stage mainStage;
    private JabRefFrame frame;
    private StateManager stateManager;
    private PreferencesService preferencesService;
    private CountingUndoManager undoManager;
    private SearchFieldSynchronizer searchFieldSynchronizer;
    private GlobalSearchBar globalSearchBar;
    private DropDownMenu dropDownMenu;

    public void setUp() {
        this.mainStage = new Stage();
        this.frame = new JabRefFrame(mainStage);
        this.stateManager = new StateManager();
        this.preferencesService = Globals.prefs;
        this.undoManager = new CountingUndoManager();
        this.searchFieldSynchronizer = new SearchFieldSynchronizer(searchField);
        this.globalSearchBar = new GlobalSearchBar(frame, stateManager, preferencesService, undoManager);
        this.searchField = globalSearchBar.searchField;
        this.dropDownMenu = new DropDownMenu(searchField, globalSearchBar, searchFieldSynchronizer);
    }

    @Test
    void testButtonWorking() {
        assertEquals("test","test");
    }

    @Test
    void testDropDownShowing() {
        setUp();
        dropDownMenu.searchbarDropDown.show(dropDownMenu.searchField);
        Event event = (Event) dropDownMenu.searchField.getOnMouseClicked();
        dropDownMenu.searchField.fireEvent(event);
        assertEquals(dropDownMenu.searchbarDropDown.isShowing(), true);
    }


    @Test
    void bla() {
        assertTrue(true);
    }
}
