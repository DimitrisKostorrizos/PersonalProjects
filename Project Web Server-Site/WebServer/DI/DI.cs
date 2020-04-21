using System;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace WebServer
{
    /// <summary>
    /// The default services of the framework that should be available everywhere in the code
    /// </summary>
    public static class DI
    {
        /// <summary>
        /// Gets the requested service from the service provider
        /// </summary>
        /// <typeparam name="TService">
        /// The type of the interface that the view model should implement.
        /// NOTE: Some services require a different approach when retrieving them.
        ///       For example, some services , in order to function properly, require
        ///       some variables that their default parameterless constructors can't set.
        ///       In that case, use the methods located in the DI in their own library!
        /// </typeparam>
        /// <returns></returns>
        public static TService GetService<TService>(Action<TService> configuration = null)
        {
            var service = Framework.Provider.GetService<TService>();

            configuration?.Invoke(service);

            return service;
        }

        /// <summary>
        /// Gets the configuration
        /// </summary>
        public static IConfiguration GetConfiguration => GetService<IConfiguration>();

        /// <summary>
        /// Gets the default logger
        /// </summary>
        public static ILogger GetLogger => GetService<ILogger>();

        /// <summary>
        /// Gets the logger factory for creating loggers
        /// </summary>
        public static ILoggerFactory GetLoggerFactory => GetService<ILoggerFactory>();

        /// <summary>
        /// The database scheme
        /// </summary>
        public static WebServerDbContext GetDatabase => GetService<WebServerDbContext>();

        /// <summary>
        /// Gets the user manager
        /// </summary>
        public static UserManager<ApplicationUser> GetUserManager => (UserManager<ApplicationUser>)Framework.Provider.CreateScope().ServiceProvider.GetService(typeof(UserManager<ApplicationUser>));
    }
}
