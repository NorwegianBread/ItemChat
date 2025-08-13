package com.bread7.item_chat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.bread7.item_chat.ItemChat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static String pattern = "[i]";

    public static String getPattern() {
        return pattern;
    }

    public static void loadConfig() {
        File file = ItemChat.configFilePath.toFile();
        if (!file.exists()) {
            saveConfig();
            return;
        }
        try {
            String json = new String(Files.readAllBytes(ItemChat.configFilePath)).replace("\n", "");
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            pattern = jsonObject.get("pattern").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("pattern", pattern);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(jsonObject);
            Files.write(ItemChat.configFilePath, json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}