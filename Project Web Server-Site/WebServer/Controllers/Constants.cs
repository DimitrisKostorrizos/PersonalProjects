namespace WebServer
{
    public static class Constants
    {
        /// <summary>
        /// Card Const Attributes Values
        /// </summary>
        #region

        public const int CardCodeLength = 6;

        #endregion

        /// <summary>
        /// User Const Attributes Values
        /// </summary>
        #region

        public const int FirstNameLength = 20;
        public const int LastNameLength = 20;

        #endregion

        /// <summary>
        /// Company Const Attributes Values
        /// </summary>
        #region

        public const int CompanyNameLength = 20;
        public const int AfmLength = 9;
        public const int TelephoneNumberLength = 10;
        public const int CountryLength = 10;
        public const int NomosLength = 10;
        public const int DimosLength = 10;
        public const int TkLength = 5;
        public const int AddressLength = 20;
        public const int StreetNumberLength = 4;
        public const int CategoryLength = 20;

        #endregion

        /// <summary>
        /// Password Attributes Values
        /// </summary>
        #region

        public const int PasswordLength = 20;
        public const bool RequireDigit = true;
        public const bool RequireLowercase = true;
        public const bool RequireUppercase = true;
        public const bool RequireNonAlphanumeric = true;

        #endregion
    }
}
