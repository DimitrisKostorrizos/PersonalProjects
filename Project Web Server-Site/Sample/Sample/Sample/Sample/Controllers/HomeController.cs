using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;

using static Sample.AuthorizationConstants;

namespace Sample
{
    public class HomeController : Controller
    {
        #region Private Members

        /// <summary>
        /// The messenger
        /// </summary>
        private IMessenger mMessenger;

        #endregion

        #region Constructors

        /// <summary>
        /// Default constructor.
        /// NOTE: We can specify any service type as a constructor parameter!
        ///       This services will be directly taken by the service provider
        ///       of the DI. If no matching service is found the <see cref="null"/>
        ///       will be passed as a parameter!
        /// NOTE: A controller gets created for every request.
        ///       Therefore, if the controller handles millions of request per minute,
        ///       requesting many services using the constructor, that we do not use 
        ///       in all of our methods could have a performance impact on our server (Assumption)!
        /// </summary>
        public HomeController(IMessenger messenger) => mMessenger = messenger;

        #endregion

        #region Public Methods

        /// <summary>
        /// Default index method that navigates to the index page
        /// </summary>
        /// <returns></returns>
        public IActionResult Index()
        {
            return View();
        }

        /// <summary>
        /// Generates and returns a JTW token that contains the <see cref="AuthorizationConstants.AdminClaimType"/>
        /// </summary>
        /// <returns></returns>
        [AllowAnonymous]
        [Route("api/getadmintoken")]
        [HttpPost]
        public Task<string> GetAdminTokenAsync()
        {
            var user = new IdentityUser("Jimakos")
            {
                Id = "1"
            };

            // NOTE: Usually the claims are taken by querying the database!
            var token = user.GenerateJwtToken(new List<Claim>() { new Claim(AdminClaimType, "true") });

            mMessenger.SendAsync("6977594432", $"Created an admin token for {user.UserName}!");

            return Task.FromResult(token);
        }

        /// <summary>
        /// Generates and returns a JTW token that contains the <see cref="AuthorizationConstants.StaffClaimType"/> 
        /// </summary>
        /// <returns></returns>
        [AllowAnonymous]
        [Route("api/getstafftoken")]
        [HttpPost]
        public Task<string> GetStaffTokenAsync()
        {
            var user = new IdentityUser("PapLabros")
            {
                Id = "2"
            };

            // NOTE: Usually the claims are taken by querying the database!
            var token = user.GenerateJwtToken(new List<Claim>() { new Claim(StaffClaimType, "true") });

            mMessenger.SendAsync("6977594432", $"Created an staff token for {user.UserName}!");

            return Task.FromResult(token);
        }

        /// <summary>
        /// This area can be accessed only by the users whose JTW token contain the <see cref="AdminClaimType"/>
        /// </summary>
        /// <returns></returns>
        [Authorize(Policy = AdminPolicy)]
        [Route("api/adminarea")]
        [HttpPost]
        public Task<string> GetInAdminAreaAsync()
        {
            mMessenger.SendAsync("6977594432", $"The user '{HttpContext.GetUserName()}' has entered the admin area!");

            return Task.FromResult("Access granted!");
        }

        /// <summary>
        /// This area can be accessed only by the users whose JTW token contain the <see cref="StaffPolicy"/>
        /// </summary>
        /// <returns></returns>
        [Authorize(Policy = StaffPolicy)]
        [Route("api/staffarea")]
        [HttpPost]
        public Task<string> GetInStaffAreaAsync()
        {
            mMessenger.SendAsync("6977594432", $"The user '{HttpContext.GetUserName()}' has entered the staff area!");

            return Task.FromResult("Access granted!");
        }

        #endregion
    }
}
