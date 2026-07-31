/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.machine.tileentity;

import ic2.api.item.ITerraformingBP;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.StackUtil;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileEntityTerra
extends TileEntityElectricMachine {
    public int failedAttempts = 0;
    private BlockPos lastPos;
    public AudioSource audioSource;
    public int inactiveTicks = 0;
    public final InvSlotConsumableClass tfbpSlot = new InvSlotConsumableClass(this, "tfbp", 1, ITerraformingBP.class);

    public TileEntityTerra(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.TERRAFORMER, blockPos, blockState, 100000, 4);
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        ItemStack itemStack = this.tfbpSlot.get();
        if (!StackUtil.isEmpty(itemStack)) {
            ITerraformingBP iTerraformingBP = (ITerraformingBP)itemStack.m_41720_();
            if (this.energy.getEnergy() >= iTerraformingBP.getConsume(itemStack)) {
                BlockPos blockPos;
                bl = true;
                Level level = this.m_58904_();
                if (this.lastPos != null) {
                    int n = iTerraformingBP.getRange(itemStack) / 10;
                    blockPos = new BlockPos(this.lastPos.m_123341_() - level.f_46441_.m_188503_(n + 1) + level.f_46441_.m_188503_(n + 1), this.f_58858_.m_123342_(), this.lastPos.m_123343_() - level.f_46441_.m_188503_(n + 1) + level.f_46441_.m_188503_(n + 1));
                } else {
                    if (this.failedAttempts > 4) {
                        this.failedAttempts = 4;
                    }
                    int n = iTerraformingBP.getRange(itemStack) * (this.failedAttempts + 1) / 5;
                    blockPos = new BlockPos(this.f_58858_.m_123341_() - level.f_46441_.m_188503_(n + 1) + level.f_46441_.m_188503_(n + 1), this.f_58858_.m_123342_(), this.f_58858_.m_123343_() - level.f_46441_.m_188503_(n + 1) + level.f_46441_.m_188503_(n + 1));
                }
                if (iTerraformingBP.terraform(itemStack, level, blockPos)) {
                    this.energy.useEnergy(iTerraformingBP.getConsume(itemStack));
                    this.failedAttempts = 0;
                    this.lastPos = blockPos;
                } else {
                    this.energy.useEnergy(iTerraformingBP.getConsume(itemStack) / 10.0);
                    ++this.failedAttempts;
                    this.lastPos = null;
                }
            }
        }
        if (bl) {
            this.inactiveTicks = 0;
            this.setActive(true);
        } else if (!bl && this.getActive() && this.inactiveTicks++ > 30) {
            this.setActive(false);
        }
    }

    @Override
    protected InteractionResult onActivated(final Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        final Level level = this.m_58904_();
        if (!player.m_6144_() && !level.f_46443_) {
            if (this.ejectBlueprint()) {
                return InteractionResult.SUCCESS;
            }
            ItemStack itemStack = StackUtil.consumeAndGet(player, interactionHand, (Predicate<ItemStack>)new com.google.common.base.Predicate<ItemStack>(){

                public boolean apply(ItemStack itemStack) {
                    Item item = itemStack.m_41720_();
                    return item instanceof ITerraformingBP && ((ITerraformingBP)item).canInsert(itemStack, player, level, TileEntityTerra.this.f_58858_);
                }
            }, 1);
            if (!StackUtil.isEmpty(itemStack)) {
                this.insertBlueprint(itemStack);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    private boolean ejectBlueprint() {
        ItemStack itemStack = this.tfbpSlot.get();
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        StackUtil.dropAsEntity(this.m_58904_(), this.f_58858_, itemStack);
        this.tfbpSlot.clear();
        return true;
    }

    private void insertBlueprint(ItemStack itemStack) {
        if (!this.tfbpSlot.isEmpty()) {
            throw new IllegalStateException("not empty");
        }
        this.tfbpSlot.put(itemStack);
    }

    public static BlockPos getFirstSolidBlockFrom(Level level, BlockPos blockPos, int n) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(blockPos.m_123341_(), blockPos.m_123342_() + n, blockPos.m_123343_());
        while (mutableBlockPos.m_123342_() >= 0) {
            BlockState blockState = level.m_8055_((BlockPos)mutableBlockPos);
            if (blockState.m_60804_((BlockGetter)level, blockPos)) {
                return mutableBlockPos.m_7949_();
            }
            mutableBlockPos.m_122173_(Direction.DOWN);
        }
        return null;
    }

    public static BlockPos getFirstBlockFrom(Level level, BlockPos blockPos, int n) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(blockPos.m_123341_(), blockPos.m_123342_() + n, blockPos.m_123343_());
        while (mutableBlockPos.m_123342_() >= 0) {
            if (!level.m_46859_((BlockPos)mutableBlockPos)) {
                return new BlockPos((Vec3i)mutableBlockPos);
            }
            mutableBlockPos.m_122173_(Direction.DOWN);
        }
        return null;
    }

    public static boolean switchGround(Level level, BlockPos blockPos, Block block, BlockState blockState, boolean bl) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_());
        while (mutableBlockPos.m_123342_() >= 0) {
            Block block2 = level.m_8055_((BlockPos)mutableBlockPos).m_60734_();
            if (bl && block2 != block || !bl && block2 == block) break;
            mutableBlockPos.m_122173_(Direction.DOWN);
        }
        if (bl && mutableBlockPos.m_123342_() == blockPos.m_123342_() || !bl && mutableBlockPos.m_123342_() < 0) {
            return false;
        }
        level.m_46597_(bl ? mutableBlockPos.m_7494_() : new BlockPos((Vec3i)mutableBlockPos), blockState);
        return true;
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("active")) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Terraformers/TerraformerGenericloop.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }
            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }
        super.onNetworkUpdate(string);
    }
}

