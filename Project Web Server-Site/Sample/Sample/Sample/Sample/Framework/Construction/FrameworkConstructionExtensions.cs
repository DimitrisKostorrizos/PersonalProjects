using static Sample.DI;

namespace Sample
{
    public static class FrameworkConstructionExtensions
    {

        /// <summary>
        /// Should be called once a Framework Construction is finished and we want to build it and
        /// start our application
        /// </summary>
        /// <param name="construction">The construction</param>
        /// <param name="logStarted">Specifies if the Framework Started message should be logged</param>
        public static void Build(this FrameworkConstruction construction, bool logStarted = true)
        {
            // Build the service provider
            construction.Build();
        }

    }
}
