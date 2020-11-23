/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static javax.xml.ws.Endpoint.create;
import model.Meal;

/**
 *
 * @author Isaiah
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
       @FXML
    private Button button;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private Button create;

    @FXML
    private Button read;

    @FXML
    private Button readWithNameAndCaloris;

    @FXML
    private Button readWithName;

    @FXML
    private Button readWithID;

    @FXML
    void Create(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter name:");
        String name = input.nextLine();
        System.out.println("Enter  nut ID:");
        int nutid = input.nextInt();
    
        
        System.out.println("Enter meal id:");
        int mealid = input.nextInt();
        String whiteSpace = input.nextLine();
        System.out.println("Descrition");
        String des = input.nextLine();
        
        System.out.println("Enter caloris:");
        double c = input.nextDouble();
        
        
        Meal meal = new Meal();
        
        
        meal.setName(name);
        meal.setMealid(mealid);
        meal.setNutid(nutid);
        meal.setDescription(des);
        meal.setCaloris(c);
        
                
        create(meal);


    }

    @FXML
    void Delete(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter meal ID:");
        int id = input.nextInt();
        
        Meal m = readbyid(id);
        System.out.println("we are deleting this student: "+ m.toString());
        delete(m);


    }

    @FXML
    void Read(ActionEvent event) {

    }
      public List<Meal> readAll(){
        Query query = manager.createNamedQuery("Meal.findAll");
    
         List<Meal> meals = query.getResultList();

        for (Meal m : meals) {
            System.out.println((m.getNutid() + " " + m.getMealid() +" "+m.getName() + " " + m.getDescription()));
        }
        
        return meals;
    }


    @FXML
    void Update(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
         System.out.println("Enter name");
        String name = input.nextLine();
        System.out.println("Enter nut ID:");
        int nutid = input.nextInt();
        
        System.out.println("Enter meal ID:");
        int mealid = input.nextInt();
        
       
        
        System.out.println("Enter Caloris:");
        double caloris = input.nextDouble();
        String whitSpace = input.nextLine();
        System.out.println("Enter description");
        String des = input.nextLine();
        
        // create a student instance
        Meal meal = new Meal();
        
        
        // set properties
        meal.setNutid(nutid);
        meal.setMealid(mealid);
        meal.setCaloris(caloris);
        meal.setDescription(des);
        meal.setName(name);
      
        
        
        // save this student to databse by calling Create operation        
        update(meal);

    }

    private void update(Meal meal) {
        
        try {

            Meal existingMeal = manager.find(Meal.class, meal.getMealid());

            if (existingMeal != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                existingMeal.setMealid(meal.getMealid());
                existingMeal.setNutid(meal.getNutid());
                existingMeal.setCaloris(meal.getCaloris());
                existingMeal.setDescription(meal.getDescription());
                existingMeal.setName(meal.getName());
                
                
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }


    @FXML
    void readWithID(ActionEvent event) {
       Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Meal m = readbyid(id);
        System.out.println(m.toString());

        

    }

    @FXML
    void readWithName(ActionEvent event) {
         Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();
        
        List<Meal> m = readByName(name);
        System.out.println(m.toString());

    }

    @FXML
    void readWithNandC(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter Caloris:");
        double c = input.nextDouble();
        
        // create a student instance      
        List<Meal> meals =  readByNAndC(name, c);


    }

    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
                
        Query query = manager.createNamedQuery("Meal.findAll");
        List<Meal> data = query.getResultList();
        
        for (Meal m : data) {            
            System.out.println(m.getMealid()+" "+ m.getNutid()+" "+m.getName()+ " " + m.getDescription()+ m.getCaloris()); 
        }  
        
    }
      EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = (EntityManager) Persistence.createEntityManagerFactory("IsaiahClaiborneFXMLPU").createEntityManager();

    }    

    private void delete(Meal m) {
         try {
            Meal existingmeal = manager.find(Meal.class, m.getMealid());

            // sanity check
            if (existingmeal != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingmeal);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
 
       
    }

    private Meal readbyid(int id) {
        Query query = manager.createNamedQuery("Meal.findByMealId");
        
        // setting query parameter
        query.setParameter("id", id);
        
        // execute query

        Meal meal = (Meal) query.getSingleResult();
        
        if (meal != null) {
            System.out.println(meal.getMealid() + " " + meal.getNutid() + " " + meal.getCaloris()+ " "+ meal.getDescription()+ " "+ meal.getName());
        }
        
        return meal;
    }        

    private List<Meal> readByName(String name) {
        Query query = manager.createNamedQuery("Meal.findByName");
        
        // setting query parameter
        query.setParameter("name", name);
        
        // execute query
        List<Meal> meals =  query.getResultList();

        for (Meal meal: meals) {
            System.out.println(meal.getMealid()+" " +meal.getNutid()+" " +meal.getName() + " " + meal.getCaloris()+ " "+ meal.getDescription());
        }
        
        return meals;
    }        
        
    

    private List<Meal> readByNAndC(String name, double c) {
        Query query = manager.createNamedQuery("Student.findByNameAndCgpa");
        
        // setting query parameter
        query.setParameter("caloris", c);
        query.setParameter("name", name);
        
        
        // execute query
        List<Meal> meals =  query.getResultList();
        for (Meal meal: meals) {
            System.out.println(meal.getMealid() + " " + meal.getNutid()+ " "+ meal.getName() + " " + meal.getCaloris()+ meal.getDescription());
        }
        
        
        
        return meals;
    }        
    }

 
    
