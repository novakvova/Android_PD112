using Microsoft.EntityFrameworkCore;
using ShopApp.Data.Entities;

namespace ShopApp.Data
{
    public class MyAppContext : DbContext
    {
        public MyAppContext(DbContextOptions<MyAppContext> options) 
            : base(options)
        {
            
        }

        public DbSet<CategoryEntity> Categories { get; set; }
    }
}
