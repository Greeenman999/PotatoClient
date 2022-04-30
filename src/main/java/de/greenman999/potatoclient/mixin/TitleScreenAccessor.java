package de.greenman999.potatoclient.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {

    @Accessor("isMinceraft")
    public void setisMinceraft(boolean isMinceraft);
}
