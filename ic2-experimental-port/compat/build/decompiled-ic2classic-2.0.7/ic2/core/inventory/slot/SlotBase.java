/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.slot;

import com.mojang.datafixers.util.Pair;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.platform.corehacks.mixins.server.SlotMixin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SlotBase
extends Slot {
    public static final Container EMPTY_INVENTORY = new SimpleContainer(0);
    protected Pair<ResourceLocation, ResourceLocation> backgroundPair;
    protected IHasInventory inventory;
    protected int f_40217_;
    int lastCount = 0;
    protected boolean markQuickCraft = false;

    public SlotBase(IHasInventory inv, int index, int xPosition, int yPosition) {
        super(EMPTY_INVENTORY, index, xPosition, yPosition);
        this.inventory = inv;
        this.f_40217_ = index;
    }

    public IHasInventory getInventory() {
        return this.inventory;
    }

    public ItemStack m_7993_() {
        return this.getInventory().getStackInSlot(this.f_40217_);
    }

    public int m_6641_() {
        return this.getInventory().getMaxStackSize(this.f_40217_);
    }

    public void m_6654_() {
        IHasInventory inventory = this.getInventory();
        if (inventory instanceof INotifyInventory) {
            ((INotifyInventory)((Object)inventory)).onNotify(inventory, this.f_40217_);
        }
    }

    public ItemStack m_6201_(int amount) {
        ItemStack stack = this.getInventory().getStackInSlot(this.f_40217_);
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        ItemStack split = stack.m_41620_(amount);
        this.getInventory().setStackInSlot(this.f_40217_, stack);
        return split;
    }

    public void m_5852_(ItemStack stack) {
        this.lastCount = stack.m_41613_();
        this.getInventory().setStackInSlot(this.f_40217_, stack);
        int max = this.inventory.getMaxStackSize(this.f_40217_);
        if (stack.m_41613_() > max) {
            stack.m_41764_(max);
        }
        if (this.markQuickCraft) {
            this.markQuickCraft = false;
            this.m_6654_();
        }
    }

    public void m_219996_(ItemStack stack) {
        this.m_5852_(stack);
    }

    public void markQuickCraft() {
        this.markQuickCraft = true;
    }

    public boolean isSameInventory(Slot other) {
        return other instanceof SlotBase && ((SlotBase)other).getInventory() == this.getInventory();
    }

    public SlotBase setX(int x) {
        ((SlotMixin)((Object)this)).setX(x);
        return this;
    }

    public SlotBase setY(int y) {
        ((SlotMixin)((Object)this)).setY(y);
        return this;
    }

    @OnlyIn(value=Dist.CLIENT)
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return this.backgroundPair;
    }

    public SlotBase setBackground(ResourceLocation sprite) {
        return this.setBackground(InventoryMenu.f_39692_, sprite);
    }

    public SlotBase setBackground(ResourceLocation atlas, ResourceLocation sprite) {
        this.backgroundPair = atlas == null || sprite == null ? null : Pair.of((Object)atlas, (Object)sprite);
        return this;
    }
}

