using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using ShopApp.Data.Entities;
using System.Data;

namespace ShopApp.Data
{
    public static class SeederDB
    {
        public static void SeedData(this IApplicationBuilder app)
        {
            using (var scope = app.ApplicationServices
                .GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<MyAppContext>();
                context.Database.Migrate();


                if (!context.Categories.Any())
                {
                    var kovbasy = new CategoryEntity
                    {
                        Name = "Квіти",
                        Description = "Гарні і свіжі"
                    };
                    var vsutiy = new CategoryEntity
                    {
                        Name = "Фрукти",
                        Description = "Гарна і класна погода"
                    };
                    context.Categories.Add(kovbasy);
                    context.Categories.Add(vsutiy);
                    context.SaveChanges();
                }
            }
        }
    }
}
