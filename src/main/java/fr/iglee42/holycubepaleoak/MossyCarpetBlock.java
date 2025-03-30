package fr.iglee42.holycubepaleoak;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubepaleoak.HolyPOBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MossyCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
    private static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    private static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    private static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    private static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
    private static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
            Util.make(Maps.newEnumMap(Direction.class), p_380156_ -> {
                p_380156_.put(Direction.NORTH, NORTH);
                p_380156_.put(Direction.EAST, EAST);
                p_380156_.put(Direction.SOUTH, SOUTH);
                p_380156_.put(Direction.WEST, WEST);
            })
    );
    private static final float AABB_OFFSET = 1.0F;
    private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final int SHORT_HEIGHT = 10;
    private static final VoxelShape WEST_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);
    private static final VoxelShape EAST_SHORT_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    private static final VoxelShape NORTH_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
    private static final VoxelShape SOUTH_SHORT_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    @Override
    public MapCodec<MossyCarpetBlock> codec() {
        return CODEC;
    }

    public MossyCarpetBlock(BlockBehaviour.Properties p_380381_) {
        super(p_380381_);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(BASE, Boolean.valueOf(true))
                        .setValue(NORTH, WallSide.NONE)
                        .setValue(EAST, WallSide.NONE)
                        .setValue(SOUTH, WallSide.NONE)
                        .setValue(WEST, WallSide.NONE)
        );
        this.shapesCache = ImmutableMap.copyOf(
                this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
        );
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.empty();
    }

    private static VoxelShape calculateShape(BlockState p_379714_) {
        VoxelShape voxelshape = Shapes.empty();
        if (p_379714_.getValue(BASE)) {
            voxelshape = DOWN_AABB;
        }
        voxelshape = switch ((WallSide)p_379714_.getValue(NORTH)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, NORTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, NORTH_AABB);
        };

        voxelshape = switch ((WallSide)p_379714_.getValue(SOUTH)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, SOUTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, SOUTH_AABB);
        };

        voxelshape = switch ((WallSide)p_379714_.getValue(EAST)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, EAST_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, EAST_AABB);
        };

        voxelshape = switch ((WallSide)p_379714_.getValue(WEST)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, WEST_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, WEST_AABB);
        };
        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    @Override
    protected VoxelShape getShape(BlockState p_380262_, BlockGetter p_379532_, BlockPos p_379586_, CollisionContext p_380281_) {
        return this.shapesCache.get(p_380262_);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState p_380336_, BlockGetter p_380068_, BlockPos p_379717_, CollisionContext p_379651_) {
        return p_380336_.getValue(BASE) ? DOWN_AABB : Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState p_320652_, BlockGetter p_320953_, BlockPos p_320082_) {
        return false;
    }

    @Override
    protected boolean canSurvive(BlockState p_379574_, LevelReader p_379768_, BlockPos p_380354_) {
        BlockState blockstate = p_379768_.getBlockState(p_380354_.below());
        return p_379574_.getValue(BASE) ? !blockstate.isAir() : blockstate.is(this) && blockstate.getValue(BASE);
    }

    private static boolean hasFaces(BlockState p_379942_) {
        if (p_379942_.getValue(BASE)) {
            return true;
        } else {
            for (EnumProperty<WallSide> enumproperty : PROPERTY_BY_DIRECTION.values()) {
                if (p_379942_.getValue(enumproperty) != WallSide.NONE) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean canSupportAtFace(BlockGetter p_380047_, BlockPos p_379895_, Direction p_380188_) {
        BlockPos blockpos = p_379895_.relative(p_380188_);
        BlockState blockstate = p_380047_.getBlockState(blockpos);
        return p_380188_ == Direction.UP ? false : MultifaceBlock.canAttachTo(p_380047_, p_380188_, p_379895_,blockstate);
    }

    private static BlockState getUpdatedState(BlockState p_379720_, BlockGetter p_379555_, BlockPos p_379912_, boolean p_379623_) {
        BlockState blockstate = null;
        BlockState blockstate1 = null;
        p_379623_ |= p_379720_.getValue(BASE);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
            WallSide wallside = canSupportAtFace(p_379555_, p_379912_, direction)
                    ? (p_379623_ ? WallSide.LOW : p_379720_.getValue(enumproperty))
                    : WallSide.NONE;
            if (wallside == WallSide.LOW) {
                if (blockstate == null) {
                    blockstate = p_379555_.getBlockState(p_379912_.above());
                }

                if (blockstate.is(HolyPOBlocks.PALE_MOSS_CARPET) && blockstate.getValue(enumproperty) != WallSide.NONE && !blockstate.getValue(BASE)) {
                    wallside = WallSide.TALL;
                }

                if (!p_379720_.getValue(BASE)) {
                    if (blockstate1 == null) {
                        blockstate1 = p_379555_.getBlockState(p_379912_.below());
                    }

                    if (blockstate1.is(HolyPOBlocks.PALE_MOSS_CARPET) && blockstate1.getValue(enumproperty) == WallSide.NONE) {
                        wallside = WallSide.NONE;
                    }
                }
            }

            p_379720_ = p_379720_.setValue(enumproperty, wallside);
        }

        return p_379720_;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_380111_) {
        return getUpdatedState(this.defaultBlockState(), p_380111_.getLevel(), p_380111_.getClickedPos(), true);
    }

    public static void placeAt(LevelAccessor p_379453_, BlockPos p_380271_, RandomSource p_379424_, int p_380167_) {
        BlockState blockstate = HolyPOBlocks.PALE_MOSS_CARPET.defaultBlockState();
        BlockState blockstate1 = getUpdatedState(blockstate, p_379453_, p_380271_, true);
        p_379453_.setBlock(p_380271_, blockstate1, 3);
        BlockState blockstate2 = createTopperWithSideChance(p_379453_, p_380271_, p_379424_::nextBoolean);
        if (!blockstate2.isAir()) {
            p_379453_.setBlock(p_380271_.above(), blockstate2, p_380167_);
        }
    }

    @Override
    public void setPlacedBy(Level p_380310_, BlockPos p_380202_, BlockState p_379659_, @Nullable LivingEntity p_379877_, ItemStack p_380344_) {
        if (!p_380310_.isClientSide) {
            RandomSource randomsource = p_380310_.getRandom();
            BlockState blockstate = createTopperWithSideChance(p_380310_, p_380202_, randomsource::nextBoolean);
            if (!blockstate.isAir()) {
                p_380310_.setBlock(p_380202_.above(), blockstate, 3);
            }
        }
    }

    private static BlockState createTopperWithSideChance(BlockGetter p_380187_, BlockPos p_380387_, BooleanSupplier p_380154_) {
        BlockPos blockpos = p_380387_.above();
        BlockState blockstate = p_380187_.getBlockState(blockpos);
        boolean flag = blockstate.is(HolyPOBlocks.PALE_MOSS_CARPET);
        if ((!flag || !blockstate.getValue(BASE)) && (flag || blockstate.canBeReplaced())) {
            BlockState blockstate1 = HolyPOBlocks.PALE_MOSS_CARPET.defaultBlockState().setValue(BASE, Boolean.valueOf(false));
            BlockState blockstate2 = getUpdatedState(blockstate1, p_380187_, p_380387_.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
                if (blockstate2.getValue(enumproperty) != WallSide.NONE && !p_380154_.getAsBoolean()) {
                    blockstate2 = blockstate2.setValue(enumproperty, WallSide.NONE);
                }
            }

            return hasFaces(blockstate2) && blockstate2 != blockstate ? blockstate2 : Blocks.AIR.defaultBlockState();
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    protected BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (!p_60541_.canSurvive(p_60544_, p_60545_)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            BlockState blockstate = getUpdatedState(p_60541_, p_60544_, p_60545_, false);
            return !hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_379510_) {
        p_379510_.add(BASE, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected BlockState rotate(BlockState p_379325_, Rotation p_380164_) {
        return switch (p_380164_) {
            case CLOCKWISE_180 -> (BlockState)p_379325_.setValue(NORTH, p_379325_.getValue(SOUTH))
                    .setValue(EAST, p_379325_.getValue(WEST))
                    .setValue(SOUTH, p_379325_.getValue(NORTH))
                    .setValue(WEST, p_379325_.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> (BlockState)p_379325_.setValue(NORTH, p_379325_.getValue(EAST))
                    .setValue(EAST, p_379325_.getValue(SOUTH))
                    .setValue(SOUTH, p_379325_.getValue(WEST))
                    .setValue(WEST, p_379325_.getValue(NORTH));
            case CLOCKWISE_90 -> (BlockState)p_379325_.setValue(NORTH, p_379325_.getValue(WEST))
                    .setValue(EAST, p_379325_.getValue(NORTH))
                    .setValue(SOUTH, p_379325_.getValue(EAST))
                    .setValue(WEST, p_379325_.getValue(SOUTH));
            default -> p_379325_;
        };
    }

    @Override
    protected BlockState mirror(BlockState p_379462_, Mirror p_380184_) {
        return switch (p_380184_) {
            case LEFT_RIGHT -> (BlockState)p_379462_.setValue(NORTH, p_379462_.getValue(SOUTH)).setValue(SOUTH, p_379462_.getValue(NORTH));
            case FRONT_BACK -> (BlockState)p_379462_.setValue(EAST, p_379462_.getValue(WEST)).setValue(WEST, p_379462_.getValue(EAST));
            default -> super.mirror(p_379462_, p_380184_);
        };
    }

    @Nullable
    public static EnumProperty<WallSide> getPropertyForFace(Direction p_379421_) {
        return PROPERTY_BY_DIRECTION.get(p_379421_);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_379909_, BlockPos p_379807_, BlockState p_379358_) {
        return p_379358_.getValue(BASE) && !createTopperWithSideChance(p_379909_, p_379807_, () -> true).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_380168_, RandomSource p_380045_, BlockPos p_380299_, BlockState p_379595_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_379402_, RandomSource p_379670_, BlockPos p_379387_, BlockState p_379934_) {
        BlockState blockstate = createTopperWithSideChance(p_379402_, p_379387_, () -> true);
        if (!blockstate.isAir()) {
            p_379402_.setBlock(p_379387_.above(), blockstate, 3);
        }
    }
}
