/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Loader
 *  org.apache.commons.lang3.StringUtils
 */
package ic2.core.profile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.StringUtils;

public class ProfileTarget {
    public final File root;
    public final String offset;
    private static final Map<File, Map<String, ZipFile>> ZIP_HOLDER = new HashMap<File, Map<String, ZipFile>>();

    public static ProfileTarget fromJar(String offset) {
        assert ("ic2".equals(Loader.instance().activeModContainer().getModId()));
        return new ProfileTarget(Loader.instance().activeModContainer().getSource(), offset);
    }

    public ProfileTarget(File root, String offset) {
        this.root = root;
        this.offset = offset;
    }

    public boolean isFile() {
        return !this.root.isDirectory();
    }

    public File asFile() {
        return new File(this.root, this.offset);
    }

    public ZipFile asZip() {
        Map offsetMap = ZIP_HOLDER.computeIfAbsent(this.root, k -> new HashMap());
        ZipFile ret = (ZipFile)offsetMap.get(this.offset);
        if (ret == null) {
            try {
                ret = this.makeZip();
                offsetMap.put(this.offset, ret);
            }
            catch (IOException e) {
                throw new RuntimeException("Failed to get zip!", e);
            }
        }
        return ret;
    }

    protected ZipFile makeZip() throws IOException {
        return new ZipFile(this.root){

            @Override
            public ZipEntry getEntry(String name) {
                return super.getEntry(StringUtils.isNotBlank((CharSequence)name) ? ProfileTarget.this.offset + '/' + name : ProfileTarget.this.offset);
            }
        };
    }

    public InputStream asStream() throws IOException {
        if (this.isFile()) {
            ZipFile zip = this.asZip();
            ZipEntry entry = zip.getEntry(null);
            if (entry == null) {
                return null;
            }
            return zip.getInputStream(entry);
        }
        File file = this.asFile();
        if (!file.canRead() || !file.isFile()) {
            return null;
        }
        return new BufferedInputStream(new FileInputStream(file));
    }

    public ProfileTarget offset(String extra) {
        return new ProfileTarget(this.root, this.offset + '/' + extra);
    }

    public String toString() {
        return "ProfileTarget<" + this.root + " -> " + this.offset + '>';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProfileTarget)) {
            return false;
        }
        ProfileTarget other = (ProfileTarget)obj;
        return Objects.equals(this.root, other.root) && Objects.equals(this.offset, other.offset);
    }
}

