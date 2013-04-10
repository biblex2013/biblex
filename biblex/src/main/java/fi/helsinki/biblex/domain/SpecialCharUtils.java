package fi.helsinki.biblex.domain;

import org.apache.commons.collections.bidimap.DualHashBidiMap;

/**
 * This class provides a mechanism to convert between special characters and
 * LaTeX commands rendering them.
 */
public class SpecialCharUtils {

    /**
     * A bijective map from human-readable chars to LaTeX.
     */
    private static DualHashBidiMap map;

    static {
        map = new DualHashBidiMap();
        map.put('ä', "\\\"{a}");
        map.put('à', "\\`{a}");
        map.put('á', "\\´{a}");
        map.put('å', "\\aa");
        map.put('Ä', "\\\"{A}");
        map.put('À', "\\`{A}");
        map.put('Á', "\\´{A}");
        map.put('Å', "\\AA");

        map.put('ö', "\\\"{o}");
        map.put('ò', "\\`{o}");
        map.put('ó', "\\´{o}");
        map.put('Ö', "\\\"{O}");
        map.put('Ò', "\\`{O}");
        map.put('Ó', "\\´{O}");
    }

    public static String translateHumanCharToLATEX(char c) {
        return (String) map.get(c);
    }

    public static Character translateLATEXToHumanChar(String s) {
        return (Character) map.getKey(s);
    }

    public static String bibtexify(String humanReadableString) {
        String s = humanReadableString; // shortcut.
        StringBuilder sb = new StringBuilder(2 * s.length());

        for (char c : s.toCharArray()) {
            String tmp = translateHumanCharToLATEX(c);
            sb.append(tmp != null ? tmp : c);
        }

        return sb.toString();
    }
}
