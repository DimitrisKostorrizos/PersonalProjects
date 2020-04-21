using Microsoft.AspNetCore.Authorization;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace Sample
{
    /// <summary>
    /// Helper methods associated with the authorization procedures
    /// </summary>
    public static class AuthorizationHelpers
    {
        #region Public Methods

        /// <summary>
        /// Searches the specified <typeparamref name="TClass"/> for fields with the <see cref="StandardPolicyAttribute"/> applied on them
        /// and adds the necessary policies to the specified <paramref name="options"/>.
        /// </summary>
        /// <typeparam name="TClass">The type of the class that contains the standard policy attributes</typeparam>
        /// <param name="options">The authorization options</param>
        /// <param name="policySuffix">The policy suffix</param>
        /// <param name="claimTypeSuffix">The claim type suffix</param>
        public static void ApplyStandardPolicies<TClass>(AuthorizationOptions options, string claimTypeSuffix = "ClaimType",string policySuffix = "Policy")
            where TClass : class
        {
            // Get all the members
            var fields = typeof(TClass).GetFields();

            // Calculate the length of the policy 
            var policySuffixLength = policySuffix.Length;

            IEnumerable<FieldInfo> standardPolicyFields = null;

            // Check if a StandardPolicyAttribute is applied to the class...
            if (typeof(TClass).GetCustomAttributes<StandardPolicyAttribute>().Count() > 0)
                // Get all the fields that end with the policy suffix
                standardPolicyFields = fields.Where(x => x.Name.EndsWith(policySuffix));
            // Else..
            else
                // Get all the members that have the StandardPolicy attribute
                standardPolicyFields = fields.Where(x => x.GetCustomAttributes().Any(x => x.GetType() == typeof(StandardPolicyAttribute)));

            foreach (var policyField in standardPolicyFields)
            {
                // Get the associated claim type name
                var claimTypeName = policyField.Name.Remove(policyField.Name.Length - policySuffixLength, policySuffixLength) + claimTypeSuffix;

                // Get the claim type field
                var claimTypeField = fields.FirstOrDefault(x => x.Name == claimTypeName);

                // If there isn't a member with the associated policy name...
                if (claimTypeField == null)
                    throw new InvalidOperationException($"A claim type associated with the policy member '{policyField.Name}' doesn't exist!");

                options.AddPolicy(policyField.GetValue(null).ToString(), policy => policy.RequireClaim(claimTypeField.GetValue(null).ToString(), "true"));
            }
        }

        #endregion
    }
}
