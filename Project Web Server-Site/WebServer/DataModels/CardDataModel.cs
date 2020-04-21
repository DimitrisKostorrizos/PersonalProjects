using System;
using System.ComponentModel.DataAnnotations;

namespace WebServer
{
    /// <summary>
    /// Cards database table representational model
    /// </summary>
    public class CardDataModel
    {
        /// <summary>
        /// The unique Id for this entry
        /// </summary>
        public string Id { get; set; }

        /// <summary>
        /// Card Code
        /// </summary>
        [Required]
        [MaxLength(Constants.CardCodeLength)]
        public string CardCode { get; set; }

        /// <summary>
        /// Card's Starting Date
        /// </summary>
        [Required]
        public DateTime StartingDate { get; set; }

        /// <summary>
        /// Card's Expiration Date
        /// </summary>
        [Required]
        public DateTime ExpirationDate { get; set; }
    }
}