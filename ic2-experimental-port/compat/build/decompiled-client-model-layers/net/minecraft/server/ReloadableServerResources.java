/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.ServerFunctionLibrary;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.util.Unit;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import org.slf4j.Logger;

public class ReloadableServerResources {
    private static final Logger f_206845_ = LogUtils.getLogger();
    private static final CompletableFuture<Unit> f_206846_ = CompletableFuture.completedFuture(Unit.INSTANCE);
    private final CommandBuildContext f_214300_;
    private final Commands f_206847_;
    private final RecipeManager f_206848_ = new RecipeManager();
    private final TagManager f_206849_;
    private final PredicateManager f_206850_ = new PredicateManager();
    private final LootTables f_206851_ = new LootTables(this.f_206850_);
    private final ItemModifierManager f_206852_ = new ItemModifierManager(this.f_206850_, this.f_206851_);
    private final ServerAdvancementManager f_206853_ = new ServerAdvancementManager(this.f_206850_);
    private final ServerFunctionLibrary f_206854_;

    public ReloadableServerResources(RegistryAccess.Frozen p_206857_, Commands.CommandSelection p_206858_, int p_206859_) {
        this.f_206849_ = new TagManager(p_206857_);
        this.f_214300_ = new CommandBuildContext(p_206857_);
        this.f_206847_ = new Commands(p_206858_, this.f_214300_);
        this.f_214300_.m_227135_(CommandBuildContext.MissingTagAccessPolicy.CREATE_NEW);
        this.f_206854_ = new ServerFunctionLibrary(p_206859_, this.f_206847_.m_82094_());
    }

    public ServerFunctionLibrary m_206860_() {
        return this.f_206854_;
    }

    public PredicateManager m_206881_() {
        return this.f_206850_;
    }

    public LootTables m_206885_() {
        return this.f_206851_;
    }

    public ItemModifierManager m_206886_() {
        return this.f_206852_;
    }

    public RecipeManager m_206887_() {
        return this.f_206848_;
    }

    public Commands m_206888_() {
        return this.f_206847_;
    }

    public ServerAdvancementManager m_206889_() {
        return this.f_206853_;
    }

    public List<PreparableReloadListener> m_206890_() {
        return List.of(this.f_206849_, this.f_206850_, this.f_206848_, this.f_206851_, this.f_206852_, this.f_206854_, this.f_206853_);
    }

    public static CompletableFuture<ReloadableServerResources> m_206861_(ResourceManager p_206862_, RegistryAccess.Frozen p_206863_, Commands.CommandSelection p_206864_, int p_206865_, Executor p_206866_, Executor p_206867_) {
        ReloadableServerResources $$6 = new ReloadableServerResources(p_206863_, p_206864_, p_206865_);
        return ((CompletableFuture)SimpleReloadInstance.m_203834_(p_206862_, $$6.m_206890_(), p_206866_, p_206867_, f_206846_, f_206845_.isDebugEnabled()).m_7237_().whenComplete((p_214309_, p_214310_) -> p_214308_.f_214300_.m_227135_(CommandBuildContext.MissingTagAccessPolicy.FAIL))).thenApply(p_214306_ -> $$6);
    }

    public void m_206868_(RegistryAccess p_206869_) {
        this.f_206849_.m_203904_().forEach(p_214315_ -> ReloadableServerResources.m_206870_(p_206869_, p_214315_));
        Blocks.m_50758_();
    }

    private static <T> void m_206870_(RegistryAccess p_206871_, TagManager.LoadResult<T> p_206872_) {
        ResourceKey $$2 = p_206872_.f_203928_();
        Map $$3 = p_206872_.f_203929_().entrySet().stream().collect(Collectors.toUnmodifiableMap(p_214303_ -> TagKey.m_203882_($$2, (ResourceLocation)p_214303_.getKey()), p_214312_ -> List.copyOf((Collection)p_214312_.getValue())));
        p_206871_.m_175515_($$2).m_203652_($$3);
    }
}

