package com.example.mareu.DI;


import com.example.mareu.service.AllActionsReuApiService;
import com.example.mareu.service.ReuApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static ReuApiService service = new AllActionsReuApiService();


    /**
     * Get an instance on @{@link ReuApiService}
     *
     * @return
     */
    public static ReuApiService getReuApiService() {
        return service;
    }


}
