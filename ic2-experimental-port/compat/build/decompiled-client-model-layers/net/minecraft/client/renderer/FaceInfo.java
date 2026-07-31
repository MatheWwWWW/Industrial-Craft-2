/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import net.minecraft.Util;
import net.minecraft.core.Direction;

public final class FaceInfo
extends Enum<FaceInfo> {
    public static final /* enum */ FaceInfo DOWN = new FaceInfo(new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108991_), new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108991_));
    public static final /* enum */ FaceInfo UP = new FaceInfo(new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108994_), new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108994_));
    public static final /* enum */ FaceInfo NORTH = new FaceInfo(new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108994_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108994_));
    public static final /* enum */ FaceInfo SOUTH = new FaceInfo(new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108991_), new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108991_));
    public static final /* enum */ FaceInfo WEST = new FaceInfo(new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108994_), new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108996_, Constants.f_108995_, Constants.f_108991_), new VertexInfo(Constants.f_108996_, Constants.f_108992_, Constants.f_108991_));
    public static final /* enum */ FaceInfo EAST = new FaceInfo(new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108991_), new VertexInfo(Constants.f_108993_, Constants.f_108995_, Constants.f_108994_), new VertexInfo(Constants.f_108993_, Constants.f_108992_, Constants.f_108994_));
    private static final FaceInfo[] f_108974_;
    private final VertexInfo[] f_108975_;
    private static final /* synthetic */ FaceInfo[] $VALUES;

    public static FaceInfo[] values() {
        return (FaceInfo[])$VALUES.clone();
    }

    public static FaceInfo valueOf(String p_108989_) {
        return Enum.valueOf(FaceInfo.class, p_108989_);
    }

    public static FaceInfo m_108984_(Direction p_108985_) {
        return f_108974_[p_108985_.m_122411_()];
    }

    private FaceInfo(VertexInfo ... p_108981_) {
        this.f_108975_ = p_108981_;
    }

    public VertexInfo m_108982_(int p_108983_) {
        return this.f_108975_[p_108983_];
    }

    private static /* synthetic */ FaceInfo[] m_172572_() {
        return new FaceInfo[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
    }

    static {
        $VALUES = FaceInfo.m_172572_();
        f_108974_ = Util.m_137469_(new FaceInfo[6], p_108987_ -> {
            p_108987_[Constants.f_108995_] = DOWN;
            p_108987_[Constants.f_108992_] = UP;
            p_108987_[Constants.f_108994_] = NORTH;
            p_108987_[Constants.f_108991_] = SOUTH;
            p_108987_[Constants.f_108996_] = WEST;
            p_108987_[Constants.f_108993_] = EAST;
        });
    }

    public static class VertexInfo {
        public final int f_108998_;
        public final int f_108999_;
        public final int f_109000_;

        VertexInfo(int p_109002_, int p_109003_, int p_109004_) {
            this.f_108998_ = p_109002_;
            this.f_108999_ = p_109003_;
            this.f_109000_ = p_109004_;
        }
    }

    public static final class Constants {
        public static final int f_108991_ = Direction.SOUTH.m_122411_();
        public static final int f_108992_ = Direction.UP.m_122411_();
        public static final int f_108993_ = Direction.EAST.m_122411_();
        public static final int f_108994_ = Direction.NORTH.m_122411_();
        public static final int f_108995_ = Direction.DOWN.m_122411_();
        public static final int f_108996_ = Direction.WEST.m_122411_();
    }
}

