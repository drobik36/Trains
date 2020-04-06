package oop;

import com.google.gson.*;
import oop.cargo.Cargo;
import oop.complectors.TrainsOnTheWay;
import oop.complectors.Train;
import oop.complectors.wagons.FreightWagon;
import oop.complectors.wagons.PassengerWagon;
import oop.queries.FreightQuery;
import oop.queries.PassengerQuery;
import oop.queries.Query;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

	// https://stackoverflow.com/questions/3629596/deserializing-an-abstract-class-in-gson
	public static class PropertyBasedInterfaceMarshal implements
			JsonSerializer<Object>, JsonDeserializer<Object> {

		private static final String CLASS_META_KEY = "CLASS_META_KEY";

		@Override
		public Object deserialize(JsonElement jsonElement, Type type,
								  JsonDeserializationContext jsonDeserializationContext)
				throws JsonParseException {
			JsonObject jsonObj = jsonElement.getAsJsonObject();
			String className = jsonObj.get(CLASS_META_KEY).getAsString();
			try {
				Class<?> clz = Class.forName(className);
				return jsonDeserializationContext.deserialize(jsonElement, clz);
			} catch (ClassNotFoundException e) {
				throw new JsonParseException(e);
			}
		}

		@Override
		public JsonElement serialize(Object object, Type type,
									 JsonSerializationContext jsonSerializationContext) {
		    //Gson nestedGson = new GsonBuilder().setPrettyPrinting().create();
            //JsonElement jsonElem = nestedGson.toJsonTree(object, object.getClass());
			JsonElement jsonElem = jsonSerializationContext.serialize(object, object.getClass());
			jsonElem.getAsJsonObject().addProperty(CLASS_META_KEY,
					object.getClass().getCanonicalName());
			return jsonElem;
		}

	}

    public static final String FIRST_TEXT_MESSAGE = "Total count of trains: %d.";
    public static final String SECOND_TEXT_MESSAGE = "%d train - %s : %d wagons, %d locomotives, %.2f total mass";
    public static final String TEXT_FOR_ALL_QUERY = "Type of query: %s";
    public static final String TEXT_FOR_PASSENGER_QUERY = "Generated queries: %d places in SV, %d places in RS, %d places in coupe";
    public static final String TEXT_FOR_FREIGHT_QUERY = "%d cargo - %s type";


    public static void main(String[] args) {
        //TrainsOnTheWay prev_state = readFile();
        //Query query = Query.createRandomQuery();
        //System.out.println(String.format(TEXT_FOR_ALL_QUERY, query.getType()));
        //if (query instanceof PassengerQuery) {
        //    PassengerQuery pQuery = (PassengerQuery) query;
        //    System.out.println(String.format(TEXT_FOR_PASSENGER_QUERY, pQuery.getCountSV(),
        //            pQuery.getCountRS(), pQuery.getCountCoupe()));
        //} else {
        //    if (query instanceof FreightQuery) {
        //        FreightQuery fQuery = (FreightQuery) query;
        //        ArrayList<Cargo> cargoes = fQuery.getCargoes();
        //        int count = 1;
        //        for (Cargo cargo : cargoes) {
        //            System.out.println(String.format(TEXT_FOR_FREIGHT_QUERY, count, cargo.getType()));
        //            ++count;
        //        }
        //    }
        //}
        //TrainMaker trainMaker = new TrainMaker();
        //ArrayList<Train> trains = trainMaker.perform(query);
        //System.out.println(String.format(FIRST_TEXT_MESSAGE, trains.size()));
        //int count = 1;
        //for (Train curr : trains) {
        //    System.out.println(String.format(SECOND_TEXT_MESSAGE, count, curr.getType(), curr.getCountOfWagons(),
        //            curr.getLocomotiveCount(), curr.getTotalWeight()));
        //    ++count;
        //}
        //TrainsOnTheWay currState = new TrainsOnTheWay();
        //currState.setTrains(trains);
        ////writeTrainsFile(currState);
    }

    //private static Gson getSerializer() {
    //	return new GsonBuilder()
	//			.setPrettyPrinting()
	//			.registerTypeAdapter(Train.class, new PropertyBasedInterfaceMarshal())
	//			.registerTypeAdapter(PassengerWagon.class, new PropertyBasedInterfaceMarshal())
	//			.registerTypeAdapter(FreightWagon.class, new PropertyBasedInterfaceMarshal())
	//			.create();
	//}


    //private static TrainsOnTheWay readFile() {
    //    String jsonFile = null;
    //    try {
    //        jsonFile = new String(Files.readAllBytes(Paths.get("src/com/resources/trains.json")));
    //    } catch (IOException ex) {
//
    //    }
    //    Gson gson = getSerializer();
    //    return gson.fromJson(jsonFile, TrainsOnTheWay.class);
    //}

}
