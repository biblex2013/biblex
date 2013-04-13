import fi.helsinki.biblex.exporter.*;
import fi.helsinki.biblex.storage.*;
import fi.helsinki.biblex.domain.*;

scenario "exporttaus luo filen", {
    
    given "tuore storage exportterille", {
        file = new File("database.dat")
        file.delete()
        storage = new SQLiteStorage("database.dat")
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
    }
}

scenario "Pystyy tallentaa artikkelin", {

    given "db on päällä ja tyhjä"

    when "lisätään article-entry", {
        e = new BibTexEntry("name1", "article")
        e.put("author", "Thor")
        e.put("title", "Titteli")
        e.put("journal", "Journal")
        e.put("year", "2010")
        storage.add(e)
    }

    then "storagesta löytyy", {
        storage.get("name1").get("author").contains("Thor").shouldBe true
    }

    and "poista db:n filu", {
        file = new File("database.dat")
        file.delete()
    }
}