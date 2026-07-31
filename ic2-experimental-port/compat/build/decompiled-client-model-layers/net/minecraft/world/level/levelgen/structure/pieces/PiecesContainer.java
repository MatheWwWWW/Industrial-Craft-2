/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.slf4j.Logger;

public record PiecesContainer(List<StructurePiece> f_192741_) {
    private static final Logger f_192742_ = LogUtils.getLogger();
    private static final ResourceLocation f_192743_ = new ResourceLocation("jigsaw");
    private static final Map<ResourceLocation, ResourceLocation> f_192744_ = ImmutableMap.builder().put((Object)new ResourceLocation("nvi"), (Object)f_192743_).put((Object)new ResourceLocation("pcp"), (Object)f_192743_).put((Object)new ResourceLocation("bastionremnant"), (Object)f_192743_).put((Object)new ResourceLocation("runtime"), (Object)f_192743_).build();

    public PiecesContainer(List<StructurePiece> f_192741_) {
        this.f_192741_ = List.copyOf(f_192741_);
    }

    public boolean m_192748_() {
        return this.f_192741_.isEmpty();
    }

    public boolean m_192751_(BlockPos p_192752_) {
        for (StructurePiece $$1 : this.f_192741_) {
            if (!$$1.m_73547_().m_71051_(p_192752_)) continue;
            return true;
        }
        return false;
    }

    public Tag m_192749_(StructurePieceSerializationContext p_192750_) {
        ListTag $$1 = new ListTag();
        for (StructurePiece $$2 : this.f_192741_) {
            $$1.add($$2.m_192644_(p_192750_));
        }
        return $$1;
    }

    public static PiecesContainer m_192753_(ListTag p_192754_, StructurePieceSerializationContext p_192755_) {
        ArrayList $$2 = Lists.newArrayList();
        for (int $$3 = 0; $$3 < p_192754_.size(); ++$$3) {
            CompoundTag $$4 = p_192754_.m_128728_($$3);
            String $$5 = $$4.m_128461_("id").toLowerCase(Locale.ROOT);
            ResourceLocation $$6 = new ResourceLocation($$5);
            ResourceLocation $$7 = f_192744_.getOrDefault($$6, $$6);
            StructurePieceType $$8 = Registry.f_122843_.m_7745_($$7);
            if ($$8 == null) {
                f_192742_.error("Unknown structure piece id: {}", (Object)$$7);
                continue;
            }
            try {
                StructurePiece $$9 = $$8.m_207333_(p_192755_, $$4);
                $$2.add($$9);
                continue;
            }
            catch (Exception $$10) {
                f_192742_.error("Exception loading structure piece with id {}", (Object)$$7, (Object)$$10);
            }
        }
        return new PiecesContainer($$2);
    }

    public BoundingBox m_192756_() {
        return StructurePiece.m_192651_(this.f_192741_.stream());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{PiecesContainer.class, "pieces", "f_192741_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PiecesContainer.class, "pieces", "f_192741_"}, this);
    }

    @Override
    public final boolean equals(Object p_192759_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PiecesContainer.class, "pieces", "f_192741_"}, this, p_192759_);
    }
}

