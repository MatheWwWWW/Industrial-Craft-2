/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public final class XmlUtil {
    public static String getAttr(Attributes attributes, String string) throws SAXException {
        String string2 = attributes.getValue(string);
        if (string2 == null) {
            throw new SAXException("missing attribute: " + string);
        }
        return string2;
    }

    public static String getAttr(Attributes attributes, String string, String string2) {
        String string3 = attributes.getValue(string);
        if (string3 == null) {
            return string2;
        }
        return string3;
    }

    public static boolean getBoolAttr(Attributes attributes, String string) throws SAXException {
        String string2 = attributes.getValue(string);
        if (string2 == null) {
            throw new SAXException("missing attribute: " + string);
        }
        return XmlUtil.parseBool(string2);
    }

    public static boolean getBoolAttr(Attributes attributes, String string, boolean bl) throws SAXException {
        String string2 = attributes.getValue(string);
        if (string2 == null) {
            return bl;
        }
        return XmlUtil.parseBool(string2);
    }

    public static boolean parseBool(String string) throws SAXException {
        if (string.equals("true")) {
            return true;
        }
        if (string.equals("false")) {
            return false;
        }
        throw new SAXException("invalid bool value: " + string);
    }

    public static int getIntAttr(Attributes attributes, String string) throws SAXException {
        String string2 = attributes.getValue(string);
        if (string2 == null) {
            throw new SAXException("missing attribute: " + string);
        }
        return XmlUtil.parseInt(string2);
    }

    public static int getIntAttr(Attributes attributes, String string, int n) {
        String string2 = attributes.getValue(string);
        if (string2 == null) {
            return n;
        }
        return XmlUtil.parseInt(string2);
    }

    public static int getIntAttr(Attributes attributes, String string, String string2, int n) {
        String string3 = attributes.getValue(string);
        if (string3 == null && (string3 = attributes.getValue(string2)) == null) {
            return n;
        }
        return XmlUtil.parseInt(string3);
    }

    public static int parseInt(String string) {
        if (string.startsWith("#")) {
            return Integer.parseInt(string.substring(1), 16);
        }
        if (string.startsWith("0x")) {
            return Integer.parseInt(string.substring(2), 16);
        }
        return Integer.parseInt(string);
    }
}

