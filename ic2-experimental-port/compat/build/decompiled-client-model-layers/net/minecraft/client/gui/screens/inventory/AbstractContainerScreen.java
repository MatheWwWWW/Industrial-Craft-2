/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractContainerScreen<T extends AbstractContainerMenu>
extends Screen
implements MenuAccess<T> {
    public static final ResourceLocation f_97725_ = new ResourceLocation("textures/gui/container/inventory.png");
    private static final float f_169605_ = 100.0f;
    private static final int f_169600_ = 500;
    public static final int f_169603_ = 100;
    private static final int f_169602_ = 200;
    protected int f_97726_ = 176;
    protected int f_97727_ = 166;
    protected int f_97728_;
    protected int f_97729_;
    protected int f_97730_;
    protected int f_97731_;
    protected final T f_97732_;
    protected final Component f_169604_;
    @Nullable
    protected Slot f_97734_;
    @Nullable
    private Slot f_97706_;
    @Nullable
    private Slot f_97707_;
    @Nullable
    private Slot f_97708_;
    @Nullable
    private Slot f_97709_;
    protected int f_97735_;
    protected int f_97736_;
    private boolean f_97710_;
    private ItemStack f_97711_ = ItemStack.f_41583_;
    private int f_97712_;
    private int f_97713_;
    private long f_97714_;
    private ItemStack f_97715_ = ItemStack.f_41583_;
    private long f_97716_;
    protected final Set<Slot> f_97737_ = Sets.newHashSet();
    protected boolean f_97738_;
    private int f_97717_;
    private int f_97718_;
    private boolean f_97719_;
    private int f_97720_;
    private long f_97721_;
    private int f_97722_;
    private boolean f_97723_;
    private ItemStack f_97724_ = ItemStack.f_41583_;

    public AbstractContainerScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97743_);
        this.f_97732_ = p_97741_;
        this.f_169604_ = p_97742_.m_5446_();
        this.f_97719_ = true;
        this.f_97728_ = 8;
        this.f_97729_ = 6;
        this.f_97730_ = 8;
        this.f_97731_ = this.f_97727_ - 94;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
        this.f_97736_ = (this.f_96544_ - this.f_97727_) / 2;
    }

    @Override
    public void m_6305_(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        ItemStack $$11;
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        this.m_7286_(p_97795_, p_97798_, p_97796_, p_97797_);
        RenderSystem.m_69465_();
        super.m_6305_(p_97795_, p_97796_, p_97797_, p_97798_);
        PoseStack $$6 = RenderSystem.m_157191_();
        $$6.m_85836_();
        $$6.m_85837_($$4, $$5, 0.0);
        RenderSystem.m_157182_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        this.f_97734_ = null;
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        for (int $$7 = 0; $$7 < ((AbstractContainerMenu)this.f_97732_).f_38839_.size(); ++$$7) {
            Slot $$8 = ((AbstractContainerMenu)this.f_97732_).f_38839_.get($$7);
            if ($$8.m_6659_()) {
                RenderSystem.m_157427_(GameRenderer::m_172817_);
                this.m_97799_(p_97795_, $$8);
            }
            if (!this.m_97774_($$8, p_97796_, p_97797_) || !$$8.m_6659_()) continue;
            this.f_97734_ = $$8;
            int $$9 = $$8.f_40220_;
            int $$10 = $$8.f_40221_;
            AbstractContainerScreen.m_169606_(p_97795_, $$9, $$10, this.m_93252_());
        }
        this.m_7027_(p_97795_, p_97796_, p_97797_);
        ItemStack itemStack = $$11 = this.f_97711_.m_41619_() ? ((AbstractContainerMenu)this.f_97732_).m_142621_() : this.f_97711_;
        if (!$$11.m_41619_()) {
            int $$12 = 8;
            int $$13 = this.f_97711_.m_41619_() ? 8 : 16;
            String $$14 = null;
            if (!this.f_97711_.m_41619_() && this.f_97710_) {
                $$11 = $$11.m_41777_();
                $$11.m_41764_(Mth.m_14167_((float)$$11.m_41613_() / 2.0f));
            } else if (this.f_97738_ && this.f_97737_.size() > 1) {
                $$11 = $$11.m_41777_();
                $$11.m_41764_(this.f_97720_);
                if ($$11.m_41619_()) {
                    $$14 = ChatFormatting.YELLOW + "0";
                }
            }
            this.m_97782_($$11, p_97796_ - $$4 - 8, p_97797_ - $$5 - $$13, $$14);
        }
        if (!this.f_97715_.m_41619_()) {
            float $$15 = (float)(Util.m_137550_() - this.f_97714_) / 100.0f;
            if ($$15 >= 1.0f) {
                $$15 = 1.0f;
                this.f_97715_ = ItemStack.f_41583_;
            }
            int $$16 = this.f_97707_.f_40220_ - this.f_97712_;
            int $$17 = this.f_97707_.f_40221_ - this.f_97713_;
            int $$18 = this.f_97712_ + (int)((float)$$16 * $$15);
            int $$19 = this.f_97713_ + (int)((float)$$17 * $$15);
            this.m_97782_(this.f_97715_, $$18, $$19, null);
        }
        $$6.m_85849_();
        RenderSystem.m_157182_();
        RenderSystem.m_69482_();
    }

    public static void m_169606_(PoseStack p_169607_, int p_169608_, int p_169609_, int p_169610_) {
        RenderSystem.m_69465_();
        RenderSystem.m_69444_(true, true, true, false);
        AbstractContainerScreen.m_168740_(p_169607_, p_169608_, p_169609_, p_169608_ + 16, p_169609_ + 16, -2130706433, -2130706433, p_169610_);
        RenderSystem.m_69444_(true, true, true, true);
        RenderSystem.m_69482_();
    }

    protected void m_7025_(PoseStack p_97791_, int p_97792_, int p_97793_) {
        if (((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_() && this.f_97734_ != null && this.f_97734_.m_6657_()) {
            this.m_6057_(p_97791_, this.f_97734_.m_7993_(), p_97792_, p_97793_);
        }
    }

    private void m_97782_(ItemStack p_97783_, int p_97784_, int p_97785_, String p_97786_) {
        PoseStack $$4 = RenderSystem.m_157191_();
        $$4.m_85837_(0.0, 0.0, 32.0);
        RenderSystem.m_157182_();
        this.m_93250_(200);
        this.f_96542_.f_115093_ = 200.0f;
        this.f_96542_.m_115203_(p_97783_, p_97784_, p_97785_);
        this.f_96542_.m_115174_(this.f_96547_, p_97783_, p_97784_, p_97785_ - (this.f_97711_.m_41619_() ? 0 : 8), p_97786_);
        this.m_93250_(0);
        this.f_96542_.f_115093_ = 0.0f;
    }

    protected void m_7027_(PoseStack p_97808_, int p_97809_, int p_97810_) {
        this.f_96547_.m_92889_(p_97808_, this.f_96539_, this.f_97728_, this.f_97729_, 0x404040);
        this.f_96547_.m_92889_(p_97808_, this.f_169604_, this.f_97730_, this.f_97731_, 0x404040);
    }

    protected abstract void m_7286_(PoseStack var1, float var2, int var3, int var4);

    private void m_97799_(PoseStack p_97800_, Slot p_97801_) {
        Pair<ResourceLocation, ResourceLocation> $$10;
        int $$2 = p_97801_.f_40220_;
        int $$3 = p_97801_.f_40221_;
        ItemStack $$4 = p_97801_.m_7993_();
        boolean $$5 = false;
        boolean $$6 = p_97801_ == this.f_97706_ && !this.f_97711_.m_41619_() && !this.f_97710_;
        ItemStack $$7 = ((AbstractContainerMenu)this.f_97732_).m_142621_();
        String $$8 = null;
        if (p_97801_ == this.f_97706_ && !this.f_97711_.m_41619_() && this.f_97710_ && !$$4.m_41619_()) {
            $$4 = $$4.m_41777_();
            $$4.m_41764_($$4.m_41613_() / 2);
        } else if (this.f_97738_ && this.f_97737_.contains(p_97801_) && !$$7.m_41619_()) {
            if (this.f_97737_.size() == 1) {
                return;
            }
            if (AbstractContainerMenu.m_38899_(p_97801_, $$7, true) && ((AbstractContainerMenu)this.f_97732_).m_5622_(p_97801_)) {
                $$4 = $$7.m_41777_();
                $$5 = true;
                AbstractContainerMenu.m_38922_(this.f_97737_, this.f_97717_, $$4, p_97801_.m_7993_().m_41619_() ? 0 : p_97801_.m_7993_().m_41613_());
                int $$9 = Math.min($$4.m_41741_(), p_97801_.m_5866_($$4));
                if ($$4.m_41613_() > $$9) {
                    $$8 = ChatFormatting.YELLOW.toString() + $$9;
                    $$4.m_41764_($$9);
                }
            } else {
                this.f_97737_.remove(p_97801_);
                this.m_97818_();
            }
        }
        this.m_93250_(100);
        this.f_96542_.f_115093_ = 100.0f;
        if ($$4.m_41619_() && p_97801_.m_6659_() && ($$10 = p_97801_.m_7543_()) != null) {
            TextureAtlasSprite $$11 = this.f_96541_.m_91258_((ResourceLocation)$$10.getFirst()).apply((ResourceLocation)$$10.getSecond());
            RenderSystem.m_157456_(0, $$11.m_118414_().m_118330_());
            AbstractContainerScreen.m_93200_(p_97800_, $$2, $$3, this.m_93252_(), 16, 16, $$11);
            $$6 = true;
        }
        if (!$$6) {
            if ($$5) {
                AbstractContainerScreen.m_93172_(p_97800_, $$2, $$3, $$2 + 16, $$3 + 16, -2130706433);
            }
            RenderSystem.m_69482_();
            this.f_96542_.m_174229_(this.f_96541_.f_91074_, $$4, $$2, $$3, p_97801_.f_40220_ + p_97801_.f_40221_ * this.f_97726_);
            this.f_96542_.m_115174_(this.f_96547_, $$4, $$2, $$3, $$8);
        }
        this.f_96542_.f_115093_ = 0.0f;
        this.m_93250_(0);
    }

    private void m_97818_() {
        ItemStack $$0 = ((AbstractContainerMenu)this.f_97732_).m_142621_();
        if ($$0.m_41619_() || !this.f_97738_) {
            return;
        }
        if (this.f_97717_ == 2) {
            this.f_97720_ = $$0.m_41741_();
            return;
        }
        this.f_97720_ = $$0.m_41613_();
        for (Slot $$1 : this.f_97737_) {
            ItemStack $$2 = $$0.m_41777_();
            ItemStack $$3 = $$1.m_7993_();
            int $$4 = $$3.m_41619_() ? 0 : $$3.m_41613_();
            AbstractContainerMenu.m_38922_(this.f_97737_, this.f_97717_, $$2, $$4);
            int $$5 = Math.min($$2.m_41741_(), $$1.m_5866_($$2));
            if ($$2.m_41613_() > $$5) {
                $$2.m_41764_($$5);
            }
            this.f_97720_ -= $$2.m_41613_() - $$4;
        }
    }

    @Nullable
    private Slot m_97744_(double p_97745_, double p_97746_) {
        for (int $$2 = 0; $$2 < ((AbstractContainerMenu)this.f_97732_).f_38839_.size(); ++$$2) {
            Slot $$3 = ((AbstractContainerMenu)this.f_97732_).f_38839_.get($$2);
            if (!this.m_97774_($$3, p_97745_, p_97746_) || !$$3.m_6659_()) continue;
            return $$3;
        }
        return null;
    }

    @Override
    public boolean m_6375_(double p_97748_, double p_97749_, int p_97750_) {
        if (super.m_6375_(p_97748_, p_97749_, p_97750_)) {
            return true;
        }
        boolean $$3 = this.f_96541_.f_91066_.f_92097_.m_90830_(p_97750_) && this.f_96541_.f_91072_.m_105290_();
        Slot $$4 = this.m_97744_(p_97748_, p_97749_);
        long $$5 = Util.m_137550_();
        this.f_97723_ = this.f_97709_ == $$4 && $$5 - this.f_97721_ < 250L && this.f_97722_ == p_97750_;
        this.f_97719_ = false;
        if (p_97750_ == 0 || p_97750_ == 1 || $$3) {
            int $$6 = this.f_97735_;
            int $$7 = this.f_97736_;
            boolean $$8 = this.m_7467_(p_97748_, p_97749_, $$6, $$7, p_97750_);
            int $$9 = -1;
            if ($$4 != null) {
                $$9 = $$4.f_40219_;
            }
            if ($$8) {
                $$9 = -999;
            }
            if (this.f_96541_.f_91066_.m_231828_().m_231551_().booleanValue() && $$8 && ((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
                this.m_7379_();
                return true;
            }
            if ($$9 != -1) {
                if (this.f_96541_.f_91066_.m_231828_().m_231551_().booleanValue()) {
                    if ($$4 != null && $$4.m_6657_()) {
                        this.f_97706_ = $$4;
                        this.f_97711_ = ItemStack.f_41583_;
                        this.f_97710_ = p_97750_ == 1;
                    } else {
                        this.f_97706_ = null;
                    }
                } else if (!this.f_97738_) {
                    if (((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
                        if ($$3) {
                            this.m_6597_($$4, $$9, p_97750_, ClickType.CLONE);
                        } else {
                            boolean $$10 = $$9 != -999 && (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344));
                            ClickType $$11 = ClickType.PICKUP;
                            if ($$10) {
                                this.f_97724_ = $$4 != null && $$4.m_6657_() ? $$4.m_7993_().m_41777_() : ItemStack.f_41583_;
                                $$11 = ClickType.QUICK_MOVE;
                            } else if ($$9 == -999) {
                                $$11 = ClickType.THROW;
                            }
                            this.m_6597_($$4, $$9, p_97750_, $$11);
                        }
                        this.f_97719_ = true;
                    } else {
                        this.f_97738_ = true;
                        this.f_97718_ = p_97750_;
                        this.f_97737_.clear();
                        if (p_97750_ == 0) {
                            this.f_97717_ = 0;
                        } else if (p_97750_ == 1) {
                            this.f_97717_ = 1;
                        } else if ($$3) {
                            this.f_97717_ = 2;
                        }
                    }
                }
            }
        } else {
            this.m_97762_(p_97750_);
        }
        this.f_97709_ = $$4;
        this.f_97721_ = $$5;
        this.f_97722_ = p_97750_;
        return true;
    }

    private void m_97762_(int p_97763_) {
        if (this.f_97734_ != null && ((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
            if (this.f_96541_.f_91066_.f_92093_.m_90830_(p_97763_)) {
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 40, ClickType.SWAP);
                return;
            }
            for (int $$1 = 0; $$1 < 9; ++$$1) {
                if (!this.f_96541_.f_91066_.f_92056_[$$1].m_90830_(p_97763_)) continue;
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, $$1, ClickType.SWAP);
            }
        }
    }

    protected boolean m_7467_(double p_97757_, double p_97758_, int p_97759_, int p_97760_, int p_97761_) {
        return p_97757_ < (double)p_97759_ || p_97758_ < (double)p_97760_ || p_97757_ >= (double)(p_97759_ + this.f_97726_) || p_97758_ >= (double)(p_97760_ + this.f_97727_);
    }

    @Override
    public boolean m_7979_(double p_97752_, double p_97753_, int p_97754_, double p_97755_, double p_97756_) {
        Slot $$5 = this.m_97744_(p_97752_, p_97753_);
        ItemStack $$6 = ((AbstractContainerMenu)this.f_97732_).m_142621_();
        if (this.f_97706_ != null && this.f_96541_.f_91066_.m_231828_().m_231551_().booleanValue()) {
            if (p_97754_ == 0 || p_97754_ == 1) {
                if (this.f_97711_.m_41619_()) {
                    if ($$5 != this.f_97706_ && !this.f_97706_.m_7993_().m_41619_()) {
                        this.f_97711_ = this.f_97706_.m_7993_().m_41777_();
                    }
                } else if (this.f_97711_.m_41613_() > 1 && $$5 != null && AbstractContainerMenu.m_38899_($$5, this.f_97711_, false)) {
                    long $$7 = Util.m_137550_();
                    if (this.f_97708_ == $$5) {
                        if ($$7 - this.f_97716_ > 500L) {
                            this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, 0, ClickType.PICKUP);
                            this.m_6597_($$5, $$5.f_40219_, 1, ClickType.PICKUP);
                            this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, 0, ClickType.PICKUP);
                            this.f_97716_ = $$7 + 750L;
                            this.f_97711_.m_41774_(1);
                        }
                    } else {
                        this.f_97708_ = $$5;
                        this.f_97716_ = $$7;
                    }
                }
            }
        } else if (this.f_97738_ && $$5 != null && !$$6.m_41619_() && ($$6.m_41613_() > this.f_97737_.size() || this.f_97717_ == 2) && AbstractContainerMenu.m_38899_($$5, $$6, true) && $$5.m_5857_($$6) && ((AbstractContainerMenu)this.f_97732_).m_5622_($$5)) {
            this.f_97737_.add($$5);
            this.m_97818_();
        }
        return true;
    }

    @Override
    public boolean m_6348_(double p_97812_, double p_97813_, int p_97814_) {
        Slot $$3 = this.m_97744_(p_97812_, p_97813_);
        int $$4 = this.f_97735_;
        int $$5 = this.f_97736_;
        boolean $$6 = this.m_7467_(p_97812_, p_97813_, $$4, $$5, p_97814_);
        int $$7 = -1;
        if ($$3 != null) {
            $$7 = $$3.f_40219_;
        }
        if ($$6) {
            $$7 = -999;
        }
        if (this.f_97723_ && $$3 != null && p_97814_ == 0 && ((AbstractContainerMenu)this.f_97732_).m_5882_(ItemStack.f_41583_, $$3)) {
            if (AbstractContainerScreen.m_96638_()) {
                if (!this.f_97724_.m_41619_()) {
                    for (Slot $$8 : ((AbstractContainerMenu)this.f_97732_).f_38839_) {
                        if ($$8 == null || !$$8.m_8010_(this.f_96541_.f_91074_) || !$$8.m_6657_() || $$8.f_40218_ != $$3.f_40218_ || !AbstractContainerMenu.m_38899_($$8, this.f_97724_, true)) continue;
                        this.m_6597_($$8, $$8.f_40219_, p_97814_, ClickType.QUICK_MOVE);
                    }
                }
            } else {
                this.m_6597_($$3, $$7, p_97814_, ClickType.PICKUP_ALL);
            }
            this.f_97723_ = false;
            this.f_97721_ = 0L;
        } else {
            if (this.f_97738_ && this.f_97718_ != p_97814_) {
                this.f_97738_ = false;
                this.f_97737_.clear();
                this.f_97719_ = true;
                return true;
            }
            if (this.f_97719_) {
                this.f_97719_ = false;
                return true;
            }
            if (this.f_97706_ != null && this.f_96541_.f_91066_.m_231828_().m_231551_().booleanValue()) {
                if (p_97814_ == 0 || p_97814_ == 1) {
                    if (this.f_97711_.m_41619_() && $$3 != this.f_97706_) {
                        this.f_97711_ = this.f_97706_.m_7993_();
                    }
                    boolean $$9 = AbstractContainerMenu.m_38899_($$3, this.f_97711_, false);
                    if ($$7 != -1 && !this.f_97711_.m_41619_() && $$9) {
                        this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, p_97814_, ClickType.PICKUP);
                        this.m_6597_($$3, $$7, 0, ClickType.PICKUP);
                        if (((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
                            this.f_97715_ = ItemStack.f_41583_;
                        } else {
                            this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, p_97814_, ClickType.PICKUP);
                            this.f_97712_ = Mth.m_14107_(p_97812_ - (double)$$4);
                            this.f_97713_ = Mth.m_14107_(p_97813_ - (double)$$5);
                            this.f_97707_ = this.f_97706_;
                            this.f_97715_ = this.f_97711_;
                            this.f_97714_ = Util.m_137550_();
                        }
                    } else if (!this.f_97711_.m_41619_()) {
                        this.f_97712_ = Mth.m_14107_(p_97812_ - (double)$$4);
                        this.f_97713_ = Mth.m_14107_(p_97813_ - (double)$$5);
                        this.f_97707_ = this.f_97706_;
                        this.f_97715_ = this.f_97711_;
                        this.f_97714_ = Util.m_137550_();
                    }
                    this.m_238391_();
                }
            } else if (this.f_97738_ && !this.f_97737_.isEmpty()) {
                this.m_6597_(null, -999, AbstractContainerMenu.m_38930_(0, this.f_97717_), ClickType.QUICK_CRAFT);
                for (Slot $$10 : this.f_97737_) {
                    this.m_6597_($$10, $$10.f_40219_, AbstractContainerMenu.m_38930_(1, this.f_97717_), ClickType.QUICK_CRAFT);
                }
                this.m_6597_(null, -999, AbstractContainerMenu.m_38930_(2, this.f_97717_), ClickType.QUICK_CRAFT);
            } else if (!((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
                if (this.f_96541_.f_91066_.f_92097_.m_90830_(p_97814_)) {
                    this.m_6597_($$3, $$7, p_97814_, ClickType.CLONE);
                } else {
                    boolean $$11;
                    boolean bl = $$11 = $$7 != -999 && (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344));
                    if ($$11) {
                        this.f_97724_ = $$3 != null && $$3.m_6657_() ? $$3.m_7993_().m_41777_() : ItemStack.f_41583_;
                    }
                    this.m_6597_($$3, $$7, p_97814_, $$11 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
                }
            }
        }
        if (((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_()) {
            this.f_97721_ = 0L;
        }
        this.f_97738_ = false;
        return true;
    }

    public void m_238391_() {
        this.f_97711_ = ItemStack.f_41583_;
        this.f_97706_ = null;
    }

    private boolean m_97774_(Slot p_97775_, double p_97776_, double p_97777_) {
        return this.m_6774_(p_97775_.f_40220_, p_97775_.f_40221_, 16, 16, p_97776_, p_97777_);
    }

    protected boolean m_6774_(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
        int $$6 = this.f_97735_;
        int $$7 = this.f_97736_;
        return (p_97772_ -= (double)$$6) >= (double)(p_97768_ - 1) && p_97772_ < (double)(p_97768_ + p_97770_ + 1) && (p_97773_ -= (double)$$7) >= (double)(p_97769_ - 1) && p_97773_ < (double)(p_97769_ + p_97771_ + 1);
    }

    protected void m_6597_(Slot p_97778_, int p_97779_, int p_97780_, ClickType p_97781_) {
        if (p_97778_ != null) {
            p_97779_ = p_97778_.f_40219_;
        }
        this.f_96541_.f_91072_.m_171799_(((AbstractContainerMenu)this.f_97732_).f_38840_, p_97779_, p_97780_, p_97781_, this.f_96541_.f_91074_);
    }

    @Override
    public boolean m_7933_(int p_97765_, int p_97766_, int p_97767_) {
        if (super.m_7933_(p_97765_, p_97766_, p_97767_)) {
            return true;
        }
        if (this.f_96541_.f_91066_.f_92092_.m_90832_(p_97765_, p_97766_)) {
            this.m_7379_();
            return true;
        }
        this.m_97805_(p_97765_, p_97766_);
        if (this.f_97734_ != null && this.f_97734_.m_6657_()) {
            if (this.f_96541_.f_91066_.f_92097_.m_90832_(p_97765_, p_97766_)) {
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 0, ClickType.CLONE);
            } else if (this.f_96541_.f_91066_.f_92094_.m_90832_(p_97765_, p_97766_)) {
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, AbstractContainerScreen.m_96637_() ? 1 : 0, ClickType.THROW);
            }
        }
        return true;
    }

    protected boolean m_97805_(int p_97806_, int p_97807_) {
        if (((AbstractContainerMenu)this.f_97732_).m_142621_().m_41619_() && this.f_97734_ != null) {
            if (this.f_96541_.f_91066_.f_92093_.m_90832_(p_97806_, p_97807_)) {
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 40, ClickType.SWAP);
                return true;
            }
            for (int $$2 = 0; $$2 < 9; ++$$2) {
                if (!this.f_96541_.f_91066_.f_92056_[$$2].m_90832_(p_97806_, p_97807_)) continue;
                this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, $$2, ClickType.SWAP);
                return true;
            }
        }
        return false;
    }

    @Override
    public void m_7861_() {
        if (this.f_96541_.f_91074_ == null) {
            return;
        }
        ((AbstractContainerMenu)this.f_97732_).m_6877_(this.f_96541_.f_91074_);
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    @Override
    public final void m_86600_() {
        super.m_86600_();
        if (!this.f_96541_.f_91074_.m_6084_() || this.f_96541_.f_91074_.m_213877_()) {
            this.f_96541_.f_91074_.m_6915_();
        } else {
            this.m_181908_();
        }
    }

    protected void m_181908_() {
    }

    @Override
    public T m_6262_() {
        return this.f_97732_;
    }

    @Override
    public void m_7379_() {
        this.f_96541_.f_91074_.m_6915_();
        super.m_7379_();
    }
}

