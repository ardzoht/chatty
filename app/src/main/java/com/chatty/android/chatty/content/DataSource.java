package com.chatty.android.chatty.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 25/04/2016.
 */
public class DataSource {

    public static List<Channel> Channels = new ArrayList<Channel>();

    static {
        Channels.add(new Channel("Deportes", "All about the sports right"));
        Channels.add(new Channel("Programacion", "Ask your questions here!"));
        Channels.add(new Channel("Mexico", "Arriba México!"));
        Channels.add(new Channel("USA", "Make America Great Again"));
        Channels.add(new Channel("Microcontroladores", "Ask your questions here!"));
        Channels.add(new Channel("Seguridad Informatica", "Just for fun"));
        Channels.add(new Channel("Python", "Pythonic Way"));
        Channels.add(new Channel("iOS", "Apple Way"));
        Channels.add(new Channel("Android", "What up"));
        Channels.add(new Channel("TV", "Game Of Thrones..."));
        Channels.add(new Channel("Cine", "Civil War"));
        Channels.add(new Channel("Entretenimiento", "Just for fun"));

    }
}
