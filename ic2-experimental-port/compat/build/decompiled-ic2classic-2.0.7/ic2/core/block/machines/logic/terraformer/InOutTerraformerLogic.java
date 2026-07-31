/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.terraformer;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.api.util.DirectionList;
import ic2.core.block.machines.logic.terraformer.ITerraformerLogic;
import ic2.core.utils.helpers.AABBUtil;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class InOutTerraformerLogic
implements ITerraformerLogic,
Consumer<AABBUtil.SearchResult> {
    List<BlockPos> positions = null;
    boolean loading = false;

    @Override
    public boolean canRun() {
        return this.positions == null && !this.loading || this.positions != null && this.positions.size() > 0;
    }

    @Override
    public boolean run(ITerraformer terraformer, ItemStack stack, ITerraformerBP blueprint) {
        if (this.positions == null) {
            AABBUtil.createOffthreadTask(terraformer.getWorldObj(), terraformer.getPosition(), blueprint.getRadius(stack), K -> true, 0, DirectionList.HORIZONTAL, this);
            this.loading = true;
            return false;
        }
        if (this.positions.size() > 0) {
            for (int i = 0; i < 100 && !this.positions.isEmpty() && !blueprint.terraform(stack, terraformer.getWorldObj(), this.positions.remove(this.positions.size() - 1), terraformer); ++i) {
            }
            return true;
        }
        return false;
    }

    @Override
    public void accept(AABBUtil.SearchResult t) {
        Collections.reverse(t.getPositions());
        this.positions = t.getPositions();
        this.loading = false;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.m_128344_("type", (byte)1);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
    }
}

