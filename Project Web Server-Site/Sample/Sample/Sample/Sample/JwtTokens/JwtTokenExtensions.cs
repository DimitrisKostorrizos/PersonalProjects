using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.IdentityModel.Tokens;

using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;

using static Sample.DI;

namespace Sample
{
    /// <summary>
    /// Extension methods for working with Jwt bearer tokens
    /// </summary>
    public static class JwtTokenExtensions
    {
        /// <summary>
        /// Generates a Jwt bearer token containing the users username.
        /// NOTE: Since we do not encrypt the body of the token it is advisable not to put
        ///       sensitive information at the user claims!
        /// </summary>
        /// <param name="user">The users details</param>
        /// <param name="claims">The claims of the user we want to encrypt in the token</param>
        /// <returns></returns>
        public static string GenerateJwtToken(this IdentityUser user, IList<Claim> claims)
        {
            // Create the credentials used to generate the token
            var credentials = new SigningCredentials(
                // Get the secret key from configuration
                new SymmetricSecurityKey(Encoding.UTF8.GetBytes(GetJWTSecretKey)),
                // Use HS256 algorithm
                SecurityAlgorithms.HmacSha256);

            // If no claims are inserter...
            if (claims == null)
                // Create a new list of claims to add the required claims
                claims = new List<Claim>();

            // The username using the Identity name so it fills out the HttpContext.User.Identity.Name value
            claims.Add(new Claim(ClaimsIdentity.DefaultNameClaimType, user.UserName));
            // Add user Id so that UserManager.GetUserAsync can find the user based on Id
            claims.Add(new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()));

            // Generate the Jwt Token
            var token = new JwtSecurityToken(
                issuer: GetJWTIssuer,
                audience: GetJWTAudience,
                claims: claims,
                signingCredentials: credentials,
                // Expire if not used for 3 months
                expires: DateTime.Now.AddMonths(3));

            // Return the generated token
            return new JwtSecurityTokenHandler().WriteToken(token);
        }

        /// <summary>
        /// Searches the specified <paramref name="claims"/> collection for a claim with type <see cref="ClaimTypes.NameIdentifier"/>
        /// and extracts its value
        /// </summary>
        /// <param name="claims">The claims collection</param>
        /// <returns></returns>
        public static string GetUserId(this IEnumerable<Claim> claims) => claims.First(x => x.Type == ClaimTypes.NameIdentifier).Value;

        /// <summary>
        /// Gets the user id from the specified <paramref name="context"/>
        /// </summary>
        /// <param name="context">The context</param>
        /// <returns></returns>
        public static string GetUserId(this HttpContext context) => context.User.Claims.GetUserId();

        /// <summary>
        /// Searches the specified <paramref name="claims"/> collection for a claim with type <see cref="ClaimsIdentity.DefaultNameClaimType"/>
        /// and extracts its value
        /// </summary>
        /// <param name="claims">The claims collection</param>
        /// <returns></returns>
        public static string GetUserName(this IEnumerable<Claim> claims) => claims.First(x => x.Type == ClaimsIdentity.DefaultNameClaimType).Value;

        /// <summary>
        /// Gets the user name from the specified <paramref name="context"/>
        /// </summary>
        /// <param name="context">The context</param>
        /// <returns></returns>
        public static string GetUserName(this HttpContext context) => context.User.Claims.GetUserName();
    }
}
