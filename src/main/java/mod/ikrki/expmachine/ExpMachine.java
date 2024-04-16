package mod.ikrki.expmachine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@Mod(ExpMachine.MOD_ID)
@EventBusSubscriber(bus=Bus.MOD)
public class ExpMachine
{
	public static final String MOD_ID = "expmachine";
	@ObjectHolder(MOD_ID + ":exp_machine")
	public static Block exp_machine;
	@ObjectHolder(MOD_ID + ":exp_machine")
	public static TileEntityType<ExpMachineTileEntity> exp_machine_te;


	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new ExpMachineBlock().setRegistryName("exp_machine"));
	}

	@SubscribeEvent
	public static void onRegisterTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().register(TileEntityType.Builder.create(ExpMachineTileEntity::new, exp_machine).build(null).setRegistryName(new ResourceLocation(exp_machine.getRegistryName().toString())));
	}

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ExpMachineItem(exp_machine).setRegistryName(exp_machine.getRegistryName()));
	}
}
