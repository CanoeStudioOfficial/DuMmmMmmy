package testdummy2.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testdummy2.TestDummy2;
import testdummy2.client.RenderDummy;
import testdummy2.client.RenderFloatingNumber;
import testdummy2.entity.EntityDummy;
import testdummy2.entity.EntityFloatingNumber;

public class ClientProxy
        extends CommonProxy {
    public void preinit() {
        MinecraftForge.EVENT_BUS.register(this);
        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFloatingNumber.class, RenderFloatingNumber::new);
    }

    @SubscribeEvent
    public void models(ModelRegistryEvent e) {
        String loc = "testdummy2:dummy";
        ModelLoader.setCustomModelResourceLocation(TestDummy2.itemDummy, 0, new ModelResourceLocation(loc, "inventory"));
    }
}