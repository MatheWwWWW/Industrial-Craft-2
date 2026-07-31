/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.packs.repository;

import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

public class ServerPacksSource
implements RepositorySource {
    public static final PackMetadataSection f_143904_ = new PackMetadataSection(Component.m_237115_("dataPack.vanilla.description"), PackType.SERVER_DATA.m_143756_(SharedConstants.m_183709_()));
    public static final String f_143905_ = "vanilla";
    private final VanillaPackResources f_10544_ = new VanillaPackResources(f_143904_, "minecraft");

    @Override
    public void m_7686_(Consumer<Pack> p_10548_, Pack.PackConstructor p_10549_) {
        Pack $$2 = Pack.m_10430_(f_143905_, false, () -> this.f_10544_, p_10549_, Pack.Position.BOTTOM, PackSource.f_10528_);
        if ($$2 != null) {
            p_10548_.accept($$2);
        }
    }
}

