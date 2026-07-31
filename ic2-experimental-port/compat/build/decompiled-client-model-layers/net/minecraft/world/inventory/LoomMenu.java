/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.world.inventory;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LoomMenu
extends AbstractContainerMenu {
    private static final int f_219989_ = -1;
    private static final int f_150612_ = 4;
    private static final int f_150613_ = 31;
    private static final int f_150614_ = 31;
    private static final int f_150615_ = 40;
    private final ContainerLevelAccess f_39845_;
    final DataSlot f_39846_ = DataSlot.m_39401_();
    private List<Holder<BannerPattern>> f_219990_ = List.of();
    Runnable f_39847_ = () -> {};
    final Slot f_39848_;
    final Slot f_39849_;
    private final Slot f_39850_;
    private final Slot f_39851_;
    long f_39852_;
    private final Container f_39853_ = new SimpleContainer(3){

        @Override
        public void m_6596_() {
            super.m_6596_();
            LoomMenu.this.m_6199_(this);
            LoomMenu.this.f_39847_.run();
        }
    };
    private final Container f_39854_ = new SimpleContainer(1){

        @Override
        public void m_6596_() {
            super.m_6596_();
            LoomMenu.this.f_39847_.run();
        }
    };

    public LoomMenu(int p_39856_, Inventory p_39857_) {
        this(p_39856_, p_39857_, ContainerLevelAccess.f_39287_);
    }

    public LoomMenu(int p_39859_, Inventory p_39860_, final ContainerLevelAccess p_39861_) {
        super(MenuType.f_39974_, p_39859_);
        this.f_39845_ = p_39861_;
        this.f_39848_ = this.m_38897_(new Slot(this.f_39853_, 0, 13, 26){

            @Override
            public boolean m_5857_(ItemStack p_39918_) {
                return p_39918_.m_41720_() instanceof BannerItem;
            }
        });
        this.f_39849_ = this.m_38897_(new Slot(this.f_39853_, 1, 33, 26){

            @Override
            public boolean m_5857_(ItemStack p_39927_) {
                return p_39927_.m_41720_() instanceof DyeItem;
            }
        });
        this.f_39850_ = this.m_38897_(new Slot(this.f_39853_, 2, 23, 45){

            @Override
            public boolean m_5857_(ItemStack p_39936_) {
                return p_39936_.m_41720_() instanceof BannerPatternItem;
            }
        });
        this.f_39851_ = this.m_38897_(new Slot(this.f_39854_, 0, 143, 58){

            @Override
            public boolean m_5857_(ItemStack p_39950_) {
                return false;
            }

            @Override
            public void m_142406_(Player p_150617_, ItemStack p_150618_) {
                LoomMenu.this.f_39848_.m_6201_(1);
                LoomMenu.this.f_39849_.m_6201_(1);
                if (!LoomMenu.this.f_39848_.m_6657_() || !LoomMenu.this.f_39849_.m_6657_()) {
                    LoomMenu.this.f_39846_.m_6422_(-1);
                }
                p_39861_.m_39292_((p_39952_, p_39953_) -> {
                    long $$2 = p_39952_.m_46467_();
                    if (LoomMenu.this.f_39852_ != $$2) {
                        p_39952_.m_5594_(null, (BlockPos)p_39953_, SoundEvents.f_12492_, SoundSource.BLOCKS, 1.0f, 1.0f);
                        LoomMenu.this.f_39852_ = $$2;
                    }
                });
                super.m_142406_(p_150617_, p_150618_);
            }
        });
        for (int $$3 = 0; $$3 < 3; ++$$3) {
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot(p_39860_, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 9; ++$$5) {
            this.m_38897_(new Slot(p_39860_, $$5, 8 + $$5 * 18, 142));
        }
        this.m_38895_(this.f_39846_);
    }

    @Override
    public boolean m_6875_(Player p_39865_) {
        return LoomMenu.m_38889_(this.f_39845_, p_39865_, Blocks.f_50617_);
    }

    @Override
    public boolean m_6366_(Player p_39867_, int p_39868_) {
        if (p_39868_ >= 0 && p_39868_ < this.f_219990_.size()) {
            this.f_39846_.m_6422_(p_39868_);
            this.m_219991_(this.f_219990_.get(p_39868_));
            return true;
        }
        return false;
    }

    private List<Holder<BannerPattern>> m_219993_(ItemStack p_219994_) {
        if (p_219994_.m_41619_()) {
            return (List)Registry.f_235736_.m_203431_(BannerPatternTags.f_215788_).map(ImmutableList::copyOf).orElse(ImmutableList.of());
        }
        Item item = p_219994_.m_41720_();
        if (item instanceof BannerPatternItem) {
            BannerPatternItem $$1 = (BannerPatternItem)item;
            return (List)Registry.f_235736_.m_203431_($$1.m_220010_()).map(ImmutableList::copyOf).orElse(ImmutableList.of());
        }
        return List.of();
    }

    private boolean m_242642_(int p_242850_) {
        return p_242850_ >= 0 && p_242850_ < this.f_219990_.size();
    }

    @Override
    public void m_6199_(Container p_39863_) {
        Holder<BannerPattern> $$12;
        ItemStack $$1 = this.f_39848_.m_7993_();
        ItemStack $$2 = this.f_39849_.m_7993_();
        ItemStack $$3 = this.f_39850_.m_7993_();
        if ($$1.m_41619_() || $$2.m_41619_()) {
            this.f_39851_.m_5852_(ItemStack.f_41583_);
            this.f_219990_ = List.of();
            this.f_39846_.m_6422_(-1);
            return;
        }
        int $$4 = this.f_39846_.m_6501_();
        boolean $$5 = this.m_242642_($$4);
        List<Holder<BannerPattern>> $$6 = this.f_219990_;
        this.f_219990_ = this.m_219993_($$3);
        if (this.f_219990_.size() == 1) {
            this.f_39846_.m_6422_(0);
            Holder<BannerPattern> $$7 = this.f_219990_.get(0);
        } else if (!$$5) {
            this.f_39846_.m_6422_(-1);
            Object $$8 = null;
        } else {
            Holder<BannerPattern> $$9 = $$6.get($$4);
            int $$10 = this.f_219990_.indexOf($$9);
            if ($$10 != -1) {
                Holder<BannerPattern> $$11 = $$9;
                this.f_39846_.m_6422_($$10);
            } else {
                $$12 = null;
                this.f_39846_.m_6422_(-1);
            }
        }
        if ($$12 != null) {
            boolean $$14;
            CompoundTag $$13 = BlockItem.m_186336_($$1);
            boolean bl = $$14 = $$13 != null && $$13.m_128425_("Patterns", 9) && !$$1.m_41619_() && $$13.m_128437_("Patterns", 10).size() >= 6;
            if ($$14) {
                this.f_39846_.m_6422_(-1);
                this.f_39851_.m_5852_(ItemStack.f_41583_);
            } else {
                this.m_219991_($$12);
            }
        } else {
            this.f_39851_.m_5852_(ItemStack.f_41583_);
        }
        this.m_38946_();
    }

    public List<Holder<BannerPattern>> m_219995_() {
        return this.f_219990_;
    }

    public int m_39891_() {
        return this.f_39846_.m_6501_();
    }

    public void m_39878_(Runnable p_39879_) {
        this.f_39847_ = p_39879_;
    }

    @Override
    public ItemStack m_7648_(Player p_39883_, int p_39884_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39884_);
        if ($$3 != null && $$3.m_6657_()) {
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            if (p_39884_ == this.f_39851_.f_40219_) {
                if (!this.m_38903_($$4, 4, 40, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39884_ == this.f_39849_.f_40219_ || p_39884_ == this.f_39848_.f_40219_ || p_39884_ == this.f_39850_.f_40219_ ? !this.m_38903_($$4, 4, 40, false) : ($$4.m_41720_() instanceof BannerItem ? !this.m_38903_($$4, this.f_39848_.f_40219_, this.f_39848_.f_40219_ + 1, false) : ($$4.m_41720_() instanceof DyeItem ? !this.m_38903_($$4, this.f_39849_.f_40219_, this.f_39849_.f_40219_ + 1, false) : ($$4.m_41720_() instanceof BannerPatternItem ? !this.m_38903_($$4, this.f_39850_.f_40219_, this.f_39850_.f_40219_ + 1, false) : (p_39884_ >= 4 && p_39884_ < 31 ? !this.m_38903_($$4, 31, 40, false) : p_39884_ >= 31 && p_39884_ < 40 && !this.m_38903_($$4, 4, 31, false)))))) {
                return ItemStack.f_41583_;
            }
            if ($$4.m_41619_()) {
                $$3.m_5852_(ItemStack.f_41583_);
            } else {
                $$3.m_6654_();
            }
            if ($$4.m_41613_() == $$2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            $$3.m_142406_(p_39883_, $$4);
        }
        return $$2;
    }

    @Override
    public void m_6877_(Player p_39881_) {
        super.m_6877_(p_39881_);
        this.f_39845_.m_39292_((p_39871_, p_39872_) -> this.m_150411_(p_39881_, this.f_39853_));
    }

    private void m_219991_(Holder<BannerPattern> p_219992_) {
        ItemStack $$1 = this.f_39848_.m_7993_();
        ItemStack $$2 = this.f_39849_.m_7993_();
        ItemStack $$3 = ItemStack.f_41583_;
        if (!$$1.m_41619_() && !$$2.m_41619_()) {
            ListTag $$7;
            $$3 = $$1.m_41777_();
            $$3.m_41764_(1);
            DyeColor $$4 = ((DyeItem)$$2.m_41720_()).m_41089_();
            CompoundTag $$5 = BlockItem.m_186336_($$3);
            if ($$5 != null && $$5.m_128425_("Patterns", 9)) {
                ListTag $$6 = $$5.m_128437_("Patterns", 10);
            } else {
                $$7 = new ListTag();
                if ($$5 == null) {
                    $$5 = new CompoundTag();
                }
                $$5.m_128365_("Patterns", $$7);
            }
            CompoundTag $$8 = new CompoundTag();
            $$8.m_128359_("Pattern", p_219992_.m_203334_().m_58579_());
            $$8.m_128405_("Color", $$4.m_41060_());
            $$7.add($$8);
            BlockItem.m_186338_($$3, BlockEntityType.f_58935_, $$5);
        }
        if (!ItemStack.m_41728_($$3, this.f_39851_.m_7993_())) {
            this.f_39851_.m_5852_($$3);
        }
    }

    public Slot m_39894_() {
        return this.f_39848_;
    }

    public Slot m_39895_() {
        return this.f_39849_;
    }

    public Slot m_39896_() {
        return this.f_39850_;
    }

    public Slot m_39897_() {
        return this.f_39851_;
    }
}

