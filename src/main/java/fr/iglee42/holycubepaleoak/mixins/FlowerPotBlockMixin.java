package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOBlocks;
import fr.iglee42.holycubepaleoak.HolyPOItems;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin {

    @Shadow protected abstract boolean isEmpty();

    @Inject(method = "useItemOn",at=@At("HEAD"), cancellable = true)
    private void holycubepaleoak$addCustomFlowers(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_316826_, CallbackInfoReturnable<ItemInteractionResult> cir){
        if (this.isEmpty()) {
            if (stack.is(HolyPOItems.CLOSED_EYEBLOSSOM)) {
                holycubepaleoak$setCustomPot(level,pos,stack,HolyPOBlocks.POTTED_CLOSED_EYEBLOSSOM,player);
                cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
            } else if (stack.is(HolyPOItems.OPEN_EYEBLOSSOM)) {
                holycubepaleoak$setCustomPot(level,pos,stack,HolyPOBlocks.POTTED_OPEN_EYEBLOSSOM,player);
                cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
            } else if (stack.is(HolyPOItems.PALE_OAK_SAPLING)) {
                holycubepaleoak$setCustomPot(level,pos,stack,HolyPOBlocks.POTTED_PALE_OAK_SAPLING,player);
                cir.setReturnValue(ItemInteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }

    @Unique
    private void holycubepaleoak$setCustomPot(Level level, BlockPos pos, ItemStack stack, Block block, Player player){
        level.setBlock(pos, block.defaultBlockState(), 3);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        player.awardStat(Stats.POT_FLOWER);
        stack.consume(1, player);
    }


}
