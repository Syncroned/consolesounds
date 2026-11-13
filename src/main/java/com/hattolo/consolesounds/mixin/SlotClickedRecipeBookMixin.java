package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsClient;
import com.hattolo.consolesounds.ConsoleSoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookComponent.class)
public abstract class SlotClickedRecipeBookMixin {
    @Shadow @Final private GhostSlots ghostSlots;
    @Shadow protected abstract boolean isCraftingSlot(Slot slot);

    @Inject(at = @At("HEAD"), method = "slotClicked")
    private void slotClicked(Slot slot, CallbackInfo ci) {
        if (slot != null && this.isCraftingSlot(slot)) {
            if (!((GhostSlotsAccessor) ghostSlots).consolesounds$getIngredients().isEmpty()) {
                if (AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().enableCraftingSounds) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ConsoleSoundsClient.UI_FAIL_EVENT, 1.0F));
            }
        }
    }
}
