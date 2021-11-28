package org.jabref.gui.search;

import java.util.ArrayList;

import org.controlsfx.control.textfield.CustomTextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchFieldSynchronizerTest {
    private CustomTextField searchField;

    @Test
    void testGetSearchString() {
        // tests whether the actual text in the search field matches the one being recalled
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        searchField = SearchTextField.create();
        searchField.setText("author:Ruh AND year:2015 OR title:Corona");
        assertEquals(sync.getSearchString(), searchField.getText());
    }

    @Test
    void testSearchStringBuilder() {
        // tests whether the searchStringBuilder() method parses the correct string from searchItemList
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        sync.searchItemList.add(new SearchItem("author:", "Ruh"));
        sync.searchItemList.add(new SearchItem("logicalOperator", "AND"));
        sync.searchItemList.add(new SearchItem("year:", "2015"));
        sync.searchItemList.add(new SearchItem("logicalOperator", "OR"));
        sync.searchItemList.add(new SearchItem("title:", "Corona"));
        assertEquals(sync.searchStringBuilder(), "author:Ruh AND year:2015 OR title:Corona");
    }

    @Test
    void testSearchItemList() {
        // tests whether the searchItemList is constructed correctly TODO: overhaul itemType
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        sync.searchItemList.add(new SearchItem("author:", "Ruh"));
        sync.searchItemList.add(new SearchItem("logicalOperator", "AND"));
        sync.searchItemList.add(new SearchItem("year:", "2015"));
        sync.searchItemList.add(new SearchItem("logicalOperator", "OR"));
        sync.searchItemList.add(new SearchItem("title:", "Corona"));
        assertEquals(5, sync.searchItemList.size());
        assertEquals(sync.searchItemList.get(0).getItemType(), "author:");
        assertEquals(sync.searchItemList.get(0).getItem(), "Ruh");
        assertEquals(sync.searchItemList.get(1).getItemType(), "logicalOperator");
        assertEquals(sync.searchItemList.get(1).getItem(), "AND");
        assertEquals(sync.searchItemList.get(2).getItemType(), "year:");
        assertEquals(sync.searchItemList.get(2).getItem(), "2015");
        assertEquals(sync.searchItemList.get(3).getItemType(), "logicalOperator");
        assertEquals(sync.searchItemList.get(3).getItem(), "OR");
        assertEquals(sync.searchItemList.get(4).getItemType(), "title:");
        assertEquals(sync.searchItemList.get(4).getItem(), "Corona");
    }

    @Test
    void testUpdateSearchItemList() {
        // tests whether the method can parse the text from the search field correctly onto the searchItemList
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        searchField = SearchTextField.create();
        searchField.setText("author:Ruh AND year:2015 OR title:Corona");

        String str = searchField.getText();
        String[]words = str.split("(?<=:)|\\ ");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith("\"")) {
                boolean isWordAfterwards = i + 1 < words.length;
                if (isWordAfterwards && words[i + 1].endsWith("\"") && !words[i].endsWith(":")) {
                    String str2 = words[i] + " " + words[i + 1];
                    list.add(str2);
                    i++;
                } else {
                    list.add(words[i]);
                }
            } else {
                list.add(words[i]);
            }
        }

        sync.updateSearchItemList(list);

        assertEquals(sync.searchItemList.get(0).getItemType(), "author:");
        assertEquals(sync.searchItemList.get(0).getItem(), "Ruh");
        assertEquals(sync.searchItemList.get(1).getItemType(), "logicalOperator");
        assertEquals(sync.searchItemList.get(1).getItem(), "AND");
        assertEquals(sync.searchItemList.get(2).getItemType(), "year:");
        assertEquals(sync.searchItemList.get(2).getItem(), "2015");
        assertEquals(sync.searchItemList.get(1).getItemType(), "logicalOperator");
        assertEquals(sync.searchItemList.get(1).getItem(), "OR");
        assertEquals(sync.searchItemList.get(2).getItemType(), "title:");
        assertEquals(sync.searchItemList.get(2).getItem(), "Corona");
    }

    @Test
    void testIsPrevAttribute() {
        //
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        sync.searchItemList.add(new SearchItem("author:", "Ruh"));
        assertEquals(true, sync.isPrevAttribute());

        sync.searchItemList.add(new SearchItem("logicalOperator", "AND"));
        assertEquals(false, sync.isPrevAttribute());

        sync.searchItemList.add(new SearchItem("year:", "2015"));
        assertEquals(true, sync.isPrevAttribute());

        sync.searchItemList.add(new SearchItem("logicalOperator", "OR"));
        assertEquals(false, sync.isPrevAttribute());

        sync.searchItemList.add(new SearchItem("title:", "Corona"));
        assertEquals(true, sync.isPrevAttribute());
    }

    @Test
    void testIsPrevOperator() {
        SearchFieldSynchronizer sync = new SearchFieldSynchronizer(searchField);
        sync.searchItemList.add(new SearchItem("author:", "Ruh"));
        assertEquals(true, sync.isPrevOperator());

        sync.searchItemList.add(new SearchItem("logicalOperator", "AND"));
        assertEquals(false, sync.isPrevOperator());

        sync.searchItemList.add(new SearchItem("year:", "2015"));
        assertEquals(true, sync.isPrevOperator());

        sync.searchItemList.add(new SearchItem("logicalOperator", "OR"));
        assertEquals(false, sync.isPrevOperator());

        sync.searchItemList.add(new SearchItem("title:", "Corona"));
        assertEquals(true, sync.isPrevOperator());
    }
}
