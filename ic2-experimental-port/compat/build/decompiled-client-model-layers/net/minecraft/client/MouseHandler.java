/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.glfw.GLFWDropCallback
 */
package net.minecraft.client;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.InputConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.lwjgl.glfw.GLFWDropCallback;

public class MouseHandler {
    private final Minecraft f_91503_;
    private boolean f_91504_;
    private boolean f_91505_;
    private boolean f_91506_;
    private double f_91507_;
    private double f_91508_;
    private int f_91509_;
    private int f_91510_ = -1;
    private boolean f_91511_ = true;
    private int f_91512_;
    private double f_91513_;
    private final SmoothDouble f_91514_ = new SmoothDouble();
    private final SmoothDouble f_91515_ = new SmoothDouble();
    private double f_91516_;
    private double f_91517_;
    private double f_91518_;
    private double f_91519_ = Double.MIN_VALUE;
    private boolean f_91520_;

    public MouseHandler(Minecraft p_91522_) {
        this.f_91503_ = p_91522_;
    }

    private void m_91530_(long p_91531_, int p_91532_, int p_91533_, int p_91534_) {
        boolean $$4;
        if (p_91531_ != this.f_91503_.m_91268_().m_85439_()) {
            return;
        }
        boolean bl = $$4 = p_91533_ == 1;
        if (Minecraft.f_91002_ && p_91532_ == 0) {
            if ($$4) {
                if ((p_91534_ & 2) == 2) {
                    p_91532_ = 1;
                    ++this.f_91509_;
                }
            } else if (this.f_91509_ > 0) {
                p_91532_ = 1;
                --this.f_91509_;
            }
        }
        int $$5 = p_91532_;
        if ($$4) {
            if (this.f_91503_.f_91066_.m_231828_().m_231551_().booleanValue() && this.f_91512_++ > 0) {
                return;
            }
            this.f_91510_ = $$5;
            this.f_91513_ = Blaze3D.m_83640_();
        } else if (this.f_91510_ != -1) {
            if (this.f_91503_.f_91066_.m_231828_().m_231551_().booleanValue() && --this.f_91512_ > 0) {
                return;
            }
            this.f_91510_ = -1;
        }
        boolean[] $$6 = new boolean[]{false};
        if (this.f_91503_.m_91265_() == null) {
            if (this.f_91503_.f_91080_ == null) {
                if (!this.f_91520_ && $$4) {
                    this.m_91601_();
                }
            } else {
                double $$7 = this.f_91507_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
                double $$8 = this.f_91508_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
                Screen $$9 = this.f_91503_.f_91080_;
                if ($$4) {
                    $$9.m_169415_();
                    Screen.m_96579_(() -> {
                        p_168085_[0] = $$9.m_6375_($$7, $$8, $$5);
                    }, "mouseClicked event handler", $$9.getClass().getCanonicalName());
                } else {
                    Screen.m_96579_(() -> {
                        p_168079_[0] = $$9.m_6348_($$7, $$8, $$5);
                    }, "mouseReleased event handler", $$9.getClass().getCanonicalName());
                }
            }
        }
        if (!$$6[0] && (this.f_91503_.f_91080_ == null || this.f_91503_.f_91080_.f_96546_) && this.f_91503_.m_91265_() == null) {
            if ($$5 == 0) {
                this.f_91504_ = $$4;
            } else if ($$5 == 2) {
                this.f_91505_ = $$4;
            } else if ($$5 == 1) {
                this.f_91506_ = $$4;
            }
            KeyMapping.m_90837_(InputConstants.Type.MOUSE.m_84895_($$5), $$4);
            if ($$4) {
                if (this.f_91503_.f_91074_.m_5833_() && $$5 == 2) {
                    this.f_91503_.f_91065_.m_93085_().m_94793_();
                } else {
                    KeyMapping.m_90835_(InputConstants.Type.MOUSE.m_84895_($$5));
                }
            }
        }
    }

    private void m_91526_(long p_91527_, double p_91528_, double p_91529_) {
        if (p_91527_ == Minecraft.m_91087_().m_91268_().m_85439_()) {
            double $$3 = (this.f_91503_.f_91066_.m_231821_().m_231551_() != false ? Math.signum(p_91529_) : p_91529_) * this.f_91503_.f_91066_.m_232122_().m_231551_();
            if (this.f_91503_.m_91265_() == null) {
                if (this.f_91503_.f_91080_ != null) {
                    double $$4 = this.f_91507_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
                    double $$5 = this.f_91508_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
                    this.f_91503_.f_91080_.m_6050_($$4, $$5, $$3);
                    this.f_91503_.f_91080_.m_169415_();
                } else if (this.f_91503_.f_91074_ != null) {
                    if (this.f_91518_ != 0.0 && Math.signum($$3) != Math.signum(this.f_91518_)) {
                        this.f_91518_ = 0.0;
                    }
                    this.f_91518_ += $$3;
                    int $$6 = (int)this.f_91518_;
                    if ($$6 == 0) {
                        return;
                    }
                    this.f_91518_ -= (double)$$6;
                    if (this.f_91503_.f_91074_.m_5833_()) {
                        if (this.f_91503_.f_91065_.m_93085_().m_94768_()) {
                            this.f_91503_.f_91065_.m_93085_().m_205380_(-$$6);
                        } else {
                            float $$7 = Mth.m_14036_(this.f_91503_.f_91074_.m_150110_().m_35942_() + (float)$$6 * 0.005f, 0.0f, 0.2f);
                            this.f_91503_.f_91074_.m_150110_().m_35943_($$7);
                        }
                    } else {
                        this.f_91503_.f_91074_.m_150109_().m_35988_($$6);
                    }
                }
            }
        }
    }

    private void m_91539_(long p_91540_, List<Path> p_91541_) {
        if (this.f_91503_.f_91080_ != null) {
            this.f_91503_.f_91080_.m_7400_(p_91541_);
        }
    }

    public void m_91524_(long p_91525_) {
        InputConstants.m_84838_(p_91525_, (p_91591_, p_91592_, p_91593_) -> this.f_91503_.execute(() -> this.m_91561_(p_91591_, p_91592_, p_91593_)), (p_91566_, p_91567_, p_91568_, p_91569_) -> this.f_91503_.execute(() -> this.m_91530_(p_91566_, p_91567_, p_91568_, p_91569_)), (p_91576_, p_91577_, p_91578_) -> this.f_91503_.execute(() -> this.m_91526_(p_91576_, p_91577_, p_91578_)), (p_91536_, p_91537_, p_91538_) -> {
            Path[] $$3 = new Path[p_91537_];
            for (int $$4 = 0; $$4 < p_91537_; ++$$4) {
                $$3[$$4] = Paths.get(GLFWDropCallback.getName((long)p_91538_, (int)$$4), new String[0]);
            }
            this.f_91503_.execute(() -> this.m_91539_(p_91536_, Arrays.asList($$3)));
        });
    }

    private void m_91561_(long p_91562_, double p_91563_, double p_91564_) {
        Screen $$3;
        if (p_91562_ != Minecraft.m_91087_().m_91268_().m_85439_()) {
            return;
        }
        if (this.f_91511_) {
            this.f_91507_ = p_91563_;
            this.f_91508_ = p_91564_;
            this.f_91511_ = false;
        }
        if (($$3 = this.f_91503_.f_91080_) != null && this.f_91503_.m_91265_() == null) {
            double $$4 = p_91563_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
            double $$5 = p_91564_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
            Screen.m_96579_(() -> $$3.m_94757_($$4, $$5), "mouseMoved event handler", $$3.getClass().getCanonicalName());
            if (this.f_91510_ != -1 && this.f_91513_ > 0.0) {
                double $$6 = (p_91563_ - this.f_91507_) * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
                double $$7 = (p_91564_ - this.f_91508_) * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
                Screen.m_96579_(() -> $$3.m_7979_($$4, $$5, this.f_91510_, $$6, $$7), "mouseDragged event handler", $$3.getClass().getCanonicalName());
            }
            $$3.m_169414_();
        }
        this.f_91503_.m_91307_().m_6180_("mouse");
        if (this.m_91600_() && this.f_91503_.m_91302_()) {
            this.f_91516_ += p_91563_ - this.f_91507_;
            this.f_91517_ += p_91564_ - this.f_91508_;
        }
        this.m_91523_();
        this.f_91507_ = p_91563_;
        this.f_91508_ = p_91564_;
        this.f_91503_.m_91307_().m_7238_();
    }

    public void m_91523_() {
        double $$12;
        double $$11;
        double $$0 = Blaze3D.m_83640_();
        double $$1 = $$0 - this.f_91519_;
        this.f_91519_ = $$0;
        if (!this.m_91600_() || !this.f_91503_.m_91302_()) {
            this.f_91516_ = 0.0;
            this.f_91517_ = 0.0;
            return;
        }
        double $$2 = this.f_91503_.f_91066_.m_231964_().m_231551_() * (double)0.6f + (double)0.2f;
        double $$3 = $$2 * $$2 * $$2;
        double $$4 = $$3 * 8.0;
        if (this.f_91503_.f_91066_.f_92067_) {
            double $$5 = this.f_91514_.m_14237_(this.f_91516_ * $$4, $$1 * $$4);
            double $$6 = this.f_91515_.m_14237_(this.f_91517_ * $$4, $$1 * $$4);
            double $$7 = $$5;
            double $$8 = $$6;
        } else if (this.f_91503_.f_91066_.m_92176_().m_90612_() && this.f_91503_.f_91074_.m_150108_()) {
            this.f_91514_.m_14236_();
            this.f_91515_.m_14236_();
            double $$9 = this.f_91516_ * $$3;
            double $$10 = this.f_91517_ * $$3;
        } else {
            this.f_91514_.m_14236_();
            this.f_91515_.m_14236_();
            $$11 = this.f_91516_ * $$4;
            $$12 = this.f_91517_ * $$4;
        }
        this.f_91516_ = 0.0;
        this.f_91517_ = 0.0;
        int $$13 = 1;
        if (this.f_91503_.f_91066_.m_231820_().m_231551_().booleanValue()) {
            $$13 = -1;
        }
        this.f_91503_.m_91301_().m_120565_($$11, $$12);
        if (this.f_91503_.f_91074_ != null) {
            this.f_91503_.f_91074_.m_19884_($$11, $$12 * (double)$$13);
        }
    }

    public boolean m_91560_() {
        return this.f_91504_;
    }

    public boolean m_168090_() {
        return this.f_91505_;
    }

    public boolean m_91584_() {
        return this.f_91506_;
    }

    public double m_91589_() {
        return this.f_91507_;
    }

    public double m_91594_() {
        return this.f_91508_;
    }

    public void m_91599_() {
        this.f_91511_ = true;
    }

    public boolean m_91600_() {
        return this.f_91520_;
    }

    public void m_91601_() {
        if (!this.f_91503_.m_91302_()) {
            return;
        }
        if (this.f_91520_) {
            return;
        }
        if (!Minecraft.f_91002_) {
            KeyMapping.m_90829_();
        }
        this.f_91520_ = true;
        this.f_91507_ = this.f_91503_.m_91268_().m_85443_() / 2;
        this.f_91508_ = this.f_91503_.m_91268_().m_85444_() / 2;
        InputConstants.m_84833_(this.f_91503_.m_91268_().m_85439_(), 212995, this.f_91507_, this.f_91508_);
        this.f_91503_.m_91152_(null);
        this.f_91503_.f_91078_ = 10000;
        this.f_91511_ = true;
    }

    public void m_91602_() {
        if (!this.f_91520_) {
            return;
        }
        this.f_91520_ = false;
        this.f_91507_ = this.f_91503_.m_91268_().m_85443_() / 2;
        this.f_91508_ = this.f_91503_.m_91268_().m_85444_() / 2;
        InputConstants.m_84833_(this.f_91503_.m_91268_().m_85439_(), 212993, this.f_91507_, this.f_91508_);
    }

    public void m_91603_() {
        this.f_91511_ = true;
    }
}

