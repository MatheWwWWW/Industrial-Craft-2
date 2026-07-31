/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.reactor.tileentity;

import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorVessel;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@NotClassic
public class TileEntityReactorAccessHatch
extends TileEntityReactorVessel
implements Container {
    public TileEntityReactorAccessHatch(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityReactorVessel>)Ic2BlockEntities.REACTOR_ACCESS_HATCH, blockPos, blockState);
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        if (tileEntityNuclearReactorElectric != null) {
            return tileEntityNuclearReactorElectric.onActivated(player, interactionHand, direction, vec3);
        }
        return InteractionResult.PASS;
    }

    public int m_6643_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6643_() : 0;
    }

    public boolean m_7983_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7983_() : true;
    }

    public ItemStack m_8020_(int n) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_8020_(n) : null;
    }

    public ItemStack m_7407_(int n, int n2) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7407_(n, n2) : null;
    }

    public ItemStack m_8016_(int n) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_8016_(n) : null;
    }

    public void m_6836_(int n, ItemStack itemStack) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_6836_(n, itemStack);
        }
    }

    public int m_6893_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6893_() : 0;
    }

    public boolean m_6542_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_6542_(player) : false;
    }

    public void m_5856_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_5856_(player);
        }
    }

    public void m_5785_(Player player) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_5785_(player);
        }
    }

    public boolean m_7013_(int n, ItemStack itemStack) {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        return tileEntityNuclearReactorElectric != null ? tileEntityNuclearReactorElectric.m_7013_(n, itemStack) : false;
    }

    public void m_6211_() {
        TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = this.getReactorInstance();
        if (tileEntityNuclearReactorElectric != null) {
            tileEntityNuclearReactorElectric.m_6211_();
        }
    }
}

