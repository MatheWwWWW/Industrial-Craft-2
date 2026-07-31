/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.entity.misc;

import ic2.core.entity.misc.IC2Boat;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RubberBoat
extends IC2Boat {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/models/boat/rubber_boat.png");
    public float nextBounce = 0.0f;

    public RubberBoat(EntityType<? extends Boat> type, Level world) {
        super(type, world);
    }

    public RubberBoat(EntityType<? extends Boat> type, Level worldIn, double x, double y, double z) {
        super(type, worldIn, x, y, z);
    }

    @Override
    public Item m_38369_() {
        return IC2Items.RUBBER_BOAT;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    public boolean m_6673_(DamageSource source) {
        return source.m_19384_() || super.m_6673_(source);
    }

    public void m_8119_() {
        if (this.nextBounce > 0.0f) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, (double)this.nextBounce, 0.0));
            this.nextBounce = 0.0f;
        }
        super.m_8119_();
    }

    @Override
    public void spawnBrokenDrops() {
        for (int i = 0; i < 3; ++i) {
            this.m_19998_((ItemLike)IC2Blocks.RUBBERWOOD_PLANKS.m_5456_());
        }
        for (int j = 0; j < 2; ++j) {
            this.m_19998_((ItemLike)Items.f_42398_);
        }
    }

    @Override
    protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.updateLastY();
        if (onGroundIn) {
            if (this.f_19789_ > 40.0f) {
                if (!this.f_19853_.f_46443_ && this.m_6084_()) {
                    this.m_142687_(Entity.RemovalReason.DISCARDED);
                    this.m_19998_((ItemLike)IC2Items.RUBBER_BOAT_BROKEN);
                }
            } else if (this.f_19789_ != 0.0f) {
                this.nextBounce = this.f_19789_ / 17.5f;
                this.m_20256_(this.m_20184_().m_82542_(1.1, 1.0, 1.1));
            }
            this.f_19789_ = 0.0f;
        } else if (!this.f_19853_.m_6425_(this.m_20183_().m_7495_()).m_205070_(FluidTags.f_13131_) && y < 0.0) {
            this.f_19789_ = (float)((double)this.f_19789_ - y);
        }
    }
}

