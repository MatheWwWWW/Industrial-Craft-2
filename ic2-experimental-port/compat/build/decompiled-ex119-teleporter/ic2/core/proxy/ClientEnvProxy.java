/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.client.renderer.item.ClampedItemPropertyFunction
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package ic2.core.proxy;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.fluid.Ic2FluidStack;
import java.io.File;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface ClientEnvProxy {
    public <H extends AbstractContainerMenu> void registerScreen(MenuType<H> var1, ScreenFactory<H> var2);

    public File getMinecraftDir();

    public void registerColorProvider(BlockColor var1, Block ... var2);

    public void registerColorProvider(ItemColor var1, ItemLike ... var2);

    public void registerKeyBinding(KeyMapping var1);

    public <E extends BlockEntity> void registerBer(BlockEntityType<E> var1, BlockEntityRendererProvider<? super E> var2);

    public void registerModelPredicateProvider(ResourceLocation var1, ClampedItemPropertyFunction var2);

    public void registerModelPredicateProvider(Item var1, ResourceLocation var2, ClampedItemPropertyFunction var3);

    public void registerBlockLayer(RenderType var1, Block ... var2);

    public <E extends Entity> void registerEntityRenderer(EntityType<? extends E> var1, EntityRendererProvider<E> var2);

    public <E extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<? extends E> var1, BlockEntityRendererProvider<E> var2);

    public TextureAtlasSprite getFluidStillSprite(Ic2FluidStack var1);

    public int getFluidColor(Ic2FluidStack var1);

    public String getFluidName(Ic2FluidStack var1);

    default public QuadData getQuadData(final BakedQuad bakedQuad) {
        VertexFormat vertexFormat = DefaultVertexFormat.f_85811_;
        int[] nArray = bakedQuad.m_111303_();
        int n = vertexFormat.m_86017_();
        int n2 = 0;
        int n3 = 4;
        final float[] fArray = new float[12];
        final float[] fArray2 = new float[8];
        for (int i = 0; i < 4; ++i) {
            int n4;
            int n5 = i * n;
            for (n4 = 0; n4 < 3; ++n4) {
                fArray[i * 4 + n4] = Float.intBitsToFloat(nArray[n5 + n2 + n4]);
            }
            for (n4 = 0; n4 < 2; ++n4) {
                fArray2[i * 4 + n4] = Float.intBitsToFloat(nArray[n5 + n3 + n4]);
            }
        }
        return new QuadData(){

            @Override
            public float[] positions() {
                return fArray;
            }

            @Override
            public float[] uvs() {
                return fArray2;
            }

            @Override
            public int tint() {
                return bakedQuad.m_111305_();
            }

            @Override
            public TextureAtlasSprite sprite() {
                return bakedQuad.m_173410_();
            }
        };
    }

    public static interface QuadData {
        public float[] positions();

        public float[] uvs();

        public int tint();

        public TextureAtlasSprite sprite();
    }

    public static interface ScreenFactory<H extends AbstractContainerMenu> {
        public AbstractContainerScreen<H> create(H var1, Inventory var2, Component var3);
    }
}

