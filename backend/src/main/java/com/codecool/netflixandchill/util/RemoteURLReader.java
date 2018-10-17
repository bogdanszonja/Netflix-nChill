package com.codecool.netflixandchill.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RemoteURLReader {
    public String readFromUrl(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine = in.readLine();
        in.close();
        return inputLine;
    }
}
