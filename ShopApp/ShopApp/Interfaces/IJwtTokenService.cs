using ShopApp.Data.Entities.Identity;

namespace ShopApp.Interfaces
{
    public interface IJwtTokenService
    {
        Task<string> CreateToken(UserEntity user);
    }
}
