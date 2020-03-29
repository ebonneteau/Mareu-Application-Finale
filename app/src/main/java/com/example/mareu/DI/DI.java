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
     * @return
     */
    public static ReuApiService getReuApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link ReuApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static ReuApiService getNewInstanceApiService() {
        return new AllActionsReuApiService();
    }


}
