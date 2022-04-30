package de.greenman999.potatoclient.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    public void setNewTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Potato Client " + SharedConstants.getGameVersion().getName());
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V"))
    public void setAlternativeWindowIcon(Window window, InputStream inputStream1, InputStream inputStream2) throws IOException {
        InputStream inputStream16 = this.getClass().getClassLoader().getResourceAsStream("assets/potatoclient/icon_16x16.png");
        InputStream inputStream32 = this.getClass().getClassLoader().getResourceAsStream("assets/potatoclient/icon_32x32.png");
        window.setIcon(inputStream16, inputStream32);
    }
}
