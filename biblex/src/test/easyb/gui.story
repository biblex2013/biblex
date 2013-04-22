import fi.helsinki.biblex.App
import fi.helsinki.biblex.domain.BibTexEntry
import fi.helsinki.biblex.storage.SQLiteStorage
import fi.helsinki.biblex.ui.*
import org.fest.swing.core.GenericTypeMatcher
import org.fest.swing.fixture.FrameFixture
import org.fest.swing.fixture.JListFixture
import org.fest.swing.timing.Pause
import org.fest.swing.data.TableCell
import org.fest.swing.fixture.JTableFixture
import org.fest.swing.core.MouseButton

import javax.swing.JList
import java.awt.Toolkit
import javax.swing.JTable

description 'User story -testit'

scenario 'Pystyy tallentamaan article-viitteen', {

    given 'GUI on luotu', {
        App.createInstance()
        app = App.getInstance()
        app.setStorage(new SQLiteStorage(":memory:"))

        gui_ = app.getGUI()
        refTable = gui_.getRefTable()
        refWindow = gui_.getWindow()
        testFrame = new FrameFixture(refWindow)
        testList = testFrame.table(new GenericTypeMatcher<JTable>(JTable.class)  {
            protected boolean isMatching(JTable table) {
                return table.isShowing();
            }
        })
        storage = app.getStorage()
        testFrame.show()
    }

    when 'article-viite luotu ja talletettu', {
        ARTICLE_NAME = "Artikkeli" + System.currentTimeMillis()
        EXPORT_FILE  = "export-" + System.currentTimeMillis() + ".bib"

        testFrame.comboBox("p_entryStyleInput").selectItem("article")
        testFrame.comboBox("p_entryStyleInput").requireSelection("article")
        testFrame.textBox("p_entryNameInput").enterText(ARTICLE_NAME)
        testFrame.button("p_setEntryButton").click()

        testFrame.textBox("year").enterText("year")
        testFrame.textBox("journal").enterText("journal")
        testFrame.textBox("author").enterText("Thor")

        testFrame.button("p_submitButton").click()

        Pause.pause(100)

        // "title" kenttä ei saa olla tyhjä
        testFrame.optionPane().requireErrorMessage()
        testFrame.optionPane().button().click()

        Pause.pause(100)

        testFrame.textBox("title").enterText("title")

        // luo 'badone'-kenttä ja yritä submitata
        testFrame.comboBox("p_fieldNameInput").enterText("badone")
        testFrame.button("p_addFieldButton").click()
        testFrame.button("p_submitButton").click()

        Pause.pause(100)

        testFrame.optionPane().requireErrorMessage()
        testFrame.optionPane().button().click()

        // poista 'badone'-kenttä, lisää 'volume' ja submittaa
        testFrame.button("btnDeleteField:badone").click()
        testFrame.comboBox("p_fieldNameInput").enterText("volume")
        testFrame.button("p_addFieldButton").click()

        Pause.pause(100)

        testFrame.button("p_submitButton").click()
    }

    then 'article-viite löytyy kannasta', {
        entry = storage.get(ARTICLE_NAME)
        entry.shouldNotBe null
        entry.getName()     .equals(ARTICLE_NAME)   .shouldBe true
        entry.get("year")   .equals("year")         .shouldBe true
        entry.get("journal").equals("journal")      .shouldBe true
        entry.get("author") .equals("Thor")         .shouldBe true
        entry.get("title")  .equals("title")        .shouldBe true

        // volume is optional, so don't insist on non-emptyness of value
        entry.get("volume") .equals("")             .shouldBe true
    }
}

scenario 'Pystyy tallentamaan book-viitteen', {

    when 'book-viite luotu ja talletettu', {
        BOOK_NAME = "Kirja" + System.currentTimeMillis()

        testFrame.comboBox("p_entryStyleInput").selectItem("book")
        testFrame.comboBox("p_entryStyleInput").requireSelection("book")
        testFrame.textBox("p_entryNameInput").enterText(BOOK_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(100)

        TX_AUTHOR =    "Thorr"
        TX_TITLE  =    "Calculus I"
        TX_PUBLISHER = "Usher"
        TX_YEAR =      "354"

        // book requires author or editor, but not both, so add author
        testFrame.comboBox("p_fieldNameInput").enterText("author")
        testFrame.button("p_addFieldButton").click()

        testFrame.textBox("author")     .enterText(TX_AUTHOR)
        testFrame.textBox("title")      .enterText(TX_TITLE)
        testFrame.textBox("publisher")  .enterText(TX_PUBLISHER)
        testFrame.textBox("year")       .enterText(TX_YEAR)

        testFrame.button("p_submitButton").click()

        Pause.pause(100)
    }

    then 'book-viite löytyy kannasta', {
        entry = storage.get(BOOK_NAME)
        entry.shouldNotBe null
        entry.getName().equals(BOOK_NAME).shouldBe true

        entry.get("author")     .equals(TX_AUTHOR)      .shouldBe true
        entry.get("title")      .equals(TX_TITLE)       .shouldBe true
        entry.get("publisher")  .equals(TX_PUBLISHER)   .shouldBe true
        entry.get("year")       .equals(TX_YEAR)        .shouldBe true
    }
}

scenario 'Pystyy tallentamaan inproceedings-viitteen', {

    when 'inproceedings-viite luotu ja talletettu', {
        INPROC_NAME = "Inproc" + System.currentTimeMillis()

        testFrame.comboBox("p_entryStyleInput").selectItem("inproceedings")
        testFrame.comboBox("p_entryStyleInput").requireSelection("inproceedings")
        testFrame.textBox("p_entryNameInput").enterText(INPROC_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(100)

        TX_AUTHOR       =    "Thorrr"
        TX_TITLE        =    "About Inproceedings"
        TX_BOOKTITLE    =    "Big funky book"
        TX_YEAR         =    "2013"

        // erikoismerkit eivät säilyy, esim '*' päättyy '(':ksi

        testFrame.textBox("author")     .enterText(TX_AUTHOR)
        testFrame.textBox("title")      .enterText(TX_TITLE)
        testFrame.textBox("booktitle")  .enterText(TX_BOOKTITLE)
        testFrame.textBox("year")       .enterText(TX_YEAR)

        testFrame.button("p_submitButton").click()

        Pause.pause(100)
    }

    then 'inproceedings-viite löytyy kannasta', {
        entry = storage.get(INPROC_NAME)
        entry.shouldNotBe null
        entry.getName().equals(INPROC_NAME).shouldBe true

        entry.get("author")     .equals(TX_AUTHOR)      .shouldBe true
        entry.get("title")      .equals(TX_TITLE)       .shouldBe true
        entry.get("booktitle")  .equals(TX_BOOKTITLE)   .shouldBe true
        entry.get("year")       .equals(TX_YEAR)        .shouldBe true
    }
}

scenario 'New Reference -toiminto menusta tyhjentää näkymän', {
    when 'Luotu viite', {
        BOOK_NAME = "Book" + System.currentTimeMillis()

        testFrame.comboBox("p_entryStyleInput").selectItem("book")
        testFrame.comboBox("p_entryStyleInput").requireSelection("book")
        testFrame.textBox("p_entryNameInput").enterText(BOOK_NAME)
        testFrame.button("p_setEntryButton").click()
    }

    then 'New Reference -toiminto tyhjentää näkymän', {

        Pause.pause(3000)

        menuItem = testFrame.menuItem("p_menuNewEntry")
        menuItem.requireEnabled()
        menuItem.click()

        Pause.pause(3000)

        ensureThrows(Exception) {
            // nappulan ei pitäisi olla siellä
            testFrame.button("btnDeleteField:author")
        }
    }
}

scenario 'Puuttuva pakollinen kenttä aiheuttaa virhevaroituksen', {
when 'inproceedings-viite luotu ja talletettu', {
        ARTICLE_NAME = "Artikkeli" + System.currentTimeMillis()

        testFrame.comboBox("p_entryStyleInput").selectItem("article")
        testFrame.comboBox("p_entryStyleInput").requireSelection("article")
        testFrame.textBox("p_entryNameInput").enterText(ARTICLE_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(100)

        TX_AUTHOR       =    "Thor 2"
        TX_TITLE        =    "Title"
        TX_JOURNAL      =    "Bonjour"
        TX_YEAR         =    "2013"

        testFrame.textBox("author")     .enterText(TX_AUTHOR)
        testFrame.textBox("title")      .enterText(TX_TITLE)
        testFrame.textBox("year")       .enterText(TX_YEAR)

        Pause.pause(300)

        // nyt poista 'journal'-kenttä
        testFrame.button("btnDeleteField:journal").click()
        testFrame.button("p_submitButton").click()

        Pause.pause(300)

        // error-ikkuna päällä
        testFrame.optionPane().requireErrorMessage()
        testFrame.optionPane().button().click()

        Pause.pause(300)

        // luo uudestaan 'journal'-kenttä
        testFrame.comboBox("p_fieldNameInput").enterText("journal")
        testFrame.button("p_addFieldButton").click()

        Pause.pause(300)

        // submita, error koska journal:lla ei arvoa
        testFrame.button("p_submitButton").click()
        testFrame.optionPane().requireErrorMessage()
        testFrame.optionPane().button().click()

        // journal:iin tekstiä
        testFrame.textBox("journal")    .enterText(TX_JOURNAL)

        Pause.pause(300)

        // ja submita
        testFrame.button("p_submitButton").click()
        Pause.pause(100)
    }

    then 'inproceedings-viite löytyy kannasta', {
        entry = storage.get(ARTICLE_NAME)
        entry.shouldNotBe null
        entry.getName().equals(ARTICLE_NAME).shouldBe true

        entry.get("author")     .equals(TX_AUTHOR)      .shouldBe true
        entry.get("title")      .equals(TX_TITLE)       .shouldBe true
        entry.get("journal")    .equals(TX_JOURNAL)     .shouldBe true
        entry.get("year")       .equals(TX_YEAR)        .shouldBe true
    }
}

scenario 'Viitteen kopioiminen leikepöydälle BibTeX-muodossa', {
when 'viite luotu ja talletettu', {
        ARTICLE_NAME = "Artikkeli" + System.currentTimeMillis()

        testFrame.comboBox("p_entryStyleInput").selectItem("article")
        testFrame.comboBox("p_entryStyleInput").requireSelection("article")
        testFrame.textBox("p_entryNameInput").enterText(ARTICLE_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(100)

        TX_AUTHOR       =    "Thor 2"
        TX_TITLE        =    "Title"
        TX_JOURNAL      =    "Bonjour"
        TX_YEAR         =    "2013"

        testFrame.textBox("author")     .enterText(TX_AUTHOR)
        testFrame.textBox("title")      .enterText(TX_TITLE)
        testFrame.textBox("year")       .enterText(TX_YEAR)

        Pause.pause(100)

        // journal:iin tekstiä
        testFrame.textBox("journal")    .enterText(TX_JOURNAL)

        Pause.pause(100)

        // ja submita
        testFrame.button("p_submitButton").click()
        Pause.pause(100)
    }

    then 'kopioiminen leikepöydälle BibTeX-muodossa onnistuu', {
        entry = storage.get(ARTICLE_NAME)
        entry.shouldNotBe null

        testList.click(new TableCell(refTable.getModel().getRowByName(ARTICLE_NAME),0), MouseButton.LEFT_BUTTON)
        testList.showPopupMenu().menuItem("menuCopyEntryToClipboard").click()
        Toolkit.getDefaultToolkit().getSystemClipboard().getContents().toString().equals(entry.toString())
    }
}
