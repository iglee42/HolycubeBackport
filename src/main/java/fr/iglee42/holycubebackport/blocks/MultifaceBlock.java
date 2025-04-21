package fr.iglee42.holycubebackport.blocks;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.Boxes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultifaceBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<MultifaceBlock> CODEC = simpleCodec(MultifaceBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
    protected static final Direction[] DIRECTIONS = Direction.values();
    private final Function<BlockState, VoxelShape> shapes;
    private final boolean canRotate;
    private final boolean canMirrorX;
    private final boolean canMirrorZ;

    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    public MultifaceBlock(BlockBehaviour.Properties p_153822_) {
        super(p_153822_);
        this.registerDefaultState(getDefaultMultifaceState(this.stateDefinition));
        this.shapes = this.makeShapes();
        this.canRotate = Direction.Plane.HORIZONTAL.stream().allMatch(this::isFaceSupported);
        this.canMirrorX = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L;
        this.canMirrorZ = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L;
    }

    private Function<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> map = Boxes.rotateAll(Boxes.boxZ(16.0, 0.0, 1.0));
        return this.getShapeForEachState(p_393369_ -> {
            VoxelShape voxelshape = Shapes.empty();

            for (Direction direction : DIRECTIONS) {
                if (hasFace(p_393369_, direction)) {
                    voxelshape = Shapes.or(voxelshape, map.get(direction));
                }
            }

            return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
        }, new Property[]{WATERLOGGED});
    }

    protected Function<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> p_152459_, Property<?>... p_394264_) {
        Map<? extends Property<?>, Object> map = Arrays.stream(p_394264_)
                .collect(Collectors.toMap(p_393321_ -> p_393321_, p_393320_ -> p_393320_.getPossibleValues().stream().toList().getFirst()));
        ImmutableMap<BlockState, VoxelShape> immutablemap = this.stateDefinition
                .getPossibleStates()
                .stream()
                .filter(p_393327_ -> map.entrySet().stream().allMatch(p_393329_ -> p_393327_.getValue((Property<?>)p_393329_.getKey()) == p_393329_.getValue()))
                .collect(ImmutableMap.toImmutableMap(Function.identity(), p_152459_));
        return p_393324_ -> {
            for (Map.Entry<? extends Property<?>, Object> entry : map.entrySet()) {
                p_393324_ = setValueHelper(p_393324_, (Property<?>)entry.getKey(), entry.getValue());
            }

            return immutablemap.get(p_393324_);
        };
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S p_394311_, Property<T> p_394352_, Object p_394525_) {
        return p_394311_.setValue(p_394352_, (T)p_394525_);
    }

    public static Set<Direction> availableFaces(BlockState p_221585_) {
        if (!(p_221585_.getBlock() instanceof MultifaceBlock)) {
            return Set.of();
        } else {
            Set<Direction> set = EnumSet.noneOf(Direction.class);

            for (Direction direction : Direction.values()) {
                if (hasFace(p_221585_, direction)) {
                    set.add(direction);
                }
            }

            return set;
        }
    }

    public static Set<Direction> unpack(byte p_221570_) {
        Set<Direction> set = EnumSet.noneOf(Direction.class);

        for (Direction direction : Direction.values()) {
            if ((p_221570_ & (byte)(1 << direction.ordinal())) > 0) {
                set.add(direction);
            }
        }

        return set;
    }

    public static byte pack(Collection<Direction> p_221577_) {
        byte b0 = 0;

        for (Direction direction : p_221577_) {
            b0 = (byte)(b0 | 1 << direction.ordinal());
        }

        return b0;
    }

    protected boolean isFaceSupported(Direction p_153921_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153917_) {
        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                p_153917_.add(getFaceProperty(direction));
            }
        }

        p_153917_.add(WATERLOGGED);
    }

    @Override
    protected BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (p_60541_.getValue(WATERLOGGED)) {
            p_60544_.scheduleTick(p_60545_, Fluids.WATER, Fluids.WATER.getTickDelay(p_60544_));
        }

        if (!hasAnyFace(p_60541_)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return hasFace(p_60541_, p_60542_) && !canAttachTo(p_60544_, p_60542_, p_60546_, p_60543_)
                    ? removeFace(p_60541_, getFaceProperty(p_60542_))
                    : p_60541_;
        }
    }



    @Override
    protected FluidState getFluidState(BlockState p_389529_) {
        return p_389529_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_389529_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_153851_, BlockGetter p_153852_, BlockPos p_153853_, CollisionContext p_153854_) {
        return this.shapes.apply(p_153851_);
    }

    @Override
    protected boolean canSurvive(BlockState p_153888_, LevelReader p_153889_, BlockPos p_153890_) {
        boolean flag = false;

        for (Direction direction : DIRECTIONS) {
            if (hasFace(p_153888_, direction)) {
                if (!canAttachTo(p_153889_, p_153890_, direction)) {
                    return false;
                }

                flag = true;
            }
        }

        return flag;
    }

    @Override
    protected boolean canBeReplaced(BlockState p_153848_, BlockPlaceContext p_153849_) {
        return !p_153849_.getItemInHand().is(this.asItem()) || hasAnyVacantFace(p_153848_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_153824_) {
        Level level = p_153824_.getLevel();
        BlockPos blockpos = p_153824_.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        return Arrays.stream(p_153824_.getNearestLookingDirections())
            .map(p_153865_ -> this.getStateForPlacement(blockstate, level, blockpos, p_153865_))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    public boolean isValidStateForPlacement(BlockGetter p_221572_, BlockState p_221573_, BlockPos p_221574_, Direction p_221575_) {
        if (this.isFaceSupported(p_221575_) && (!p_221573_.is(this) || !hasFace(p_221573_, p_221575_))) {
            BlockPos blockpos = p_221574_.relative(p_221575_);
            return canAttachTo(p_221572_, p_221575_, blockpos, p_221572_.getBlockState(blockpos));
        } else {
            return false;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockState p_153941_, BlockGetter p_153942_, BlockPos p_153943_, Direction p_153944_) {
        if (!this.isValidStateForPlacement(p_153942_, p_153941_, p_153943_, p_153944_)) {
            return null;
        } else {
            BlockState blockstate;
            if (p_153941_.is(this)) {
                blockstate = p_153941_;
            } else if (p_153941_.getFluidState().isSourceOfType(Fluids.WATER)) {
                blockstate = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
            } else {
                blockstate = this.defaultBlockState();
            }

            return blockstate.setValue(getFaceProperty(p_153944_), true);
        }
    }

    @Override
    protected BlockState rotate(BlockState p_153895_, Rotation p_153896_) {
        return !this.canRotate ? p_153895_ : this.mapDirections(p_153895_, p_153896_::rotate);
    }

    @Override
    protected BlockState mirror(BlockState p_153892_, Mirror p_153893_) {
        if (p_153893_ == Mirror.FRONT_BACK && !this.canMirrorX) {
            return p_153892_;
        } else {
            return p_153893_ == Mirror.LEFT_RIGHT && !this.canMirrorZ ? p_153892_ : this.mapDirections(p_153892_, p_153893_::mirror);
        }
    }

    private BlockState mapDirections(BlockState p_153911_, Function<Direction, Direction> p_153912_) {
        BlockState blockstate = p_153911_;

        for (Direction direction : DIRECTIONS) {
            if (this.isFaceSupported(direction)) {
                blockstate = blockstate.setValue(getFaceProperty(p_153912_.apply(direction)), p_153911_.getValue(getFaceProperty(direction)));
            }
        }

        return blockstate;
    }

    public static boolean hasFace(BlockState p_153901_, Direction p_153902_) {
        BooleanProperty booleanproperty = getFaceProperty(p_153902_);
        return p_153901_.getOptionalValue(booleanproperty).orElse(false);
    }

    public static boolean canAttachTo(BlockGetter p_383192_, BlockPos p_383013_, Direction p_383221_) {
        BlockPos blockpos = p_383013_.relative(p_383221_);
        BlockState blockstate = p_383192_.getBlockState(blockpos);
        return canAttachTo(p_383192_, p_383221_, blockpos, blockstate);
    }

    public static boolean canAttachTo(BlockGetter p_153830_, Direction p_153831_, BlockPos p_153832_, BlockState p_153833_) {
        return Block.isFaceFull(p_153833_.getBlockSupportShape(p_153830_, p_153832_), p_153831_.getOpposite())
            || Block.isFaceFull(p_153833_.getCollisionShape(p_153830_, p_153832_), p_153831_.getOpposite());
    }

    private static BlockState removeFace(BlockState p_153898_, BooleanProperty p_153899_) {
        BlockState blockstate = p_153898_.setValue(p_153899_, false);
        return hasAnyFace(blockstate) ? blockstate : Blocks.AIR.defaultBlockState();
    }

    public static BooleanProperty getFaceProperty(Direction p_153934_) {
        return PROPERTY_BY_DIRECTION.get(p_153934_);
    }

    private static BlockState getDefaultMultifaceState(StateDefinition<Block, BlockState> p_153919_) {
        BlockState blockstate = p_153919_.any().setValue(WATERLOGGED, false);

        for (BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values()) {
            blockstate = blockstate.trySetValue(booleanproperty, false);
        }

        return blockstate;
    }

    protected static boolean hasAnyFace(BlockState p_153961_) {
        for (Direction direction : DIRECTIONS) {
            if (hasFace(p_153961_, direction)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasAnyVacantFace(BlockState p_153963_) {
        for (Direction direction : DIRECTIONS) {
            if (!hasFace(p_153963_, direction)) {
                return true;
            }
        }

        return false;
    }
}
