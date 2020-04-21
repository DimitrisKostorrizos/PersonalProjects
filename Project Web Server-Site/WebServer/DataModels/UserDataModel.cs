using System.ComponentModel.DataAnnotations;

namespace WebServer
{
    /// <summary>
    /// Users database table representational model
    /// </summary>
    public class UsersDataModel
    {
        /// <summary>
        /// The unique Id for this entry
        /// </summary>
        public string Id { get; set; }

        /// <summary>
        /// User First Name
        /// </summary>
        [Required]
        [MaxLength(Constants.FirstNameLength)]
        public string FirstName { get; set; }

        /// <summary>
        /// User Last Name
        /// </summary>
        [Required]
        [MaxLength(Constants.LastNameLength)]
        public string LastName { get; set; }

        /// <summary>
        /// User Password
        /// </summary>
        [Required]
        [MaxLength(Constants.PasswordLength)]
        public string Password { get; set; }

        /// <summary>
        /// User Email
        /// </summary>
        [Required]
        public string Email { get; set; }

        /// <summary>
        /// User Recovery Email
        /// </summary>
        [Required]
        public string RecoveryEmail { get; set; }

        /// <summary>
        /// User Recovery Email
        /// </summary>
        [Required]
        [MaxLength(Constants.TelephoneNumberLength)]
        public string Telephone { get; set; }

        /// <summary>
        /// User Card Code
        /// </summary>
        [Required]
        public CardDataModel CardCode { get; set; }
    }
}