namespace ShopApp.Models.Account
{
    public class LoginViewModel
    {
        /// <summary>
        /// Логін користувача
        /// </summary>
        /// <example>admin@gmail.com</example>
        public required string Email { get; set; }
        /// <summary>
        /// Логін користувача
        /// </summary>
        /// <example>123456</example>
        public required string Password { get; set; }
    }
}
