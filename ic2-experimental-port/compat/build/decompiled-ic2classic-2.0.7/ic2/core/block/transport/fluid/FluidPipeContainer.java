/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.fluid;

import ic2.core.block.transport.fluid.FluidPipeComponent;
import ic2.core.block.transport.fluid.helper.InventoryData;
import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.FilterComponent;
import ic2.core.inventory.slot.FluidSlot;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluidPipeContainer
extends ContainerComponent<PipeTileEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/misc/gui_pipe.png");
    public InventoryData data;

    public FluidPipeContainer(PipeTileEntity key, Player player, int id) {
        super(key, player, id);
        this.data = new InventoryData(key.fluidCache, key.cache);
        this.m_38884_(this.data);
        for (int i = 0; i < 9; ++i) {
            this.m_38897_(new FluidSlot(this.data, i, 8 + 18 * i, 24));
        }
        this.addComponent(new FilterComponent(this.getPreviewOffset()).setFluid());
        this.addComponent(new FluidPipeComponent(key, this.data, key::hasEmitter));
        this.addPlayerInventoryWithOffset(player.m_150109_(), 0, -22);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiLoaded(IC2Screen screen) {
        screen.modifySize(0, -21);
    }

    @Override
    public int getInventorySize() {
        return 9;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId >= 0 && slotId < this.getInventorySize()) {
            this.data.setFluidInSlot(slotId, FluidHelper.getDisplayFluid(this.m_142621_()));
            return;
        }
        super.m_150399_(slotId, dragType, clickTypeIn, player);
    }
}

