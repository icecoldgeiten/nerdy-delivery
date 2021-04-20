package com.route;
import io.github.cdimascio.dotenv.Dotenv;



public class Main {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();

        System.out.println(dotenv.get("MY_ENV_VAR1"));
        System.out.println(dotenv.get("MY_EVV_VAR2"));
        System.out.println(dotenv.get("TEST_ENV"));
        }
    }
