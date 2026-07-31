/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.model;

import com.mojang.math.OctahedralGroup;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.util.Mth;

public final class BlockModelRotation
extends Enum<BlockModelRotation>
implements ModelState {
    public static final /* enum */ BlockModelRotation X0_Y0 = new BlockModelRotation(0, 0);
    public static final /* enum */ BlockModelRotation X0_Y90 = new BlockModelRotation(0, 90);
    public static final /* enum */ BlockModelRotation X0_Y180 = new BlockModelRotation(0, 180);
    public static final /* enum */ BlockModelRotation X0_Y270 = new BlockModelRotation(0, 270);
    public static final /* enum */ BlockModelRotation X90_Y0 = new BlockModelRotation(90, 0);
    public static final /* enum */ BlockModelRotation X90_Y90 = new BlockModelRotation(90, 90);
    public static final /* enum */ BlockModelRotation X90_Y180 = new BlockModelRotation(90, 180);
    public static final /* enum */ BlockModelRotation X90_Y270 = new BlockModelRotation(90, 270);
    public static final /* enum */ BlockModelRotation X180_Y0 = new BlockModelRotation(180, 0);
    public static final /* enum */ BlockModelRotation X180_Y90 = new BlockModelRotation(180, 90);
    public static final /* enum */ BlockModelRotation X180_Y180 = new BlockModelRotation(180, 180);
    public static final /* enum */ BlockModelRotation X180_Y270 = new BlockModelRotation(180, 270);
    public static final /* enum */ BlockModelRotation X270_Y0 = new BlockModelRotation(270, 0);
    public static final /* enum */ BlockModelRotation X270_Y90 = new BlockModelRotation(270, 90);
    public static final /* enum */ BlockModelRotation X270_Y180 = new BlockModelRotation(270, 180);
    public static final /* enum */ BlockModelRotation X270_Y270 = new BlockModelRotation(270, 270);
    private static final int f_174872_ = 360;
    private static final Map<Integer, BlockModelRotation> f_119142_;
    private final Transformation f_119143_;
    private final OctahedralGroup f_119144_;
    private final int f_119145_;
    private static final /* synthetic */ BlockModelRotation[] $VALUES;

    public static BlockModelRotation[] values() {
        return (BlockModelRotation[])$VALUES.clone();
    }

    public static BlockModelRotation valueOf(String p_119165_) {
        return Enum.valueOf(BlockModelRotation.class, p_119165_);
    }

    private static int m_119159_(int p_119160_, int p_119161_) {
        return p_119160_ * 360 + p_119161_;
    }

    private BlockModelRotation(int p_119151_, int p_119152_) {
        this.f_119145_ = BlockModelRotation.m_119159_(p_119151_, p_119152_);
        Quaternion $$2 = Vector3f.f_122225_.m_122240_(-p_119152_);
        $$2.m_80148_(Vector3f.f_122223_.m_122240_(-p_119151_));
        OctahedralGroup $$3 = OctahedralGroup.IDENTITY;
        for (int $$4 = 0; $$4 < p_119152_; $$4 += 90) {
            $$3 = $$3.m_56521_(OctahedralGroup.ROT_90_Y_NEG);
        }
        for (int $$5 = 0; $$5 < p_119151_; $$5 += 90) {
            $$3 = $$3.m_56521_(OctahedralGroup.ROT_90_X_NEG);
        }
        this.f_119143_ = new Transformation(null, $$2, null, null);
        this.f_119144_ = $$3;
    }

    @Override
    public Transformation m_6189_() {
        return this.f_119143_;
    }

    public static BlockModelRotation m_119153_(int p_119154_, int p_119155_) {
        return f_119142_.get(BlockModelRotation.m_119159_(Mth.m_14100_(p_119154_, 360), Mth.m_14100_(p_119155_, 360)));
    }

    public OctahedralGroup m_174873_() {
        return this.f_119144_;
    }

    private static /* synthetic */ BlockModelRotation[] m_174874_() {
        return new BlockModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};
    }

    static {
        $VALUES = BlockModelRotation.m_174874_();
        f_119142_ = Arrays.stream(BlockModelRotation.values()).collect(Collectors.toMap(p_119163_ -> p_119163_.f_119145_, p_119157_ -> p_119157_));
    }
}

