package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsClient;
import com.hattolo.consolesounds.ConsoleSoundsConfig;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ExitScreenMixin {
    @Inject(at = @At("RETURN"), method = "keyPressed")
    private void keyPressed(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        var screen = (Screen) (Object) this;

        if (event.key() == InputConstants.KEY_ESCAPE && screen.shouldCloseOnEsc()) {
            if (AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().playSoundOnMenuExit) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ConsoleSoundsClient.UI_BACK_EVENT, 1.0F));
        }
    }
}
