/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.slot;

import com.mojang.datafixers.util.Pair;
import ic2.core.block.machines.logic.crafter.IMemorySlotProvider;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MemorySlot
extends SlotBase {
    IMemorySlotProvider bench;
    ItemStack display = IC2Items.ICON_DISPLAY.create(1);

    public MemorySlot(IMemorySlotProvider inv, int index, int xPosition, int yPosition) {
        super(inv.getRecipes(), index, xPosition, yPosition);
        this.bench = inv;
    }

    public boolean m_8010_(Player playerIn) {
        return false;
    }

    public boolean m_5857_(ItemStack stack) {
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean m_6659_() {
        return (this.bench.getEnabledSlots() & 1 << this.f_40217_) != 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return Pair.of((Object)InventoryMenu.f_39692_, (Object)IC2Textures.getMappedEntriesIC2("misc/gui").get("memory_stick").m_118413_());
    }

    @Override
    public ItemStack m_7993_() {
        if (!this.bench.isServerSided() && (this.bench.getEnabledSlots() & 1 << this.f_40217_) == 0) {
            return this.display;
        }
        return super.m_7993_();
    }
}

