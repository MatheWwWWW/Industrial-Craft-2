/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.pathfinder;

import net.minecraft.world.level.pathfinder.Node;

public class BinaryHeap {
    private Node[] f_77078_ = new Node[128];
    private int f_77079_;

    public Node m_77084_(Node p_77085_) {
        if (p_77085_.f_77274_ >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.f_77079_ == this.f_77078_.length) {
            Node[] $$1 = new Node[this.f_77079_ << 1];
            System.arraycopy(this.f_77078_, 0, $$1, 0, this.f_77079_);
            this.f_77078_ = $$1;
        }
        this.f_77078_[this.f_77079_] = p_77085_;
        p_77085_.f_77274_ = this.f_77079_;
        this.m_77082_(this.f_77079_++);
        return p_77085_;
    }

    public void m_77081_() {
        this.f_77079_ = 0;
    }

    public Node m_164680_() {
        return this.f_77078_[0];
    }

    public Node m_77091_() {
        Node $$0 = this.f_77078_[0];
        this.f_77078_[0] = this.f_77078_[--this.f_77079_];
        this.f_77078_[this.f_77079_] = null;
        if (this.f_77079_ > 0) {
            this.m_77089_(0);
        }
        $$0.f_77274_ = -1;
        return $$0;
    }

    public void m_164681_(Node p_164682_) {
        this.f_77078_[p_164682_.f_77274_] = this.f_77078_[--this.f_77079_];
        this.f_77078_[this.f_77079_] = null;
        if (this.f_77079_ > p_164682_.f_77274_) {
            if (this.f_77078_[p_164682_.f_77274_].f_77277_ < p_164682_.f_77277_) {
                this.m_77082_(p_164682_.f_77274_);
            } else {
                this.m_77089_(p_164682_.f_77274_);
            }
        }
        p_164682_.f_77274_ = -1;
    }

    public void m_77086_(Node p_77087_, float p_77088_) {
        float $$2 = p_77087_.f_77277_;
        p_77087_.f_77277_ = p_77088_;
        if (p_77088_ < $$2) {
            this.m_77082_(p_77087_.f_77274_);
        } else {
            this.m_77089_(p_77087_.f_77274_);
        }
    }

    public int m_164683_() {
        return this.f_77079_;
    }

    private void m_77082_(int p_77083_) {
        Node $$1 = this.f_77078_[p_77083_];
        float $$2 = $$1.f_77277_;
        while (p_77083_ > 0) {
            int $$3 = p_77083_ - 1 >> 1;
            Node $$4 = this.f_77078_[$$3];
            if (!($$2 < $$4.f_77277_)) break;
            this.f_77078_[p_77083_] = $$4;
            $$4.f_77274_ = p_77083_;
            p_77083_ = $$3;
        }
        this.f_77078_[p_77083_] = $$1;
        $$1.f_77274_ = p_77083_;
    }

    private void m_77089_(int p_77090_) {
        Node $$1 = this.f_77078_[p_77090_];
        float $$2 = $$1.f_77277_;
        while (true) {
            float $$10;
            Node $$9;
            int $$3 = 1 + (p_77090_ << 1);
            int $$4 = $$3 + 1;
            if ($$3 >= this.f_77079_) break;
            Node $$5 = this.f_77078_[$$3];
            float $$6 = $$5.f_77277_;
            if ($$4 >= this.f_77079_) {
                Object $$7 = null;
                float $$8 = Float.POSITIVE_INFINITY;
            } else {
                $$9 = this.f_77078_[$$4];
                $$10 = $$9.f_77277_;
            }
            if ($$6 < $$10) {
                if (!($$6 < $$2)) break;
                this.f_77078_[p_77090_] = $$5;
                $$5.f_77274_ = p_77090_;
                p_77090_ = $$3;
                continue;
            }
            if (!($$10 < $$2)) break;
            this.f_77078_[p_77090_] = $$9;
            $$9.f_77274_ = p_77090_;
            p_77090_ = $$4;
        }
        this.f_77078_[p_77090_] = $$1;
        $$1.f_77274_ = p_77090_;
    }

    public boolean m_77092_() {
        return this.f_77079_ == 0;
    }

    public Node[] m_164684_() {
        Node[] $$0 = new Node[this.m_164683_()];
        System.arraycopy(this.f_77078_, 0, $$0, 0, this.m_164683_());
        return $$0;
    }
}

