/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FilenameUtils
 */
package net.minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import org.apache.commons.io.FilenameUtils;

public class FileUtil {
    private static final Pattern f_133725_ = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
    private static final int f_179920_ = 255;
    private static final Pattern f_133726_ = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);

    public static String m_133730_(Path p_133731_, String p_133732_, String p_133733_) throws IOException {
        for (char $$3 : SharedConstants.f_136184_) {
            p_133732_ = ((String)p_133732_).replace($$3, '_');
        }
        if (f_133726_.matcher((CharSequence)(p_133732_ = ((String)p_133732_).replaceAll("[./\"]", "_"))).matches()) {
            p_133732_ = "_" + (String)p_133732_ + "_";
        }
        Matcher $$4 = f_133725_.matcher((CharSequence)p_133732_);
        int $$5 = 0;
        if ($$4.matches()) {
            p_133732_ = $$4.group("name");
            $$5 = Integer.parseInt($$4.group("count"));
        }
        if (((String)p_133732_).length() > 255 - p_133733_.length()) {
            p_133732_ = ((String)p_133732_).substring(0, 255 - p_133733_.length());
        }
        while (true) {
            Object $$6 = p_133732_;
            if ($$5 != 0) {
                String $$7 = " (" + $$5 + ")";
                int $$8 = 255 - $$7.length();
                if (((String)$$6).length() > $$8) {
                    $$6 = ((String)$$6).substring(0, $$8);
                }
                $$6 = (String)$$6 + $$7;
            }
            $$6 = (String)$$6 + p_133733_;
            Path $$9 = p_133731_.resolve((String)$$6);
            try {
                Path $$10 = Files.createDirectory($$9, new FileAttribute[0]);
                Files.deleteIfExists($$10);
                return p_133731_.relativize($$10).toString();
            }
            catch (FileAlreadyExistsException $$11) {
                ++$$5;
                continue;
            }
            break;
        }
    }

    public static boolean m_133728_(Path p_133729_) {
        Path $$1 = p_133729_.normalize();
        return $$1.equals(p_133729_);
    }

    public static boolean m_133734_(Path p_133735_) {
        for (Path $$1 : p_133735_) {
            if (!f_133726_.matcher($$1.toString()).matches()) continue;
            return false;
        }
        return true;
    }

    public static Path m_133736_(Path p_133737_, String p_133738_, String p_133739_) {
        String $$3 = p_133738_ + p_133739_;
        Path $$4 = Paths.get($$3, new String[0]);
        if ($$4.endsWith(p_133739_)) {
            throw new InvalidPathException($$3, "empty resource name");
        }
        return p_133737_.resolve($$4);
    }

    public static String m_179922_(String p_179923_) {
        return FilenameUtils.getFullPath((String)p_179923_).replace(File.separator, "/");
    }

    public static String m_179924_(String p_179925_) {
        return FilenameUtils.normalize((String)p_179925_).replace(File.separator, "/");
    }
}

