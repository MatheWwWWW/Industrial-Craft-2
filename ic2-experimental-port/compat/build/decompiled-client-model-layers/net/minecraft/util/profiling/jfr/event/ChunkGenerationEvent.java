/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

@Name(value="minecraft.ChunkGeneration")
@Label(value="Chunk Generation")
@Category(value={"Minecraft", "World Generation"})
@StackTrace(value=false)
@Enabled(value=false)
@DontObfuscate
public class ChunkGenerationEvent
extends Event {
    public static final String f_195533_ = "minecraft.ChunkGeneration";
    public static final EventType f_195534_ = EventType.getEventType(ChunkGenerationEvent.class);
    @Name(value="worldPosX")
    @Label(value="First Block X World Position")
    public final int f_195539_;
    @Name(value="worldPosZ")
    @Label(value="First Block Z World Position")
    public final int f_195540_;
    @Name(value="chunkPosX")
    @Label(value="Chunk X Position")
    public final int f_195535_;
    @Name(value="chunkPosZ")
    @Label(value="Chunk Z Position")
    public final int f_195536_;
    @Name(value="status")
    @Label(value="Status")
    public final String f_195538_;
    @Name(value="level")
    @Label(value="Level")
    public final String f_195537_;

    public ChunkGenerationEvent(ChunkPos p_195543_, ResourceKey<Level> p_195544_, String p_195545_) {
        this.f_195538_ = p_195545_;
        this.f_195537_ = p_195544_.toString();
        this.f_195535_ = p_195543_.f_45578_;
        this.f_195536_ = p_195543_.f_45579_;
        this.f_195539_ = p_195543_.m_45604_();
        this.f_195540_ = p_195543_.m_45605_();
    }

    public static class Fields {
        public static final String f_195546_ = "worldPosX";
        public static final String f_195547_ = "worldPosZ";
        public static final String f_195548_ = "chunkPosX";
        public static final String f_195549_ = "chunkPosZ";
        public static final String f_195550_ = "status";
        public static final String f_195551_ = "level";

        private Fields() {
        }
    }
}

