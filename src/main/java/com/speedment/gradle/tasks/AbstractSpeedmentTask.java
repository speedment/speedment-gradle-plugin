package com.speedment.gradle.tasks;

import com.speedment.generator.core.GeneratorBundle;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.gradle.utils.StringUtils;
import com.speedment.runtime.application.ApplicationBuilders;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.ToolBundle;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import org.gradle.api.DefaultTask;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;

import static com.speedment.runtime.application.internal.DefaultApplicationMetadata.METADATA_LOCATION;
import static com.speedment.tool.core.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
abstract class AbstractSpeedmentTask extends DefaultTask {

    private static final Path DEFAULT_CONFIG = Paths.get(DEFAULT_CONFIG_LOCATION);
    private static final Consumer<ApplicationBuilder<?, ?>> NOTHING = builder -> {};

    private final Consumer<ApplicationBuilder<?, ?>> configurer;

    private String configFile;
    private boolean debug;
    private String dbmsHost;
    private int dbmsPort;
    private String dbmsUsername;
    private String dbmsPassword;
    private String dbmsConnectionUrl;
    private String[] components;
    // private Mapping[] typeMappers;
    // private ConfigParam[] parameters;

    AbstractSpeedmentTask() {
        this(NOTHING);
    }

    AbstractSpeedmentTask(Consumer<ApplicationBuilder<?, ?>> configurer) {
        this.configurer = requireNonNull(configurer);
    }

    @Input
    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Input
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Input
    public String getDbmsHost() {
        return dbmsHost;
    }

    public void setDbmsHost(String dbmsHost) {
        this.dbmsHost = dbmsHost;
    }

    @Input
    public int getDbmsPort() {
        return dbmsPort;
    }

    public void setDbmsPort(int dbmsPort) {
        this.dbmsPort = dbmsPort;
    }

    @Input
    public String getDbmsUsername() {
        return dbmsUsername;
    }

    public void setDbmsUsername(String dbmsUsername) {
        this.dbmsUsername = dbmsUsername;
    }

    @Input
    public String getDbmsPassword() {
        return dbmsPassword;
    }

    public void setDbmsPassword(String dbmsPassword) {
        this.dbmsPassword = dbmsPassword;
    }

    @Input
    public String getDbmsConnectionUrl() {
        return dbmsConnectionUrl;
    }

    public void setDbmsConnectionUrl(String dbmsConnectionUrl) {
        this.dbmsConnectionUrl = dbmsConnectionUrl;
    }

    @Input
    public String[] getComponents() {
        return components;
    }

    public void setComponents(String[] components) {
        this.components = components;
    }

    protected abstract void execute(Speedment speedment);

    @TaskAction
    public void execute() {
        final ApplicationBuilder<?, ?> builder = createBuilder();

        // TODO: Investigate if project path needs to be added
        //builder.withComponent(MavenPathComponent.class);
        //builder.withParam(MAVEN_BASE_DIR, project().getBasedir().toString());

        configurer.accept(builder);

        if (isDebug()) {
            builder.withLogging(ApplicationBuilder.LogType.APPLICATION_BUILDER);
        }

        builder.withSkipCheckDatabaseConnectivity();
        final Speedment speedment = builder.build();

        getLogger().info(getDescription());
        execute(speedment);
    }

    protected Path configLocation() {
        final String top = Optional.ofNullable(getConfigFile())
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .orElse(DEFAULT_CONFIG_LOCATION);

        return getProject().getProjectDir().toPath().resolve(top);
    }

    protected final boolean hasConfigFile() {
        return hasConfigFile(configLocation());
    }

    protected final boolean hasConfigFile(Path file) {
        if (file == null) {
            final String msg = "The expected .json-file is null.";
            getLogger().info(msg);
            return false;
        } else if (!Files.exists(file)) {
            final String msg = "The expected .json-file '"
                + file + "' does not exist.";
            getLogger().info(msg);
            return false;
        } else if (!Files.isReadable(file)) {
            final String err = "The expected .json-file '"
                + file + "' is not readable.";
            getLogger().error(err);
            return false;
        } else {
            return true;
        }
    }

    protected final void assertHasConfigFile() {
        if (!hasConfigFile()) {
            final String err = "To run speedment:generate a valid configFile needs to be specified.";
            getLogger().error(err);
            throw new InvalidUserDataException(err);
        }
    }

    private ApplicationBuilder<?, ?> createBuilder() {
        final ApplicationBuilder<?, ?> result;

        // TODO: Create a ClassLoader that can read Gradle dependencies.

        // Configure config file location
        if (hasConfigFile()) {
            result = ApplicationBuilders.standard()
                .withParam(METADATA_LOCATION, configLocation().toAbsolutePath().toString());
        } else if (hasConfigFile(DEFAULT_CONFIG)) {
            result = ApplicationBuilders.standard()
                .withParam(METADATA_LOCATION, DEFAULT_CONFIG_LOCATION);
        } else {
            result = ApplicationBuilders.empty();
        }

        //
        result.withSkipCheckDatabaseConnectivity();

        // Configure manual database settings
        if (StringUtils.isNotBlank(getDbmsHost())) {
            result.withIpAddress(getDbmsHost());
            getLogger().info("Custom database host '" + getDbmsHost() + "'.");
        }

        if (getDbmsPort() != 0) {
            result.withPort(getDbmsPort());
            getLogger().info("Custom database port '" + getDbmsPort() + "'.");
        }

        if (StringUtils.isNotBlank(getDbmsUsername())) {
            result.withUsername(getDbmsUsername());
            getLogger().info("Custom database username '" + getDbmsUsername() + "'.");
        }

        if (StringUtils.isNotBlank(getDbmsPassword())) {
            result.withPassword(getDbmsPassword());
            getLogger().info("Custom database password '********'.");
        }

        if (StringUtils.isNotBlank(getDbmsConnectionUrl())) {
            result.withConnectionUrl(getDbmsConnectionUrl());
            getLogger().info("Custom connection URL '" + getDbmsConnectionUrl() + "'.");
        }

        // Add mandatory components that are not included in 'runtime'
        result
            .withBundle(GeneratorBundle.class)
            .withBundle(ToolBundle.class)
            .withComponent(CodeGenerationComponentImpl.class)
            .withComponent(UserInterfaceComponentImpl.class);

        // TODO: See if .withComponent(MavenPathComponent.class); is necessary

        // Add any extra type mappers requested by the user
        // TODO: Add extra type mappers
//        TypeMapperInstaller.mappings = getTypeMappers(); // <-- Hack to pass type mappers to class with default constructor.
//        result.withComponent(TypeMapperInstaller.class);

        // Add extra components requested by the user
        // TODO: Add extra components
//        final String[] components = components();
//        if (components != null) {
//            for (final String component : components) {
//                try {
//                    final Class<?> uncasted = classLoader.loadClass(component);
//
//                    if (InjectBundle.class.isAssignableFrom(uncasted)) {
//                        @SuppressWarnings("unchecked")
//                        final Class<? extends InjectBundle> casted
//                            = (Class<? extends InjectBundle>) uncasted;
//                        result.withBundle(casted);
//                    } else {
//                        result.withComponent(uncasted);
//                    }
//
//                } catch (final ClassNotFoundException ex) {
//                    throw new MojoExecutionException(
//                        "Specified class '" + component + "' could not be "
//                            + "found on class path. Has the dependency been "
//                            + "configured properly?", ex
//                    );
//                }
//            }
//        }

        // Set parameters configured in the pom.xml
        // TODO: Add ConfigParam
//        final ConfigParam[] parameters = parameters();
//        if (parameters != null) {
//            for (final ConfigParam param : parameters()) {
//                result.withParam(param.getName(), param.getValue());
//            }
//        }

        // Return the resulting builder.
        return result;
    }

//    @InjectKey(TypeMapperInstaller.class)
//    private final static class TypeMapperInstaller {
//
//        private static Mapping[] mappings;
//
//        @ExecuteBefore(RESOLVED)
//        void installInTypeMapper(
//            final Injector injector,
//            final @WithState(INITIALIZED) TypeMapperComponent typeMappers
//        ) throws MojoExecutionException {
//            if (mappings != null) {
//                for (final Mapping mapping : mappings) {
//                    final Class<?> databaseType;
//                    try {
//                        databaseType = injector.classLoader()
//                            .loadClass(mapping.getDatabaseType());
//
//                    } catch (final ClassNotFoundException ex) {
//                        throw new MojoExecutionException(
//                            "Specified database type '" + mapping.getDatabaseType() + "' "
//                                + "could not be found on class path. Make sure it is a "
//                                + "valid JDBC type for the choosen connector.", ex
//                        );
//                    } catch (final ClassCastException ex) {
//                        throw new MojoExecutionException(
//                            "An unexpected ClassCastException occured.", ex
//                        );
//                    }
//
//                    try {
//                        final Class<?> uncasted = injector.classLoader()
//                            .loadClass(mapping.getImplementation());
//
//                        @SuppressWarnings("unchecked")
//                        final Class<TypeMapper<?, ?>> casted
//                            = (Class<TypeMapper<?, ?>>) uncasted;
//                        final Constructor<TypeMapper<?, ?>> constructor
//                            = casted.getConstructor();
//
//                        final Supplier<TypeMapper<?, ?>> supplier = () -> {
//                            try {
//                                return constructor.newInstance();
//                            } catch (final IllegalAccessException
//                                | IllegalArgumentException
//                                | InstantiationException
//                                | InvocationTargetException ex) {
//
//                                throw new TypeMapperInstantiationException(ex);
//                            }
//                        };
//
//                        typeMappers.install(databaseType, supplier);
//                    } catch (final ClassNotFoundException ex) {
//                        throw new MojoExecutionException(
//                            "Specified class '" + mapping.getImplementation()
//                                + "' could not be found on class path. Has the "
//                                + "dependency been configured properly?", ex
//                        );
//                    } catch (final ClassCastException ex) {
//                        throw new MojoExecutionException(
//                            "Specified class '" + mapping.getImplementation()
//                                + "' does not implement the '"
//                                + TypeMapper.class.getSimpleName() + "'-interface.",
//                            ex
//                        );
//                    } catch (final NoSuchMethodException
//                        | TypeMapperInstantiationException ex) {
//                        throw new MojoExecutionException(
//                            "Specified class '" + mapping.getImplementation()
//                                + "' could not be instantiated. Does it have a "
//                                + "default constructor?", ex
//                        );
//                    }
//                }
//            }
//        }
//    }
}
