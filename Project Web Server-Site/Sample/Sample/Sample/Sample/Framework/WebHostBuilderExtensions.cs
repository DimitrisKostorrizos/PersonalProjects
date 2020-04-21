using Microsoft.AspNetCore.Hosting;
using System;

namespace Sample
{
    /// <summary>
    /// Extension methods for <see cref="IWebHostBuilder"/>
    /// </summary>
    public static class WebHostBuilderExtensions
    {
        public static IWebHostBuilder UseCustomFramework(this IWebHostBuilder builder, Action<FrameworkConstruction> configure = null)
        {
            builder.ConfigureServices((context, services) =>
            {
                // Construct a hosted Framework
                Framework.Construct<HostedFrameworkConstruction>();

                // Use the already existing services
                Framework.Construction.UseHostedServices(services)
                // Add configuration
                .AddConfiguration(context.Configuration)
                // Add default services
                .AddDefaultServices();

                // Further configure the construction if needed
                configure?.Invoke(Framework.Construction);

                // NOTE: Framework will do .Build() from the Startup.cs Configure call
                //       app.UseCustomFramework()
            });

            // Return builder for chaining
            return builder;
        }
    }
}
