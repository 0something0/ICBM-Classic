package icbm.core;

import net.minecraftforge.common.ForgeChunkManager.Ticket;

public interface IChunkLoadHandler {
	public void chunkLoaderInit(Ticket ticket);
}
