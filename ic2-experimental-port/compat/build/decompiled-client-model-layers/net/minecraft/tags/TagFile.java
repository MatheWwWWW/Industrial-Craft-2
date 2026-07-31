/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.tags;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.tags.TagEntry;

public record TagFile(List<TagEntry> f_215959_, boolean f_215960_) {
    public static final Codec<TagFile> f_215958_ = RecordCodecBuilder.create(p_215967_ -> p_215967_.group((App)TagEntry.f_215911_.listOf().fieldOf("values").forGetter(TagFile::f_215959_), (App)Codec.BOOL.optionalFieldOf("replace", (Object)false).forGetter(TagFile::f_215960_)).apply((Applicative)p_215967_, TagFile::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{TagFile.class, "entries;replace", "f_215959_", "f_215960_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TagFile.class, "entries;replace", "f_215959_", "f_215960_"}, this);
    }

    @Override
    public final boolean equals(Object p_215970_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TagFile.class, "entries;replace", "f_215959_", "f_215960_"}, this, p_215970_);
    }
}

