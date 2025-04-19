package fr.iglee42.holycubebackport;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import com.mojang.math.OctahedralGroup;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static com.mojang.math.OctahedralGroup.*;

public class Boxes {
    private static final Vec3 BLOCK_CENTER = new Vec3(0.5, 0.5, 0.5);

    public static VoxelShape box(double p_49797_, double p_49798_, double p_49799_, double p_49800_, double p_49801_, double p_49802_) {
        return Shapes.box(p_49797_ / 16.0, p_49798_ / 16.0, p_49799_ / 16.0, p_49800_ / 16.0, p_49801_ / 16.0, p_49802_ / 16.0);
    }

    public static VoxelShape[] boxes(int p_393774_, IntFunction<VoxelShape> p_394099_) {
        return IntStream.rangeClosed(0, p_393774_).mapToObj(p_394099_).toArray(VoxelShape[]::new);
    }

    public static VoxelShape cube(double p_394458_) {
        return cube(p_394458_, p_394458_, p_394458_);
    }

    public static VoxelShape cube(double p_393493_, double p_394533_, double p_394623_) {
        double d0 = p_394533_ / 2.0;
        return column(p_393493_, p_394623_, 8.0 - d0, 8.0 + d0);
    }

    public static VoxelShape column(double p_393922_, double p_394403_, double p_393991_) {
        return column(p_393922_, p_393922_, p_394403_, p_393991_);
    }

    public static VoxelShape column(double p_393678_, double p_394077_, double p_394409_, double p_394538_) {
        double d0 = p_393678_ / 2.0;
        double d1 = p_394077_ / 2.0;
        return box(8.0 - d0, p_394409_, 8.0 - d1, 8.0 + d0, p_394538_, 8.0 + d1);
    }

    public static Map<Direction.Axis, VoxelShape> rotateHorizontalAxis(VoxelShape p_394394_) {
        return rotateHorizontalAxis(p_394394_, BLOCK_CENTER);
    }

    public static Map<Direction.Axis, VoxelShape> rotateHorizontalAxis(VoxelShape p_393683_, Vec3 p_394313_) {
        return Maps.newEnumMap(
                Map.of(Direction.Axis.Z, p_393683_, Direction.Axis.X, rotate(p_393683_, fromXYAngles(Quadrant.R0, Quadrant.R90), p_394313_))
        );
    }

    public static VoxelShape rotate(VoxelShape p_393610_, OctahedralGroup p_393964_) {
        return rotate(p_393610_, p_393964_, BLOCK_CENTER);
    }

    public static VoxelShape rotate(VoxelShape p_394159_, OctahedralGroup p_393775_, Vec3 p_393709_) {
        if (p_393775_ == IDENTITY) {
            return p_394159_;
        } else {
            DiscreteVoxelShape discretevoxelshape = rotate(p_394159_.shape,p_393775_);
            if (p_394159_ instanceof CubeVoxelShape && BLOCK_CENTER.equals(p_393709_)) {
                return new CubeVoxelShape(discretevoxelshape);
            } else {
                Direction.Axis direction$axis = permute(p_393775_,Direction.Axis.X);
                Direction.Axis direction$axis1 = permute(p_393775_,Direction.Axis.Y);
                Direction.Axis direction$axis2 = permute(p_393775_,Direction.Axis.Z);
                DoubleList doublelist = p_394159_.getCoords(direction$axis);
                DoubleList doublelist1 = p_394159_.getCoords(direction$axis1);
                DoubleList doublelist2 = p_394159_.getCoords(direction$axis2);
                int flag = p_393775_.inverts(direction$axis) ? 1 : 0;
                int flag1 = p_393775_.inverts(direction$axis1) ? 1 : 0;
                int flag2 = p_393775_.inverts(direction$axis2) ? 1 : 0;
                boolean flag3 = direction$axis.choose(flag, flag1, flag2) == 1;
                boolean flag4 = direction$axis1.choose(flag, flag1, flag2) == 1;
                boolean flag5 = direction$axis2.choose(flag, flag1, flag2) == 1;
                return new ArrayVoxelShape(
                        discretevoxelshape,
                        makeAxis(doublelist, flag3, p_393709_.get(direction$axis), p_393709_.x),
                        makeAxis(doublelist1, flag4, p_393709_.get(direction$axis1), p_393709_.y),
                        makeAxis(doublelist2, flag5, p_393709_.get(direction$axis2), p_393709_.z)
                );
            }
        }
    }

    @VisibleForTesting
    static DoubleList makeAxis(DoubleList p_394476_, boolean p_393672_, double p_393636_, double p_394608_) {
        if (!p_393672_ && p_393636_ == p_394608_) {
            return p_394476_;
        } else {
            int i = p_394476_.size();
            DoubleList doublelist = new DoubleArrayList(i);
            int j = p_393672_ ? -1 : 1;

            for (int k = p_393672_ ? i - 1 : 0; k >= 0 && k < i; k += j) {
                doublelist.add(p_394608_ + j * (p_394476_.getDouble(k) - p_393636_));
            }

            return doublelist;
        }
    }

    private static final Direction.Axis[] AXES = Direction.Axis.values();

    public static Direction.Axis permute(OctahedralGroup group,Direction.Axis p_393703_) {
        return AXES[group.permutation.permutation(p_393703_.ordinal())];
    }


    public static Map<Direction, VoxelShape> rotateHorizontal(VoxelShape p_393618_) {
        return rotateHorizontal(p_393618_, BLOCK_CENTER);
    }

    public static Map<Direction, VoxelShape> rotateHorizontal(VoxelShape p_394145_, Vec3 p_393544_) {
        return Maps.newEnumMap(
                Map.of(
                        Direction.NORTH,
                        p_394145_,
                        Direction.EAST,
                        rotate(p_394145_, fromXYAngles(Quadrant.R0, Quadrant.R90), p_393544_),
                        Direction.SOUTH,
                        rotate(p_394145_, fromXYAngles(Quadrant.R0, Quadrant.R180), p_393544_),
                        Direction.WEST,
                        rotate(p_394145_, fromXYAngles(Quadrant.R0, Quadrant.R270), p_393544_)
                )
        );
    }

    private static final OctahedralGroup[][] XY_TABLE = Util.make(new OctahedralGroup[Quadrant.values().length][Quadrant.values().length], p_403831_ -> {
        for (Quadrant quadrant : Quadrant.values()) {
            for (Quadrant quadrant1 : Quadrant.values()) {
                OctahedralGroup octahedralgroup = IDENTITY;

                for (int i = 0; i < quadrant1.shift; i++) {
                    octahedralgroup = octahedralgroup.compose(ROT_90_Y_NEG);
                }

                for (int j = 0; j < quadrant.shift; j++) {
                    octahedralgroup = octahedralgroup.compose(ROT_90_X_NEG);
                }

                p_403831_[quadrant.ordinal()][quadrant1.ordinal()] = octahedralgroup;
            }
        }
    });
    public static OctahedralGroup fromXYAngles(Quadrant p_405465_, Quadrant p_405597_) {
        return XY_TABLE[p_405465_.ordinal()][p_405597_.ordinal()];
    }

    private static DiscreteVoxelShape rotate(DiscreteVoxelShape
                                             shape,OctahedralGroup p_394332_) {
        if (p_394332_ == OctahedralGroup.IDENTITY) {
            return shape;
        } else {
            Direction.Axis direction$axis = permute(p_394332_,Direction.Axis.X);
            Direction.Axis direction$axis1 =permute(p_394332_,Direction.Axis.Y);
            Direction.Axis direction$axis2 =permute(p_394332_,Direction.Axis.Z);
            int i = direction$axis.choose(shape.xSize, shape.ySize, shape.zSize);
            int j = direction$axis1.choose(shape.xSize, shape.ySize, shape.zSize);
            int k = direction$axis2.choose(shape.xSize, shape.ySize, shape.zSize);
            int flag = p_394332_.inverts(direction$axis) ? 1 : 0;
            int flag1 = p_394332_.inverts(direction$axis1) ? 1 : 0;
            int flag2 = p_394332_.inverts(direction$axis2) ? 1 : 0;
            boolean flag3 = direction$axis.choose(flag, flag1, flag2) == 1;
            boolean flag4 = direction$axis1.choose(flag, flag1, flag2) == 1;
            boolean flag5 = direction$axis2.choose(flag, flag1, flag2) == 1;
            DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(i, j, k);

            for (int l = 0; l < shape.xSize; l++) {
                for (int i1 = 0; i1 < shape.ySize; i1++) {
                    for (int j1 = 0; j1 < shape.zSize; j1++) {
                        if (shape.isFull(l, i1, j1)) {
                            int k1 = direction$axis.choose(l, i1, j1);
                            int l1 = direction$axis1.choose(l, i1, j1);
                            int i2 = direction$axis2.choose(l, i1, j1);
                            discretevoxelshape.fill(flag3 ? i - 1 - k1 : k1, flag4 ? j - 1 - l1 : l1, flag5 ? k - 1 - i2 : i2);
                        }
                    }
                }
            }

            return discretevoxelshape;
        }
    }

}
