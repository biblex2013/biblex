import fi.helsinki.biblex.App
import fi.helsinki.biblex.domain.BibTexEntry
import fi.helsinki.biblex.storage.SQLiteStorage
import fi.helsinki.biblex.ui.*
import org.fest.swing.fixture.FrameFixture
import org.fest.swing.timing.Pause

description 'User story -testit'

scenario 'Pystyy tallentamaan article-viitteen', {

    given 'GUI on luotu', {
        App.createInstance()
        app = App.getInstance()
        app.setStorage(new SQLiteStorage(":memory:"))

        gui_ = app.getGUI()
        refWindow = gui_.getWindow()
        testFrame = new FrameFixture(refWindow)
        storage = app.getStorage()
        testFrame.show()
    }

    when 'article-viite luotu ja talletettu', {
        ARTICLE_NAME = "Artikkeli" + System.currentTimeMillis()
        EXPORT_FILE  = "export-" + System.currentTimeMillis() + ".bib"

        testFrame.comboBox().selectItem("article")
        testFrame.comboBox().requireSelection("article")
        testFrame.textBox("p_entryNameInput").enterText(ARTICLE_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(1000)

        testFrame.textBox("year").enterText("year")
        testFrame.textBox("journal").enterText("journal")
        testFrame.textBox("author").enterText("Thor")

        testFrame.button("p_submitButton").click()

        Pause.pause(1000)

        // "title" kenttä ei saa olla tyhjä
        testFrame.optionPane().requireErrorMessage()
        testFrame.optionPane().button().click()

        Pause.pause(1000)

        testFrame.textBox("title").enterText("title")
        testFrame.button("p_submitButton").click()

        Pause.pause(1000)

        // luo kaksi vapaaehtoista kenttää: volume, number (tulossa)

    }

    then 'article-viite löytyy kannasta', {
        entry = storage.get(ARTICLE_NAME)
        entry.getName().equals(ARTICLE_NAME).shouldBe true
        entry.get("year").equals("year").shouldBe true
        entry.get("journal").equals("journal").shouldBe true
        entry.get("author").equals("Thor").shouldBe true
        entry.get("title").equals("title").shouldBe true
    }
}

scenario 'Pystyy tallentamaan book-viitteen', {

    when 'book-viite luotu ja talletettu', {
        BOOK_NAME = "Kirja" + System.currentTimeMillis()

        testFrame.comboBox().selectItem("book")
        testFrame.comboBox().requireSelection("book")
        testFrame.textBox("p_entryNameInput").enterText(BOOK_NAME)
        testFrame.button("p_setEntryButton").click()

        Pause.pause(1000)

        TX_AUTHOR =    "Thorr"
        TX_TITLE  =    "Calculus I"
        TX_EDITOR =    "Odin"
        TX_PUBLISHER = "Usher"
        TX_YEAR =      "354"

        testFrame.textBox("author")     .enterText(TX_AUTHOR)
        testFrame.textBox("title")      .enterText(TX_TITLE)
        testFrame.textBox("editor")     .enterText(TX_EDITOR)
        testFrame.textBox("publisher")  .enterText(TX_PUBLISHER)
        testFrame.textBox("year")       .enterText(TX_YEAR)

        Pause.pause(1000)

        testFrame.button("p_submitButton").click()

        Pause.pause(1000)
    }

    then 'book-viite löytyy kannasta', {
        entry = storage.get(BOOK_NAME)
        entry.shouldNotBe null
        entry.getName().equals(BOOK_NAME).shouldBe true

        entry.get("author")     .equals(TX_AUTHOR)      .shouldBe true
        entry.get("title")      .equals(TX_TITLE)       .shouldBe true
        entry.get("editor")     .equals(TX_EDITOR)      .shouldBe true
        entry.get("publisher")  .equals(TX_PUBLISHER)   .shouldBe true
        entry.get("year")       .equals(TX_YEAR)        .shouldBe true
    }
}