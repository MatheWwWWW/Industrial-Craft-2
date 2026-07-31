/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.PalettedContainer
 *  net.minecraft.world.level.chunk.PalettedContainerRO
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.items.ITerraformerBP;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ITerraformer;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.logic.terraformer.ITerraformerLogic;
import ic2.core.block.machines.logic.terraformer.InOutTerraformerLogic;
import ic2.core.block.machines.logic.terraformer.RandomTerraformerLogic;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.BiomePacket;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TerraformerTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITerraformer,
IClickable,
ISpecialWrenchable,
ITileActivityProvider {
    ITerraformerLogic logic;
    int inactiveTime = 0;
    IAudioSource source;
    @NetworkInfo
    public boolean tfbp = false;

    public TerraformerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1, 512, 100000);
        this.getInventoryHandler().setActive(false);
        this.addNetworkFields("tfbp");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TERRAFORMER;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        CompoundTag data = compound.m_128469_("logic");
        this.logic = this.createLogic(data.m_128451_("type"));
        if (this.logic != null) {
            this.logic.load(data);
        }
        this.tfbp = !((ItemStack)this.inventory.get(0)).m_41619_();
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.logic != null) {
            compound.m_128365_("logic", (Tag)this.logic.save(new CompoundTag()));
        }
    }

    private ITerraformerLogic createLogic(int type) {
        switch (type) {
            case 1: {
                return new InOutTerraformerLogic();
            }
            case 2: {
                return new RandomTerraformerLogic();
            }
        }
        return null;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        Item item = stack.m_41720_();
        if (item instanceof ITerraformerBP) {
            ITerraformerBP blueprint = (ITerraformerBP)item;
            if (!blueprint.canInsert(stack, player, this.f_58857_, this.f_58858_)) {
                return false;
            }
            if (this.isRendering()) {
                return true;
            }
            if (!((ItemStack)this.inventory.get(0)).m_41619_()) {
                this.ejectBluePrint();
            }
            this.logic = this.createLogic(blueprint.isRandomized(stack) ? 2 : 1);
            this.inventory.set(0, (Object)StackUtil.copyWithSize(stack, 1));
            this.tfbp = true;
            this.updateTileField("tfbp");
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        return !this.tfbp ? null : new AABB(BlockPos.f_121853_);
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        if (!((ItemStack)this.inventory.get(0)).m_41619_()) {
            this.ejectBluePrint();
            return true;
        }
        return false;
    }

    public void ejectBluePrint() {
        if (this.isSimulating()) {
            Block.m_49840_((Level)this.f_58857_, (BlockPos)this.f_58858_, (ItemStack)((ItemStack)this.inventory.get(0)));
        }
        this.inventory.set(0, (Object)ItemStack.f_41583_);
        this.logic = null;
        this.tfbp = false;
        this.updateTileField("tfbp");
    }

    @Override
    public void onTick() {
        int energy;
        boolean active = false;
        ITerraformerBP terra = this.getBluePrint();
        if (terra != null && this.logic != null && this.logic.canRun() && this.hasEnergy(energy = terra.getEnergyUsage((ItemStack)this.inventory.get(0)))) {
            active = true;
            this.useEnergy(this.logic.run(this, (ItemStack)this.inventory.get(0), terra) ? energy : energy / 10);
        }
        if (active) {
            this.setActive(true);
            this.inactiveTime = 0;
        } else if (this.isActive() && this.inactiveTime++ > 60) {
            this.setActive(false);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.source = null;
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            if (this.source == null || !this.source.isValid()) {
                this.source = IC2.AUDIO.createSource(this, IC2Sounds.TERRA_FORMER_PROCESSING);
                if (this.source == null) {
                    return;
                }
            }
            this.source.playStop(this.isActive());
        }
    }

    @Override
    public boolean setBiome(Level world, BlockPos pos, ResourceLocation biomeKey) {
        ChunkAccess chunk = world.m_6522_(pos.m_123341_() >> 4, pos.m_123343_() >> 4, ChunkStatus.f_62326_, false);
        if (chunk == null) {
            return false;
        }
        Optional biomeRef = IC2.PLATFORM.getBiomeRegistry(world).m_203636_(ResourceKey.m_135785_((ResourceKey)Registry.f_122885_, (ResourceLocation)biomeKey));
        if (biomeRef.isEmpty()) {
            return false;
        }
        Holder biome = (Holder)biomeRef.get();
        boolean changed = false;
        int m = world.m_151559_();
        for (int i = 0; i < m; ++i) {
            LevelChunkSection section = chunk.m_183278_(i);
            PalettedContainerRO palette = section.m_187996_();
            if (!(palette instanceof PalettedContainer)) continue;
            PalettedContainer chunkPalette = (PalettedContainer)palette;
            for (int y = 0; y < 4; ++y) {
                if (((Holder)chunkPalette.m_63091_(pos.m_123341_() & 3, y, pos.m_123343_() & 3, (Object)biome)).get() == biome.get()) continue;
                changed = true;
            }
        }
        if (changed) {
            PacketManager.INSTANCE.sendToAllChunkWatchers((LevelChunk)chunk, new BiomePacket(pos, biomeKey));
            return true;
        }
        return false;
    }

    public ITerraformerBP getBluePrint() {
        ItemStack stack = (ItemStack)this.inventory.get(0);
        return stack.m_41720_() instanceof ITerraformerBP ? (ITerraformerBP)stack.m_41720_() : null;
    }
}

