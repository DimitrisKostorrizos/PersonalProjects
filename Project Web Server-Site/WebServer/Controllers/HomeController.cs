using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;

namespace WebServer
{
    public class HomeController : Controller
    {
        protected UserManager<ApplicationUser> mUserManager;
        
        public HomeController(UserManager<ApplicationUser> userManager)
        {
            mUserManager = userManager;
        }

        /// <summary>
        /// Default action
        /// </summary>
        /// <returns></returns>
        public async Task<IActionResult> Index()
        {
            await mUserManager.CreateAsync(new ApplicationUser() { CardId = "12", UserName = "Jimakis", Email = "jimakis@gmail.com" }, "Aa12312312%3");
            return View();
        }
    }
}
