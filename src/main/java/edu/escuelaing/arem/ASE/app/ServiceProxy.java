package edu.escuelaing.arem.ASE.app;

import java.io.IOException;

import static spark.Spark.port;
import static  spark.Spark.get;

public class ServiceProxy {

    private static final String[] serversPrimes={"http://ec2-44-211-75-161.compute-1.amazonaws.com:4567/primes?value=", "http://ec2-18-234-125-230.compute-1.amazonaws.com:4567/primes?value="};

    private static final String[] serversFactors={"http://ec2-44-211-75-161.compute-1.amazonaws.com:4567/factors?value=", "http://ec2-18-234-125-230.compute-1.amazonaws.com:4567/factors?value="};

   private static int urlInstancePrimes=0;

    private static int urlInstanceFactors=0;



    public static void main(String... args){
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
        get("index", (req, res)-> getIndex());
        get("primes", (req, res) ->{
            res.type("application/json");
            return getResponsePrimes(req.queryParams("value"));
        });
        get("factors", (req, res) ->{
            res.type("application/json");
            return getResponseFactors(req.queryParams("value"));
        });
    }

    public static String getResponsePrimes(String value) throws IOException {
        return  HttpConnectionExample.getResponse(roundRobinPrimes()+value);
    }

    public static String getResponseFactors(String value) throws IOException {
        return HttpConnectionExample.getResponse(roundRobinFactors()+value);
    }

    public static String roundRobinPrimes(){
        urlInstancePrimes=(urlInstancePrimes+1)%serversPrimes.length;
        return serversPrimes[urlInstancePrimes];
    }

    public static String roundRobinFactors(){
        urlInstanceFactors=(urlInstanceFactors+1)%serversFactors.length;
        return serversFactors[urlInstanceFactors];
    }


    public static String getIndex(){
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form for Primes</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">value:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"100\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/primes?value=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "        <h1>Form for Factors</h1>\n" +
                "        <form action=\"/hellod\">\n" +
                "            <label for=\"nameu\">Value:</label><br>\n" +
                "            <input type=\"text\" id=\"nameu\" name=\"name\" value=\"13\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsge()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsge\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsge() {\n" +
                "                let nameVar = document.getElementById(\"nameu\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsge\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/factors?value=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }
}
