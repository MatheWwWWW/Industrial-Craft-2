/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTUtil
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.util.DelegatingInventory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPersonalChest
extends TileEntityInventory
implements IPersonalBlock,
IHasGui {
    private GameProfile owner = null;
    private static final int openingSteps = 10;
    private static final List<AxisAlignedBB> aabbs = Arrays.asList(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375));
    public final InvSlot contentSlot;
    private final Set<EntityPlayer> usingPlayers = Collections.newSetFromMap(new WeakHashMap());
    private int usingPlayerCount;
    private byte lidAngle;
    private byte prevLidAngle;

    public TileEntityPersonalChest() {
        this.contentSlot = new InvSlot(this, "content", InvSlot.Access.NONE, 54);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        if (nbt.func_74764_b("ownerGameProfile")) {
            this.owner = NBTUtil.func_152459_a((NBTTagCompound)nbt.func_74775_l("ownerGameProfile"));
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        if (this.owner != null) {
            NBTTagCompound ownerNbt = new NBTTagCompound();
            NBTUtil.func_180708_a((NBTTagCompound)ownerNbt, (GameProfile)this.owner);
            nbt.func_74782_a("ownerGameProfile", (NBTBase)ownerNbt);
        }
        return nbt;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        this.prevLidAngle = this.lidAngle;
        if (this.usingPlayerCount > 0 && this.lidAngle <= 0) {
            World world = this.func_145831_w();
            world.func_184133_a(null, this.field_174879_c, SoundEvents.field_187657_V, SoundCategory.BLOCKS, 0.5f, world.field_73012_v.nextFloat() * 0.1f + 0.9f);
        }
        if (this.usingPlayerCount == 0 && this.lidAngle > 0 || this.usingPlayerCount > 0 && this.lidAngle < 10) {
            this.lidAngle = this.usingPlayerCount > 0 ? (byte)(this.lidAngle + 1) : (byte)(this.lidAngle - 1);
            byte closeThreshold = 5;
            if (this.lidAngle < closeThreshold && this.prevLidAngle >= closeThreshold) {
                World world = this.func_145831_w();
                world.func_184133_a(null, this.field_174879_c, SoundEvents.field_187651_T, SoundCategory.BLOCKS, 0.5f, world.field_73012_v.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
        return aabbs;
    }

    @Override
    public void func_174889_b(EntityPlayer player) {
        if (!this.func_145831_w().field_72995_K) {
            this.usingPlayers.add(player);
            this.updateUsingPlayerCount();
        }
    }

    @Override
    public void func_174886_c(EntityPlayer player) {
        if (!this.func_145831_w().field_72995_K) {
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
        List<String> ret = super.getNetworkedFields();
        ret.add("owner");
        ret.add("usingPlayerCount");
        return ret;
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer player) {
        if (!this.permitsAccess(player.func_146103_bH())) {
            IC2.platform.messagePlayer(player, "This safe is owned by " + this.owner.getName(), new Object[0]);
            return false;
        }
        if (!this.contentSlot.isEmpty()) {
            IC2.platform.messagePlayer(player, "Can't wrench non-empty safe", new Object[0]);
            return false;
        }
        return true;
    }

    @Override
    public boolean permitsAccess(GameProfile profile) {
        return TileEntityPersonalChest.checkAccess(this, profile);
    }

    @Override
    public IInventory getPrivilegedInventory(GameProfile accessor) {
        if (!this.permitsAccess(accessor)) {
            return this;
        }
        return new DelegatingInventory(this){

            @Override
            public int func_70302_i_() {
                return TileEntityPersonalChest.this.contentSlot.size();
            }

            @Override
            public ItemStack func_70301_a(int index) {
                return TileEntityPersonalChest.this.contentSlot.get(index);
            }

            @Override
            public ItemStack func_70298_a(int index, int amount) {
                ItemStack stack = this.func_70301_a(index);
                if (StackUtil.isEmpty(stack)) {
                    return StackUtil.emptyStack;
                }
                if (amount >= StackUtil.getSize(stack)) {
                    this.func_70299_a(index, StackUtil.emptyStack);
                    return stack;
                }
                if (amount != 0) {
                    if (amount < 0) {
                        int space = Math.min(TileEntityPersonalChest.this.contentSlot.getStackSizeLimit(), stack.func_77976_d()) - StackUtil.getSize(stack);
                        amount = Math.max(amount, -space);
                    }
                    stack = StackUtil.decSize(stack, amount);
                    this.func_70299_a(index, stack);
                }
                ItemStack ret = StackUtil.copyWithSize(stack, amount);
                return ret;
            }

            @Override
            public ItemStack func_70304_b(int index) {
                ItemStack ret = this.func_70301_a(index);
                if (!StackUtil.isEmpty(ret)) {
                    this.func_70299_a(index, StackUtil.emptyStack);
                }
                return ret;
            }

            @Override
            public void func_70299_a(int index, ItemStack stack) {
                TileEntityPersonalChest.this.contentSlot.put(index, stack);
                this.func_70296_d();
            }

            @Override
            public int func_70297_j_() {
                return TileEntityPersonalChest.this.contentSlot.getStackSizeLimit();
            }

            @Override
            public boolean func_94041_b(int index, ItemStack stack) {
                return TileEntityPersonalChest.this.contentSlot.accepts(stack);
            }
        };
    }

    public static <T extends TileEntity> boolean checkAccess(T te, GameProfile profile) {
        if (profile == null) {
            return ((IPersonalBlock)te).getOwner() == null;
        }
        GameProfile teOwner = ((IPersonalBlock)te).getOwner();
        if (!te.func_145831_w().field_72995_K) {
            if (teOwner == null) {
                ((IPersonalBlock)te).setOwner(profile);
                IC2.network.get(true).updateTileEntityField(te, "owner");
                return true;
            }
            if (te.func_145831_w().func_73046_m().func_184103_al().func_152596_g(profile)) {
                return true;
            }
        } else if (teOwner == null) {
            return true;
        }
        return teOwner.getId() != null ? teOwner.getId().equals(profile.getId()) : teOwner.getName().equals(profile.getName());
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return false;
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!this.func_145831_w().field_72995_K && !this.permitsAccess(player.func_146103_bH())) {
            IC2.platform.messagePlayer(player, "This safe is owned by " + this.getOwner().getName(), new Object[0]);
            return false;
        }
        return super.onActivated(player, hand, side, hitX, hitY, hitZ);
    }

    public ContainerBase<TileEntityPersonalChest> getGuiContainer(EntityPlayer player) {
        this.func_174889_b(player);
        return new DynamicContainer<TileEntityPersonalChest>(this, player, GuiParser.parse(this.teBlock)){

            public void func_75134_a(EntityPlayer player) {
                ((TileEntityPersonalChest)this.base).onGuiClosed(player);
                super.func_75134_a(player);
            }
        };
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
        this.func_174886_c(player);
    }

    public float getLidAngle(float partialTicks) {
        return Util.lerp(this.prevLidAngle, this.lidAngle, partialTicks) / 10.0f;
    }
}

