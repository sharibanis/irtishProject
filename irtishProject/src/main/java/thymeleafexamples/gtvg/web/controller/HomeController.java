/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package thymeleafexamples.gtvg.web.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.BsonDouble;
import org.bson.Document;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

public class HomeController implements IGTVGController {
	
	Logger log = Logger.getLogger(this.getClass().getName());
	/*MongoClientURI uri = new MongoClientURI(
			"mongodb+srv://irtishProject:irtishProject@sharib0-35h98.mongodb.net/test?retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);*/
	
    	MongoClientURI uri = new MongoClientURI(
				"mongodb://irtishProject:irtishProject@localhost:27017/admin");
		MongoClient mongoClient = new MongoClient(uri);

		public HomeController() {
        super();
    }

    void fetchDarkSkyData (WebContext ctx) throws ForecastException {
		// get Dark Sky weather info using https://github.com/200Puls/darksky-forecast-api driver
    	log.info("Fetching Dark Sky weather info...");
        //Campbell, CA
        ForecastRequest forecastRequest0 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(-121.949959), new Latitude(37.287167))).build();

        DarkSkyClient client0 = new DarkSkyClient();
        String forecast0 = client0.forecastJsonString(forecastRequest0);
        //log.debug(forecast0);
        byte[] byteArr = forecast0.getBytes();
        InputStream bis = new ByteArrayInputStream(byteArr);
        JsonReader jsonReader0 = Json.createReader(bis);
        JsonObject jsonObject0 = jsonReader0.readObject();
        JsonObject jsonObject01 = (JsonObject) jsonObject0.get("daily");
        //log.debug(jsonObject01);
        JsonArray JsonArray011 = (JsonArray) jsonObject01.getJsonArray("data");
        JsonObject jsonObject011 = JsonArray011.getJsonObject(0);
        JsonObject jsonForecast0 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject011.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject011.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject011.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast0", jsonForecast0);
        log.debug(jsonForecast0);
        
        //Omaha, NE
        ForecastRequest forecastRequest1 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(-95.995102), new Latitude(41.257160))).build();

        DarkSkyClient client1 = new DarkSkyClient();
        String forecast1 = client1.forecastJsonString(forecastRequest1);
        //log.debug(forecast1);
        byte[] byteArr1 = forecast1.getBytes();
        InputStream bis1 = new ByteArrayInputStream(byteArr1);
        JsonReader jsonReader1 = Json.createReader(bis1);
        JsonObject jsonObject1 = jsonReader1.readObject();
        JsonObject jsonObject11 = (JsonObject) jsonObject1.get("daily");
        JsonArray JsonArray111 = (JsonArray) jsonObject11.getJsonArray("data");
        JsonObject jsonObject111 = JsonArray111.getJsonObject(0);
        
        JsonObject jsonForecast1 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject111.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject111.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject111.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast1", jsonForecast1);
        log.debug(jsonForecast1);
        
        //Austin, TX
        ForecastRequest forecastRequest2 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(-97.733330), new Latitude(30.266666))).build();

        DarkSkyClient client2 = new DarkSkyClient();
        String forecast2 = client2.forecastJsonString(forecastRequest2);
        //log.debug(forecast2);
        byte[] byteArr2 = forecast2.getBytes();
        InputStream bis2 = new ByteArrayInputStream(byteArr2);
        JsonReader jsonReader2 = Json.createReader(bis2);
        JsonObject jsonObject2 = jsonReader2.readObject();
        JsonObject jsonObject21 = (JsonObject) jsonObject2.get("daily");
        JsonArray JsonArray211 = (JsonArray) jsonObject21.getJsonArray("data");
        JsonObject jsonObject211 = JsonArray211.getJsonObject(0);
        
        JsonObject jsonForecast2 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject211.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject211.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject211.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast2", jsonForecast2);
        log.debug(jsonForecast2);
        
        //Niseko, Japan
        ForecastRequest forecastRequest3 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(140.6874), new Latitude(42.8048))).build();

        DarkSkyClient client3 = new DarkSkyClient();
        String forecast3 = client3.forecastJsonString(forecastRequest3);
        //log.debug(forecast3);
        byte[] byteArr3 = forecast3.getBytes();
        InputStream bis3 = new ByteArrayInputStream(byteArr3);
        JsonReader jsonReader3 = Json.createReader(bis3);
        JsonObject jsonObject3 = jsonReader3.readObject();
        JsonObject jsonObject31 = (JsonObject) jsonObject3.get("daily");
        JsonArray JsonArray311 = (JsonArray) jsonObject31.getJsonArray("data");
        JsonObject jsonObject311 = JsonArray311.getJsonObject(0);
        
        JsonObject jsonForecast3 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject311.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject311.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject311.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast3", jsonForecast3);
        log.debug(jsonForecast3);
        
        //Nara, Japan
        ForecastRequest forecastRequest4 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(135.8048), new Latitude(34.6851))).build();

        DarkSkyClient client4 = new DarkSkyClient();
        String forecast4 = client4.forecastJsonString(forecastRequest4);
        //log.debug(forecast4);
        byte[] byteArr4 = forecast4.getBytes();
        InputStream bis4 = new ByteArrayInputStream(byteArr4);
        JsonReader jsonReader4 = Json.createReader(bis4);
        JsonObject jsonObject4 = jsonReader4.readObject();
        JsonObject jsonObject41 = (JsonObject) jsonObject4.get("daily");
        JsonArray JsonArray411 = (JsonArray) jsonObject41.getJsonArray("data");
        JsonObject jsonObject411 = JsonArray411.getJsonObject(0);
        
        JsonObject jsonForecast4 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject411.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject411.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject411.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast4", jsonForecast4);
        log.debug(jsonForecast4);
        
        //Jakarta, Indonesia
        ForecastRequest forecastRequest5 = new ForecastRequestBuilder()
                .key(new APIKey("4b622e0e8337b11f1fa5fe9af2a19ee1"))
                .language(ForecastRequestBuilder.Language.en)
                .units(ForecastRequestBuilder.Units.si)
                .exclude(ForecastRequestBuilder.Block.minutely)
                .location(new GeoCoordinates(new Longitude(106.8456), new Latitude(6.2088))).build();

        DarkSkyClient client5 = new DarkSkyClient();
        String forecast5 = client5.forecastJsonString(forecastRequest5);
        //log.debug(forecast5);
        byte[] byteArr5 = forecast5.getBytes();
        InputStream bis5 = new ByteArrayInputStream(byteArr5);
        JsonReader jsonReader5 = Json.createReader(bis5);
        JsonObject jsonObject5 = jsonReader5.readObject();
        JsonObject jsonObject51 = (JsonObject) jsonObject5.get("daily");
        JsonArray JsonArray511 = (JsonArray) jsonObject51.getJsonArray("data");
        JsonObject jsonObject511 = JsonArray511.getJsonObject(0);
        
        JsonObject jsonForecast5 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject511.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject511.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject511.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast5", jsonForecast5);
        log.debug(jsonForecast5);
    	log.info("Fetched Dark Sky weather info.");
    	
    	log.info("Connecting to MongoDB...");
		MongoDatabase database = mongoClient.getDatabase("irtishProjectDB");
		MongoCollection<Document> collection = database.getCollection("irtishProject");
    	log.info("Updating MongoDB...");
		collection.deleteMany(gt("time", 0));
		List<Document> documents = new ArrayList<Document>();
		Document doc = new Document("name", "Campbell, CA")
                .append("time", jsonObject011.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject011.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject011.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject011.getString("summary"));

		documents.add(doc);
		doc = new Document("name", "Omaha, NE")
                .append("time", jsonObject111.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject111.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject111.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject111.getString("summary"));

		documents.add(doc);
		doc = new Document("name", "Austin, TX")
                .append("time", jsonObject211.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject211.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject211.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject211.getString("summary"));

		documents.add(doc);
		doc = new Document("name", "Niseko, Japan")
                .append("time", jsonObject311.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject311.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject311.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject311.getString("summary"));

		documents.add(doc);
		doc = new Document("name", "Nara, Japan")
                .append("time", jsonObject411.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject411.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject411.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject411.getString("summary"));

		documents.add(doc);
		doc = new Document("name", "Jakarta, Indonesia")
                .append("time", jsonObject511.getJsonNumber("time").longValue())
                .append("temperatureHigh", jsonObject511.getJsonNumber("temperatureHigh").doubleValue())
                .append("temperatureLow", jsonObject511.getJsonNumber("temperatureLow").doubleValue())
                .append("summary", jsonObject511.getString("summary"));

		documents.add(doc);
		
		collection.insertMany(documents);
    	log.info("Updated MongoDB.");
    }
    
    void fetchMongoData (WebContext ctx, MongoCursor<Document> cursor) {
    	log.info("Fetching MongoDB weather info...");
        String forecast0 = cursor.next().toJson();
        log.debug(forecast0);
        byte[] byteArr = forecast0.getBytes();
        InputStream bis = new ByteArrayInputStream(byteArr);
        JsonReader jsonReader0 = Json.createReader(bis);
        JsonObject jsonObject0 = jsonReader0.readObject();
        JsonObject jsonForecast0 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject0.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject0.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject0.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast0", jsonForecast0);
        log.debug(jsonForecast0);
        
        //Omaha, NE
        String forecast1 = cursor.next().toJson();
        //log.debug(forecast1);
        byte[] byteArr1 = forecast1.getBytes();
        InputStream bis1 = new ByteArrayInputStream(byteArr1);
        JsonReader jsonReader1 = Json.createReader(bis1);
        JsonObject jsonObject1 = jsonReader1.readObject();
        JsonObject jsonForecast1 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject1.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject1.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject1.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast1", jsonForecast1);
        log.debug(jsonForecast1);
        
        //Austin, TX
        String forecast2 = cursor.next().toJson();
        //log.debug(forecast2);
        byte[] byteArr2 = forecast2.getBytes();
        InputStream bis2 = new ByteArrayInputStream(byteArr2);
        JsonReader jsonReader2 = Json.createReader(bis2);
        JsonObject jsonObject2 = jsonReader2.readObject();
        JsonObject jsonForecast2 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject2.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject2.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject2.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast2", jsonForecast2);
        log.debug(jsonForecast2);
        
        //Niseko, Japan
        String forecast3 = cursor.next().toJson();
        //log.debug(forecast3);
        byte[] byteArr3 = forecast3.getBytes();
        InputStream bis3 = new ByteArrayInputStream(byteArr3);
        JsonReader jsonReader3 = Json.createReader(bis3);
        JsonObject jsonObject3 = jsonReader3.readObject();
        JsonObject jsonForecast3 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject3.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject3.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject3.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast3", jsonForecast3);
        log.debug(jsonForecast3);
        
        //Nara, Japan
        String forecast4 = cursor.next().toJson();
        //log.debug(forecast4);
        byte[] byteArr4 = forecast4.getBytes();
        InputStream bis4 = new ByteArrayInputStream(byteArr4);
        JsonReader jsonReader4 = Json.createReader(bis4);
        JsonObject jsonObject4 = jsonReader4.readObject();
        JsonObject jsonForecast4 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject4.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject4.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject4.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast4", jsonForecast4);
        log.debug(jsonForecast4);
        
        //Jakarta, Indonesia
        String forecast5 = cursor.next().toJson();
        //log.debug(forecast5);
        byte[] byteArr5 = forecast5.getBytes();
        InputStream bis5 = new ByteArrayInputStream(byteArr5);
        JsonReader jsonReader5 = Json.createReader(bis5);
        JsonObject jsonObject5 = jsonReader5.readObject();
        JsonObject jsonForecast5 = Json.createObjectBuilder()
        .add("temperatureHigh", jsonObject5.getJsonNumber("temperatureHigh"))
        .add("temperatureLow", jsonObject5.getJsonNumber("temperatureLow"))
        .add("summary", jsonObject5.getJsonString("summary"))
        .build();
        ctx.setVariable("forecast5", jsonForecast5);
        log.debug(jsonForecast5);
    	log.info("Fetched MongoDB weather info.");
    	}
    
    
    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {
        
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("today", Calendar.getInstance());
        
        //check in MongoDB
    	log.info("Connecting to MongoDB...");
		MongoDatabase database = mongoClient.getDatabase("irtishProjectDB");
		MongoCollection<Document> collection = database.getCollection("irtishProject");
    	log.info("Connected. Getting data from MongoDB...");
    	log.debug("irtishProject collection.count() "+ collection.count());
    	if (collection.count() == 0) {//empty collection
    		//fetch data from DarkSky and update mongo
    		try {
        		fetchDarkSkyData(ctx);
    		} catch (ForecastException ex) {
    			log.error(ex.toString());
    		}
    	}
		long today = System.currentTimeMillis()/1000;
    	long yesterday = today - 86400;
    	Document result = null;
    	result = collection.find(lt("time", (int)yesterday)).first();
    	log.debug("result "+ result);
    	if (result != null) {//stale data
    		//fetch data from DarkSky and update mongo
    		try {
        		fetchDarkSkyData(ctx);
    		} catch (ForecastException ex) {
    			log.error(ex.toString());
    		}
    	} else {
    		//return data from mongo
    		MongoCursor<Document> cursor = collection.find().iterator();
    		try {
        		fetchMongoData(ctx, cursor);
    		} catch (Exception ex) {
    			log.error(ex.toString());
    		} finally {
    		    cursor.close();
    		}
    	}
        templateEngine.process("home", ctx, response.getWriter());
		
    }
}
