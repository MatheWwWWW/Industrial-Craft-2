/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.personal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class ModelPersonalChest
extends ModelBase {
    private final ModelRenderer door;

    public ModelPersonalChest() {
        this.field_78090_t = 64;
        this.field_78089_u = 64;
        this.door = new ModelRenderer((ModelBase)this, 30, 0);
        this.door.func_178769_a(2.0f, 1.0f, 2.0f, 12, 14, 1, true);
        this.door.func_78787_b(64, 64);
    }

    public void render() {
        this.door.func_78785_a(0.0625f);
    }
}

