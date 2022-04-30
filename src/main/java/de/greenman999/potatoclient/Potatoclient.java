package de.greenman999.potatoclient;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class Potatoclient implements ModInitializer {

    private final Logger LOGGER = Logger.getLogger("potatoclient");

    @Override
    public void onInitialize() {

        LOGGER.info("Initialized PotatoClient!");
    }
}
