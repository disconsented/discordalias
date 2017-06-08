package com.disconsented;

import java.io.*;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Config {
    private String email;
    private String password;
    private String userID;
    private ArrayList<AbstractMap.SimpleEntry<String, String>> alises =
            new ArrayList<AbstractMap.SimpleEntry<String, String>>(){{this.add(new AbstractMap.SimpleEntry<String, String>("a", "b"));}};
    private transient static File fileLocation = new File("config.json");

    private Config(){

    }

    public static Config loadConfig(){
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<Config>(){}.getType();
            Reader inputReader = new InputStreamReader(new FileInputStream(fileLocation));
            Config config = gson.fromJson(inputReader, type);
            inputReader.close();
            return config;
        } catch (FileNotFoundException e){

            Config config = new Config();
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            try {
//                fileLocation.createNewFile();
                Writer writer = new OutputStreamWriter(new FileOutputStream(fileLocation));
                gson.toJson(config, writer);
                writer.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }
         return null;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<AbstractMap.SimpleEntry<String, String>> getAlises() {
        return alises;
    }


    public String getUserID() {
        return userID;
    }
}
