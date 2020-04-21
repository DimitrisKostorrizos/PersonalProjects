using System;

namespace Sample
{
    /// <summary>
    /// Specifies that the policy requires a claim of the same type name
    /// </summary>
    [AttributeUsage(AttributeTargets.Field | AttributeTargets.Class,AllowMultiple =false)]
    public class StandardPolicyAttribute : Attribute
    {

    }
}
