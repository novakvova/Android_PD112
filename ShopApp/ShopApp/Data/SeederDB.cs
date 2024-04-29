using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using ShopApp.Constants;
using ShopApp.Data.Entities;
using ShopApp.Data.Entities.Identity;
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

                var userManager = scope.ServiceProvider
                    .GetRequiredService<UserManager<UserEntity>>();

                var roleManager = scope.ServiceProvider
                    .GetRequiredService<RoleManager<RoleEntity>>();

                #region Seed Roles and Users

                if (!context.Roles.Any())
                {
                    foreach (var role in Roles.All)
                    {
                        var result = roleManager.CreateAsync(new RoleEntity
                        {
                            Name = role
                        }).Result;
                    }
                }

                var userId = 0L;

                if (!context.Users.Any())
                {
                    UserEntity user = new()
                    {
                        FirstName = "Іван",
                        LastName = "Капот",
                        Email = "admin@gmail.com",
                        UserName = "admin@gmail.com",
                    };
                    var result = userManager.CreateAsync(user, "123456")
                        .Result;
                    if (result.Succeeded)
                    {
                        userId = user.Id;
                        result = userManager
                            .AddToRoleAsync(user, Roles.Admin)
                            .Result;
                    }
                }

                #endregion

                if (!context.Categories.Any())
                {
                    var flowers = new CategoryEntity
                    {
                        Name = "Квіти",
                        Description = "Гарні і свіжі",
                        Image = "flowers.webp",
                        UserId = userId,
                    };
                    var foods = new CategoryEntity
                    {
                        Name = "Фрукти",
                        Description = "Закуска не погана",
                        Image = "foods.jpg",
                        UserId = userId,
                    };
                    context.Categories.Add(flowers);
                    context.Categories.Add(foods);
                    context.SaveChanges();
                }
            }
        }
    }
}
