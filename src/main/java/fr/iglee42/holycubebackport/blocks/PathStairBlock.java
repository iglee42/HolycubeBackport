package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.IntStream;

public class PathStairBlock extends StairBlock {

    protected static final VoxelShape TOP_AABB = Block.box(0.0, 7.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape OCTET_NNN = Block.box(0.0, 0.0, 0.0, 8.0, 7.0, 8.0);
    protected static final VoxelShape OCTET_NNP = Block.box(0.0, 0.0, 8.0, 8.0, 7.0, 16.0);
    protected static final VoxelShape OCTET_NPN = Block.box(0.0, 7.0, 0.0, 8.0, 15.0, 8.0);
    protected static final VoxelShape OCTET_NPP = Block.box(0.0, 7.0, 8.0, 8.0, 15.0, 16.0);
    protected static final VoxelShape OCTET_PNN = Block.box(8.0, 0.0, 0.0, 16.0, 7.0, 8.0);
    protected static final VoxelShape OCTET_PNP = Block.box(8.0, 0.0, 8.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape OCTET_PPN = Block.box(8.0, 7.0, 0.0, 16.0, 15.0, 8.0);
    protected static final VoxelShape OCTET_PPP = Block.box(8.0, 7.0, 8.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    protected static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    private static VoxelShape[] makeShapes(VoxelShape p_56934_, VoxelShape p_56935_, VoxelShape p_56936_, VoxelShape p_56937_, VoxelShape p_56938_) {
        return IntStream.range(0, 16)
                .mapToObj(p_56945_ -> makeStairShape(p_56945_, p_56934_, p_56935_, p_56936_, p_56937_, p_56938_))
                .toArray(VoxelShape[]::new);
    }

    private static VoxelShape makeStairShape(
            int p_56865_, VoxelShape p_56866_, VoxelShape p_56867_, VoxelShape p_56868_, VoxelShape p_56869_, VoxelShape p_56870_
    ) {
        VoxelShape voxelshape = p_56866_;
        if ((p_56865_ & 1) != 0) {
            voxelshape = Shapes.or(p_56866_, p_56867_);
        }

        if ((p_56865_ & 2) != 0) {
            voxelshape = Shapes.or(voxelshape, p_56868_);
        }

        if ((p_56865_ & 4) != 0) {
            voxelshape = Shapes.or(voxelshape, p_56869_);
        }

        if ((p_56865_ & 8) != 0) {
            voxelshape = Shapes.or(voxelshape, p_56870_);
        }

        return voxelshape;
    }

    @Override
    protected VoxelShape getShape(BlockState p_56956_, BlockGetter p_56957_, BlockPos p_56958_, CollisionContext p_56959_) {
        return (p_56956_.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(p_56956_)]];
    }

    private int getShapeIndex(BlockState p_56983_) {
        return ((StairsShape)p_56983_.getValue(SHAPE)).ordinal() * 4 + ((Direction)p_56983_.getValue(FACING)).get2DDataValue();
    }

    public PathStairBlock(BlockState p_56862_, Properties p_56863_) {
        super(p_56862_, p_56863_);
    }
}
