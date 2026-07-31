/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package ic2.core.utils.collection;

import ic2.core.utils.collection.IterableWrapper;
import java.util.Iterator;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class NBTListWrapper {
    public static <T extends Tag> Iterable<T> wrap(final ListTag nbt, Class<T> clz) {
        return IterableWrapper.wrap(new Iterator<T>(){
            Iterator<Tag> iter;
            {
                this.iter = nbt.iterator();
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public T next() {
                return this.iter.next();
            }

            @Override
            public void remove() {
                this.iter.remove();
            }
        });
    }
}

