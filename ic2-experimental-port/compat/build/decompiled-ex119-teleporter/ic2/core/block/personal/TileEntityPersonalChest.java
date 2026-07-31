/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.util.DelegatingInventory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TileEntityPersonalChest
extends TileEntityInventory
implements IPersonalBlock,
IHasGui {
    private GameProfile owner = null;
    private static final int openingSteps = 10;
    private static final List<AABB> aabbs = Arrays.asList(new AABB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375));
    public final InvSlot contentSlot;
    private final Set<Player> usingPlayers = Collections.newSetFromMap(new WeakHashMap());
    private int usingPlayerCount;
    private byte lidAngle;
    private byte prevLidAngle;

    public TileEntityPersonalChest(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.PERSONAL_CHEST, blockPos, blockState);
        this.contentSlot = new InvSlot(this, "content", InvSlot.Access.NONE, 54);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        if (compoundTag.m_128441_("ownerGameProfile")) {
            this.owner = NbtUtils.m_129228_((CompoundTag)compoundTag.m_128469_("ownerGameProfile"));
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.owner != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            NbtUtils.m_129230_((CompoundTag)compoundTag2, (GameProfile)this.owner);
            compoundTag.m_128365_("ownerGameProfile", (Tag)compoundTag2);
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        this.prevLidAngle = this.lidAngle;
        if (this.usingPlayerCount > 0 && this.lidAngle <= 0) {
            Level level = this.m_58904_();
            level.m_5594_(null, this.f_58858_, SoundEvents.f_11749_, SoundSource.BLOCKS, 0.5f, level.f_46441_.m_188501_() * 0.1f + 0.9f);
        }
        if (this.usingPlayerCount == 0 && this.lidAngle > 0 || this.usingPlayerCount > 0 && this.lidAngle < 10) {
            this.lidAngle = this.usingPlayerCount > 0 ? (byte)(this.lidAngle + 1) : (byte)(this.lidAngle - 1);
            byte by = 5;
            if (this.lidAngle < by && this.prevLidAngle >= by) {
                Level level = this.m_58904_();
                level.m_5594_(null, this.f_58858_, SoundEvents.f_11747_, SoundSource.BLOCKS, 0.5f, level.f_46441_.m_188501_() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    protected List<AABB> getAabbs(boolean bl) {
        return aabbs;
    }

    @Override
    public void m_5856_(Player player) {
        if (!this.m_58904_().f_46443_) {
            this.usingPlayers.add(player);
            this.updateUsingPlayerCount();
        }
    }

    @Override
    public void m_5785_(Player player) {
        if (!this.m_58904_().f_46443_) {
            this.usingPlayers.remove(player);
            this.updateUsingPlayerCount();
        }
    }

    private void updateUsingPlayerCount() {
        this.usingPlayerCount = this.usingPlayers.size();
        IC2.network.get(true).updateTileEntityField(this, "usingPlayerCount");
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("owner");
        list.add("usingPlayerCount");
        return list;
    }

    @Override
    public boolean wrenchCanRemove(Player player) {
        if (!this.permitsAccess(player.m_36316_())) {
            IC2.sideProxy.messagePlayer(player, "This safe is owned by " + this.owner.getName(), new Object[0]);
            return false;
        }
        if (!this.contentSlot.isEmpty()) {
            IC2.sideProxy.messagePlayer(player, "Can't wrench non-empty safe", new Object[0]);
            return false;
        }
        return true;
    }

    @Override
    public boolean permitsAccess(GameProfile gameProfile) {
        return TileEntityPersonalChest.checkAccess(this, gameProfile);
    }

    @Override
    public Container getPrivilegedInventory(GameProfile gameProfile) {
        if (!this.permitsAccess(gameProfile)) {
            return this;
        }
        return new DelegatingInventory(this){

            @Override
            public int m_6643_() {
                return TileEntityPersonalChest.this.contentSlot.size();
            }

            @Override
            public ItemStack m_8020_(int n) {
                return TileEntityPersonalChest.this.contentSlot.get(n);
            }

            @Override
            public ItemStack m_7407_(int n, int n2) {
                ItemStack itemStack = this.m_8020_(n);
                if (StackUtil.isEmpty(itemStack)) {
                    return StackUtil.emptyStack;
                }
                if (n2 >= StackUtil.getSize(itemStack)) {
                    this.m_6836_(n, StackUtil.emptyStack);
                    return itemStack;
                }
                if (n2 != 0) {
                    if (n2 < 0) {
                        int n3 = Math.min(TileEntityPersonalChest.this.contentSlot.getStackSizeLimit(), itemStack.m_41741_()) - StackUtil.getSize(itemStack);
                        n2 = Math.max(n2, -n3);
                    }
                    itemStack = StackUtil.decSize(itemStack, n2);
                    this.m_6836_(n, itemStack);
                }
                ItemStack itemStack2 = StackUtil.copyWithSize(itemStack, n2);
                return itemStack2;
            }

            @Override
            public ItemStack m_8016_(int n) {
                ItemStack itemStack = this.m_8020_(n);
                if (!StackUtil.isEmpty(itemStack)) {
                    this.m_6836_(n, StackUtil.emptyStack);
                }
                return itemStack;
            }

            @Override
            public void m_6836_(int n, ItemStack itemStack) {
                TileEntityPersonalChest.this.contentSlot.put(n, itemStack);
                this.m_6596_();
            }

            @Override
            public int m_6893_() {
                return TileEntityPersonalChest.this.contentSlot.getStackSizeLimit();
            }

            @Override
            public boolean m_7013_(int n, ItemStack itemStack) {
                return TileEntityPersonalChest.this.contentSlot.accepts(itemStack);
            }
        };
    }

    public static <T extends BlockEntity> boolean checkAccess(T t, GameProfile gameProfile) {
        if (gameProfile == null) {
            return ((IPersonalBlock)t).getOwner() == null;
        }
        GameProfile gameProfile2 = ((IPersonalBlock)t).getOwner();
        if (!t.m_58904_().f_46443_) {
            if (gameProfile2 == null) {
                ((IPersonalBlock)t).setOwner(gameProfile);
                IC2.network.get(true).updateTileEntityField(t, "owner");
                return true;
            }
            if (t.m_58904_().m_7654_().m_6846_().m_11303_(gameProfile)) {
                return true;
            }
        } else if (gameProfile2 == null) {
            return true;
        }
        return gameProfile2.getId() != null ? gameProfile2.getId().equals(gameProfile.getId()) : gameProfile2.getName().equals(gameProfile.getName());
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(GameProfile gameProfile) {
        this.owner = gameProfile;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return false;
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (!this.m_58904_().f_46443_ && !this.permitsAccess(player.m_36316_())) {
            IC2.sideProxy.messagePlayer(player, "This safe is owned by " + this.getOwner().getName(), new Object[0]);
            return InteractionResult.FAIL;
        }
        return super.onActivated(player, interactionHand, direction, vec3);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        this.m_5856_(player);
        return new DynamicContainer<TileEntityInventory>(Ic2ScreenHandlers.DYNAMIC_BE, n, player.m_150109_(), (TileEntityInventory)this, GuiParser.parse(Util.getName(this.getBlockType()), this.getClass())){

            public void m_6877_(Player player) {
                ((TileEntityInventory)this.base).m_5785_(player);
                super.m_6877_(player);
            }
        };
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    public void onScreenClosed(Player player) {
        this.m_5785_(player);
    }

    public float getLidAngle(float f) {
        return Util.lerp(this.prevLidAngle, this.lidAngle, f) / 10.0f;
    }
}

