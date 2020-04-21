using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace Sample
{
    public class Program
    {
        public static void Main()
        {
            CreateHostBuilder().Build().Run();
        }

        public static IWebHostBuilder CreateHostBuilder()
        {
            return WebHost.CreateDefaultBuilder()
              .UseCustomFramework()
              .UseStartup<Startup>();
        }
           
    }
}
