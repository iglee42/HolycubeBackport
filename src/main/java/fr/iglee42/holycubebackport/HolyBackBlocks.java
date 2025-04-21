package fr.iglee42.holycubebackport;

import fr.iglee42.holycubebackport.blocks.*;
import fr.iglee42.holycubebackport.blocks.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import fr.iglee42.holycubebackport.blocks.MultifaceBlock;

import java.util.function.Function;

public class HolyBackBlocks {


    public static final Block PALE_OAK_WOOD = register(
            "pale_oak_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()
    );

    public static final Block PALE_OAK_PLANKS = register(
            "pale_oak_planks",
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_LOG = register(
            "pale_oak_log", RotatedPillarBlock::new, logProperties(PALE_OAK_PLANKS.defaultMapColor(), PALE_OAK_WOOD.defaultMapColor(), SoundType.WOOD)
    );

    public static final Block PALE_OAK_SAPLING = register(
            "pale_oak_sapling",
            p_379242_ -> new SaplingBlock(HolycubeBackport.PALE_OAK, p_379242_),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block STRIPPED_PALE_OAK_LOG = register(
            "stripped_pale_oak_log", RotatedPillarBlock::new, logProperties(PALE_OAK_PLANKS.defaultMapColor(), PALE_OAK_PLANKS.defaultMapColor(), SoundType.WOOD)
    );

    public static final Block STRIPPED_PALE_OAK_WOOD = register(
            "stripped_pale_oak_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_LEAVES = register(
            "pale_oak_leaves",
            LeavesBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::ocelotOrParrot)
                    .isSuffocating(HolyBackBlocks::never)
                    .isViewBlocking(HolyBackBlocks::never)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .isRedstoneConductor(HolyBackBlocks::never)
    );

    public static final Block PALE_OAK_SIGN = register(
            "pale_oak_sign",
            p_379244_ -> new StandingSignBlock(HolycubeBackport.PALE_OAK_TYPE, p_379244_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_WALL_SIGN = register(
            "pale_oak_wall_sign",
            p_379250_ -> new WallSignBlock(HolycubeBackport.PALE_OAK_TYPE, p_379250_),
            wallVariant(PALE_OAK_SIGN, true)
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_HANGING_SIGN = register(
            "pale_oak_hanging_sign",
            p_379247_ -> new CeilingHangingSignBlock(HolycubeBackport.PALE_OAK_TYPE, p_379247_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_PRESSURE_PLATE = register(
            "pale_oak_pressure_plate",
            p_379246_ -> new PressurePlateBlock(HolycubeBackport.PALE_OAK_SET, p_379246_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block PALE_OAK_TRAPDOOR = register(
            "pale_oak_trapdoor",
            p_379252_ -> new TrapDoorBlock(HolycubeBackport.PALE_OAK_SET, p_379252_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .ignitedByLava()
    );

    public static final Block POTTED_PALE_OAK_SAPLING = register(
            "potted_pale_oak_sapling", p_379245_ -> new FlowerPotBlock(()-> (FlowerPotBlock) Blocks.FLOWER_POT,()->PALE_OAK_SAPLING, p_379245_), flowerPotProperties()
    );

    public static final Block PALE_OAK_BUTTON = register(
            "pale_oak_button", p_379240_ -> new ButtonBlock(HolycubeBackport.PALE_OAK_SET, 30, p_379240_), buttonProperties()
    );

    public static final Block PALE_OAK_STAIRS = registerLegacyStair("pale_oak_stairs", PALE_OAK_PLANKS);

    public static final Block PALE_OAK_SLAB = register(
            "pale_oak_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_FENCE_GATE = register(
            "pale_oak_fence_gate",
            p_379243_ -> new FenceGateBlock(HolycubeBackport.PALE_OAK_TYPE, p_379243_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .ignitedByLava()
    );

    public static final Block PALE_OAK_FENCE = register(
            "pale_oak_fence",
            FenceBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
    );

    public static final Block PALE_OAK_DOOR = register(
            "pale_oak_door",
            p_379249_ -> new DoorBlock(HolycubeBackport.PALE_OAK_SET, p_379249_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block OPEN_EYEBLOSSOM = register(
            "open_eyeblossom",
            p_382765_ -> new EyeblossomBlock(EyeblossomBlock.Type.OPEN, p_382765_),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
    );

    public static final Block CLOSED_EYEBLOSSOM = register(
            "closed_eyeblossom",
            p_382766_ -> new EyeblossomBlock(EyeblossomBlock.Type.CLOSED, p_382766_),
            BlockBehaviour.Properties.of()
                    .mapColor(PALE_OAK_LEAVES.defaultMapColor())
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
    );

    public static final Block PALE_MOSS_BLOCK = register(
            "pale_moss_block",
            p_379241_ -> new BonemealableFeaturePlacerBlock(HolycubeBackport.PALE_MOSS_PATCH_BONEMEAL, p_379241_),
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.1F)
                    .sound(SoundType.MOSS)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block PALE_MOSS_CARPET = register(
            "pale_moss_carpet",
            MossyCarpetBlock::new,
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .mapColor(PALE_MOSS_BLOCK.defaultMapColor())
                    .strength(0.1F)
                    .sound(SoundType.MOSS_CARPET)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block PALE_HANGING_MOSS = register(
            "pale_hanging_moss",
            HangingMossBlock::new,
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .mapColor(PALE_MOSS_BLOCK.defaultMapColor())
                    .noCollission()
                    .sound(SoundType.MOSS_CARPET)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block POTTED_OPEN_EYEBLOSSOM = register(
            "potted_open_eyeblossom", p_382762_ -> new FlowerPotBlock(()-> (FlowerPotBlock) Blocks.FLOWER_POT,()->OPEN_EYEBLOSSOM, p_382762_), flowerPotProperties().randomTicks()
    );
    public static final Block POTTED_CLOSED_EYEBLOSSOM = register(
            "potted_closed_eyeblossom", p_382761_ -> new FlowerPotBlock(()-> (FlowerPotBlock) Blocks.FLOWER_POT,()->CLOSED_EYEBLOSSOM, p_382761_), flowerPotProperties().randomTicks()
    );

    public static final Block PALE_OAK_WALL_HANGING_SIGN = register(
            "pale_oak_wall_hanging_sign",
            p_379248_ -> new WallHangingSignBlock(HolycubeBackport.PALE_OAK_TYPE, p_379248_),
            wallVariant(PALE_OAK_HANGING_SIGN, true)
                    .mapColor(PALE_OAK_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()
    );

    public static final Block RESIN_BLOCK = register(
            "resin_block", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).sound(HolyBackSounds.RESIN)
    );
    public static final Block RESIN_BRICKS = register(
            "resin_bricks",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(HolyBackSounds.RESIN_BRICKS)
                    .strength(1.5F, 6.0F)
    );
    public static final Block RESIN_BRICK_STAIRS = registerLegacyStair("resin_brick_stairs", RESIN_BRICKS);
    public static final Block RESIN_BRICK_SLAB = register(
            "resin_brick_slab",
            p_382763_ -> new SlabBlock(p_382763_),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(HolyBackSounds.RESIN_BRICKS)
                    .strength(1.5F, 6.0F)
    );
    public static final Block RESIN_BRICK_WALL = register(
            "resin_brick_wall",
            p_382767_ -> new WallBlock(p_382767_),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(HolyBackSounds.RESIN_BRICKS)
                    .strength(1.5F, 6.0F)
    );
    public static final Block CHISELED_RESIN_BRICKS = register(
            "chiseled_resin_bricks",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(HolyBackSounds.RESIN_BRICKS)
                    .strength(1.5F, 6.0F)
    );

    public static final Block RESIN_CLUMP = register(
            "resin_clump",
            MultifaceBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .replaceable()
                    .noCollission()
                    .sound(HolyBackSounds.RESIN)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block FIREFLY_BUSH = register(
            "firefly_bush",
            FireflyBushBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .ignitedByLava()
                    .lightLevel(p_400949_ -> 2)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block BUSH = register(
            "bush",
            BushBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );


    public static final Block SHORT_DRY_GRASS = register(
            "short_dry_grass",
            ShortDryGrassBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block TALL_DRY_GRASS = register(
            "tall_dry_grass",
            TallDryGrassBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block LEAF_LITTER = register(
            "leaf_litter",
            LeafLitterBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .replaceable()
                    .noCollission()
                    .sound(HolyBackSounds.LEAF_LITTER)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block CACTUS_FLOWER = register(
            "cactus_flower",
            CactusFlowerBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .noCollission()
                    .instabreak()
                    .ignitedByLava()
                    .sound(HolyBackSounds.CACTUS_FLOWER)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static Block GRASS_STAIRS ;
    public static Block GRASS_SLAB ;
    public static Block PATH_STAIRS ;
    public static Block PATH_SLAB ;



    private static Block register(String id, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties){
        Block block = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.withDefaultNamespace(id),function.apply(properties));
        return block;
    }

    private static BlockBehaviour.Properties logProperties(MapColor p_368712_, MapColor p_368663_, SoundType p_368633_) {
        return BlockBehaviour.Properties.of()
                .mapColor(p_152624_ -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_368712_ : p_368663_)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(p_368633_)
                .ignitedByLava();
    }


    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static BlockBehaviour.Properties wallVariant(Block p_368685_, boolean p_368625_) {
        BlockBehaviour.Properties blockbehaviour$properties = p_368685_.properties();
        BlockBehaviour.Properties blockbehaviour$properties1 = BlockBehaviour.Properties.of().lootFrom(()->p_368685_);

        return blockbehaviour$properties1;
    }

    private static BlockBehaviour.Properties flowerPotProperties() {
        return BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties buttonProperties() {
        return BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
    }

    @Deprecated
    private static Block registerLegacyStair(String p_368526_, Block p_368697_) {
        return register(p_368526_, p_368077_ -> new StairBlock(p_368697_.defaultBlockState(), p_368077_), BlockBehaviour.Properties.ofLegacyCopy(p_368697_));
    }

    private static Block register(String p_50796_, BlockBehaviour.Properties p_368759_) {
        return register(p_50796_, Block::new, p_368759_);
    }


}
