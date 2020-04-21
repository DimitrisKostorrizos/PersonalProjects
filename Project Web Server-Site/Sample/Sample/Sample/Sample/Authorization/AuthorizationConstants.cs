namespace Sample
{
    /// <summary>
    /// Constants associated with the authorization policies of the server
    /// </summary>
    [StandardPolicy]
    public class AuthorizationConstants
    {
        #region Claim Types

        public const string AdminClaimType = "Admin";

        public const string SecretaryClaimType = "Secretary";

        public const string StaffClaimType = "Staff";

        public const string CanAddProductClaimType = "CanAddProduct";

        public const string CanEditProductClaimType = "CanEditProduct";

        public const string CanDeleteProductClaimType = "CanDeleteProduct";

        public const string CanAddCustomerClaimType = "CanAddCustomer";

        public const string CanEditCustomerClaimType = "CanEditCustomer";

        public const string CanDeleteCustomerClaimType = "CanEditCustomer";

        #endregion

        #region Policies

        public const string AdminPolicy = "Admin";

        public const string SecretaryPolicy = "Secretary";

        public const string StaffPolicy = "Staff";

        public const string CanAddProductPolicy = "CanAddProduct";

        public const string CanEditProductPolicy = "CanEditProduct";

        public const string CanDeleteProductPolicy = "CanDeleteProduct";

        public const string CanAddCustomerPolicy = "CanAddCustomer";

        public const string CanEditCustomerPolicy = "CanEditCustomer";

        public const string CanDeleteCustomerPolicy = "CanEditCustomer";

        #endregion
    }
}
