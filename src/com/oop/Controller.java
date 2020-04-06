package oop;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import oop.Main;
import oop.TrainMaker;
import oop.cargo.Cargo;
import oop.complectors.Train;
import oop.complectors.TrainsOnTheWay;
import oop.complectors.wagons.FreightWagon;
import oop.complectors.wagons.PassengerWagon;
import oop.queries.FreightQuery;
import oop.queries.PassengerQuery;
import oop.queries.Query;
//import oop.queries.QueryThread;
import sun.misc.Queue;

public class Controller{

    public Button CreateRequestButton;
    public Button GenerateTrainsButton;
    public TextArea TrainsText;
    public TextArea PreviousTrainsText;
    public TextArea RequestText;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    void initialize() {

    }
    private ArrayDeque<Query> queryQueue = new ArrayDeque<>();

    public void addNewQuery(){
        Query query = Query.createRandomQuery();
        queryQueue.add(query);
    }

    public void OnCreateRequestClick(ActionEvent actionEvent) {
        Query query = Query.createRandomQuery();
        queryQueue.add(query);
        String json = writeQueryFile(query);
        RequestText.setText(json);
    }

    private static Gson getSerializer() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Train.class, new Main.PropertyBasedInterfaceMarshal())
                .registerTypeAdapter(PassengerWagon.class, new Main.PropertyBasedInterfaceMarshal())
                .registerTypeAdapter(FreightWagon.class, new Main.PropertyBasedInterfaceMarshal())
                .registerTypeAdapter(Query.class, new Main.PropertyBasedInterfaceMarshal())
                .registerTypeAdapter(Cargo.class, new Main.PropertyBasedInterfaceMarshal())
                .create();
    }

    public void OnGenerateTrainsClick(ActionEvent actionEvent) {
        PreviousTrainsText.setText(writeTrainsFile(readTrainsFile()));
        Query query = readQueryFile();
        TrainMaker trainMaker = new TrainMaker();
        ArrayList<Train> trains = trainMaker.perform(query);
        TrainsOnTheWay trainsOnTheWay = new TrainsOnTheWay();
        trainsOnTheWay.setTrains(trains);

        String json = writeTrainsFile(trainsOnTheWay);
        TrainsText.setText(json);
    }

    private static TrainsOnTheWay readTrainsFile() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/com/resources/trains.json")));
        } catch (IOException ex) {

        }
        Gson gson = getSerializer();
        return gson.fromJson(json, TrainsOnTheWay.class);
    }

    private static Query readQueryFile() {
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get("src/com/resources/query.json")));
        } catch (IOException ex) {

        }
        Gson gson = getSerializer();
        return gson.fromJson(json, Query.class);
    }

    private static String writeTrainsFile(TrainsOnTheWay currState) {
        Gson gson = getSerializer();
        String json = gson.toJson(currState);
        System.out.println(json);
        try (FileWriter fileWriter = new FileWriter("src/com/resources/trains.json", false)) {
            fileWriter.write(json);
        } catch (IOException ex) {

        }
        return json;
    }

    private static String writeQueryFile(Query query) {
        Gson gson = getSerializer();
        String json = gson.toJson(query, Query.class);
        try (FileWriter fileWriter = new FileWriter("src/com/resources/query.json", false)) {
            fileWriter.write(json);
        } catch (IOException ex) {

        }
        return json;
    }



}
