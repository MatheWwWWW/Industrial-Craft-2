/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagLong
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeChunkManager
 *  net.minecraftforge.common.ForgeChunkManager$LoadingCallback
 *  net.minecraftforge.common.ForgeChunkManager$Ticket
 *  net.minecraftforge.common.ForgeChunkManager$Type
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.world.WorldEvent$Unload
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ic2.core;

import ic2.core.IC2;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ChunkLoaderLogic
implements ForgeChunkManager.LoadingCallback {
    private static ChunkLoaderLogic instance;
    private final Map<World, List<ForgeChunkManager.Ticket>> tickets = new IdentityHashMap<World, List<ForgeChunkManager.Ticket>>();

    ChunkLoaderLogic() {
        if (instance != null) {
            throw new IllegalStateException();
        }
        instance = this;
        MinecraftForge.EVENT_BUS.register((Object)this);
        ForgeChunkManager.setForcedChunkLoadingCallback((Object)IC2.getInstance(), (ForgeChunkManager.LoadingCallback)this);
    }

    private List<ForgeChunkManager.Ticket> getTicketsForWorld(World world) {
        if (world.field_72995_K) {
            return null;
        }
        if (!this.tickets.containsKey(world)) {
            this.tickets.put(world, new ArrayList());
        }
        return this.tickets.get(world);
    }

    @SubscribeEvent
    public void unloadWorld(WorldEvent.Unload event) {
        if (event.getWorld().field_72995_K) {
            return;
        }
        if (this.tickets.containsKey(event.getWorld())) {
            this.tickets.remove(event.getWorld());
        }
    }

    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        List<ForgeChunkManager.Ticket> worldTickets = this.getTicketsForWorld(world);
        for (ForgeChunkManager.Ticket ticket : tickets) {
            worldTickets.add(ticket);
            NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
            for (int i = 0; i < list.func_74745_c(); ++i) {
                NBTTagLong value = (NBTTagLong)list.func_179238_g(i);
                ForgeChunkManager.forceChunk((ForgeChunkManager.Ticket)ticket, (ChunkPos)ChunkLoaderLogic.deserialize(value.func_150291_c()));
            }
            ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(this.getPosFromTicket(ticket));
            if (ticket.getChunkList().contains((Object)mainChunk)) continue;
            ForgeChunkManager.forceChunk((ForgeChunkManager.Ticket)ticket, (ChunkPos)mainChunk);
        }
    }

    public ForgeChunkManager.Ticket getTicket(World world, BlockPos pos, boolean create) {
        if (world.field_72995_K) {
            return null;
        }
        List<ForgeChunkManager.Ticket> ticketList = this.getTicketsForWorld(world);
        if (ticketList == null) {
            throw new IllegalStateException();
        }
        for (ForgeChunkManager.Ticket ticket : ticketList) {
            if (!pos.equals((Object)this.getPosFromTicket(ticket))) continue;
            return ticket;
        }
        if (create) {
            return this.createTicket(world, pos);
        }
        return null;
    }

    public ForgeChunkManager.Ticket createTicket(World world, BlockPos pos) {
        assert (this.getTicket(world, pos, false) == null);
        ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket((Object)IC2.getInstance(), (World)world, (ForgeChunkManager.Type)ForgeChunkManager.Type.NORMAL);
        ticket.getModData().func_74768_a("x", pos.func_177958_n());
        ticket.getModData().func_74768_a("y", pos.func_177956_o());
        ticket.getModData().func_74768_a("z", pos.func_177952_p());
        this.getTicketsForWorld(world).add(ticket);
        this.addChunkToTicket(ticket, ChunkLoaderLogic.getChunkCoords(pos));
        return ticket;
    }

    public void addChunkToTicket(ForgeChunkManager.Ticket ticket, ChunkPos chunk) {
        if (ticket.getChunkList().contains((Object)chunk)) {
            return;
        }
        ForgeChunkManager.forceChunk((ForgeChunkManager.Ticket)ticket, (ChunkPos)chunk);
        ForgeChunkManager.reorderChunk((ForgeChunkManager.Ticket)ticket, (ChunkPos)ChunkLoaderLogic.getChunkCoords(this.getPosFromTicket(ticket)));
        NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
        if (!ticket.getModData().func_150297_b("loadedChunks", 9)) {
            ticket.getModData().func_74782_a("loadedChunks", (NBTBase)list);
        }
        ticket.getModData().func_74782_a("loadedChunks", (NBTBase)list);
        list.func_74742_a((NBTBase)new NBTTagLong((long)chunk.field_77276_a & 0xFFFFFFFFL | ((long)chunk.field_77275_b & 0xFFFFFFFFL) << 32));
    }

    public void removeChunkFromTicket(ForgeChunkManager.Ticket ticket, ChunkPos chunk) {
        if (ChunkLoaderLogic.getChunkCoords(this.getPosFromTicket(ticket)).equals((Object)chunk)) {
            return;
        }
        ForgeChunkManager.unforceChunk((ForgeChunkManager.Ticket)ticket, (ChunkPos)chunk);
        NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
        long serializedChunk = ChunkLoaderLogic.serialize(chunk);
        for (int i = 0; i < list.func_74745_c(); ++i) {
            NBTTagLong pos = (NBTTagLong)list.func_179238_g(i);
            if (pos.func_150291_c() != serializedChunk) continue;
            list.func_74744_a(i--);
        }
    }

    public void removeTicket(World world, BlockPos pos) {
        this.removeTicket(this.getTicket(world, pos, false));
    }

    public void removeTicket(ForgeChunkManager.Ticket ticket) {
        ForgeChunkManager.releaseTicket((ForgeChunkManager.Ticket)ticket);
        this.getTicketsForWorld(ticket.world).remove(ticket);
    }

    public int getMaxChunksPerTicket() {
        return ForgeChunkManager.getMaxChunkDepthFor((String)"ic2");
    }

    private BlockPos getPosFromTicket(ForgeChunkManager.Ticket ticket) {
        return new BlockPos(ticket.getModData().func_74762_e("x"), ticket.getModData().func_74762_e("y"), ticket.getModData().func_74762_e("z"));
    }

    public static ChunkLoaderLogic getInstance() {
        return instance;
    }

    public static long serialize(ChunkPos chunk) {
        return (long)chunk.field_77276_a & 0xFFFFFFFFL | ((long)chunk.field_77275_b & 0xFFFFFFFFL) << 32;
    }

    public static ChunkPos deserialize(long value) {
        return new ChunkPos((int)(value & 0xFFFFFFFFFFFFFFFFL), (int)(value >> 32));
    }

    public static ChunkPos getChunkCoords(BlockPos pos) {
        return new ChunkPos(pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
    }
}

