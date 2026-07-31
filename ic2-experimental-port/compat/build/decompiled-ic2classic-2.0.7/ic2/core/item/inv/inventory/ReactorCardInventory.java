/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.item.inv.components.ReactorCardComponent;
import ic2.core.item.inv.inventory.CardInventory;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorCardInventory
extends CardInventory {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/items/gui_reactor_card.png");
    public int maxHeat;

    public ReactorCardInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot, int colorCount, List<BaseTileInfoProvider.SettingEntry> settings) {
        super(owner, held, stack, slot, colorCount, settings);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        super.onGuiLoaded(screen);
        screen.setYSize(221);
    }

    @Override
    public int getPlayerYOffset() {
        return 55;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void addComponents(Consumer<GuiWidget> components) {
        super.addComponents(components);
        components.accept(new ReactorCardComponent(this));
    }

    @Override
    public void onDataReceived(int key, int value) {
        super.onDataReceived(key, value);
        if (key == -1) {
            this.maxHeat = value;
        }
    }

    @Override
    public void save(CompoundTag nbt) {
        super.save(nbt);
        NBTUtils.putInt(nbt, "max_heat", this.maxHeat, 0);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.maxHeat = nbt.m_128451_("max_heat");
    }
}

