using System;
using System.Diagnostics;
using System.Threading.Tasks;

namespace Sample
{
    /// <summary>
    /// Provides abstractions for a messaging system
    /// </summary>
    public interface IMessenger
    {
        #region Methods

        /// <summary>
        /// Sends the specified <paramref name="message"/> to the specified <paramref name="receiver"/>
        /// </summary>
        /// <param name="message">The message to send</param>
        /// <param name="receiver">The receiver of the message</param>
        /// <returns></returns>
        Task SendAsync(string receiver,string message);

        #endregion
    }

    /// <summary>
    /// Mock messenger that uses the Output window of Visual Studio
    /// </summary>
    public class DebugMessenger : IMessenger
    {
        /// <summary>
        /// Sends the specified <paramref name="message"/> to the specified <paramref name="receiver"/>
        /// </summary>
        /// <param name="message">The message to send</param>
        /// <param name="receiver">The receiver of the message</param>
        /// <returns></returns>
        public Task SendAsync(string receiver, string message)
        {
            Debug.WriteLine($"Just sent the message '{message}' at the receiver '{receiver}'.");

            return Task.CompletedTask;
        }
    }

    /// <summary>
    /// Mock messenger that uses the console window
    /// </summary>
    public class ConsoleMessenger : IMessenger
    {
        public Task SendAsync(string receiver, string message)
        {
            /// <summary>
            /// Sends the specified <paramref name="message"/> to the specified <paramref name="receiver"/>
            /// </summary>
            /// <param name="message">The message to send</param>
            /// <param name="receiver">The receiver of the message</param>
            /// <returns></returns>
            Console.WriteLine($"Just sent the message '{message}' at the receiver '{receiver}'.");

            return Task.CompletedTask;
        }
    }
}
