/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 */
package ic2.core.utils.config.api;

import ic2.core.utils.config.api.IConfigProxy;
import ic2.core.utils.config.utils.Helpers;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.nio.file.Path;
import java.util.List;

public class SimpleConfigProxy
implements IConfigProxy {
    Path path;

    public SimpleConfigProxy(Path path) {
        this.path = path;
    }

    @Override
    public boolean isDynamicProxy() {
        return false;
    }

    @Override
    public List<Path> getBasePaths() {
        return ObjectLists.singleton((Object)this.path);
    }

    public List<SimpleTarget> getPotentialConfigs() {
        return ObjectLists.singleton((Object)new SimpleTarget(this.path, Helpers.firstLetterUppercase(this.path.getFileName().toString())));
    }

    public static class SimpleTarget
    implements IConfigProxy.IPotentialTarget {
        Path folder;
        String name;

        public SimpleTarget(Path folder, String name) {
            this.folder = folder;
            this.name = name;
        }

        @Override
        public Path getFolder() {
            return this.folder;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}

