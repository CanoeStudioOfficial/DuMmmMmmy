package testdummy2.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import testdummy2.network.DamageMessage;
import testdummy2.network.SyncEquipmentMessage;

public class CommonProxy {
    public SimpleNetworkWrapper network;

    public void preinit() {
    }

    public void init() {
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel("TestDummy2");

        this.network.registerMessage(DamageMessage.MessageHandlerClient.class, DamageMessage.class, 0, Side.CLIENT);
        this.network.registerMessage(SyncEquipmentMessage.MessageHandlerClient.class, SyncEquipmentMessage.class, 1, Side.CLIENT);
    }
}
