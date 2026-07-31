/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item;

import ic2.api.reactor.IBaseReactorComponent;
import ic2.api.reactor.IReactor;
import ic2.core.item.ItemMulti;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ItemNuclearResource
extends ItemMulti<NuclearResourceType>
implements IBaseReactorComponent {
    public ItemNuclearResource() {
        super(ItemName.nuclear, NuclearResourceType.class);
    }

    @Override
    public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
        return false;
    }
}

