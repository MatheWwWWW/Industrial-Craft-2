/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.container;

import ic2.core.block.base.features.redstone.IComparable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerHasGui;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.ComparatorComponent;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.gui.components.simple.InventorySettingsComponent;
import ic2.core.inventory.handler.IHasInventoryHandler;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ContainerComponent<T extends IHasGui>
extends ContainerHasGui<T> {
    public static final Vec2i DEFAULT_OFFSET = new Vec2i();
    List<GuiWidget> components = new ArrayList<GuiWidget>();
    private boolean addedPreviewer = false;

    public ContainerComponent(T key, Player player, int id) {
        super(key, player, id);
        if (this.gui instanceof IHasInventoryHandler && ((IHasInventoryHandler)((Object)this.gui)).allowsUI()) {
            this.addComponent(new InventorySettingsComponent(((IHasInventoryHandler)((Object)this.gui)).getInventoryHandler(), this.getInventoryOffset(), this.getInvButtonOffset()));
        }
        if (this.gui instanceof IComparable && this.gui instanceof BaseTileEntity && ((IComparable)((Object)this.gui)).isAllowingUI()) {
            this.addComponent(new ComparatorComponent<BaseTileEntity>((BaseTileEntity)((Object)this.gui), this.getComparatorButtonOffset(), this.getComparatorOffset()));
        }
    }

    @Override
    protected void addInternalSlots(Inventory player) {
        InventorySettingsComponent comp = this.getComponent(InventorySettingsComponent.class);
        if (comp != null) {
            IHasInventory inv = ((IHasInventoryHandler)((Object)this.gui)).getInventoryHandler().getFilters();
            for (int i = 0; i < 5; ++i) {
                this.m_38897_(new SettingsSlot(comp, inv, i, -94 + i * 18, 91));
            }
            this.addExtraSlots(5);
        }
    }

    public Slot m_38897_(Slot slot) {
        if (slot instanceof IGhostSlot && ((IGhostSlot)slot).getType() == IGhostSlot.GhostType.NORMAL && !this.addedPreviewer) {
            this.addedPreviewer = true;
            this.addComponent(new FilterComponent(this.getPreviewOffset(), this.getPreviewButtonOffset()));
        }
        return super.m_38897_(slot);
    }

    @Override
    public int getInventorySize() {
        if (this.getHolder() instanceof IHasInventory) {
            return ((IHasInventory)this.getHolder()).getSlotCount();
        }
        throw new IllegalStateException("Implement [getInventorySize] in [" + ((Object)((Object)this)).getClass() + "]");
    }

    @Override
    public void m_6877_(Player playerIn) {
        if (this.gui instanceof IComparable) {
            ((IComparable)((Object)this.gui)).getManager().setSync(false);
        }
        super.m_6877_(playerIn);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
    }

    public void addComponent(GuiWidget component) {
        this.components.add(0, component);
    }

    public List<GuiWidget> getComponents() {
        return this.components;
    }

    public <S extends GuiWidget> S getComponent(Class<S> clz) {
        for (GuiWidget comp : this.components) {
            if (!clz.isInstance(comp)) continue;
            return (S)((GuiWidget)clz.cast(comp));
        }
        return null;
    }

    public Component getName() {
        Object gui = this.getHolder();
        if (gui instanceof Nameable) {
            Nameable name = (Nameable)gui;
            return name.m_8077_() ? name.m_7770_() : name.m_7755_();
        }
        throw new RuntimeException("Container[" + gui.getClass() + "] needs to implement Nameable or override getName() in [" + ((Object)((Object)this)).getClass() + "]");
    }

    public void disablePreviewer() {
        this.addedPreviewer = true;
    }

    public abstract ResourceLocation getTexture();

    public Vec2i getInventoryOffset() {
        return DEFAULT_OFFSET;
    }

    public Vec2i getInvButtonOffset() {
        return DEFAULT_OFFSET;
    }

    public Vec2i getPreviewOffset() {
        return this.getInventoryOffset();
    }

    public Vec2i getPreviewButtonOffset() {
        return this.getInvButtonOffset();
    }

    public Vec2i getComparatorOffset() {
        return this.getInventoryOffset();
    }

    public Vec2i getComparatorButtonOffset() {
        return this.getInvButtonOffset();
    }

    public class SettingsSlot
    extends FilterSlot {
        InventorySettingsComponent comp;

        public SettingsSlot(InventorySettingsComponent comp, IHasInventory inv, int index, int xPosition, int yPosition) {
            super(inv, index, xPosition + comp.getOffset().getX(), yPosition + comp.getOffset().getY(), SpecialFilters.HANDLER_ITEM);
            this.comp = comp;
        }

        public boolean m_8010_(Player p_40228_) {
            return super.m_8010_(p_40228_) && !this.comp.getHandler().isLocked();
        }

        @Override
        public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && !this.comp.getHandler().isLocked();
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean m_6659_() {
            return this.comp.isVisible();
        }
    }
}

