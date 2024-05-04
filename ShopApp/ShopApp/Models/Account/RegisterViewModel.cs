using System.ComponentModel.DataAnnotations;

namespace ShopApp.Models.Account
{
    public class RegisterViewModel
    {
        /// <summary>
        /// Ім'я користувача
        /// </summary>
        /// <example>Іван</example>
        public required string FirstName { get; set; }
        /// <summary>
        /// Прізвище користувача
        /// </summary>
        /// <example>Марко</example>
        public required string LastName { get; set; }

        /// <summary>
        /// Логін користувача
        /// </summary>
        /// <example>example@gmail.com</example>
        public required string Email { get; set; }
        /// <summary>
        /// Логін користувача
        /// </summary>
        /// <example>123456</example>
        public required string Password { get; set; }
    }
}
