using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using ShopApp.Data.Entities;
using ShopApp.Data.Entities.Identity;

namespace ShopApp.Data
{
    public class MyAppContext : IdentityDbContext<UserEntity, RoleEntity, long,
        IdentityUserClaim<long>, UserRoleEntity, IdentityUserLogin<long>,
        IdentityRoleClaim<long>, IdentityUserToken<long>>
    {
        public MyAppContext(DbContextOptions<MyAppContext> options) 
            : base(options)
        {
            
        }

        public DbSet<CategoryEntity> Categories { get; set; }
    }
}
