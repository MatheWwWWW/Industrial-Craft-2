/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.block;

import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomBlockModel {
    default public List<BlockState> getCustomStates() {
        return ((Block)this).m_49965_().m_61056_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState var1);
}

