/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.model.geom.builders;

import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;

public final class CubeDefinition {
    @Nullable
    private final String f_171434_;
    private final Vector3f f_171435_;
    private final Vector3f f_171436_;
    private final CubeDeformation f_171437_;
    private final boolean f_171438_;
    private final UVPair f_171439_;
    private final UVPair f_171440_;

    protected CubeDefinition(@Nullable String p_171442_, float p_171443_, float p_171444_, float p_171445_, float p_171446_, float p_171447_, float p_171448_, float p_171449_, float p_171450_, CubeDeformation p_171451_, boolean p_171452_, float p_171453_, float p_171454_) {
        this.f_171434_ = p_171442_;
        this.f_171439_ = new UVPair(p_171443_, p_171444_);
        this.f_171435_ = new Vector3f(p_171445_, p_171446_, p_171447_);
        this.f_171436_ = new Vector3f(p_171448_, p_171449_, p_171450_);
        this.f_171437_ = p_171451_;
        this.f_171438_ = p_171452_;
        this.f_171440_ = new UVPair(p_171453_, p_171454_);
    }

    public ModelPart.Cube m_171455_(int p_171456_, int p_171457_) {
        return new ModelPart.Cube((int)this.f_171439_.m_171612_(), (int)this.f_171439_.m_171613_(), this.f_171435_.m_122239_(), this.f_171435_.m_122260_(), this.f_171435_.m_122269_(), this.f_171436_.m_122239_(), this.f_171436_.m_122260_(), this.f_171436_.m_122269_(), this.f_171437_.f_171459_, this.f_171437_.f_171460_, this.f_171437_.f_171461_, this.f_171438_, (float)p_171456_ * this.f_171440_.m_171612_(), (float)p_171457_ * this.f_171440_.m_171613_());
    }
}

