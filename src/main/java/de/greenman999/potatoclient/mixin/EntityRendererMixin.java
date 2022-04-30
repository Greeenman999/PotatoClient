package de.greenman999.potatoclient.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.awt.image.BufferedImage;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/EntityRenderer;renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
            ),
            index = 1
    )
    public Text injectLogo(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        BufferedImage image = new BufferedImage(16, 16,BufferedImage.TYPE_INT_RGB);
        return entity instanceof PlayerEntity ? Text.of("Potato " + text.getString()) : text;
    }
}
