package fr.iglee42.holycubebackport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class BonemealUtils {

    public static boolean hasSpreadableNeighbourPos(LevelReader p_401785_, BlockPos p_401901_, BlockState p_401773_) {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.stream().toList(), p_401785_, p_401901_, p_401773_).isPresent();
    }

    public static Optional<BlockPos> findSpreadableNeighbourPos(Level p_401928_, BlockPos p_401865_, BlockState p_401938_) {
        return getSpreadableNeighbourPos(Direction.Plane.HORIZONTAL.shuffledCopy(p_401928_.random), p_401928_, p_401865_, p_401938_);
    }

    private static Optional<BlockPos> getSpreadableNeighbourPos(List<Direction> p_401891_, LevelReader p_401878_, BlockPos p_401894_, BlockState p_401830_) {
        for(Direction direction : p_401891_) {
            BlockPos blockpos = p_401894_.relative(direction);
            if (p_401878_.isEmptyBlock(blockpos) && p_401830_.canSurvive(p_401878_, blockpos)) {
                return Optional.of(blockpos);
            }
        }

        return Optional.empty();
    }
}
