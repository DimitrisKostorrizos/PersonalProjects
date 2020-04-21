using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.IdentityModel.Tokens;

using System.Text;

using static Sample.DI;

namespace Sample
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        /// <summary>
        /// This method gets called by the runtime. Use this method to add services to the container.
        /// </summary>
        /// <param name="services"></param>
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllersWithViews();

            // Add our custom messenger service
            // NOTE: This is a simple example of using the DI.
            //       We use DI because it makes our application a lot more modifiable.
            //       In this instance we can very easily switch between type of messenger
            //       we want our server to use by simply changing the implantation type of
            //       IMessenger interface!
            services.AddSingleton<IMessenger, DebugMessenger>();

            // Adds a JWT bearer token authentication used for the native applications
            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme).AddJwtBearer((options) =>
            {
                options.TokenValidationParameters = new TokenValidationParameters()
                {
                    // Validate issuer
                    ValidateIssuer = true,
                    // Validate audience
                    ValidateAudience = true,
                    // Validate expiration
                    ValidateLifetime = true,
                    // Validate signature
                    ValidateIssuerSigningKey = true,

                    // Set issuer
                    ValidIssuer = GetJWTIssuer,
                    // Set audience
                    ValidAudience = GetJWTAudience,

                    // Set signing key
                    IssuerSigningKey = new SymmetricSecurityKey(
                            // Get our secret key from configuration
                            Encoding.UTF8.GetBytes(GetJWTSecretKey)),
                };
            });

            // Configure the authorization policies
            services.Configure<AuthorizationOptions>((options) =>
            {
                AuthorizationHelpers.ApplyStandardPolicies<AuthorizationConstants>(options);
            });

            services.Configure<CookiePolicyOptions>((options) =>
            { 
                // This lambda determines whether user consent for non-essential cookies is needed for a given request.
                options.CheckConsentNeeded = context => true;
                options.MinimumSameSitePolicy = SameSiteMode.None;
            });

            services.AddMvc((options) => options.EnableEndpointRouting = false).SetCompatibilityVersion(CompatibilityVersion.Version_3_0);
        }

        /// <summary>
        /// This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        /// </summary>
        /// <param name="app"></param>
        /// <param name="env"></param>
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            // Use our Framework
            app.UseCustomFramework();

            // Setup Identity
            app.UseAuthentication();
            app.UseAuthorization();

            if (env.IsDevelopment())
                app.UseDeveloperExceptionPage();
            else
            {
                app.UseExceptionHandler("/Home/Error");
                app.UseHsts();
            }
            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller=Home}/{action=Index}/{id?}");
            });

            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });

            //var policy = await GetService<IAuthorizationPolicyProvider>().GetPolicyAsync("Admin");
        }
    }
}
