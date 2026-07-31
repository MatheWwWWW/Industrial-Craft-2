/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketCloseWindow
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.network.FMLNetworkEvent$ClientCustomPacketEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.network;

import com.mojang.authlib.GameProfile;
import ic2.api.network.IGrowingBuffer;
import ic2.api.network.INetworkItemEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.PositionSpec;
import ic2.core.block.ITeBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Components;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.IHandHeldSubInventory;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.IRpcProvider;
import ic2.core.network.NetworkManager;
import ic2.core.network.SubPacketType;
import ic2.core.network.TeUpdate;
import ic2.core.util.LogCategory;
import ic2.core.util.ParticleUtil;
import ic2.core.util.StackUtil;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.InflaterOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class NetworkManagerClient
extends NetworkManager {
    private GrowingBuffer largePacketBuffer;

    @Override
    protected boolean isClient() {
        return true;
    }

    @Override
    public void initiateClientItemEvent(ItemStack stack, int event) {
        try {
            GrowingBuffer buffer = new GrowingBuffer(256);
            SubPacketType.ItemEvent.writeTo(buffer);
            DataEncoder.encode(buffer, stack, false);
            buffer.writeInt(event);
            buffer.flip();
            this.sendPacket(buffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initiateKeyUpdate(int keyState) {
        GrowingBuffer buffer = new GrowingBuffer(5);
        SubPacketType.KeyUpdate.writeTo(buffer);
        buffer.writeInt(keyState);
        buffer.flip();
        this.sendPacket(buffer);
    }

    @Override
    public void initiateClientTileEntityEvent(TileEntity te, int event) {
        try {
            GrowingBuffer buffer = new GrowingBuffer(32);
            SubPacketType.TileEntityEvent.writeTo(buffer);
            DataEncoder.encode(buffer, te, false);
            buffer.writeInt(event);
            buffer.flip();
            this.sendPacket(buffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initiateRpc(int id, Class<? extends IRpcProvider<?>> provider, Object[] args) {
        try {
            GrowingBuffer buffer = new GrowingBuffer(256);
            SubPacketType.Rpc.writeTo(buffer);
            buffer.writeInt(id);
            buffer.writeString(provider.getName());
            DataEncoder.encode(buffer, args);
            buffer.flip();
            this.sendPacket(buffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void requestGUI(IHasGui inventory) {
        try {
            GrowingBuffer buffer = new GrowingBuffer(32);
            SubPacketType.RequestGUI.writeTo(buffer);
            if (inventory instanceof TileEntity) {
                TileEntity te = (TileEntity)inventory;
                buffer.writeBoolean(false);
                DataEncoder.encode(buffer, te, false);
            } else {
                EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
                if (!StackUtil.isEmpty(player.field_71071_by.func_70448_g()) && player.field_71071_by.func_70448_g().func_77973_b() instanceof IHandHeldInventory || !StackUtil.isEmpty(player.func_184592_cb()) && player.func_184592_cb().func_77973_b() instanceof IHandHeldInventory) {
                    buffer.writeBoolean(true);
                } else {
                    IC2.platform.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + inventory + ")", new Object[0]);
                }
            }
            buffer.flip();
            this.sendPacket(buffer);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        assert (!this.getClass().getName().equals(NetworkManager.class.getName()));
        try {
            this.onPacketData(GrowingBuffer.wrap(event.getPacket().payload()), (EntityPlayer)Minecraft.func_71410_x().field_71439_g);
        }
        catch (Throwable t) {
            IC2.log.warn(LogCategory.Network, t, "Network read failed");
            throw new RuntimeException(t);
        }
        event.getPacket().payload().release();
    }

    private void onPacketData(GrowingBuffer is, final EntityPlayer player) throws IOException {
        if (!is.hasAvailable()) {
            return;
        }
        SubPacketType packetType = SubPacketType.read(is, false);
        if (packetType == null) {
            return;
        }
        block0 : switch (packetType) {
            case LargePacket: {
                int state = is.readUnsignedByte();
                if ((state & 2) != 0) {
                    GrowingBuffer input;
                    if ((state & 1) != 0) {
                        input = is;
                    } else {
                        input = this.largePacketBuffer;
                        if (input == null) {
                            throw new IOException("unexpected large packet continuation");
                        }
                        is.writeTo(input);
                        input.flip();
                        this.largePacketBuffer = null;
                    }
                    GrowingBuffer decompBuffer = new GrowingBuffer(input.available() * 2);
                    InflaterOutputStream inflate = new InflaterOutputStream(decompBuffer);
                    input.writeTo(inflate);
                    inflate.close();
                    decompBuffer.flip();
                    switch (state >> 2) {
                        case 0: {
                            TeUpdate.receive(decompBuffer);
                            break;
                        }
                        case 1: {
                            NetworkManagerClient.processChatPacket(decompBuffer);
                            break;
                        }
                        case 2: {
                            NetworkManagerClient.processConsolePacket(decompBuffer);
                        }
                    }
                    break;
                }
                if ((state & 1) != 0) {
                    assert (this.largePacketBuffer == null);
                    this.largePacketBuffer = new GrowingBuffer(32752);
                }
                if (this.largePacketBuffer == null) {
                    throw new IOException("unexpected large packet continuation");
                }
                is.writeTo(this.largePacketBuffer);
                break;
            }
            case TileEntityEvent: {
                final Object teDeferred = DataEncoder.decodeDeferred(is, TileEntity.class);
                final int event = is.readInt();
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        TileEntity te = (TileEntity)DataEncoder.getValue(teDeferred);
                        if (te instanceof INetworkTileEntityEventListener) {
                            ((INetworkTileEntityEventListener)te).onNetworkEvent(event);
                        }
                    }
                });
                break;
            }
            case ItemEvent: {
                final GameProfile profile = DataEncoder.decode((IGrowingBuffer)is, GameProfile.class);
                final ItemStack stack = DataEncoder.decode((IGrowingBuffer)is, ItemStack.class);
                final int event = is.readInt();
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        WorldClient world = Minecraft.func_71410_x().field_71441_e;
                        for (Object obj : world.field_73010_i) {
                            EntityPlayer player = (EntityPlayer)obj;
                            if ((profile.getId() == null || !profile.getId().equals(player.func_146103_bH().getId())) && (profile.getId() != null || !profile.getName().equals(player.func_146103_bH().getName()))) continue;
                            if (!(stack.func_77973_b() instanceof INetworkItemEventListener)) break;
                            ((INetworkItemEventListener)stack.func_77973_b()).onNetworkEvent(stack, player, event);
                            break;
                        }
                    }
                });
                break;
            }
            case GuiDisplay: {
                final boolean isAdmin = is.readBoolean();
                switch (is.readByte()) {
                    case 0: {
                        final Object teDeferred = DataEncoder.decodeDeferred(is, TileEntity.class);
                        final int windowId = is.readInt();
                        IC2.platform.requestTick(false, new Runnable(){

                            @Override
                            public void run() {
                                EntityPlayer player = IC2.platform.getPlayerInstance();
                                TileEntity te = (TileEntity)DataEncoder.getValue(teDeferred);
                                if (te instanceof IHasGui) {
                                    IC2.platform.launchGuiClient(player, (IHasGui)te, isAdmin);
                                    player.field_71070_bA.field_75152_c = windowId;
                                } else if (player instanceof EntityPlayerSP) {
                                    ((EntityPlayerSP)player).field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(windowId));
                                }
                            }
                        });
                        break block0;
                    }
                    case 1: {
                        final int currentItemPosition = is.readInt();
                        final boolean subGUI = is.readBoolean();
                        final short ID = subGUI ? is.readShort() : (short)0;
                        final int windowId = is.readInt();
                        IC2.platform.requestTick(false, new Runnable(){

                            @Override
                            public void run() {
                                ItemStack currentItem;
                                EntityPlayer player = IC2.platform.getPlayerInstance();
                                if (currentItemPosition < 0) {
                                    int actualItemPosition = ~currentItemPosition;
                                    if (actualItemPosition > player.field_71071_by.field_184439_c.size() - 1) {
                                        return;
                                    }
                                    currentItem = (ItemStack)player.field_71071_by.field_184439_c.get(actualItemPosition);
                                } else {
                                    if (currentItemPosition != player.field_71071_by.field_70461_c) {
                                        return;
                                    }
                                    currentItem = player.field_71071_by.func_70448_g();
                                }
                                if (currentItem != null && currentItem.func_77973_b() instanceof IHandHeldInventory) {
                                    if (subGUI && currentItem.func_77973_b() instanceof IHandHeldSubInventory) {
                                        IC2.platform.launchGuiClient(player, ((IHandHeldSubInventory)currentItem.func_77973_b()).getSubInventory(player, currentItem, ID), isAdmin);
                                    } else {
                                        IC2.platform.launchGuiClient(player, ((IHandHeldInventory)currentItem.func_77973_b()).getInventory(player, currentItem), isAdmin);
                                    }
                                } else if (player instanceof EntityPlayerSP) {
                                    ((EntityPlayerSP)player).field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(windowId));
                                }
                                player.field_71070_bA.field_75152_c = windowId;
                            }
                        });
                        break block0;
                    }
                }
                break;
            }
            case ExplosionEffect: {
                final Object worldDeferred = DataEncoder.decodeDeferred(is, World.class);
                final Vec3d pos = DataEncoder.decode((IGrowingBuffer)is, Vec3d.class);
                final ExplosionIC2.Type type = DataEncoder.decodeEnum(is, ExplosionIC2.Type.class);
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        World world = (World)DataEncoder.getValue(worldDeferred);
                        if (world != null) {
                            switch (type) {
                                case Normal: {
                                    world.func_184133_a(player, new BlockPos(pos), SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0f, (1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
                                    world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0, 0.0, 0.0, new int[0]);
                                    break;
                                }
                                case Electrical: {
                                    IC2.audioManager.playOnce(new AudioPosition(world, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c), PositionSpec.Center, "Machines/MachineOverload.ogg", true, IC2.audioManager.getDefaultVolume());
                                    world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0, 0.0, 0.0, new int[0]);
                                    break;
                                }
                                case Heat: {
                                    world.func_184133_a(player, new BlockPos(pos), SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 4.0f, (1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
                                    world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0, 0.0, 0.0, new int[0]);
                                    break;
                                }
                                case Nuclear: {
                                    IC2.audioManager.playOnce(new AudioPosition(world, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c), PositionSpec.Center, "Tools/NukeExplosion.ogg", true, IC2.audioManager.getDefaultVolume());
                                    world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0, 0.0, 0.0, new int[0]);
                                }
                            }
                        }
                    }
                });
                break;
            }
            case Rpc: {
                throw new RuntimeException("Received unexpected RPC packet");
            }
            case TileEntityBlockComponent: {
                final int dimensionId = is.readInt();
                final BlockPos pos = DataEncoder.decode((IGrowingBuffer)is, BlockPos.class);
                String componentName = is.readString();
                final Class componentCls = Components.getClass(componentName);
                if (componentCls == null) {
                    throw new IOException("invalid component: " + componentName);
                }
                int dataLen = is.readVarInt();
                if (dataLen > 65536) {
                    throw new IOException("data length limit exceeded");
                }
                final byte[] data = new byte[dataLen];
                is.readFully(data);
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        WorldClient world = Minecraft.func_71410_x().field_71441_e;
                        if (world.field_73011_w.getDimension() != dimensionId) {
                            return;
                        }
                        TileEntity teRaw = world.func_175625_s(pos);
                        if (!(teRaw instanceof TileEntityBlock)) {
                            return;
                        }
                        Object component = ((TileEntityBlock)teRaw).getComponent(componentCls);
                        if (component == null) {
                            return;
                        }
                        DataInputStream dataIs = new DataInputStream(new ByteArrayInputStream(data));
                        try {
                            ((TileEntityComponent)component).onNetworkUpdate(dataIs);
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                break;
            }
            case TileEntityBlockLandEffect: {
                final Object worldDeferred = DataEncoder.decodeDeferred(is, World.class);
                final BlockPos pos = is.readBoolean() ? (BlockPos)DataEncoder.decode((IGrowingBuffer)is, DataEncoder.EncodedType.BlockPos) : null;
                final double x = is.readDouble();
                final double y = is.readDouble();
                final double z = is.readDouble();
                final int count = is.readInt();
                final ITeBlock teBlock = TeBlockRegistry.get(is.readString());
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        World world = (World)DataEncoder.getValue(worldDeferred);
                        if (world == null) {
                            return;
                        }
                        ParticleUtil.spawnBlockLandParticles(world, pos, x, y, z, count, teBlock);
                    }
                });
                break;
            }
            case TileEntityBlockRunEffect: {
                final Object worldDeferred = DataEncoder.decodeDeferred(is, World.class);
                final BlockPos pos = is.readBoolean() ? (BlockPos)DataEncoder.decode((IGrowingBuffer)is, DataEncoder.EncodedType.BlockPos) : null;
                final double x = is.readDouble();
                final double y = is.readDouble();
                final double z = is.readDouble();
                final double xSpeed = is.readDouble();
                final double zSpeed = is.readDouble();
                final ITeBlock teBlock = TeBlockRegistry.get(is.readString());
                IC2.platform.requestTick(false, new Runnable(){

                    @Override
                    public void run() {
                        World world = (World)DataEncoder.getValue(worldDeferred);
                        if (world == null) {
                            return;
                        }
                        ParticleUtil.spawnBlockRunParticles(world, pos, x, y, z, xSpeed, zSpeed, teBlock);
                    }
                });
                break;
            }
            default: {
                this.onCommonPacketData(packetType, false, is, player);
            }
        }
    }

    private static void processChatPacket(GrowingBuffer buffer) {
        final String messages = buffer.readString();
        IC2.platform.requestTick(false, new Runnable(){

            @Override
            public void run() {
                for (String line : messages.split("[\\r\\n]+")) {
                    IC2.platform.messagePlayer(null, line, new Object[0]);
                }
            }
        });
    }

    private static void processConsolePacket(GrowingBuffer buffer) {
        String messages = buffer.readString();
        PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
        for (String line : messages.split("[\\r\\n]+")) {
            console.println(line);
        }
        console.flush();
    }
}

