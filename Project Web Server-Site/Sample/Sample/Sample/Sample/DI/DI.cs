using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.DependencyInjection;
using System;

namespace Sample
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
        /// Gets the requested service of the specified <paramref name="serviceType"/> from the service provider
        /// </summary>
        /// <param name="serviceType">The type of the service we want to get</param>
        /// <returns></returns>
        public static object GetService(Type serviceType) => Framework.Provider.GetService(serviceType);

        /// <summary>
        /// Gets the configuration
        /// </summary>
        public static IConfiguration GetConfiguration => GetService<IConfiguration>();

        /// <summary>
        /// Gets the default logger
        /// </summary>
        public static ILogger GetLogger => GetService<ILogger>();

        /// <summary>
        /// Gets the messenger
        /// </summary>
        public static IMessenger GetMessenger => GetService<IMessenger>();

        #region JWT

        /// <summary>
        /// Gets the JWT issuer
        /// </summary>
        public static string GetJWTIssuer => GetConfiguration["Jwt:Issuer"];

        /// <summary>
        /// Gets the JWT audience
        /// </summary>
        public static string GetJWTAudience => GetConfiguration["Jwt:Audience"];

        /// <summary>
        /// Gets the JWT secret key
        /// </summary>
        public static string GetJWTSecretKey => GetConfiguration["Jwt:SecretKey"];

        #endregion
    }
}
