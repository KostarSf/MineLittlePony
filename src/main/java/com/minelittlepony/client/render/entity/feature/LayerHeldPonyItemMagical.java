package com.minelittlepony.client.render.entity.feature;

import com.minelittlepony.client.PonyRenderManager;
import com.minelittlepony.client.model.IPonyModel;
import com.minelittlepony.client.render.IPonyRenderContext;
import com.minelittlepony.model.IUnicorn;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;

public class LayerHeldPonyItemMagical<T extends LivingEntity, M extends EntityModel<T> & IPonyModel<T>> extends LayerHeldPonyItem<T, M> {

    public LayerHeldPonyItemMagical(IPonyRenderContext<T, M> context) {
        super(context);
    }

    protected boolean isUnicorn() {
        return getModel() instanceof IUnicorn<?> && ((IUnicorn<?>)getModel()).canCast();
    }

    @Override
    protected void preItemRender(T entity, ItemStack drop, ModelTransformation.Type transform, Arm hand, MatrixStack stack) {
        if (isUnicorn()) {
            stack.translate(hand == Arm.LEFT ? -0.6F : 0, 0.5F, -0.3F);
        } else {
            super.preItemRender(entity, drop, transform, hand, stack);
        }
    }

    @Override
    protected void postItemRender(T entity, ItemStack drop, ModelTransformation.Type transform, Arm hand, MatrixStack stack, VertexConsumerProvider renderContext) {
        if (isUnicorn()) {
            PonyRenderManager.getInstance().getMagicRenderer().renderItemGlow(entity, drop, transform, hand, ((IUnicorn<?>)getModel()).getMagicColor(), stack, renderContext);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderArm(Arm arm, MatrixStack stack) {
        if (isUnicorn()) {
            ((IUnicorn<ModelPart>)getModel()).getUnicornArmForSide(arm).rotate(stack);
        } else {
            super.renderArm(arm, stack);
        }
    }
}
