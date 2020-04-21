using Microsoft.Extensions.Configuration;
using System;

namespace WebServer
{
    /// <summary>
    /// The default framework construction containing all the default services
    /// </summary>
    public class DefaultFrameworkConstruction : FrameworkConstruction
    {
        #region Constructor

        /// <summary>
        /// Default constructor
        /// </summary>
        public DefaultFrameworkConstruction()
        {
            // Configure...
            this.AddDefaultConfiguration()
                // And add default services
                .AddDefaultServices();
        }

        /// <summary>
        /// Constructor with configuration options
        /// </summary>
        public DefaultFrameworkConstruction(Action<IConfigurationBuilder> configure)
        {
            // Configure...
            this.AddDefaultConfiguration(configure)
                // And add default services
                .AddDefaultServices();
        }

        #endregion
    }
}
