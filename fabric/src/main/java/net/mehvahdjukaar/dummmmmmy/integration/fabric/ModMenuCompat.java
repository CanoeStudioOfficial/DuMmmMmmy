package net.mehvahdjukaar.dummmmmmy.integration.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.mehvahdjukaar.dummmmmmy.configs.ClientConfigs;
import net.mehvahdjukaar.dummmmmmy.configs.CommonConfigs;
import net.mehvahdjukaar.moonlight.api.client.gui.MediaButton;
import net.mehvahdjukaar.moonlight.api.platform.configs.fabric.FabricConfigListScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigSelectScreen::new;
    }

    public static class ModConfigSelectScreen extends FabricConfigListScreen {

        public ModConfigSelectScreen(Screen parent) {
            super(Dummmmmmy.MOD_ID, Dummmmmmy.DUMMY_ITEM.get().getDefaultInstance(),
                    Component.literal("§6MmmMmmMmmMmm Configs"), ResourceLocation.tryParse("textures/block/hay_block_side.png"),
                    parent, ClientConfigs.SPEC, CommonConfigs.SPEC);
        }

        @Override
        protected void addExtraButtons() {

            int y = this.height - 27;
            int centerX = this.width / 2;

            this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> this.minecraft.setScreen(this.parent))
                    .bounds(centerX - 45, y, 90, 20).build());

            this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> this.minecraft.setScreen(this.parent))
                    .bounds(centerX - 45, y, 90, 20).build());

            this.addRenderableWidget(MediaButton.patreon(this, centerX - 45 - 22, y,
                    "https://www.patreon.com/user?u=53696377"));

            this.addRenderableWidget(MediaButton.koFi(this, centerX - 45 - 22 * 2, y,
                    "https://ko-fi.com/mehvahdjukaar"));

            this.addRenderableWidget(MediaButton.curseForge(this, centerX - 45 - 22 * 3, y,
                    "https://www.curseforge.com/minecraft/mc-mods/amendments"));

            this.addRenderableWidget(MediaButton.github(this, centerX - 45 - 22 * 4, y,
                    "https://github.com/MehVahdJukaar/Supplementaries/wiki/amendments"));


            this.addRenderableWidget(MediaButton.discord(this, centerX + 45 + 2, y,
                    "https://discord.com/invite/qdKRTDf8Cv"));

            this.addRenderableWidget(MediaButton.youtube(this, centerX + 45 + 2 + 22, y,
                    "https://www.youtube.com/watch?v=LSPNAtAEn28&t=1s"));

            this.addRenderableWidget(MediaButton.twitter(this, centerX + 45 + 2 + 22 * 2, y,
                    "https://twitter.com/Supplementariez?s=09"));

            this.addRenderableWidget(MediaButton.akliz(this, centerX + 45 + 2 + 22 * 3, y,
                    "https://www.akliz.net/supplementaries"));

        }

    }
}