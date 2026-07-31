/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.tile.RetexturableBlock;
import ic2.core.IC2;
import ic2.core.block.state.BlockStateUtil;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.PriorityUsableItem;
import ic2.core.model.ModelUtil;
import ic2.core.network.IPlayerItemDataListener;
import ic2.core.proxy.ClientEnvProxy;
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.Vector3;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemObscurator
extends BaseElectricItem
implements PriorityUsableItem,
IPlayerItemDataListener {
    private final int scanOperationCost = 20000;
    private final int printOperationCost = 5000;
    private static final int[] noTint = new int[]{-1};
    private static final int[] zeroTint = new int[]{0};
    private static final int[] defaultColorMultiplier = new int[]{0xFFFFFF};
    private static final int[] colorMultiplierOpaqueWhite = new int[]{-1};

    public ItemObscurator(Item.Properties properties) {
        super(properties, 100000.0, 250.0, 2);
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(ElectricItem.manager.getToolTip(itemStack));
        return linkedList;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        Level level = useOnContext.m_43725_();
        BlockPos blockPos = useOnContext.m_8083_();
        Direction direction = useOnContext.m_43719_();
        Player player = useOnContext.m_43723_();
        if (!player.m_6144_() && !level.f_46443_ && ElectricItem.manager.canUse(itemStack, 5000.0)) {
            int[] nArray;
            Direction direction2;
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            BlockState blockState = ItemObscurator.getState(compoundTag);
            if (blockState == null || (direction2 = ItemObscurator.getSide(compoundTag)) == null || (nArray = ItemObscurator.getColorMultipliers(compoundTag)) == null) {
                ItemObscurator.clear(compoundTag);
                return InteractionResult.PASS;
            }
            BlockState blockState2 = level.m_8055_(blockPos);
            Block block = blockState2.m_60734_();
            String string = ItemObscurator.getVariant(compoundTag);
            boolean bl = block instanceof RetexturableBlock ? ((RetexturableBlock)block).retexture(blockState2, level, blockPos, direction, player, blockState, string, direction2, nArray) : IC2.envProxy.announceRetexture(level, blockPos, blockState2, direction, player, blockState, string, direction2, nArray);
            if (bl) {
                ElectricItem.manager.use(itemStack, 5000.0, (LivingEntity)player);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (player.m_6144_() && level.f_46443_ && ElectricItem.manager.canUse(itemStack, 20000.0)) {
            return this.scanBlock(itemStack, player, level, blockPos, direction) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    private boolean scanBlock(ItemStack itemStack, Player player, Level level, BlockPos blockPos, Direction direction) {
        assert (level.f_46443_);
        BlockState blockState = level.m_8055_(blockPos);
        if (blockState.m_60795_()) {
            return false;
        }
        ObscuredRenderInfo obscuredRenderInfo = ItemObscurator.getRenderInfo(blockState, direction);
        if (obscuredRenderInfo == null) {
            return false;
        }
        String string = ModelUtil.getVariant(blockState);
        int[] nArray = new int[obscuredRenderInfo.tints.length];
        for (int i = 0; i < obscuredRenderInfo.tints.length; ++i) {
            nArray[i] = SideProxyClient.mc.m_91298_().m_92577_(blockState, (BlockAndTintGetter)level, blockPos, obscuredRenderInfo.tints[i]);
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (ItemObscurator.getState(compoundTag) != blockState || !string.equals(ItemObscurator.getVariant(compoundTag)) || ItemObscurator.getSide(compoundTag) != direction || !Arrays.equals(ItemObscurator.getColorMultipliers(compoundTag), nArray)) {
            IC2.network.get(false).sendPlayerItemData(player, player.m_150109_().f_35977_, blockState.m_60734_(), string, direction, nArray);
            return true;
        }
        return false;
    }

    @Override
    public void onPlayerItemNetworkData(Player player, int n, Object ... objectArray) {
        if (!(objectArray[0] instanceof Block)) {
            return;
        }
        if (!(objectArray[1] instanceof String)) {
            return;
        }
        if (!(objectArray[2] instanceof Integer)) {
            return;
        }
        if (!(objectArray[3] instanceof int[])) {
            return;
        }
        ItemStack itemStack = (ItemStack)player.m_150109_().f_35974_.get(n);
        if (!ElectricItem.manager.use(itemStack, 20000.0, (LivingEntity)player)) {
            return;
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        ItemObscurator.setState(compoundTag, (Block)objectArray[0], (String)objectArray[1]);
        ItemObscurator.setSide(compoundTag, (Integer)objectArray[2]);
        ItemObscurator.setColorMultipliers(compoundTag, (int[])objectArray[3]);
    }

    public static BlockState getState(CompoundTag compoundTag) {
        String string = compoundTag.m_128461_("refBlock");
        if (string.isEmpty()) {
            return null;
        }
        Block block = Util.getBlock(string);
        if (block == null) {
            return null;
        }
        String string2 = ItemObscurator.getVariant(compoundTag);
        return BlockStateUtil.getState(block, string2);
    }

    public static String getVariant(CompoundTag compoundTag) {
        return compoundTag.m_128461_("refVariant");
    }

    private static void setState(CompoundTag compoundTag, Block block, String string) {
        compoundTag.m_128359_("refBlock", Util.getName(block).toString());
        compoundTag.m_128359_("refVariant", string);
    }

    public static Direction getSide(CompoundTag compoundTag) {
        byte by = compoundTag.m_128445_("refSide");
        if (by < 0 || by >= Util.ALL_DIRS.length) {
            return null;
        }
        return Util.ALL_DIRS[by];
    }

    private static void setSide(CompoundTag compoundTag, int n) {
        compoundTag.m_128344_("refSide", (byte)n);
    }

    public static int[] getColorMultipliers(CompoundTag compoundTag) {
        int[] nArray = compoundTag.m_128465_("refColorMuls");
        return nArray.length == 0 ? null : ItemObscurator.internColorMultipliers(nArray);
    }

    public static void setColorMultipliers(CompoundTag compoundTag, int[] nArray) {
        if (nArray.length == 0) {
            throw new IllegalArgumentException();
        }
        compoundTag.m_128385_("refColorMuls", nArray);
    }

    private static void clear(CompoundTag compoundTag) {
        compoundTag.m_128473_("refBlock");
        compoundTag.m_128473_("refVariant");
        compoundTag.m_128473_("refSide");
        compoundTag.m_128473_("refColorMul");
    }

    public static ObscuredRenderInfo getRenderInfo(BlockState blockState, Direction direction) {
        if (ItemBlockRenderTypes.m_109282_((BlockState)blockState) == RenderType.m_110466_()) {
            return null;
        }
        BakedModel bakedModel = ModelUtil.getBlockModel(blockState);
        if (bakedModel == null) {
            return null;
        }
        RandomSource randomSource = RandomSource.m_216335_((long)42L);
        List list = bakedModel.m_213637_(blockState, direction, randomSource);
        if (list.isEmpty()) {
            return null;
        }
        float[] fArray = new float[list.size() * 4];
        int n = 0;
        int[] nArray = new int[list.size()];
        TextureAtlasSprite[] textureAtlasSpriteArray = new TextureAtlasSprite[list.size()];
        for (BakedQuad bakedQuad : list) {
            Vector3 vector3;
            Vector3 vector32;
            Vector3 vector33;
            Vector3 vector34;
            ClientEnvProxy.QuadData quadData = SideProxyClient.envProxy.getQuadData(bakedQuad);
            float[] fArray2 = quadData.positions();
            int n2 = direction.m_122429_();
            int n3 = direction.m_122430_();
            int n4 = direction.m_122431_();
            int n5 = (n2 + 1) / 2;
            int n6 = (n3 + 1) / 2;
            int n7 = (n4 + 1) / 2;
            int n8 = -1;
            for (int i = 0; i < 4; ++i) {
                int n9 = i * 3;
                if (!Util.isSimilar(fArray2[n9 + 0], n5) || !Util.isSimilar(fArray2[n9 + 1], n6) || !Util.isSimilar(fArray2[n9 + 2], n7)) continue;
                n8 = i;
                break;
            }
            if (n8 == -1 || !Util.isSimilar((vector34 = new Vector3(fArray2[3] - fArray2[0], fArray2[4] - fArray2[1], fArray2[5] - fArray2[2])).lengthSquared(), 1.0) || !Util.isSimilar((vector33 = new Vector3(fArray2[9] - fArray2[0], fArray2[10] - fArray2[1], fArray2[11] - fArray2[2])).lengthSquared(), 1.0) || !Util.isSimilar((vector32 = new Vector3(fArray2[9] - fArray2[6], fArray2[10] - fArray2[7], fArray2[11] - fArray2[8])).copy().add(vector34).lengthSquared(), 0.0) || !Util.isSimilar((vector3 = vector34.copy().cross(vector33)).copy().sub(n2, n3, n4).lengthSquared(), 0.0)) continue;
            nArray[n / 4] = quadData.tint();
            textureAtlasSpriteArray[n / 4] = quadData.sprite();
            float[] fArray3 = quadData.uvs();
            fArray[n++] = fArray3[n8 * 2];
            fArray[n++] = fArray3[n8 * 2 + 1];
            fArray[n++] = fArray3[(n8 + 2) % 4 * 2];
            fArray[n++] = fArray3[(n8 + 2) % 4 * 2 + 1];
        }
        if (n == 0) {
            return null;
        }
        if (n < fArray.length) {
            fArray = Arrays.copyOf(fArray, n);
            nArray = Arrays.copyOf(nArray, n / 4);
        }
        nArray = ItemObscurator.internTints(nArray);
        return new ObscuredRenderInfo(fArray, nArray, textureAtlasSpriteArray);
    }

    public static int[] internTints(int[] nArray) {
        if (nArray.length == 1) {
            if (nArray[0] == noTint[0]) {
                return noTint;
            }
            if (nArray[0] == zeroTint[0]) {
                return zeroTint;
            }
        }
        return nArray;
    }

    public static int[] internColorMultipliers(int[] nArray) {
        if (nArray.length == 1) {
            if (nArray[0] == defaultColorMultiplier[0]) {
                return defaultColorMultiplier;
            }
            if (nArray[0] == colorMultiplierOpaqueWhite[0]) {
                return colorMultiplierOpaqueWhite;
            }
        }
        return nArray;
    }

    public static class ObscuredRenderInfo {
        public final float[] uvs;
        public final int[] tints;
        public final TextureAtlasSprite[] sprites;

        private ObscuredRenderInfo(float[] fArray, int[] nArray, TextureAtlasSprite[] textureAtlasSpriteArray) {
            this.uvs = fArray;
            this.tints = nArray;
            this.sprites = textureAtlasSpriteArray;
        }
    }
}

