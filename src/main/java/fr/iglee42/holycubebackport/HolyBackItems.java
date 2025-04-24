package fr.iglee42.holycubebackport;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

import static net.minecraft.world.item.Items.registerBlock;
import static net.minecraft.world.item.Items.registerItem;

public class HolyBackItems {

    public static final Item PALE_OAK_PLANKS = registerBlock(HolyBackBlocks.PALE_OAK_PLANKS);
    public static final Item PALE_OAK_SAPLING = registerBlock(HolyBackBlocks.PALE_OAK_SAPLING);
    public static final Item PALE_OAK_LOG = registerBlock(HolyBackBlocks.PALE_OAK_LOG);
    public static final Item STRIPPED_PALE_OAK_LOG = registerBlock(HolyBackBlocks.STRIPPED_PALE_OAK_LOG);
    public static final Item STRIPPED_PALE_OAK_WOOD = registerBlock(HolyBackBlocks.STRIPPED_PALE_OAK_WOOD);
    public static final Item PALE_OAK_WOOD = registerBlock(HolyBackBlocks.PALE_OAK_WOOD);
    public static final Item PALE_OAK_LEAVES = registerBlock(HolyBackBlocks.PALE_OAK_LEAVES);
    public static final Item PALE_OAK_SLAB = registerBlock(HolyBackBlocks.PALE_OAK_SLAB);
    public static final Item PALE_OAK_FENCE = registerBlock(HolyBackBlocks.PALE_OAK_FENCE);
    public static final Item PALE_OAK_STAIRS = registerBlock(HolyBackBlocks.PALE_OAK_STAIRS);
    public static final Item PALE_OAK_BUTTON = registerBlock(HolyBackBlocks.PALE_OAK_BUTTON);
    public static final Item PALE_OAK_PRESSURE_PLATE = registerBlock(HolyBackBlocks.PALE_OAK_PRESSURE_PLATE);
    public static final Item PALE_OAK_DOOR = registerBlock(new DoubleHighBlockItem(HolyBackBlocks.PALE_OAK_DOOR, new Item.Properties()));
    public static final Item PALE_OAK_TRAPDOOR = registerBlock(HolyBackBlocks.PALE_OAK_TRAPDOOR);
    public static final Item PALE_OAK_FENCE_GATE = registerBlock(HolyBackBlocks.PALE_OAK_FENCE_GATE);
    public static final Item PALE_OAK_BOAT = registerItem(
            "pale_oak_boat",new BoatItem(false, HolycubeBackport.PALE_OAK_BOAT, new Item.Properties().stacksTo(1))
    );
    public static final Item PALE_OAK_CHEST_BOAT = registerItem(
            "pale_oak_chest_boat",new BoatItem(true, HolycubeBackport.PALE_OAK_BOAT, new Item.Properties().stacksTo(1))
    );
    public static final Item PALE_OAK_SIGN = registerItem(
            "pale_oak_sign", new SignItem( new Item.Properties().stacksTo(16), HolyBackBlocks.PALE_OAK_SIGN, HolyBackBlocks.PALE_OAK_WALL_SIGN)
    );
    public static final Item PALE_OAK_HANGING_SIGN = registerItem(
            "pale_oak_hanging_sign", new HangingSignItem(HolyBackBlocks.PALE_OAK_HANGING_SIGN, HolyBackBlocks.PALE_OAK_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16))
    );

    public static final Item OPEN_EYEBLOSSOM = registerBlock(HolyBackBlocks.OPEN_EYEBLOSSOM);
    public static final Item CLOSED_EYEBLOSSOM = registerBlock(HolyBackBlocks.CLOSED_EYEBLOSSOM);
    public static final Item PALE_MOSS_CARPET = registerBlock(HolyBackBlocks.PALE_MOSS_CARPET);
    public static final Item PALE_HANGING_MOSS = registerBlock(HolyBackBlocks.PALE_HANGING_MOSS);
    public static final Item PALE_MOSS_BLOCK = registerBlock(HolyBackBlocks.PALE_MOSS_BLOCK);

    public static final Item RESIN_BLOCK = registerBlock(HolyBackBlocks.RESIN_BLOCK);
    public static final Item RESIN_BRICKS = registerBlock(HolyBackBlocks.RESIN_BRICKS);
    public static final Item RESIN_BRICK_STAIRS = registerBlock(HolyBackBlocks.RESIN_BRICK_STAIRS);
    public static final Item RESIN_BRICK_SLAB = registerBlock(HolyBackBlocks.RESIN_BRICK_SLAB);
    public static final Item RESIN_BRICK_WALL = registerBlock(HolyBackBlocks.RESIN_BRICK_WALL);
    public static final Item CHISELED_RESIN_BRICKS = registerBlock(HolyBackBlocks.CHISELED_RESIN_BRICKS);
    public static final Item RESIN_BRICK = registerItem("resin_brick",new Item(new Item.Properties()));
    public static final Item RESIN_CLUMP = registerBlock(HolyBackBlocks.RESIN_CLUMP);
    public static final Item FIREFLY_BUSH = registerBlock(HolyBackBlocks.FIREFLY_BUSH);
    public static final Item BUSH = registerBlock(HolyBackBlocks.BUSH);
    public static final Item DRY_SHORT_GRASS = registerBlock(HolyBackBlocks.SHORT_DRY_GRASS);
    public static final Item DRY_TALL_GRASS = registerBlock(HolyBackBlocks.TALL_DRY_GRASS);
    public static final Item LEAF_LITER = registerBlock(HolyBackBlocks.LEAF_LITTER);
    public static final Item CACTUS_FLOWER = registerBlock(HolyBackBlocks.CACTUS_FLOWER);
    public static final Item WILDFLOWERS = registerBlock(HolyBackBlocks.WILDFLOWERS);



    public static Item GRASS_SLAB;
    public static Item GRASS_STAIRS;
    public static Item PATH_SLAB;
    public static Item PATH_STAIRS;







}
