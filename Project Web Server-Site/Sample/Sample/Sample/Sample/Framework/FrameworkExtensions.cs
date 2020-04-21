﻿using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using System;
using System.IO;
using System.Reflection;

namespace Sample
{
    /// <summary>
    /// Extension methods associated with the <see cref="Framework"/>
    /// </summary>
    public static class FrameworkExtensions
    {
        #region Construction

        /// <summary>
        /// Configures a framework construction in the default way
        /// </summary>
        /// <param name="construction">The construction to configure</param>
        /// <param name="configure">The custom configuration action</param>
        /// <returns></returns>
        public static FrameworkConstruction AddDefaultConfiguration(this FrameworkConstruction construction, Action<IConfigurationBuilder> configure = null)
        {
            // Create configuration builder
            var configurationBuilder = new ConfigurationBuilder()
                // Add the environment variables
                .AddEnvironmentVariables();

            // Add file based configuration

            // Set base path for Json files as the startup location of the application
            configurationBuilder.SetBasePath(Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location));

            // Add application settings json files
            configurationBuilder.AddJsonFile("appsettings.json", optional: true, reloadOnChange: true);

            // Let custom configuration happen
            configure?.Invoke(configurationBuilder);

            // Inject configuration into services
            var configuration = configurationBuilder.Build();
            construction.Services.AddSingleton<IConfiguration>(configuration);

            // Set the construction Configuration
            construction.UseConfiguration(configuration);

            // Chain the construction
            return construction;
        }

        /// <summary>
        /// Configures a framework construction using the provided configuration
        /// </summary>
        /// <param name="construction">The construction to configure</param>
        /// <param name="configure">The configuration</param>
        /// <returns></returns>
        public static FrameworkConstruction AddConfiguration(this FrameworkConstruction construction, IConfiguration configuration)
        {
            // Add specific configuration
            construction.UseConfiguration(configuration);

            // Add configuration to services
            construction.Services.AddSingleton(configuration);

            // Chain the construction
            return construction;
        }

        #endregion

        /// <summary>
        /// Injects all of the default services used by Framework for a quicker and cleaner setup
        /// </summary>
        /// <param name="construction">The construction</param>
        /// <returns></returns>
        public static FrameworkConstruction AddDefaultServices(this FrameworkConstruction construction)
        {
            // Add default logger
            construction.AddDefaultLogger();

            // Chain the construction
            return construction;
        }

        /// <summary>
        /// Injects the default logger into the framework construction
        /// </summary>
        /// <param name="construction">The construction</param>
        /// <returns></returns>
        public static FrameworkConstruction AddDefaultLogger(this FrameworkConstruction construction)
        {
            // Add logging as default
            construction.Services.AddLogging(options =>
            {
                // Setup loggers from configuration
                options.AddConfiguration(construction.Configuration.GetSection("Logging"));

                // Add console logger
                options.AddConsole();

                // Add debug logger
                options.AddDebug();
            });

            // Adds a default logger so that we can get a non-generic ILogger
            // that will have the category name of "BaseLogger"
            construction.Services.AddTransient(provider => provider.GetService<ILoggerFactory>().CreateLogger("BaseLogger"));

            // Chain the construction
            return construction;
        }
    }
}
