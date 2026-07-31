/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class DefaultClientPackResources
extends VanillaPackResources {
    private final AssetIndex f_118606_;

    public DefaultClientPackResources(PackMetadataSection p_174827_, AssetIndex p_174828_) {
        super(p_174827_, "minecraft", "realms");
        this.f_118606_ = p_174828_;
    }

    @Override
    @Nullable
    protected InputStream m_8033_(PackType p_118621_, ResourceLocation p_118622_) {
        File $$2;
        if (p_118621_ == PackType.CLIENT_RESOURCES && ($$2 = this.f_118606_.m_7879_(p_118622_)) != null && $$2.exists()) {
            try {
                return new FileInputStream($$2);
            }
            catch (FileNotFoundException fileNotFoundException) {
                // empty catch block
            }
        }
        return super.m_8033_(p_118621_, p_118622_);
    }

    @Override
    public boolean m_7211_(PackType p_118618_, ResourceLocation p_118619_) {
        File $$2;
        if (p_118618_ == PackType.CLIENT_RESOURCES && ($$2 = this.f_118606_.m_7879_(p_118619_)) != null && $$2.exists()) {
            return true;
        }
        return super.m_7211_(p_118618_, p_118619_);
    }

    @Override
    @Nullable
    protected InputStream m_5539_(String p_118616_) {
        File $$1 = this.f_118606_.m_7974_(p_118616_);
        if ($$1 != null && $$1.exists()) {
            try {
                return new FileInputStream($$1);
            }
            catch (FileNotFoundException fileNotFoundException) {
                // empty catch block
            }
        }
        return super.m_5539_(p_118616_);
    }

    @Override
    public Collection<ResourceLocation> m_214146_(PackType p_235011_, String p_235012_, String p_235013_, Predicate<ResourceLocation> p_235014_) {
        Collection<ResourceLocation> $$4 = super.m_214146_(p_235011_, p_235012_, p_235013_, p_235014_);
        $$4.addAll(this.f_118606_.m_214011_(p_235013_, p_235012_, p_235014_));
        return $$4;
    }
}

