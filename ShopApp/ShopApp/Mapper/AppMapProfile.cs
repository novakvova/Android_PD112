using AutoMapper;
using ShopApp.Data.Entities;
using ShopApp.Data.Entities.Identity;
using ShopApp.Models.Account;
using ShopApp.Models.Categories;

namespace ShopApp.Mapper
{
    public class AppMapProfile : Profile
    {
        public AppMapProfile()
        {
            CreateMap<CategoryEntity, CategoryItemViewModel>();
            CreateMap<RegisterViewModel, UserEntity>()
                .ForMember(x=>x.UserName, opt=>opt.MapFrom(x=>x.Email));
        }
    }
}
