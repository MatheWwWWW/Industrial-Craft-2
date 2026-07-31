/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.repository;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

public class FolderRepositorySource
implements RepositorySource {
    private static final FileFilter f_10381_ = p_10398_ -> {
        boolean $$1 = p_10398_.isFile() && p_10398_.getName().endsWith(".zip");
        boolean $$2 = p_10398_.isDirectory() && new File(p_10398_, "pack.mcmeta").isFile();
        return $$1 || $$2;
    };
    private final File f_10382_;
    private final PackSource f_10383_;

    public FolderRepositorySource(File p_10386_, PackSource p_10387_) {
        this.f_10382_ = p_10386_;
        this.f_10383_ = p_10387_;
    }

    @Override
    public void m_7686_(Consumer<Pack> p_10391_, Pack.PackConstructor p_10392_) {
        File[] $$2;
        if (!this.f_10382_.isDirectory()) {
            this.f_10382_.mkdirs();
        }
        if (($$2 = this.f_10382_.listFiles(f_10381_)) == null) {
            return;
        }
        for (File $$3 : $$2) {
            String $$4 = "file/" + $$3.getName();
            Pack $$5 = Pack.m_10430_($$4, false, this.m_10388_($$3), p_10392_, Pack.Position.TOP, this.f_10383_);
            if ($$5 == null) continue;
            p_10391_.accept($$5);
        }
    }

    private Supplier<PackResources> m_10388_(File p_10389_) {
        if (p_10389_.isDirectory()) {
            return () -> new FolderPackResources(p_10389_);
        }
        return () -> new FilePackResources(p_10389_);
    }
}

