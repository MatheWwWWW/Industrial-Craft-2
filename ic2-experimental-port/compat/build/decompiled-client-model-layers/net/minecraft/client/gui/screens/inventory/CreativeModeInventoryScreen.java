/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeInventoryListener;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class CreativeModeInventoryScreen
extends EffectRenderingInventoryScreen<ItemPickerMenu> {
    private static final ResourceLocation f_98504_ = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static final String f_169735_ = "textures/gui/container/creative_inventory/tab_";
    private static final String f_169736_ = "CustomCreativeLock";
    private static final int f_169737_ = 5;
    private static final int f_169738_ = 9;
    private static final int f_169739_ = 28;
    private static final int f_169740_ = 32;
    private static final int f_169741_ = 12;
    private static final int f_169742_ = 15;
    static final SimpleContainer f_98505_ = new SimpleContainer(45);
    private static final Component f_98506_ = Component.m_237115_("inventory.binSlot");
    private static final int f_169743_ = 0xFFFFFF;
    private static int f_98507_ = CreativeModeTab.f_40749_.m_40775_();
    private float f_98508_;
    private boolean f_98509_;
    private EditBox f_98510_;
    @Nullable
    private List<Slot> f_98511_;
    @Nullable
    private Slot f_98512_;
    private CreativeInventoryListener f_98513_;
    private boolean f_98514_;
    private boolean f_98515_;
    private final Set<TagKey<Item>> f_98516_ = new HashSet<TagKey<Item>>();

    public CreativeModeInventoryScreen(Player p_98519_) {
        super(new ItemPickerMenu(p_98519_), p_98519_.m_150109_(), CommonComponents.f_237098_);
        p_98519_.f_36096_ = this.f_97732_;
        this.f_96546_ = true;
        this.f_97727_ = 136;
        this.f_97726_ = 195;
    }

    @Override
    public void m_181908_() {
        super.m_181908_();
        if (!this.f_96541_.f_91072_.m_105290_()) {
            this.f_96541_.m_91152_(new InventoryScreen(this.f_96541_.f_91074_));
        } else if (this.f_98510_ != null) {
            this.f_98510_.m_94120_();
        }
    }

    @Override
    protected void m_6597_(@Nullable Slot p_98556_, int p_98557_, int p_98558_, ClickType p_98559_) {
        if (this.m_98553_(p_98556_)) {
            this.f_98510_.m_94201_();
            this.f_98510_.m_94208_(0);
        }
        boolean $$4 = p_98559_ == ClickType.QUICK_MOVE;
        ClickType clickType = p_98559_ = p_98557_ == -999 && p_98559_ == ClickType.PICKUP ? ClickType.THROW : p_98559_;
        if (p_98556_ != null || f_98507_ == CreativeModeTab.f_40761_.m_40775_() || p_98559_ == ClickType.QUICK_CRAFT) {
            if (p_98556_ != null && !p_98556_.m_8010_(this.f_96541_.f_91074_)) {
                return;
            }
            if (p_98556_ == this.f_98512_ && $$4) {
                for (int $$5 = 0; $$5 < this.f_96541_.f_91074_.f_36095_.m_38927_().size(); ++$$5) {
                    this.f_96541_.f_91072_.m_105241_(ItemStack.f_41583_, $$5);
                }
            } else if (f_98507_ == CreativeModeTab.f_40761_.m_40775_()) {
                if (p_98556_ == this.f_98512_) {
                    ((ItemPickerMenu)this.f_97732_).m_142503_(ItemStack.f_41583_);
                } else if (p_98559_ == ClickType.THROW && p_98556_ != null && p_98556_.m_6657_()) {
                    ItemStack $$6 = p_98556_.m_6201_(p_98558_ == 0 ? 1 : p_98556_.m_7993_().m_41741_());
                    ItemStack $$7 = p_98556_.m_7993_();
                    this.f_96541_.f_91074_.m_36176_($$6, true);
                    this.f_96541_.f_91072_.m_105239_($$6);
                    this.f_96541_.f_91072_.m_105241_($$7, ((SlotWrapper)p_98556_).f_98655_.f_40219_);
                } else if (p_98559_ == ClickType.THROW && !((ItemPickerMenu)this.f_97732_).m_142621_().m_41619_()) {
                    this.f_96541_.f_91074_.m_36176_(((ItemPickerMenu)this.f_97732_).m_142621_(), true);
                    this.f_96541_.f_91072_.m_105239_(((ItemPickerMenu)this.f_97732_).m_142621_());
                    ((ItemPickerMenu)this.f_97732_).m_142503_(ItemStack.f_41583_);
                } else {
                    this.f_96541_.f_91074_.f_36095_.m_150399_(p_98556_ == null ? p_98557_ : ((SlotWrapper)p_98556_).f_98655_.f_40219_, p_98558_, p_98559_, this.f_96541_.f_91074_);
                    this.f_96541_.f_91074_.f_36095_.m_38946_();
                }
            } else if (p_98559_ != ClickType.QUICK_CRAFT && p_98556_.f_40218_ == f_98505_) {
                ItemStack $$8 = ((ItemPickerMenu)this.f_97732_).m_142621_();
                ItemStack $$9 = p_98556_.m_7993_();
                if (p_98559_ == ClickType.SWAP) {
                    if (!$$9.m_41619_()) {
                        ItemStack $$10 = $$9.m_41777_();
                        $$10.m_41764_($$10.m_41741_());
                        this.f_96541_.f_91074_.m_150109_().m_6836_(p_98558_, $$10);
                        this.f_96541_.f_91074_.f_36095_.m_38946_();
                    }
                    return;
                }
                if (p_98559_ == ClickType.CLONE) {
                    if (((ItemPickerMenu)this.f_97732_).m_142621_().m_41619_() && p_98556_.m_6657_()) {
                        ItemStack $$11 = p_98556_.m_7993_().m_41777_();
                        $$11.m_41764_($$11.m_41741_());
                        ((ItemPickerMenu)this.f_97732_).m_142503_($$11);
                    }
                    return;
                }
                if (p_98559_ == ClickType.THROW) {
                    if (!$$9.m_41619_()) {
                        ItemStack $$12 = $$9.m_41777_();
                        $$12.m_41764_(p_98558_ == 0 ? 1 : $$12.m_41741_());
                        this.f_96541_.f_91074_.m_36176_($$12, true);
                        this.f_96541_.f_91072_.m_105239_($$12);
                    }
                    return;
                }
                if (!$$8.m_41619_() && !$$9.m_41619_() && $$8.m_41656_($$9) && ItemStack.m_41658_($$8, $$9)) {
                    if (p_98558_ == 0) {
                        if ($$4) {
                            $$8.m_41764_($$8.m_41741_());
                        } else if ($$8.m_41613_() < $$8.m_41741_()) {
                            $$8.m_41769_(1);
                        }
                    } else {
                        $$8.m_41774_(1);
                    }
                } else if ($$9.m_41619_() || !$$8.m_41619_()) {
                    if (p_98558_ == 0) {
                        ((ItemPickerMenu)this.f_97732_).m_142503_(ItemStack.f_41583_);
                    } else {
                        ((ItemPickerMenu)this.f_97732_).m_142621_().m_41774_(1);
                    }
                } else {
                    ((ItemPickerMenu)this.f_97732_).m_142503_($$9.m_41777_());
                    $$8 = ((ItemPickerMenu)this.f_97732_).m_142621_();
                    if ($$4) {
                        $$8.m_41764_($$8.m_41741_());
                    }
                }
            } else if (this.f_97732_ != null) {
                ItemStack $$13 = p_98556_ == null ? ItemStack.f_41583_ : ((ItemPickerMenu)this.f_97732_).m_38853_(p_98556_.f_40219_).m_7993_();
                ((ItemPickerMenu)this.f_97732_).m_150399_(p_98556_ == null ? p_98557_ : p_98556_.f_40219_, p_98558_, p_98559_, this.f_96541_.f_91074_);
                if (AbstractContainerMenu.m_38947_(p_98558_) == 2) {
                    for (int $$14 = 0; $$14 < 9; ++$$14) {
                        this.f_96541_.f_91072_.m_105241_(((ItemPickerMenu)this.f_97732_).m_38853_(45 + $$14).m_7993_(), 36 + $$14);
                    }
                } else if (p_98556_ != null) {
                    ItemStack $$15 = ((ItemPickerMenu)this.f_97732_).m_38853_(p_98556_.f_40219_).m_7993_();
                    this.f_96541_.f_91072_.m_105241_($$15, p_98556_.f_40219_ - ((ItemPickerMenu)this.f_97732_).f_38839_.size() + 9 + 36);
                    int $$16 = 45 + p_98558_;
                    if (p_98559_ == ClickType.SWAP) {
                        this.f_96541_.f_91072_.m_105241_($$13, $$16 - ((ItemPickerMenu)this.f_97732_).f_38839_.size() + 9 + 36);
                    } else if (p_98559_ == ClickType.THROW && !$$13.m_41619_()) {
                        ItemStack $$17 = $$13.m_41777_();
                        $$17.m_41764_(p_98558_ == 0 ? 1 : $$17.m_41741_());
                        this.f_96541_.f_91074_.m_36176_($$17, true);
                        this.f_96541_.f_91072_.m_105239_($$17);
                    }
                    this.f_96541_.f_91074_.f_36095_.m_38946_();
                }
            }
        } else if (!((ItemPickerMenu)this.f_97732_).m_142621_().m_41619_() && this.f_98515_) {
            if (p_98558_ == 0) {
                this.f_96541_.f_91074_.m_36176_(((ItemPickerMenu)this.f_97732_).m_142621_(), true);
                this.f_96541_.f_91072_.m_105239_(((ItemPickerMenu)this.f_97732_).m_142621_());
                ((ItemPickerMenu)this.f_97732_).m_142503_(ItemStack.f_41583_);
            }
            if (p_98558_ == 1) {
                ItemStack $$18 = ((ItemPickerMenu)this.f_97732_).m_142621_().m_41620_(1);
                this.f_96541_.f_91074_.m_36176_($$18, true);
                this.f_96541_.f_91072_.m_105239_($$18);
            }
        }
    }

    private boolean m_98553_(@Nullable Slot p_98554_) {
        return p_98554_ != null && p_98554_.f_40218_ == f_98505_;
    }

    @Override
    protected void m_7856_() {
        if (this.f_96541_.f_91072_.m_105290_()) {
            super.m_7856_();
            this.f_96541_.f_91068_.m_90926_(true);
            this.f_98510_ = new EditBox(this.f_96547_, this.f_97735_ + 82, this.f_97736_ + 6, 80, this.f_96547_.f_92710_, Component.m_237115_("itemGroup.search"));
            this.f_98510_.m_94199_(50);
            this.f_98510_.m_94182_(false);
            this.f_98510_.m_94194_(false);
            this.f_98510_.m_94202_(0xFFFFFF);
            this.m_7787_(this.f_98510_);
            int $$0 = f_98507_;
            f_98507_ = -1;
            this.m_98560_(CreativeModeTab.f_40748_[$$0]);
            this.f_96541_.f_91074_.f_36095_.m_38943_(this.f_98513_);
            this.f_98513_ = new CreativeInventoryListener(this.f_96541_);
            this.f_96541_.f_91074_.f_36095_.m_38893_(this.f_98513_);
        } else {
            this.f_96541_.m_91152_(new InventoryScreen(this.f_96541_.f_91074_));
        }
    }

    @Override
    public void m_6574_(Minecraft p_98595_, int p_98596_, int p_98597_) {
        String $$3 = this.f_98510_.m_94155_();
        this.m_6575_(p_98595_, p_98596_, p_98597_);
        this.f_98510_.m_94144_($$3);
        if (!this.f_98510_.m_94155_().isEmpty()) {
            this.m_98630_();
        }
    }

    @Override
    public void m_7861_() {
        super.m_7861_();
        if (this.f_96541_.f_91074_ != null && this.f_96541_.f_91074_.m_150109_() != null) {
            this.f_96541_.f_91074_.f_36095_.m_38943_(this.f_98513_);
        }
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_5534_(char p_98521_, int p_98522_) {
        if (this.f_98514_) {
            return false;
        }
        if (f_98507_ != CreativeModeTab.f_40754_.m_40775_()) {
            return false;
        }
        String $$2 = this.f_98510_.m_94155_();
        if (this.f_98510_.m_5534_(p_98521_, p_98522_)) {
            if (!Objects.equals($$2, this.f_98510_.m_94155_())) {
                this.m_98630_();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7933_(int p_98547_, int p_98548_, int p_98549_) {
        this.f_98514_ = false;
        if (f_98507_ != CreativeModeTab.f_40754_.m_40775_()) {
            if (this.f_96541_.f_91066_.f_92098_.m_90832_(p_98547_, p_98548_)) {
                this.f_98514_ = true;
                this.m_98560_(CreativeModeTab.f_40754_);
                return true;
            }
            return super.m_7933_(p_98547_, p_98548_, p_98549_);
        }
        boolean $$3 = !this.m_98553_(this.f_97734_) || this.f_97734_.m_6657_();
        boolean $$4 = InputConstants.m_84827_(p_98547_, p_98548_).m_84876_().isPresent();
        if ($$3 && $$4 && this.m_97805_(p_98547_, p_98548_)) {
            this.f_98514_ = true;
            return true;
        }
        String $$5 = this.f_98510_.m_94155_();
        if (this.f_98510_.m_7933_(p_98547_, p_98548_, p_98549_)) {
            if (!Objects.equals($$5, this.f_98510_.m_94155_())) {
                this.m_98630_();
            }
            return true;
        }
        if (this.f_98510_.m_93696_() && this.f_98510_.m_94213_() && p_98547_ != 256) {
            return true;
        }
        return super.m_7933_(p_98547_, p_98548_, p_98549_);
    }

    @Override
    public boolean m_7920_(int p_98612_, int p_98613_, int p_98614_) {
        this.f_98514_ = false;
        return super.m_7920_(p_98612_, p_98613_, p_98614_);
    }

    private void m_98630_() {
        ((ItemPickerMenu)this.f_97732_).f_98639_.clear();
        this.f_98516_.clear();
        String $$0 = this.f_98510_.m_94155_();
        if ($$0.isEmpty()) {
            for (Item $$1 : Registry.f_122827_) {
                $$1.m_6787_(CreativeModeTab.f_40754_, ((ItemPickerMenu)this.f_97732_).f_98639_);
            }
        } else {
            SearchTree<ItemStack> $$3;
            if ($$0.startsWith("#")) {
                $$0 = $$0.substring(1);
                SearchTree<ItemStack> $$2 = this.f_96541_.m_231372_(SearchRegistry.f_119942_);
                this.m_98619_($$0);
            } else {
                $$3 = this.f_96541_.m_231372_(SearchRegistry.f_119941_);
            }
            ((ItemPickerMenu)this.f_97732_).f_98639_.addAll($$3.m_6293_($$0.toLowerCase(Locale.ROOT)));
        }
        this.f_98508_ = 0.0f;
        ((ItemPickerMenu)this.f_97732_).m_98642_(0.0f);
    }

    private void m_98619_(String p_98620_) {
        Predicate<ResourceLocation> $$5;
        int $$1 = p_98620_.indexOf(58);
        if ($$1 == -1) {
            Predicate<ResourceLocation> $$2 = p_98609_ -> p_98609_.m_135815_().contains(p_98620_);
        } else {
            String $$3 = p_98620_.substring(0, $$1).trim();
            String $$4 = p_98620_.substring($$1 + 1).trim();
            $$5 = p_98606_ -> p_98606_.m_135827_().contains($$3) && p_98606_.m_135815_().contains($$4);
        }
        Registry.f_122827_.m_203613_().filter(p_205410_ -> $$5.test(p_205410_.f_203868_())).forEach(this.f_98516_::add);
    }

    @Override
    protected void m_7027_(PoseStack p_98616_, int p_98617_, int p_98618_) {
        CreativeModeTab $$3 = CreativeModeTab.f_40748_[f_98507_];
        if ($$3.m_40789_()) {
            RenderSystem.m_69461_();
            this.f_96547_.m_92889_(p_98616_, $$3.m_40786_(), 8.0f, 6.0f, 0x404040);
        }
    }

    @Override
    public boolean m_6375_(double p_98531_, double p_98532_, int p_98533_) {
        if (p_98533_ == 0) {
            double $$3 = p_98531_ - (double)this.f_97735_;
            double $$4 = p_98532_ - (double)this.f_97736_;
            for (CreativeModeTab $$5 : CreativeModeTab.f_40748_) {
                if (!this.m_98562_($$5, $$3, $$4)) continue;
                return true;
            }
            if (f_98507_ != CreativeModeTab.f_40761_.m_40775_() && this.m_98523_(p_98531_, p_98532_)) {
                this.f_98509_ = this.m_98631_();
                return true;
            }
        }
        return super.m_6375_(p_98531_, p_98532_, p_98533_);
    }

    @Override
    public boolean m_6348_(double p_98622_, double p_98623_, int p_98624_) {
        if (p_98624_ == 0) {
            double $$3 = p_98622_ - (double)this.f_97735_;
            double $$4 = p_98623_ - (double)this.f_97736_;
            this.f_98509_ = false;
            for (CreativeModeTab $$5 : CreativeModeTab.f_40748_) {
                if (!this.m_98562_($$5, $$3, $$4)) continue;
                this.m_98560_($$5);
                return true;
            }
        }
        return super.m_6348_(p_98622_, p_98623_, p_98624_);
    }

    private boolean m_98631_() {
        return f_98507_ != CreativeModeTab.f_40761_.m_40775_() && CreativeModeTab.f_40748_[f_98507_].m_40791_() && ((ItemPickerMenu)this.f_97732_).m_98654_();
    }

    private void m_98560_(CreativeModeTab p_98561_) {
        int $$1 = f_98507_;
        f_98507_ = p_98561_.m_40775_();
        this.f_97737_.clear();
        ((ItemPickerMenu)this.f_97732_).f_98639_.clear();
        this.m_238391_();
        if (p_98561_ == CreativeModeTab.f_40760_) {
            HotbarManager $$2 = this.f_96541_.m_91303_();
            for (int $$3 = 0; $$3 < 9; ++$$3) {
                Hotbar $$4 = $$2.m_90806_($$3);
                if ($$4.isEmpty()) {
                    for (int $$5 = 0; $$5 < 9; ++$$5) {
                        if ($$5 == $$3) {
                            ItemStack $$6 = new ItemStack(Items.f_42516_);
                            $$6.m_41698_(f_169736_);
                            Component $$7 = this.f_96541_.f_91066_.f_92056_[$$3].m_90863_();
                            Component $$8 = this.f_96541_.f_91066_.f_92057_.m_90863_();
                            $$6.m_41714_(Component.m_237110_("inventory.hotbarInfo", $$8, $$7));
                            ((ItemPickerMenu)this.f_97732_).f_98639_.add($$6);
                            continue;
                        }
                        ((ItemPickerMenu)this.f_97732_).f_98639_.add(ItemStack.f_41583_);
                    }
                    continue;
                }
                ((ItemPickerMenu)this.f_97732_).f_98639_.addAll((Collection<ItemStack>)((Object)$$4));
            }
        } else if (p_98561_ != CreativeModeTab.f_40754_) {
            p_98561_.m_6151_(((ItemPickerMenu)this.f_97732_).f_98639_);
        }
        if (p_98561_ == CreativeModeTab.f_40761_) {
            InventoryMenu $$9 = this.f_96541_.f_91074_.f_36095_;
            if (this.f_98511_ == null) {
                this.f_98511_ = ImmutableList.copyOf((Collection)((ItemPickerMenu)this.f_97732_).f_38839_);
            }
            ((ItemPickerMenu)this.f_97732_).f_38839_.clear();
            for (int $$10 = 0; $$10 < $$9.f_38839_.size(); ++$$10) {
                int $$25;
                int $$23;
                if ($$10 >= 5 && $$10 < 9) {
                    int $$11 = $$10 - 5;
                    int $$12 = $$11 / 2;
                    int $$13 = $$11 % 2;
                    int $$14 = 54 + $$12 * 54;
                    int $$15 = 6 + $$13 * 27;
                } else if ($$10 >= 0 && $$10 < 5) {
                    int $$16 = -2000;
                    int $$17 = -2000;
                } else if ($$10 == 45) {
                    int $$18 = 35;
                    int $$19 = 20;
                } else {
                    int $$20 = $$10 - 9;
                    int $$21 = $$20 % 9;
                    int $$22 = $$20 / 9;
                    $$23 = 9 + $$21 * 18;
                    if ($$10 >= 36) {
                        int $$24 = 112;
                    } else {
                        $$25 = 54 + $$22 * 18;
                    }
                }
                SlotWrapper $$26 = new SlotWrapper($$9.f_38839_.get($$10), $$10, $$23, $$25);
                ((ItemPickerMenu)this.f_97732_).f_38839_.add($$26);
            }
            this.f_98512_ = new Slot(f_98505_, 0, 173, 112);
            ((ItemPickerMenu)this.f_97732_).f_38839_.add(this.f_98512_);
        } else if ($$1 == CreativeModeTab.f_40761_.m_40775_()) {
            ((ItemPickerMenu)this.f_97732_).f_38839_.clear();
            ((ItemPickerMenu)this.f_97732_).f_38839_.addAll(this.f_98511_);
            this.f_98511_ = null;
        }
        if (this.f_98510_ != null) {
            if (p_98561_ == CreativeModeTab.f_40754_) {
                this.f_98510_.m_94194_(true);
                this.f_98510_.m_94190_(false);
                this.f_98510_.m_94178_(true);
                if ($$1 != p_98561_.m_40775_()) {
                    this.f_98510_.m_94144_("");
                }
                this.m_98630_();
            } else {
                this.f_98510_.m_94194_(false);
                this.f_98510_.m_94190_(true);
                this.f_98510_.m_94178_(false);
                this.f_98510_.m_94144_("");
            }
        }
        this.f_98508_ = 0.0f;
        ((ItemPickerMenu)this.f_97732_).m_98642_(0.0f);
    }

    @Override
    public boolean m_6050_(double p_98527_, double p_98528_, double p_98529_) {
        if (!this.m_98631_()) {
            return false;
        }
        int $$3 = (((ItemPickerMenu)this.f_97732_).f_98639_.size() + 9 - 1) / 9 - 5;
        float $$4 = (float)(p_98529_ / (double)$$3);
        this.f_98508_ = Mth.m_14036_(this.f_98508_ - $$4, 0.0f, 1.0f);
        ((ItemPickerMenu)this.f_97732_).m_98642_(this.f_98508_);
        return true;
    }

    @Override
    protected boolean m_7467_(double p_98541_, double p_98542_, int p_98543_, int p_98544_, int p_98545_) {
        boolean $$5 = p_98541_ < (double)p_98543_ || p_98542_ < (double)p_98544_ || p_98541_ >= (double)(p_98543_ + this.f_97726_) || p_98542_ >= (double)(p_98544_ + this.f_97727_);
        this.f_98515_ = $$5 && !this.m_98562_(CreativeModeTab.f_40748_[f_98507_], p_98541_, p_98542_);
        return this.f_98515_;
    }

    protected boolean m_98523_(double p_98524_, double p_98525_) {
        int $$2 = this.f_97735_;
        int $$3 = this.f_97736_;
        int $$4 = $$2 + 175;
        int $$5 = $$3 + 18;
        int $$6 = $$4 + 14;
        int $$7 = $$5 + 112;
        return p_98524_ >= (double)$$4 && p_98525_ >= (double)$$5 && p_98524_ < (double)$$6 && p_98525_ < (double)$$7;
    }

    @Override
    public boolean m_7979_(double p_98535_, double p_98536_, int p_98537_, double p_98538_, double p_98539_) {
        if (this.f_98509_) {
            int $$5 = this.f_97736_ + 18;
            int $$6 = $$5 + 112;
            this.f_98508_ = ((float)p_98536_ - (float)$$5 - 7.5f) / ((float)($$6 - $$5) - 15.0f);
            this.f_98508_ = Mth.m_14036_(this.f_98508_, 0.0f, 1.0f);
            ((ItemPickerMenu)this.f_97732_).m_98642_(this.f_98508_);
            return true;
        }
        return super.m_7979_(p_98535_, p_98536_, p_98537_, p_98538_, p_98539_);
    }

    @Override
    public void m_6305_(PoseStack p_98577_, int p_98578_, int p_98579_, float p_98580_) {
        this.m_7333_(p_98577_);
        super.m_6305_(p_98577_, p_98578_, p_98579_, p_98580_);
        for (CreativeModeTab $$4 : CreativeModeTab.f_40748_) {
            if (this.m_98584_(p_98577_, $$4, p_98578_, p_98579_)) break;
        }
        if (this.f_98512_ != null && f_98507_ == CreativeModeTab.f_40761_.m_40775_() && this.m_6774_(this.f_98512_.f_40220_, this.f_98512_.f_40221_, 16, 16, p_98578_, p_98579_)) {
            this.m_96602_(p_98577_, f_98506_, p_98578_, p_98579_);
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        this.m_7025_(p_98577_, p_98578_, p_98579_);
    }

    @Override
    protected void m_6057_(PoseStack p_98590_, ItemStack p_98591_, int p_98592_, int p_98593_) {
        if (f_98507_ == CreativeModeTab.f_40754_.m_40775_()) {
            Map<Enchantment, Integer> $$8;
            List<Component> $$4 = p_98591_.m_41651_(this.f_96541_.f_91074_, this.f_96541_.f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
            ArrayList $$5 = Lists.newArrayList($$4);
            Item $$6 = p_98591_.m_41720_();
            CreativeModeTab $$7 = $$6.m_41471_();
            if ($$7 == null && p_98591_.m_150930_(Items.f_42690_) && ($$8 = EnchantmentHelper.m_44831_(p_98591_)).size() == 1) {
                Enchantment $$9 = $$8.keySet().iterator().next();
                for (CreativeModeTab $$10 : CreativeModeTab.f_40748_) {
                    if (!$$10.m_40776_($$9.f_44672_)) continue;
                    $$7 = $$10;
                    break;
                }
            }
            this.f_98516_.forEach(p_205407_ -> {
                if (p_98591_.m_204117_((TagKey<Item>)p_205407_)) {
                    $$5.add(1, Component.m_237113_("#" + p_205407_.f_203868_()).m_130940_(ChatFormatting.DARK_PURPLE));
                }
            });
            if ($$7 != null) {
                $$5.add(1, $$7.m_40786_().m_6881_().m_130940_(ChatFormatting.BLUE));
            }
            this.m_169388_(p_98590_, $$5, p_98591_.m_150921_(), p_98592_, p_98593_);
        } else {
            super.m_6057_(p_98590_, p_98591_, p_98592_, p_98593_);
        }
    }

    @Override
    protected void m_7286_(PoseStack p_98572_, float p_98573_, int p_98574_, int p_98575_) {
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        CreativeModeTab $$4 = CreativeModeTab.f_40748_[f_98507_];
        for (CreativeModeTab $$5 : CreativeModeTab.f_40748_) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_98504_);
            if ($$5.m_40775_() == f_98507_) continue;
            this.m_98581_(p_98572_, $$5);
        }
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, new ResourceLocation(f_169735_ + $$4.m_40788_()));
        this.m_93228_(p_98572_, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
        this.f_98510_.m_6305_(p_98572_, p_98574_, p_98575_, p_98573_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        int $$6 = this.f_97735_ + 175;
        int $$7 = this.f_97736_ + 18;
        int $$8 = $$7 + 112;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_98504_);
        if ($$4.m_40791_()) {
            this.m_93228_(p_98572_, $$6, $$7 + (int)((float)($$8 - $$7 - 17) * this.f_98508_), 232 + (this.m_98631_() ? 0 : 12), 0, 12, 15);
        }
        this.m_98581_(p_98572_, $$4);
        if ($$4 == CreativeModeTab.f_40761_) {
            InventoryScreen.m_98850_(this.f_97735_ + 88, this.f_97736_ + 45, 20, this.f_97735_ + 88 - p_98574_, this.f_97736_ + 45 - 30 - p_98575_, this.f_96541_.f_91074_);
        }
    }

    protected boolean m_98562_(CreativeModeTab p_98563_, double p_98564_, double p_98565_) {
        int $$3 = p_98563_.m_40793_();
        int $$4 = 28 * $$3;
        int $$5 = 0;
        if (p_98563_.m_6563_()) {
            $$4 = this.f_97726_ - 28 * (6 - $$3) + 2;
        } else if ($$3 > 0) {
            $$4 += $$3;
        }
        $$5 = p_98563_.m_40794_() ? ($$5 -= 32) : ($$5 += this.f_97727_);
        return p_98564_ >= (double)$$4 && p_98564_ <= (double)($$4 + 28) && p_98565_ >= (double)$$5 && p_98565_ <= (double)($$5 + 32);
    }

    protected boolean m_98584_(PoseStack p_98585_, CreativeModeTab p_98586_, int p_98587_, int p_98588_) {
        int $$4 = p_98586_.m_40793_();
        int $$5 = 28 * $$4;
        int $$6 = 0;
        if (p_98586_.m_6563_()) {
            $$5 = this.f_97726_ - 28 * (6 - $$4) + 2;
        } else if ($$4 > 0) {
            $$5 += $$4;
        }
        $$6 = p_98586_.m_40794_() ? ($$6 -= 32) : ($$6 += this.f_97727_);
        if (this.m_6774_($$5 + 3, $$6 + 3, 23, 27, p_98587_, p_98588_)) {
            this.m_96602_(p_98585_, p_98586_.m_40786_(), p_98587_, p_98588_);
            return true;
        }
        return false;
    }

    protected void m_98581_(PoseStack p_98582_, CreativeModeTab p_98583_) {
        boolean $$2 = p_98583_.m_40775_() == f_98507_;
        boolean $$3 = p_98583_.m_40794_();
        int $$4 = p_98583_.m_40793_();
        int $$5 = $$4 * 28;
        int $$6 = 0;
        int $$7 = this.f_97735_ + 28 * $$4;
        int $$8 = this.f_97736_;
        int $$9 = 32;
        if ($$2) {
            $$6 += 32;
        }
        if (p_98583_.m_6563_()) {
            $$7 = this.f_97735_ + this.f_97726_ - 28 * (6 - $$4);
        } else if ($$4 > 0) {
            $$7 += $$4;
        }
        if ($$3) {
            $$8 -= 28;
        } else {
            $$6 += 64;
            $$8 += this.f_97727_ - 4;
        }
        this.m_93228_(p_98582_, $$7, $$8, $$5, $$6, 28, 32);
        this.f_96542_.f_115093_ = 100.0f;
        int n = $$3 ? 1 : -1;
        ItemStack $$10 = p_98583_.m_40787_();
        this.f_96542_.m_115203_($$10, $$7 += 6, $$8 += 8 + n);
        this.f_96542_.m_115169_(this.f_96547_, $$10, $$7, $$8);
        this.f_96542_.f_115093_ = 0.0f;
    }

    public int m_98628_() {
        return f_98507_;
    }

    public static void m_98598_(Minecraft p_98599_, int p_98600_, boolean p_98601_, boolean p_98602_) {
        LocalPlayer $$4 = p_98599_.f_91074_;
        HotbarManager $$5 = p_98599_.m_91303_();
        Hotbar $$6 = $$5.m_90806_(p_98600_);
        if (p_98601_) {
            for (int $$7 = 0; $$7 < Inventory.m_36059_(); ++$$7) {
                ItemStack $$8 = ((ItemStack)$$6.get($$7)).m_41777_();
                $$4.m_150109_().m_6836_($$7, $$8);
                p_98599_.f_91072_.m_105241_($$8, 36 + $$7);
            }
            $$4.f_36095_.m_38946_();
        } else if (p_98602_) {
            for (int $$9 = 0; $$9 < Inventory.m_36059_(); ++$$9) {
                $$6.set($$9, $$4.m_150109_().m_8020_($$9).m_41777_());
            }
            Component $$10 = p_98599_.f_91066_.f_92056_[p_98600_].m_90863_();
            Component $$11 = p_98599_.f_91066_.f_92058_.m_90863_();
            MutableComponent $$12 = Component.m_237110_("inventory.hotbarSaved", $$11, $$10);
            p_98599_.f_91065_.m_93063_($$12, false);
            p_98599_.m_240477_().m_168785_($$12);
            $$5.m_90805_();
        }
    }

    public static class ItemPickerMenu
    extends AbstractContainerMenu {
        public final NonNullList<ItemStack> f_98639_ = NonNullList.m_122779_();
        private final AbstractContainerMenu f_169749_;

        public ItemPickerMenu(Player p_98641_) {
            super(null, 0);
            this.f_169749_ = p_98641_.f_36095_;
            Inventory $$1 = p_98641_.m_150109_();
            for (int $$2 = 0; $$2 < 5; ++$$2) {
                for (int $$3 = 0; $$3 < 9; ++$$3) {
                    this.m_38897_(new CustomCreativeSlot(f_98505_, $$2 * 9 + $$3, 9 + $$3 * 18, 18 + $$2 * 18));
                }
            }
            for (int $$4 = 0; $$4 < 9; ++$$4) {
                this.m_38897_(new Slot($$1, $$4, 9 + $$4 * 18, 112));
            }
            this.m_98642_(0.0f);
        }

        @Override
        public boolean m_6875_(Player p_98645_) {
            return true;
        }

        public void m_98642_(float p_98643_) {
            int $$1 = (this.f_98639_.size() + 9 - 1) / 9 - 5;
            int $$2 = (int)((double)(p_98643_ * (float)$$1) + 0.5);
            if ($$2 < 0) {
                $$2 = 0;
            }
            for (int $$3 = 0; $$3 < 5; ++$$3) {
                for (int $$4 = 0; $$4 < 9; ++$$4) {
                    int $$5 = $$4 + ($$3 + $$2) * 9;
                    if ($$5 >= 0 && $$5 < this.f_98639_.size()) {
                        f_98505_.m_6836_($$4 + $$3 * 9, this.f_98639_.get($$5));
                        continue;
                    }
                    f_98505_.m_6836_($$4 + $$3 * 9, ItemStack.f_41583_);
                }
            }
        }

        public boolean m_98654_() {
            return this.f_98639_.size() > 45;
        }

        @Override
        public ItemStack m_7648_(Player p_98650_, int p_98651_) {
            Slot $$2;
            if (p_98651_ >= this.f_38839_.size() - 9 && p_98651_ < this.f_38839_.size() && ($$2 = (Slot)this.f_38839_.get(p_98651_)) != null && $$2.m_6657_()) {
                $$2.m_5852_(ItemStack.f_41583_);
            }
            return ItemStack.f_41583_;
        }

        @Override
        public boolean m_5882_(ItemStack p_98647_, Slot p_98648_) {
            return p_98648_.f_40218_ != f_98505_;
        }

        @Override
        public boolean m_5622_(Slot p_98653_) {
            return p_98653_.f_40218_ != f_98505_;
        }

        @Override
        public ItemStack m_142621_() {
            return this.f_169749_.m_142621_();
        }

        @Override
        public void m_142503_(ItemStack p_169751_) {
            this.f_169749_.m_142503_(p_169751_);
        }
    }

    static class SlotWrapper
    extends Slot {
        final Slot f_98655_;

        public SlotWrapper(Slot p_98657_, int p_98658_, int p_98659_, int p_98660_) {
            super(p_98657_.f_40218_, p_98658_, p_98659_, p_98660_);
            this.f_98655_ = p_98657_;
        }

        @Override
        public void m_142406_(Player p_169754_, ItemStack p_169755_) {
            this.f_98655_.m_142406_(p_169754_, p_169755_);
        }

        @Override
        public boolean m_5857_(ItemStack p_98670_) {
            return this.f_98655_.m_5857_(p_98670_);
        }

        @Override
        public ItemStack m_7993_() {
            return this.f_98655_.m_7993_();
        }

        @Override
        public boolean m_6657_() {
            return this.f_98655_.m_6657_();
        }

        @Override
        public void m_5852_(ItemStack p_98679_) {
            this.f_98655_.m_5852_(p_98679_);
        }

        @Override
        public void m_6654_() {
            this.f_98655_.m_6654_();
        }

        @Override
        public int m_6641_() {
            return this.f_98655_.m_6641_();
        }

        @Override
        public int m_5866_(ItemStack p_98675_) {
            return this.f_98655_.m_5866_(p_98675_);
        }

        @Override
        @Nullable
        public Pair<ResourceLocation, ResourceLocation> m_7543_() {
            return this.f_98655_.m_7543_();
        }

        @Override
        public ItemStack m_6201_(int p_98663_) {
            return this.f_98655_.m_6201_(p_98663_);
        }

        @Override
        public boolean m_6659_() {
            return this.f_98655_.m_6659_();
        }

        @Override
        public boolean m_8010_(Player p_98665_) {
            return this.f_98655_.m_8010_(p_98665_);
        }
    }

    static class CustomCreativeSlot
    extends Slot {
        public CustomCreativeSlot(Container p_98633_, int p_98634_, int p_98635_, int p_98636_) {
            super(p_98633_, p_98634_, p_98635_, p_98636_);
        }

        @Override
        public boolean m_8010_(Player p_98638_) {
            if (super.m_8010_(p_98638_) && this.m_6657_()) {
                return this.m_7993_().m_41737_(CreativeModeInventoryScreen.f_169736_) == null;
            }
            return !this.m_6657_();
        }
    }
}

