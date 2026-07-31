/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public record StructurePieceSerializationContext(ResourceManager f_192762_, RegistryAccess f_192763_, StructureTemplateManager f_226956_) {
    public static StructurePieceSerializationContext m_192770_(ServerLevel p_192771_) {
        MinecraftServer $$1 = p_192771_.m_7654_();
        return new StructurePieceSerializationContext($$1.m_177941_(), $$1.m_206579_(), $$1.m_236738_());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{StructurePieceSerializationContext.class, "resourceManager;registryAccess;structureTemplateManager", "f_192762_", "f_192763_", "f_226956_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StructurePieceSerializationContext.class, "resourceManager;registryAccess;structureTemplateManager", "f_192762_", "f_192763_", "f_226956_"}, this);
    }

    @Override
    public final boolean equals(Object p_192775_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StructurePieceSerializationContext.class, "resourceManager;registryAccess;structureTemplateManager", "f_192762_", "f_192763_", "f_226956_"}, this, p_192775_);
    }
}

