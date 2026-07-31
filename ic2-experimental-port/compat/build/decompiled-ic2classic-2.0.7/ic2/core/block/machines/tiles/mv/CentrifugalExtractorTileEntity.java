/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BaseAdvMachineTileEntity;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CentrifugalExtractorTileEntity
extends BaseAdvMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_centrifuge.png");
    public static final Component SPEED = Component.m_237115_((String)"info.block.ic2.centrifugal_extractor.speed");
    public static final Box2i CHARGE_POS = new Box2i(40, 36, 14, 14);
    public static final Box2i PROGRESS_POS = new Box2i(64, 34, 24, 16);

    public CentrifugalExtractorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5, 15, 4000);
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).extractor;
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.EXTRACTOR_PROCESSING;
    }

    @Override
    public Box2i getChargeBox() {
        return CHARGE_POS;
    }

    @Override
    public Box2i getProgressBox() {
        return PROGRESS_POS;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{2, 3, 4};
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public Slot[] addSlots(Player player) {
        Slot[] slots = new Slot[]{FilterSlot.createDischargeSlot(this, this.tier, 0, 40, 53), new FilterSlot(this, 1, 40, 17, T -> this.getRecipeList().getRecipe(T, false) != null), new XPSlot(this, 2, 92, 35), new XPSlot(this, 3, 110, 35), new XPSlot(this, 4, 128, 35)};
        return slots;
    }

    @Override
    public Component getSpeedName() {
        return SPEED;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CENTRIFUGAL_EXTRACTOR;
    }
}

