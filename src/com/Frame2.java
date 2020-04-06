import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import oop.Main;
import oop.TrainMaker;
import oop.cargo.Cargo;
import oop.complectors.Train;
import oop.complectors.TrainsOnTheWay;
import oop.complectors.wagons.FreightWagon;
import oop.complectors.wagons.PassengerWagon;
import oop.queries.Query;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Frame2 extends Application {
    public Button startButton;
    public TextArea TrainsField;
    public TextArea ResourcesField;
    public TextArea QueriesField;
    public Label QueriesCountText;
    public Label TrainsCountText;
    public BlockingQueue<Train> returnedTrainQueue = new LinkedBlockingDeque<>();
    public BlockingQueue<Train> trainsOngoing = new LinkedBlockingDeque<>();
    public BlockingQueue<Query> requestQueue = new LinkedBlockingDeque<>();
    public final Object event = new Object();
    public Resources resources = new Resources();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newframe.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Lol");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        while (this.resources == null) {
            this.resources = new Resources();
        }

        this.startButton.setOnAction(event -> {
            try {
                startSimulation(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //QueryThread queryThread = new QueryThread(this);
    }


//    public void OnCreateRequestClick(ActionEvent actionEvent) {
//        Query query = Query.createRandomQuery();
//        queryQueue.add(query);
//        String json = writeQueryFile(query);
//        RequestText.setText(json);
//    }

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

    public class RequestProcessingThread extends Thread {
        private Frame2 controller;

        RequestProcessingThread(Frame2 controller) {
            this.controller = controller;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (controller.event) {
                        ArrayList<Query> notDone = new ArrayList<>();
                        controller.event.wait();
                        controller.draw();
                        this.sleep(500);
                        while (!returnedTrainQueue.isEmpty()) {
                            controller.resources.addResources(controller.returnedTrainQueue.poll());
                        }
                        while (!controller.requestQueue.isEmpty()) {
                            Query query = controller.requestQueue.poll();
                            TrainMaker trainMaker = new TrainMaker();
                            ArrayList<Train> trains = trainMaker.perform(query);
                            Resources need = new Resources();
                            for (Train train : trains) {
                                need.addResources(train);
                            }
                            if (controller.resources.enoughResources(need)) {
                                for (Train train : trains) {
                                    TrainThread trainThread = new TrainThread(train, this.controller);
                                    trainThread.start();
                                    controller.resources.takeResources(train);
                                }
                            } else {
                                notDone.add(query);
                            }
                        }
                        for (Query query : notDone) {
                            controller.requestQueue.add(query);
                        }
                        controller.draw();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RequestGeneratorThread extends Thread {
        private Frame2 controller;

        RequestGeneratorThread(Frame2 controller) {
            this.controller = controller;
        }

        @Override
        public void run() {
            while (true) {
                Query query = Query.createRandomQuery();
                controller.requestQueue.add(query);
                synchronized (controller.event) {
                    controller.event.notify();
                }
                try {
                    this.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class TrainThread extends Thread {
        private Train train;
        private Frame2 controller;

        public TrainThread(Train train, Frame2 controller) {
            this.train = train;
            this.controller = controller;
        }

        @Override
        public void run() {
            controller.trainsOngoing.add(this.train);
            synchronized (controller.event) {
                controller.event.notify();
            }
            try {
                sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.trainsOngoing.remove(this.train);
            controller.returnedTrainQueue.add(train);
            synchronized (controller.event) {
                controller.event.notify();
            }
        }
    }

    public void startSimulation(ActionEvent actionEvent) throws Exception {
        for (int i = 0; i < 7; ++i) {
            Query query = Query.createRandomQuery();
            TrainMaker trainMaker = new TrainMaker();
            ArrayList<Train> trains = trainMaker.perform(query);
            for (Train train : trains) {
                this.resources.addResources(train);
            }
        }

        RequestProcessingThread processingThread = new RequestProcessingThread(this);
        processingThread.setDaemon(true);
        processingThread.start();
        RequestGeneratorThread generatorThread = new RequestGeneratorThread(this);
        generatorThread.setDaemon(true);
        generatorThread.start();
    }

    private void draw() {
        Platform.runLater(
            () -> {
                Gson gson = getSerializer();
                String trainsText = "";
                for (Train train : this.trainsOngoing) {
                    trainsText += gson.toJson(train, Train.class) + '\n' + "==================" + '\n';
                }
                this.TrainsField.setText(trainsText);
                this.TrainsCountText.setText('[' + String.valueOf(this.trainsOngoing.size()) + ']');
                String queryText = "";
                for (Query query : this.requestQueue) {
                    queryText += gson.toJson(query, Query.class) + '\n' + "==================" + '\n';
                }
                this.QueriesField.setText(queryText);
                this.QueriesCountText.setText('[' + String.valueOf(this.requestQueue.size()) + ']');
                this.ResourcesField.setText(gson.toJson(this.resources));
            }
        );
    }
}
