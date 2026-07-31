/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.tubes.ILimiterTube;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.LimiterTubeContainer;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.SynchronizedList;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LimiterTubeTileEntity
extends TubeTileEntity
implements ILimiterTube,
ITileGui {
    public EnumSet<DyeColor> validColors = EnumSet.noneOf(DyeColor.class);
    @NetworkInfo
    public SynchronizedList<DyeColor> usedColors = new SynchronizedList<DyeColor>(IOutputBuffer::writeEnum, T -> T.readEnum(DyeColor.class));

    public LimiterTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("usedColors");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.LIMITER_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        byte[] data = new byte[this.usedColors.size()];
        int m = this.usedColors.size();
        for (int i = 0; i < m; ++i) {
            data[i] = (byte)((DyeColor)this.usedColors.get(i)).m_41060_();
        }
        compound.m_128382_("colors", data);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        byte[] data = compound.m_128463_("colors");
        for (int i = 0; i < data.length; ++i) {
            DyeColor dye = DyeColor.m_41053_((int)data[i]);
            this.usedColors.add(dye);
            this.validColors.add(dye);
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            if (value >= 0 && value < this.usedColors.size()) {
                this.validColors.remove(this.usedColors.remove(value));
                this.updateGuiField("usedColors");
                TubeNet.INSTANCE.updateTube(this);
            }
        } else if (key == 2) {
            DyeColor color = DyeColor.m_41053_((int)value);
            if (this.usedColors.contains(color)) {
                return;
            }
            this.usedColors.add(color);
            this.validColors.add(color);
            this.updateGuiField("usedColors");
            TubeNet.INSTANCE.updateTube(this);
        }
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new LimiterTubeContainer(this, player, windowID);
    }

    @Override
    public EnumSet<DyeColor> getValidColors() {
        return this.validColors;
    }
}

