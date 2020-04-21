using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace WebServer
{
    public class Startup
    {
        public IConfiguration Configuration { get; }

        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        /// <summary>
        /// This method gets called by the runtime. Use this method to add services to the container.
        /// For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940</summary>
        /// <param name="services"></param>
        public void ConfigureServices(IServiceCollection services)
        {
            // Adds the database context to the services
            services.AddDbContext<WebServerDbContext>(options =>
            {
                options.UseMySQL(Configuration.GetConnectionString("DefaultConnection"));
            }, ServiceLifetime.Singleton);

            // Adds scoped classes for things like UserManager, SignInManager, PasswordHashers etc..
            // NOTE: Automatically adds the validated user from a cookie to the HttpContext.User
            // https://github.com/aspnet/Identity/blob/85f8a49aef68bf9763cd9854ce1dd4a26a7c5d3c/src/Identity/IdentityServiceCollectionExtensions.cs
            services.AddIdentity<ApplicationUser, IdentityRole>()
                    .AddEntityFrameworkStores<WebServerDbContext>();

            // Configure password requirments
            services.Configure<IdentityOptions>((options) =>
            {
                options.Password = new PasswordOptions()
                {
                    RequireDigit = Constants.RequireDigit,
                    RequireLowercase = Constants.RequireLowercase,
                    RequireUppercase = Constants.RequireUppercase,
                    RequireNonAlphanumeric = Constants.RequireNonAlphanumeric,
                };
            });

            services.AddAuthentication().AddJwtBearer((options) => 
            {

            });

            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
        }

        /// <summary>
        /// This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        /// </summary>
        /// <param name="app"></param>
        /// <param name="env"></param>
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            Framework.Construction.Build(app.ApplicationServices);

            // Creates the data base if it doesn't exists
            // NOTE: Changes are not made to the database, migrations should be used for that functionality
            DI.GetDatabase.Database.EnsureCreated();
            
            // Setup Identity
            app.UseAuthentication();

            if (env.IsDevelopment())
                app.UseDeveloperExceptionPage();
            else
                app.UseExceptionHandler("/Home/Error");

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{moreInfo?}");

                routes.MapRoute(
                    name: "aboutPage",
                    template: "more",
                    defaults: new { controller = "About", action = "TellMeMore" });
            });
        }
    }
}
