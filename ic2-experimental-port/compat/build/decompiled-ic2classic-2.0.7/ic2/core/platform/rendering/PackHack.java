/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.AbstractPackResources
 *  net.minecraft.server.packs.PackResources
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.metadata.MetadataSectionSerializer
 *  net.minecraft.server.packs.metadata.pack.PackMetadataSection
 *  net.minecraft.server.packs.repository.Pack
 *  net.minecraft.server.packs.repository.Pack$PackConstructor
 *  net.minecraft.server.packs.repository.Pack$Position
 *  net.minecraft.server.packs.repository.PackSource
 *  net.minecraft.server.packs.repository.RepositorySource
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.forgespi.language.IModInfo
 *  net.minecraftforge.forgespi.locating.IModFile
 *  org.apache.commons.io.IOUtils
 */
package ic2.core.platform.rendering;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.apache.commons.io.IOUtils;

@OnlyIn(value=Dist.CLIENT)
public class PackHack {
    public static void doResourcePackHack() {
        Minecraft instance = Minecraft.m_91087_();
        if (instance != null) {
            instance.m_91099_().addPackFinder((RepositorySource)new PackFinder());
        }
    }

    public static class PackFinder
    implements RepositorySource {
        public void m_7686_(Consumer<Pack> acceptor, Pack.PackConstructor factory) {
            acceptor.accept(Pack.m_10430_((String)"mod:ic2loader", (boolean)true, CustomResourcePack::new, (Pack.PackConstructor)factory, (Pack.Position)Pack.Position.BOTTOM, (PackSource)PackSource.f_10528_));
            acceptor.accept(Pack.m_10430_((String)"mod:ic2devpack", (boolean)false, () -> new DeveloperPack(ModList.get().getModFileById("ic2").getFile()), (Pack.PackConstructor)factory, (Pack.Position)Pack.Position.TOP, (PackSource)PackSource.f_10528_));
        }
    }

    public static class DeveloperPack
    extends AbstractPackResources {
        private final IModFile modFile;

        public DeveloperPack(IModFile modFile) {
            super(new File("dummy"));
            this.modFile = modFile;
        }

        public void close() {
        }

        protected boolean m_6105_(String name) {
            return Files.exists(this.modFile.findResource(new String[]{name.replaceFirst("ic2", "ic2dev")}), new LinkOption[0]);
        }

        protected InputStream m_5541_(String name) throws IOException {
            name = name.replaceFirst("ic2", "ic2dev");
            Path path = this.modFile.findResource(new String[]{name});
            if (!Files.exists(path, new LinkOption[0])) {
                throw new FileNotFoundException(name);
            }
            return Files.newInputStream(path, StandardOpenOption.READ);
        }

        public Collection<ResourceLocation> m_214146_(PackType type, String namespaceIn, String pathIn, Predicate<ResourceLocation> filterIn) {
            if (type == PackType.SERVER_DATA) {
                return Collections.emptyList();
            }
            Path root = this.modFile.findResource(new String[]{type.m_10305_(), namespaceIn.replaceFirst("ic2", "ic2dev")}).toAbsolutePath();
            Path inputPath = root.getFileSystem().getPath(pathIn.replaceFirst("ic2", "ic2dev"), new String[0]);
            try {
                return Files.walk(root, new FileVisitOption[0]).map(path -> root.relativize(path.toAbsolutePath())).filter(path -> !path.toString().endsWith(".mcmeta")).filter(path -> path.startsWith(inputPath)).filter(path -> filterIn.test(new ResourceLocation(namespaceIn, path.getFileName().toString()))).map(path -> new ResourceLocation(namespaceIn, Joiner.on((char)'/').join((Iterable)path))).collect(Collectors.toList());
            }
            catch (IOException e) {
                return Collections.emptyList();
            }
        }

        public Set<String> m_5698_(PackType type) {
            if (type == PackType.SERVER_DATA) {
                return Collections.emptySet();
            }
            ObjectSet set = CollectionUtils.createSet();
            set.add((String)"ic2");
            return set;
        }

        public String m_8017_() {
            return "IC2C Programmer Art";
        }

        public <T> T m_5550_(MetadataSectionSerializer<T> deserializer) throws IOException {
            if (deserializer.m_7991_().equalsIgnoreCase("pack")) {
                return (T)new PackMetadataSection((Component)Component.m_237113_((String)"Restores some of the older IC2Classic textures"), 9);
            }
            return null;
        }
    }

    public static class CustomResourcePack
    implements PackResources {
        public void close() {
        }

        public boolean isHidden() {
            return true;
        }

        public InputStream m_5542_(String fileName) throws IOException {
            throw new FileNotFoundException(fileName);
        }

        public InputStream m_8031_(PackType type, ResourceLocation location) throws IOException {
            if (type == PackType.SERVER_DATA) {
                return null;
            }
            String path = location.m_135815_();
            if (path.endsWith(".json")) {
                if (path.startsWith("models/item") && IC2Models.hasCustomItemModel(this.getPath(location, "models/item"))) {
                    return IOUtils.toInputStream((String)"{}", (String)"UTF-8");
                }
                if (path.startsWith("blockstates") && IC2Models.hasCustomModelStates(this.getPath(location, "blockstates"))) {
                    return IOUtils.toInputStream((String)"{\"variants\":{\"\":{\"model\":\"block/air\"}}}", (String)"UTF-8");
                }
            }
            return null;
        }

        public Collection<ResourceLocation> m_214146_(PackType type, String namespaceIn, String pathIn, Predicate<ResourceLocation> filterIn) {
            return Collections.emptyList();
        }

        public boolean m_7211_(PackType type, ResourceLocation location) {
            String path;
            if (type == PackType.CLIENT_RESOURCES && (path = location.m_135815_()).endsWith(".json")) {
                if (path.startsWith("models/item")) {
                    return IC2Models.hasCustomItemModel(this.getPath(location, "models/item"));
                }
                if (path.startsWith("blockstates")) {
                    return IC2Models.hasCustomModelStates(this.getPath(location, "blockstates"));
                }
            }
            return false;
        }

        private ResourceLocation getPath(ResourceLocation id, String search) {
            String path = id.m_135815_();
            String s = path.substring(search.length() + 1);
            return new ResourceLocation(id.m_135827_(), s.substring(0, s.indexOf(".json")));
        }

        public Set<String> m_5698_(PackType type) {
            if (type == PackType.SERVER_DATA) {
                return ObjectSets.emptySet();
            }
            ObjectLinkedOpenHashSet set = new ObjectLinkedOpenHashSet((Collection)Lists.transform((List)ModList.get().getMods(), IModInfo::getModId));
            set.add("ic2");
            set.add("minecraft");
            return set;
        }

        public <T> T m_5550_(MetadataSectionSerializer<T> deserializer) throws IOException {
            return (T)(deserializer.m_7991_().equalsIgnoreCase("pack") ? new PackMetadataSection((Component)Component.m_237113_((String)"IC2ClientPack"), 9) : null);
        }

        public String m_8017_() {
            return "ic2loader";
        }
    }
}

