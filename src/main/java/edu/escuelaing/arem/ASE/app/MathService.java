package edu.escuelaing.arem.ASE.app;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.port;
import static  spark.Spark.get;

public class MathService {
    public static void main(String... args){
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
        get("primes", (req, res) ->{
            res.type("application/json");
            return responsePrimos(Integer.parseInt(req.queryParams("value")));
        });
        get("factors", (req, res) ->{
            res.type("application/json");
            return responseFactors(Integer.parseInt(req.queryParams("value")));
        });
    }

    public static String responsePrimos(Integer value){
        List<Integer> primes= primes(value);
        return "{ \n \"operation\" : \"Primes\" ,\n \"input\" : \""+value+"\" , \n \"output\" : \""+ formatList(primes)+"\" \n}";
    }

    public static String responseFactors(Integer value){
        List<Integer> factors= factors(value);
        return "{ \n \"operation\" : \"Factors\" ,\n \"input\" : \""+value+"\" , \n \"output\" : \""+ formatList(factors)+"\" \n}";
    }


    public static String formatList(List<Integer> listNumbers){
        String response="";
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0; i<listNumbers.size(); i++){
            stringBuilder.append(listNumbers.get(i));
            response+=String.valueOf(listNumbers.get(i));
            if(i<listNumbers.size()-1){
                stringBuilder.append(", ");
                response+=",";
            }
        }
        return response;
    }

    public static List<Integer>  factors(Integer value){
        List<Integer> factorsn=new ArrayList<>();
        for (int i=1; i<=value/2; i++){
            if(value%i==0){
                factorsn.add(i);
            }
        }
        factorsn.add(value);
        return factorsn;
    }
    public static List<Integer> primes(Integer value){
        List<Integer> numberPrimes= new ArrayList<>();
        for ( int i=2; i<=value;i++){
            if(divisores(i)==2){
                numberPrimes.add(i);
            }
        }
        return numberPrimes;
    }

    public static int divisores(Integer number){
        int divisores=0;
        for (int i=1; i<=number; i++){
            if(number%i==0){
                divisores+=1;
            }
        }
        return divisores;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
