package de.greenman999.potatoclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import de.greenman999.potatoclient.screens.ConfigScreen;
import net.minecraft.SharedConstants;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    @Mutable
    @Shadow @Final private boolean isMinceraft;
    @Unique
    private static final Identifier logo = new Identifier("potatoclient", "logo.png");

    @Unique
    private static final Identifier background = new Identifier("potatoclient","background.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void addButton(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width - 100, 2, 98, 20, Text.of("Potato Config"), (button) -> {
            this.client.setScreen(new ConfigScreen(Text.of("Test"), this));
        }));
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 0), method = "render")
    private Identifier injectBackground(Identifier identifier) {

        return background;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0), index = 2)
    private String injectBrand(String text) {
        return "Potato Client " + SharedConstants.getGameVersion().getName();
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;", ordinal = 0))
    public Element removeLanguageButton(TitleScreen instance, Element element) {
        return null;
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;", ordinal = 3))
    public Element removeAccessibilityButton(TitleScreen instance, Element element) {
        return null;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    public void removeSplashText(MatrixStack matrixStack, TextRenderer textRenderer, String s, int centerX, int y, int color) {

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawWithOutline(IILjava/util/function/BiConsumer;)V"))
    public void cancelMCRender(TitleScreen instance, int var1, int var2, BiConsumer biConsumer) {

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/TitleScreen.drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIFFIIII)V"))
    public void removeEditionLogo(MatrixStack matrixStack, int var1, int var2, float var3, float var4, int var5, int var6, int var7, int var8) {

    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V", ordinal = 1))
    public void changeMCLogo(int i, Identifier identifier) {
        RenderSystem.setShaderTexture(0, logo);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", shift = At.Shift.AFTER))
    public void renderLogo(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.drawTexture(matrices,  (this.width / 2 - (256 / 2)) + 6, 30, 0, 0, 256, 200);
    }
}
