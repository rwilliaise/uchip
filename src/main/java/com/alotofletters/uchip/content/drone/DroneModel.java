// Generated using Blockbench
package com.alotofletters.uchip.content.drone;

import com.alotofletters.uchip.Microchip;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.phys.Vec3;

public class DroneModel extends EntityModel<DroneEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Microchip.location("drone"), "main");
    private final ModelPart northeast;
    private final ModelPart northwest;
    private final ModelPart southeast;
    private final ModelPart southwest;
    private final ModelPart main;

    public DroneModel(ModelPart root) {
        this.northeast = root.getChild("northeast");
        this.northwest = root.getChild("northwest");
        this.southeast = root.getChild("southeast");
        this.southwest = root.getChild("southwest");
        this.main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition northeast = partdefinition.addOrReplaceChild("northeast", CubeListBuilder.create(), PartPose.offset(-6.0F, 22.0F, -6.0F));
        PartDefinition cube_r1 = northeast.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, -0.7854F, 3.1416F));
        PartDefinition cube_r2 = northeast.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r3 = northeast.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.7854F, 0.0F));
        PartDefinition northwest = partdefinition.addOrReplaceChild("northwest", CubeListBuilder.create(), PartPose.offset(6.0F, 22.0F, -6.0F));
        PartDefinition cube_r4 = northwest.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, -0.7854F, 0.0F));
        PartDefinition cube_r5 = northwest.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 1).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.7854F, 3.1416F));
        PartDefinition cube_r6 = northwest.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition southeast = partdefinition.addOrReplaceChild("southeast", CubeListBuilder.create(), PartPose.offset(-6.0F, 22.0F, 6.0F));
        PartDefinition cube_r7 = southeast.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, -0.7854F, 0.0F));
        PartDefinition cube_r8 = southeast.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r9 = southeast.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 1).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.7854F, 3.1416F));
        PartDefinition southwest = partdefinition.addOrReplaceChild("southwest", CubeListBuilder.create(), PartPose.offset(6.0F, 22.0F, 6.0F));
        PartDefinition cube_r10 = southwest.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.7854F, 0.0F));
        PartDefinition cube_r11 = southwest.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 1).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, -0.7854F, 3.1416F));
        PartDefinition cube_r12 = southwest.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r13 = bb_main.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, -9.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r14 = bb_main.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, -9.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        northeast.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        northwest.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        southeast.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        southwest.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(DroneEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                          float pNetHeadYaw, float pHeadPitch) {
        float rot = (float) (pAgeInTicks * Math.max(pEntity.getDeltaMovement().y + 1, 0));
        northeast.yRot = rot;
        northwest.yRot = rot;
        southeast.yRot = rot;
        southwest.yRot = rot;
    }
}
