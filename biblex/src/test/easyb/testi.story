import fi.helsinki.biblex.exporter.*;
import fi.helsinki.biblex.storage.*;


scenario "exporttaus luo filen", {
    
    given "storage exportterille", {
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