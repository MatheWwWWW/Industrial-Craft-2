/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.tileentity;

import ic2.core.IC2;
import ic2.core.block.comp.Obscuration;
import ic2.core.block.misc.WallBlock;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.ref.Ic2BlockEntities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TileEntityWall
extends Ic2TileEntity {
    protected final Obscuration obscuration;
    private DyeColor color = WallBlock.DEFAULT_COLOR;
    private volatile WallRenderState renderState;

    public TileEntityWall(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.WALL, blockPos, blockState);
        this.obscuration = this.addComponent(new Obscuration(this, new Runnable(){

            @Override
            public void run() {
                IC2.network.get(true).updateTileEntityField(TileEntityWall.this, "obscuration");
            }
        }));
    }

    public void setColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.color = DyeColor.m_41053_((int)compoundTag.m_128445_("color"));
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128344_("color", (byte)this.color.m_41060_());
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (this.m_58904_().f_46443_) {
            this.updateRenderState();
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("color");
        arrayList.add("obscuration");
        arrayList.addAll(super.getNetworkedFields());
        return arrayList;
    }

    @Override
    public void onNetworkUpdate(String string) {
        super.onNetworkUpdate(string);
        if (this.updateRenderState()) {
            this.rerender();
        }
    }

    @Override
    protected boolean recolor(Direction direction, DyeColor dyeColor) {
        if (dyeColor == this.color) {
            return false;
        }
        this.color = dyeColor;
        if (!this.m_58904_().f_46443_) {
            IC2.network.get(true).updateTileEntityField(this, "obscuration");
            this.m_6596_();
        } else if (this.updateRenderState()) {
            this.rerender();
        }
        return true;
    }

    @Override
    protected ItemStack getPickBlock(Player player, BlockHitResult blockHitResult) {
        return new ItemStack((ItemLike)WallBlock.get(this.color));
    }

    private boolean updateRenderState() {
        WallRenderState wallRenderState = new WallRenderState(this.color, this.obscuration.getRenderState());
        if (wallRenderState.equals(this.renderState)) {
            return false;
        }
        this.renderState = wallRenderState;
        return true;
    }

    public static class WallRenderState {
        public final DyeColor color;
        public final Obscuration.ObscurationData[] obscurations;

        public WallRenderState(DyeColor dyeColor, Obscuration.ObscurationData[] obscurationDataArray) {
            this.color = dyeColor;
            this.obscurations = obscurationDataArray;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof WallRenderState)) {
                return false;
            }
            WallRenderState wallRenderState = (WallRenderState)object;
            return wallRenderState.color == this.color && Arrays.equals(wallRenderState.obscurations, this.obscurations);
        }

        public int hashCode() {
            return this.color.hashCode() * 31 + Arrays.hashCode(this.obscurations);
        }

        public String toString() {
            return "WallState<" + this.color + ", " + Arrays.toString(this.obscurations) + ">";
        }
    }
}

