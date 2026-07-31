/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.data.structures;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.slf4j.Logger;

public class NbtToSnbt
implements DataProvider {
    private static final Logger f_126421_ = LogUtils.getLogger();
    private final DataGenerator f_126422_;

    public NbtToSnbt(DataGenerator p_126425_) {
        this.f_126422_ = p_126425_;
    }

    @Override
    public void m_213708_(CachedOutput p_236376_) throws IOException {
        Path $$1 = this.f_126422_.m_123916_();
        for (Path $$2 : this.f_126422_.m_123913_()) {
            Files.walk($$2, new FileVisitOption[0]).filter(p_126430_ -> p_126430_.toString().endsWith(".nbt")).forEach(p_236390_ -> NbtToSnbt.m_236381_(p_236376_, p_236390_, this.m_126435_($$2, (Path)p_236390_), $$1));
        }
    }

    @Override
    public String m_6055_() {
        return "NBT to SNBT";
    }

    private String m_126435_(Path p_126436_, Path p_126437_) {
        String $$2 = p_126436_.relativize(p_126437_).toString().replaceAll("\\\\", "/");
        return $$2.substring(0, $$2.length() - ".nbt".length());
    }

    @Nullable
    public static Path m_236381_(CachedOutput p_236382_, Path p_236383_, String p_236384_, Path p_236385_) {
        Path path;
        block8: {
            InputStream $$4 = Files.newInputStream(p_236383_, new OpenOption[0]);
            try {
                Path $$5 = p_236385_.resolve(p_236384_ + ".snbt");
                NbtToSnbt.m_236377_(p_236382_, $$5, NbtUtils.m_178063_(NbtIo.m_128939_($$4)));
                f_126421_.info("Converted {} from NBT to SNBT", (Object)p_236384_);
                path = $$5;
                if ($$4 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$4 != null) {
                        try {
                            $$4.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException $$6) {
                    f_126421_.error("Couldn't convert {} from NBT to SNBT at {}", new Object[]{p_236384_, p_236383_, $$6});
                    return null;
                }
            }
            $$4.close();
        }
        return path;
    }

    public static void m_236377_(CachedOutput p_236378_, Path p_236379_, String p_236380_) throws IOException {
        ByteArrayOutputStream $$3 = new ByteArrayOutputStream();
        HashingOutputStream $$4 = new HashingOutputStream(Hashing.sha1(), (OutputStream)$$3);
        $$4.write(p_236380_.getBytes(StandardCharsets.UTF_8));
        $$4.write(10);
        p_236378_.m_213871_(p_236379_, $$3.toByteArray(), $$4.hash());
    }
}

