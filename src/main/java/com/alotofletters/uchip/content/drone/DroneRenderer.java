package com.alotofletters.uchip.content.drone;

import com.alotofletters.uchip.Microchip;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DroneRenderer extends MobRenderer<Drone, DroneModel> {
    public DroneRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DroneModel(pContext.bakeLayer(DroneModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity pEntity) {
        return Microchip.location("textures/entity/drone.png");
    }
}
