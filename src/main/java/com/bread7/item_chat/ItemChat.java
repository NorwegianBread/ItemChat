package com.bread7.item_chat;

import com.bread7.item_chat.utils.Config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.nio.file.Path;




public class ItemChat implements ModInitializer {
   public static final Logger LOGGER = LogManager.getLogger();

	public static final Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve("item_chat.json");

	@Override
	public void onInitialize() {
		Config.loadConfig();
		LOGGER.info("Initialized ItemChat");

	}

}