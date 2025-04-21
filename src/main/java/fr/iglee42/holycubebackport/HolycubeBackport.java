package fr.iglee42.holycubebackport;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;

import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.*;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
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
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

import java.util.*;

@Mod(HolycubeBackport.MODID)
public class HolycubeBackport {
    public static final String MODID = "holycubebackport";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_OAK_BONEMEAL = FeatureUtils.createKey("pale_oak_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH = FeatureUtils.createKey("pale_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_MOSS_PATCH_BONEMEAL = FeatureUtils.createKey("pale_moss_patch_bonemeal");
    public static final TagKey<Block> PALE_OAK_LOGS = TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("pale_oak_logs"));
    public static final TagKey<Block> DRY_VEGETATION_MAY_PLACE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("dry_vegetation_may_place_on"));
    public static final TagKey<Block> PLAYS_AMBIENT_DESERT_BLOCK_SOUNDS = TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("plays_ambient_desert_block_sounds"));
    public static final TreeDecoratorType<PaleMossDecorator> PALE_MOSS_DECORATOR = TreeDecoratorType.register("pale_moss", PaleMossDecorator.CODEC);
    public static final TreeDecoratorType<PlaceOnGroundDecorator> PLACE_ON_GROUND = TreeDecoratorType.register("place_on_ground", PlaceOnGroundDecorator.CODEC);


    public static final BlockSetType PALE_OAK_SET = BlockSetType.register(new BlockSetType("pale_oak"));
    public static final WoodType PALE_OAK_TYPE = WoodType.register(new WoodType("pale_oak", PALE_OAK_SET));
    public static final TreeGrower PALE_OAK = new TreeGrower("pale_oak", Optional.of(PALE_OAK_BONEMEAL), Optional.empty(), Optional.empty());
    public static Boat.Type PALE_OAK_BOAT;



    public HolycubeBackport(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        HolyBackSounds.SOUNDS.register(modEventBus);
//        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist == Dist.CLIENT) modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerEvent);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_OAK_LEAVES, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.CLOSED_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.OPEN_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_OAK_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_OAK_TRAPDOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_MOSS_CARPET, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_HANGING_MOSS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.POTTED_PALE_OAK_SAPLING, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.POTTED_CLOSED_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.POTTED_OPEN_EYEBLOSSOM, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.PALE_OAK_DOOR, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.FIREFLY_BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.BUSH, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.GRASS_SLAB, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.GRASS_STAIRS, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.SHORT_DRY_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.TALL_DRY_GRASS, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.LEAF_LITTER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.CACTUS_FLOWER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HolyBackBlocks.RESIN_CLUMP, RenderType.cutout());

        Minecraft.getInstance().getBlockColors().register(
                (p_276237_, p_276238_, p_276239_, p_276240_) -> p_276238_ != null && p_276239_ != null
                        ? BiomeColors.getAverageGrassColor(p_276238_, p_276239_)
                        : GrassColor.getDefaultColor(),
                HolyBackBlocks.GRASS_SLAB, HolyBackBlocks.GRASS_STAIRS,HolyBackBlocks.BUSH
        );
        Minecraft.getInstance().getItemColors().register(
                (p_92687_, p_92688_) -> {
                    BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
                    return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, p_92688_);
                },
                HolyBackBlocks.GRASS_SLAB,HolyBackBlocks.GRASS_STAIRS,HolyBackBlocks.BUSH
        );

    }

    private void registerEvent(RegisterEvent event){
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(HolyBackBlocks.PALE_OAK_LOG, HolyBackBlocks.STRIPPED_PALE_OAK_LOG);
        AxeItem.STRIPPABLES.put(HolyBackBlocks.PALE_OAK_WOOD, HolyBackBlocks.STRIPPED_PALE_OAK_WOOD);
    }

}
