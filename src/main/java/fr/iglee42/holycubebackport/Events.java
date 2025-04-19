package fr.iglee42.holycubebackport;

import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD,modid = HolycubeBackport.MODID)
public class Events {

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_LOG, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_WOOD, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.STRIPPED_PALE_OAK_LOG, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.STRIPPED_PALE_OAK_WOOD,event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_PLANKS, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_STAIRS, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_SLAB, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_FENCE, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_FENCE_GATE, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_DOOR, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_TRAPDOOR, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_PRESSURE_PLATE,event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_BUTTON, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.PALE_OAK_LEAVES, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, HolyBackItems.PALE_OAK_SIGN, event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, HolyBackItems.PALE_OAK_HANGING_SIGN,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.TOOLS_AND_UTILITIES, HolyBackItems.PALE_OAK_BOAT,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.TOOLS_AND_UTILITIES, HolyBackItems.PALE_OAK_CHEST_BOAT,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.PALE_OAK_SAPLING,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.CLOSED_EYEBLOSSOM,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.OPEN_EYEBLOSSOM,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.PALE_MOSS_BLOCK,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.PALE_MOSS_CARPET,event.getParentEntries(),event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.PALE_HANGING_MOSS,event.getParentEntries(),event);

        registerItemInTab(CreativeModeTabs.INGREDIENTS, HolyBackItems.RESIN_BRICK, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.INGREDIENTS, HolyBackItems.RESIN_CLUMP, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.RESIN_BRICKS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.RESIN_BRICK_STAIRS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.RESIN_BRICK_SLAB, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.RESIN_BRICK_WALL, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.BUILDING_BLOCKS, HolyBackItems.CHISELED_RESIN_BRICKS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.RESIN_BLOCK, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.GRASS_STAIRS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.GRASS_SLAB, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.FIREFLY_BUSH, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.BUSH, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.DRY_SHORT_GRASS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.DRY_TALL_GRASS, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.LEAF_LITER, event.getParentEntries(), event);
        registerItemInTab(CreativeModeTabs.NATURAL_BLOCKS, HolyBackItems.CACTUS_FLOWER, event.getParentEntries(), event);
    }

    private static void registerItemInTab(ResourceKey<CreativeModeTab> tab, Item item, ObjectSortedSet<ItemStack> currentTab, BuildCreativeModeTabContentsEvent event){
        if (tab.equals(event.getTabKey())){
            ItemStack stack = new ItemStack(item);
            if (!currentTab.contains(stack))
                event.accept(stack, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
            if (!event.getSearchEntries().contains(stack))
                event.accept(stack, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
        }
    }

}
