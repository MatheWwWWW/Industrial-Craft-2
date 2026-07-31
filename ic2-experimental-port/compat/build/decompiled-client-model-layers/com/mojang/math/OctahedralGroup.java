/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.booleans.BooleanArrayList
 *  it.unimi.dsi.fastutil.booleans.BooleanList
 *  javax.annotation.Nullable
 */
package com.mojang.math;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix3f;
import com.mojang.math.SymmetricGroup3;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.util.StringRepresentable;

public final class OctahedralGroup
extends Enum<OctahedralGroup>
implements StringRepresentable {
    public static final /* enum */ OctahedralGroup IDENTITY = new OctahedralGroup("identity", SymmetricGroup3.P123, false, false, false);
    public static final /* enum */ OctahedralGroup ROT_180_FACE_XY = new OctahedralGroup("rot_180_face_xy", SymmetricGroup3.P123, true, true, false);
    public static final /* enum */ OctahedralGroup ROT_180_FACE_XZ = new OctahedralGroup("rot_180_face_xz", SymmetricGroup3.P123, true, false, true);
    public static final /* enum */ OctahedralGroup ROT_180_FACE_YZ = new OctahedralGroup("rot_180_face_yz", SymmetricGroup3.P123, false, true, true);
    public static final /* enum */ OctahedralGroup ROT_120_NNN = new OctahedralGroup("rot_120_nnn", SymmetricGroup3.P231, false, false, false);
    public static final /* enum */ OctahedralGroup ROT_120_NNP = new OctahedralGroup("rot_120_nnp", SymmetricGroup3.P312, true, false, true);
    public static final /* enum */ OctahedralGroup ROT_120_NPN = new OctahedralGroup("rot_120_npn", SymmetricGroup3.P312, false, true, true);
    public static final /* enum */ OctahedralGroup ROT_120_NPP = new OctahedralGroup("rot_120_npp", SymmetricGroup3.P231, true, false, true);
    public static final /* enum */ OctahedralGroup ROT_120_PNN = new OctahedralGroup("rot_120_pnn", SymmetricGroup3.P312, true, true, false);
    public static final /* enum */ OctahedralGroup ROT_120_PNP = new OctahedralGroup("rot_120_pnp", SymmetricGroup3.P231, true, true, false);
    public static final /* enum */ OctahedralGroup ROT_120_PPN = new OctahedralGroup("rot_120_ppn", SymmetricGroup3.P231, false, true, true);
    public static final /* enum */ OctahedralGroup ROT_120_PPP = new OctahedralGroup("rot_120_ppp", SymmetricGroup3.P312, false, false, false);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_XY_NEG = new OctahedralGroup("rot_180_edge_xy_neg", SymmetricGroup3.P213, true, true, true);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_XY_POS = new OctahedralGroup("rot_180_edge_xy_pos", SymmetricGroup3.P213, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_XZ_NEG = new OctahedralGroup("rot_180_edge_xz_neg", SymmetricGroup3.P321, true, true, true);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_XZ_POS = new OctahedralGroup("rot_180_edge_xz_pos", SymmetricGroup3.P321, false, true, false);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_YZ_NEG = new OctahedralGroup("rot_180_edge_yz_neg", SymmetricGroup3.P132, true, true, true);
    public static final /* enum */ OctahedralGroup ROT_180_EDGE_YZ_POS = new OctahedralGroup("rot_180_edge_yz_pos", SymmetricGroup3.P132, true, false, false);
    public static final /* enum */ OctahedralGroup ROT_90_X_NEG = new OctahedralGroup("rot_90_x_neg", SymmetricGroup3.P132, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_90_X_POS = new OctahedralGroup("rot_90_x_pos", SymmetricGroup3.P132, false, true, false);
    public static final /* enum */ OctahedralGroup ROT_90_Y_NEG = new OctahedralGroup("rot_90_y_neg", SymmetricGroup3.P321, true, false, false);
    public static final /* enum */ OctahedralGroup ROT_90_Y_POS = new OctahedralGroup("rot_90_y_pos", SymmetricGroup3.P321, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_90_Z_NEG = new OctahedralGroup("rot_90_z_neg", SymmetricGroup3.P213, false, true, false);
    public static final /* enum */ OctahedralGroup ROT_90_Z_POS = new OctahedralGroup("rot_90_z_pos", SymmetricGroup3.P213, true, false, false);
    public static final /* enum */ OctahedralGroup INVERSION = new OctahedralGroup("inversion", SymmetricGroup3.P123, true, true, true);
    public static final /* enum */ OctahedralGroup INVERT_X = new OctahedralGroup("invert_x", SymmetricGroup3.P123, true, false, false);
    public static final /* enum */ OctahedralGroup INVERT_Y = new OctahedralGroup("invert_y", SymmetricGroup3.P123, false, true, false);
    public static final /* enum */ OctahedralGroup INVERT_Z = new OctahedralGroup("invert_z", SymmetricGroup3.P123, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_60_REF_NNN = new OctahedralGroup("rot_60_ref_nnn", SymmetricGroup3.P312, true, true, true);
    public static final /* enum */ OctahedralGroup ROT_60_REF_NNP = new OctahedralGroup("rot_60_ref_nnp", SymmetricGroup3.P231, true, false, false);
    public static final /* enum */ OctahedralGroup ROT_60_REF_NPN = new OctahedralGroup("rot_60_ref_npn", SymmetricGroup3.P231, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_60_REF_NPP = new OctahedralGroup("rot_60_ref_npp", SymmetricGroup3.P312, false, false, true);
    public static final /* enum */ OctahedralGroup ROT_60_REF_PNN = new OctahedralGroup("rot_60_ref_pnn", SymmetricGroup3.P231, false, true, false);
    public static final /* enum */ OctahedralGroup ROT_60_REF_PNP = new OctahedralGroup("rot_60_ref_pnp", SymmetricGroup3.P312, true, false, false);
    public static final /* enum */ OctahedralGroup ROT_60_REF_PPN = new OctahedralGroup("rot_60_ref_ppn", SymmetricGroup3.P312, false, true, false);
    public static final /* enum */ OctahedralGroup ROT_60_REF_PPP = new OctahedralGroup("rot_60_ref_ppp", SymmetricGroup3.P231, true, true, true);
    public static final /* enum */ OctahedralGroup SWAP_XY = new OctahedralGroup("swap_xy", SymmetricGroup3.P213, false, false, false);
    public static final /* enum */ OctahedralGroup SWAP_YZ = new OctahedralGroup("swap_yz", SymmetricGroup3.P132, false, false, false);
    public static final /* enum */ OctahedralGroup SWAP_XZ = new OctahedralGroup("swap_xz", SymmetricGroup3.P321, false, false, false);
    public static final /* enum */ OctahedralGroup SWAP_NEG_XY = new OctahedralGroup("swap_neg_xy", SymmetricGroup3.P213, true, true, false);
    public static final /* enum */ OctahedralGroup SWAP_NEG_YZ = new OctahedralGroup("swap_neg_yz", SymmetricGroup3.P132, false, true, true);
    public static final /* enum */ OctahedralGroup SWAP_NEG_XZ = new OctahedralGroup("swap_neg_xz", SymmetricGroup3.P321, true, false, true);
    public static final /* enum */ OctahedralGroup ROT_90_REF_X_NEG = new OctahedralGroup("rot_90_ref_x_neg", SymmetricGroup3.P132, true, false, true);
    public static final /* enum */ OctahedralGroup ROT_90_REF_X_POS = new OctahedralGroup("rot_90_ref_x_pos", SymmetricGroup3.P132, true, true, false);
    public static final /* enum */ OctahedralGroup ROT_90_REF_Y_NEG = new OctahedralGroup("rot_90_ref_y_neg", SymmetricGroup3.P321, true, true, false);
    public static final /* enum */ OctahedralGroup ROT_90_REF_Y_POS = new OctahedralGroup("rot_90_ref_y_pos", SymmetricGroup3.P321, false, true, true);
    public static final /* enum */ OctahedralGroup ROT_90_REF_Z_NEG = new OctahedralGroup("rot_90_ref_z_neg", SymmetricGroup3.P213, false, true, true);
    public static final /* enum */ OctahedralGroup ROT_90_REF_Z_POS = new OctahedralGroup("rot_90_ref_z_pos", SymmetricGroup3.P213, true, false, true);
    private final Matrix3f f_56473_;
    private final String f_56474_;
    @Nullable
    private Map<Direction, Direction> f_56475_;
    private final boolean f_56476_;
    private final boolean f_56478_;
    private final boolean f_56479_;
    private final SymmetricGroup3 f_56480_;
    private static final OctahedralGroup[][] f_56481_;
    private static final OctahedralGroup[] f_56482_;
    private static final /* synthetic */ OctahedralGroup[] $VALUES;

    public static OctahedralGroup[] values() {
        return (OctahedralGroup[])$VALUES.clone();
    }

    public static OctahedralGroup valueOf(String p_56543_) {
        return Enum.valueOf(OctahedralGroup.class, p_56543_);
    }

    private OctahedralGroup(String p_56513_, SymmetricGroup3 p_56514_, boolean p_56515_, boolean p_56516_, boolean p_56517_) {
        this.f_56474_ = p_56513_;
        this.f_56476_ = p_56515_;
        this.f_56478_ = p_56516_;
        this.f_56479_ = p_56517_;
        this.f_56480_ = p_56514_;
        this.f_56473_ = new Matrix3f();
        this.f_56473_.f_8134_ = p_56515_ ? -1.0f : 1.0f;
        this.f_56473_.f_8138_ = p_56516_ ? -1.0f : 1.0f;
        this.f_56473_.f_8142_ = p_56517_ ? -1.0f : 1.0f;
        this.f_56473_.m_8178_(p_56514_.m_109179_());
    }

    private BooleanList m_56534_() {
        return new BooleanArrayList(new boolean[]{this.f_56476_, this.f_56478_, this.f_56479_});
    }

    public OctahedralGroup m_56521_(OctahedralGroup p_56522_) {
        return f_56481_[this.ordinal()][p_56522_.ordinal()];
    }

    public OctahedralGroup m_174944_() {
        return f_56482_[this.ordinal()];
    }

    public Matrix3f m_174948_() {
        return this.f_56473_.m_8183_();
    }

    public String toString() {
        return this.f_56474_;
    }

    @Override
    public String m_7912_() {
        return this.f_56474_;
    }

    public Direction m_56528_(Direction p_56529_) {
        if (this.f_56475_ == null) {
            this.f_56475_ = Maps.newEnumMap(Direction.class);
            for (Direction $$1 : Direction.values()) {
                Direction.Axis $$2 = $$1.m_122434_();
                Direction.AxisDirection $$3 = $$1.m_122421_();
                Direction.Axis $$4 = Direction.Axis.values()[this.f_56480_.m_109180_($$2.ordinal())];
                Direction.AxisDirection $$5 = this.m_56526_($$4) ? $$3.m_122541_() : $$3;
                Direction $$6 = Direction.m_122387_($$4, $$5);
                this.f_56475_.put($$1, $$6);
            }
        }
        return this.f_56475_.get(p_56529_);
    }

    public boolean m_56526_(Direction.Axis p_56527_) {
        switch (p_56527_) {
            case X: {
                return this.f_56476_;
            }
            case Y: {
                return this.f_56478_;
            }
        }
        return this.f_56479_;
    }

    public FrontAndTop m_56530_(FrontAndTop p_56531_) {
        return FrontAndTop.m_122622_(this.m_56528_(p_56531_.m_122625_()), this.m_56528_(p_56531_.m_122629_()));
    }

    private static /* synthetic */ OctahedralGroup[] m_174953_() {
        return new OctahedralGroup[]{IDENTITY, ROT_180_FACE_XY, ROT_180_FACE_XZ, ROT_180_FACE_YZ, ROT_120_NNN, ROT_120_NNP, ROT_120_NPN, ROT_120_NPP, ROT_120_PNN, ROT_120_PNP, ROT_120_PPN, ROT_120_PPP, ROT_180_EDGE_XY_NEG, ROT_180_EDGE_XY_POS, ROT_180_EDGE_XZ_NEG, ROT_180_EDGE_XZ_POS, ROT_180_EDGE_YZ_NEG, ROT_180_EDGE_YZ_POS, ROT_90_X_NEG, ROT_90_X_POS, ROT_90_Y_NEG, ROT_90_Y_POS, ROT_90_Z_NEG, ROT_90_Z_POS, INVERSION, INVERT_X, INVERT_Y, INVERT_Z, ROT_60_REF_NNN, ROT_60_REF_NNP, ROT_60_REF_NPN, ROT_60_REF_NPP, ROT_60_REF_PNN, ROT_60_REF_PNP, ROT_60_REF_PPN, ROT_60_REF_PPP, SWAP_XY, SWAP_YZ, SWAP_XZ, SWAP_NEG_XY, SWAP_NEG_YZ, SWAP_NEG_XZ, ROT_90_REF_X_NEG, ROT_90_REF_X_POS, ROT_90_REF_Y_NEG, ROT_90_REF_Y_POS, ROT_90_REF_Z_NEG, ROT_90_REF_Z_POS};
    }

    static {
        $VALUES = OctahedralGroup.m_174953_();
        f_56481_ = Util.m_137469_(new OctahedralGroup[OctahedralGroup.values().length][OctahedralGroup.values().length], p_56533_ -> {
            Map<Pair, OctahedralGroup> $$1 = Arrays.stream(OctahedralGroup.values()).collect(Collectors.toMap(p_174952_ -> Pair.of((Object)((Object)p_174952_.f_56480_), (Object)p_174952_.m_56534_()), p_174950_ -> p_174950_));
            for (OctahedralGroup $$2 : OctahedralGroup.values()) {
                for (OctahedralGroup $$3 : OctahedralGroup.values()) {
                    BooleanList $$4 = $$2.m_56534_();
                    BooleanList $$5 = $$3.m_56534_();
                    SymmetricGroup3 $$6 = $$3.f_56480_.m_109182_($$2.f_56480_);
                    BooleanArrayList $$7 = new BooleanArrayList(3);
                    for (int $$8 = 0; $$8 < 3; ++$$8) {
                        $$7.add($$4.getBoolean($$8) ^ $$5.getBoolean($$2.f_56480_.m_109180_($$8)));
                    }
                    p_56533_[$$2.ordinal()][$$3.ordinal()] = $$1.get(Pair.of((Object)((Object)$$6), (Object)$$7));
                }
            }
        });
        f_56482_ = (OctahedralGroup[])Arrays.stream(OctahedralGroup.values()).map(p_56536_ -> Arrays.stream(OctahedralGroup.values()).filter(p_174947_ -> p_56536_.m_56521_((OctahedralGroup)p_174947_) == IDENTITY).findAny().get()).toArray(OctahedralGroup[]::new);
    }
}

