package net.ramixin.slashlength.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.EditBox;
import net.ramixin.slashlength.TextFieldWidgetDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EditBox.class)
public abstract class TextFieldWidgetMixin implements TextFieldWidgetDuck {

    @Shadow private int cursorPos;
    @Shadow private int highlightPos;

    @Unique
    private boolean isChatBox = false;

    @WrapOperation(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;insertText(Ljava/lang/String;)V", ordinal = 0))
    private void preventTruncationIfPastingCommand(EditBox instance, String input, Operation<Void> original) {
        boolean isCommand = false;
        if(isChatBox) {
            if (Math.min(Math.min(this.cursorPos, this.highlightPos), instance.getCursorPosition()) > 0) {
                isCommand = instance.getValue().startsWith("/");
            } else isCommand = input.startsWith("/");
        }
        if (isCommand) instance.setMaxLength(Integer.MAX_VALUE);
        else instance.setMaxLength(256);
        original.call(instance, input);
    }

    @Override
    public void slashLength$setAsChatBox() {
        isChatBox = true;
    }
}
