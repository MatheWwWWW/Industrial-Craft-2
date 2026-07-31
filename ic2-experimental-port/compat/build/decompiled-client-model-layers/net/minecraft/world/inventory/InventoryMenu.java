/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class InventoryMenu
extends RecipeBookMenu<CraftingContainer> {
    public static final int f_150579_ = 0;
    public static final int f_150580_ = 0;
    public static final int f_150581_ = 1;
    public static final int f_150582_ = 5;
    public static final int f_150583_ = 5;
    public static final int f_150584_ = 9;
    public static final int f_150585_ = 9;
    public static final int f_150586_ = 36;
    public static final int f_150587_ = 36;
    public static final int f_150588_ = 45;
    public static final int f_150589_ = 45;
    public static final ResourceLocation f_39692_ = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation f_39693_ = new ResourceLocation("item/empty_armor_slot_helmet");
    public static final ResourceLocation f_39694_ = new ResourceLocation("item/empty_armor_slot_chestplate");
    public static final ResourceLocation f_39695_ = new ResourceLocation("item/empty_armor_slot_leggings");
    public static final ResourceLocation f_39696_ = new ResourceLocation("item/empty_armor_slot_boots");
    public static final ResourceLocation f_39697_ = new ResourceLocation("item/empty_armor_slot_shield");
    static final ResourceLocation[] f_39699_ = new ResourceLocation[]{f_39696_, f_39695_, f_39694_, f_39693_};
    private static final EquipmentSlot[] f_39700_ = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final CraftingContainer f_39701_ = new CraftingContainer(this, 2, 2);
    private final ResultContainer f_39702_ = new ResultContainer();
    public final boolean f_39698_;
    private final Player f_39703_;

    public InventoryMenu(Inventory p_39706_, boolean p_39707_, final Player p_39708_) {
        super(null, 0);
        this.f_39698_ = p_39707_;
        this.f_39703_ = p_39708_;
        this.m_38897_(new ResultSlot(p_39706_.f_35978_, this.f_39701_, this.f_39702_, 0, 154, 28));
        for (int $$3 = 0; $$3 < 2; ++$$3) {
            for (int $$4 = 0; $$4 < 2; ++$$4) {
                this.m_38897_(new Slot(this.f_39701_, $$4 + $$3 * 2, 98 + $$4 * 18, 18 + $$3 * 18));
            }
        }
        for (int $$5 = 0; $$5 < 4; ++$$5) {
            final EquipmentSlot $$6 = f_39700_[$$5];
            this.m_38897_(new Slot(p_39706_, 39 - $$5, 8, 8 + $$5 * 18){

                @Override
                public void m_5852_(ItemStack p_219985_) {
                    ItemStack $$1 = this.m_7993_();
                    super.m_5852_(p_219985_);
                    p_39708_.m_238392_($$6, $$1, p_219985_);
                }

                @Override
                public int m_6641_() {
                    return 1;
                }

                @Override
                public boolean m_5857_(ItemStack p_39746_) {
                    return $$6 == Mob.m_147233_(p_39746_);
                }

                @Override
                public boolean m_8010_(Player p_39744_) {
                    ItemStack $$1 = this.m_7993_();
                    if (!$$1.m_41619_() && !p_39744_.m_7500_() && EnchantmentHelper.m_44920_($$1)) {
                        return false;
                    }
                    return super.m_8010_(p_39744_);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> m_7543_() {
                    return Pair.of((Object)f_39692_, (Object)f_39699_[$$6.m_20749_()]);
                }
            });
        }
        for (int $$7 = 0; $$7 < 3; ++$$7) {
            for (int $$8 = 0; $$8 < 9; ++$$8) {
                this.m_38897_(new Slot(p_39706_, $$8 + ($$7 + 1) * 9, 8 + $$8 * 18, 84 + $$7 * 18));
            }
        }
        for (int $$9 = 0; $$9 < 9; ++$$9) {
            this.m_38897_(new Slot(p_39706_, $$9, 8 + $$9 * 18, 142));
        }
        this.m_38897_(new Slot(p_39706_, 40, 77, 62){

            @Override
            public Pair<ResourceLocation, ResourceLocation> m_7543_() {
                return Pair.of((Object)f_39692_, (Object)f_39697_);
            }
        });
    }

    public static boolean m_150592_(int p_150593_) {
        return p_150593_ >= 36 && p_150593_ < 45 || p_150593_ == 45;
    }

    @Override
    public void m_5816_(StackedContents p_39714_) {
        this.f_39701_.m_5809_(p_39714_);
    }

    @Override
    public void m_6650_() {
        this.f_39702_.m_6211_();
        this.f_39701_.m_6211_();
    }

    @Override
    public boolean m_6032_(Recipe<? super CraftingContainer> p_39719_) {
        return p_39719_.m_5818_(this.f_39701_, this.f_39703_.f_19853_);
    }

    @Override
    public void m_6199_(Container p_39710_) {
        CraftingMenu.m_150546_(this, this.f_39703_.f_19853_, this.f_39703_, this.f_39701_, this.f_39702_);
    }

    @Override
    public void m_6877_(Player p_39721_) {
        super.m_6877_(p_39721_);
        this.f_39702_.m_6211_();
        if (p_39721_.f_19853_.f_46443_) {
            return;
        }
        this.m_150411_(p_39721_, this.f_39701_);
    }

    @Override
    public boolean m_6875_(Player p_39712_) {
        return true;
    }

    @Override
    public ItemStack m_7648_(Player p_39723_, int p_39724_) {
        ItemStack $$2 = ItemStack.f_41583_;
        Slot $$3 = (Slot)this.f_38839_.get(p_39724_);
        if ($$3 != null && $$3.m_6657_()) {
            int $$6;
            ItemStack $$4 = $$3.m_7993_();
            $$2 = $$4.m_41777_();
            EquipmentSlot $$5 = Mob.m_147233_($$2);
            if (p_39724_ == 0) {
                if (!this.m_38903_($$4, 9, 45, true)) {
                    return ItemStack.f_41583_;
                }
                $$3.m_40234_($$4, $$2);
            } else if (p_39724_ >= 1 && p_39724_ < 5 ? !this.m_38903_($$4, 9, 45, false) : (p_39724_ >= 5 && p_39724_ < 9 ? !this.m_38903_($$4, 9, 45, false) : ($$5.m_20743_() == EquipmentSlot.Type.ARMOR && !((Slot)this.f_38839_.get(8 - $$5.m_20749_())).m_6657_() ? !this.m_38903_($$4, $$6 = 8 - $$5.m_20749_(), $$6 + 1, false) : ($$5 == EquipmentSlot.OFFHAND && !((Slot)this.f_38839_.get(45)).m_6657_() ? !this.m_38903_($$4, 45, 46, false) : (p_39724_ >= 9 && p_39724_ < 36 ? !this.m_38903_($$4, 36, 45, false) : (p_39724_ >= 36 && p_39724_ < 45 ? !this.m_38903_($$4, 9, 36, false) : !this.m_38903_($$4, 9, 45, false))))))) {
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
            $$3.m_142406_(p_39723_, $$4);
            if (p_39724_ == 0) {
                p_39723_.m_36176_($$4, false);
            }
        }
        return $$2;
    }

    @Override
    public boolean m_5882_(ItemStack p_39716_, Slot p_39717_) {
        return p_39717_.f_40218_ != this.f_39702_ && super.m_5882_(p_39716_, p_39717_);
    }

    @Override
    public int m_6636_() {
        return 0;
    }

    @Override
    public int m_6635_() {
        return this.f_39701_.m_39347_();
    }

    @Override
    public int m_6656_() {
        return this.f_39701_.m_39346_();
    }

    @Override
    public int m_6653_() {
        return 5;
    }

    public CraftingContainer m_39730_() {
        return this.f_39701_;
    }

    @Override
    public RecipeBookType m_5867_() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean m_142157_(int p_150591_) {
        return p_150591_ != this.m_6636_();
    }
}

