package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathSlabBlock extends SlabBlock {

    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape TOP_AABB = Block.box(0.0, 8.0, 0.0, 16.0, 15.0, 16.0);

    @Override
    protected VoxelShape getShape(BlockState p_56390_, BlockGetter p_56391_, BlockPos p_56392_, CollisionContext p_56393_) {
        SlabType slabtype = p_56390_.getValue(TYPE);
        switch (slabtype) {
            case DOUBLE:
                return Block.box(0.0,0.0,0.0,16.0,15.0,16.0);
            case TOP:
                return TOP_AABB;
            default:
                return BOTTOM_AABB;
        }
    }
    public PathSlabBlock(Properties p_56359_) {
        super(p_56359_);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState p_56395_) {
        return true;
    }
}
