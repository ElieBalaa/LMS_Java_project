/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lmsServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Cpt.EB
 */
@Path("lmsserv")
public class MainServices210351 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MainServices210351
     */
    public MainServices210351() {
    }

    /**
     * Retrieves representation of an instance of lmsServices.MainServices210351
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MainServices210351
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
    private JsonArray readJsonFile(String filename){
        File fileToRead = new File(filename);
        JsonReader jr = null;
        JsonArray ja = null;
        
        try{
            jr = Json.createReader(new FileReader(fileToRead));
            ja = jr.readArray();
            jr.close();
        }
        catch(Exception ex){
            
        }
        return ja;
    }
    
    @GET
    @Path("/viewStudentGroups")
    @Produces(MediaType.TEXT_HTML)
    public String viewStudentGroups(@Context HttpServletRequest request){
        String username = request.getRemoteUser();
        String retVal ="";
        
        JsonArray studentsArray = null, groupsArray=null, enrolmentsArray=null;
        studentsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\students.json");
        groupsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\groups.json");
        enrolmentsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\enrolments.json");
        
        //retrieve the student name
        for(int i=0;i<studentsArray.size();i++){
            JsonObject studentsEntry = studentsArray.getJsonObject(i);
            if(username.equals(studentsEntry.getString("username"))){
                retVal+="<div style =\"width: 400px;margin: auto;padding: 20px;border-style: solid;border-color: #B6C7D6;border-radius: 20px;background-color: #EAF0F6;>Welcome\" "+ studentsEntry.getString("studentname")+"<br>";
            }
        }
        // Retrieve the student Grous
        retVal+="<b>Your Groups: </b><br>";
        for(int i=0;i<enrolmentsArray.size();i++){
            JsonObject enrolmentEntry = enrolmentsArray.getJsonObject(i);
            if (username.equals(enrolmentEntry.getString("username"))){
                int groupid = enrolmentEntry.getInt("groupid");
                for (int j = 0 ; j<groupsArray.size();j++){
                    JsonObject groupEntry = groupsArray.getJsonObject(j);
                    if(groupid == groupEntry.getInt("groupid")){
                        retVal+=groupEntry.getString("groupname")+ "<br>";
                    }
                }
            }
            
        }
        return retVal+"</div>" ;
    }
    
    @GET
    @Path("/viewAllGroups")
    @Produces(MediaType.TEXT_HTML)
    public String viewAllGroups(){
        String retVal = "";
        
        JsonArray groupsArray = null;
        groupsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\groups.json");
        
        for(int i=0;i<groupsArray.size();i++){
           JsonObject groupEntry = groupsArray.getJsonObject(i);
           retVal+= groupEntry.getInt("groupid")+", " + groupEntry.getString("groupname") + "\n";
           
           //BufferedReader 
        }
        
        return retVal;
    }
    
    @GET
    @Path("/viewGroupMembers/{groupid}")
    @Produces(MediaType.TEXT_HTML)
    public String viewGroupMembers(@PathParam("groupid") String groupID){
        String retVal = "";
        JsonArray studentsArray = null, groupsArray=null, enrolmentsArray=null;
        studentsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\students.json");
        enrolmentsArray = readJsonFile("E:\\AOU 2020-2021\\TM352\\TMA\\enrolments.json");
        
        for(int i=0;i<enrolmentsArray.size();i++){
            JsonObject enrolmentEntry = enrolmentsArray.getJsonObject(i);            
                int gid = enrolmentEntry.getInt("groupid");
                int tempId = Integer.parseInt(groupID);
                if(gid == tempId){
                    String username = enrolmentEntry.getString("username");
                
                for (int j = 0 ; j<studentsArray.size();j++){
                    JsonObject studentEntry = studentsArray.getJsonObject(j);
                    if(username .equals(studentEntry.getString("username"))){
                        retVal+=studentEntry.getString("studentname")+ "\n";
                    }
                }
                }
            
        }
        
        return retVal;
    }
    
}
