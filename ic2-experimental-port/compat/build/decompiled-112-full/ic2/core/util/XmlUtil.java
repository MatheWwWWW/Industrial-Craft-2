/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public final class XmlUtil {
    public static String getAttr(Attributes attributes, String name) throws SAXException {
        String val = attributes.getValue(name);
        if (val == null) {
            throw new SAXException("missing attribute: " + name);
        }
        return val;
    }

    public static String getAttr(Attributes attributes, String name, String defValue) {
        String val = attributes.getValue(name);
        if (val == null) {
            return defValue;
        }
        return val;
    }

    public static boolean getBoolAttr(Attributes attributes, String name) throws SAXException {
        String val = attributes.getValue(name);
        if (val == null) {
            throw new SAXException("missing attribute: " + name);
        }
        return XmlUtil.parseBool(val);
    }

    public static boolean getBoolAttr(Attributes attributes, String name, boolean defValue) throws SAXException {
        String val = attributes.getValue(name);
        if (val == null) {
            return defValue;
        }
        return XmlUtil.parseBool(val);
    }

    public static boolean parseBool(String str) throws SAXException {
        if (str.equals("true")) {
            return true;
        }
        if (str.equals("false")) {
            return false;
        }
        throw new SAXException("invalid bool value: " + str);
    }

    public static int getIntAttr(Attributes attributes, String name) throws SAXException {
        String val = attributes.getValue(name);
        if (val == null) {
            throw new SAXException("missing attribute: " + name);
        }
        return XmlUtil.parseInt(val);
    }

    public static int getIntAttr(Attributes attributes, String name, int defValue) {
        String val = attributes.getValue(name);
        if (val == null) {
            return defValue;
        }
        return XmlUtil.parseInt(val);
    }

    public static int getIntAttr(Attributes attributes, String nameA, String nameB, int defValue) {
        String val = attributes.getValue(nameA);
        if (val == null && (val = attributes.getValue(nameB)) == null) {
            return defValue;
        }
        return XmlUtil.parseInt(val);
    }

    public static int parseInt(String str) {
        if (str.startsWith("#")) {
            return Integer.parseInt(str.substring(1), 16);
        }
        if (str.startsWith("0x")) {
            return Integer.parseInt(str.substring(2), 16);
        }
        return Integer.parseInt(str);
    }
}

