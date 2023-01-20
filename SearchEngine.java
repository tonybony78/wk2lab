import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> items = new ArrayList<>();
    public final String intro = "Tony's WordFinder";
    public final String itemFind = "Here are the keywords that contain your search term: ";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return intro;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    items.add(parameters[1]);
                    return String.format("You have added %s", parameters[1]);
                }
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> strCheck = new ArrayList<>();
                    for(int i = 0; i < items.size(); i++){
                        if(items.get(i).contains(parameters[1])){
                            strCheck.add(items.get(i));
                        }
                    }
                    return itemFind + strCheck;
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

