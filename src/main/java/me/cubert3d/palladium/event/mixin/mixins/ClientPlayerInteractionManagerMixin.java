package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.ToolSaverCallback;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClassInfo(
        authors = "cubert3d",
        date = "7/14/2021",
        type = ClassType.MIXIN
)

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"))
    private void attackBlockInject(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        ToolSaverCallback.EVENT.invoker().interact(false);
    }

    @Inject(method = "interactBlock(" +
            "Lnet/minecraft/client/network/ClientPlayerEntity;" +
            "Lnet/minecraft/client/world/ClientWorld;" +
            "Lnet/minecraft/util/Hand;" +
            "Lnet/minecraft/util/hit/BlockHitResult;" +
            ")Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"))
    private void interactBlockInject(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> info) {
        ToolSaverCallback.EVENT.invoker().interact(false);
    }

    @Inject(method = "interactItem(" +
            "Lnet/minecraft/entity/player/PlayerEntity;" +
            "Lnet/minecraft/world/World;" +
            "Lnet/minecraft/util/Hand;" +
            ")Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"))
    private void interactItemInject(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ToolSaverCallback.EVENT.invoker().interact(false);
    }

    @Inject(method = "attackEntity(" +
            "Lnet/minecraft/entity/player/PlayerEntity;" +
            "Lnet/minecraft/entity/Entity;" +
            ")V",
            at = @At("HEAD"))
    private void attackEntityInject(PlayerEntity player, Entity target, CallbackInfo info) {
        ToolSaverCallback.EVENT.invoker().interact(false);
    }

    @Inject(method = "interactEntity(" +
            "Lnet/minecraft/entity/player/PlayerEntity;" +
            "Lnet/minecraft/entity/Entity;" +
            "Lnet/minecraft/util/Hand;" +
            ")Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"))
    private void interactEntityInject(PlayerEntity player, Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ToolSaverCallback.EVENT.invoker().interact(false);
    }
}
