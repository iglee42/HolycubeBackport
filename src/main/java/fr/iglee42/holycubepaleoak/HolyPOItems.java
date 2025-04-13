package fr.iglee42.holycubepaleoak;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;

import static fr.iglee42.holycubepaleoak.HolycubePaleOak.MODID;
import static net.minecraft.world.item.Items.registerBlock;
import static net.minecraft.world.item.Items.registerItem;

public class HolyPOItems {

    public static final Item PALE_OAK_PLANKS = registerBlock(HolyPOBlocks.PALE_OAK_PLANKS);
    public static final Item PALE_OAK_SAPLING = registerBlock(HolyPOBlocks.PALE_OAK_SAPLING);
    public static final Item PALE_OAK_LOG = registerBlock(HolyPOBlocks.PALE_OAK_LOG);
    public static final Item STRIPPED_PALE_OAK_LOG = registerBlock(HolyPOBlocks.STRIPPED_PALE_OAK_LOG);
    public static final Item STRIPPED_PALE_OAK_WOOD = registerBlock(HolyPOBlocks.STRIPPED_PALE_OAK_WOOD);
    public static final Item PALE_OAK_WOOD = registerBlock(HolyPOBlocks.PALE_OAK_WOOD);
    public static final Item PALE_OAK_LEAVES = registerBlock(HolyPOBlocks.PALE_OAK_LEAVES);
    public static final Item PALE_OAK_SLAB = registerBlock(HolyPOBlocks.PALE_OAK_SLAB);
    public static final Item PALE_OAK_FENCE = registerBlock(HolyPOBlocks.PALE_OAK_FENCE);
    public static final Item PALE_OAK_STAIRS = registerBlock(HolyPOBlocks.PALE_OAK_STAIRS);
    public static final Item PALE_OAK_BUTTON = registerBlock(HolyPOBlocks.PALE_OAK_BUTTON);
    public static final Item PALE_OAK_PRESSURE_PLATE = registerBlock(HolyPOBlocks.PALE_OAK_PRESSURE_PLATE);
    public static final Item PALE_OAK_DOOR = registerBlock(new DoubleHighBlockItem(HolyPOBlocks.PALE_OAK_DOOR, new Item.Properties()));
    public static final Item PALE_OAK_TRAPDOOR = registerBlock(HolyPOBlocks.PALE_OAK_TRAPDOOR);
    public static final Item PALE_OAK_FENCE_GATE = registerBlock(HolyPOBlocks.PALE_OAK_FENCE_GATE);
    public static final Item PALE_OAK_BOAT = registerItem(
            "pale_oak_boat",new BoatItem(false,HolycubePaleOak.PALE_OAK_BOAT, new Item.Properties().stacksTo(1))
    );
    public static final Item PALE_OAK_CHEST_BOAT = registerItem(
            "pale_oak_chest_boat",new BoatItem(true,HolycubePaleOak.PALE_OAK_BOAT, new Item.Properties().stacksTo(1))
    );
    public static final Item PALE_OAK_SIGN = registerItem(
            "pale_oak_sign", new SignItem( new Item.Properties().stacksTo(16),HolyPOBlocks.PALE_OAK_SIGN, HolyPOBlocks.PALE_OAK_WALL_SIGN)
    );
    public static final Item PALE_OAK_HANGING_SIGN = registerItem(
            "pale_oak_hanging_sign", new HangingSignItem(HolyPOBlocks.PALE_OAK_HANGING_SIGN, HolyPOBlocks.PALE_OAK_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16))
    );

    public static final Item OPEN_EYEBLOSSOM = registerBlock(HolyPOBlocks.OPEN_EYEBLOSSOM);
    public static final Item CLOSED_EYEBLOSSOM = registerBlock(HolyPOBlocks.CLOSED_EYEBLOSSOM);
    public static final Item PALE_MOSS_CARPET = registerBlock(HolyPOBlocks.PALE_MOSS_CARPET);
    public static final Item PALE_HANGING_MOSS = registerBlock(HolyPOBlocks.PALE_HANGING_MOSS);
    public static final Item PALE_MOSS_BLOCK = registerBlock(HolyPOBlocks.PALE_MOSS_BLOCK);

    public static final Item RESIN_BLOCK = registerBlock(HolyPOBlocks.RESIN_BLOCK);
    public static final Item RESIN_BRICKS = registerBlock(HolyPOBlocks.RESIN_BRICKS);
    public static final Item RESIN_BRICK_STAIRS = registerBlock(HolyPOBlocks.RESIN_BRICK_STAIRS);
    public static final Item RESIN_BRICK_SLAB = registerBlock(HolyPOBlocks.RESIN_BRICK_SLAB);
    public static final Item RESIN_BRICK_WALL = registerBlock(HolyPOBlocks.RESIN_BRICK_WALL);
    public static final Item CHISELED_RESIN_BRICKS = registerBlock(HolyPOBlocks.CHISELED_RESIN_BRICKS);
    public static final Item RESIN_BRICK = registerItem("resin_brick",new Item(new Item.Properties()));
    public static final Item RESIN_CLUMP = registerItem("resin_clump", new Item(new Item.Properties()));






}
