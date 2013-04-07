package fi.helsinki.biblex.domain;

/**
 * This class enumerates all BibTex-reference types supported by biblex.
 */
public enum BibTexStyle {
    ARTICLE("article"),
    BOOK("book"),
    INPROCEEDINGS("inproceedings");

    private String name;

    private BibTexStyle(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
