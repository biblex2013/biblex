package fi.helsinki.biblex.storage;

import fi.helsinki.biblex.domain.BibTexEntry;

import java.util.Iterator;
import java.util.Map;
import java.io.*;
import java.sql.*;

/**
 * This class implements persistent storage using SQLite
 */

public class SQLiteStorage extends Storage {
    private Connection conn = null;

    public SQLiteStorage(String filename) throws Exception {
				/* Load the sqlite-JDBC driver using the current class loader */
        Class.forName("org.sqlite.JDBC");

        this.open(filename);

				/* Initalize the opened DB if uninitialized */
        if (!this.checkDB())
            this.initialize();
    }

    public Iterator<BibTexEntry> iterator() {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT id,name,style FROM Entries;");
            rs.next();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return new SQLiteStorageIterator(rs);
    }

    public void open(String filename) throws Exception {
        if (filename == null)
            throw new IllegalArgumentException("Filename may not be null");

        conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        conn.setAutoCommit(false);
    }

    public void create(String filename) throws Exception {
        if (conn != null)
            this.close();

        this.open(filename);
        if (this.checkDB())
            ; // EEXISTS

        this.initialize();
    }

    public void close() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

		/**
		 * Add a new entry to the DB
		 */
    public long add(BibTexEntry entry) throws Exception {
        if (entry == null)
            throw new IllegalArgumentException("Entry may not be null.");
        if (conn == null)
            throw new Exception("DB Connection not open.");

        PreparedStatement st = conn.prepareStatement(
            "INSERT INTO Entries (name, style) VALUES (?, ?)");
        st.setString(1, entry.getName());
        st.setString(2, entry.getStyle().toString());

        st.execute();
        long eid = st.getGeneratedKeys().getLong(1);

        st = conn.prepareStatement(
            "INSERT INTO Fields (entry, name, value) VALUES (?, ?, ?)");
        st.setLong(1, eid);

        for (Map.Entry<String, String> e : entry) {
            st.setString(2, e.getKey());
            st.setString(3, e.getValue());
            st.execute();
        }

        conn.commit();
        return eid;
    }

		/**
		 * Get entry by ID
		 */
    public BibTexEntry get(long eid) {
        try {
            PreparedStatement st = conn.prepareStatement(
                "SELECT id,name,style FROM Entries WHERE id = ?");
            st.setLong(1, eid);
            return entryFromResultSet(st.executeQuery());
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

		/**
		 * Get entry by name
		 */
    public BibTexEntry get(String name) {
        try {
            PreparedStatement st = conn.prepareStatement(
                "SELECT id,name,style FROM Entries WHERE name = ?");
            st.setString(1, name);
            return entryFromResultSet(st.executeQuery());
        } catch (Exception e) {
            System.err.println(e);
        }

        return null;
    }

    public boolean delete(long eid) throws Exception {
        return false;
    }

    public boolean update(long eid, BibTexEntry entry) throws Exception {
        return false;
    }

    public Iterable<BibTexEntry> search(String key, String value) {
        return null;
    }

    public Iterable<BibTexEntry> search(Iterable<Map.Entry<String, String>> constraints) {
        return null;
    }


		/**
		 * Check whether the DB is initialized or not
		 */
    private boolean checkDB() {
        try {
            Statement st = conn.createStatement();
            st.execute("SELECT * FROM Entries;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private BibTexEntry entryFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String style = rs.getString("style");

        BibTexEntry entry = new BibTexEntry(name, style);
        readFieldsFromDB(id, entry);

        return entry;
    }

    private void initialize() throws Exception {
        Statement st = conn.createStatement();
        st.execute(
            "CREATE TABLE Entries(" +
            "    id INTEGER PRIMARY KEY," +
            "    name TEXT NOT NULL UNIQUE," +
            "    style TEXT NOT NULL" +
            ");");
        st.execute(
            "CREATE TABLE Fields(" +
            "    entry INTEGER NOT NULL," +
            "    name TEXT NOT NULL," +
            "    value TEXT NOT NULL," +

            "    PRIMARY KEY (entry, name)," +
            "    FOREIGN KEY (entry) REFERENCES Entries(id)" +
            ");");

        conn.commit();
    }

    private void readFieldsFromDB(long eid, BibTexEntry entry) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
            "SELECT name,value FROM Fields WHERE entry = ?");
        st.setLong(1, eid);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String field = rs.getString("name");
            String value = rs.getString("value");
            entry.put(field, value);
        }
    }


    public class SQLiteStorageIterator implements Iterator<BibTexEntry> {
        private boolean nextp = true;
        private ResultSet rs;

        public SQLiteStorageIterator(ResultSet rs) {
            if (rs == null)
                nextp = false;
            this.rs = rs;
        }

        public boolean hasNext() {
            return nextp;
        }

        public BibTexEntry next() {
            if (!nextp || rs == null)
                return null;

            try {
                BibTexEntry entry = entryFromResultSet(rs);
                if (!rs.next())
                    nextp = false;
                return entry;
            } catch (SQLException e) {
                nextp = false;
            }

            return null;
        }

        public void remove() {}
    }
}