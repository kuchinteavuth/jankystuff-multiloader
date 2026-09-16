package com.inteavuthkuch.jankystuff;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class Constants {

	public static final String MOD_ID = "jankystuff";
	public static final String MOD_NAME = "JankyStuff";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static Identifier modId(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}

	public static Identifier minecraftId(String name) {
		return Identifier.withDefaultNamespace(name);
	}
}