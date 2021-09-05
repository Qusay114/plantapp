package com.teamwater.plantApp.controllers;


import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.teamwater.plantApp.models.garden.Garden;
import com.teamwater.plantApp.models.garden.GardenRepository;
import com.teamwater.plantApp.models.plant.Plant;
import com.teamwater.plantApp.models.plant.PlantRepository;
import com.teamwater.plantApp.models.user.AppUserRepository;
import com.teamwater.plantApp.services.garden.GardenService;
import com.teamwater.plantApp.services.plant.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PlantController {

   @Autowired
    PlantService plantService ;

    @Autowired
    GardenService gardenService;

//  ======== routes =======
    @PostMapping("/addPlantToGarden")
    @ResponseStatus(value= HttpStatus.OK)
    public void addPlantToGarden(@RequestParam(value="common_name")String common_name,
                                 @RequestParam(value="image_url")String image_url,
                                 @RequestParam(value="plantIdFromApi")Long plantIdFromApi,
                                 @RequestParam(value="gardenId")Long gardenId
                                 ){

        System.out.println("plantIdFromApi " + plantIdFromApi);
        System.out.println("gardenId " + gardenId);
//        System.out.println("gardenId's id " + gardenId.getClass());
//        System.out.println("plantIdFromApi's class " + plantIdFromApi.getClass());
        System.out.println(" the common name is: ====================" + common_name);
        System.out.println(" the image url is: =====================" + image_url);

        Garden garden = gardenService.getGarden(gardenId);
        Plant plant = plantService.getPlantFromApi(plantIdFromApi);
        if(plant == null){
            plant = new Plant(common_name, image_url, plantIdFromApi);
            plantService.savePlant(plant);
        }
        garden.addPlant(plant);
        plant.listOfGardens.add(garden);

        gardenService.saveGarden(garden);
        plantService.savePlant(plant);

        System.out.println(common_name);
        System.out.println(image_url);
        System.out.println("------ added PLANT TO DB ------");
    }
}
