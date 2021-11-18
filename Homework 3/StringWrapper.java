/**
 * Test class mainly used to have a compareTo for String using string lengths
 */
public class StringWrapper implements Comparable<StringWrapper> {
    String str;

    public StringWrapper(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public int compareTo(StringWrapper o) {
        return ((Integer) str.length()).compareTo(o.getStr().length());
    }
}
