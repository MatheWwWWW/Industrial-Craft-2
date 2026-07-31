/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys.shapes;

public interface BooleanOp {
    public static final BooleanOp f_82681_ = (p_82747_, p_82748_) -> false;
    public static final BooleanOp f_82682_ = (p_82744_, p_82745_) -> !p_82744_ && !p_82745_;
    public static final BooleanOp f_82683_ = (p_82741_, p_82742_) -> p_82742_ && !p_82741_;
    public static final BooleanOp f_82684_ = (p_82738_, p_82739_) -> !p_82738_;
    public static final BooleanOp f_82685_ = (p_82735_, p_82736_) -> p_82735_ && !p_82736_;
    public static final BooleanOp f_82686_ = (p_82732_, p_82733_) -> !p_82733_;
    public static final BooleanOp f_82687_ = (p_82729_, p_82730_) -> p_82729_ != p_82730_;
    public static final BooleanOp f_82688_ = (p_82726_, p_82727_) -> !p_82726_ || !p_82727_;
    public static final BooleanOp f_82689_ = (p_82723_, p_82724_) -> p_82723_ && p_82724_;
    public static final BooleanOp f_82690_ = (p_82720_, p_82721_) -> p_82720_ == p_82721_;
    public static final BooleanOp f_82691_ = (p_82717_, p_82718_) -> p_82718_;
    public static final BooleanOp f_82692_ = (p_82714_, p_82715_) -> !p_82714_ || p_82715_;
    public static final BooleanOp f_82693_ = (p_82711_, p_82712_) -> p_82711_;
    public static final BooleanOp f_82694_ = (p_82708_, p_82709_) -> p_82708_ || !p_82709_;
    public static final BooleanOp f_82695_ = (p_82705_, p_82706_) -> p_82705_ || p_82706_;
    public static final BooleanOp f_82696_ = (p_82699_, p_82700_) -> true;

    public boolean m_82701_(boolean var1, boolean var2);
}

