using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebServer
{
    /// <summary>
    /// Creates a default framework construction containing all 
    /// the default configuration and services, when used inside
    /// a project that has it's own service provider such as an
    /// ASP.Net Core website
    /// </summary>
    public class HostedFrameworkConstruction : FrameworkConstruction
    {
        #region Constructor

        /// <summary>
        /// Default constructor
        /// </summary>
        public HostedFrameworkConstruction() : base(createServiceCollection: false)
        {

        }

        #endregion
    }
}
