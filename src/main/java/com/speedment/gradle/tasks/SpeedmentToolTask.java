/**
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.gradle.tasks;

import com.speedment.common.injector.Injector;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.MainApp;
import javafx.application.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DriverManager;

/**
 * @author Sergio Figueras (sergio@yourecm.com)
 * @author Emil Forslund
 */
public class SpeedmentToolTask extends AbstractSpeedmentTask {

    private static final Logger LOGGER = LoggerManager.getLogger(SpeedmentToolTask.class);
    public static final String SPEEDMENT_TOOL_TASK_NAME = "speedment.tool";

    @Override
    protected void execute(Speedment speedment) {
        final Injector injector = speedment.getOrThrow(Injector.class);
        MainApp.setInjector(injector);

        LOGGER.info("Launching Speedment Tool from Gradle Plugin.");

        try {

            final Method method = DriverManager.class.getDeclaredMethod("loadInitialDrivers");
            method.setAccessible(true);
            method.invoke(null);
        } catch (final InvocationTargetException | IllegalAccessException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }

        if (hasConfigFile()) {
            Application.launch(MainApp.class, configLocation().toAbsolutePath().toString());
        } else {
            Application.launch(MainApp.class);
        }
//        CompletableFuture<MainApp> future;
//        if (hasConfigFile()) {
//            future = launchApplication(MainApp.class, configLocation().toAbsolutePath().toString());
//        } else {
//            future = launchApplication(MainApp.class);
//        }
//
//        MainApp app;
//        try { app = future.get(); }
//        catch (final InterruptedException | ExecutionException ex) {
//            throw new RuntimeException(ex);
//        }

        try { Thread.sleep(10_000); } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Done!");
    }
//
//    protected final void tryLaunch(Class<? extends Application> appClass, String... args) {
//        Platform.exit();
//
//        try {
//            LOGGER.info("Launching Speedment Tool from Gradle Plugin.");
//            Application.launch(appClass, args);
//        } catch (final IllegalStateException ex) {
//
//            LOGGER.warn("A JavaFX-application was already running. " +
//                "Attempting to create a second one.");
//
//            Platform.runLater(() -> { // In the GUI-thread:
//                try {
//                    System.out.println("Hello, world!"); // TODO: Remove this.
//
//                    final Application app = appClass.newInstance();
//                    app.start(new Stage());
//
//                } catch (InstantiationException | IllegalAccessException ex2) {
//                    throw new RuntimeException(
//                        "Error creating the Speedment JavaFX-Application " +
//                        "instance using reflection. This typically happen if " +
//                        "another JavaFX application is already running and " +
//                        "can't therefore be created using conventional " +
//                        "means. Make sure the constructor for the " +
//                        appClass.getName() + " class is public.",
//                        ex2
//                    );
//                } catch (Exception ex2) {
//                    throw new RuntimeException(
//                        "Error running Speedment Tool.", ex2);
//                }
//            });
//        }
//
//        try { Thread.sleep(10_000); } catch (final Exception ex) {throw new RuntimeException(ex);}
//    }

    @Override
    public String getDescription() {
        return "Opens Speedment Tool.";
    }
}