package net.ramgames.nccl.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.StringHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StringHelper.class)
public class StringHelperMixin {

    @WrapOperation(method = "truncateChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/StringHelper;truncate(Ljava/lang/String;IZ)Ljava/lang/String;"))
    private static String removeTruncationLimitIfCommand(String text, int maxLength, boolean addEllipsis, Operation<String> original) {
        if(text.startsWith("/")) return text;
        return original.call(text, maxLength, addEllipsis);
    }

}
