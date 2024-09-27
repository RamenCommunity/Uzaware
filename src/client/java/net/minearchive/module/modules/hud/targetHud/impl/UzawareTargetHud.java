package net.minearchive.module.modules.hud.targetHud.impl;

import net.minearchive.module.modules.hud.targetHud.TargetHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

public class UzawareTargetHud extends TargetHud {

    @Override
    public void render(LivingEntity target) {

    }

    @Override
    public Pair<Integer, Integer> getWH() {
        return new Pair<>(0, 0);
    }
}
