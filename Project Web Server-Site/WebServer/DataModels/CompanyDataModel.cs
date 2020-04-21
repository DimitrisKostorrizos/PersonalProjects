using System.ComponentModel.DataAnnotations;

namespace WebServer
{
    /// <summary>
    /// Company database table representational model
    /// <summary>
    public class CompanyDataModel
    {
        /// <summary>
        /// The unique Id for this entry
        /// </summary>
        public string Id { get; set; }

        /// <summary>
        /// The Company's Name
        /// </summary>
        [Required]
        [MaxLength(Constants.CompanyNameLength)]
        public string CompanyName { get; set; }

        /// <summary>
        /// Company's Afm
        /// </summary>
        [Required]
        [MaxLength(Constants.AfmLength)]
        public string Afm { get; set; }

        /// <summary>
        /// Company's Telephone Number
        /// </summary>
        [MaxLength(Constants.TelephoneNumberLength)]
        public string TelephoneNumber { get; set; }

        /// <summary>
        /// Compny's Site Url
        /// </summary>
        public string SiteUrl { get; set; }

        /// <summary>
        /// Company's Email
        /// </summary>
        [Required]
        public string Email { get; set; }

        /// <summary>
        /// Company's Country
        /// </summary>
        [Required]
        [MaxLength(Constants.CountryLength)]
        public string Country { get; set; }

        /// <summary>
        /// Company's Nomos
        /// </summary>
        [Required]
        [MaxLength(Constants.NomosLength)]
        public string Nomos { get; set; }

        /// <summary>
        /// Company's Dimos
        /// </summary>
        [Required]
        [MaxLength(Constants.DimosLength)]
        public string Dimos { get; set; }

        /// <summary>
        /// Company's Tk
        /// </summary>
        [Required]
        [MaxLength(Constants.TkLength)]
        public string Tk { get; set; }

        /// <summary>
        /// Company's Address
        /// </summary>
        [Required]
        [MaxLength(Constants.AddressLength)]
        public string Address { get; set; }

        /// <summary>
        /// Company's Street Number
        /// </summary>
        [Required]
        [MaxLength(Constants.StreetNumberLength)]
        public string StreetNumber { get; set; }

        /// <summary>
        /// Company's Category
        /// </summary>
        [Required]
        [MaxLength(Constants.CategoryLength)]
        public string Category { get; set; }
    }
}