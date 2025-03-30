package fr.iglee42.holycubepaleoak;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mod(HolycubePaleOak.MODID)
public class HolycubePaleOak {
    public static final String MODID = "holycubepaleoak";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_BONEMEAL = FeatureUtils.createKey("pale_oak_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH = FeatureUtils.createKey("pale_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH_BONEMEAL = FeatureUtils.createKey("pale_moss_patch_bonemeal");
    public static final TagKey<Block> PALE_OAK_LOGS = TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("pale_oak_logs"));
    public static final TreeDecoratorType<PaleMossDecorator> PALE_MOSS_DECORATOR = TreeDecoratorType.register("pale_moss", PaleMossDecorator.CODEC);


    public static final BlockSetType PALE_OAK_SET = BlockSetType.register(new BlockSetType("pale_oak"));
    public static final WoodType PALE_OAK_TYPE = WoodType.register(new WoodType("pale_oak", PALE_OAK_SET));
    public static final TreeGrower PALE_OAK = new TreeGrower("pale_oak", Optional.of(PALE_OAK_BONEMEAL), Optional.empty(), Optional.empty());
    private List<ResourceKey<CreativeModeTab>> wasCreativeRegister = new ArrayList<>();
    public static Boat.Type PALE_OAK_BOAT;



    public HolycubePaleOak(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        HolyPOSounds.SOUNDS.register(modEventBus);
//        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist == Dist.CLIENT) modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerEvent);
        NeoForge.EVENT_BUS.addListener(this::onReload);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (wasCreativeRegister.contains(event.getTabKey())) return;
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS ){
            event.accept(HolyPOItems.PALE_OAK_LOG);
            event.accept(HolyPOItems.PALE_OAK_WOOD);
            event.accept(HolyPOItems.STRIPPED_PALE_OAK_LOG);
            event.accept(HolyPOItems.STRIPPED_PALE_OAK_WOOD);
            event.accept(HolyPOItems.PALE_OAK_PLANKS);
            event.accept(HolyPOItems.PALE_OAK_STAIRS);
            event.accept(HolyPOItems.PALE_OAK_SLAB);
            event.accept(HolyPOItems.PALE_OAK_FENCE);
            event.accept(HolyPOItems.PALE_OAK_FENCE_GATE);
            event.accept(HolyPOItems.PALE_OAK_DOOR);
            event.accept(HolyPOItems.PALE_OAK_TRAPDOOR);
            event.accept(HolyPOItems.PALE_OAK_PRESSURE_PLATE);
            event.accept(HolyPOItems.PALE_OAK_BUTTON);
            event.accept(HolyPOItems.PALE_OAK_LEAVES);

        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(HolyPOItems.PALE_OAK_SIGN);
            event.accept(HolyPOItems.PALE_OAK_HANGING_SIGN);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(HolyPOItems.PALE_OAK_BOAT);
            event.accept(HolyPOItems.PALE_OAK_CHEST_BOAT);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(HolyPOItems.PALE_OAK_SAPLING);
            event.accept(HolyPOItems.CLOSED_EYEBLOSSOM);
            event.accept(HolyPOItems.OPEN_EYEBLOSSOM);
            event.accept(HolyPOItems.PALE_MOSS_BLOCK);
            event.accept(HolyPOItems.PALE_MOSS_CARPET);
            event.accept(HolyPOItems.PALE_HANGING_MOSS);
        }
        wasCreativeRegister.add(event.getTabKey());
    }

    private void onReload(AddReloadListenerEvent event){
        event.addListener((ResourceManagerReloadListener) resourceManager -> wasCreativeRegister.clear());
    }

    private void clientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_OAK_LEAVES, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.CLOSED_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.OPEN_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_OAK_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_OAK_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_MOSS_CARPET, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_HANGING_MOSS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.POTTED_PALE_OAK_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.POTTED_CLOSED_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.POTTED_OPEN_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyPOBlocks.PALE_OAK_DOOR, RenderType.cutout());
    }

    private void registerEvent(RegisterEvent event){
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(HolyPOBlocks.PALE_OAK_LOG,HolyPOBlocks.STRIPPED_PALE_OAK_LOG);
        AxeItem.STRIPPABLES.put(HolyPOBlocks.PALE_OAK_WOOD,HolyPOBlocks.STRIPPED_PALE_OAK_WOOD);
    }

}
