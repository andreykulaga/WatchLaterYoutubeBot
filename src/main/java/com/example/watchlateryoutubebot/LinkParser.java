package com.example.watchlateryoutubebot;

import com.example.watchlateryoutubebot.exceptions.UnsupportedServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@AllArgsConstructor
public class LinkParser {

    String whatServiceIsIt(String link) throws UnsupportedServiceException, URISyntaxException{
        URI uri = new URI(link);
        if (uri.getHost().equalsIgnoreCase("www.youtube.com") ||
                uri.getHost().equalsIgnoreCase("youtu.be")) {
            return "YouTube";
        } else {
            throw new UnsupportedServiceException();
        }
    }

    String getVideoIdFromLink(String link) {
        try {
            URI uri = new URI(link);
            String host = uri.getHost();
            if (uri.getHost().equalsIgnoreCase("youtu.be")) {
                //take a path of the shortened link and get rid of "/"
                return uri.getPath().substring(1);
            } else {
                //take all query elements and search for "v" element, return it's value
                String query = uri.getQuery();
                //todo использовать бибилотерку, возможно Spring для обработки параметров
                if (query != null) {
                    String[] strings = query.split("&");
                    for (String st: strings) {
                        String[] splittedSt = st.split("=");
                        if (splittedSt[0].equalsIgnoreCase("v")) {
                            return splittedSt[1];
                        }
                    }
                }
            }
        } catch (URISyntaxException e) {
            return null;
        }
        //if couldn't find any "v" parameter in the query
       return null;
    }

}
