/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BedBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity
 *  net.minecraft.world.level.block.entity.SignBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BedPart
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 */
package ic2.api.blocks;

import ic2.api.blocks.DyeableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class PainterHelper {
    public static final PainterHelper INSTANCE = new PainterHelper();
    Map<Block, IPaintable> paintable = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());
    public static final BiFunction<BlockState, BlockState, BlockState> DEFAULT_MAPPER = (S, T) -> T;
    public static final BiFunction<BlockState, BlockState, BlockState> DEFAULT_COPY_MAPPER = PainterHelper::copyProperties;
    public static DyeableMap BEDS;
    public static DyeableMap WOOL;
    public static DyeableMap CARPETS;
    public static DyeableMap CONCRETE;
    public static DyeableMap CONCRETE_DUST;
    public static DyeableMap GLASS;
    public static DyeableMap GLASS_PANE;
    public static DyeableMap TERRACOTTA;
    public static DyeableMap GLAZED_TERRACOTTA;
    public static DyeableMap SHULKER;

    public static void init() {
        BEDS = PainterHelper.getBedColors();
        INSTANCE.registerPaintHelper((Collection<Block>)BEDS.getBlocks(), (IPaintable)new BedPainter());
        INSTANCE.registerPaintHelper(PainterHelper.getSigns(), (IPaintable)new SignPainter());
        CARPETS = PainterHelper.getCarpetColors();
        INSTANCE.registerPaintHelper(CARPETS, DEFAULT_MAPPER);
        CONCRETE = PainterHelper.getConcreteColor();
        INSTANCE.registerPaintHelper(CONCRETE, DEFAULT_MAPPER);
        CONCRETE_DUST = PainterHelper.getConcreteDustColor();
        INSTANCE.registerPaintHelper(CONCRETE_DUST, DEFAULT_MAPPER);
        GLASS = PainterHelper.getGlassColors();
        INSTANCE.registerPaintHelper(GLASS, DEFAULT_MAPPER);
        GLASS_PANE = PainterHelper.getGlassPaneColors();
        INSTANCE.registerPaintHelper(GLASS_PANE, DEFAULT_COPY_MAPPER);
        GLAZED_TERRACOTTA = PainterHelper.getGlazedTerracottaColors();
        INSTANCE.registerPaintHelper(GLAZED_TERRACOTTA, DEFAULT_MAPPER);
        SHULKER = PainterHelper.getShulkerColors();
        INSTANCE.registerPaintHelper((Collection<Block>)SHULKER.getBlocks(), (IPaintable)new ShulkerPainter());
        TERRACOTTA = PainterHelper.getTerracottaColors();
        INSTANCE.registerPaintHelper(TERRACOTTA, DEFAULT_MAPPER);
        WOOL = PainterHelper.getWoolColors();
        INSTANCE.registerPaintHelper(WOOL, DEFAULT_MAPPER);
    }

    public void registerPaintHelper(DyeableMap map, BiFunction<BlockState, BlockState, BlockState> mapper) {
        this.registerPaintHelper((Collection<Block>)map.getBlocks(), (IPaintable)new Colorable(map, mapper));
    }

    public <T extends Block> void registerPaintHelper(T entry) {
        this.paintable.put(entry, (IPaintable)entry);
    }

    public void registerPaintHelper(Collection<Block> blocks, IPaintable helper) {
        for (Block block : blocks) {
            this.paintable.put(block, helper);
        }
    }

    public void registerPaintHelper(Block block, IPaintable helper) {
        this.paintable.put(block, helper);
    }

    public IPaintable getPaintable(Block block) {
        IPaintable paintable;
        IPaintable paint = this.paintable.get(block);
        IPaintable iPaintable = paint == null ? (block instanceof IPaintable ? (paintable = (IPaintable)block) : null) : paint;
        return iPaintable;
    }

    public IPaintable getPaintable(BlockState state) {
        return this.getPaintable(state.m_60734_());
    }

    public DyeColor getColor(BlockState state) {
        IPaintable paint = this.getPaintable(state);
        return paint == null ? null : paint.getColor(state);
    }

    public static <T extends Property<V>, V extends Comparable<V>> BlockState copyProperties(BlockState source, BlockState target) {
        for (Property prop : source.m_61147_()) {
            if (!target.m_61138_(prop)) continue;
            target = (BlockState)target.m_61124_(prop, source.m_61143_(prop));
        }
        return target;
    }

    private static DyeableMap getWoolColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50041_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50042_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50096_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50097_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50098_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50099_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50100_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50101_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50102_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50103_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50104_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50105_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50106_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50107_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50108_, DyeColor.RED);
        colors.addBlock(Blocks.f_50109_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getTerracottaColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50287_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50288_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50289_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50290_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50291_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50292_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50293_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50294_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50295_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50296_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50297_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50298_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50299_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50300_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50301_, DyeColor.RED);
        colors.addBlock(Blocks.f_50302_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getGlazedTerracottaColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50526_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50527_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50528_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50529_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50530_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50531_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50532_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50533_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50534_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50535_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50536_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50537_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50538_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50539_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50540_, DyeColor.RED);
        colors.addBlock(Blocks.f_50541_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getConcreteColor() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50542_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50543_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50544_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50545_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50494_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50495_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50496_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50497_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50498_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50499_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50500_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50501_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50502_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50503_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50504_, DyeColor.RED);
        colors.addBlock(Blocks.f_50505_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getConcreteDustColor() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50506_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50507_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50508_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50509_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50510_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50511_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50512_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50513_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50514_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50515_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50516_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50517_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50518_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50519_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50573_, DyeColor.RED);
        colors.addBlock(Blocks.f_50574_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getShulkerColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50457_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50458_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50459_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50460_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50461_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50462_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50463_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50464_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50465_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50466_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50520_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50521_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50522_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50523_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50524_, DyeColor.RED);
        colors.addBlock(Blocks.f_50525_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getBedColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50066_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50067_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50068_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50017_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50018_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50019_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50020_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50021_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50022_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50023_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50024_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50025_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50026_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50027_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50028_, DyeColor.RED);
        colors.addBlock(Blocks.f_50029_, DyeColor.BLACK);
        return colors;
    }

    public static List<Block> getSigns() {
        ObjectArrayList signs = new ObjectArrayList();
        signs.add(Blocks.f_50151_);
        signs.add(Blocks.f_50161_);
        signs.add(Blocks.f_50150_);
        signs.add(Blocks.f_50160_);
        signs.add(Blocks.f_50153_);
        signs.add(Blocks.f_50163_);
        signs.add(Blocks.f_50152_);
        signs.add(Blocks.f_50162_);
        signs.add(Blocks.f_50095_);
        signs.add(Blocks.f_50158_);
        signs.add(Blocks.f_50149_);
        signs.add(Blocks.f_50159_);
        signs.add(Blocks.f_50673_);
        signs.add(Blocks.f_50675_);
        signs.add(Blocks.f_50674_);
        signs.add(Blocks.f_50676_);
        return signs;
    }

    private static DyeableMap getGlassColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50147_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50148_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50202_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50203_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50204_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50205_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50206_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50207_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50208_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50209_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50210_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50211_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50212_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50213_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50214_, DyeColor.RED);
        colors.addBlock(Blocks.f_50215_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getGlassPaneColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50041_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50304_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50305_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50306_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50307_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50361_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50362_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50363_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50364_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50365_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50366_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50367_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50368_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50369_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50370_, DyeColor.RED);
        colors.addBlock(Blocks.f_50371_, DyeColor.BLACK);
        return colors;
    }

    private static DyeableMap getCarpetColors() {
        DyeableMap colors = new DyeableMap();
        colors.addBlock(Blocks.f_50336_, DyeColor.WHITE);
        colors.addBlock(Blocks.f_50337_, DyeColor.ORANGE);
        colors.addBlock(Blocks.f_50338_, DyeColor.MAGENTA);
        colors.addBlock(Blocks.f_50339_, DyeColor.LIGHT_BLUE);
        colors.addBlock(Blocks.f_50340_, DyeColor.YELLOW);
        colors.addBlock(Blocks.f_50341_, DyeColor.LIME);
        colors.addBlock(Blocks.f_50342_, DyeColor.PINK);
        colors.addBlock(Blocks.f_50343_, DyeColor.GRAY);
        colors.addBlock(Blocks.f_50344_, DyeColor.LIGHT_GRAY);
        colors.addBlock(Blocks.f_50345_, DyeColor.CYAN);
        colors.addBlock(Blocks.f_50346_, DyeColor.PURPLE);
        colors.addBlock(Blocks.f_50347_, DyeColor.BLUE);
        colors.addBlock(Blocks.f_50348_, DyeColor.BROWN);
        colors.addBlock(Blocks.f_50349_, DyeColor.GREEN);
        colors.addBlock(Blocks.f_50350_, DyeColor.RED);
        colors.addBlock(Blocks.f_50351_, DyeColor.BLACK);
        return colors;
    }

    public static class BedPainter
    extends Colorable {
        public BedPainter() {
            super(PainterHelper.getBedColors(), null);
        }

        @Override
        public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
            DyeColor currentColor = this.map.getColor(state.m_60734_());
            if (currentColor == null || currentColor == color) {
                return false;
            }
            Block block = this.map.getBlock(color);
            if (block == null) {
                return false;
            }
            world.m_7731_(pos, PainterHelper.copyProperties(state, block.m_49966_()), 17);
            pos = pos.m_121945_(state.m_61143_((Property)BedBlock.f_49440_) == BedPart.HEAD ? ((Direction)state.m_61143_((Property)BedBlock.f_54117_)).m_122424_() : (Direction)state.m_61143_((Property)BedBlock.f_54117_));
            state = world.m_8055_(pos);
            world.m_7731_(pos, PainterHelper.copyProperties(state, block.m_49966_()), 17);
            return true;
        }
    }

    public static interface IPaintable {
        public boolean recolor(BlockState var1, Level var2, BlockPos var3, Vec3 var4, Direction var5, DyeColor var6);

        public DyeColor getColor(BlockState var1);
    }

    public static class SignPainter
    implements IPaintable {
        @Override
        public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
            BlockEntity tileEntity = world.m_7702_(pos);
            if (tileEntity instanceof SignBlockEntity) {
                SignBlockEntity signEntity = (SignBlockEntity)tileEntity;
                if (signEntity.m_59753_() != color) {
                    signEntity.m_59739_(color);
                }
                return true;
            }
            return false;
        }

        @Override
        public DyeColor getColor(BlockState state) {
            return null;
        }
    }

    public static class ShulkerPainter
    extends Colorable {
        public ShulkerPainter() {
            super(PainterHelper.getShulkerColors(), null);
        }

        @Override
        public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
            DyeColor currentColor = this.map.getColor(state.m_60734_());
            if (currentColor == null || currentColor == color) {
                return false;
            }
            Block block = this.map.getBlock(color);
            if (block == null) {
                return false;
            }
            BlockEntity sourceTile = world.m_7702_(pos);
            if (sourceTile instanceof ShulkerBoxBlockEntity) {
                ShulkerBoxBlockEntity sourceShukler = (ShulkerBoxBlockEntity)sourceTile;
                BlockState newState = PainterHelper.copyProperties(state, block.m_49966_());
                if (!world.m_46597_(pos, newState)) {
                    return false;
                }
                BlockEntity targetTile = world.m_7702_(pos);
                if (!(targetTile instanceof ShulkerBoxBlockEntity)) {
                    return false;
                }
                ShulkerBoxBlockEntity targetShulker = (ShulkerBoxBlockEntity)targetTile;
                targetShulker.m_142466_(sourceShukler.m_187482_());
                return true;
            }
            return false;
        }
    }

    public static class Colorable
    implements IPaintable {
        DyeableMap map;
        BiFunction<BlockState, BlockState, BlockState> mapper;

        public Colorable(DyeableMap map, BiFunction<BlockState, BlockState, BlockState> mapper) {
            this.map = map;
            this.mapper = mapper;
        }

        @Override
        public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
            DyeColor currentColor = this.map.getColor(state.m_60734_());
            if (currentColor == null || currentColor == color) {
                return false;
            }
            Block block = this.map.getBlock(color);
            return block != null && world.m_46597_(pos, this.mapper.apply(state, block.m_49966_()));
        }

        @Override
        public DyeColor getColor(BlockState state) {
            return this.map.getColor(state.m_60734_());
        }
    }
}

