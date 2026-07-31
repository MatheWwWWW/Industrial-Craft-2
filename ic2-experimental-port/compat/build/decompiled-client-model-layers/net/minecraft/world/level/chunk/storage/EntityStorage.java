/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.IOWorker;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import org.slf4j.Logger;

public class EntityStorage
implements EntityPersistentStorage<Entity> {
    private static final Logger f_156535_ = LogUtils.getLogger();
    private static final String f_156536_ = "Entities";
    private static final String f_156537_ = "Position";
    private final ServerLevel f_156538_;
    private final IOWorker f_156539_;
    private final LongSet f_156540_ = new LongOpenHashSet();
    private final ProcessorMailbox<Runnable> f_182485_;
    protected final DataFixer f_156534_;

    public EntityStorage(ServerLevel p_196924_, Path p_196925_, DataFixer p_196926_, boolean p_196927_, Executor p_196928_) {
        this.f_156538_ = p_196924_;
        this.f_156534_ = p_196926_;
        this.f_182485_ = ProcessorMailbox.m_18751_(p_196928_, "entity-deserializer");
        this.f_156539_ = new IOWorker(p_196925_, p_196927_, "entities");
    }

    @Override
    public CompletableFuture<ChunkEntities<Entity>> m_141930_(ChunkPos p_156551_) {
        if (this.f_156540_.contains(p_156551_.m_45588_())) {
            return CompletableFuture.completedFuture(EntityStorage.m_156568_(p_156551_));
        }
        return this.f_156539_.m_156587_(p_156551_).thenApplyAsync(p_223458_ -> {
            if (p_223458_.isEmpty()) {
                this.f_156540_.add(p_156551_.m_45588_());
                return EntityStorage.m_156568_(p_156551_);
            }
            try {
                ChunkPos $$2 = EntityStorage.m_156570_((CompoundTag)p_223458_.get());
                if (!Objects.equals(p_156551_, $$2)) {
                    f_156535_.error("Chunk file at {} is in the wrong location. (Expected {}, got {})", new Object[]{p_156551_, p_156551_, $$2});
                }
            }
            catch (Exception $$3) {
                f_156535_.warn("Failed to parse chunk {} position info", (Object)p_156551_, (Object)$$3);
            }
            CompoundTag $$4 = this.m_156572_((CompoundTag)p_223458_.get());
            ListTag $$5 = $$4.m_128437_(f_156536_, 10);
            List $$6 = (List)EntityType.m_147045_($$5, this.f_156538_).collect(ImmutableList.toImmutableList());
            return new ChunkEntities(p_156551_, $$6);
        }, this.f_182485_::m_6937_);
    }

    private static ChunkPos m_156570_(CompoundTag p_156571_) {
        int[] $$1 = p_156571_.m_128465_(f_156537_);
        return new ChunkPos($$1[0], $$1[1]);
    }

    private static void m_156562_(CompoundTag p_156563_, ChunkPos p_156564_) {
        p_156563_.m_128365_(f_156537_, new IntArrayTag(new int[]{p_156564_.f_45578_, p_156564_.f_45579_}));
    }

    private static ChunkEntities<Entity> m_156568_(ChunkPos p_156569_) {
        return new ChunkEntities<Entity>(p_156569_, (List<Entity>)ImmutableList.of());
    }

    @Override
    public void m_141971_(ChunkEntities<Entity> p_156559_) {
        ChunkPos $$1 = p_156559_.m_156791_();
        if (p_156559_.m_156793_()) {
            if (this.f_156540_.add($$1.m_45588_())) {
                this.f_156539_.m_63538_($$1, null);
            }
            return;
        }
        ListTag $$2 = new ListTag();
        p_156559_.m_156792_().forEach(p_156567_ -> {
            CompoundTag $$2 = new CompoundTag();
            if (p_156567_.m_20223_($$2)) {
                $$2.add($$2);
            }
        });
        CompoundTag $$3 = new CompoundTag();
        $$3.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        $$3.m_128365_(f_156536_, $$2);
        EntityStorage.m_156562_($$3, $$1);
        this.f_156539_.m_63538_($$1, $$3).exceptionally(p_156554_ -> {
            f_156535_.error("Failed to store chunk {}", (Object)$$1, p_156554_);
            return null;
        });
        this.f_156540_.remove($$1.m_45588_());
    }

    @Override
    public void m_182219_(boolean p_182487_) {
        this.f_156539_.m_182498_(p_182487_).join();
        this.f_182485_.m_182329_();
    }

    private CompoundTag m_156572_(CompoundTag p_156573_) {
        int $$1 = EntityStorage.m_156560_(p_156573_);
        return NbtUtils.m_129213_(this.f_156534_, DataFixTypes.ENTITY_CHUNK, p_156573_, $$1);
    }

    public static int m_156560_(CompoundTag p_156561_) {
        return p_156561_.m_128425_("DataVersion", 99) ? p_156561_.m_128451_("DataVersion") : -1;
    }

    @Override
    public void close() throws IOException {
        this.f_156539_.close();
    }
}

