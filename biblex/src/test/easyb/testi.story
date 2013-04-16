import fi.helsinki.biblex.exporter.*;
import fi.helsinki.biblex.storage.*;
import fi.helsinki.biblex.domain.*;

scenario "exporttaus luo filen", {

    given "tuore storage exportterille", {
        storage = new SQLiteStorage(":memory:")
        exporter = new ExportToFile(storage)
    }

    when "kutsutaan write():ä", {
        exporter.write("test.txt")
    }

    then "test.txt on olemassa", {
        file = new File("test.txt")
        file.exists().shouldBe true
    }

    and "poista pasket lopuksi", {
        file.delete()
        storage.close()
    }
}

scenario "Pystyy tallentaa artikkelin", {

    given "db on päällä ja tyhjä", {
        storage = new SQLiteStorage(":memory:")
    }

    when "lisätään article-entry", {
        e = new BibTexEntry("name1", "article")
        e.put("author", "Thor")
        e.put("title", "Titteli")
        e.put("journal", "Journal")
        e.put("year", "2010")
        storage.add(e)
    }

    then "lisätty article-entry löytyy", {
        e = storage.get("name1")
        e.shouldNotBe null
        e.get("author").contains("Thor").shouldBe true

        storage.close()
    }
}