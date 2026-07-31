/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.machines.logic.terraformer;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.core.block.machines.logic.terraformer.ITerraformerLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RandomTerraformerLogic
implements ITerraformerLogic {
    BlockPos lastPos = null;
    int failedAttempts = 0;

    @Override
    public boolean canRun() {
        return true;
    }

    @Override
    public boolean run(ITerraformer terraformer, ItemStack stack, ITerraformerBP bluePrint) {
        BlockPos nextPos;
        Level world = terraformer.getWorldObj();
        BlockPos pos = terraformer.getPosition();
        int maxRadius = bluePrint.getRadius(stack);
        int range = 1;
        if (this.lastPos != null) {
            range = Math.max(1, maxRadius / 10);
            nextPos = new BlockPos(this.lastPos.m_123341_() - world.f_46441_.m_188503_(range + 1) + world.f_46441_.m_188503_(range + 1), pos.m_123342_(), this.lastPos.m_123343_() - world.f_46441_.m_188503_(range + 1) + world.f_46441_.m_188503_(range + 1));
        } else {
            if (this.failedAttempts > 4) {
                this.failedAttempts = 4;
            }
            range = maxRadius * (this.failedAttempts + 1) / 5;
            nextPos = new BlockPos(pos.m_123341_() - world.f_46441_.m_188503_(range + 1) + world.f_46441_.m_188503_(range + 1), pos.m_123342_(), pos.m_123343_() - world.f_46441_.m_188503_(range + 1) + world.f_46441_.m_188503_(range + 1));
        }
        if (pos.m_123314_((Vec3i)nextPos, (double)maxRadius) && bluePrint.terraform(stack, terraformer.getWorldObj(), nextPos, terraformer)) {
            this.failedAttempts = 0;
            this.lastPos = nextPos;
            return true;
        }
        ++this.failedAttempts;
        this.lastPos = null;
        return false;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.m_128344_("type", (byte)2);
        tag.m_128405_("fails", this.failedAttempts);
        if (this.lastPos != null) {
            tag.m_128356_("last", this.lastPos.m_121878_());
        }
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.failedAttempts = tag.m_128451_("fails");
        if (tag.m_128441_("last")) {
            this.lastPos = BlockPos.m_122022_((long)tag.m_128454_("last"));
        }
    }
}

