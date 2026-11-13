package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsClient;
import com.hattolo.consolesounds.ConsoleSoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSliderButton.class)
public class ScrollSliderWidgetMixin {
    @Shadow protected double value;

    @Inject(at = @At("HEAD"), method = "setValue")
    private void setValue(double newValue, CallbackInfo ci) {
        double v = Mth.clamp(roundDown2(newValue), 0.0D, 1.0D);
        double ov = Mth.clamp(roundDown2(value), 0.0D, 1.0D);

        if (v != ov && AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().enableSliderSounds) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ConsoleSoundsClient.UI_SCROLL_EVENT, 1.0F));
    }

    // makes decimals not go beyond the hundredths place
    private static double roundDown2(double d) {
        return Math.floor(d * 1e2) / 1e2;
    }
}
