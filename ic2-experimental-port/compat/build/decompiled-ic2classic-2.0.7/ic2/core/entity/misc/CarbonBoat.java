/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.entity.misc;

import ic2.core.entity.misc.IC2Boat;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CarbonBoat
extends IC2Boat {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/models/boat/carbon_boat.png");

    public CarbonBoat(EntityType<? extends Boat> type, Level world) {
        super(type, world);
    }

    public CarbonBoat(EntityType<? extends Boat> type, Level worldIn, double x, double y, double z) {
        super(type, worldIn, x, y, z);
    }

    @Override
    public Item m_38369_() {
        return IC2Items.CARBON_BOAT;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public void m_20321_(boolean downwards) {
    }

    public void m_6845_(boolean downwards) {
    }

    @Override
    protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.updateLastY();
        if (onGroundIn) {
            this.f_19789_ = 0.0f;
        } else if (!this.f_19853_.m_6425_(this.m_20183_().m_7495_()).m_205070_(FluidTags.f_13131_) && y < 0.0) {
            this.f_19789_ = (float)((double)this.f_19789_ - y);
        }
    }
}

