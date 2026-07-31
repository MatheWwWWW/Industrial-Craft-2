/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.tileentity;

import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Components;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.event.IWorldTickCallback;
import ic2.core.event.TickHandler;
import ic2.core.gui.dynamic.IGuiConditionProvider;
import ic2.core.init.Localization;
import ic2.core.ref.Ic2Items;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class Ic2TileEntity
extends BlockEntity
implements INetworkDataProvider,
INetworkUpdateListener,
IGuiConditionProvider {
    public static final String teBlockName = "teBlk";
    protected static final int lightOpacityTranslucent = 0;
    protected static final int lightOpacityOpaque = 255;
    private static final List<AABB> defaultAabbs = List.of(new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0));
    private static final List<TileEntityComponent> emptyComponents = Collections.emptyList();
    private static final Map<Class<?>, TickSubscription> tickSubscriptions = new IdentityHashMap();
    private static final byte loadStateInitial = 0;
    private static final byte loadStateQueued = 1;
    private static final byte loadStateLoaded = 2;
    private static final byte loadStateUnloaded = 3;
    private static final boolean debugLoad = System.getProperty("ic2.te.debugload") != null;
    private Map<Class<? extends TileEntityComponent>, TileEntityComponent> components;
    private List<TileEntityComponent> updatableComponents;
    private boolean active = false;
    private byte loadState = 0;
    protected final Ic2TileEntityBlock teBlock;
    private boolean enableWorldTick;

    public Ic2TileEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.teBlock = (Ic2TileEntityBlock)blockState.m_60734_();
    }

    public final Ic2TileEntityBlock getBlockType() {
        return this.teBlock;
    }

    public final void m_7651_() {
        if (this.loadState == 2) {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Block, "TE markRemoved for %s at %s.", this, Util.formatPosition(this));
            }
            this.onUnloaded();
        } else {
            if (debugLoad) {
                IC2.log.debug(LogCategory.Block, "Skipping TE markRemoved for %s at %s, state: %d.", this, Util.formatPosition(this), this.loadState);
            }
            this.loadState = (byte)3;
        }
        super.m_7651_();
    }

    public final void m_6339_() {
        super.m_6339_();
        Level level = this.m_58904_();
        if (level == null || this.f_58858_ == null) {
            throw new IllegalStateException("no world/pos");
        }
        if (this.loadState != 0 && this.loadState != 3) {
            throw new IllegalStateException("invalid load state: " + this.loadState);
        }
        this.loadState = 1;
        TickHandler.requestSingleWorldTick(level, new IWorldTickCallback(){

            @Override
            public void onTick(Level level) {
                if (level == Ic2TileEntity.this.m_58904_() && Ic2TileEntity.this.f_58858_ != null && !Ic2TileEntity.this.m_58901_() && Ic2TileEntity.this.loadState == 1 && level.m_46749_(Ic2TileEntity.this.f_58858_) && level.m_8055_(Ic2TileEntity.this.f_58858_).m_60734_() == Ic2TileEntity.this.teBlock && level.m_7702_(Ic2TileEntity.this.f_58858_) == Ic2TileEntity.this) {
                    if (debugLoad) {
                        IC2.log.debug(LogCategory.Block, "TE onLoaded for %s at %s.", Ic2TileEntity.this, Util.formatPosition(Ic2TileEntity.this));
                    }
                    Ic2TileEntity.this.onLoaded();
                } else if (debugLoad) {
                    IC2.log.debug(LogCategory.Block, "Skipping TE init for %s at %s.", Ic2TileEntity.this, Util.formatPosition(Ic2TileEntity.this));
                }
            }
        });
    }

    protected void onLoaded() {
        if (this.loadState != 1) {
            throw new IllegalStateException("invalid load state: " + this.loadState);
        }
        this.loadState = (byte)2;
        this.enableWorldTick = Ic2TileEntity.getTickSubscription(this.getClass()).get(this.f_58857_.f_46443_);
        if (this.components == null) {
            return;
        }
        for (TileEntityComponent tileEntityComponent : this.components.values()) {
            tileEntityComponent.onLoaded();
            if (!tileEntityComponent.enableWorldTick()) continue;
            if (this.updatableComponents == null) {
                this.updatableComponents = new ArrayList<TileEntityComponent>(4);
            }
            this.updatableComponents.add(tileEntityComponent);
        }
    }

    protected void onUnloaded() {
        if (this.loadState == 3) {
            throw new IllegalStateException("invalid load state: " + this.loadState);
        }
        this.loadState = (byte)3;
        if (this.components == null) {
            return;
        }
        for (TileEntityComponent tileEntityComponent : this.components.values()) {
            tileEntityComponent.onUnloaded();
        }
    }

    public void m_142466_(CompoundTag compoundTag) {
        this.active = compoundTag.m_128471_("active");
        if (this.components == null || !compoundTag.m_128425_("components", 10)) {
            return;
        }
        CompoundTag compoundTag2 = compoundTag.m_128469_("components");
        for (String string : compoundTag2.m_128431_()) {
            Object t;
            Class clazz = Components.getClass(string);
            if (clazz == null || (t = this.getComponent(clazz)) == null) {
                IC2.log.warn(LogCategory.Block, "Can't find component %s while loading %s.", string, this);
                continue;
            }
            CompoundTag compoundTag3 = compoundTag2.m_128469_(string);
            ((TileEntityComponent)t).readFromNbt(compoundTag3);
        }
    }

    public void m_183515_(CompoundTag compoundTag) {
        compoundTag.m_128379_("active", this.active);
        if (this.components == null) {
            return;
        }
        CompoundTag compoundTag2 = null;
        for (TileEntityComponent tileEntityComponent : this.components.values()) {
            String string;
            CompoundTag compoundTag3 = tileEntityComponent.writeToNbt();
            if (compoundTag3 == null) continue;
            if (compoundTag2 == null) {
                compoundTag2 = new CompoundTag();
                compoundTag.m_128365_("components", (Tag)compoundTag2);
            }
            if ((string = Components.getId(tileEntityComponent.getClass())) == null) {
                throw new RuntimeException("no component id for " + tileEntityComponent.getClass().getName());
            }
            compoundTag2.m_128365_(string, (Tag)compoundTag3);
        }
    }

    public final boolean canTick() {
        return this.enableWorldTick || this.updatableComponents != null;
    }

    public final void tick() {
        if (this.loadState != 2) {
            return;
        }
        if (this.updatableComponents != null) {
            for (TileEntityComponent tileEntityComponent : this.updatableComponents) {
                tileEntityComponent.onWorldTick();
            }
        }
        if (this.enableWorldTick) {
            if (this.f_58857_.f_46443_) {
                this.updateEntityClient();
            } else {
                this.updateEntityServer();
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
    }

    protected void updateEntityServer() {
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>(3);
        arrayList.add("teBlk=" + Registry.f_122824_.m_7981_((Object)this.teBlock));
        arrayList.add("active");
        return arrayList;
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("active") && this.hasActiveTexture() || string.equals("facing")) {
            this.rerender();
        }
    }

    @Environment(value=EnvType.CLIENT)
    private boolean hasActiveTexture() {
        return this.teBlock.canActive();
    }

    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
    }

    protected VoxelShape getOutlineShape(CollisionContext collisionContext) {
        return this.getShape(false);
    }

    protected VoxelShape getCollisionShape(CollisionContext collisionContext) {
        return this.getShape(true);
    }

    protected VoxelShape getCullingShape() {
        return this.getShape(false);
    }

    private VoxelShape getShape(boolean bl) {
        List<AABB> list = this.getAabbs(bl);
        if (list == defaultAabbs) {
            return Shapes.m_83144_();
        }
        if (list.isEmpty()) {
            throw new RuntimeException("No AABBs for " + this);
        }
        if (list.size() == 1) {
            return Shapes.m_83064_((AABB)list.get(0));
        }
        VoxelShape voxelShape = null;
        for (AABB aABB : list) {
            VoxelShape voxelShape2 = Shapes.m_83064_((AABB)aABB);
            if (voxelShape == null) {
                voxelShape = voxelShape2;
                continue;
            }
            voxelShape = Shapes.m_83110_((VoxelShape)voxelShape, (VoxelShape)voxelShape2);
        }
        return voxelShape;
    }

    protected void onEntityCollision(Entity entity) {
    }

    protected boolean isNormalCube() {
        List<AABB> list = this.getAabbs(false);
        if (list == defaultAabbs) {
            return true;
        }
        if (list.size() != 1) {
            return false;
        }
        AABB aABB = list.get(0);
        return aABB.f_82288_ <= 0.0 && aABB.f_82289_ <= 0.0 && aABB.f_82290_ <= 0.0 && aABB.f_82291_ >= 1.0 && aABB.f_82292_ >= 1.0 && aABB.f_82293_ >= 1.0;
    }

    protected int getLightOpacity() {
        return this.isNormalCube() ? 255 : 0;
    }

    protected int getLightValue() {
        return 0;
    }

    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (!(this instanceof IHasGui) || this.f_58857_ == null) {
            return InteractionResult.PASS;
        }
        if (this.f_58857_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        return ((IHasGui)((Object)this)).openManagedBe(player, interactionHand) ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    protected InteractionResult onClicked(Player player) {
        return InteractionResult.PASS;
    }

    protected void onNeighborChange(Block block, BlockPos blockPos) {
        if (this.components != null) {
            for (TileEntityComponent tileEntityComponent : this.components.values()) {
                tileEntityComponent.onNeighborChange(block, blockPos);
            }
        }
    }

    protected boolean recolor(Direction direction, DyeColor dyeColor) {
        return false;
    }

    protected void onExploded(Explosion explosion) {
    }

    protected void onBlockBreak() {
    }

    protected boolean onRemovedByPlayer(Player player, boolean bl) {
        return true;
    }

    protected ItemStack getPickBlock(Player player, BlockHitResult blockHitResult) {
        return new ItemStack((ItemLike)this.teBlock);
    }

    protected List<ItemStack> getSelfDrops(int n, boolean bl) {
        ItemStack itemStack = this.getPickBlock(null, null);
        if ((itemStack = this.adjustDrop(itemStack, bl)) == null) {
            return Collections.emptyList();
        }
        return List.of(itemStack);
    }

    protected List<ItemStack> getAuxDrops(int n) {
        return Collections.emptyList();
    }

    protected boolean canEntityDestroy(Entity entity) {
        return true;
    }

    public Direction getFacing() {
        if (this.teBlock.facingProperty == null) {
            return Direction.NORTH;
        }
        return (Direction)this.m_58900_().m_61143_(this.teBlock.facingProperty);
    }

    protected boolean canSetFacingWrench(Direction direction, Player player) {
        if (!this.teBlock.allowWrenchRotating()) {
            return false;
        }
        if (direction == this.getFacing()) {
            return false;
        }
        return this.getSupportedFacings().contains(direction);
    }

    protected boolean setFacingWrench(Level level, Direction direction, Player player) {
        if (!this.canSetFacingWrench(direction, player)) {
            return false;
        }
        this.setFacing(level, direction);
        return true;
    }

    protected boolean wrenchCanRemove(Player player) {
        return true;
    }

    protected Direction getPlacementFacing(LivingEntity livingEntity, Direction direction) {
        return direction;
    }

    protected List<AABB> getAabbs(boolean bl) {
        return defaultAabbs;
    }

    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        if (bl) {
            return itemStack;
        }
        return switch (this.teBlock.getDefaultDrop()) {
            default -> throw new IncompatibleClassChangeError();
            case Ic2TileEntityBlock.DefaultDrop.Self -> itemStack;
            case Ic2TileEntityBlock.DefaultDrop.None -> null;
            case Ic2TileEntityBlock.DefaultDrop.Generator -> new ItemStack((ItemLike)Ic2Items.GENERATOR);
            case Ic2TileEntityBlock.DefaultDrop.Machine -> new ItemStack((ItemLike)Ic2Items.MACHINE);
            case Ic2TileEntityBlock.DefaultDrop.AdvMachine -> new ItemStack((ItemLike)Ic2Items.ADVANCED_MACHINE);
        };
    }

    protected Set<Direction> getSupportedFacings() {
        return this.teBlock.getSupportedFacings();
    }

    protected void setFacing(Level level, Direction direction) {
        if (direction == null) {
            throw new NullPointerException("null facing");
        }
        if (this.getFacing().ordinal() == direction.ordinal()) {
            throw new IllegalArgumentException("unchanged facing");
        }
        if (!this.getSupportedFacings().contains(direction)) {
            throw new IllegalArgumentException("invalid facing: " + direction + ", supported: " + this.getSupportedFacings());
        }
        BlockState blockState = (BlockState)level.m_8055_(this.f_58858_).m_61124_(this.teBlock.facingProperty, (Comparable)direction);
        level.m_46597_(this.f_58858_, blockState);
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean bl) {
        if (!this.teBlock.canActive()) {
            return;
        }
        if (this.active == bl) {
            return;
        }
        this.active = bl;
        IC2.network.get(true).updateTileEntityField(this, "active");
    }

    @Override
    public boolean getGuiState(String string) {
        if ("active".equals(string)) {
            return this.getActive();
        }
        throw new IllegalArgumentException("Unexpected GUI value requested: " + string);
    }

    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        if (this.hasComponent(Energy.class)) {
            Energy energy = this.getComponent(Energy.class);
            if (!energy.getSourceDirs().isEmpty()) {
                list.add(Localization.translate("ic2.item.tooltip.PowerTier", energy.getSourceTier()));
            } else if (!energy.getSinkDirs().isEmpty()) {
                list.add(Localization.translate("ic2.item.tooltip.PowerTier", energy.getSinkTier()));
            }
        }
    }

    protected final <T extends TileEntityComponent> T addComponent(T t) {
        TileEntityComponent tileEntityComponent;
        if (t == null) {
            throw new NullPointerException("null component");
        }
        if (this.components == null) {
            this.components = new IdentityHashMap<Class<? extends TileEntityComponent>, TileEntityComponent>(4);
        }
        if ((tileEntityComponent = this.components.put(t.getClass(), t)) != null) {
            throw new RuntimeException("conflicting component while adding " + t + ", already used by " + tileEntityComponent + ".");
        }
        return t;
    }

    public boolean hasComponent(Class<? extends TileEntityComponent> clazz) {
        if (this.components == null) {
            return false;
        }
        return this.components.containsKey(clazz);
    }

    public <T extends TileEntityComponent> T getComponent(Class<T> clazz) {
        if (this.components == null) {
            return null;
        }
        return (T)this.components.get(clazz);
    }

    public final Iterable<? extends TileEntityComponent> getComponents() {
        if (this.components == null) {
            return emptyComponents;
        }
        return this.components.values();
    }

    protected final void rerender() {
        BlockState blockState = this.m_58900_();
        Objects.requireNonNull(this.m_58904_()).m_7260_(this.f_58858_, blockState, blockState, 2);
        if (this.teBlock.canActive() && this.f_58857_ != null) {
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)blockState.m_61124_((Property)Ic2TileEntityBlock.ACTIVE, (Comparable)Boolean.valueOf(this.active)));
        }
    }

    private static synchronized TickSubscription getTickSubscription(Class<?> clazz) {
        TickSubscription tickSubscription = tickSubscriptions.get(clazz);
        if (tickSubscription == null) {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = IC2.envProxy.isClientEnv();
            for (Class<?> clazz2 = clazz; clazz2 != Ic2TileEntity.class && (!bl && bl3 || !bl2); clazz2 = clazz2.getSuperclass()) {
                boolean bl4;
                if (!bl && bl3) {
                    bl4 = true;
                    try {
                        clazz2.getDeclaredMethod("updateEntityClient", new Class[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        bl4 = false;
                    }
                    if (bl4) {
                        bl = true;
                    }
                }
                if (bl2) continue;
                bl4 = true;
                try {
                    clazz2.getDeclaredMethod("updateEntityServer", new Class[0]);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    bl4 = false;
                }
                if (!bl4) continue;
                bl2 = true;
            }
            tickSubscription = bl ? (bl2 ? TickSubscription.Both : TickSubscription.Client) : (bl2 ? TickSubscription.Server : TickSubscription.None);
            tickSubscriptions.put(clazz, tickSubscription);
        }
        return tickSubscription;
    }

    private static enum TickSubscription {
        None(false, false),
        Client(true, false),
        Server(false, true),
        Both(true, true);

        final boolean client;
        final boolean server;

        private TickSubscription(boolean bl, boolean bl2) {
            this.client = bl;
            this.server = bl2;
        }

        boolean get(boolean bl) {
            return bl ? this.client : this.server;
        }
    }
}

