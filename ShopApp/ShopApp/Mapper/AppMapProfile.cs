using AutoMapper;
using ShopApp.Data.Entities;
using ShopApp.Models.Categories;

namespace ShopApp.Mapper
{
    public class AppMapProfile : Profile
    {
        public AppMapProfile()
        {
            CreateMap<CategoryEntity, CategoryItemViewModel>();
        }
    }
}
