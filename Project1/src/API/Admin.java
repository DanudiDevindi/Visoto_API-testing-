package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Admin {
    public static void main(String[] args) {

        //Get accesstoken
        String accessTokenResponse = given().urlEncodingEnabled(true)
                .auth().basic("ADMIN", "")
                .header("Authorization", "Basic QURNSU46").header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "123")
                .formParam("grant_type", "password")
                .when().log().all()
                .post("http://198.211.109.205:8082/v1/authorize").asString();
        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");


        RestAssured.baseURI = "http://198.211.109.205:8082";

        // Add a receptionist
//Please add valid data before  you run the program
        String username="Admin@123";
        String firstname="Chamidi";
        String lastname="Lakshani";
        String nic="996100788v";
        String email="chamidiwijesuriya12345@gmail.com";
        String mobile= "0713456734";
        String password="12345";
       // String createdate=null;
        String role="RECEP";
       String status="ACTIVE";
        String addreceptionist = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"userId\":0,\n" +
                        "    \"userName\":\""+username+"\",\n" +
                        "    \"firstName\":\""+firstname+"\",\n" +
                        "    \"lastName\":\""+lastname+"\",\n" +
                        "    \"nic\":\""+nic+"\",\n" +
                        "    \"email\":\""+email+"\",\n" +
                        "    \"mobile\":\"+"+mobile+"\",\n" +
                        "    \"password\":\""+password+"\",\n" +
                        "    \"createdDate\": null,\n" +
                        "    \"role\":\""+role+"\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().post("/v1/receptionist/add")
                .then().assertThat().statusCode(200).body("success",equalTo(true))
                .body("msg",equalTo("Receptionist account created successfully"))
                .body("body",equalTo(null))
                .extract().response().asString();
        System.out.println(addreceptionist);

     // Already input details

        String already_inputreceptionist = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"userId\":0,\n" +
                        "    \"userName\":\"U24\",\n" +
                        "    \"firstName\":\"Kavindu\",\n" +
                        "    \"lastName\":\"Akash\",\n" +
                        "    \"nic\":\"1234549300\",\n" +
                        "    \"email\":\"bkkasamarasing00@gmail.com\",\n" +
                        "    \"mobile\":\"+94767771895\",\n" +
                        "    \"password\":\"123\",\n" +
                        "    \"createdDate\": null,\n" +
                        "    \"role\":\"RECEP\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().post("/v1/receptionist/add")
                .then().assertThat().statusCode(417).body("success",equalTo(false))
                .body("msg",equalTo("This employee already have an account"))
                .extract().response().asString();
        System.out.println(already_inputreceptionist);


    //Get receptionist
    String getreceptionist = given().log().all().queryParam("word","")
            .header("Content-Type", "application/json")
            .auth().oauth2(accessToken)
            .body("").when().get("/v1/receptionist/all")
            .then().assertThat().statusCode(200)
            .body("success",equalTo(true))
            .extract().response().asString();

        JsonPath js1 = new JsonPath(getreceptionist);
        System.out.println(getreceptionist);

        List<Object> body = js1.getList("body");
        int size = body.size();
        int lastrecep = js1.getInt("body["+(size-1)+"].userId");

        Object recep = body.get(size - 1);
        System.out.println(recep);
        System.out.println(lastrecep);
        String actualusername= js1.getString("body["+(size-1)+"].userName");
        String actualfirstname= js1.getString("body["+(size-1)+"].firstName");
        String actuallastname= js1.getString("body["+(size-1)+"].lastName");
        String actualnic= js1.getString("body["+(size-1)+"].nic");
        String actualemail= js1.getString("body["+(size-1)+"].email");
        String actualmobile= js1.getString("body["+(size-1)+"].mobile");
       // String actualpassword= js1.getString("body["+(size-1)+"].password");
       // String actualcreatedate= js1.getString("body["+(size-1)+"].createdDate");
        String actualuserstatus= js1.getString("body["+(size-1)+"].status");
        Assert.assertEquals(actualusername,username);
        Assert.assertEquals(actualfirstname,firstname);
        Assert.assertEquals(actuallastname,lastname);
        Assert.assertEquals(actualnic,nic);
        Assert.assertEquals(actualemail,email);
       // Assert.assertEquals(actualpassword,password);
        //Assert.assertEquals(actualcreatedate,createdate);
        Assert.assertEquals(actualmobile,mobile);
        Assert.assertEquals(actualuserstatus,status);
        System.out.println(actualusername);
        System.out.println(actualuserstatus);
        System.out.println(actualfirstname);
        System.out.println(actuallastname);
        System.out.println(actualnic);
        System.out.println(actualmobile);
       // System.out.println(actualpassword);
        System.out.println(lastrecep);

     //Update Receptionist
        //Please add valid data before when you run the program
        String updateusername="NEWrecep";
        String updatenic="23456718";
        String update_email="lak123@gmail.com";
        String updatemobile="0712345678";
        String updatepassword="1234567";
        String updatefirstname="Chamidi";
        String updatelastname="wijesuriya";
        String updatedate=null;
        String updatestatus="ACTIVE";

        String updatereceptionist = given().log().all()
               .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"userId\":"+lastrecep+",\n" +
                        "    \"userName\":\""+updateusername+"\",\n" +
                        "    \"firstName\":\""+updatefirstname+"\",\n" +
                        "    \"lastName\":\""+updatelastname+"\",\n" +
                        "    \"nic\":\""+updatenic+"\",\n" +
                        "    \"email\":\""+update_email+"\",\n" +
                        "    \"mobile\":\""+updatemobile+"\",\n" +
                        "    \"password\":\""+updatepassword+"\",\n" +
                        "    \"createdDate\": "+updatedate+",\n" +
                        "    \"role\":\"RECEP\",\n" +
                        "    \"status\":\""+updatestatus+"\"\n" +
                        "}").when().put("/v1/receptionist/update")
                .then().assertThat().statusCode(200).body("success",equalTo(true))
                .body("msg",equalTo("Receptionist account updated successfully"))
                .extract().response().asString();
        System.out.println(updatereceptionist);

//Already input details
        String update_receptionist = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"userId\":1,\n" +
                        "    \"userName\":\"chamidi1235\",\n" +
                        "    \"firstName\":\"Kavindu\",\n" +
                        "    \"lastName\":\"Akash\",\n" +
                        "    \"nic\":\"996100758\",\n" +
                        "    \"email\":\"kavindu3@ceyentra.com\",\n" +
                        "    \"mobile\":\"+94767221832\",\n" +
                        "    \"password\":\"123\",\n" +
                        "    \"createdDate\": null,\n" +
                        "    \"role\":\"RECEP\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().put("/v1/receptionist/update")
                .then().assertThat().statusCode(417).body("success",equalTo(false))
                .body("msg",equalTo("This username already exist"))
                .extract().response().asString();
        System.out.println(update_receptionist);

//get update
        String get_updatereceptionist = given().log().all().queryParam("word","")
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/receptionist/all")
                .then().assertThat().statusCode(200).body("success",equalTo(true))
                .body("msg",equalTo("Receptionist account records found successfully"))
                .extract().response().asString();

        JsonPath js2 = new JsonPath(get_updatereceptionist);
        System.out.println(get_updatereceptionist);

        List<Object> updatebody = js2.getList("body");
        int size2 = updatebody.size();
        int lastrecep1 = js2.getInt("body["+(size2-1)+"].userId");

        Object updaterecep = updatebody.get(size2 - 1);
        System.out.println(updaterecep);
        System.out.println(lastrecep);
        String actualupdateusername= js2.getString("body["+(size2-1)+"].userName");
        String actualupdateuseremail= js2.getString("body["+(size2-1)+"].email");
        String actualupdatefirstname= js2.getString("body["+(size2-1)+"].firstName");
        String actualupdatelastname= js2.getString("body["+(size2-1)+"].lastName");
        String actualupdatemobile= js2.getString("body["+(size2-1)+"].mobile");
        String actualupdatenic= js2.getString("body["+(size2-1)+"].nic");
       // String actualupdatedate= js2.getString("body["+(size2-1)+"].createdDate");
        String actualupdateuserstatus= js2.getString("body["+(size2-1)+"].status");
       // String actualupdatepassword= js2.getString("body["+(size2-1)+"].password");
        Assert.assertEquals(actualupdateusername,updateusername);
        Assert.assertEquals(actualupdateuseremail,update_email);
        Assert.assertEquals(actualupdatefirstname,updatefirstname);
        Assert.assertEquals(actualupdatelastname,updatelastname);
        Assert.assertEquals(actualupdatemobile,updatemobile);
        Assert.assertEquals(actualupdatenic,updatenic);
       // Assert.assertEquals(actualupdatepassword,updatepassword);
       // Assert.assertEquals(actualcreatedate,updatedate);
        Assert.assertEquals(actualupdateuserstatus,updatestatus);
        System.out.println(actualupdateuseremail);
        System.out.println(actualupdateusername);

        System.out.println(lastrecep1);



//Add building
        String newbuildingname="Hall2qe";
        String newstatus="ACTIVE";
        String addbuilding = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"buildingId\":0,\n" +
                        "    \"name\":\""+newbuildingname+"\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().post("/v1/building/add")
                .then().assertThat().statusCode(200).body("msg",equalTo("Building saved successfully"))
                .body("success",equalTo(true))
                .body("body",equalTo(null))
                .extract().response().asString();
        System.out.println(addbuilding);

// ADD already exit building
        String add_building = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"buildingId\":0,\n" +
                        "    \"name\":\""+newbuildingname+"\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().post("/v1/building/add")
                .then().assertThat().statusCode(417).body("msg",equalTo("Application Error Occurred"))
                .extract().response().asString();
        System.out.println(add_building);

//Get added building
        String get_added_building = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/building/all")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js4 = new JsonPath(get_added_building);
        System.out.println(get_added_building);
        List<Object> building_body = js4.getList("body");
        int size3 = building_body.size();
        int lastbuildingId = js4.getInt("body["+(size3-1)+"].buildingId");

        Object building = building_body.get(size3 - 1);
        System.out.println(building);
        System.out.println(lastbuildingId);
        String actualname= js4.getString("body["+(size3-1)+"].name");
        String actualstatus= js4.getString("body["+(size3-1)+"].status");
        Assert.assertEquals(actualname,newbuildingname);
        Assert.assertEquals(actualstatus,newstatus);
        System.out.println(actualname);
        System.out.println(actualstatus);


// Update building
        String updatename="PurpleHAll4";
        String update_status="ACTIVE";
        String updatebuilding = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"buildingId\":"+lastbuildingId+",\n" +
                        "    \"name\":\""+updatename+"\",\n" +
                        "    \"status\":\""+update_status+"\"\n" +
                        "}").when().put("/v1/building/update")
                .then().assertThat().statusCode(200).body("success",equalTo(true))
                .body("msg",equalTo("Building updated successfully"))
                .extract().response().asString();
        System.out.println(updatebuilding);

//Already exit building
        String alreadyexitbuilding = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"buildingId\":1,\n" +
                        "    \"name\":\"Milano Tower2\",\n" +
                        "    \"status\":\"ACTIVE\"\n" +
                        "}").when().put("/v1/building/update")
                .then().assertThat().statusCode(417).body("success",equalTo(false))
                .body("msg",equalTo("Application Error Occurred"))
                .extract().response().asString();
        System.out.println(alreadyexitbuilding);

        // Get update building
        String getupdatebuilding = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/building/all")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js5 = new JsonPath(getupdatebuilding);
        List<Object> updatebody1 = js5.getList("body");
        int size4 = updatebody1.size();
        int lastbuildingId1 = js5.getInt("body["+(size4-1)+"].buildingId");

        Object building1 = updatebody1.get(size4 - 1);
        System.out.println(building1);
        System.out.println(lastbuildingId1);
        String actualupdatename1= js5.getString("body["+(size4-1)+"].name");
        String actualupdatestatus1= js5.getString("body["+(size4-1)+"].status");
        Assert.assertEquals(actualupdatename1,updatename);
        Assert.assertEquals(actualupdatestatus1,update_status);
        System.out.println(actualupdatename1);
        System.out.println(actualupdatestatus1);

   //Delete building
        String deletebuilding = given().log().all()
                .queryParam("id",lastbuildingId)
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().delete("/v1/building/delete")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Building deleted successfully"))
                .extract().response().asString();
        System.out.println(deletebuilding);

    //Get delete building
//        String getdeletebuilding = given().log().all()
//                .header("Content-Type", "application/json")
//                .auth().oauth2(accessToken)
//                .body("").when().get("/v1/building/all")
//                .then().assertThat().statusCode(200)
//                .body("success",equalTo(true))
//                .extract().response().asString();
//        JsonPath js6 = new JsonPath(getdeletebuilding);
//        int actualdeletebuildingID=js6.getInt("body["+(size4-1)+"].buildingId");
//        String actualdeletename= js6.getString("body["+(size4-1)+"].name");
//        String actualdeletestatus=js6.getString("body["+(size4-1)+"].status");
//        Assert.assertEquals(actualdeletebuildingID,lastbuildingId);
//        Assert.assertEquals(actualdeletename,updatename);
//        Assert.assertEquals(actualdeletestatus,update_status);
//
        //Get active buildings

        String getactivebuildings = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/building/active")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Active building records found successfully"))
                .extract().response().asString();
        System.out.println(getactivebuildings);



////--------------------------------------------------------------------------------------------------------
//    //Add floor
        String floorname="testfloor688";
        String floorstatus="ACTIVE";
        int building_id=1;
        String addfloor = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"floorId\":0,\n" +
                        "    \"name\":\""+floorname+"\",\n" +
                        "    \"FloorStatus\":\""+floorstatus+"\",\n" +
                        "    \"buildingId\":"+building_id+"\n" +
                        "}").when().post("/v1/floor/add")
                .then().assertThat().statusCode(200).body("msg",equalTo("Floor saved successfully"))
                .body("success",equalTo(true))
                .extract().response().asString();
        System.out.println(addfloor);

    //Get floor
        String get_added_floor = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/floor/all")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js7 = new JsonPath(get_added_floor);
        System.out.println(get_added_floor);
        List<Object> floor_body = js7.getList("body");
        int size5 = floor_body.size();
        int lastfloorID = js7.getInt("body["+(size5-1)+"].floorId");

        Object floor = floor_body.get(size5 - 1);
        System.out.println(floor);
        System.out.println(lastfloorID);
        String actualfloorname= js7.getString("body["+(size5-1)+"].name");
        String actualfloorstatus= js7.getString("body["+(size5-1)+"].status");
        int actualbuilding_id=js7.getInt("body["+(size5-1)+"].building.buildingId");
        Assert.assertEquals(actualfloorname,floorname);
        Assert.assertEquals(actualfloorstatus,floorstatus);
        Assert.assertEquals(actualbuilding_id,building_id);
        System.out.println(actualfloorname);
        System.out.println(actualfloorstatus);

        //Update floor
        String updatefloorname="Darkfloor1968";
        String updatefloorstatus="ACTIVE";
        int updatebuildingId=1;
        String updatefloor = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"floorId\":"+lastfloorID+",\n" +
                        "    \"name\":\""+updatefloorname+"\",\n" +
                        "    \"status\":\""+updatefloorstatus+"\",\n" +
                        "    \"buildingId\":"+updatebuildingId+"\n" +
                        "}").when().put("/v1/floor/update")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println(updatefloor);
       // JsonPath js8= new JsonPath(updatefloor);

    //Get floor
        String getupdatefloor = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/floor/all")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js9 = new JsonPath(getupdatefloor);
        List<Object> updatefloorbody = js9.getList("body");
        int size6 = updatefloorbody.size();
        int lastfloorID1 = js9.getInt("body["+(size6-1)+"].floorId");

        Object floor1 = updatefloorbody.get(size6 - 1);
        System.out.println(floor1);
        System.out.println(lastfloorID1);
        String actualupdatefloorname= js9.getString("body["+(size6-1)+"].name");
        String actualupdatefloorstatus= js9.getString("body["+(size6-1)+"].status");
        int actualupdatebuilding_id=js9.getInt("body["+(size6-1)+"].building.buildingId");
        Assert.assertEquals(actualupdatefloorname,updatefloorname);
        Assert.assertEquals(actualupdatefloorstatus,updatefloorstatus);
        Assert.assertEquals(actualupdatebuilding_id,updatebuildingId);
        System.out.println(actualupdatefloorname);
        System.out.println(actualupdatefloorstatus);

       // Get active floors
        String getactivefloors = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/floor/active")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Active floor records found successfully"))
                .extract().response().asString();
        System.out.println(getactivefloors);

        // Floor filter by building id
        String filterfloors = given().log().all()
                .header("Content-Type", "application/json").pathParam("building","1")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/floor/active/building/{building}")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Active floor records found successfully"))
                .extract().response().asString();
        System.out.println(filterfloors);

//------------------------------------------------------------------------------------------------------
    //Add employee
        String empnic="15506787901";
        String empemail="wijesuriy@gmail.com";
        String empmobile="071655529";
        String empfirstname="Denuwan";
        String emplastname="Dodangoda";
        String empdesignation="manager";
        String addemployee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"employeeId\":0,\n" +
                        "    \"firstName\":\""+empfirstname+"\",\n" +
                        "    \"lastName\":\""+emplastname+"\",\n" +
                        "    \"nic\":\""+empnic+"\",\n" +
                        "    \"email\":\""+empemail+"\",\n" +
                        "    \"mobile\":\""+empmobile+"\",\n" +
                        "    \"designation\":\""+empdesignation+"\"\n" +
                        "}").when().post("/v1/employee/add")
                .then().assertThat().statusCode(200).body("msg",equalTo("Employee saved successfully"))
                .body("success",equalTo(true))
                .extract().response().asString();
        System.out.println(addemployee);

 //Already exit employee
        String add_employee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"employeeId\":0,\n" +
                        "    \"firstName\":\"Kavindu\",\n" +
                        "    \"lastName\":\"Akash\",\n" +
                        "    \"nic\":\"123457\",\n" +
                        "    \"email\":\"bkkasamarasingha120@gmail.com\",\n" +
                        "    \"mobile\":\"+94767221871\",\n" +
                        "    \"designation\":\"Manager\"\n" +
                        "}").when().post("/v1/employee/add")
                .then().assertThat().statusCode(417)
                .body("msg",equalTo("Already exist an employee with this NIC"))
                .body("success",equalTo(false))
                .extract().response().asString();
        System.out.println(add_employee);

       //Get employee
        String get_added_employee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/employee/search/all/active")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js8 = new JsonPath(get_added_employee);
        System.out.println(get_added_employee);
        List<Object> emp_body = js8.getList("body");
        int size8 = emp_body.size();
        int lastempId = js8.getInt("body["+(size8-1)+"].employeeId");

        Object emp = emp_body.get(size8- 1);
        System.out.println(emp);
        System.out.println(lastempId);

        String actualempnic= js8.getString("body["+(size8-1)+"].nic");
        String actualempemail= js8.getString("body["+(size8-1)+"].email");
        String actualempfirstname= js8.getString("body["+(size8-1)+"].firstName");
        String actualempelastname= js8.getString("body["+(size8-1)+"].lastName");
        String actualempemobile= js8.getString("body["+(size8-1)+"].mobile");
        String actualempdesignation= js8.getString("body["+(size8-1)+"].designation");
        Assert.assertEquals(actualempnic,empnic);
        Assert.assertEquals(actualempemail,empemail);
        Assert.assertEquals(actualempemobile,empmobile);
        Assert.assertEquals(actualempfirstname,empfirstname);
        Assert.assertEquals(actualempelastname,emplastname);
        Assert.assertEquals(actualempdesignation,actualempdesignation);
        System.out.println(actualempnic);
        System.out.println(actualempemail);



        //Update employee
        String update_empenic="9961817473";
        String update_empmail="lakshaniiin@gmail.com";
        String update_empmobile="0715490797";
        String update_empfirstname="Kavinda";
        String update_emplastname="Hanasaka";
        String update_designation="Project Manager";

        String update_employee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"employeeId\":"+lastempId+",\n" +
                        "    \"firstName\":\""+update_empfirstname+"\",\n" +
                        "    \"lastName\":\""+update_emplastname+"\",\n" +
                        "    \"nic\":\""+update_empenic+"\",\n" +
                        "    \"email\":\""+update_empmail+"\",\n" +
                        "    \"mobile\":\""+update_empmobile+"\",\n" +
                        "    \"designation\":\""+update_designation+"\"\n" +
                        "}").when().put("/v1/employee/update")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println(update_employee);

      //Already exit employee
        String alreadyexit_updateemployee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("{\n" +
                        "    \"employeeId\":2,\n" +
                        "    \"firstName\":\"Kavindu\",\n" +
                        "    \"lastName\":\"Akash\",\n" +
                        "    \"nic\":\"12345\",\n" +
                        "    \"email\":\"bkkasamarasingha1220@gmail.com\",\n" +
                        "    \"mobile\":\"+94767221831\",\n" +
                        "    \"designation\":\"Project Manager\"\n" +
                        "}").when().put("/v1/employee/update")
                .then().assertThat().statusCode(417)
                .body("msg",equalTo("Already exist an employee with this NIC"))
                .extract().response().asString();
        System.out.println(alreadyexit_updateemployee);

//        //Get employee
        String get_update_employee = given().log().all()
                .header("Content-Type", "application/json")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/employee/search/all/active")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .extract().response().asString();
        JsonPath js10 = new JsonPath(get_update_employee);
        System.out.println(get_added_employee);
        List<Object> emp_body1 = js10.getList("body");
        int size7 = emp_body1.size();
        int lastempId1 = js10.getInt("body["+(size7-1)+"].employeeId");

        Object emp1 = emp_body1.get(size7- 1);
        System.out.println(emp1);
        System.out.println(lastempId1);
        String actualupdate_empnic= js10.getString("body["+(size7-1)+"].nic");
        String actualupdate_empemail= js10.getString("body["+(size7-1)+"].email");
        String actualupdate_empmobile= js10.getString("body["+(size7-1)+"].mobile");
        String actualupdate_empfirstname= js10.getString("body["+(size7-1)+"].firstName");
        String actualupdate_emplastname= js10.getString("body["+(size7-1)+"].lastName");
        String actualupdate_empdesignation= js10.getString("body["+(size7-1)+"].designation");
        Assert.assertEquals(actualupdate_empnic,update_empenic);
        Assert.assertEquals(actualupdate_empemail,update_empmail);
        Assert.assertEquals(actualupdate_empmobile,update_empmobile);
        Assert.assertEquals(actualupdate_empfirstname,update_empfirstname);
        Assert.assertEquals(actualupdate_emplastname,update_emplastname);
        Assert.assertEquals(actualupdate_empdesignation,update_designation);
        System.out.println(actualupdate_empnic);
        System.out.println(actualupdate_empemail);

 //Get employees and filtering
        String getemployeefiltering = given().log().all()
                .header("Content-Type", "application/json")
                .queryParam("word","kar")
                .queryParam("index","0")
                .queryParam("size","10")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/employee/all")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Employee records found successfully"))
                .extract().response().asString();
        System.out.println(getemployeefiltering);

   //Visitor filtering
        String visitorfiltering = given().log().all()
                .header("Content-Type", "application/json")
                .queryParam("word","12345")
                .queryParam("index","1")
                .queryParam("size","10")
                .auth().oauth2(accessToken)
                .body("").when().get("/v1/visitor/filter")
                .then().assertThat().statusCode(200)
                .body("success",equalTo(true))
                .body("msg",equalTo("Matching visitor details filter successfully"))
                .extract().response().asString();
        System.out.println(visitorfiltering);
    }
}
