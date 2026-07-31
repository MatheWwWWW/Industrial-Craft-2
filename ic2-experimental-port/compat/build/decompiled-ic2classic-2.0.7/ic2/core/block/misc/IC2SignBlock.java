/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Sheets
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.StandingSignBlock
 *  net.minecraft.world.level.block.WallSignBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.block.misc;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.misc.IAtlasProvider;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.plugins.IRegistryProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class IC2SignBlock
extends StandingSignBlock
implements IBlockModel,
IAtlasProvider,
EntityBlock,
IRegistryProvider {
    private IBlockDropProvider drops;
    private ITextureProvider provider;
    private ResourceLocation texture;
    private BlockEntityType<? extends BlockEntity> tile;
    private ResourceLocation id;

    public IC2SignBlock(String name, WoodType type, BlockBehaviour.Properties properties, BlockEntityType<? extends BlockEntity> tile, ITextureProvider provider, IBlockDropProvider drops, ResourceLocation texture) {
        super(properties.m_60910_(), type);
        this.id = GameData.checkPrefix((String)name, (boolean)false);
        this.tile = tile;
        this.drops = drops;
        this.provider = provider;
        this.texture = texture;
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.tile.m_155264_(pos, state);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.id;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ResourceLocation getAtlas() {
        return Sheets.f_110739_;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ResourceLocation getAtlasTexture() {
        return this.texture;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return this.provider.getTexture(state, side);
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();
        result.add(this.drops.createDrop(state, (ItemStack)builder.m_78982_(LootContextParams.f_81463_), builder.m_78962_().f_46441_, null, false));
        return result;
    }

    public static class IC2WallSignBlock
    extends WallSignBlock
    implements IBlockModel,
    IAtlasProvider,
    EntityBlock,
    IRegistryProvider {
        private IBlockDropProvider drops;
        private ITextureProvider provider;
        private ResourceLocation texture;
        private BlockEntityType<? extends BlockEntity> tile;
        private ResourceLocation id;

        public IC2WallSignBlock(String name, WoodType type, BlockBehaviour.Properties properties, BlockEntityType<? extends BlockEntity> tile, ITextureProvider provider, IBlockDropProvider drops, ResourceLocation texture) {
            super(properties.m_60910_(), type);
            this.id = GameData.checkPrefix((String)name, (boolean)false);
            this.tile = tile;
            this.drops = drops;
            this.provider = provider;
            this.texture = texture;
        }

        public BlockEntity m_142194_(BlockPos pos, BlockState state) {
            return this.tile.m_155264_(pos, state);
        }

        @Override
        public ResourceLocation getRegistryName() {
            return this.id;
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public ResourceLocation getAtlasTexture() {
            return this.texture;
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public ResourceLocation getAtlas() {
            return Sheets.f_110739_;
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
            return this.provider.getTexture(state, side);
        }

        public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
            LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
            if (loottable != LootTable.f_79105_) {
                return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
            }
            ArrayList<ItemStack> result = new ArrayList<ItemStack>();
            result.add(this.drops.createDrop(state, (ItemStack)builder.m_78982_(LootContextParams.f_81463_), builder.m_78962_().f_46441_, null, false));
            return result;
        }
    }
}

