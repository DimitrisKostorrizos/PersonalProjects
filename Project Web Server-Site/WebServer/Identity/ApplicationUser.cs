using Microsoft.AspNetCore.Identity;

namespace WebServer
{
    public class ApplicationUser : IdentityUser
    {
        #region Public Properties

        /// <summary>
        /// The unique identifier of the user card
        /// </summary>
        public string CardId { get; set; }

        #endregion
    }
}
