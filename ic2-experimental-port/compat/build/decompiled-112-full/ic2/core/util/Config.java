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

    public Config(String name) {
        this(null, name, "");
    }

    private Config(Config parent, String name, String comment) {
        assert (parent != this);
        this.parent = parent;
        this.name = name;
        this.comment = comment;
    }

    public Config getRoot() {
        Config ret = this;
        while (ret.parent != null) {
            ret = ret.parent;
        }
        return ret;
    }

    public Config getSub(String key) {
        List<String> parts = Config.split(key, '/');
        return this.getSub(parts, parts.size(), false);
    }

    public Config addSub(String key, String aComment) {
        assert (Config.split(key, '/').size() == 1);
        Config config = this.sections.get(key);
        if (config == null) {
            config = new Config(this, key, aComment);
            this.sections.put(key, config);
        } else {
            config.comment = aComment;
        }
        return config;
    }

    public Value get(String key) {
        List<String> parts = Config.split(key, '/');
        Config config = this.getSub(parts, parts.size() - 1, false);
        if (config == null) {
            return null;
        }
        return config.values.get(parts.get(parts.size() - 1));
    }

    public void set(String key, Value value) {
        List<String> parts = Config.split(key, '/');
        assert (parts.get(parts.size() - 1).equals(value.name));
        Config config = this.getSub(parts, parts.size() - 1, true);
        config.values.put(parts.get(parts.size() - 1), value);
    }

    public <T> void set(String key, T value) {
        List<String> parts = Config.split(key, '/');
        Config config = this.getSub(parts, parts.size() - 1, true);
        String tName = parts.get(parts.size() - 1);
        Value existingValue = config.values.get(tName);
        if (existingValue == null) {
            existingValue = new Value(tName, "", null);
            config.values.put(tName, existingValue);
        }
        existingValue.set(value);
    }

    public Value remove(String key) {
        List<String> parts = Config.split(key, '/');
        Config config = this.getSub(parts, parts.size() - 1, true);
        String tName = parts.get(parts.size() - 1);
        return config.values.remove(tName);
    }

    public void clear() {
        this.sections.clear();
        this.values.clear();
    }

    public void sort() {
        ArrayList<Map.Entry<String, Value>> valueList = new ArrayList<Map.Entry<String, Value>>(this.values.entrySet());
        Collections.sort(valueList, new Comparator<Map.Entry<String, Value>>(){

            @Override
            public int compare(Map.Entry<String, Value> a, Map.Entry<String, Value> b) {
                return a.getKey().compareTo(b.getKey());
            }
        });
        this.values.clear();
        for (Map.Entry entry : valueList) {
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

    public void setSaveWithParent(boolean saveWithParent) {
        this.saveWithParent = saveWithParent;
    }

    public void load(InputStream is) throws IOException, ParseException {
        Config root;
        InputStreamReader isReader = new InputStreamReader(is, Charsets.UTF_8);
        LineNumberReader reader = new LineNumberReader(isReader);
        Config config = root = this;
        StringBuilder tComment = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                if ((line = Config.trim(line)).isEmpty()) continue;
                if (line.startsWith(";")) {
                    if (line.equals(";---")) {
                        tComment = new StringBuilder();
                        continue;
                    }
                    line = line.substring(1).trim();
                    if (tComment.length() != 0) {
                        tComment.append(lineSeparator);
                    }
                    tComment.append(line);
                    continue;
                }
                if (line.startsWith("[")) {
                    if (!line.endsWith("]")) {
                        throw new ParseException("section without closing bracket", reader.getLineNumber(), line);
                    }
                    String section = line.substring(1, line.length() - 1);
                    List<String> keys = Config.split(section, '/');
                    ListIterator<String> it = keys.listIterator();
                    while (it.hasNext()) {
                        it.set(Config.unescapeSection(it.next()));
                    }
                    if (tComment.length() > 0) {
                        config = root.getSub(keys, keys.size() - 1, true);
                        config = config.addSub(keys.get(keys.size() - 1), tComment.toString());
                        tComment = new StringBuilder();
                        continue;
                    }
                    config = root.getSub(keys, keys.size(), true);
                    continue;
                }
                List<String> parts = Config.split(line, '=');
                if (parts.size() != 2) {
                    throw new ParseException("invalid key-value pair", reader.getLineNumber(), line);
                }
                String key = Config.unescapeValue(parts.get(0).trim());
                if (key.isEmpty()) {
                    throw new ParseException("empty key", reader.getLineNumber(), line);
                }
                String valueStr = parts.get(1).trim();
                while (valueStr.replaceAll("\\\\.", "xx").endsWith("\\")) {
                    valueStr = valueStr.substring(0, valueStr.length() - 1) + " ";
                    valueStr = valueStr + reader.readLine().trim();
                }
                valueStr = Config.unescapeValue(valueStr);
                config.set(key, new Value(key, tComment.toString(), valueStr, reader.getLineNumber()));
                if (tComment.length() <= 0) continue;
                tComment = new StringBuilder();
            }
        }
        catch (IOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ParseException("general parse error", reader.getLineNumber(), line, e);
        }
        finally {
            IOUtils.closeQuietly((Reader)reader);
            IOUtils.closeQuietly((Reader)isReader);
        }
    }

    public void load(File file) throws ParseException, IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            this.load(is);
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly(is);
            throw throwable;
        }
        IOUtils.closeQuietly((InputStream)is);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void save(OutputStream os) throws IOException {
        OutputStreamWriter osWriter = new OutputStreamWriter(os, Charsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osWriter);
        try {
            Config config;
            writer.write("; ");
            writer.write(this.name);
            writer.newLine();
            writer.write("; created ");
            writer.write(DateFormat.getDateTimeInstance().format(new Date()));
            writer.newLine();
            writer.write(";---");
            writer.newLine();
            Config root = this;
            ArrayDeque<Config> todo = new ArrayDeque<Config>();
            todo.add(this);
            while ((config = (Config)todo.poll()) != null) {
                if (!config.values.isEmpty() || !config.comment.isEmpty() || config.sections.isEmpty()) {
                    writer.newLine();
                    if (config != root) {
                        void var8_12;
                        Config config2;
                        if (!config.comment.isEmpty()) {
                            String[] commentParts;
                            for (String comment : commentParts = config.comment.split("\\n")) {
                                writer.write("; ");
                                writer.write(comment);
                                writer.newLine();
                            }
                        }
                        writer.write(91);
                        ArrayList keys = new ArrayList();
                        Config config3 = config;
                        do {
                            keys.add(var8_12.name);
                        } while ((config2 = var8_12.parent) != root);
                        for (int i = keys.size() - 1; i >= 0; --i) {
                            writer.write(Config.escapeSection((String)keys.get(i)));
                            if (i <= 0) continue;
                            writer.write(" / ");
                        }
                        writer.write(93);
                        writer.newLine();
                    }
                    for (Value value : config.values.values()) {
                        if (!value.comment.isEmpty()) {
                            for (String line : value.comment.split("\\n")) {
                                writer.write("; ");
                                writer.write(line);
                                writer.newLine();
                            }
                        }
                        writer.write(Config.escapeValue(value.name));
                        writer.write(" = ");
                        writer.write(Config.escapeValue(value.getString()));
                        writer.newLine();
                    }
                }
                ArrayList<Config> toAdd = new ArrayList<Config>(config.sections.size());
                for (Config section : config.sections.values()) {
                    if (!section.saveWithParent) continue;
                    toAdd.add(section);
                }
                ListIterator listIterator = toAdd.listIterator(toAdd.size());
                while (listIterator.hasPrevious()) {
                    todo.addFirst((Config)listIterator.previous());
                }
            }
            writer.newLine();
        }
        finally {
            IOUtils.closeQuietly((Writer)writer);
            IOUtils.closeQuietly((Writer)osWriter);
        }
    }

    public void save(File file) throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            this.save(os);
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly(os);
            throw throwable;
        }
        IOUtils.closeQuietly((OutputStream)os);
    }

    private Config getSub(List<String> keys, int end, boolean create) {
        Config ret = this;
        for (int i = 0; i < end; ++i) {
            String key = keys.get(i);
            assert (key.length() > 0);
            Config config = ret.sections.get(key);
            if (config == null) {
                if (create) {
                    config = new Config(ret, key, "");
                    ret.sections.put(key, config);
                } else {
                    return null;
                }
            }
            ret = config;
        }
        return ret;
    }

    private static List<String> split(String str, char splitChar) {
        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean empty = true;
        boolean passNext = false;
        boolean quoted = false;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (passNext) {
                current.append(c);
                empty = false;
                passNext = false;
                continue;
            }
            if (c == '\\') {
                current.append(c);
                empty = false;
                passNext = true;
                continue;
            }
            if (c == '\"') {
                current.append(c);
                empty = false;
                quoted = !quoted;
                continue;
            }
            if (!quoted && c == splitChar) {
                ret.add(current.toString().trim());
                current = new StringBuilder();
                empty = true;
                continue;
            }
            if (Character.isWhitespace(c) && empty) continue;
            current.append(c);
            empty = false;
        }
        ret.add(current.toString().trim());
        return ret;
    }

    private static String escapeSection(String str) {
        return str.replaceAll("([\\[\\];/])", "\\\\$1").replace("\n", "\\n");
    }

    private static String unescapeSection(String str) {
        return str.replaceAll("\\\\([\\[\\];/])", "$1").replace("\\n", "\n");
    }

    private static String escapeValue(String str) {
        return str.replaceAll("([\\[\\];=\\\\])", "\\\\$1").replace("\n", "\\\n");
    }

    private static String unescapeValue(String str) {
        return str.replaceAll("\\\\([\\[\\];=])", "$1");
    }

    private static String trim(String str) {
        int end;
        char c;
        int start;
        int len = str.length();
        for (start = 0; start < len && ((c = str.charAt(start)) <= ' ' || c == '\ufeff'); ++start) {
        }
        for (end = len - 1; end >= start && ((c = str.charAt(end)) <= ' ' || c == '\ufeff'); --end) {
        }
        if (start > 0 || end < len - 1) {
            return str.substring(start, end + 1);
        }
        return str;
    }

    public static class ParseException
    extends RuntimeException {
        private static final long serialVersionUID = 8721912755972301225L;

        public ParseException(String msg, int line, String content) {
            super(ParseException.formatMsg(msg, line, content));
        }

        public ParseException(String msg, int line, String content, Exception e) {
            super(ParseException.formatMsg(msg, line, content), e);
        }

        public ParseException(String msg, Value value) {
            super(ParseException.formatMsg(msg, value));
        }

        public ParseException(String msg, Value value, Exception e) {
            super(ParseException.formatMsg(msg, value), e);
        }

        private static String formatMsg(String msg, int line, String content) {
            if (!ParseException.isPrintable(content)) {
                content = content + "|" + ParseException.toPrintable(content);
            }
            if (line >= 0) {
                return msg + " at line " + line + " (" + content + ").";
            }
            return msg + " at an unknown line (" + content + ").";
        }

        private static String formatMsg(String msg, Value value) {
            return ParseException.formatMsg(msg, value.getLine(), value.name + " = " + value.getString());
        }

        private static boolean isPrintable(String str) {
            int len = str.length();
            for (int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (c >= ' ' && c <= '~') continue;
                return false;
            }
            return true;
        }

        private static String toPrintable(String str) {
            int len = str.length();
            String ret = "";
            for (int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (c < ' ' || c > '~') {
                    if (i > 0) {
                        ret = ret + ',';
                    }
                    ret = ret + String.format("0x%x", c);
                    if (i >= len - 1) continue;
                    ret = ret + ',';
                    continue;
                }
                ret = ret + c;
            }
            return ret;
        }
    }

    public static class Value {
        public final String name;
        public String comment;
        public String value;
        private final int line;
        private Number numberCache;

        public Value(String name, String comment, String value) {
            this(name, comment, value, -1);
        }

        private Value(String name, String comment, String value, int line) {
            this.name = name;
            this.comment = comment;
            this.value = value;
            this.line = line;
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
            catch (java.text.ParseException e) {
                throw new ParseException("invalid value", this, e);
            }
        }

        public float getFloat() {
            try {
                return this.getNumber().floatValue();
            }
            catch (java.text.ParseException e) {
                throw new ParseException("invalid value", this, e);
            }
        }

        public double getDouble() {
            try {
                return this.getNumber().doubleValue();
            }
            catch (java.text.ParseException e) {
                throw new ParseException("invalid value", this, e);
            }
        }

        public <T> void set(T value) {
            this.value = String.valueOf(value);
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
}

