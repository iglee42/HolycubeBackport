package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.HolyBackSounds;
import fr.iglee42.holycubebackport.HolycubeBackport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<HangingMossBlock> CODEC = simpleCodec(HangingMossBlock::new);
    private static final int SIDE_PADDING = 1;
    private static final VoxelShape TIP_SHAPE = Block.box(1.0, 2.0, 1.0, 15.0, 16.0, 15.0);
    private static final VoxelShape BASE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final BooleanProperty TIP = BooleanProperty.create("tip");

    @Override
    public MapCodec<HangingMossBlock> codec() {
        return CODEC;
    }

    public HangingMossBlock(BlockBehaviour.Properties p_379903_) {
        super(p_379903_);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP, Boolean.valueOf(true)));
    }

    @Override
    protected VoxelShape getShape(BlockState p_379697_, BlockGetter p_380282_, BlockPos p_379821_, CollisionContext p_379644_) {
        return p_379697_.getValue(TIP) ? TIP_SHAPE : BASE_SHAPE;
    }

    @Override
    public void animateTick(BlockState p_379410_, Level p_379865_, BlockPos p_379365_, RandomSource p_380130_) {
        if (p_380130_.nextInt(500) == 0) {
            BlockState blockstate = p_379865_.getBlockState(p_379365_.above());
            if (blockstate.is(HolycubeBackport.PALE_OAK_LOGS) || blockstate.is(HolyBackBlocks.PALE_OAK_LEAVES)) {
                p_379865_.playLocalSound(
                        (double)p_379365_.getX(),
                        (double)p_379365_.getY(),
                        (double)p_379365_.getZ(),
                        HolyBackSounds.PALE_HANGING_MOSS_IDLE.get(),
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F,
                        false
                );
            }
        }
    }


    @Override
    protected boolean propagatesSkylightDown(BlockState p_320652_, BlockGetter p_320953_, BlockPos p_320082_) {
        return false;
    }

    @Override
    protected boolean canSurvive(BlockState p_380096_, LevelReader p_379969_, BlockPos p_380283_) {
        return this.canStayAtPosition(p_379969_, p_380283_);
    }

    private boolean canStayAtPosition(BlockGetter p_379546_, BlockPos p_379355_) {
        BlockPos blockpos = p_379355_.relative(Direction.UP);
        BlockState blockstate = p_379546_.getBlockState(blockpos);
        return MultifaceBlock.canAttachTo(p_379546_, Direction.UP, blockpos, blockstate) || blockstate.is(HolyBackBlocks.PALE_HANGING_MOSS);
    }

    @Override
    protected BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (!canStayAtPosition(p_60544_,p_60545_))
            p_60544_.scheduleTick(p_60545_,this,1);
        return p_60541_.setValue(TIP, Boolean.valueOf(!p_60544_.getBlockState(p_60545_.below()).is(this)));
    }


    @Override
    protected void tick(BlockState p_381085_, ServerLevel p_381014_, BlockPos p_381010_, RandomSource p_380962_) {
        if (!this.canStayAtPosition(p_381014_, p_381010_)) {
            p_381014_.destroyBlock(p_381010_, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_379416_) {
        p_379416_.add(TIP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_379509_, BlockPos p_379596_, BlockState p_380331_) {
        return this.canGrowInto(p_379509_.getBlockState(this.getTip(p_379509_, p_379596_).below()));
    }

    private boolean canGrowInto(BlockState p_379897_) {
        return p_379897_.isAir();
    }

    public BlockPos getTip(BlockGetter p_379440_, BlockPos p_380142_) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = p_380142_.mutable();

        BlockState blockstate;
        do {
            blockpos$mutableblockpos.move(Direction.DOWN);
            blockstate = p_379440_.getBlockState(blockpos$mutableblockpos);
        } while (blockstate.is(this));

        return blockpos$mutableblockpos.relative(Direction.UP).immutable();
    }

    @Override
    public boolean isBonemealSuccess(Level p_380206_, RandomSource p_380151_, BlockPos p_379719_, BlockState p_379567_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_379337_, RandomSource p_379974_, BlockPos p_379496_, BlockState p_379559_) {
        BlockPos blockpos = this.getTip(p_379337_, p_379496_).below();
        if (this.canGrowInto(p_379337_.getBlockState(blockpos))) {
            p_379337_.setBlockAndUpdate(blockpos, p_379559_.setValue(TIP, Boolean.valueOf(true)));
        }
    }
}
