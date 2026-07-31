/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.trading;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class MerchantOffer {
    private final ItemStack f_45310_;
    private final ItemStack f_45311_;
    private final ItemStack f_45312_;
    private int f_45313_;
    private final int f_45314_;
    private boolean f_45315_ = true;
    private int f_45316_;
    private int f_45317_;
    private float f_45318_;
    private int f_45319_ = 1;

    public MerchantOffer(CompoundTag p_45351_) {
        this.f_45310_ = ItemStack.m_41712_(p_45351_.m_128469_("buy"));
        this.f_45311_ = ItemStack.m_41712_(p_45351_.m_128469_("buyB"));
        this.f_45312_ = ItemStack.m_41712_(p_45351_.m_128469_("sell"));
        this.f_45313_ = p_45351_.m_128451_("uses");
        this.f_45314_ = p_45351_.m_128425_("maxUses", 99) ? p_45351_.m_128451_("maxUses") : 4;
        if (p_45351_.m_128425_("rewardExp", 1)) {
            this.f_45315_ = p_45351_.m_128471_("rewardExp");
        }
        if (p_45351_.m_128425_("xp", 3)) {
            this.f_45319_ = p_45351_.m_128451_("xp");
        }
        if (p_45351_.m_128425_("priceMultiplier", 5)) {
            this.f_45318_ = p_45351_.m_128457_("priceMultiplier");
        }
        this.f_45316_ = p_45351_.m_128451_("specialPrice");
        this.f_45317_ = p_45351_.m_128451_("demand");
    }

    public MerchantOffer(ItemStack p_45321_, ItemStack p_45322_, int p_45323_, int p_45324_, float p_45325_) {
        this(p_45321_, ItemStack.f_41583_, p_45322_, p_45323_, p_45324_, p_45325_);
    }

    public MerchantOffer(ItemStack p_45327_, ItemStack p_45328_, ItemStack p_45329_, int p_45330_, int p_45331_, float p_45332_) {
        this(p_45327_, p_45328_, p_45329_, 0, p_45330_, p_45331_, p_45332_);
    }

    public MerchantOffer(ItemStack p_45334_, ItemStack p_45335_, ItemStack p_45336_, int p_45337_, int p_45338_, int p_45339_, float p_45340_) {
        this(p_45334_, p_45335_, p_45336_, p_45337_, p_45338_, p_45339_, p_45340_, 0);
    }

    public MerchantOffer(ItemStack p_45342_, ItemStack p_45343_, ItemStack p_45344_, int p_45345_, int p_45346_, int p_45347_, float p_45348_, int p_45349_) {
        this.f_45310_ = p_45342_;
        this.f_45311_ = p_45343_;
        this.f_45312_ = p_45344_;
        this.f_45313_ = p_45345_;
        this.f_45314_ = p_45346_;
        this.f_45319_ = p_45347_;
        this.f_45318_ = p_45348_;
        this.f_45317_ = p_45349_;
    }

    public ItemStack m_45352_() {
        return this.f_45310_;
    }

    public ItemStack m_45358_() {
        int $$0 = this.f_45310_.m_41613_();
        ItemStack $$1 = this.f_45310_.m_41777_();
        int $$2 = Math.max(0, Mth.m_14143_((float)($$0 * this.f_45317_) * this.f_45318_));
        $$1.m_41764_(Mth.m_14045_($$0 + $$2 + this.f_45316_, 1, this.f_45310_.m_41720_().m_41459_()));
        return $$1;
    }

    public ItemStack m_45364_() {
        return this.f_45311_;
    }

    public ItemStack m_45368_() {
        return this.f_45312_;
    }

    public void m_45369_() {
        this.f_45317_ = this.f_45317_ + this.f_45313_ - (this.f_45314_ - this.f_45313_);
    }

    public ItemStack m_45370_() {
        return this.f_45312_.m_41777_();
    }

    public int m_45371_() {
        return this.f_45313_;
    }

    public void m_45372_() {
        this.f_45313_ = 0;
    }

    public int m_45373_() {
        return this.f_45314_;
    }

    public void m_45374_() {
        ++this.f_45313_;
    }

    public int m_45375_() {
        return this.f_45317_;
    }

    public void m_45353_(int p_45354_) {
        this.f_45316_ += p_45354_;
    }

    public void m_45376_() {
        this.f_45316_ = 0;
    }

    public int m_45377_() {
        return this.f_45316_;
    }

    public void m_45359_(int p_45360_) {
        this.f_45316_ = p_45360_;
    }

    public float m_45378_() {
        return this.f_45318_;
    }

    public int m_45379_() {
        return this.f_45319_;
    }

    public boolean m_45380_() {
        return this.f_45313_ >= this.f_45314_;
    }

    public void m_45381_() {
        this.f_45313_ = this.f_45314_;
    }

    public boolean m_45382_() {
        return this.f_45313_ > 0;
    }

    public boolean m_45383_() {
        return this.f_45315_;
    }

    public CompoundTag m_45384_() {
        CompoundTag $$0 = new CompoundTag();
        $$0.m_128365_("buy", this.f_45310_.m_41739_(new CompoundTag()));
        $$0.m_128365_("sell", this.f_45312_.m_41739_(new CompoundTag()));
        $$0.m_128365_("buyB", this.f_45311_.m_41739_(new CompoundTag()));
        $$0.m_128405_("uses", this.f_45313_);
        $$0.m_128405_("maxUses", this.f_45314_);
        $$0.m_128379_("rewardExp", this.f_45315_);
        $$0.m_128405_("xp", this.f_45319_);
        $$0.m_128350_("priceMultiplier", this.f_45318_);
        $$0.m_128405_("specialPrice", this.f_45316_);
        $$0.m_128405_("demand", this.f_45317_);
        return $$0;
    }

    public boolean m_45355_(ItemStack p_45356_, ItemStack p_45357_) {
        return this.m_45365_(p_45356_, this.m_45358_()) && p_45356_.m_41613_() >= this.m_45358_().m_41613_() && this.m_45365_(p_45357_, this.f_45311_) && p_45357_.m_41613_() >= this.f_45311_.m_41613_();
    }

    private boolean m_45365_(ItemStack p_45366_, ItemStack p_45367_) {
        if (p_45367_.m_41619_() && p_45366_.m_41619_()) {
            return true;
        }
        ItemStack $$2 = p_45366_.m_41777_();
        if ($$2.m_41720_().m_41465_()) {
            $$2.m_41721_($$2.m_41773_());
        }
        return ItemStack.m_41746_($$2, p_45367_) && (!p_45367_.m_41782_() || $$2.m_41782_() && NbtUtils.m_129235_(p_45367_.m_41783_(), $$2.m_41783_(), false));
    }

    public boolean m_45361_(ItemStack p_45362_, ItemStack p_45363_) {
        if (!this.m_45355_(p_45362_, p_45363_)) {
            return false;
        }
        p_45362_.m_41774_(this.m_45358_().m_41613_());
        if (!this.m_45364_().m_41619_()) {
            p_45363_.m_41774_(this.m_45364_().m_41613_());
        }
        return true;
    }
}

