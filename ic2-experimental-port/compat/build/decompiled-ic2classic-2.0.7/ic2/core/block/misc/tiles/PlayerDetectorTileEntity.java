/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap$Entry
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.misc.tiles;

import ic2.api.blocks.PainterHelper;
import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.misc.tiles.container.PlayerDetectorContainer;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.ToIntFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class PlayerDetectorTileEntity
extends BaseTileEntity
implements ITickListener,
IRedstoneListener,
IRedstoneProvider,
ITileGui,
IPersonalTile,
ITileActivityProvider,
IAreaOfEffect,
ICamouflagable,
IClickable {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int xRadius = 1;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int yRadius = 2;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int zRadius = 1;
    @NetworkInfo
    public UUID owner;
    @NetworkInfo
    public int mode;
    @NetworkInfo
    public byte foamed;
    @NetworkInfo
    CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);
    public int networkId = -1;
    public Object2LongMap<String> trackerId = new Object2LongLinkedOpenHashMap();

    public PlayerDetectorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("xRadius", "yRadius", "zRadius");
        this.addNetworkFields("mode", "owner", "foamed", "storage");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PLAYER_DETECTOR;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.xRadius = compound.m_128451_("xRadius");
        this.yRadius = compound.m_128451_("yRadius");
        this.zRadius = compound.m_128451_("zRadius");
        this.owner = compound.m_128403_("owner") ? compound.m_128342_("owner") : null;
        this.mode = compound.m_128451_("mode");
        this.storage.load(compound.m_128469_("camouflage"));
        this.foamed = compound.m_128445_("foamed");
        for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("tracker", 10), CompoundTag.class)) {
            this.trackerId.put((Object)data.m_128461_("name"), data.m_128454_("time"));
        }
        this.changeFoam(this.foamed, true);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "xRadius", (int)((byte)this.xRadius), 0);
        NBTUtils.putByte(compound, "yRadius", (int)((byte)this.yRadius), 0);
        NBTUtils.putByte(compound, "zRadius", (int)((byte)this.zRadius), 0);
        if (this.owner != null) {
            compound.m_128362_("owner", this.owner);
        }
        compound.m_128344_("mode", (byte)this.mode);
        NBTUtils.putByte(compound, "foamed", (int)this.foamed, 0);
        NBTUtils.putIf(compound, "camouflage", this.storage.save(new CompoundTag()), this.foamed > 0);
        ListTag list = new ListTag();
        for (Object2LongMap.Entry entry : this.trackerId.object2LongEntrySet()) {
            CompoundTag data = new CompoundTag();
            data.m_128359_("name", (String)entry.getKey());
            data.m_128356_("time", entry.getLongValue());
            list.add((Object)data);
        }
        compound.m_128365_("tracked", (Tag)list);
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.foamed == 2 && this.storage.canApply(dir, container)) {
            this.storage.setTexture(DirectionList.ofFacing(dir), container);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    public boolean setColor(Direction dir, DyeColor color) {
        if (this.foamed == 2 && this.isSimulating() && this.storage.canApply(dir == null ? DirectionList.ALL : DirectionList.ofFacing(dir), color)) {
            this.storage.setColor(dir == null ? DirectionList.ALL : DirectionList.ofFacings(dir), color);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.foamed > 1;
    }

    @Override
    public boolean removeCamouflage() {
        return this.changeFoam((byte)0, false);
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        return this.foamed != 2 ? ModelData.EMPTY : ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T)).build();
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null && owner != null) {
            this.owner = owner;
        }
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public boolean canDoAction(UUID user, Action action, boolean ignorePublic) {
        if (this.owner == null) {
            this.setOwner(user);
            return true;
        }
        if (this.owner.equals(user) || !ignorePublic && this.mode == 0 || IC2.PLATFORM.isOp(user)) {
            return true;
        }
        if (this.mode == 2) {
            return false;
        }
        Friend friend = FriendManager.getFriends().getFriend(this.owner, user);
        return friend != null && friend.canApplyAction(action);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41720_() == IC2Blocks.CFOAM_WET.m_5456_()) {
            if (this.foamed == 0) {
                this.changeFoam((byte)1, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        } else {
            BlockState dustState;
            if (stack.m_204117_(ItemTags.f_13137_) && this.foamed == 1) {
                this.changeFoam((byte)2, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
            if (this.foamed == 1 && PainterHelper.CONCRETE_DUST.contains((dustState = Block.m_49814_((Item)stack.m_41720_()).m_49966_()).m_60734_())) {
                this.storage.setColor(DirectionList.ALL, PainterHelper.INSTANCE.getColor(dustState));
                this.changeFoam((byte)2, false);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.canDoAction(player.m_20148_(), Action.PLAYER_DETECTOR_SETTINGS, false) && (this.foamed < 2 || player.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && player.m_21120_(InteractionHand.OFF_HAND).m_41619_());
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.canDoAction(player.m_20148_(), Action.PLAYER_DETECTOR_SETTINGS, false);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new PlayerDetectorContainer(this, player, windowID);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("storage") || fields.contains("foamed")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 0) {
            this.xRadius = Mth.m_14045_((int)value, (int)0, (int)64);
            this.updateTileField("xRadius");
        } else if (key == 1) {
            this.yRadius = Mth.m_14045_((int)value, (int)0, (int)64);
            this.updateTileField("yRadius");
        } else if (key == 2) {
            this.zRadius = Mth.m_14045_((int)value, (int)0, (int)64);
            this.updateTileField("zRadius");
        } else if (key == 3) {
            if (entity.m_20148_().equals(this.owner)) {
                this.mode = (this.mode + 1) % 3;
                this.updateTileField("mode");
            } else {
                entity.m_5661_((Component)this.translate("gui.ic2.personal.perm"), false);
            }
        }
        super.onClientDataReceived(entity, key, value);
    }

    @Override
    public void onTick() {
        if (this.clock(5) && this.setActive(this.isPlayerNearby())) {
            this.notifyChanges(true, DirectionList.ALL, this.m_58899_().m_121945_(this.getFacing().m_122424_()));
        }
    }

    @Override
    public int getAreaOfEffectColor() {
        return ColorUtils.RED & 0x80FFFFFF;
    }

    @Override
    public void setVisualizationId(int id) {
        this.networkId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.networkId;
    }

    @Override
    public AABB getAreaOfEffect() {
        Direction dir = this.getFacing();
        return new AABB(this.m_58899_()).m_82377_((double)this.xRadius * (1.0 - (double)Math.abs(dir.m_122429_())), (double)this.yRadius * (1.0 - (double)Math.abs(dir.m_122430_())), (double)this.zRadius * (1.0 - (double)Math.abs(dir.m_122431_()))).m_82363_((double)(dir.m_122429_() * this.xRadius), (double)(dir.m_122430_() * this.yRadius), (double)(dir.m_122431_() * this.zRadius));
    }

    public boolean isPlayerNearby() {
        List players = this.f_58857_.m_6907_();
        AABB box = this.getAreaOfEffect();
        Vec3 base = Vec3.m_82539_((Vec3i)this.m_58899_());
        double closest = Double.MAX_VALUE;
        Player closest_player = null;
        int m = players.size();
        for (int i = 0; i < m; ++i) {
            Player player = (Player)players.get(i);
            if (!box.m_82393_(player.m_20185_(), player.m_20186_(), player.m_20189_()) || !this.canDoAction(player.m_20148_(), Action.PLAYER_DETECTOR_DETECTION, false)) continue;
            double dist = player.m_20238_(base);
            if (dist < closest) {
                closest = dist;
                closest_player = player;
            }
            this.trackerId.put((Object)player.m_36316_().getName(), this.f_58857_.m_46467_());
        }
        return closest_player != null;
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return this.isActive() ? 15 : 0;
    }

    @Override
    public boolean allowWeakSignal(Direction dir) {
        return false;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.tickFoamed();
        }
    }

    public boolean changeFoam(byte level, boolean duringLoad) {
        if (this.foamed == level) {
            return false;
        }
        if (this.isSimulating()) {
            this.foamed = level;
            if (this.foamed == 1) {
                this.storage.setColor(DirectionList.ALL, DyeColor.LIGHT_GRAY);
                if (!duringLoad) {
                    this.updateTileField("storage");
                }
                this.tickFoamed();
            }
            if (!duringLoad) {
                this.updateTileField("foamed");
                this.onStateChanged();
            }
        }
        return true;
    }

    private void tickFoamed() {
        if (this.foamed != 1) {
            return;
        }
        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, new ToIntFunction<Level>(){

            @Override
            public int applyAsInt(Level world) {
                if (PlayerDetectorTileEntity.this.m_58901_() || PlayerDetectorTileEntity.this.foamed != 1) {
                    return 0;
                }
                if (world.m_46472_().equals((Object)Level.f_46429_)) {
                    if (world.f_46441_.m_188503_(5) == 0) {
                        PlayerDetectorTileEntity.this.changeFoam((byte)2, false);
                        return 0;
                    }
                    return 20;
                }
                if (world.m_46803_(PlayerDetectorTileEntity.this.f_58858_) * 6 > world.f_46441_.m_188503_(1000)) {
                    PlayerDetectorTileEntity.this.changeFoam((byte)2, false);
                    return 0;
                }
                return 500;
            }
        });
    }
}

