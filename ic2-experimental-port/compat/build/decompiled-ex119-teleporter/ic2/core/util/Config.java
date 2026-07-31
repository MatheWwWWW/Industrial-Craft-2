/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  org.apache.commons.io.IOUtils
 */
package ic2.core.util;

import com.google.common.base.Charsets;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class Config {
    private final Config parent;
    public final String name;
    private String comment;
    private boolean saveWithParent = true;
    private final Map<String, Config> sections = new LinkedHashMap<String, Config>();
    private final Map<String, Value> values = new LinkedHashMap<String, Value>();
    private static final String lineSeparator = System.getProperty("line.separator");

    public Config(String string) {
        this(null, string, "");
    }

    private Config(Config config, String string, String string2) {
        assert (config != this);
        this.parent = config;
        this.name = string;
        this.comment = string2;
    }

    public Config getRoot() {
        Config config = this;
        while (config.parent != null) {
            config = config.parent;
        }
        return config;
    }

    public Config getSub(String string) {
        List<String> list = Config.split(string, '/');
        return this.getSub(list, list.size(), false);
    }

    public Config addSub(String string, String string2) {
        assert (Config.split(string, '/').size() == 1);
        Config config = this.sections.get(string);
        if (config == null) {
            config = new Config(this, string, string2);
            this.sections.put(string, config);
        } else {
            config.comment = string2;
        }
        return config;
    }

    public Value get(String string) {
        List<String> list = Config.split(string, '/');
        Config config = this.getSub(list, list.size() - 1, false);
        if (config == null) {
            return null;
        }
        return config.values.get(list.get(list.size() - 1));
    }

    public void set(String string, Value value) {
        List<String> list = Config.split(string, '/');
        assert (list.get(list.size() - 1).equals(value.name));
        Config config = this.getSub(list, list.size() - 1, true);
        config.values.put(list.get(list.size() - 1), value);
    }

    public <T> void set(String string, T t) {
        List<String> list = Config.split(string, '/');
        Config config = this.getSub(list, list.size() - 1, true);
        String string2 = list.get(list.size() - 1);
        Value value = config.values.get(string2);
        if (value == null) {
            value = new Value(string2, "", null);
            config.values.put(string2, value);
        }
        value.set(t);
    }

    public Value remove(String string) {
        List<String> list = Config.split(string, '/');
        Config config = this.getSub(list, list.size() - 1, true);
        String string2 = list.get(list.size() - 1);
        return config.values.remove(string2);
    }

    public void clear() {
        this.sections.clear();
        this.values.clear();
    }

    public void sort() {
        ArrayList<Map.Entry<String, Value>> arrayList = new ArrayList<Map.Entry<String, Value>>(this.values.entrySet());
        Collections.sort(arrayList, new Comparator<Map.Entry<String, Value>>(){

            @Override
            public int compare(Map.Entry<String, Value> entry, Map.Entry<String, Value> entry2) {
                return entry.getKey().compareTo(entry2.getKey());
            }
        });
        this.values.clear();
        for (Map.Entry entry : arrayList) {
            this.values.put((String)entry.getKey(), (Value)entry.getValue());
        }
    }

    public Iterator<Config> sectionIterator() {
        return this.sections.values().iterator();
    }

    public boolean hasChildSection() {
        return !this.sections.isEmpty();
    }

    public int getNumberOfSections() {
        return this.sections.size();
    }

    public Iterator<Value> valueIterator() {
        return this.values.values().iterator();
    }

    public boolean isEmptySection() {
        return this.values.isEmpty();
    }

    public int getNumberOfConfigs() {
        return this.values.size();
    }

    public void setSaveWithParent(boolean bl) {
        this.saveWithParent = bl;
    }

    public void load(InputStream inputStream) throws IOException, ParseException {
        Config config;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
        LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
        Config config2 = config = this;
        StringBuilder stringBuilder = new StringBuilder();
        String string = "";
        try {
            while ((string = lineNumberReader.readLine()) != null) {
                Object object;
                Object object2;
                List<String> list;
                if ((string = Config.trim(string)).isEmpty()) continue;
                if (string.startsWith(";")) {
                    if (string.equals(";---")) {
                        stringBuilder = new StringBuilder();
                        continue;
                    }
                    string = string.substring(1).trim();
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append(lineSeparator);
                    }
                    stringBuilder.append(string);
                    continue;
                }
                if (string.startsWith("[")) {
                    if (!string.endsWith("]")) {
                        throw new ParseException("section without closing bracket", lineNumberReader.getLineNumber(), string);
                    }
                    list = string.substring(1, string.length() - 1);
                    object2 = Config.split((String)((Object)list), '/');
                    object = object2.listIterator();
                    while (object.hasNext()) {
                        object.set(Config.unescapeSection((String)object.next()));
                    }
                    if (stringBuilder.length() > 0) {
                        config2 = config.getSub((List<String>)object2, object2.size() - 1, true);
                        config2 = config2.addSub((String)object2.get(object2.size() - 1), stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        continue;
                    }
                    config2 = config.getSub((List<String>)object2, object2.size(), true);
                    continue;
                }
                list = Config.split(string, '=');
                if (list.size() != 2) {
                    throw new ParseException("invalid key-value pair", lineNumberReader.getLineNumber(), string);
                }
                object2 = Config.unescapeValue(list.get(0).trim());
                if (((String)object2).isEmpty()) {
                    throw new ParseException("empty key", lineNumberReader.getLineNumber(), string);
                }
                object = ((String)list.get(1)).trim();
                while (((String)object).replaceAll("\\\\.", "xx").endsWith("\\")) {
                    object = ((String)object).substring(0, ((String)object).length() - 1) + " ";
                    object = (String)object + lineNumberReader.readLine().trim();
                }
                object = Config.unescapeValue((String)object);
                config2.set((String)object2, new Value((String)object2, stringBuilder.toString(), (String)object, lineNumberReader.getLineNumber()));
                if (stringBuilder.length() <= 0) continue;
                stringBuilder = new StringBuilder();
            }
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (Exception exception) {
            throw new ParseException("general parse error", lineNumberReader.getLineNumber(), string, exception);
        }
        finally {
            IOUtils.closeQuietly((Reader)lineNumberReader);
            IOUtils.closeQuietly((Reader)inputStreamReader);
        }
    }

    public void load(File file) throws ParseException, IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            this.load(fileInputStream);
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly(fileInputStream);
            throw throwable;
        }
        IOUtils.closeQuietly((InputStream)fileInputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void save(OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        try {
            Config config;
            bufferedWriter.write("; ");
            bufferedWriter.write(this.name);
            bufferedWriter.newLine();
            bufferedWriter.write("; created ");
            bufferedWriter.write(DateFormat.getDateTimeInstance().format(new Date()));
            bufferedWriter.newLine();
            bufferedWriter.write(";---");
            bufferedWriter.newLine();
            Config config2 = this;
            ArrayDeque<Config> arrayDeque = new ArrayDeque<Config>();
            arrayDeque.add(this);
            while ((config = (Config)arrayDeque.poll()) != null) {
                Object object2;
                if (!config.values.isEmpty() || !config.comment.isEmpty() || config.sections.isEmpty()) {
                    int n;
                    bufferedWriter.newLine();
                    if (config != config2) {
                        void var8_12;
                        Config config3;
                        int n2;
                        if (!config.comment.isEmpty()) {
                            object2 = config.comment.split("\\n");
                            Object object = object2;
                            n2 = ((String[])object).length;
                            for (n = 0; n < n2; ++n) {
                                String string = object[n];
                                bufferedWriter.write("; ");
                                bufferedWriter.write(string);
                                bufferedWriter.newLine();
                            }
                        }
                        bufferedWriter.write(91);
                        object2 = new ArrayList();
                        Config config4 = config;
                        do {
                            object2.add(var8_12.name);
                        } while ((config3 = var8_12.parent) != config2);
                        for (n2 = object2.size() - 1; n2 >= 0; --n2) {
                            bufferedWriter.write(Config.escapeSection((String)object2.get(n2)));
                            if (n2 <= 0) continue;
                            bufferedWriter.write(" / ");
                        }
                        bufferedWriter.write(93);
                        bufferedWriter.newLine();
                    }
                    for (Value value : config.values.values()) {
                        if (!value.comment.isEmpty()) {
                            String[] stringArray = value.comment.split("\\n");
                            n = stringArray.length;
                            for (int i = 0; i < n; ++i) {
                                String string = stringArray[i];
                                bufferedWriter.write("; ");
                                bufferedWriter.write(string);
                                bufferedWriter.newLine();
                            }
                        }
                        bufferedWriter.write(Config.escapeValue(value.name));
                        bufferedWriter.write(" = ");
                        bufferedWriter.write(Config.escapeValue(value.getString()));
                        bufferedWriter.newLine();
                    }
                }
                object2 = new ArrayList(config.sections.size());
                for (Config config5 : config.sections.values()) {
                    if (!config5.saveWithParent) continue;
                    ((ArrayList)object2).add(config5);
                }
                ListIterator object4 = ((ArrayList)object2).listIterator(((ArrayList)object2).size());
                while (object4.hasPrevious()) {
                    arrayDeque.addFirst((Config)object4.previous());
                }
            }
            bufferedWriter.newLine();
        }
        finally {
            IOUtils.closeQuietly((Writer)bufferedWriter);
            IOUtils.closeQuietly((Writer)outputStreamWriter);
        }
    }

    public void save(File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            this.save(fileOutputStream);
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly(fileOutputStream);
            throw throwable;
        }
        IOUtils.closeQuietly((OutputStream)fileOutputStream);
    }

    private Config getSub(List<String> list, int n, boolean bl) {
        Config config = this;
        for (int i = 0; i < n; ++i) {
            String string = list.get(i);
            assert (string.length() > 0);
            Config config2 = config.sections.get(string);
            if (config2 == null) {
                if (bl) {
                    config2 = new Config(config, string, "");
                    config.sections.put(string, config2);
                } else {
                    return null;
                }
            }
            config = config2;
        }
        return config;
    }

    private static List<String> split(String string, char c) {
        ArrayList<String> arrayList = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        boolean bl2 = false;
        boolean bl3 = false;
        for (int i = 0; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (bl2) {
                stringBuilder.append(c2);
                bl = false;
                bl2 = false;
                continue;
            }
            if (c2 == '\\') {
                stringBuilder.append(c2);
                bl = false;
                bl2 = true;
                continue;
            }
            if (c2 == '\"') {
                stringBuilder.append(c2);
                bl = false;
                bl3 = !bl3;
                continue;
            }
            if (!bl3 && c2 == c) {
                arrayList.add(stringBuilder.toString().trim());
                stringBuilder = new StringBuilder();
                bl = true;
                continue;
            }
            if (Character.isWhitespace(c2) && bl) continue;
            stringBuilder.append(c2);
            bl = false;
        }
        arrayList.add(stringBuilder.toString().trim());
        return arrayList;
    }

    private static String escapeSection(String string) {
        return string.replaceAll("([\\[\\];/])", "\\\\$1").replace("\n", "\\n");
    }

    private static String unescapeSection(String string) {
        return string.replaceAll("\\\\([\\[\\];/])", "$1").replace("\\n", "\n");
    }

    private static String escapeValue(String string) {
        return string.replaceAll("([\\[\\];=\\\\])", "\\\\$1").replace("\n", "\\\n");
    }

    private static String unescapeValue(String string) {
        return string.replaceAll("\\\\([\\[\\];=])", "$1");
    }

    private static String trim(String string) {
        int n;
        char c;
        int n2;
        int n3 = string.length();
        for (n2 = 0; n2 < n3 && ((c = string.charAt(n2)) <= ' ' || c == '\ufeff'); ++n2) {
        }
        for (n = n3 - 1; n >= n2 && ((c = string.charAt(n)) <= ' ' || c == '\ufeff'); --n) {
        }
        if (n2 > 0 || n < n3 - 1) {
            return string.substring(n2, n + 1);
        }
        return string;
    }

    public static class Value {
        public final String name;
        public String comment;
        public String value;
        private final int line;
        private Number numberCache;

        public Value(String string, String string2, String string3) {
            this(string, string2, string3, -1);
        }

        private Value(String string, String string2, String string3, int n) {
            this.name = string;
            this.comment = string2;
            this.value = string3;
            this.line = n;
        }

        public String getString() {
            return this.value;
        }

        public boolean getBool() {
            return Boolean.valueOf(this.value);
        }

        public int getInt() {
            try {
                return this.getNumber().intValue();
            }
            catch (java.text.ParseException parseException) {
                throw new ParseException("invalid value", this, parseException);
            }
        }

        public float getFloat() {
            try {
                return this.getNumber().floatValue();
            }
            catch (java.text.ParseException parseException) {
                throw new ParseException("invalid value", this, parseException);
            }
        }

        public double getDouble() {
            try {
                return this.getNumber().doubleValue();
            }
            catch (java.text.ParseException parseException) {
                throw new ParseException("invalid value", this, parseException);
            }
        }

        public <T> void set(T t) {
            this.value = String.valueOf(t);
            this.numberCache = null;
        }

        public int getLine() {
            return this.line;
        }

        private Number getNumber() throws java.text.ParseException {
            if (this.numberCache == null) {
                this.numberCache = NumberFormat.getInstance(Locale.US).parse(this.value);
            }
            return this.numberCache;
        }
    }

    public static class ParseException
    extends RuntimeException {
        private static final long serialVersionUID = 8721912755972301225L;

        public ParseException(String string, int n, String string2) {
            super(ParseException.formatMsg(string, n, string2));
        }

        public ParseException(String string, int n, String string2, Exception exception) {
            super(ParseException.formatMsg(string, n, string2), exception);
        }

        public ParseException(String string, Value value) {
            super(ParseException.formatMsg(string, value));
        }

        public ParseException(String string, Value value, Exception exception) {
            super(ParseException.formatMsg(string, value), exception);
        }

        private static String formatMsg(String string, int n, String object) {
            if (!ParseException.isPrintable((String)object)) {
                object = (String)object + "|" + ParseException.toPrintable((String)object);
            }
            if (n >= 0) {
                return string + " at line " + n + " (" + (String)object + ").";
            }
            return string + " at an unknown line (" + (String)object + ").";
        }

        private static String formatMsg(String string, Value value) {
            return ParseException.formatMsg(string, value.getLine(), value.name + " = " + value.getString());
        }

        private static boolean isPrintable(String string) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c >= ' ' && c <= '~') continue;
                return false;
            }
            return true;
        }

        private static String toPrintable(String string) {
            int n = string.length();
            Object object = "";
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c < ' ' || c > '~') {
                    if (i > 0) {
                        object = (String)object + ",";
                    }
                    object = (String)object + String.format("0x%x", c);
                    if (i >= n - 1) continue;
                    object = (String)object + ",";
                    continue;
                }
                object = (String)object + c;
            }
            return object;
        }
    }
}

